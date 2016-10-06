import java.util.LinkedList;

public class Node {
	LinkedList<Integer> nodes;
	LinkedList<Integer>[] graph;
	int parent;
	LinkedList<Node> children;
	int[] indSets;
	String[] permutations;

	public static void main(String[] args) {

	}

	public Node(LinkedList<Integer> nodes, LinkedList<Integer>[] graph, int parent, LinkedList<Node> children) {
		this.nodes = nodes;
		this.graph = graph;
		this.parent = parent;
		this.children = children;
		indSets = new int[(int) Math.pow(2, graph.length)];

		permutations = new String[(int) Math.pow(2, graph.length)];
		for (int i = 0; i < Math.pow(2, graph.length); i++) {
			permutations[i] = "";
			String bin = Integer.toBinaryString(i);
			for (int j = 0; j < graph.length - bin.length(); j++)
				permutations[i] += "0";
			permutations[i] += bin;
		}
	}

	public void solveNode() {
		if (children.isEmpty())
			for (int i = 0; i < permutations.length; i++)
				indSets[i] = addSol(permutations[i]);
		else
			for (int i = 0; i < permutations.length; i++) {
				int max = 0;
				for (Node n : children) {
					String p = "";
					max += n.getPermMax(n.getSharedPerm(permutations[i], nodes));
				}
				indSets[i] = addSol(permutations[i]) + max;
			}
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
		for (int i = 0; i < p.length(); i++)
			if (perm.charAt(i) == '1')
				p += '1';
			else
				p += '.';

		int max = -1;
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
	public String getSharedPerm(String perm, LinkedList<Integer> nodes) {
		String res = "";
		for (int i = 0; i < this.nodes.size(); i++)
			if (nodes.contains(this.nodes.get(i)) && perm.charAt(nodes.indexOf(this.nodes.get(i))) == '1')
				res += '1';
			else
				res += '0';
		return res;
	}
}
