package codeforces;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * TLE on test 13
 * Can't not figure out why?
 * 
 * The reason the outputstream doesn't close.
 * PrintWriter out = new PrintWriter(System.out);
 * out.fulsh();
 * out.close();
 * 
 * @since 2014.03.16
 * @author whimsy
 *
 */
public class P380C {
	static class Tree {
		Node root;
		String data;

		public Tree(String data2) {
			data = data2;
			root = buildTree(1, data.length());
		}

		private Node buildTree(int l, int r) {
			Node ret = new Node();
			ret.l = l;
			ret.r = r;
			if (l == r) {
				ret.lv += data.charAt(l - 1) == '(' ? 1 : 0;
				ret.rv += data.charAt(l - 1) == ')' ? 1 : 0;
			} else {
				ret.left = buildTree(l, ret.mid());
				ret.right = buildTree(ret.mid() + 1, r);
				int t = Math.min(ret.left.lv, ret.right.rv);
				ret.v = ret.left.v + ret.right.v + t;
				ret.lv = ret.left.lv + ret.right.lv - t;
				ret.rv = ret.left.rv + ret.right.rv - t;
			}

			return ret;
		}

		public int retrieve(int l, int r) {
			return retrieve(root, l, r).v;
		}

		private Entry retrieve(Node x, int l, int r) {
			if (l <= x.l && r >= x.r)
				return new Entry(x);
			if (r <= x.mid())
				return retrieve(x.left, l, r);
			if (l > x.mid())
				return retrieve(x.right, l, r);
			Entry le = retrieve(x.left, l, x.mid());
			Entry re = retrieve(x.right, x.mid() + 1, r);
			int t = Math.min(le.lv, re.rv);
			return new Entry(le.v + re.v + t, le.lv + re.lv - t, re.rv + le.rv
					- t);
		}
	}

	static class Entry {
		int v, lv, rv;

		public Entry(Node x) {
			this.v = x.v;
			this.lv = x.lv;
			this.rv = x.rv;
		}

		public Entry(int v, int lv, int rv) {
			this.v = v;
			this.lv = lv;
			this.rv = rv;
		}
		@Override
		public String toString() {

			return v+" "+lv+" "+rv;
		}

	}

	static class Node {
		int v, lv, rv;
		int l, r;
		Node left, right;

		public Node() {
			v = lv = rv = l = r = 0;
			left = right = null;
		}

		public int mid() {
			return (l + r) / 2;
		}
		@Override
		public String toString() {
			return v+" "+lv+" "+rv+" | "+l + " "+r;
		}
	}

	private void solve() {
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		String data = in.nextLine();
		
		int n = in.parseInt1D(1)[0];
		Tree tree = new Tree(data);
		for (int i = 0; i < n; ++i) {
			int [] tmp = in.parseInt1D(2);
			int l = tmp[0];
			int r = tmp[1];
			out.println(tree.retrieve(l, r)*2);
		}
		out.flush();
		out.close();
	}

	public static void main(String[] args) {
		new P380C().solve();
	}


	static class InputReader {
		private BufferedReader br;
		private StringTokenizer st;

		public InputReader(InputStream in) {
			br = new BufferedReader(new InputStreamReader(in));
			
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
		public int[] parseInt1D(int n) {
			readLine();
			int r[] = new int[n];
			for (int i = 0; i < n; i++) {
				r[i] = nextInt();
			}
			return r;
		}
		public String nextLine(){
			readLine();
			return st.nextToken();
			
		}

	}
}
