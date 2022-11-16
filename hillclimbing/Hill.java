package hillclimbing;
import java.util.LinkedList;
import java.util.Random;

public class Hill {
  int iterationsNumber; // max number of interations
  
  public int[] hillClimbing(int [][] matrix, int iterationsNumber, int citiesQty) {
    this.iterationsNumber = iterationsNumber;
    int t = 0; // counter
    boolean local;
    int evalCurrentPoint = 0;
    LinkedList<int []> neighbors = null;
    int [] bestNeighborsPoint = null;
    int [] bestPointOfAll = null;
    int [] currentPoint = null;
    
    while(t < iterationsNumber) {
      local = false;
      // select a random point
      currentPoint = selectRandomPoint(citiesQty);
      // Now this point is the best point of all
      bestPointOfAll = currentPoint;
      // eval the current point
      evalCurrentPoint = aval(currentPoint, matrix);
      
      // I look for the best point in the neighborhood
      while(local == false) {
        // create the neighborhood
        neighbors = createNeighborhood(currentPoint);
        // select the best point in the neighborhood
        while(!neighbors.isEmpty()) {
          int [] x = neighbors.pop();
          int [] y = neighbors.pop();
          
          if(!x.equals(null) || !x.equals(null)) {
            if(aval(x, matrix) < aval(y, matrix)) {
              bestNeighborsPoint = x;
            } else {
              bestNeighborsPoint = y;
            }
          }
        }
        // evaluating if we found a better point than the current point
        if(aval(bestNeighborsPoint, matrix) < aval(currentPoint, matrix)) {
          currentPoint = bestNeighborsPoint;
        } else {
          local = true;
        }
      }
      t++;
      // uptade the best point
      if(evalCurrentPoint < aval(bestPointOfAll, matrix)) {
        bestPointOfAll = currentPoint;
      }
    }
    return bestPointOfAll;
  }

  // compute the point evaluation function
  public int aval(int [] point, int [][] matrixAdj) {
    int cost = 0;
    for(int i = 0; i < point.length; i++) {
        cost = cost + matrixAdj[point[i]][point[i]];                          
    }
    return cost;
  }

  // create the neighborhood with 2-swap techniche
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
  
  public int[] swap(int currentPoint[], int i, int j) {
    int aux = currentPoint[i];
    currentPoint[i] = currentPoint[j];
    currentPoint[j] = aux;
    return currentPoint;	
  }

  // select a random point in the search space
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
