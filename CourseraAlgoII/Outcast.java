import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/**
 * Coursera AlgII PA1
 * 
 * 
 * @since  2014.03.22
 * @author whimsy
 *
 */

public class Outcast {

	WordNet wordnet;

	/**
	 * constructor takes a WordNet Object
	 * 
	 * @param wordnet
	 */
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}

	/**
	 * Outcast detection. Given a list of wordnet nouns A1, A2, ..., An, which
	 * noun is the least related to the others? To identify an outcast, compute
	 * the sum of the distances between each noun and every other one:
	 * 
	 * di = dist(Ai, A1) + dist(Ai, A2) + ... + dist(Ai, An) and return a noun
	 * At for which dt is maximum.
	 * 
	 * 
	 * given an array of WordNet nouns, return an outcast
	 * 
	 * @param nouns
	 * @return
	 */
	public String outcast(String[] nouns) {
		int max = Integer.MIN_VALUE;
		int ret = -1;
		for (int i = 0; i < nouns.length; ++i) {
			int t = 0;
			for (int j = 0; j < nouns.length; ++j)
				t += wordnet.distance(nouns[i], nouns[j]);
			if (t > max) {
				max = t;
				ret = i;
			}
		}
		return nouns[ret];
	}

	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			String[] nouns = In.readStrings(args[t]);
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
}
