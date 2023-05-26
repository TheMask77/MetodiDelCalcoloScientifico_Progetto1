package solvers;

import org.ejml.simple.SimpleMatrix;

public class ConjugateGradientSolver extends Solver {
    public SimpleMatrix solve(SimpleMatrix A, SimpleMatrix b, int maxIterations, double tolerance){

        SimpleMatrix r;
        SimpleMatrix d;
        SimpleMatrix w;
        SimpleMatrix y;
        SimpleMatrix z;
        SimpleMatrix dr;
        SimpleMatrix dy;
        SimpleMatrix dw;
        SimpleMatrix alpha;
        SimpleMatrix beta;
        SimpleMatrix x = new SimpleMatrix(A.getNumCols(), 1);

        r = b.minus(A.mult(x));
        d = r;
        for(int iteration = 0; iteration < maxIterations; iteration++){
            r = b.minus(A.mult(x));
            y = A.mult(d);
            z = A.mult(r);
            dr = d.transpose().mult(r);
            dy = d.transpose().mult(y);
            alpha = new SimpleMatrix(1, 1);
            alpha.set(0, 0, dr.get(0, 0)/dy.get(0, 0));
            x = x.plus(d.mult(alpha));
            r = b.minus(A.mult(x));
            w = A.mult(r);
            dw = d.transpose().mult(w);
            beta = new SimpleMatrix(1, 1);
            beta.set(0, 0, dw.get(0, 0) / dy.get(0, 0));
            d = r.minus(d.mult(beta));

            if (isConverged(x, b, A, tolerance)) {
                System.out.println("Gradient method converged in " + (iteration+1) + " iterations");
                return x;
            }

        }
        System.out.println("Conjugate Gradient method did not converge after " + maxIterations + " iterations");
        return x;
    }
}
