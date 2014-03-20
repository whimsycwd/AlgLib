package com.whimsycwd.alglib;

import java.util.ArrayList;

/*************************************************************************
 *
 *  Limitations
 *  -----------
 *   -  Assumes M 6's mutiplication and M >= 6
 *   -  should b be an array of children or list (it would help with
 *      casting to make it a list)
 *
 *************************************************************************/

/**
 * Extension for fully BTree feature. Add delete(). 10^6 insert cost 2125ms 10^6
 * delete cost 2057ms 2*10^6 insert cost 4722ms 2*10*6 delete cost 4400ms
 * 
 * Test by com.whimsycwd.alglib.test.BTreeTest.java It simpily test the
 * efficiency. and small scale dataset correctness.
 * 
 * Large scale dataSet correctness need furthur Test.
 * 
 * @author whimsy
 * @since 2014.3.12
 * 
 * @param <Key>
 * @param <Value>
 */

public class BTree<Key extends Comparable<Key>, Value> {
	private static final int M = 6; // max children per B-tree node = M-1

	private Node root; // root of the B-tree
	private int HT; // height of the B-tree
	private int N; // number of key-value pairs in the B-tree

	// helper B-tree node data type
	private static final class Node {
		private int m; // number of children
		private Entry[] children = new Entry[M]; // the array of children

		private Node(int k) {
			m = k;
		} // create a node with k children
	}

	// internal nodes: only use key and next
	// external nodes: only use key and value
	public static class Entry {
		private Comparable key;
		private Object value;
		private Node next; // helper field to iterate over array entries

		public Entry(Comparable key, Object value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		@Override
		public String toString() {
			return "" + key;
		}
	}

	// constructor
	public BTree() {
		root = new Node(0);
	}

	/**
	 * @return return number of key-value pairs in the B-tree
	 */
	public int size() {
		return N;
	}

	/**
	 * 
	 * @return return height of B-tree
	 */
	public int height() {
		return HT;
	}

	/**
	 * @param key
	 * @return search for given key, return associated value; return null if no
	 *         such key
	 */
	//
	public Value get(Key key) {
		return search(root, key, HT);
	}

	/**
	 * auxilury function for get
	 * 
	 * @param x
	 * @param key
	 * @param ht
	 * @return
	 */
	private Value search(Node x, Key key, int ht) {
		Entry[] children = x.children;

		// external node
		if (ht == 0) {
			for (int j = 0; j < x.m; j++) {
				if (eq(key, children[j].key))
					return (Value) children[j].value;
			}
		}

		// internal node
		else {
			for (int j = 0; j < x.m; j++) {
				if (j + 1 == x.m || less(key, children[j + 1].key))
					return search(children[j].next, key, ht - 1);
			}
		}
		return null;
	}

	/**
	 * insert key-value pair insert key must not exists.
	 * 
	 * (key,null) mean delete A deletion, the key must have existed.
	 * 
	 * @param key
	 * @param value
	 */

	public void put(Key key, Value value) {

		if (get(key) != null && value != null)
			throw new IllegalArgumentException("Duplicate key");

		if (value == null) {
			if (get(key) == null)
				throw new IllegalArgumentException(
						"The delete key doesn't exist.");

			delete(root, key, HT, null);
			--N;
			return;
		}

		Node u = insert(root, key, value, HT);
		N++;
		if (u == null)
			return;

		// need to split root
		Node t = new Node(2);
		t.children[0] = new Entry(root.children[0].key, null, root);
		t.children[1] = new Entry(u.children[0].key, null, u);
		root = t;
		HT++;
	}

