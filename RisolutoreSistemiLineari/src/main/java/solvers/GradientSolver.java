package solvers;

import org.ejml.simple.SimpleMatrix;

public class GradientSolver extends Solver {
    public SimpleMatrix solve(SimpleMatrix A, SimpleMatrix b, int maxIterations, double tolerance){

        double a;
        double c;
        SimpleMatrix r;
        SimpleMatrix y;
        SimpleMatrix div;
        SimpleMatrix x = new SimpleMatrix(A.getNumCols(), 1);
        for(int iteration = 0; iteration < maxIterations; iteration++){
            r = b.minus(A.mult(x));
            y = A.mult(r);
            a = r.transpose().mult(r).get(0, 0);
            c = r.transpose().mult(y).get(0, 0);
            div = new SimpleMatrix(1, 1);
            div.set(0, 0, a/c);
            x = x.plus(r.mult(div));

            if (isConverged(x, b, A, tolerance)) {
                System.out.println("GRADIENT METHOD CONVERGED IN " + (iteration+1) + " ITERATIONS");
                return x;
            }

        }
        System.out.println("GRADIENT METHOD DID NOT CONVERGE AFTER " + maxIterations + " ITERATIONS");
        return x;
    }
}
