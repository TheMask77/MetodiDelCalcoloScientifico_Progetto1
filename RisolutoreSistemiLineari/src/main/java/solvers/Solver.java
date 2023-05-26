package solvers;

import org.ejml.simple.SimpleMatrix;

public abstract class Solver {
    public abstract SimpleMatrix solve(SimpleMatrix A, SimpleMatrix b, int maxIterations, double tolerance);
    public static boolean isConverged(SimpleMatrix x, SimpleMatrix b, SimpleMatrix A, double tolerance) {
        SimpleMatrix numerator = A.mult(x).minus(b);
        Double numeratorNorm = numerator.normF();
        Double denominatorNorm = b.normF();
        return (numeratorNorm / denominatorNorm) < tolerance;
    }
}
