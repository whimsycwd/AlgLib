package codeforces;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 
 * unknown reasion TLE on test5.
 * A lot of peopeo run into similar obstacle. in java.
 * 
 * TLE's reasion is the system.out need to close!!
 * 
 * @since 2014.03.13
 * @author whimsy
 * 
 */

public class P379C {

	static class Entry implements Comparable<Entry> {
		int a;
		int i;

		public Entry(int a, int i) {
			this.a = a;
			this.i = i;
		}

		@Override
		public int compareTo(Entry that) {
			if (this.a < that.a)
				return -1;
			if (this.a > that.a)
				return 1;
			return 0;
		}
	}

	static class InputReader {
		private BufferedReader br;
		private StringTokenizer st;

		public InputReader(InputStream in) {
			br = new BufferedReader(new InputStreamReader(in));
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException ignored) {

			}
		}

		public void readLine() {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException e) {
				return;
			}
		}

		public int nextInt() {
			return Integer.parseInt(st.nextToken());
		}

		/**
		 * Parse 1D array from current StringTokenizer
		 */
		public int[] parseInt1D(int n) {
			readLine();
			int r[] = new int[n];
			for (int i = 0; i < n; i++) {
				r[i] = nextInt();
			}
			return r;
		}
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		new P379C().solve();

	}

	private void solve() throws NumberFormatException, IOException {
		//ArrayList<Entry> list = new ArrayList<Entry>();
		/*
		 * Scanner in = new Scanner(System.in); int n = in.nextInt(); for (int i
		 * = 0;i<n;++i){ list.add(new Entry(in.nextInt(),i)); }
		 */
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = in.nextInt();
		int [] values = in.parseInt1D(n);
		/*
		for (int i = 0; i < n; ++i) {
			list.add(new Entry(values[i], i));
		}
		*/
		Entry[] list = new Entry[n];
		for (int i = 0;i<n;++i){
			list[i] = new Entry(values[i],i);
		}
		
		Arrays.sort(list);
		int[] res = new int[n];
		int t = 0;
		for (Entry e : list) {
			t = Math.max(e.a, t);
			res[e.i] = t;
			t++;
		}
		for (int i = 0; i < n; ++i) {
			out.print(res[i] + ((i==n-1) ? "" : " "));
		}
		out.println();
		out.flush();
		out.close();
	}

}
