import java.util.Scanner;


public class P387B {

	public static void main(String[] args){
		new P387B().solve();
	}
	
	private void solve(){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		int [] a = new int[n];
		int [] b = new int[m];
		for (int i = 0;i<n;++i) 
			a[i] = in.nextInt();
		for (int i = 0;i<m;++i)
			b[i] = in.nextInt();
	
		int ap = 0;
		int bp = 0;
		int match = 0;
		
		while (ap<n && bp<m){
			if (a[ap]<=b[bp]){
				++match;
				++ap;
				++bp;
			} else {
				++bp;
			}
		}
		System.out.println(n-match);
	}
}
