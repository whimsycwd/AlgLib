package codeforces;
import java.util.Scanner;

/**
 * @since 2014.03.13
 * @author whimsy
 * 
 */
public class P401B {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int x, k;
		x = in.nextInt();
		k = in.nextInt();
		boolean[] arr = new boolean[x];

		for (int i = 0; i < k; ++i) {
			int bj;
			bj = in.nextInt();
			if (bj == 1) {
				arr[in.nextInt()] = true;
				arr[in.nextInt()] = true;
			} else {
				arr[in.nextInt()] = true;
			}
		}

		int minAns = 0;
		int maxAns = 0;
		for (int i = 1; i < x; ++i) {
			if (!arr[i]) {
				++maxAns;
			}
		}
		for (int i = 1; i < x; ++i) {
			if (i + 1 < x && !arr[i] && !arr[i]) {
				++minAns;
				++i;
				continue;
			}
			if (!arr[i]) {
				++minAns;
			}
		}
		System.out.println(minAns+" "+maxAns);
	}

}
