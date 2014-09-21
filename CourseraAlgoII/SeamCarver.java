import java.awt.Color;
import java.io.File;

/**
 * 
 * Coursera Alogorithm II PA2
 * 
 * Raw Score 95.83 / 100.00
 * 
 * Correctness: 18/18 tests passed
 * Memory: 7/7 tests passed 
 * Timing: 5/6 tests  passed
 * 
 * In removal, I create a new Picture Object everytime. In fact, we can do some laziness optimization. when picture() was called, we create a new Picture.
 * 
 * 
 * @since 2014.04.02
 * @author whimsycwd
 * 
 */

public class SeamCarver {
	private Picture pic;

	public SeamCarver(Picture picture) {
		pic = picture;

	}

	// current picture
	public Picture picture() {
		return pic;

	}

	// width of current picture
	public int width() {
		return pic.width();
	}

	// height of current picture
	public int height() {
		return pic.height();
	}

	// energy of pixel at column x and row y in current picture
	public double energy(int x, int y) {
		if (x < 0 || x >= width() || y < 0 || y >= height())
			throw new IndexOutOfBoundsException();
		if (x == 0 || y == 0 || x + 1 == width() || y + 1 == height()) {
			return 255 * 255 * 3;
		}

		return delta(pic.get(x - 1, y), pic.get(x + 1, y))
				+ delta(pic.get(x, y - 1), pic.get(x, y + 1));

	}

	private double delta(Color l, Color r) {

		return Math.pow(l.getRed() - r.getRed(), 2)
				+ Math.pow(l.getBlue() - r.getBlue(), 2)
				+ Math.pow(l.getGreen() - r.getGreen(), 2);
	}

	// sequence of indices for horizontal seam in current picture
	public int[] findHorizontalSeam() {
		double[][] dist = new double[width()][height()];
		for (int i = 0; i < width(); ++i)
			for (int j = 0; j < height(); ++j)
				dist[i][j] = Integer.MAX_VALUE;

		for (int i = 0; i < height(); ++i)
			dist[0][i] = energy(0, i);

		for (int i = 1; i < width(); ++i) {
			for (int j = 0; j < height(); ++j) {
				dist[i][j] = Math
						.min(dist[i - 1][j] + energy(i, j), dist[i][j]);
				if (j > 0) {
					dist[i][j] = Math.min(dist[i - 1][j - 1] + energy(i, j),
							dist[i][j]);
				}
				if (j + 1 < height()) {
					dist[i][j] = Math.min(dist[i - 1][j + 1] + energy(i, j),
							dist[i][j]);
				}
			}
		}
		double min = Integer.MAX_VALUE;
		int mark = -1;
		for (int j = 0; j < height(); ++j) {
			if (dist[width() - 1][j] < min) {
				min = dist[width() - 1][j];
				mark = j;
			}
		}
		Stack<Integer> stack = new Stack<Integer>();

		for (int i = width() - 1; i >= 0; --i) {
			stack.push(mark);
			if (i == 0)
				break;
			min -= energy(i, mark);
			if (mark != 0 && min == dist[i - 1][mark - 1]) {
				mark = mark - 1;
			} else if (mark + 1 != height() && min == dist[i - 1][mark + 1]) {
				mark = mark + 1;
			}
		}
		int[] ret = new int[stack.size()];
		int cnt = 0;
		while (!stack.isEmpty()) {
			ret[cnt++] = stack.pop();
		}
		return ret;
	}

	// sequence of indices for vertical seam in current picture
	public int[] findVerticalSeam() {
		double[][] dist = new double[width()][height()];
		for (int i = 0; i < width(); ++i)
			for (int j = 0; j < height(); ++j)
				dist[i][j] = Integer.MAX_VALUE;

		for (int i = 0; i < width(); ++i)
			dist[i][0] = energy(i, 0);

		for (int j = 1; j < height(); ++j) {
			for (int i = 0; i < width(); ++i) {

				dist[i][j] = Math
						.min(dist[i][j - 1] + energy(i, j), dist[i][j]);
				if (i > 0) {
					dist[i][j] = Math.min(dist[i - 1][j - 1] + energy(i, j),
							dist[i][j]);
				}
				if (i + 1 < width()) {
					dist[i][j] = Math.min(dist[i + 1][j - 1] + energy(i, j),
							dist[i][j]);
				}
			}
		}
		double min = Integer.MAX_VALUE;
		int mark = -1;
		for (int i = 0; i < width(); ++i) {
			if (dist[i][height() - 1] < min) {
				min = dist[i][height() - 1];
				mark = i;
			}
		}
		Stack<Integer> stack = new Stack<Integer>();

		for (int i = height() - 1; i >= 0; --i) {
			stack.push(mark);
			if (i == 0)
				break;
			// System.out.println(mark);
			min -= energy(mark, i);
			if (mark != 0 && min == dist[mark - 1][i - 1]) {
				mark = mark - 1;
			} else if (mark + 1 != width() && min == dist[mark + 1][i - 1]) {
				mark = mark + 1;
			}
		}
		int[] ret = new int[stack.size()];
		int cnt = 0;
		while (!stack.isEmpty()) {
			ret[cnt++] = stack.pop();
		}
		return ret;
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] a) {
		if (a.length != width() || height() == 1)
			throw new IllegalArgumentException();
		for (int i = 1; i < a.length; ++i) {
			if (Math.abs(a[i] - a[i - 1]) > 1)
				throw new IllegalArgumentException();
		}
		for (int i = 0; i < a.length; ++i) {
			if (a[i] < 0 || a[i] >= height())
				throw new IllegalArgumentException();
		}

		Picture newPic = new Picture(width(), height() - 1);
		for (int i = 0; i < width(); ++i) {
			for (int j = 0; j < a[i]; ++j) {
				newPic.set(i, j, pic.get(i, j));
			}
			for (int j = a[i]; j < height() - 1; ++j) {
				newPic.set(i, j, pic.get(i, j + 1));
			}
		}

		pic = newPic;
	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] a) {
		// System.out.println(a.length+ " " + height());
		if (a.length != height() || width() == 1)
			throw new IllegalArgumentException();
		for (int i = 1; i < a.length; ++i) {
			if (Math.abs(a[i] - a[i - 1]) > 1)
				throw new IllegalArgumentException();
		}
		for (int i = 0; i < a.length; ++i) {
			if (a[i] < 0 || a[i] >= width())
				throw new IllegalArgumentException();
		}

		Picture newPic = new Picture(width() - 1, height());
		for (int j = 0; j < height(); ++j) {
			for (int i = 0; i < a[j]; ++i) {
				newPic.set(i, j, pic.get(i, j));
			}
			for (int i = a[j]; i < width() - 1; ++i) {
				newPic.set(i, j, pic.get(i + 1, j));
			}
		}

		pic = newPic;
	}

	private void save(String filename) {
		pic.save(new File(filename));
	}

	public static void main(String[] args) {
		SeamCarver sc = new SeamCarver(new Picture("HJoceanSmall.png"));
		for (int i = 1; i <= 220; ++i) {
			sc.removeVerticalSeam(sc.findVerticalSeam());
		}
		for (int i = 1; i <= 100; ++i) {
			sc.removeHorizontalSeam(sc.findHorizontalSeam());
		}
		sc.save("temp.png");

	}
}
