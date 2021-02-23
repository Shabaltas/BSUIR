package by.mo.bsuir;

import java.util.Arrays;
import java.util.function.Function;

import static java.lang.Math.sqrt;

public class Resolver {

    private static final double EPSILA = 0.0000001d;

    private int numberOfIterations;

    public int getNumberOfIterations() { return this.numberOfIterations; }

    public double[] CoordinateDescent(Function<double[], Double> func, double[] initArgs, int maxNumberOfIterations) {
        if (func == null || initArgs == null || initArgs.length == 0 || maxNumberOfIterations < 1)
            throw new IllegalArgumentException();
        double[]  oldArgs;
        int size = initArgs.length;
        for (numberOfIterations = 0; numberOfIterations < maxNumberOfIterations; numberOfIterations++) {
            System.out.println("Iteration " + (numberOfIterations+1));
            oldArgs = Arrays.copyOf(initArgs, 5);
            for (int i = 0; i < size; i++) {
                optimize(initArgs, func, i, 100, 300, 50);
                for (int j = 0; j < size; j++) {
                    System.out.println("q[" + (j + 1) + "] = " + initArgs[j]);
                }
                System.out.println("L = " + func.apply(initArgs) + "\n");
            }

            double s = 0;
            for (int j = 0; j < size; j++) {
                s += (oldArgs[j] - initArgs[j])*(oldArgs[j] - initArgs[j]);
            }
            if (sqrt(s) < EPSILA || Math.abs(func.apply(initArgs) - func.apply(oldArgs)) < EPSILA)
                return initArgs;
        }
        return initArgs;
    }

    private void optimize(double[] x, Function<double[], Double> func, int p, double a, double b, int n) {
        int i;
        double s1;
        double s2;
        double u1;
        double u2;
        double fu1;
        double fu2;

        s1 = (3 - sqrt(5d)) / 2;
        s2 = (sqrt(5d) - 1) / 2;
        u1 = a + s1 * (b - a);
        u2 = a + s2 * (b - a);
        x[p] = u1;
        fu1 = func.apply(x);
        x[p] = u2;
        fu2 = func.apply(x);

        for (i = 1; i <= n; i++)
        {
            if (fu1 <= fu2)
            {
                b = u2;
                u2 = u1;
                fu2 = fu1;
                u1 = a + s1 * (b - a);
                x[p] = u1;
                fu1 = func.apply(x);
            }
            else
            {
                a = u1;
                u1 = u2;
                fu1 = fu2;
                u2 = a + s2 * (b - a);
                x[p] = u2;
                fu2 = func.apply(x);
            }
        }
        x[p] = u1;
        fu1 = func.apply(x);
        x[p] = u2;
        fu2 = func.apply(x);

        if (fu1 < fu2)
            x[p] = u1;
        else
            x[p] = u2;
    }
}
