package by.mo.bsuir;

import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Resolver resolver = new Resolver();
        Function<double[], Double> func25 = x ->
                1350 * 70 / x[0] + 1210 * 65 / x[1] + 1150 * 80 / x[2] + 1300 * 77 / x[3] + 890 * 93 / x[4]
                + 0.5*(11 * x[0] + 9 * x[1] + 3 * x[2] + 7 * x[3] + 6 * x[4]);
        Function<double[], Double> func28 = x ->
                3500/x[0]+ 7.5*x[0] + 1000/x[1] + 2*x[1] + 10000/x[2]+ 5*x[2] + 450/x[3] + x[3] + 3200/x[4] + 10*x[4];

        double[] result = resolver.CoordinateDescent(func25, new double[]{1, 1, 1, 1, 1}, 20);
        System.out.println("Result L = " + func25.apply(result));
        for (double v : result) {
            System.out.println(v);
        }
        System.out.println("took " + (resolver.getNumberOfIterations() + 1) + " iterations");

    }
}
