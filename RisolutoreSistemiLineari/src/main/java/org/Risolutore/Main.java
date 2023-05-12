package org.Risolutore;

import org.ejml.simple.SimpleMatrix;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        double tol;

        //Initializing
        String inputFilePath ="D:/Download/Dati Progetto 1 Bis-20230418/testing.mtx";
        SimpleMatrix inputMatrix = Utils.loadSimpleMatrixFromFile(inputFilePath);
        new SimpleMatrix(inputMatrix.getNumRows(), 1);
        SimpleMatrix solverSolution;
        SimpleMatrix exactSolution = new SimpleMatrix(inputMatrix.getNumRows(), 1);
        SimpleMatrix startingSolution = new SimpleMatrix(inputMatrix.getNumRows(), 1);

        for (int i = 0; i < exactSolution.getNumRows(); i++) {
            exactSolution.set(i, 0, 1);
            startingSolution.set(i, 0, 0);
        }

        SimpleMatrix rightHandSide = inputMatrix.mult(exactSolution);

        //Other testing
        inputMatrix.print();
        JacobiSolver.getPMatrix(inputMatrix).print();
        JacobiSolver.getInvertedPMatrix(inputMatrix).print();
        JacobiSolver.getNMatrix(inputMatrix).print();


        //Testing the solver
        /*
        long startTime = System.nanoTime();
        System.out.println("Starting now");

        solverSolution = GaussSeidelSolver.solve(inputMatrix, rightHandSide, 20000, 0.0000000001);

        long endTime = System.nanoTime();

        System.out.println("Solutions found in " + (endTime - startTime)/1000000000 + " seconds");
        System.out.println("Solver");
        solverSolution.transpose().print();
        System.out.println("Exact");
        exactSolution.transpose().print();
         */

    }
}