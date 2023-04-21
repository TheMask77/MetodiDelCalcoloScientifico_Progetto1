package org.Risolutore;

import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.DMatrixSparseTriplet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
    
    public static DMatrixSparseCSC loadMatrixFromFile(String filePath) {

        File inputFile = new File(filePath);
        Scanner inputFileReader = null;
        try {
            inputFileReader = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        DMatrixSparseCSC matrix;
        int rows = inputFileReader.nextInt();
        int columns = inputFileReader.nextInt();
        int nonZeros = inputFileReader.nextInt();
        matrix = new DMatrixSparseCSC(rows, columns, 0);
        
        int itemsAdded = 0;
        int newEntryRowIndex;
        int newEntryColumnIndex;
        double value;

        while (itemsAdded < nonZeros) {
            newEntryRowIndex = inputFileReader.nextInt();
            newEntryColumnIndex = inputFileReader.nextInt();
            value = Double.parseDouble(inputFileReader.next());

            matrix.set((newEntryRowIndex - 1), (newEntryColumnIndex - 1), value);

            itemsAdded++;
        }

        inputFileReader.close();
        
        return matrix;
    }

    public static DMatrixSparseCSC loadMatrixFromFile() {
        String filePath = "";

        Scanner scanner = new Scanner(System.in);

        System.out.print("Insert filepath ==> ");
        filePath = scanner.nextLine();

        return loadMatrixFromFile(filePath);
    }
    
}
