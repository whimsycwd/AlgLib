
/**
 * 
 * Programming Assignment 6
 * 
 * @since 2014.4.27
 * @author whimsycwd
 * 
 */

import java.util.Arrays;

public class CircularSuffixArray {
	

	
	private Manber manber;
	
//	private char [] chars;
	 // circular suffix array of s
/*    
	private class Node implements Comparable<Node>{
		int offset;
		
		Node(int offset){
			this.offset = offset;
		}
		@Override
		public int compareTo(Node o) {
			
			for (int i = 0;i<len;++i){
				if (chars[i+offset] < chars[i+o.offset]){
					return -1;
				}
				if (chars[i+offset] > chars[i+o.offset]){
					return 1;
				}
			}
			
			return 0;
		}
	}
	
	Node [] subs;
	
	public CircularSuffixArray(String s){
    	
    	len = s.length();
    	s = s+s;
    	chars = s.toCharArray();
    	subs = new Node[len];
    	for (int i = 0;i<len;++i){
    		subs[i] = new Node(i);
    	}
    	
    	Arrays.sort(subs);
	}
	
    // length of s
    public int length() {
    	return len;
    }
    
    // returns index of ith sorted suffix
    public int index(int i) {
    	return subs[i].offset;
    }*/
    // unit test
	
	public CircularSuffixArray(String str){
		manber = new Manber(str);	
	}
	
    // length of s
    public int length() {
    	return manber.getSize();
    }
    
    
    // returns index of ith sorted suffix
    public int index(int i) {
    	if (i<0 || i>=length()) throw new IndexOutOfBoundsException("Out of Bound");
    	return manber.index(i);
    }
    
    public static void main(String[] args) {
		CircularSuffixArray a = new CircularSuffixArray("ABRACADABRA!");
		System.out.println(a.length());
		
		for (int i = 0;i<12;++i){
			System.out.println(a.index(i));
		}
	}
}

