import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class SetTest {
	public static void main(String[] args) {
		new SetTest().run_TreeSet();
	}

	private void run_TreeSet() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Integer[] ilist = { 1, 4, 5, 6, 32, 2, 1, 3, 5, 6 };

		for (int i = 0; i < ilist.length; ++i) {
			list.add(ilist[i]);
		}
		TreeSet<Integer> set = new TreeSet<Integer>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return -o1.compareTo(o2);
			}
		});

		set.addAll(list);

		System.out.println(set.comparator());
		for (Integer i : set.subSet(6, false, 3, true)) {
			System.out.println(i);
		}

	}

}
