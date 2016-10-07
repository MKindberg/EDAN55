import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class IndSetTree {

	LinkedList<Integer>[] G;
	LinkedList<Integer>[] T;
	LinkedList<Integer>[] TNodes;
	Node[] tree;
	int width;

	public static void main(String[] args) {
		IndSetTree i = new IndSetTree();
		i.readInput("./Graphs/FibonacciTree_10");
		i.solve();
	}

	public void solve() {
		int max = 0;
		for (int i = tree.length - 1; i >= 0; i--) {
			tree[i].solveNode();
			// System.out.println(i + ": " + tree[i].getMaxSol());
//			System.out.print(i + ": ");
//			tree[i].printSol();
			if (max < tree[i].getMaxSol())
				max = tree[i].getMaxSol();
		}
		IndSet ind = new IndSet();
		System.out.println(ind.R2(G));
		System.out.println(max);
		System.out.println();
//		for (int i = 0; i < tree.length; i++) {
//			System.out.print(i + ": ");
//			tree[i].printChildren();
//			System.out.println();
//		}
	}

	public void readInput(String file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file + ".gr"))) {// read
																					// graph
			String line = br.readLine();
			while (line.charAt(0) == 'c')
				line = br.readLine();
			String[] head = line.split(" ");
			int n = Integer.parseInt(head[2]);

			G = new LinkedList[n];
			for (int i = 0; i < n; i++)
				G[i] = new LinkedList<Integer>();

			line = br.readLine();
			while (line != null) {
				String[] edge = line.split(" ");
				if (edge[0].equals("c")) {
					line = br.readLine();
					continue;
				}
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

			tree = new Node[n];
			int j = 0;

			T = new LinkedList[n];
			TNodes = new LinkedList[n];
			for (int i = 0; i < n; i++) {
				T[i] = new LinkedList<Integer>();
				TNodes[i] = new LinkedList<Integer>();
			}

			line = br.readLine();
			while (line != null && line.substring(0, 1).equals("b")) {
				LinkedList<Integer> nds = new LinkedList<Integer>();
				String[] nodes = line.split(" ");
				int node = Integer.parseInt(nodes[1]) - 1;
				for (int i = 2; i < nodes.length; i++) {
					TNodes[node].add(Integer.parseInt(nodes[i]) - 1);
					nds.add(Integer.parseInt(nodes[i]) - 1);
				}

				tree[j] = new Node(nds, j);
				j++;
				line = br.readLine();
			}

			for (j = 0; j < tree.length; j++) { // construct internal graph
				LinkedList<Integer> nodes = tree[j].getNodes();
				LinkedList<Integer>[] graph = new LinkedList[nodes.size()];

				for (int i = 0; i < graph.length; i++) {
					graph[i] = new LinkedList<Integer>();
					for (int k = 0; k < nodes.size(); k++)
						if (G[nodes.get(i)].contains(nodes.get(k)))
							graph[i].add(tree[j].getNodeIndex(nodes.get(k)));
				}
				tree[j].addGraph(graph);
			}

			while (line != null) {
				String[] edge = line.split(" ");
				int node1 = Integer.parseInt(edge[0]) - 1;
				int node2 = Integer.parseInt(edge[1]) - 1;
				T[node1].add(node2);
				T[node2].add(node1);
				//tree[node1].addChild(tree[node2]);
				line = br.readLine();
			}
			tree[0].addChild2(T, tree);

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
