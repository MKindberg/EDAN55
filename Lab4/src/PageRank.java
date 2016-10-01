import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Random;

public class PageRank {

	private LinkedList<Integer>[] graph;
	private Random r = new Random();
	private int exited = 0;

	public static void main(String[] args) {
		int rounds = 1000000;
		String[] files = new String[] { "three", "tiny", "wikipedia", "medium", "p2p-Gnutella08-mod" };
		PageRank p = new PageRank();
		p.run("p2p-Gnutella08-mod", rounds);
		// p.printGraph();
		// for (String file : files)
		// p.run(file, rounds);
	}

	public void run(String file, int rounds) {
		readfile(file + ".txt");
		int[] visited = session(rounds);
		System.out.println(file + ": ");
		// printResults(visited, rounds);
		System.out.println();
	}

	public int[] session(int rounds) {
		int n = graph.length;
		int[] res = new int[n];
		int current = 0;
		res[0]++;
		int[] oldperc = new int[n];
		for (int i = 0; i < rounds; i++) {
			if (r.nextInt(100) < 15 || graph[current].isEmpty())
				current = r.nextInt(n);// jump to random node
			else
				current = graph[current].get(r.nextInt(graph[current].size()));
			res[current]++;
			int[] perc = div(res, i);
			if (eq(perc, oldperc)) {
				System.out.println("Rounds: " + i);
				exited = i;
				break;
			}
			oldperc = perc;
		}

		return res;

	}

	private boolean eq(int[] one, int[] two) {
		for (int i = 0; i < one.length; i++)
			if (one[i] != two[i])
				return false;
		return true;
	}

	private int[] div(int[] vec, int num) {
		int[] res = new int[vec.length];
		for (int i = 0; i < vec.length; i++)
			res[i] = (int) Math.round(100 * ((double) vec[i] / num));
		return res;
	}

	public int getE() {
		return exited;
	}

	public void readfile(String file) {

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			int n = Integer.parseInt(br.readLine().trim());
			graph = new LinkedList[n];
			for (int i = 0; i < n; i++)
				graph[i] = new LinkedList<Integer>();
			String line = br.readLine();
			while (line != null) {
				String[] paths = line.trim().split("\\s+");
				for (int i = 0; i < paths.length && paths.length > 1; i += 2)
					graph[Integer.parseInt(paths[i])].add(Integer.parseInt(paths[i + 1]));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printResults(int[] res, int rounds) {
		System.out.println("Node\tVisited\t\tPercent");
		for (int i = 0; i < res.length; i++)
			System.out.println(i + "\t" + res[i] + "\t\t" + res[i] * 100.0 / exited);
	}

	public void printGraph() {
		for (int i = 0; i < graph.length; i++) {
			System.out.print(i + ": ");
			for (int j : graph[i])
				System.out.print(j + " ");
			System.out.println();
		}
	}

	public void toMatlab() {
		int n = graph.length;
		double[][] res = new double[n][n];
		for (int i = 0; i < n; i++) {
			if (graph[i].isEmpty())
				for (int j = 0; j < n; j++)
					res[i][j] = 1.0 / n;
			for (int j : graph[i])
				res[i][j] += 1.0 / graph[i].size();
		}

		System.out.print("[");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(Math.round(res[i][j] * 10000) / 10000.0 + " ");
			System.out.println(";");
		}
		System.out.println("];");
		System.out.println(n);
	}
}
