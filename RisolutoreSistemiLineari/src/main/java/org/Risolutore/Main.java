package org.Risolutore;

import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.DMatrixSparseTriplet;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.mult.MatrixMatrixMult_DDRM;
import org.ejml.simple.SimpleMatrix;
import org.ejml.sparse.csc.CommonOps_DSCC;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        double tol;

        String inputFilePath ="D:/Download/Dati Progetto 1 Bis-20230418/spa1.mtx";

        DMatrixSparseCSC inputMatrix = Utils.loadMatrixFromFile(inputFilePath);

        DMatrixSparseCSC exactSolution = new DMatrixSparseCSC(inputMatrix.getNumRows(), 1);
        for (int i = 0; i < exactSolution.getNumRows(); i++)
            exactSolution.set(i, 0, 1);

        DMatrixSparseCSC rightHandSide = new DMatrixSparseCSC(exactSolution.getNumRows(), 1);
        CommonOps_DSCC.mult(inputMatrix, exactSolution, rightHandSide);
        System.out.println("Mult result, non zeros = " + rightHandSide.getNonZeroLength());
        rightHandSide.print();
        //rightHandSide.print();
        //MatrixMatrixMult_DDRM.mult_reorder(inputMatrix, exactSolution, rightHandSide);

    }
}