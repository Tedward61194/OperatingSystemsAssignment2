class Step3 extends Thread {

    private int[] x;
    private int[] w;
    private int index;

    public Step3 (int[] x, int[] w, int index) {
        this.x = x;
        this.w = w;
        this.index = index;
    }

    @Override
    public void run() {
    }

    public int getX() {
        return x[index];
    }

    public int getW() {
        return w[index];
    }

    public int getIndex() {
        return index;
    }
}
