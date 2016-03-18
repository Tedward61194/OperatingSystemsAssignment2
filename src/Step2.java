class Step2 extends Thread {

	private int i;
	private int j;
	private int[] x;
	private int[] w;

	public Step2 (int i, int j, int[] x, int[] w) {
		this.i = i;
		this.j = j;
		this.x = x;
		this.w = w;
	}

	@Override
	public void run() {
		if (x[i] < x[j]) {
			w[i] = 0;
		} else if (x[i] > x[j]) {
			w[j] = 0;
		} else if (x[i] == x[j]) {
			return;
		}

	}
}