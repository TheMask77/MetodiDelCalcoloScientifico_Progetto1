package org.Risolutore;

import org.ejml.simple.SimpleMatrix;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        double tol[] = {0.0001, 0.000001, 0.00000001, 0.0000000001};
        String filePaths[] = new String[]{"spa1", "spa2", "vem1", "vem2"};
        String inputFilePath;
        SimpleMatrix inputMatrix;
        SimpleMatrix solverSolution;
        SimpleMatrix exactSolution;
        SimpleMatrix startingSolution;



        for (int tols = 0; tols < tol.length; tols++)
            for (int paths = 0; paths < filePaths.length; paths++) {
                //Initializing
                inputFilePath = "D:/Download/Dati Progetto 1 Bis-20230418/" + filePaths[paths] + ".mtx";
                inputMatrix = Utils.loadSimpleMatrixFromFile(inputFilePath);
                exactSolution = new SimpleMatrix(inputMatrix.getNumRows(), 1);
                startingSolution = new SimpleMatrix(inputMatrix.getNumRows(), 1);

                for (int i = 0; i < exactSolution.getNumRows(); i++) {
                    exactSolution.set(i, 0, 1);
                    startingSolution.set(i, 0, 0);
                }

                SimpleMatrix rightHandSide = inputMatrix.mult(exactSolution);

                //Other testing

                //Testing the solver
                long startTime = System.nanoTime();
                System.out.println("Starting now with " + filePaths[paths] + "  with tol = " + tol[tols]);

                solverSolution = GaussSeidelSolver.solve(inputMatrix, rightHandSide, 20000, tol[tols]);

                long endTime = System.nanoTime();

                System.out.println("Solutions found in " + (endTime - startTime) / 1000000000 + " seconds");
                System.out.println("Relative error ==> " + Utils.evaluateRelativeError(exactSolution, solverSolution));
                System.out.println("========================================================================");
            }

    }
}