	/**
	 * implement delete
	 * 
	 */
	private Node delete(Node h, Key key, int ht, Node parent) {
		int j = 0;

		// leaf node

		if (ht == 0) {
			for (j = 0; j < h.m; j++) {
				if (eq(key, h.children[j].key) == true) {
					break;
				}
			}
		}
		// internal node
		else {
			for (int k = 0; k < h.m; k++) {
				if (k + 1 == h.m || less(key, h.children[k + 1].key)) {
					Node u = delete(h.children[k].next, key, ht - 1, h);
					if (u == null)
						return null;

					for (j = 0; j < h.m; ++j) {
						if (u == h.children[j].next)
							break;
					}

					break;
				}
			}
		}

		for (int i = j; i < h.m - 1; ++i) {
			h.children[i] = h.children[i + 1];
		}
		--h.m;

		if (parent == null) {
			// shrink the BTree and need to confirm that this isn't the root.
			if (h.m == 1 && HT != 0) {
				root = root.children[0].next;
				--HT;
			} else
			// the BTree is empty
			if (h.m == 0) {
				root = null;

				HT = 0;
			}
			return null;
		}
		if (h.m < M / 3) {
			int no = 0;
			for (int i = 0; i < parent.m; ++i) {
				if (h == parent.children[i].next) {
					no = i;
					break;
				}
			}
			Node sibling = null;
			// have right sibling, then borrow or merge current with right one.
			if (no + 1 < parent.m) {
				sibling = parent.children[no + 1].next;
				// megrge two small node
				if (sibling.m == M / 3) {
					for (int i = 0; i < sibling.m; ++i) {
						h.children[h.m++] = sibling.children[i];
					}
					sibling.m = 0;
					return sibling;
				}
				// borrow one
				else {
					h.children[h.m++] = sibling.children[0];
					for (int i = 0; i < sibling.m - 1; ++i) {
						sibling.children[i] = sibling.children[i + 1];
					}
					parent.children[no + 1].key = sibling.children[0].key; // update
																			// key
																			// value.
					sibling.m--;

				}
			}
			// have left sibling
			// different from right sibling, we should delete current node
			// instend of sibling.
			else if (no > 0) {
				sibling = parent.children[no - 1].next;

				// megrge two small node
				if (sibling.m == M / 3) {
					for (int i = 0; i < h.m; ++i) {
						sibling.children[sibling.m++] = h.children[i];
					}
					h.m = 0;
					return h;
				}
				// borrow one
				else {
					h.m++;
					for (int i = 0; i < h.m - 1; ++i) {
						h.children[i + 1] = h.children[i];
					}
					h.children[0] = sibling.children[--sibling.m];
					parent.children[no].key = h.children[0].key;

				}
			}

		}

		return null;

	}

	private Node insert(Node h, Key key, Value value, int ht) {
		int j;
		Entry t = new Entry(key, value, null);

		// external node
		if (ht == 0) {
			for (j = 0; j < h.m; j++) {
				if (less(key, h.children[j].key))
					break;
			}
		}

		// internal node
		else {
			for (j = 0; j < h.m; j++) {
				if ((j + 1 == h.m) || less(key, h.children[j + 1].key)) {
					Node u = insert(h.children[j++].next, key, value, ht - 1);
					if (u == null)
						return null;
					t.key = u.children[0].key;
					t.next = u;
					break;
				}
			}
		}

		for (int i = h.m; i > j; i--)
			h.children[i] = h.children[i - 1];
		h.children[j] = t;
		h.m++;
		if (h.m < M)
			return null;
		else
			return split(h);
	}

	// split node in half
	private Node split(Node h) {
		Node t = new Node(M / 2);
		h.m = M / 2;
		for (int j = 0; j < M / 2; j++)
			t.children[j] = h.children[M / 2 + j];
		return t;
	}

	// for debugging
	public String toString() {
		return toString(root, HT, "") + "\n";
	}

	private String toString(Node h, int ht, String indent) {
		String s = "";
		Entry[] children = h.children;

		if (ht == 0) {
			for (int j = 0; j < h.m; j++) {
				s += indent + children[j].key + " " + children[j].value + "\n";
			}
		} else {
			for (int j = 0; j < h.m; j++) {
				if (j > 0)
					s += indent + "(" + children[j].key + ")\n";
				s += toString(children[j].next, ht - 1, indent + "     ");
			}
		}
		return s;
	}

	// comparison functions - make Comparable instead of Key to avoid casts
	private boolean less(Comparable k1, Comparable k2) {
		return k1.compareTo(k2) < 0;
	}

