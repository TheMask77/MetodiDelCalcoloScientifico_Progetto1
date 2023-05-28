package main;

import org.ejml.equation.MatrixConstructor;
import solvers.*;
import org.ejml.simple.SimpleMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    private static int answer;
    private static File[] listOfFiles;
    private static String question;
    private static final Scanner keyboard = new Scanner(System.in);
    private static final String defaultFilePath ="src/files/";

    public static void main(String[] args) throws FileNotFoundException {

        //Variables Declaration
        double tolerance = 0;
        int fileNumber = 0;
        Solver solver = null;
        String filePath = "";

        //User Interaction
        solver = MethodSelection();
        tolerance = ToleranceSelection();
        filePath = FileSelection();

        //Checks for option all files
        try{
            if(Integer.parseInt(filePath) == listOfFiles.length + 1)  fileNumber = listOfFiles.length;
        }catch(NumberFormatException e){
            MatrixEvaluation(solver, filePath, tolerance);
        }
        for(int i = 0; i < fileNumber; i++){
            if (listOfFiles[i].isFile()) {
                filePath = defaultFilePath + listOfFiles[i].getName();
                MatrixEvaluation(solver, filePath, tolerance);
            }
        }
    }

    private static void MatrixEvaluation(Solver solver, String filePath, double tolerance) {

        int maxIterations = 20000;
        double startTime;
        double endTime;
        SimpleMatrix inputMatrixTest;
        SimpleMatrix exactSolution = null;
        SimpleMatrix rightHandSide = null;
        SimpleMatrix startingSolution = null;
        SimpleMatrix solverSolution = null;

        System.out.println("\n\n========================= PROCEDURE STARTUP FOR " + filePath.split("/")[2] + " =========================");

        //Matrix Setup
        inputMatrixTest = Utils.loadSimpleMatrixFromFile(filePath);
        exactSolution = new SimpleMatrix(inputMatrixTest.getNumRows(), 1);
        startingSolution = new SimpleMatrix(inputMatrixTest.getNumRows(), 1);
        for (int j = 0; j < inputMatrixTest.getNumRows(); j++) {
            exactSolution.set(j, 0, 1);
            startingSolution.set(j, 0, 0);
        }
        rightHandSide = inputMatrixTest.mult(exactSolution);

        //Solution Calculation
        startTime = System.nanoTime();
        solverSolution = solver.solve(inputMatrixTest, rightHandSide, maxIterations, tolerance);
        endTime = System.nanoTime();
        System.out.println("TIME ELAPSED: " + new DecimalFormat("#####.##").format((endTime - startTime)/1000000000) + " s");
        System.out.println("RELATIVE ERROR: " + Utils.evaluateRelativeError(exactSolution, solverSolution));
        System.out.println("TOLERANCE: " + tolerance);

        /*System.out.println("Solver");
        solverSolution.transpose().print();
        System.out.println("Exact");
        exactSolution.transpose().print();*/
    }

    private static double ToleranceSelection(){

        double[] tolerances = {0.0001, 0.000001, 0.00000001, 0.0000000001};

        question = "Select a tolerance value: ";
        for(int i = 0; i < tolerances.length; i++){
            question += "\n" + (i + 1) + ") " + tolerances[i];
        }
        do{
            System.out.println(question);
            answer = keyboard.nextInt();
        }while(answer < 1 || answer > tolerances.length);

        return tolerances[answer - 1];
    }

    private static String FileSelection() {
        //File Selection
        File folder = new File(defaultFilePath);
        listOfFiles = folder.listFiles();
        question = "Select one of the following matrices: ";
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                question += "\n" + (i + 1) + ") " + listOfFiles[i].getName();
            }
        }
        question += "\n" + (listOfFiles.length + 1) + ") All the above";
        do{
            System.out.println(question);
            answer = keyboard.nextInt();
        }while(answer < 1 || answer > listOfFiles.length + 1);

        if(answer == listOfFiles.length + 1){
            return String.valueOf(listOfFiles.length + 1);
        }
        else{
            return defaultFilePath + listOfFiles[answer - 1].getName();
        }
    }

    public static Solver MethodSelection(){
        do {
            question = "Which method do you want to use?";
            question += "\n1) Jacobi method";
            question += "\n2) Gauss-Seidel method";
            question += "\n3) Gradient method";
            question += "\n4) Conjugate gradient method";
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