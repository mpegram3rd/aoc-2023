import utils.Accumulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader garbIn = new BufferedReader(new FileReader(args[0]));
            String line = garbIn.readLine();
            Accumulator acc = new Accumulator();

            while (line != null) {
                acc.parseLine(line);
                line = garbIn.readLine();
            }
            garbIn.close();

            acc.optimize();
            acc.solution1();
            System.out.println();
            acc.solution2();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}