
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class R0 {

	private int nbrOfCalls = 0;

	public static void main(String[] args) {
		R0 i = new R0();
		for (int j = 30; j < 61; j += 10) {
			LinkedList<Integer>[] graph = i.readInput("g" + j + ".in");
			System.out.println(j + ": " + i.alg(graph) + " calls: " + i.nbrOfCalls);
		}
	}

	public int alg(LinkedList<Integer>[] graph) {
		nbrOfCalls++;
		// case 1:
		if (empty(graph))
			return 0;
		// case 2:
		for (int i = 0; i < graph.length; i++)
			if (graph[i] != null && graph[i].size() == 0) {
				graph[i] = null;
				return 1 + alg(graph);
			}

		// case 3:
		int max = 0;
		int maxN = -1;

		for (int i = 0; i < graph.length; i++)// get max neighbors
			if (graph[i] != null && graph[i].size() > max) {
				max = graph[i].size();
				maxN = i;
			}
		LinkedList<Integer>[] graph2 = copy(graph);
		remove(graph, maxN);
		while (!graph2[maxN].isEmpty())
			if (graph2[graph2[maxN].getFirst()] != null)
				remove(graph2, graph2[maxN].getFirst());
		remove(graph2, maxN);
		return Math.max(alg(graph), 1 + alg(graph2));

	}

	public boolean empty(LinkedList<Integer>[] graph) {
		for (LinkedList l : graph)
			if (l != null)
				return false;
		return true;
	}

	public LinkedList<Integer>[] copy(LinkedList<Integer>[] graph) {
		LinkedList<Integer>[] cpy = new LinkedList[graph.length];
		int i = 0;
		for (LinkedList<Integer> l : graph) {
			LinkedList<Integer> c;
			if (l == null)
				c = null;
			else {
				c = new LinkedList<Integer>();
				for (int j : l)
					c.add(j);
			}
			cpy[i++] = c;
		}
		return cpy;
	}

	public void remove(LinkedList<Integer>[] graph, Integer node) {
		for (int i : graph[node])
			graph[i].remove(node);
		graph[node] = null;
	}

	public LinkedList<Integer>[] readInput(String file) {

		nbrOfCalls = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(file));) {
			int n = Integer.parseInt(br.readLine());
			LinkedList<Integer>[] graph = new LinkedList[n];
			String line = br.readLine();
			for (int j = 0; j < n; j++) {
				String[] row = line.split(" ");
				graph[j] = new LinkedList<Integer>();
				for (int i = 0; i < row.length; i++)
					if (Integer.parseInt(row[i]) == 1)
						graph[j].add(i);
				line = br.readLine();
			}
			return graph;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void printGraph(LinkedList<Integer>[] graph) {
		for (LinkedList<Integer> l : graph) {
			for (Integer i : l)
				System.out.print(i + " ");
			System.out.println();
		}
	}
}
