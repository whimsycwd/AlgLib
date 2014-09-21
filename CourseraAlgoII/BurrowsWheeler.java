import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * Programming Assignment 6.
 * 
 * Burrows-Wheeler transform. The goal of the Burrows-Wheeler transform is not
 * to compress a message, but rather to transform it into a form that is more
 * amenable to compression. The transform rearranges the characters in the input
 * so that there are lots of clusters with repeated characters, but in such a
 * way that it is still possible to recover the original input. It relies on the
 * following intuition: if you see the letters hen in English text, then most of
 * the time the letter preceding it is t or w. If you could somehow group all
 * such preceding letters together (mostly t's and some w's), then you would
 * have an easy opportunity for data compression.
 * 
 * @since 2014.4.27
 * @author whimsycwd
 * 
 */

public class BurrowsWheeler {
	// apply Burrows-Wheeler encoding, reading from standard input and writing
	// to standard output
	public static void encode() {

		String input = BinaryStdIn.readString();

		CircularSuffixArray cs = new CircularSuffixArray(input);

		for (int i = 0; i < cs.length(); ++i) {
			if (cs.index(i) == 0) {
				BinaryStdOut.write(i);
				break;
			}
		}
		for (int i = 0; i < cs.length(); ++i) {

			BinaryStdOut.write(input.charAt((cs.index(i) + input.length() - 1)
					% input.length()));
		}
		BinaryStdOut.close();

	}

	// apply Burrows-Wheeler decoding, reading from standard input and writing
	// to standard output
	public static void decode() {
		int first = BinaryStdIn.readInt();

		ArrayList<Character> list = new ArrayList<Character>();

		int[] count = new int[257]; // in fact \0 <=> 0 is not possible . so 256 is fine.

		while (!BinaryStdIn.isEmpty()) {
			char c = BinaryStdIn.readChar();
			list.add(c);
			count[c + 1]++;
		}

		for (int i = 1; i < 257; ++i)
			count[i] += count[i - 1];

		int[] next = new int[list.size()];

		Arrays.fill(next, -1);

		char[] sorted = new char[list.size()];
		for (int i = 0; i < list.size(); ++i) {
			char c = list.get(i);

			sorted[count[c]] = c;
			next[count[c]] = i;
			++count[c];
		}

		for (int i = 0; i < list.size(); ++i) {
			BinaryStdOut.write(sorted[first]);
			first = next[first];
		}
		BinaryStdOut.close();

	}

	// if args[0] is '-', apply Burrows-Wheeler encoding
	// if args[0] is '+', apply Burrows-Wheeler decoding
	public static void main(String[] args) {
		if (args[0].equals("-"))
			encode();
		else if (args[0].equals("+"))
			decode();
		else
			throw new IllegalArgumentException("Illegal command line argument");
	}

}
