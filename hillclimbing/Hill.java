package hillclimbing;
import java.util.LinkedList;
import java.util.Random;

public class Hill {
  // Stores the number of iterations of Hill Climbing algorithm
  int iterationsNumber;
  
  /** 
   * This method compares through a loop all points in the neighborhood in search of the best.
   * When all points in the neighborhood are checked (when the list is empty), we return the
   * best point found. To compare the points I use the cost function defined by the aval() method.

   * @param adjMatrix This matrix represents the cities graph
   * @param iterationsNumber number of iterations of Hill Climbing algorithm
   * @param citiesQty number of cities represented by the graph
   * @return int[] This returns represents the best route found.
   */
  public int[] hillClimbing(int [][] adjMatrix, int iterationsNumber, int citiesQty) {
    this.iterationsNumber = iterationsNumber;
    int t = 0; // counter, counts the interactions of do hillclimbing
    boolean local; // used to exit the internal loop. If it has found a better point in
                   // the neighborhood than a given point previously chosen
    LinkedList<int []> neighbors = null;
    int [] bestNeighborsPoint = null;
    int [] bestPointOfAll = null;
    int [] currentPoint = null;
    
    while(t < iterationsNumber) {
      local = false;
      currentPoint = selectRandomPoint(citiesQty);
      bestPointOfAll = currentPoint; // now this point is the best point of all
      
      // look for the best point in the neighborhood
      while(local == false) {
        neighbors = createNeighborhood(currentPoint);
        
        // select the best point in the neighborhood
        while(!neighbors.isEmpty()) {
          int [] pointX = neighbors.pop();
          int [] pointY = neighbors.pop();
          
          if(!pointX.equals(null) || !pointX.equals(null)) {
            if(aval(pointX, adjMatrix) < aval(pointY, adjMatrix)) {
              bestNeighborsPoint = pointX;
            } else {
              bestNeighborsPoint = pointY;
            }
          }
        }


        /* Checks if the bestNeighborsPoint is better than the currentPoint previously defined.
           In positive case, the bestNeighborsPoint becomes the new current point. If it isn't, it
           means we won't find better spots in that neighborhood, so we set the local variable as
           true, so we can explore another neighborhood in search of better spots. */
        if(aval(bestNeighborsPoint, adjMatrix) < aval(currentPoint, adjMatrix)) {
          currentPoint = bestNeighborsPoint;
        } else {
          local = true;
        }
      }
      t++;

      // uptade the bestPointOfAll
      if(aval(currentPoint, adjMatrix) < aval(bestPointOfAll, adjMatrix)) {
        bestPointOfAll = currentPoint;
      }
    }
    return bestPointOfAll;
  }


  /**
   * This function works as follows: knowing that each point in the search space is a route,
   * the sum of the weights of the edges (distance) between the vertices (cities) of a route
   * is calculated and stored in the cost variable. This cost performing the role of an evaluation
   * function, since each path has a different cost. 
   * 
   * @param point This param represents the point that will have its cost calculated
   * @param matrixAdj This matrix represents the cities graph
   * @return int The return is computed cost
   */
  public int aval(int [] point, int [][] matrixAdj) {
    int cost = 0;
    for(int i = 0; i < point.length; i++) {
        cost = cost + matrixAdj[point[i]][point[i]];                          
    }
    return cost;
  }

  // create the neighborhood with 2-swap techniche

  /**
   * This function receives a point and calculates the neighborhood for this point using the
   * 2-opt swap technique. To implement this technique, two loops are used, fixing the first
   * element of a route and swapping positions with the next ones. After swap the first element
   * with all the following, we fix the second, and we repeat these steps until all elements
   * have been exchanged. This ensures that all possible combinations of neighbors are generated.
   *  
   * @param currentPoint This param represent a point
   * @return LinkedList<int []> Returns a linked list with neighborhood
   */
  public LinkedList<int []> createNeighborhood(int []  currentPoint) {
    int i, j;
    LinkedList<int []> neighborhood = new LinkedList();
    for(i = 0; i < currentPoint.length; i++) {
      for(j = i+1; j < currentPoint.length; j++) {
        neighborhood.add(swap(currentPoint, i, j));
      }
    }
    return neighborhood;
  }
  
  /**
   * This method performs a swap of array elements. 
   * 
   * @param currentPoint Array that represents a point that will have its elements swapped
   * @param i First Array index
   * @param j Second Array index
   * @return int[] Array updated with values swapped
   * 
   */
  private int[] swap(int currentPoint[], int i, int j) {
    int aux = currentPoint[i];
    currentPoint[i] = currentPoint[j];
    currentPoint[j] = aux;
    return currentPoint;	
  }

  /**
   * This function generates a random route in the search space. Its operation consists of
   * generating random values ​​between 0 and n, where n is the number of cities. When generating
   * a random number, that number is added to a Linked List, just to be able to  verify whether
   * or not the next number generated was added to that list because LinkedList library implements
   * the contains() method, allowing you to check the existence of duplicate numbers. The numbers
   * only stop being generated and inserted when this list has the exact number of numbers to
   * represent the number of cities described in the parameter. Then I use a loop to transfer all
   * the numbers generated stored inside the list to a vector of integers which is the structure
   * used to store the search space points.
   * 
   * @param citiesQty
   * @return int[] Returns if array that represent a route, a point in space search
   */
  public int[] selectRandomPoint(int citiesQty) {
    LinkedList<Integer> cities = new LinkedList<Integer>();   
    int [] citiesArr = new int[citiesQty];
    int aux = 0;
    Random randomGenerator = new Random();
    while (cities.size() < citiesQty) {
        int random = randomGenerator.nextInt(citiesQty);
        if (!cities.contains(random)) {
          cities.add(random);
        }
    }
    
    while(!cities.isEmpty() && aux < citiesArr.length) {
      citiesArr[aux] = cities.pop();
      aux++;
    }
    return citiesArr;
  }
}