package main;

import solvers.ConjugateGradientSolver;
import solvers.GaussSeidelSolver;
import solvers.GradientSolver;
import solvers.Solver;
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
        SimpleMatrix exactSolutionTest = null;
        SimpleMatrix rightHandSideTest = null;
        SimpleMatrix startingSolutionTest = null;
        SimpleMatrix solverSolutionTest = null;
        Solver solver = null;
        String filePath = "";

        //User Interaction
        solver = MethodSelection();
        filePath = FileSelection();

        //Matrix Setup
        inputMatrixTest = Utils.loadSimpleMatrixFromFile(filePath);
        exactSolutionTest = new SimpleMatrix(inputMatrixTest.getNumRows(), 1);
        startingSolutionTest = new SimpleMatrix(inputMatrixTest.getNumRows(), 1);
        for (int i = 0; i < inputMatrixTest.getNumRows(); i++) {
            exactSolutionTest.set(i, 0, 1);
            startingSolutionTest.set(i, 0, 0);
        }
        rightHandSideTest = inputMatrixTest.mult(exactSolutionTest);

        //Solution Calculation
        startTime = System.nanoTime();
        System.out.println("Starting now...");
        solverSolutionTest = solver.solve(inputMatrixTest, rightHandSideTest, 20000, 0.00001);
        endTime = System.nanoTime();
        System.out.println("Solutions found in " + (endTime - startTime)/1000000000 + " seconds");
        System.out.println("Solver");
        solverSolutionTest.transpose().print();
        System.out.println("Exact");
        exactSolutionTest.transpose().print();
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
            case 1:
            case 2: return new GaussSeidelSolver();
            case 3: return new GradientSolver();
            case 4: return new ConjugateGradientSolver();
        }
        return null;
    }
}