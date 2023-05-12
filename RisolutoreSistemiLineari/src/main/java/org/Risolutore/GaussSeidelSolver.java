package org.Risolutore;

import org.ejml.simple.SimpleMatrix;

public class GaussSeidelSolver {

    public static SimpleMatrix solve(SimpleMatrix inputMatrix, SimpleMatrix rightHandSide, int maxIterations, double tolerance) {
        int n = inputMatrix.getNumCols();
        SimpleMatrix currentSolution = new SimpleMatrix(n, 1);


        SimpleMatrix pMatrix = getPMatrix(inputMatrix);
        SimpleMatrix nMatrix = getNMatrix(inputMatrix);

        SimpleMatrix invertedPMatrix = pMatrix.invert();
        SimpleMatrix T = invertedPMatrix.mult(nMatrix);

        SimpleMatrix C = invertedPMatrix.mult(rightHandSide);

        for (int iter = 0; iter < maxIterations; iter++) {
            currentSolution = T.mult(currentSolution).plus(C);

            if (converged(currentSolution, rightHandSide, inputMatrix, tolerance)) {
                System.out.println("Gauss-Seidel converged in " + (iter+1) + " iterations");
                return currentSolution;
            }
        }

        System.out.println("Gauss-Seidel did not converge after " + maxIterations + " iterations");
        return currentSolution;
    }

    private static boolean converged(SimpleMatrix currentSolution, SimpleMatrix rightHandSide, SimpleMatrix inputMatrix, double tolerance) {
        SimpleMatrix numerator = inputMatrix.mult(currentSolution).minus(rightHandSide);
        Double numeratorNorm = numerator.normF();
        Double denominatorNorm = rightHandSide.normF();

        return (numeratorNorm / denominatorNorm) < tolerance;
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
