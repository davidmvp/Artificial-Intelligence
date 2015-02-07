import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;


public class dataProcessorForMonkData {
    public static void main(String[] args) throws Exception {
        
        
        FileInputStream in = null;
        Scanner input = new Scanner(System.in);
        System.out.println("Please type in the data set you want to process.");
        String filename = input.nextLine();
       
        
        File inputFile = new File(filename);
        in = new FileInputStream(inputFile);
        filename = filename + "_Processed.txt";
        FileWriter fstream = new FileWriter(filename);
        BufferedWriter out = new BufferedWriter(fstream);
        BufferedReader bin = new BufferedReader(new InputStreamReader(in) );
        String line = "";
        while (true) {
            line = bin.readLine();
            if (line == null) {
                break;
            }
            String str = "";
            StringTokenizer tokenizer = new StringTokenizer(line);
            for (int i = 0; i < 7 ; i++) {
                
                 str = str + " " + tokenizer.nextToken();
              
     
                 
            }
            str = str + "\n";
            out.write(str);
            
        }
        out.close();
        
        
    }
}
