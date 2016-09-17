
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class IndSet {

	private int nbrOfCalls = 0;

	public static void main(String[] args) {
		IndSet i = new IndSet();

		System.out.println("R0:");
		for (int j = 30; j <= 60; j += 10) {
			LinkedList<Integer>[] graph = i.readInput("g" + j + ".in");
			long start = System.currentTimeMillis();
			System.out.println(j + ": " + i.R0(graph) + ", Calls: " + i.nbrOfCalls + ", Time: "
					+ (System.currentTimeMillis() - start) + " ms");
		}

		System.out.println();
		System.out.println("R1:");
		for (int j = 40; j <= 100; j += 10) {
			LinkedList<Integer>[] graph = i.readInput("g" + j + ".in");
			long start = System.currentTimeMillis();
			System.out.println(j + ": " + i.R1(graph) + ", Calls: " + i.nbrOfCalls + ", Time: "
					+ (System.currentTimeMillis() - start) + " ms");
		}

		System.out.println();
		System.out.println("R2:");
		for (int j = 40; j <= 120; j += 10) {
			LinkedList<Integer>[] graph = i.readInput("g" + j + ".in");
			long start = System.currentTimeMillis();
			System.out.println(j + ": " + i.R2(graph) + ", Calls: " + i.nbrOfCalls + ", Time: "
					+ (System.currentTimeMillis() - start) + " ms");
		}

	}

	public int R0(LinkedList<Integer>[] graph) {
		nbrOfCalls++;
		// case 1:
		if (isEmpty(graph))
			return 0;
		// case 2:
		for (int i = 0; i < graph.length; i++)
			if (graph[i] != null && graph[i].size() == 0) {
				graph[i] = null;
				return 1 + R0(graph);
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
		return Math.max(R0(graph), 1 + R0(graph2));
	}

	public int R1(LinkedList<Integer>[] graph) {
		nbrOfCalls++;
		// case 1:
		if (isEmpty(graph))
			return 0;
		// case 2:
		for (int i = 0; i < graph.length; i++)
			if (graph[i] != null && graph[i].size() < 2) {
				if (graph[i].size() == 1)
					remove(graph, graph[i].getFirst());
				graph[i] = null;
				return 1 + R1(graph);
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
		return Math.max(R1(graph), 1 + R1(graph2));
	}

	public int R2(LinkedList<Integer>[] graph) {
		nbrOfCalls++;
		// case 1:
		if (isEmpty(graph))
			return 0;

		// case 1.5
		for (int i = 0; i < graph.length; i++)
			if (graph[i] != null && graph[i].size() == 2)
				if (graph[graph[i].getFirst()].contains(graph[i].get(1))) {
					remove(graph, graph[i].getFirst());
					remove(graph, graph[i].getFirst());
					graph[i] = null;
					return 1 + R2(graph);
				} else {
					LinkedList<Integer> l = new LinkedList<Integer>();
					l.addAll(graph[graph[i].get(0)]);
					l.remove((Integer) i);
					for (Integer j : graph[graph[i].get(1)])
						if (!l.contains(j) && j != i)
							l.add(j);
					remove(graph, graph[i].getFirst());
					remove(graph, graph[i].getFirst());
					add(graph, l, i);
					return 1 + R2(graph);
				}
		// case 2:
		for (int i = 0; i < graph.length; i++)
			if (graph[i] != null && graph[i].size() < 2) {
				if (graph[i].size() == 1)
					remove(graph, graph[i].getFirst());
				graph[i] = null;
				return 1 + R2(graph);
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
		return Math.max(R2(graph), 1 + R2(graph2));
	}

	public boolean isEmpty(LinkedList<Integer>[] graph) {
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

	public void add(LinkedList<Integer>[] graph, LinkedList<Integer> l, Integer node) {
		graph[node] = l;
		l.remove(node);
		l.remove(node);
		for (Integer i : l)
			graph[i].add(node);
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
		for (LinkedList<Integer> l : graph)
			if (l != null) {
				for (Integer i : l)
					System.out.print(i + " ");
				System.out.println();
			} else
				System.out.println("-- -- --");
	}
}
