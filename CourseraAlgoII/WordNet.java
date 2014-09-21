import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/**
 * 
 * Coursera AlgII PA1
 * 
 * 
 * @since 2014.03.22
 * @author whimsy
 * 
 */

public final class WordNet {

	Digraph G;
	// ArrayList<Node> store = new ArrayList<Node>();
	TreeMap<String, ArrayList<Integer>> map = new TreeMap<String, ArrayList<Integer>>();
	SAP sap;
	ArrayList<String> store = new ArrayList<String>();

	/**
	 * constructor takes the name of the two input files
	 */
	public WordNet(String synsets, String hypernyms) {
		In in = new In(synsets);
		int cnt = 0;
		while (!in.isEmpty()) {
			String s = in.readLine();
			String[] strs = s.split(",");
			store.add(strs[1]);
			String[] nourns = strs[1].split(" ");

			for (int i = 0; i < nourns.length; ++i) {
				ArrayList<Integer> t = map.get(nourns[i]);
				if (t == null) {
					t = new ArrayList<Integer>();
					t.add(cnt);
					map.put(nourns[i], t);
				} else
					t.add(cnt);
			}

			++cnt;
		}
		in.close();

		G = new Digraph(cnt);
		int root = -1;
		in = new In(hypernyms);
		while (!in.isEmpty()) {
			String s = in.readLine();
			String[] strs = s.split(",");
			if (strs.length == 1) {
				if (root != -1)
					throw new IllegalArgumentException("Need Single Root");
				root = Integer.parseInt(strs[0]);
			}
			for (int i = 1; i < strs.length; ++i) {
				G.addEdge(Integer.parseInt(strs[0]), Integer.parseInt(strs[i]));
			}
		}

		if (root == -1)
			throw new IllegalArgumentException("At Least One Root");
		Digraph reverse = G.reverse();

		if (!testCycle(reverse)) {
			throw new IllegalArgumentException("Graph has a cycle");
		}

		if (!testConnect(G.reverse(), root)) {
			throw new IllegalArgumentException("Grpah doesn't connect together");
		}
		sap = new SAP(G);
	}

	private boolean testCycle(Digraph G) {
		DirectedCycle cycle = new DirectedCycle(G);
		return !cycle.hasCycle();
	}

	private boolean testConnect(Digraph G, int root) {

		DirectedDFS dfs = new DirectedDFS(G, root);

		for (int i = 0; i < G.V(); ++i) {
			if (!dfs.marked(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * the set of nouns (no duplicates), returned as an Iterable
	 * 
	 * @return
	 */
	public Iterable<String> nouns() {
		return map.keySet();
	}

	/**
	 * 
	 * is the word a WordNet noun?
	 * 
	 * @param word
	 * @return
	 */
	public boolean isNoun(String word) {
		return map.get(word) != null;
	}

	/**
	 * distance between nounA and nounB
	 * 
	 * @param nounA
	 * @param nounB
	 * @return
	 */
	public int distance(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB)) {
			throw new IllegalArgumentException("Both input should be valid");
		}
		return sap.length(map.get(nounA), map.get(nounB));
	}

	/**
	 * a synset (second filed of synsets.txt) that i the common acestor of nounA
	 * and nounB in a shourtest ancestral path
	 * 
	 * @param nounA
	 * @param nounB
	 * @return
	 */
	public String sap(String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB)) {
			throw new IllegalArgumentException("Both input should be valid");
		}
		return store.get(sap.ancestor(map.get(nounA), map.get(nounB)));
	}

	public static void main(String[] args) {
		WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
		System.out.println(wordnet.isNoun("horse"));
		System.out.println(wordnet.map.size());
	}
}
