package hillclimbing;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static boolean validInput(String[] args) {
		if (args.length < 2) {
			if (args[0].isEmpty()) {
				System.out.println("Enter the number of vertices in the graph.");
				return false; 
			}	else if (args[1].isEmpty()) {
				System.out.println("Enter input file path.");
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws FileNotFoundException {
		boolean argsIsValid = Main.validInput(args);
		// if args is not a valid input
		if(!argsIsValid) {
			return;
		}

		int vertexQty; // number of vertexs 
		int[][] adjMatrix; // ajacency matrix that represents the cities graph
		int [] route = null; // rout found by the hillclimb algorithm
		int iterationsNumber;
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
			System.out.println("Input File: " + args[0]);
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("[\\s,]+"); // delimitador das linhas generico

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
			System.out.println("\n\n");
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
