package com.company.components.matrix;
import java.io.*;
import java.util.ArrayList;

class Spiral {
    public int index;
    public String row;

    Spiral() {
        index = 0;
        row = "";
    }

    Spiral (Spiral data) {
        this.index = data.index;
        this.row = data.row;
    }
}

public class Matrix {
    private int rowCount;
    private int colCount;
    private ArrayList<ArrayList<Integer>> contents;
    private ArrayList<Integer> evens;
    private ArrayList<Integer> odds;

    public Matrix(int rows, int columns) {
        contents = new ArrayList<>();
        rowCount = rows;
        colCount = columns;

        evens = new ArrayList<>();
        odds = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            contents.add(new ArrayList<>());
            for (int j = 0; j< columns; j++) {
                contents.get(i).add((int) Math.floor(Math.random() * 10));
            }
        }
    }

    private ArrayList<ArrayList<Integer>> createPomArray() {
        ArrayList<ArrayList<Integer>> pom;
        pom = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            pom.add((ArrayList<Integer>) contents.get(i).clone());
        }

        return pom;
    }

    public String readSpiral() {
        ArrayList<ArrayList<Integer>> matrix = this.createPomArray();
        Spiral spiral = new Spiral();

        while(matrix.size() > 0) {
            try {
                spiral = this.writeRow(spiral, matrix, true);
                spiral = this.writeColumn(spiral, matrix, true);
                spiral = this.writeRow(spiral, matrix, false);
                spiral = this.writeColumn(spiral, matrix, false);
            } catch (IndexOutOfBoundsException err) {
                break;
            }
        }

        return spiral.row;
    }

    private Spiral writeRow(Spiral contents, ArrayList<ArrayList<Integer>> matrix, boolean direction) {
        ArrayList<Integer> toWrite = matrix.get(contents.index);
        Spiral content = new Spiral(contents);

        if (direction) {
            for (int i =  0; i < toWrite.size(); i++) {
                content.row += toWrite.get(i) + " ";
                this.evensAndOdds(toWrite.get(i));
                content.index = i;
            }
        } else {
            for (int i = toWrite.size() -1; i >= 0; i--) {
                content.row += toWrite.get(i)  + " ";
                this.evensAndOdds(toWrite.get(i));
                content.index = i;
            }
        }

        matrix.remove(contents.index);
        return content;
    }

    private Spiral writeColumn (Spiral contents, ArrayList<ArrayList<Integer>> matrix, boolean direction) {
        Spiral content = new Spiral(contents);

        if (direction) {
            for (int i =  0; i < matrix.size(); i++) {
                content.row += matrix.get(i).get(contents.index) + " ";
                this.evensAndOdds(matrix.get(i).get(contents.index));
                matrix.get(i).remove(contents.index);
                content.index = i;
            }
        } else {
            for (int i = matrix.size() -1; i >= 0; i--) {
                content.row += matrix.get(i).get(contents.index) + " ";
                this.evensAndOdds(matrix.get(i).get(contents.index));
                matrix.get(i).remove(contents.index);
                content.index = i;
            }
        }

        return content;
    }

    private void evensAndOdds(int value) {
        if (value % 2 == 0) {
            evens.add(value);
        } else {
            odds.add(value);
        }
    }

    private int sumVector(ArrayList<Integer> vector) {
        int sum = 0;

        for (int i = 0; i < vector.size(); i++) {
            sum += vector.get(i);
        }

        return sum;
    }

    @Override
    public String toString() {
        String content = "This is a CMatrix: " + rowCount + " x " + colCount + "\n";

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                content += contents.get(i).get(j) + "\t";
            }

            content += "\n";
        }

        content += "\n\nThe matrix written in a circular mannor going in a spiral clockWise \n";
        content += this.readSpiral() + "\n";
        content += "Evens: \n";
        content += evens + "\n";
        content += "Sum:\n";
        content += sumVector(evens) + "\n";
        content += "Odds: \n";
        content += odds + "\n";
        content += "Sum:\n";
        content += sumVector(odds) + "\n";

        return content;
    }

    public void writeResToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(this.toString());

        writer.close();
    }

    public void writeMatrixToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(rowCount + ";" + colCount + "\n");

        for (int i = 0; i < rowCount; i++) {
            String line = "";
            for (int j = 0; j < colCount; j++) {
                line += contents.get(i).get(j) + ";";
            }
            line += "\n";
            writer.write(line);
        }

        writer.close();
    }

    public void loadFromFile(String FileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(FileName));

        String st;
        while ((st = br.readLine()) != null) {
            String[] loaded = st.split(";");

            if (loaded.length == 2) {
                rowCount = Integer.parseInt(loaded[0]);
                colCount = Integer.parseInt(loaded[1]);
            } else {
                throw(new IOException("WRONG FILE"));
            }

            contents = new ArrayList<>();

            for (int i = 0; i < rowCount; i++) {
                st = br.readLine();
                loaded = st.split(";");
                contents.add(new ArrayList<>());
                for (int j = 0; j < colCount; j++) {
                    contents.get(i).add(Integer.parseInt(loaded[j]));
                }
            }
        }
    }
}