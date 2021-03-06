import java.util.HashMap;

public class Fibonacci {
	int callsToFib = 0;
	int result = 0;
	HashMap<Integer, Integer> alreadySeen;

	public Fibonacci(int n) {
		this.callsToFib = 0;
		this.alreadySeen = new HashMap<Integer, Integer>();
		this.result = fib(n);
	}

	public int fib(int n) {
		return fibHelper(n, alreadySeen);
	}

	private int fibHelper(int n, HashMap<Integer, Integer> alreadySeen) {
		callsToFib++;
		if (n == 0)
			return 0;
		else if (n == 1) {
			return 1;
		} else if (alreadySeen.containsKey(n)) {
			return alreadySeen.get(n);
		} else {
			int val = fibHelper(n - 1, alreadySeen)
					+ fibHelper(n - 2, alreadySeen);
			alreadySeen.put(null, val);
			return val;
		}
	}
}
