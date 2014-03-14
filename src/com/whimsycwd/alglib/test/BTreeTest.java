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
		//generateData();
		//new BTreeTest().hashMapCalc();
		//new BTreeTest().BTreeCalc();
		//new BTreeTest().deleteTest();
		new BTreeTest().hashMapDeleteTest();
	}

	private void hashMapDeleteTest() {
		HashMap<Integer,String> ha =new HashMap<Integer,String>();
		int total =0;
		ArrayList<Integer> arr = new ArrayList<Integer>(10000);
		Long oldTime = System.currentTimeMillis();
		
		for (int i = 0;i<1000000;++i){
			arr.add(i);
		}
		
		Collections.shuffle(arr);
		
		for (int i = 0;i<arr.size();++i){
			ha.put(i, ""+i);
		}
		System.out.println(ha.size());

		
		Long newTime = System.currentTimeMillis();
		System.out.println("Insert Time"+ (newTime-oldTime) );
		oldTime = newTime;
		
		Collections.shuffle(arr);
		
		

		for (int i = 0;i<arr.size();++i){
			//System.out.println(arr.get(i));
			ha.put(arr.get(i), null);
		}


		
		System.out.println(ha.size());

		
		newTime = System.currentTimeMillis();
		System.out.println("Delete Time" + (newTime-oldTime) );
		oldTime = newTime;
	}

	private void deleteTest() {
		BTree<Integer,String> bt = new BTree<Integer,String>();
		int total =0;
		ArrayList<Integer> arr = new ArrayList<Integer>(10000);
		Long oldTime = System.currentTimeMillis();
		
		for (int i = 0;i<2000000;++i){
			arr.add(i);
		}
		
		Collections.shuffle(arr);
		
		for (int i = 0;i<arr.size();++i){
			bt.put(i, ""+i);
		}
		System.out.println(bt.size());
		System.out.println(bt.height());
		
		Long newTime = System.currentTimeMillis();
		System.out.println("Insert Time"+ (newTime-oldTime) );
		oldTime = newTime;
		
		Collections.shuffle(arr);
		
		

		for (int i = 0;i<arr.size();++i){
			//System.out.println(arr.get(i));
			bt.put(arr.get(i), null);
		}


		
		System.out.println(bt.size());
		System.out.println(bt.height());
		
		newTime = System.currentTimeMillis();
		System.out.println("Delete Time" + (newTime-oldTime) );
		oldTime = newTime;
	}

	private void BTreeCalc() throws FileNotFoundException {
		BTree<Integer,String> bt = new BTree<Integer,String>();
		Scanner in = new Scanner(new File("testData/BTreeTest.txt"));
		PrintWriter out = new PrintWriter(new File("testData/BTreeTest_bTreeResult.txt"));
		while (in.hasNext()){
			int type = in.nextInt();
			int key = in.nextInt();
			if (type == 0){
				if (bt.get(key)!=null){
					out.println("duplicate key");
					continue;
				}
				bt.put(key,""+key);
			}
			if (type == 1){
				if (bt.get(key) == null){
					out.println("Key doesn't exist");
				} else 
					out.println(bt.get(key));
			}
			if (type == 2){
				if (bt.get(key) == null){
					out.println("Key doesn't exist");
				} else 
					bt.put(key,null);
			}
			
		}
		out.close();
		System.out.println(bt.height());
		System.out.println(bt);
	}

	private  void hashMapCalc() throws FileNotFoundException {
		Map<Integer,String> mp = new HashMap<Integer,String>();

		Scanner in = new Scanner(new File("testData/BTreeTest.txt"));
		PrintWriter out = new PrintWriter(new File("testData/BTreeTest_hashmapResult.txt"));
		while (in.hasNext()){
			int type = in.nextInt();
			int key = in.nextInt();
			if (type == 0){
				if (mp.get(key)!=null){
					out.println("duplicate key");
					continue;
				}
				mp.put(key,""+key);
			}
			if (type == 1){
				if (mp.get(key) == null){
					out.println("Key doesn't exist");
				} else 
					out.println(mp.get(key));
			}
			if (type == 2){
				if (mp.get(key) == null){
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
		
		for (int i = 0;i<total;++i){
		
			out.println(0 + " " + r.nextInt(modulo) );
		}
		for (int i = 0;i<total;++i){
			
			out.println(1 + " " + r.nextInt(modulo) );
		}
		for (int i = 0;i<total;++i){
			
			out.println(2 + " " + r.nextInt(modulo) );
		}
		out.close();
	}

}
