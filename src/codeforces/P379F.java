package codeforces;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Try one: TLE on test 11 change log2() Math.log()/Math.log bla..bla.. to
 * iterate one.
 * 
 * Try two: MLE on test 12: WTH? Correctness is ok. Time and Memory Performance
 * neither is good.
 * 
 * Need furthur optimize.
 * 
 * @since 2014.03.16
 * @author whimsy
 * 
 */

public class P379F {

	ArrayList<Node> nodes = new ArrayList<Node>();

	static class Node {
		ArrayList<Integer> children = new ArrayList<Integer>();

		public Node(int... ints) {
			for (int i = 0; i < ints.length; ++i) {
				children.add(ints[i]);
			}
		}

	}

	static class LCA_DoubleSearch {

		int tFlag = 0;
		int[] tin;
		int[] tout;
		int[][] ancestor;
		int[] level;
		int N;
		ArrayList<Node> nodes;

		public LCA_DoubleSearch(int n, ArrayList<Node> nodes) {
			tin = new int[n];
			tout = new int[n];
			ancestor = new int[n][log2(n)];
			level = new int[n];
			this.N = n;
			this.nodes = nodes;
			dfs(0, 0, 0);
		}

		private void dfs(int x, int px, int height) {
			tin[x] = tFlag++;
			ancestor[x][0] = px;
			level[x] = height;

			for (int i = 1; i < log2(N); ++i) {
				ancestor[x][i] = ancestor[ancestor[x][i - 1]][i - 1];
			}

			for (int child : nodes.get(x).children) {
				dfs(child, x, height + 1);
			}
			tout[x] = tFlag++;

		}

		private int log2(int n) {
			// return (int) Math.ceil(Math.log(n) / Math.log(2.0));
			int ret = 0;
			int tmp = 1;
			while (tmp <= n) {
				++ret;
				tmp *= 2;
			}
			return ret - 1;
		}

		private boolean ancestor(int x, int y) {
			return tin[x] <= tin[y] && tout[x] >= tout[y];
		}

		int LCA(int x, int y) {
			if (ancestor(x, y))
				return x;
			if (ancestor(y, x))
				return y;

			for (int i = log2(N) - 1; i >= 0; i--) {
				if (!ancestor(ancestor[x][i], y))
					x = ancestor[x][i];
			}
			return ancestor[x][0];
		}

		public int distance(int x, int y) {
			int lca = LCA(x, y);
			return level[x] + level[y] - 2 * level[lca];
		}

	}

	void solve() {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		ArrayList<Integer> store = new ArrayList<Integer>();

		int n = in.nextInt();
		int total = 4 + n * 2;
		for (int i = 0; i < 4 + n * 2; ++i)
			nodes.add(new Node());
		nodes.set(0, new Node(1, 2, 3));

		for (int i = 0; i < n; ++i) {
			store.add(in.nextInt() - 1);
		}
		n = 4;
		for (int x : store) {
			nodes.set(x, new Node(n, n + 1));
			n += 2;
		}
		LCA_DoubleSearch lcaCore = new LCA_DoubleSearch(n, nodes);

		int either = 1;
		int other = 2;
		int length = 2;
		for (int x = 4; x < total; ++x) {
			int dis = lcaCore.distance(either, x);

			if (dis > length) {
				other = x;
				length = dis;

			} else {
				dis = lcaCore.distance(other, x);
				if (dis > length) {
					either = x;
					length = dis;
				}

			}
			if (x % 2 == 1)
				out.println(length);

		}
		out.flush();
		out.close();
		in.close();
	}

	public static void main(String[] args) {
		new P379F().solve();
		// testLCA();
	}

	/*
	 * simply testcase for LCA_DoubleSearch
	 */
	private static void testLCA() {
		P379F p = new P379F();
		p.nodes.add(new Node(1, 2)); // 0
		p.nodes.add(new Node()); // 1
		p.nodes.add(new Node(3, 4)); // 2
		p.nodes.add(new Node(5, 6)); // 3
		p.nodes.add(new Node(7)); // 4
		p.nodes.add(new Node()); // 5
		p.nodes.add(new Node()); // 6
		p.nodes.add(new Node()); // 7

		LCA_DoubleSearch lca = new LCA_DoubleSearch(8, p.nodes);
		System.out.println(lca.LCA(1, 2));
		System.out.println(lca.LCA(3, 2));
		System.out.println(lca.LCA(7, 6));
		System.out.println(lca.LCA(5, 6));
		System.out.println(lca.distance(5, 6));

	}
}
