package aAlgorithm;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainAAlgorithm {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        writeInResult();
        //optionalMain();
    }

    private static void writeInResult() throws FileNotFoundException {
        AAlgorithm alg = new AAlgorithm(2);
        alg.algorithm();
        System.out.println(alg.toString());
        alg.writeInFile("result.txt");
    }

    private static void optionalMain() {
        int numberOfTests = 250, numberOfLevels = 9;
        List<List<Integer>> lengths = new ArrayList<List<Integer>>();
        List<Double> ratioOfFoundSolutionPerLevel = new ArrayList<>();
        for(int i = 0; i < numberOfLevels; ++i) {
            int numberOfBoards = 0;
            int boardNumber = 0;
            //System.out.println("Porcentaje of obstacles level " + i);
            List<Integer> length = new ArrayList<>();
            while(numberOfBoards < numberOfTests) {
                try {
                    AAlgorithm alg = new AAlgorithm(i);
                    ++numberOfBoards;
                    int lengthPath = alg.algorithm();
                    if(lengthPath > 0) {
                        System.out.println(alg.toString());
                    }
                    //System.out.println("Length of the path was " + lengthPath);
                    length.add(lengthPath);
                    if(alg.getPathFound() == false) {
                        ++boardNumber;
                    }
                } catch (Exception e) {
                    //System.err.println(e.getMessage());
                }
            }
            lengths.add(length);
            ratioOfFoundSolutionPerLevel.add((double) boardNumber/numberOfTests);
        }
        System.out.println();
        for(int k = 0; k < numberOfLevels; ++k) {
            //System.out.print("[ ");
            double average = 0;
            int numOfValidTests = 0;
            for(int i = 0; i < numberOfTests; ++i) {
                if(lengths.get(k).get(i) != 0) {
                    average += lengths.get(k).get(i);
                    ++numOfValidTests;
                }
                //System.out.print(lengths.get(k).get(i) + "   ");
            }
            average /= numOfValidTests;
            //System.out.print("] ");
            DecimalFormat df = new DecimalFormat("#.##");
            String resultado = df.format(100-ratioOfFoundSolutionPerLevel.get(k)*100);
            System.out.println(resultado + "% of the tests with " +
                    "a " + 10*(k+1) + "% of obstacles found an optimal path solution");
            System.out.println("Average length of the optimal path with " + 10*(k+1) + "% of obstacles --> " + average + "\n");
        }
    }
}
