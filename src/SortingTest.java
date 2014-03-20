import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * 
 * http://stackoverflow.com/questions/3707190/why-java-arrays-use-two-different-
 * sort-algorithms-for-different-types
 * 
 * Q1:
 * Arrays.sort() use both quicksort and mergesort, Why bother?
 * 
 * A1:
 * The most likely reason: quicksort is not stable, i.e. equal entries can
 * change their relative position during the sort; among other things, this
 * means that if you sort an already sorted array, it may not stay unchanged.
 * 
 * Since primitive types have no identity (there is no way to distinguish two
 * ints with the same value), this does not matter for them. But for reference
 * types, it could cause problems for some applications. Therefore, a stable
 * merge sort is used for those.
 * 
 * OTOH, a reason not to use the (guaranteed n*log(n)) merge sort for primitive
 * types might be that it requires making a clone of the array. For reference
 * types, where the referred objects usually take up far more memory than the
 * array of references, this generally does not matter. But for primitive types,
 * cloning the array outright doubles the memory usage.
 * 
 * 
 * 
 * @since 2014.03.17
 * @author whimsy
 * 
 */

public class SortingTest {

	public static void main(String[] args) {
		// new Test().run_Arrays_sort();
		new SortingTest().run_Collection_sort();
	}

	private void run_Collection_sort() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Integer[] ilist = { 1, 4, 5, 6, 32, 2, 1, 3, 5, 6 };

		for (int i = 0; i < ilist.length; ++i) {
			list.add(ilist[i]);
		}
		Collections.sort(list, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return -o1.compareTo(o2);
			}
		});
		for (Integer i : list) {
			System.out.print(i + " ");
		}
	}

	private void run_Arrays_sort() {
		Integer[] list = { 1, 4, 5, 6, 32, 2, 1, 3, 5, 6 };

		PrintWriter out = new PrintWriter(System.out);

		Arrays.sort(list, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 < o2)
					return 1;
				if (o1 > o2)
					return -1;
				return 0;
			}

		});

		for (int i = 0; i < list.length; ++i) {
			out.print(list[i] + " ");
		}
		out.flush();
		out.close();

	}
}
