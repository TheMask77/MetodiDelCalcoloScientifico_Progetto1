package solvers;

import org.ejml.simple.SimpleMatrix;

public class JacobiSolver extends Solver{

    public SimpleMatrix solve(SimpleMatrix inputMatrix, SimpleMatrix rightHandSide, int maxIterations, double tolerance) {
        SimpleMatrix currentSolution = new SimpleMatrix(inputMatrix.getNumRows(), 1);

        SimpleMatrix pMatrix = getPMatrix(inputMatrix);
        SimpleMatrix nMatrix = getNMatrix(inputMatrix);
        SimpleMatrix invertedPMatrix = getInvertedPMatrix(inputMatrix);

        for (int iter = 0; iter < maxIterations; iter++) {
            if (isConverged(currentSolution, rightHandSide, inputMatrix, tolerance)) {
                System.out.println("JACOBI METHOD CONVERGED IN " + iter + " ITERATIONS");
                return currentSolution;
            }

            currentSolution = currentSolution.plus(invertedPMatrix.mult(rightHandSide.minus(inputMatrix.mult(currentSolution))));
        }

        System.out.println("JACOBI METHOD DID NOT CONVERGE AFTER " + maxIterations + " ITERATIONS");
        return currentSolution;
    }

    public static SimpleMatrix getPMatrix(SimpleMatrix inputMatrix) {
        SimpleMatrix pMatrix = new SimpleMatrix(inputMatrix.getNumRows(), inputMatrix.getNumCols());

        for (int i = 0; i < pMatrix.getNumRows(); i++)
            pMatrix.set(i, i, inputMatrix.get(i, i));

        return pMatrix;
    }

    public static SimpleMatrix getInvertedPMatrix(SimpleMatrix inputMatrix) {
        SimpleMatrix invertedPMatrix = new SimpleMatrix(inputMatrix.getNumRows(), inputMatrix.getNumCols());

        for (int i = 0; i < invertedPMatrix.getNumRows(); i++)
            invertedPMatrix.set(i, i, 1/inputMatrix.get(i, i));

        return invertedPMatrix;
    }

    public static SimpleMatrix getNMatrix(SimpleMatrix inputMatrix) {
        SimpleMatrix nMatrix = inputMatrix.copy();

        for (int i = 0; i < nMatrix.getNumRows(); i++)
            nMatrix.set(i, i, 0);

        return nMatrix;
    }

}
