import java.util.Random;

public class Case3 {

	private Random r = new Random();

	private int N = 1048575;
	private int iter = 10;

	private int[] nbrs;
	private int[] is;

	private boolean[] tree = new boolean[N + 1];
	private int marked = 0;
	private int rounds = 1;

	public void play(int size, int it) {
		this.N = size;
		this.iter = it;
		double total = 0;
		prep();
		double[] all = new double[iter];
		tree = new boolean[N + 1];

		for (int i = 0; i < iter; i++) {
			game();
			total += rounds;
			all[i] = rounds;
			reset();
		}
		double mean = total / iter;
		System.out.println("N = " + N);
		System.out.println(mean + " \\pm " + Stat.stdVar(all, mean));
		System.out.println();
	}

	public int getNode1() {
		return r.nextInt(N) + 1;
	}

	private void prep() {
		nbrs = new int[N];
		is = new int[N];
		for (int i = 0; i < N; i++) {
			nbrs[i] = i;
			is[i] = i;
		}
	}

	public int getNode() {
		int max = N - marked;
		int i = r.nextInt(max);
		int rand = nbrs[i];
		return rand + 1;
	}

	public void game() {
		while (!mark(getNode()))
			rounds++;
	}

	public boolean mark(int n) {
		if (!tree[n]) {
			tree[n] = true;
			if (++marked == N)
				return true;

			int temp = nbrs[N - marked];
			nbrs[N - marked] = nbrs[is[n - 1]];
			nbrs[is[n - 1]] = temp;
			is[temp] = is[n - 1];
			is[n - 1] = N - marked;

			if (n <= N / 2 && tree[2 * n]) // mark right child
				if (mark(2 * n + 1))
					return true;
			if (n <= N / 2 && tree[2 * n + 1]) // mark left child
				if (mark(2 * n))
					return true;
			if (n % 2 == 0 && tree[n / 2]) // mark right sibling
				if (mark(n + 1))
					return true;
			if (n % 2 != 0 && tree[n / 2]) // mark left sibling
				if (mark(n - 1))
					return true;
			if (n % 2 == 0 && tree[n + 1]) // mark parent if right
				if (mark(n / 2))
					return true;
			if (n % 2 != 0 && tree[n - 1]) // mark parent if left
				if (mark(n / 2))
					return true;
		}
		return false;

	}

	public void reset() {
		tree = new boolean[N + 1];
		marked = 0;
		rounds = 1;
		prep();
	}
}
