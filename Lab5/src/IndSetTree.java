import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class IndSetTree {

	LinkedList<Integer>[] G;
	LinkedList<Integer>[] T;
	LinkedList<Integer>[] TNodes;

	public static void main(String[] args) {
		IndSetTree i = new IndSetTree();
		i.readInput(".\\Graphs\\HouseGraph");
		i.printNodes();
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
				T[node2].add(node1);
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
