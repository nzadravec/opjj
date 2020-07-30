package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * The program asks user to enter roots (complex numbers) to define polynomial,
 * and then it starts fractal viewer and display the fractal. If no root is
 * given, program uses default polynomial f(z) = z^4 - 1.
 * 
 * General syntax for complex numbers is of form a+ib or a-ib where parts that
 * are zero can be dropped, but not both (empty string is not legal complex
 * number); for example, zero can be given as 0, i0, 0+i0, 0-i0. If there is 'i'
 * present but no b is given, it is assumed that b=1.
 * 
 * @author nikola
 *
 */
public class Newton {

	/**
	 * Maximum number of iterations
	 */
	private static final int MAX_ITER = 16;
	/**
	 * Convergence threshold of |z_{n+1}-z_n|
	 */
	private static final double CONVERGENCE_TRESHOLD = 1E-3;
	/**
	 * Acceptable root-distance
	 */
	private static final double ROOT_TRESHOLD = 0.002;

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		List<Complex> cs = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int index = 1;
		while (true) {
			System.out.print("Root " + index + "> ");
			String line = sc.nextLine();
			if (line.equals("done")) {
				break;
			}

			try {
				cs.add(Complex.parse(line));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
			index++;
		}
		sc.close();

		ComplexRootedPolynomial rooted;
		if (cs.size() == 0) {
			Complex[] roots = new Complex[] { new Complex(1, 0), new Complex(-1, 0), new Complex(0, 1),
					new Complex(0, -1) };
			rooted = new ComplexRootedPolynomial(roots);
			System.out.println("Using default polynomial: " + rooted);
		} else {
			Complex[] roots = new Complex[cs.size()];
			for (int i = 0; i < cs.size(); i++) {
				roots[i] = cs.get(i);
			}
			rooted = new ComplexRootedPolynomial(roots);
		}

		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new MyProducer(rooted));
	}

	public static class ComputeJob implements Callable<Void> {

		private ComplexRootedPolynomial rooted;
		private ComplexPolynomial polynomial;
		private ComplexPolynomial derived;

		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		short[] data;

		public ComputeJob(ComplexRootedPolynomial rooted, ComplexPolynomial polynomial, double reMin, double reMax,
				double imMin, double imMax, int width, int height, int yMin, int yMax, short[] data) {
			super();
			this.rooted = rooted;
			this.polynomial = polynomial;
			this.derived = polynomial.derive();

			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
		}

		@Override
		public Void call() throws Exception {
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {

					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);
					int iter = 0;
					Complex zn1;
					double module;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iter++;
					} while (module > CONVERGENCE_TRESHOLD && iter < MAX_ITER);
					int index = rooted.indexOfClosestRootFor(zn1, ROOT_TRESHOLD);
					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) index;
					}
				}
			}

			return null;
		}
	}

	public static class MyProducer implements IFractalProducer {

		private ComplexRootedPolynomial rooted;
		private ExecutorService pool;

		public MyProducer(ComplexRootedPolynomial rooted) {
			super();
			this.rooted = rooted;
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {

				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setDaemon(true);
					return t;
				}
			});
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {

			ComplexPolynomial polynomial = rooted.toComplexPolynom();

			short[] data = new short[width * height];
			final int numOfLanes = 8 * Runtime.getRuntime().availableProcessors();
			int numOfYPerLane = height / numOfLanes;

			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < numOfLanes; i++) {
				int yMin = i * numOfYPerLane;
				int yMax = (i + 1) * numOfYPerLane - 1;
				if (i == numOfLanes - 1) {
					yMax = height - 1;
				}
				ComputeJob job = new ComputeJob(rooted, polynomial, reMin, reMax, imMin, imMax, width, height, yMin,
						yMax, data);
				results.add(pool.submit(job));
			}
			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

	}

}
