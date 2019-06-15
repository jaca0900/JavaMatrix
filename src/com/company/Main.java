package com.company;

import com.company.components.matrix.Matrix;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Matrix matrix = new Matrix(3, 3);

        try {
            matrix.loadFromFile("Matrix.txt");
            System.out.println(matrix.toString());
            matrix.writeMatrixToFile("Matrix.txt");
            matrix.writeResToFile("Results.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
