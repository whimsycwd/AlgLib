import java.util.Scanner;

public class P386B {
	public static void main(String[] args) {
		new P386B().solve();
	}

	private void solve() {
		Scanner in = new Scanner(System.in);
		int[] count = new int[1001];
		int n = in.nextInt();
		for (int i = 0; i < n; ++i) {
			count[in.nextInt()]++;
		}
		for (int i = 1; i <= 1000; ++i) {
			count[i] += count[i - 1];
		}
		int ans = 0; 
		int interval = in.nextInt();
		if (interval < 1000)
			++interval;
		for (int i = 0; i <= 1000 - interval; ++i) {
			ans = Math.max(ans, count[i + interval] - count[i]);
		}
		System.out.println(ans);

	}

}
