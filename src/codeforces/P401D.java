package codeforces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.princeton.cs.algs4.Arbitrage;

/**
 * 
 * @since 2014.03.13
 * @author whimsy
 * 
 */

public class P401D {
	int n;
	int m; // modulo

	
	
	public void solve() {
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		m = in.nextInt();
		
	}

	public int[] status(int x) {
		int[] ret = new int[10];
		while (x > 0) {
			ret[x % 10]++;
			x /= 10;
		}
		return ret;
	}

	public static void main(String[] args) {
		new P401D().solve();

	}

}
