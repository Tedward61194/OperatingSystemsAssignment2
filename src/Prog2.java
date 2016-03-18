/**
 * Program created by Teddy Segal on April 18, 2016
 * Problem 2 for Assignment 2 for CSC 331, Operating Systems
 * The Program will create 3 threads to print out sequences of 'abc'
 * The user will be asked for the number of sequences to print.
 * 0 will result in an infinite loop of abc's
*/

/*infinate loop:
for(int i = 0; i < Double.POSITIVE_INFINITY; i++) {
    System.out.println("inf test: " + i);
}*/

import java.util.Scanner;

public class Prog2 {
    static Runner theRunner;
    static double input;

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        //get number of sequences
        System.out.println("This program will use threads to repeatedly print the sequence 'abc'."
                + "\nPlease enter the number of times you wish to print this sequence. Enter '0' to print an infinate loop");
        input = kb.nextDouble();
        if (input == 0) {
            input = Double.POSITIVE_INFINITY;
        }

        //create and run the runner object
        theRunner = new Runner();
        theRunner.myRun();

        System.out.println("\nDONE");
    }


    //There will be 1 runner object which holds the current state and has a method 'run' which when called, will initiate and start threads
    static class Runner {
        int state;

        public Runner() {
            this.state = 1;
        }

        public void myRun() {
            generateA a = new generateA();
            generateB b = new generateB();
            generateC c = new generateC();

            a.start();
            b.start();
            c.start();

            try {
                a.join();
                b.join();
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class generateA extends Thread {
        public generateA() {}

        @Override
        public void run() {
            synchronized (theRunner) {
                for (int i = 0; i < input; i++) {
                    while (theRunner.state != 1) { //wait for turn
                        try {
                            theRunner.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print('a');
                    theRunner.state = 2;
                    theRunner.notifyAll();
                }
            }
        }
    }

    static class generateB extends Thread {
        public generateB() {}

        @Override
        public void run() {
            synchronized (theRunner) {
                for (int i = 0; i < input; i++) {
                    while (theRunner.state != 2) {
                        try {
                            theRunner.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print('b');
                    theRunner.state = 3;
                    theRunner.notifyAll();
                }
            }
        }
    }

    static class generateC extends Thread {
        public generateC() {}

        @Override
        public void run() {
            synchronized (theRunner) {
                for (int i = 0; i < input; i++) {
                    while (theRunner.state != 3) {
                        try {
                            theRunner.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print('c');
                    theRunner.state = 1;
                    theRunner.notifyAll();
                }
            }
        }
    }
}
