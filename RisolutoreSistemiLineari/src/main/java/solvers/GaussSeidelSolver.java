package solvers;

import org.ejml.simple.SimpleMatrix;

public class GaussSeidelSolver extends Solver {

    public SimpleMatrix solve(SimpleMatrix inputMatrix, SimpleMatrix rightHandSide, int maxIterations, double tolerance) {

        int n = inputMatrix.getNumCols();
        SimpleMatrix currentSolution = new SimpleMatrix(n, 1);
        SimpleMatrix pMatrix = getPMatrix(inputMatrix);
        SimpleMatrix nMatrix = getNMatrix(inputMatrix);
        SimpleMatrix invertedPMatrix = pMatrix.invert();
        SimpleMatrix T = invertedPMatrix.mult(nMatrix);

        SimpleMatrix C = invertedPMatrix.mult(rightHandSide);

        for (int iter = 0; iter < maxIterations; iter++) {
            currentSolution = T.mult(currentSolution).plus(C);

            if (isConverged(currentSolution, rightHandSide, inputMatrix, tolerance)) {
                System.out.println("GAUSS-SEIDEL CONVERGED IN " + (iter+1) + " ITERATIONS");
                return currentSolution;
            }

            currentSolution = T.mult(currentSolution).plus(C);
        }

        System.out.println("GAUSS-SEIDEL DI NOT CONVERGE AFTER " + maxIterations + " ITERATIONS");
        return currentSolution;
    }

    public static SimpleMatrix getPMatrix(SimpleMatrix inputMatrix) {
        SimpleMatrix lowerTriangular = new SimpleMatrix(inputMatrix.getNumRows(), inputMatrix.getNumCols());

        for (int rowIndex = 0; rowIndex < inputMatrix.getNumRows(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < rowIndex + 1; columnIndex++) {
                lowerTriangular.set(rowIndex, columnIndex, inputMatrix.get(rowIndex, columnIndex));
            }
        }

        return lowerTriangular;
    }

    public static SimpleMatrix getNMatrix(SimpleMatrix inputMatrix) {
        SimpleMatrix nMatrix = new SimpleMatrix(inputMatrix.getNumRows(), inputMatrix.getNumCols());

        for (int rowIndex = 0; rowIndex < nMatrix.getNumRows(); rowIndex++)
            for (int columnIndex = rowIndex + 1; columnIndex < nMatrix.getNumCols(); columnIndex++)
                nMatrix.set(rowIndex, columnIndex, -inputMatrix.get(rowIndex, columnIndex));

        return nMatrix;
    }

}
