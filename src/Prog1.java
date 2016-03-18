/**
 * Program created by Teddy Segal on March 8, 2016
 * Problem 1 for Assignment 2 for CSC 331, Operating Systems
 * Will run from the command line command 'java prog1 n x0 x1 ... xn-1'
 * Where n is the number of arguments to consider and x0 to xn-1 are inputed values
 * Program will then find the max number as fast as possible through threading
 * I can assume that all values inputed are betwen 3 and 10 and correct (no error checking)
*/
import java.lang.*;

public class Prog1 {

	private static int[] x;
	private static int[] w;
	private static Step1[] s1a;
	private static Step2[] s2a;
    private static Step3[] s3a;
	private static int step2Index;
	private static int size;

	public static void main(String[] args) {
		System.out.println();

		if (args.length == 0) {
			System.out.println("Error: Please run the program in following format:"
				+"\njava prog1 n X0 X1 ... Xn-1"
				+"\nWhere 'n' is the number of inputs and X0 to Xn-1 are the inputs");
			return;
		}

		size = Integer.parseInt(args[0]); //First argument should be num of inputs

        System.out.println("Number of input values: " + size);

		x = new int[size];
		for (int i = 0; i < size; i++) {
			x[i] = Integer.parseInt(args[i+1]); // read args into first array
		}

		//Initialize w with 1's
		w = new int[size];
		s1a = new Step1[size];
		for (int i = 0; i < size; i++) {
			Step1 curS1 = new Step1(i, w);
			s1a[i] = curS1;
			s1a[i].start();
		}
		for (int i = 0; i < size; i++) { //wait for step 1 to finish
			try {
				s1a[i].join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); //set interrupt flag
				System.out.println("Failed do to InterruptException");
			}
		}

		//Test Step 1
		System.out.println("Step1:");
		System.out.print("X: ");
		for (int i = 0; i < size; i++) {
			System.out.print(x[i] + ", ");
		}
		System.out.println();
		System.out.print("W: ");
		for (int i = 0; i < size; i++) {
			System.out.print(w[i] + ", ");
		}
        System.out.println("\n");

		step2Index = 0;		//Make a thread for each comparison+. The run of the thread compares
		int sizeOfStep2Array = size * size;           //In class we talked about it being something like((size*(size-1))/2);
		s2a = new Step2[sizeOfStep2Array];
		for (int i = 0; i < size - 1; i ++) {
			for (int j = 1; j < size; j++) {
				Step2 curS2 = new Step2(i, j, x, w);
				s2a[step2Index] = curS2;
				s2a[step2Index].start();
				step2Index++;
			}
		}
		for (int i = 0; i <= ((size*(size-1))/2); i++) {	//Wait for step 2 to finish before moving on to 3
			try {
				s2a[i].join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Failed do to InterruptException");
			}
		}

		//Test Step 2
		System.out.println("Step2:");
		System.out.print("W: ");
		for (int i = 0; i < size; i++) {
			System.out.print(w[i]);
		}
        System.out.println("\n");

        //Find index of 1 in w, corresponding to max in x and print max
        System.out.println("Step3: ");
        s3a = new Step3[size];
        for (int i = 0; i < size; i++) {
            Step3 curS3 = new Step3(x, w, i);
            s3a[i] = curS3;
            s3a[i].start();
            if (s3a[i].getW() == 1) {
                System.out.println("Maximum: " + s3a[i].getX());
                System.out.println("Location (Starting from 0): " + s3a[i].getIndex());
            }
        }
	}
}