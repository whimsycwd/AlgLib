import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 97.50 / 100.00
 * 
 * 
 * Student memory = 232.74 N^2 + 3.35 N + 843.14 (R^2 = 1.000) Reference memory
 * = 3.99 N^2 + 222.25 N + 396.00 (R^2 = 1.000) => FAILED, memory must grow
 * asymptotically (N^2 term) no faster than 50x the reference solution.
 * 
 * Used the fordFulkerson,and I create a new FlowNetwork for every probe.
 * Should I reuse FlowNetwork? 
 * 
 * @since  2014.04.06
 * @author whimsycwd
 * 
 */

public class BaseballElimination {

	private FlowNetwork network;
	private FordFulkerson fordFulkerson;
	private int[] wins;
	private int[] loses;
	private int[] remains;
	private int[][] g;
	private Map<String, Integer> dict;
	private int numberOfTeams;
	private List<String> teamsList;

	private boolean[] elimated;
	private List<String>[] certificates;

	// create a baseball division from given filename in format specified below
	@SuppressWarnings("unchecked")
	public BaseballElimination(String filename) {
		In in = new In(filename);
		int n = in.readInt();
		numberOfTeams = n;
		elimated = new boolean[n];
		certificates = (List<String>[]) new List[n];
		wins = new int[n];
		loses = new int[n];
		remains = new int[n];
		g = new int[n][n];

		teamsList = new ArrayList<String>();

		dict = new HashMap<String, Integer>();

		for (int i = 0; i < n; ++i) {
			String name = in.readString();
			teamsList.add(name);
			dict.put(name, i);
			wins[i] = in.readInt();
			loses[i] = in.readInt();
			remains[i] = in.readInt();
			for (int j = 0; j < n; ++j) {
				g[i][j] = in.readInt();
			}
		}

		for (int kk = 0; kk < n; ++kk) {

			network = new FlowNetwork(n * n + n + 2);
			certificates[kk] = new ArrayList<String>();

			int s = n * n + n;
			int t = s + 1;

			for (int i = 0; i < n; ++i) {
				if (i != kk) {
					if (wins[kk] + remains[kk] - wins[i] < 0) {
						elimated[kk] = true;
						certificates[kk].add(teamsList.get(i));
						break;
					}
					network.addEdge(new FlowEdge(n * n + i, t, wins[kk]
							+ remains[kk] - wins[i]));
				}
			}

			if (!elimated[kk])
				for (int i = 0; i < n; ++i)
					for (int j = i + 1; j < n; ++j)
						if (i != j && i != kk && j != kk) {

							network.addEdge(new FlowEdge(s, i * n + j, g[i][j]));
							network.addEdge(new FlowEdge(i * n + j, n * n + i,
									Integer.MAX_VALUE));
							network.addEdge(new FlowEdge(i * n + j, n * n + j,
									Integer.MAX_VALUE));

						}

			if (!elimated[kk]) {
				fordFulkerson = new FordFulkerson(network, s, t);
				for (int i = 0; i < n; ++i)
					for (int j = 0; j < n; ++j)
						if (i != kk && j != kk) {
							if (fordFulkerson.inCut(i * n + j)) {
								elimated[kk] = true;

							}
						}
				if (elimated[kk]) {
					for (int i = 0; i < n; ++i) {
						if (fordFulkerson.inCut(n * n + i)) {
							certificates[kk].add(teamsList.get(i));
						}
					}
				}
			}

		}
		System.gc();// have effect??
	}

	// number of teams

	public int numberOfTeams() {
		return numberOfTeams;
	}

	// all teams

	public Iterable<String> teams() {
		return teamsList;
	}

	// number of wins for given team

	public int wins(String team) {
		if (dict.get(team) == null)
			throw new IllegalArgumentException();
		return wins[dict.get(team)];
	}

	// number of losses for given team

	public int losses(String team) {
		if (dict.get(team) == null)
			throw new IllegalArgumentException();
		return loses[dict.get(team)];
	}

	// number of remaining games for given team

	public int remaining(String team) {
		if (dict.get(team) == null)
			throw new IllegalArgumentException();
		return remains[dict.get(team)];
	}

	// number of remaining games between team1 and team2

	public int against(String team1, String team2) {
		if (dict.get(team1) == null)
			throw new IllegalArgumentException();
		if (dict.get(team2) == null)
			throw new IllegalArgumentException();
		return g[dict.get(team1)][dict.get(team2)];
	}

	// is given team eliminated?

	public boolean isEliminated(String team) {
		if (dict.get(team) == null)
			throw new IllegalArgumentException();
		return elimated[dict.get(team)];
	}

	// subset R of teams that eliminates given team; null if not eliminated

	public Iterable<String> certificateOfElimination(String team) {
		if (dict.get(team) == null)
			throw new IllegalArgumentException();
		if (certificates[dict.get(team)].size() == 0)
			return null;
		return certificates[dict.get(team)];
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination("teams5.txt");
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team))
					StdOut.print(t + " ");
				StdOut.println("}");
			} else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}

}
