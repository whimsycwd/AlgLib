package codeforces;


import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @since 2014.03.13
 * @author whimsy
 * 
 */

public class P401A {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = in.nextInt();
		int x = in.nextInt();
		int sum = 0;
		for (int i = 0; i < n; ++i) {
			sum += in.nextInt();
		}
		int tmp = 0;
		out.println(( Math.abs(sum) / x) + (Math.abs(sum) % x == 0 ? 0 : 1));
		out.close();
		in.close();
	}

}
