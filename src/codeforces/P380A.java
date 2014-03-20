package codeforces;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * Generate the first number 100000. Will in turn handle the requests, if the
 * request gets to the point of adding one number, just print it. Otherwise see
 * what element will meet our and just print it from precalculated array.
 * 
 * One thing need to noticed.
 * 
 * Long x;
 * ArrayList<Long> arr = ...
 * arr.get(i) == x
 * 
 * In this scenario, it's actually compare two long Object reference same, not the value.
 * However,
 * long x;
 * ArrayList<Long> arr = ...
 * arr.get(i) == x
 * is equivalent to  
 * arr.get(i).longValue == x, Auto unboxing was conducted. 
 *
 * 
 * @since 2014.03.16
 * @author whimsy
 * 
 */
public class P380A {

	ArrayList<Object> ops = new ArrayList<Object>();
	ArrayList<Long> landmark = new ArrayList<Long>();

	int[] arr = new int[100001];

	public static void main(String[] args) {
		new P380A().solve();
	}

	static class Pair {
		int first, second;

		public Pair(int x, int y) {
			first = x;
			second = y;
		}
	}

	private void solve() {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		int n = in.nextInt();
		int op = 0;
		for (int i = 0; i < n; ++i) {
			op = in.nextInt();
			if (op == 1) {
				ops.add(in.nextInt());
			} else {
				ops.add(new Pair(in.nextInt(), in.nextInt()));
			}
		}

		preProcess();
		processLandMark();
		n = in.nextInt();
		for (int i = 0; i < n; ++i) {
			out.print(calcValue(in.nextLong()) + " ");
		}
		out.println();

		out.flush();
		out.close();
		in.close();
	}

	private int calcValue(long x) {

		int l = 0;
		int r = landmark.size() - 1;
		// the exit staus: l == r && x>=landmark[l];
		while (l < r) {
			int mid = (l + r) / 2 + 1;
			if (x >= landmark.get(mid))
				l = mid;
			else
				r = mid - 1;
		}

		if (x == landmark.get(l) && ops.get(l).getClass() == Integer.class) {
			return (int) ops.get(l);
		} else {
			if (x > landmark.get(l)) {

				Pair ee = (Pair) ops.get(l + 1);
				return arr[(int) ((x - landmark.get(l) - 1) % ee.first + 1)];

			} else {
				Pair ee = (Pair) ops.get(l);
				return arr[(int) ee.first];
			}
		}

	}

	private void processLandMark() {
		long total = 0;
		for (Object e : ops) {
			if (e.getClass() == Integer.class) {
				++total;
				landmark.add(total);
			} else {
				Pair ee = (Pair) e;
				total += (long) ee.first * ee.second;
				landmark.add(total);
			}
		}
	}

	/*
	 * because 1<=li<=10^5 so if we preprocess the first 10^5 element, we will
	 * search only once instead of cascade search.
	 */
	private void preProcess() {
		int total = 0;
		for (Object e : ops) {
			if (total == 100000)
				return;
			if (e.getClass() == Integer.class) {
				arr[++total] = (int) e;
			} else {
				Pair ee = (Pair) e;

				for (int i = 0; i < ee.second; ++i) {
					for (int j = 0; j < ee.first; ++j) {
						arr[++total] = arr[j + 1];
						if (total == 100000)
							return;
					}
				}
			}
		}

	}

}
