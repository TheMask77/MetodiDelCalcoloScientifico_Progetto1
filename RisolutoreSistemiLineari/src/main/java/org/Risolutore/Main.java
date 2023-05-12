package org.Risolutore;

import org.ejml.simple.SimpleMatrix;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        double tol;

        String inputFilePath ="D:/Download/Dati Progetto 1 Bis-20230418/vem2.mtx";
        SimpleMatrix inputMatrixTest = Utils.loadSimpleMatrixFromFile(inputFilePath);
        new SimpleMatrix(inputMatrixTest.getNumRows(), 1);
        SimpleMatrix solverSolutionTest;
        SimpleMatrix exactSolutionTest = new SimpleMatrix(inputMatrixTest.getNumRows(), 1);
        SimpleMatrix startingSolutionTest = new SimpleMatrix(inputMatrixTest.getNumRows(), 1);

        for (int i = 0; i < exactSolutionTest.getNumRows(); i++) {
            exactSolutionTest.set(i, 0, 1);
            startingSolutionTest.set(i, 0, 0);
        }

        SimpleMatrix rightHandSideTest = inputMatrixTest.mult(exactSolutionTest);

        long startTime = System.nanoTime();
        System.out.println("Starting now");

        solverSolutionTest = GaussSeidelSolver.solve(inputMatrixTest, rightHandSideTest, 20000, 0.00001);

        long endTime = System.nanoTime();

        System.out.println("Solutions found in " + (endTime - startTime)/1000000000 + " seconds");
        System.out.println("Solver");
        solverSolutionTest.transpose().print();
        System.out.println("Exact");
        exactSolutionTest.transpose().print();



//====================================================================================================

/*
        DMatrixSparseCSC inputMatrix = Utils.loadMatrixFromFile(inputFilePath);

        DMatrixSparseCSC exactSolution = new DMatrixSparseCSC(inputMatrix.getNumRows(), 1);
        DMatrixSparseCSC startingSolution = new DMatrixSparseCSC(inputMatrix.getNumRows(), 1);
        for (int i = 0; i < exactSolution.getNumRows(); i++) {
            exactSolution.set(i, 0, 1);
            startingSolution.set(i, 0, 0);
        }

        DMatrixSparseCSC rightHandSide = new DMatrixSparseCSC(inputMatrix.getNumRows(), 1);
        CommonOps_DSCC.mult(inputMatrix, exactSolution, rightHandSide);

        Solvers solvers = new Solvers(inputMatrix, startingSolution, rightHandSide, 0.0);
        DMatrixSparseCSC solverSolution = solvers.gaussSeidel();

        System.out.println("Solutions with my solver: ");
        System.out.println("Solver");
        CommonOps_DSCC.transpose(solverSolution, null,null).print();
        System.out.println("Exact");
        CommonOps_DSCC.transpose(exactSolution, null,null).print();

 */

    }
}