	private boolean eq(Comparable k1, Comparable k2) {
		return k1.compareTo(k2) == 0;
	}

	/*************************************************************************
	 * test client
	 *************************************************************************/
	public static void main(String[] args) {
		BTree<Integer, String> st = new BTree<Integer, String>();
		/*
		 * for (int i = 0; i < 10; ++i) { st.put(i, "" + i); } st.put(7, null);
		 * st.put(9, null); st.put(6, null); st.put(8, null); st.put(3, null);
		 * st.put(4, null); st.put(5, null); System.out.println(st); /*
		 * st.put(1, "one"); st.put(2, "twt"); st.put(3, "three"); st.put(0,
		 * "zero"); st.put(-1, "-1"); StdOut.println(st); st.put(-2, "-2");
		 * StdOut.println(st); StdOut.println(); st.put(-2, "-1");
		 * 
		 * StdOut.println(st); StdOut.println(); st.put(-4, "-2"); // st.put(-3,
		 * "-1"); // st.put(-4, "-2"); StdOut.println(st);
		 * StdOut.print(st.height());
		 * 
		 * /* BTree<String, String> st = new BTree<String, String>(); //
		 * st.put("www.cs.princeton.edu", "128.112.136.12");
		 * st.put("www.cs.princeton.edu", "128.112.136.11");
		 * st.put("www.princeton.edu", "128.112.128.15"); st.put("www.yale.edu",
		 * "130.132.143.21"); st.put("www.simpsons.com", "209.052.165.60");
		 * st.put("www.apple.com", "17.112.152.32"); st.put("www.amazon.com",
		 * "207.171.182.16"); st.put("www.ebay.com", "66.135.192.87");
		 * st.put("www.cnn.com", "64.236.16.20"); st.put("www.google.com",
		 * "216.239.41.99"); st.put("www.nytimes.com", "199.239.136.200");
		 * st.put("www.microsoft.com", "207.126.99.140"); st.put("www.dell.com",
		 * "143.166.224.230"); st.put("www.slashdot.org", "66.35.250.151");
		 * st.put("www.espn.com", "199.181.135.201"); st.put("www.weather.com",
		 * "63.111.66.11"); st.put("www.yahoo.com", "216.109.118.65");
		 * 
		 * 
		 * StdOut.println("cs.princeton.edu:  " +
		 * st.get("www.cs.princeton.edu")); StdOut.println("hardvardsucks.com: "
		 * + st.get("www.harvardsucks.com"));
		 * StdOut.println("simpsons.com:      " + st.get("www.simpsons.com"));
		 * StdOut.println("apple.com:         " + st.get("www.apple.com"));
		 * StdOut.println("ebay.com:          " + st.get("www.ebay.com"));
		 * StdOut.println("dell.com:          " + st.get("www.dell.com"));
		 * StdOut.println();
		 * 
		 * StdOut.println("size:    " + st.size()); StdOut.println("height:  " +
		 * st.height()); StdOut.println(st); StdOut.println();
		 */
	}

	private void travase(Node h, int ht, ArrayList<Key> ret) {
		if (ht == 0) {
			for (int j = 0; j < h.m; j++) {
				ret.add((Key) h.children[j].key);
			}
		}

		// internal node
		else {
			for (int j = 0; j < h.m; j++) {

				travase(h.children[j].next, ht - 1, ret);

			}
		}
	}

	public ArrayList<Key> travase() {
		ArrayList<Key> ret = new ArrayList<Key>();
		travase(root, HT, ret);
		return ret;
	}

}

/*************************************************************************
 * Copyright 2002-2012, Robert Sedgewick and Kevin Wayne.
 * 
 * This file is part of algs4-package.jar, which accompanies the textbook
 * 
 * Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne, Addison-Wesley
 * Professional, 2011, ISBN 0-321-57351-X. http://algs4.cs.princeton.edu
 * 
 * 
 * algs4-package.jar is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * algs4-package.jar is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * algs4-package.jar. If not, see http://www.gnu.org/licenses.
 *************************************************************************/

