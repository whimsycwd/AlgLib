import java.util.HashSet;
import java.util.Set;


/**
 * 
 * Program Assignment Week 5
 * 
 * 90/100 Need to use TrieSet instead to pass timing test.
 * fail 2 timing test.  
 * need touse Space to trade Time.
 * 
 * 
 * 
 * @since 2014.4.26
 * @author whimsycwd
 * 
 */

public class BoggleSolver {

	private MyTST<Integer> trieDict = new MyTST<Integer>();
	private Set<String> hashSet = null;
	private BoggleBoard board;

	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A through Z.)
	public BoggleSolver(String[] dictionary) {
		for (String e : dictionary) {
			trieDict.put(e, e.length());
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an
	// Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		this.board = board;
		Queue<String> que = new Queue<String>();
		hashSet = new HashSet<String>();
		boolean[][] aux = new boolean[board.rows()][board.cols()];
		for (int i = 0; i < board.rows(); ++i) {
			for (int j = 0; j < board.cols(); ++j) {
				dfs(i, j, "", que, aux);
			}
		}
		return que;
	}

	private void dfs(int i, int j, String string, Queue<String> que,
			boolean[][] aux) {
		aux[i][j] = true;
		char c = board.getLetter(i, j);
		if (c == 'Q') {
			string += "QU";
		} else
			string += c;

		if (string.length() >= 3 && !hashSet.contains(string)
				&& trieDict.contains(string)) {

			hashSet.add(string);
			que.enqueue(string);
		}
		if (trieDict.hasPrefixMatch(string)) {
			for (int dx = -1; dx <= 1; ++dx) {
				for (int dy = -1; dy <= 1; ++dy) {
					if (i + dx >= 0 && i + dx < board.rows() && j + dy >= 0
							&& j + dy < board.cols() && !aux[i + dx][j + dy]) {
						dfs(i + dx, j + dy, string, que, aux);
					}
				}
			}
		}
		aux[i][j] = false;

	}

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through
	// Z.)
	public int scoreOf(String word) {
		if (!trieDict.contains(word))
			return 0;
		int len = word.length();
		if (len < 3)
			return 0;
		else if (len < 5)
			return 1;
		else if (len < 6)
			return 2;
		else if (len < 7)
			return 3;
		else if (len < 8)
			return 5;
		return 11;

	}

	public static void main(String[] args) {
//		In in = new In("week5_dictionary.txt");
		In in = new In("dictionary-yawl.txt");
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
		// BoggleBoard board = new BoggleBoard("board4x4.txt");
//		BoggleBoard board = new BoggleBoard("board-q.txt");
		BoggleBoard board = new BoggleBoard("board-points4.txt");

		StdOut.println(dictionary.length);
		StdOut.println(board);
		int score = 0;
		int total = 0;
		for (String word : solver.getAllValidWords(board)) {
			StdOut.println(word + " " + solver.scoreOf(word));
			++total;
			score += solver.scoreOf(word);
		}
		StdOut.println(total);
		StdOut.println("Score = " + score);
	}
}
