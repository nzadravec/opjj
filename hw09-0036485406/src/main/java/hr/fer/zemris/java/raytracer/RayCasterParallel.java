package hr.fer.zemris.java.raytracer;

import static java.lang.Math.max;
import static java.lang.Math.pow;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

public class RayCasterParallel {

	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	public static class ComputeJob extends RecursiveAction {

		private static final long serialVersionUID = 1L;

		static final int treshold = 16;

		private Point3D eye;
		private double horizontal;
		private double vertical;
		private int width;
		private int height;
		private short[] red;
		private short[] green;
		private short[] blue;
		private int yMin;
		private int yMax;
		private Point3D yAxis;
		private Point3D xAxis;
		private Point3D screenCorner;
		private Scene scene;

		public ComputeJob(Point3D eye, double horizontal, double vertical, int width, int height, short[] red,
				short[] green, short[] blue, int yMin, int yMax, Point3D yAxis, Point3D xAxis, Point3D screenCorner,
				Scene scene) {
			super();
			this.eye = eye;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.yMin = yMin;
			this.yMax = yMax;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
		}

		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= treshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new ComputeJob(eye, horizontal, vertical, width, height, red, green, blue, yMin,
							yMin + (yMax - yMin) / 2, yAxis, xAxis, screenCorner, scene),
					new ComputeJob(eye, horizontal, vertical, width, height, red, green, blue,
							yMin + (yMax - yMin) / 2 + 1, yMax, yAxis, xAxis, screenCorner, scene));
		}

		public void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1)))
							.modifySub(yAxis.scalarMultiply(y * vertical / (height - 1)));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
			}
		}

	}

	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Point3D zAxis = view.sub(eye).modifyNormalize();
				viewUp.modifyNormalize();
				Point3D yAxis = viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).modifyNormalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).modifyNormalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.modifyAdd(yAxis.scalarMultiply(vertical / 2));
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new ComputeJob(eye, horizontal, vertical, width, height, red, green, blue, 0, height - 1, yAxis,
						xAxis, screenCorner, scene));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}
	
	/**
	 * Determines closest intersection of ray and any object in the scene (in front
	 * of observer); if no intersection exists, returns rgb(0,0,0) else return
	 * rbg({@link #determineColorFor()}).
	 * 
	 * @param scene scene
	 * @param ray ray
	 * @param rgb rgb array
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
		} else {
			determineColorFor(scene, closest, ray.start, rgb);
		}
	};

	/**
	 * Determines color for the intersection.
	 * 
	 * @param scene scene
	 * @param intersection intersection
	 * @param eye eye-position
	 * @param rgb rgb array
	 */
	private static void determineColorFor(Scene scene, RayIntersection intersection, Point3D eye, short[] rgb) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		Point3D diffuseComponent = new Point3D();
		Point3D reflectiveComponent = new Point3D();
		for (LightSource ls : scene.getLights()) {
			Ray ray = Ray.fromPoints(intersection.getPoint(), ls.getPoint());
			RayIntersection closest = findClosestIntersection(scene, ray);
			if (closest != null) {
				continue;
			}

			Point3D i = new Point3D(ls.getR(), ls.getG(), ls.getB());
			Point3D l = ray.direction;
			Point3D n = intersection.getNormal();

			diffuseComponent.modifyAdd(i.scalarMultiply(max(l.scalarProduct(n), 0)));

			Point3D r = reflectedVector(l, n);
			Point3D v = eye.sub(intersection.getPoint()).modifyNormalize();
			double rDotv = r.scalarProduct(v);
			if (rDotv <= 0) {
				continue;
			}

			reflectiveComponent.modifyAdd(i.scalarMultiply(pow(rDotv, intersection.getKrn())));
		}

		rgb[0] += intersection.getKdr() * diffuseComponent.x;
		rgb[1] += intersection.getKdg() * diffuseComponent.y;
		rgb[2] += intersection.getKdb() * diffuseComponent.z;

		rgb[0] += intersection.getKrr() * reflectiveComponent.x;
		rgb[1] += intersection.getKrg() * reflectiveComponent.y;
		rgb[2] += intersection.getKrb() * reflectiveComponent.z;
	}

	/**
	 * Returns normalized reflected vector of m over n.
	 * 
	 * @param m vector to reflect
	 * @param n vector to reflect over
	 * @return normalized reflected vector of m over n
	 */
	private static Point3D reflectedVector(Point3D m, Point3D n) {
		return n.scalarMultiply(2 * m.scalarProduct(n)).modifySub(m).modifyNormalize();
	}

	/**
	 * Returns closest intersection of ray in scene.
	 * 
	 * @param scene scene
	 * @param ray ray
	 * @return closest intersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closest = null;
		for (GraphicalObject o : scene.getObjects()) {
			RayIntersection intersection = o.findClosestRayIntersection(ray);
			if (intersection == null) {
				continue;
			}
			if (closest == null || closest.getDistance() > intersection.getDistance()) {
				closest = intersection;
			}
		}

		return closest;
	}

}
