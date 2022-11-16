package hillclimbing;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws FileNotFoundException {
    // check if args are valid
    if (args.length < 2) {
      if (args[0].isEmpty()) {
        System.out.println("Enter the number of vertices in the graph.");
        return; 
      }	else if (args[1].isEmpty()) {
        System.out.println("Enter a valid input file path.");
        return;
      }
    }

    int vertexQty; // number of vertexs 
    int[][] adjMatrix; // ajacency matrix that represents the cities graph
    int [] route = null; // rout found by the hillclimb algorithm
    int iterationsNumber; // number of iterations of Hill Climbing algorithm
    try {
      iterationsNumber = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      System.out.println("The first arg is not a number");
      return;
    }
    
    // Read a .txt file that represents the cities graph
    File file = new File(args[0]);
    
    // Check if is a valid file
    if(file.exists() && file.isFile() && file.canRead()) {
      System.out.println("\n\nInput File: " + args[0]);
      Scanner scanner = new Scanner(file);
      scanner.useDelimiter("[\\s,]+"); // generic line delimiter

      // The first line of the file defines the number of vertices of the graph
      vertexQty = scanner.nextInt();
      adjMatrix = new int[vertexQty][vertexQty];

      // Adding the values ​​read from file into the matrix structure
      for(int i=0; i < vertexQty; i++) {
          for (int j=0; j < vertexQty; j++) {
            adjMatrix[i][j] = scanner.nextInt();
          }
      }
      scanner.close();
      
      // Hill Climbing
      Hill hill = new Hill();
      route = hill.hillClimbing(adjMatrix, iterationsNumber, vertexQty);
      System.out.println("Number of Vertexs: " + vertexQty);
      System.out.println("Number of iterations: " + iterationsNumber);
      System.out.println("Solution Found - route: " + Arrays.toString(route));
      System.out.println("Solution Found - valor: " + hill.aval(route, adjMatrix));
    } else {
      System.out.println("File not found!");
      return;
    }
  }
}
