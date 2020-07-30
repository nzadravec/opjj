package hr.fer.zemris.java.raytracer.model;

import static java.lang.Math.sqrt;

/**
 * Represents graphical object sphere.
 * 
 * @author nikola
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Spheres center
	 */
	private Point3D center;
	/**
	 * Spheres radius
	 */
	private double radius;
	/**
	 * Diffuse components
	 */
	private double kdr;
	private double kdg;
	private double kdb;
	/**
	 * Reflective components
	 */
	private double krr;
	private double krg;
	private double krb;
	private double krn;

	/**
	 * Creates arbitrary graphical object sphere.
	 * 
	 * @param center spheres center
	 * @param radius spheres radius
	 * @param kdr red diffuse component
	 * @param kdg green diffuse component
	 * @param kdb blue diffuse component
	 * @param krr red reflective component
	 * @param krg green reflective component
	 * @param krb red reflective component
	 * @param krn coefficient <code>n</code> {@link RayIntersection#getKrn()}
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D diff = ray.start.sub(center);
		double b = 2*ray.direction.scalarProduct(diff);
		double c = diff.scalarProduct(diff) - radius*radius;
		double D = b*b - 4*c;
		if(D < 0) {
			return null;
		}
		double lambda1 = (-b + sqrt(D)) / 2;
		double lambda2 = (-b - sqrt(D)) / 2;
		
		double lambda = lambda1 < lambda2 ? lambda1 : lambda2;
		if(lambda <= 0) {
			return null;
		}
		boolean outer = true;
		if(lambda1 * lambda2 <= 0) {
			outer = false;
		}
		
		Point3D point = ray.start.add(ray.direction.scalarMultiply(lambda));
		double distance = point.sub(ray.start).norm();
		
		return new RayIntersection(point, distance, outer) {
			
			private Point3D normal = point.sub(center).modifyNormalize();
			
			@Override
			public Point3D getNormal() {
				return normal;
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
