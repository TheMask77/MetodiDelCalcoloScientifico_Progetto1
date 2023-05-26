package main;

import solvers.*;
import org.ejml.simple.SimpleMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static int answer;
    private static String question;
    private static final Scanner keyboard = new Scanner(System.in);
    private static final String defaultFilePath ="src/files/";

    public static void main(String[] args) throws FileNotFoundException {

        //Variables Declaration
        long startTime;
        long endTime;
        SimpleMatrix inputMatrixTest;
        SimpleMatrix exactSolution = null;
        SimpleMatrix rightHandSide = null;
        SimpleMatrix startingSolution = null;
        SimpleMatrix solverSolution = null;
        Solver solver = null;
        String filePath = "";

        //User Interaction
        solver = MethodSelection();
        filePath = FileSelection();

        //Matrix Setup
        inputMatrixTest = Utils.loadSimpleMatrixFromFile(filePath);
        exactSolution = new SimpleMatrix(inputMatrixTest.getNumRows(), 1);
        startingSolution = new SimpleMatrix(inputMatrixTest.getNumRows(), 1);
        for (int i = 0; i < inputMatrixTest.getNumRows(); i++) {
            exactSolution.set(i, 0, 1);
            startingSolution.set(i, 0, 0);
        }
        rightHandSide = inputMatrixTest.mult(exactSolution);

        //Solution Calculation
        startTime = System.nanoTime();
        System.out.println("Starting now...");
        solverSolution = solver.solve(inputMatrixTest, rightHandSide, 20000, 0.00001);
        endTime = System.nanoTime();
        System.out.println("Solutions found in " + (endTime - startTime)/1000000000 + " seconds");
        System.out.println("Relative error ==> " + Utils.evaluateRelativeError(exactSolution, solverSolution));
        System.out.println("Solver");
        solverSolution.transpose().print();
        System.out.println("Exact");
        exactSolution.transpose().print();
    }

    private static String FileSelection() {
        //File Selection
        File folder = new File(defaultFilePath);
        File[] listOfFiles = folder.listFiles();
        question = "Selezionare una delle seguenti matrici: ";
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                question += "\n" + (i + 1) + ") " + listOfFiles[i].getName();
            }
        }
        question += "\n" + (listOfFiles.length + 1) + ") Tutti i file";
        do{
            System.out.println(question);
            answer = keyboard.nextInt();
        }while(answer < 1 || answer > listOfFiles.length + 1);

        return defaultFilePath + listOfFiles[answer - 1].getName();

        //TODO implementation of all files choice
    }

    public static Solver MethodSelection(){
        do {
            question = "Quale metodo risolutivo vuoi applicare?";
            question += "\n1) Metodo di Jacobi";
            question += "\n2) Metodo di Gauss-Seidel";
            question += "\n3) Metodo del gradiente";
            question += "\n4) Metodo del gradiente coniugato";
            System.out.println(question);
            answer = keyboard.nextInt();
        }while(answer < 1 || answer > 4);

        //Solver Inizialization
        switch(answer){
            case 1: return new JacobiSolver();
            case 2: return new GaussSeidelSolver();
            case 3: return new GradientSolver();
            case 4: return new ConjugateGradientSolver();
        }
        return null;
    }
}