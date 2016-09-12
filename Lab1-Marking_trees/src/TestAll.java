
public class TestAll {

	public static void main(String[] args) {
		int[] Ns = { 3, 7, 15, 31, 63, 127, 255, 511, 1023, 524287, 1048575 };
		Case3 c = new Case3();
		for (int N : Ns)
			c.play(N, 1000);

	}
}
