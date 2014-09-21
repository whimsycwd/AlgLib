package com.whimsycwd.alglib.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.whimsycwd.alglib.BTree;

public class BTreeTest {

	public static void main(String[] args) throws FileNotFoundException {
		// generateData();
		// new BTreeTest().hashMapCalc();
		// new BTreeTest().BTreeCalc();
		// new BTreeTest().deleteTest();
		// new BTreeTest().hashMapDeleteTest();
		new BTreeTest().bigDataTest();
	}

	private void bigDataTest() throws FileNotFoundException {
		Scanner in = new Scanner(new File("ex-data.txt"));
//		Scanner in = new Scanner(new File("aaa"));
		BTree<Integer, String> bt = new BTree<Integer, String>();
		Long current = System.currentTimeMillis();
		int cnt = 0;
		int RecordNumber = 0;
		while (in.hasNext()) {
			int key = in.nextInt();
			String value = in.next();
			++RecordNumber;
			if (RecordNumber > 2000000)
				break;
			try {
				bt.put(key, value);
			} catch (Exception e) {
				if (e.getMessage().equals("Duplicate key")) {
					++cnt;
					bt.put(key, null);
				} else {
					throw e;
				}
			}
		}
		in.close();

		System.out.println("Deleted Entry Times : " + cnt);
		/*
		 * ArrayList<Integer> list = new ArrayList<Integer>(); for (Integer i :
		 * list){
		 * 
		 * }
		 */
		System.out.println("Load Time : "
				+ (System.currentTimeMillis() - current));
		current = System.currentTimeMillis();

		System.out.println("Tree Height : " + bt.height());
		System.out.println("Tree Size   : " + bt.size());

		PrintWriter out = new PrintWriter(new File("tmp.out"));

		ArrayList<Integer> list = bt.travase();
	
		System.out.println("Travse Time : "
				+ (System.currentTimeMillis() - current));
		current = System.currentTimeMillis();
		for (Integer i : list) {
			out.println(i);
		}
		System.out.println("Output Travase Time : "
				+ (System.currentTimeMillis() - current));
		current = System.currentTimeMillis();
		
		
		Collections.shuffle(list);
		for (Integer i : list) {
			bt.put(i, null);
		}

		System.out.println("Deleted all entry : "
				+ (System.currentTimeMillis() - current));
		System.out.println("Tree Height : " + bt.height());
		System.out.println("Tree Size   : " + bt.size());
		out.close();
		

	}

	private void hashMapDeleteTest() {
		HashMap<Integer, String> ha = new HashMap<Integer, String>();
		int total = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>(10000);
		Long oldTime = System.currentTimeMillis();

		for (int i = 0; i < 1000000; ++i) {
			arr.add(i);
		}

		Collections.shuffle(arr);

		for (int i = 0; i < arr.size(); ++i) {
			ha.put(i, "" + i);
		}
		System.out.println(ha.size());

		Long newTime = System.currentTimeMillis();
		System.out.println("Insert Time" + (newTime - oldTime));
		oldTime = newTime;

		Collections.shuffle(arr);

		for (int i = 0; i < arr.size(); ++i) {
			// System.out.println(arr.get(i));
			ha.put(arr.get(i), null);
		}

		System.out.println(ha.size());

		newTime = System.currentTimeMillis();
		System.out.println("Delete Time" + (newTime - oldTime));
		oldTime = newTime;
	}

	private void deleteTest() {
		BTree<Integer, String> bt = new BTree<Integer, String>();
		int total = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>(10000);
		Long oldTime = System.currentTimeMillis();

		for (int i = 0; i < 2000000; ++i) {
			arr.add(i);
		}

		Collections.shuffle(arr);

		for (int i = 0; i < arr.size(); ++i) {
			bt.put(i, "" + i);
		}
		System.out.println(bt.size());
		System.out.println(bt.height());

		Long newTime = System.currentTimeMillis();
		System.out.println("Insert Time" + (newTime - oldTime));
		oldTime = newTime;

		Collections.shuffle(arr);

		for (int i = 0; i < arr.size(); ++i) {
			// System.out.println(arr.get(i));
			bt.put(arr.get(i), null);
		}

		System.out.println(bt.size());
		System.out.println(bt.height());

		newTime = System.currentTimeMillis();
		System.out.println("Delete Time" + (newTime - oldTime));
		oldTime = newTime;
	}

	private void BTreeCalc() throws FileNotFoundException {
		BTree<Integer, String> bt = new BTree<Integer, String>();
		Scanner in = new Scanner(new File("testData/BTreeTest.txt"));
		PrintWriter out = new PrintWriter(new File(
				"testData/BTreeTest_bTreeResult.txt"));
		while (in.hasNext()) {
			int type = in.nextInt();
			int key = in.nextInt();
			if (type == 0) {
				if (bt.get(key) != null) {
					out.println("duplicate key");
					continue;
				}
				bt.put(key, "" + key);
			}
			if (type == 1) {
				if (bt.get(key) == null) {
					out.println("Key doesn't exist");
				} else
					out.println(bt.get(key));
			}
			if (type == 2) {
				if (bt.get(key) == null) {
					out.println("Key doesn't exist");
				} else
					bt.put(key, null);
			}

		}
		out.close();
		System.out.println(bt.height());
		System.out.println(bt);
	}

	private void hashMapCalc() throws FileNotFoundException {
		Map<Integer, String> mp = new HashMap<Integer, String>();

		Scanner in = new Scanner(new File("testData/BTreeTest.txt"));
		PrintWriter out = new PrintWriter(new File(
				"testData/BTreeTest_hashmapResult.txt"));
		while (in.hasNext()) {
			int type = in.nextInt();
			int key = in.nextInt();
			if (type == 0) {
				if (mp.get(key) != null) {
					out.println("duplicate key");
					continue;
				}
				mp.put(key, "" + key);
			}
			if (type == 1) {
				if (mp.get(key) == null) {
					out.println("Key doesn't exist");
				} else
					out.println(mp.get(key));
			}
			if (type == 2) {
				if (mp.get(key) == null) {
					out.println("Key doesn't exist");
				} else
					mp.remove(key);
			}

		}
		out.close();
		System.out.println(mp.size());

	}

	private static void generateData() throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new File("testData/BTreeTest.txt"));
		Random r = new Random();
		int total = 10000;
		int modulo = 10000;

		for (int i = 0; i < total; ++i) {

			out.println(0 + " " + r.nextInt(modulo));
		}
		for (int i = 0; i < total; ++i) {

			out.println(1 + " " + r.nextInt(modulo));
		}
		for (int i = 0; i < total; ++i) {

			out.println(2 + " " + r.nextInt(modulo));
		}
		out.close();
	}

}
