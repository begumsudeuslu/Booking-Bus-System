import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BookingSystem {
    
    public static void main(String[] args)   {

        if (args.length==2)  {
             
            File inputFile = new File(args[0]);
            if (inputFile.exists()) {
            
                String[] commentLines = FileInput.readFile(args[0], true, true);
                
                String output = BusSystem.system(commentLines);

                output = output.trim();
                try (FileWriter writer = new FileWriter(args[1]))   {
                    writer.write(output); 
                } catch (IOException e)  {
                    e.printStackTrace();
                } 
            } else  {
                System.out.println("Input file does not exists.");
            }
        } else {
            System.out.println("Please enter the input and output txt, this is wrong.");
        }
    
    } 



} 
