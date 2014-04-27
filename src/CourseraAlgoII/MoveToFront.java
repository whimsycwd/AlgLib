/**
 * 
 * 
 * Programming Assignment 6.
 * 
 * Implement the Move to front encoding.
 * 
 * Move-to-front encoding. Given a text file in which sequences of the same
 * character occur near each other many times, convert it into a text file in
 * which certain characters appear more frequently than others.
 * 
 * @since 2014.4.27
 * 
 * @author whimsycwd
 * 
 */

public class MoveToFront {
	private static int R = 256;
	private static int r = 8;

	// apply move-to-front encoding, reading from standard input and writing to
	// standard output
	public static void encode() {
		char[] a = new char[R];
		for (int i = 0; i < R; ++i) {
			a[i] = (char) i;
		}

		String input = BinaryStdIn.readString();

		for (int i = 0; i < input.length(); ++i) {
			for (int j = 0; j < R; ++j) {
				if (a[j] == input.charAt(i)) {

					BinaryStdOut.write(j, r);

					char t = a[j];
					for (int k = j - 1; k >= 0; --k) {
						a[k + 1] = a[k];
					}

					a[0] = t;

					break;

				}
			}
		}

		BinaryStdOut.close();
	}

	// apply move-to-front decoding, reading from standard input and writing to
	// standard output
	public static void decode() {

		char[] a = new char[R];
		for (int i = 0; i < R; ++i) {
			a[i] = (char) i;
		}

		while (!BinaryStdIn.isEmpty()) {
			char b = BinaryStdIn.readChar();
			BinaryStdOut.write(a[b]);

			int t = a[b];
			for (int i = b - 1; i >= 0; --i) {
				a[i + 1] = a[i];
			}
			a[0] = (char) t;
		}

		BinaryStdOut.close();

	}

	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {

		if (args[0].equals("-"))
			encode();
		else if (args[0].equals("+"))
			decode();
		else
			throw new IllegalArgumentException("Illegal command line argument");

	}

}