import java.util.Random;

public class Test {
	public static void main(String[] args) {
		Random r = new Random();
		int sum = 0;
		for (int i = 0; i < 1000000; i++) {
			boolean[] b = new boolean[4];
			int c = 0;
			while (!b[0] || !b[1] || !b[2] || !b[3]) {
				b[r.nextInt(4)] = true;
				c++;
			}
			sum += c;
		}
		System.out.println(sum / 1000000.0);
	}
}
