import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class IndSetTree {

	LinkedList<Integer>[] G;
	LinkedList<Integer>[] T;
	LinkedList<Integer>[] TNodes;
	int width;
	int[][] solution;

	public static void main(String[] args) {
		IndSetTree i = new IndSetTree();
		i.readInput(".\\Graphs\\HouseGraph");
		i.printNodes();
	}

	public int alg() {
		for (int i = 0; i < T.length; i++)
			comp(i);
		return 0;
	}

	private int comp(int node) {
		if (T[node].isEmpty()) { // if leaf

		} else {

		}
		return 0;
	}

	public int R0(LinkedList<Integer>[] graph) {
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

	private boolean isEmpty(LinkedList<Integer>[] graph) {
		for (LinkedList l : graph)
			if (l != null)
				return false;
		return true;
	}

	private LinkedList<Integer>[] copy(LinkedList<Integer>[] graph) {
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

	private void remove(LinkedList<Integer>[] graph, Integer node) {
		for (int i : graph[node])
			graph[i].remove(node);
		graph[node] = null;
	}

	public void readInput(String file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file + ".gr"))) {// read
																					// graph
			String line = br.readLine();
			String[] head = line.split(" ");
			int n = Integer.parseInt(head[2]);

			G = new LinkedList[n];
			for (int i = 0; i < n; i++)
				G[i] = new LinkedList<Integer>();

			line = br.readLine();
			while (line != null) {
				String[] edge = line.split(" ");
				int node1 = Integer.parseInt(edge[0]) - 1;
				int node2 = Integer.parseInt(edge[1]) - 1;
				G[node1].add(node2);
				G[node2].add(node1);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (BufferedReader br = new BufferedReader(new FileReader(file + ".td"))) {// read
			// tree
			String line = br.readLine();
			String[] head = line.split(" ");

			while (!head[0].equals("s")) {
				line = br.readLine();
				head = line.split(" ");
			}
			int n = Integer.parseInt(head[2]);
			width = Integer.parseInt(head[3]);
			solution = new int[n][(int) Math.pow(2, width)];

			T = new LinkedList[n];
			TNodes = new LinkedList[n];
			for (int i = 0; i < n; i++) {
				T[i] = new LinkedList<Integer>();
				TNodes[i] = new LinkedList<Integer>();
			}

			line = br.readLine();
			while (line != null && line.substring(0, 1).equals("b")) {
				String[] nodes = line.split(" ");
				int node = Integer.parseInt(nodes[1]) - 1;
				for (int i = 2; i < nodes.length; i++)
					TNodes[node].add(Integer.parseInt(nodes[i]) - 1);
				line = br.readLine();
			}

			while (line != null) {
				String[] edge = line.split(" ");
				int node1 = Integer.parseInt(edge[0]) - 1;
				int node2 = Integer.parseInt(edge[1]) - 1;
				T[node1].add(node2);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printGraph() {
		for (LinkedList<Integer> l : G) {
			for (int i : l)
				System.out.print(i + " ");
			System.out.println();
		}
	}

	public void printTree() {
		for (LinkedList<Integer> l : T) {
			for (int i : l)
				System.out.print(i + " ");
			System.out.println();
		}
	}

	public void printNodes() {
		for (LinkedList<Integer> l : TNodes) {
			for (int i : l)
				System.out.print(i + " ");
			System.out.println();
		}
	}
}
