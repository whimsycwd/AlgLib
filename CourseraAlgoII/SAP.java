import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/**
 * 
 * Coursera AlgII PA1
 * 
 * 
 * All methods should throw a java.lang.IndexOutOfBoundsException if one (or
 * more) of the input arguments is not between 0 and G.V() - 1. You may assume
 * that the iterable arguments contain at least one integer. All methods (and
 * the constructor) should take time at most proportional to E + V in the worst
 * case, where E and V are the number of edges and vertices in the digraph,
 * respectively. Your data type should use space proportional to E + V.
 * 
 * Can it take time at most proportional to E+V???
 * 
 * @since 2014.03.22
 * @author whimsy
 * 
 */

public final class SAP {
	final Digraph G;

	/**
	 * constructor takes a digraph(not a necessarily a DAG)
	 * 
	 * @param G
	 */
	public SAP(final Digraph G) {
		this.G = G;
	}

	/**
	 * length of shortest ancestral path between v and w; -1 if no such path
	 * 
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(int v, int w) {
		if (v < 0 || v > G.V() || w < 0 || w > G.V())
			throw new IndexOutOfBoundsException();
		ArrayList<Integer> vList = new ArrayList<Integer>();
		ArrayList<Integer> wList = new ArrayList<Integer>();
		vList.add(v);
		wList.add(w);

		return length(vList, wList);
	}

	/**
	 * a common ancestor of v and w that participates in a shortest ancestral
	 * path; -1 if no such path
	 * 
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(int v, int w) {
		if (v < 0 || v > G.V() || w < 0 || w > G.V())
			throw new IndexOutOfBoundsException();
		ArrayList<Integer> vList = new ArrayList<Integer>();
		ArrayList<Integer> wList = new ArrayList<Integer>();
		vList.add(v);
		wList.add(w);
		return ancestor(vList, wList);
	}

	/**
	 * 
	 * a common ancestor that participates in shortest ancestral path; -1 if no
	 * such path
	 * 
	 * @param v
	 * @param v
	 * @return
	 */
	public int length(Iterable<Integer> vList, Iterable<Integer> wList) {
		for (Integer v : vList) {
			if (v < 0 || v > G.V())
				throw new IndexOutOfBoundsException();
		}
		for (Integer w : wList) {
			if (w < 0 || w > G.V())
				throw new IndexOutOfBoundsException();
		}
		return dfs(vList, wList, 1);
	}

	/**
	 * a common acestor that participates in shortest ancestral path; -1 if no
	 * such path
	 * 
	 * @param v
	 * @param w
	 * @return
	 */

	public int ancestor(Iterable<Integer> vList, Iterable<Integer> wList) {
		for (Integer v : vList) {
			if (v < 0 || v > G.V())
				throw new IndexOutOfBoundsException();
		}
		for (Integer w : wList) {
			if (w < 0 || w > G.V())
				throw new IndexOutOfBoundsException();
		}
		return dfs(vList, wList, 2);
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}

	/**
	 * utily function type = 1 return shortest path length type = 2 return
	 * ancestor
	 * 
	 * @param vList
	 * @param wList
	 * @param type
	 * @return
	 */
	private int dfs(Iterable<Integer> vList, Iterable<Integer> wList, int type) {

		int[] vLength = new int[G.V()];
		int[] wLength = new int[G.V()];
		for (int i = 0; i < G.V(); ++i) {
			vLength[i] = Integer.MAX_VALUE;
			wLength[i] = Integer.MAX_VALUE;

		}

		Queue<Integer> queue = new LinkedList<Integer>();
		for (Integer v : vList) {
			queue.add(v);
			vLength[v] = 0;
		}
		Integer front = null;
		while ((front = queue.poll()) != null) {
			for (Integer adj : G.adj(front)) {
				if (vLength[front] + 1 < vLength[adj]) {
					queue.add(adj);
					vLength[adj] = vLength[front] + 1;

				}
			}
		}

		for (Integer w : wList) {
			queue.add(w);
			wLength[w] = 0;
		}
		while ((front = queue.poll()) != null) {
			for (Integer adj : G.adj(front)) {
				if (wLength[front] + 1 < wLength[adj]) {
					queue.add(adj);
					wLength[adj] = wLength[front] + 1;

				}
			}
		}
		int length = Integer.MAX_VALUE;
		int ancestor = -1;
		for (int i = 0; i < G.V(); ++i) {
			if (vLength[i] < Integer.MAX_VALUE
					&& wLength[i] < Integer.MAX_VALUE
					&& vLength[i] + wLength[i] < length) {
				length = vLength[i] + wLength[i];
				ancestor = i;
			}
		}
		if (length == Integer.MAX_VALUE)
			length = -1;
		if (type == 1)
			return length;
		else
			return ancestor;

	}
}
