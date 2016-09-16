package lab3;

import java.util.LinkedList;

public class IndSet {
	
	
	public static void main(String[] args) {
		LinkedList<Integer>[] graph;
	}
	
	public int R0(LinkedList<Integer>[] graph){
		//case 1:
		if(empty(graph))
			return 0;
		
		//case 2:
		for(int i=0;i<graph.length;i++)
			if(graph[i].size()==0){
				graph[i]=null;
				return 1+R0(graph);
			}
		
		//case 3:
		int max=0;
		int maxN=-1;
		
		for(int i = 0;i< graph.length;i++)//get max neighbors
			if(graph[i].size()>max){
				max = graph[i].size();
				maxN = i;
			}
		LinkedList<Integer>[] graph2 = copy(graph);
		remove(graph, maxN);
		for(int i:graph2[maxN])
			remove(graph2, i);
		return 1 + Math.max(R0(graph), R0(graph2));
		
	}
	
	public boolean empty(LinkedList<Integer>[] graph){
		for(LinkedList l:graph)
			if(l!=null)
				return false;
		return true;
	}
	
	public LinkedList<Integer>[] copy (LinkedList<Integer>[] graph){
		LinkedList<Integer>[] cpy = new LinkedList[graph.length];
		int i = 0;
		for(LinkedList<Integer> l:graph){
			LinkedList<Integer> c;
			if(l==null)
				c=null;
			else{
				c = new LinkedList<Integer>();
				for(int j:l)
					c.add(j);
			}
			cpy[i++] = c;
		}
		return cpy;
	}
	
	//TODO implement remove method
	public void remove(LinkedList<Integer>[] graph, int node){
		
	}
}