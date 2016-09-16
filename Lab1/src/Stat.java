
public class Stat {

	public static double mean(double[] arr) {
		double sum = 0;
		for (double i : arr)
			sum += i;
		return sum / arr.length;
	}

	public static double variance(double[] arr, double mean) {
		double var = 0;
		for (double i : arr)
			var += (i - mean) * (i - mean);
		return var / arr.length;
	}

	public static double variance(double[] arr) {
		return variance(arr, mean(arr));
	}

	public static double stdVar(double[] arr, double mean) {
		return Math.sqrt(variance(arr, mean));
	}
}
