package org.Risolutore;

import org.ejml.simple.SimpleMatrix;

public class JacobiSolver {

    public static SimpleMatrix solve(SimpleMatrix inputMatrix, SimpleMatrix rightHandSide, int maxIterations, double tolerance) {
        SimpleMatrix currentSolution = new SimpleMatrix(inputMatrix.getNumRows(), 1);

        //while()

        return currentSolution;
    }

    private static boolean converged(SimpleMatrix currentSolution, SimpleMatrix rightHandSide, SimpleMatrix inputMatrix, double tolerance) {
        SimpleMatrix numerator = inputMatrix.mult(currentSolution).minus(rightHandSide);
        Double numeratorNorm = numerator.normF();
        Double denominatorNorm = rightHandSide.normF();

        return (numeratorNorm / denominatorNorm) < tolerance;
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
