import java.util.LinkedList;

public class Node {
	LinkedList<Integer> nodes;
	LinkedList<Integer>[] graph;
	LinkedList<Node> children;
	int[] indSets;
	String[] permutations;
	boolean visited = false;

	public void addChild(Node n) {
		children.add(n);
	}

	public void addGraph(LinkedList<Integer>[] graph) {
		this.graph = graph;
	}

	public Node(LinkedList<Integer> nodes) {
		this.nodes = nodes;
		this.children = new LinkedList<Node>();
		indSets = new int[(int) Math.pow(2, nodes.size())];

		permutations = new String[(int) Math.pow(2, nodes.size())];
		for (int i = 0; i < Math.pow(2, nodes.size()); i++) {
			permutations[i] = "";
			String bin = Integer.toBinaryString(i);
			for (int j = 0; j < nodes.size() - bin.length(); j++)
				permutations[i] += "0";
			permutations[i] += bin;
		}
	}

	public LinkedList<Integer> getNodes() {
		return nodes;
	}

	public void solveNode() {
		if (children.isEmpty())
			for (int i = 0; i < permutations.length; i++)
				indSets[i] = addSol(permutations[i]);
		else {
			for (Node n : children)
				if (!n.isVisited())
					n.solveNode();
			for (int i = 0; i < permutations.length; i++) {
				int max = 0;
				for (Node n : children) {
					String perm = n.getSharedPerm(permutations[i], nodes, graph);
					int m = n.getPermMax(perm);
					for (int j = 0; j < perm.length(); j++)
						if (perm.charAt(j) == '1')
							m--;
					max = m;// max += m > 0 ? m : 0;
				}
				indSets[i] = addSol(permutations[i]) + max;
			}
		}
		visited = true;
	}

	public boolean isVisited() {
		return visited;
	}

	private int addSol(String perm) {
		LinkedList<Integer> marked = new LinkedList<Integer>();
		for (int i = 0; i < perm.length(); i++)
			if (perm.charAt(i) == '1')
				marked.add(i);
		for (int n : marked)
			for (int m : marked)
				if (graph[n].contains(m))
					return -1;
		return marked.size();
	}

	public int getNodeIndex(int node) {
		return nodes.indexOf(node);
	}

	public int getSize() {
		return nodes.size();
	}

	public int getPermMax(String perm) {
		String p = "";
		for (int i = 0; i < perm.length(); i++)
			if (perm.charAt(i) == '1')
				p += '1';
			else if (perm.charAt(i) == '-')
				p += '0';
			else
				p += '.';
		int max = 0;
		for (int i = 0; i < permutations.length; i++)
			if (permutations[i].matches(p) && indSets[i] > max)
				max = indSets[i];
		return max;
	}

	/**
	 * Returns a permutation string that can be used in getPermMax
	 * 
	 * @param perm
	 * @param nodes
	 * @return
	 */
	public String getSharedPerm(String perm, LinkedList<Integer> nodes, LinkedList<Integer>[] graph) {
		String res = "";
		for (int i = 0; i < this.nodes.size(); i++)
			if (nodes.contains(this.nodes.get(i)) && perm.charAt(nodes.indexOf(this.nodes.get(i))) == '1')
				res += '1';
			else {
				for (int j = 0; j < perm.length(); j++)
					if (perm.charAt(j) == '1' && graph[j].contains(nodes.indexOf(this.nodes.get(i)))) {
						res += '-';
						break;
					}
				if (i == res.length())
					res += '0';

			}
		return res;
	}

	public void printSol() {
		for (int i : indSets)
			System.out.print(i + " ");
		System.out.println();
	}

	public int getMaxSol() {
		int max = 0;
		for (int i : indSets)
			if (max < i)
				max = i;
		return max;
	}
}
