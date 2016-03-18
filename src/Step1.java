class Step1 extends Thread {
	private int index;
	private int[] w;

	public Step1 (int i, int[] w) {
		this.index = i;
		this.w = w;
	}

	@Override
	public void run() {
		w[index] = 1;
	}
}