package graphvis.group30;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;

class SaturationComparator implements Comparator<Vertex> {
    // this class is used to sort the vertices by their saturation degree

    @Override
    public int compare(Vertex v1, Vertex v2) {
        return Integer.compare(v2.saturationDegree, v1.saturationDegree);
    }
}

class conflictCountComparator implements Comparator<Entry<Vertex, Integer>> {
    // this class is used to sort vertices by the amount of neighbours they have in their colour class

    @Override
    public int compare(Entry<Vertex, Integer> e1, Entry<Vertex, Integer> e2) {
        return Integer.compare(e2.getValue(), e1.getValue());
    }
}

class Configuration{
    /*
     * A class responsible for creating and storing an individual
     * "configuration", which is a partition of k subsets of the
     * set of vertices.
     */

    ArrayList<ArrayList<Vertex>> partition;
    private boolean isSolution = false;
    int k;
    ArrayList<Vertex> vertices;

    /**
     * @param vertices an array of all the vertices belonging to the graph
     * @param k the target chromatic number, i.e. the number of subsets of v in the partition
     */
    public Configuration(ArrayList<Vertex> vertices, int k){
        partition = new ArrayList<>();
        this.k = k;
        this.vertices = vertices;

    }

    public void initDsatur() {
        // sorts V by saturation degree:
        vertices.sort(new SaturationComparator());

        for (int i = 0; i < vertices.size(); i++) { // looping through every vertex in graph
            boolean vertexAssigned = false;
            for (int j = 0; j < k; j++) { // looping through each colour class (each element to be in the partition)
                if (j >= partition.size()) {
                    partition.add(new ArrayList<Vertex>());
                }
                boolean isConflictingSet = false;
                for (int l = 0; l < partition.get(j).size(); l++) { // looping through every vertex in the colour class
                    if (vertices.get(i).neighbours.contains(partition.get(j).get(l))) {
                        isConflictingSet = true;
                        break;
                    }
                }
                if (!isConflictingSet) {
                    partition.get(j).add(vertices.get(i));
                    vertexAssigned = true;
                    break;
                }
            }
            if (!vertexAssigned) { // randomnly assigns remaining vertices
                int rNum = (int) (Math.random() * k);
                partition.get(rNum).add(vertices.get(i));
            }
        }
    }

    public ArrayList<Entry<Vertex, Integer>> getVertexConflictCounts() {
        // this method returns an arraylist of map entries with vertices as keys and their corresponding amount of conflicts (amount of neighbours in same colour class) as values.
        ArrayList<Entry<Vertex, Integer>> conflictCounts = new ArrayList<>();
        for (int i = 0; i < partition.size(); i++) {
            for (int j = 0; j < partition.get(i).size(); j++) {
                int conflicts = 0;
                for (int neighbour = 0; neighbour < partition.get(i).get(j).neighbours.size(); neighbour++) {
                    if (partition.get(i).contains(partition.get(i).get(j).neighbours.get(neighbour))) {
                        conflicts++;
                    }
                }
                Entry<Vertex, Integer> vertexConflictCountPair = new AbstractMap.SimpleEntry<>(partition.get(i).get(j), conflicts);
                conflictCounts.add(vertexConflictCountPair);
            }
        }

        // sort conflict count array:
        conflictCounts.sort(new conflictCountComparator());

        return conflictCounts;
    }

    public int getVertexColourClass(Vertex v) {
        for (int i = 0; i < partition.size(); i++) {
            if (partition.get(i).contains(v)) {
                return i;
            } 
        }
        return 0;
    }

    public int getTotalConflictCount() {
        // this method returns the amount of total conflicts (vertices present in the same colour class as a neighbour) present in the configuration
        int conflictCount = 0;
        for (int i = 0; i < partition.size(); i++) {
            for (int j = 0; j < partition.get(i).size(); j++) {
                for (int neighbour = 0; neighbour < partition.get(i).get(j).neighbours.size(); neighbour++) {
                    if (partition.get(i).contains(partition.get(i).get(j).neighbours.get(neighbour))) {
                        conflictCount++;
                    }
                }
            }
        }
        return conflictCount;
    }


    public ArrayList<ArrayList<Vertex>> removeVertices(ArrayList<Vertex> largestColourClass) {
        // this method returns a new partition equal to the old partition but with all the vertices from the paramater array removed
        ArrayList<ArrayList<Vertex>> newPartition = new ArrayList<>();
        for (int i = 0; i < partition.size(); i++) {
            newPartition.add(new ArrayList<Vertex>());

            for (int j = 0; j < partition.get(i).size(); j++) {
                if (!largestColourClass.contains(partition.get(i).get(j))) {
                    newPartition.get(i).add(partition.get(i).get(j));
                }
            }
        }
        return newPartition;
    }

    @Override
    public String toString() {
        return partition.toString();
    }
    
}


public class HybridGeneticAlgorithm {

    ArrayList<Vertex> v;
    int[][] p;
    int k;

    // parameters for tabu tenure
    int A = 10;
    double alpha = 0.6;

    public HybridGeneticAlgorithm(Vertex[] vertices) {
        this.v = new ArrayList<>();
        // create array of vertices using the information from the edges.
        // Also notes down that the vertices which are connected by the edge are neighbours.
        for (Vertex vertex : vertices) {
            v.add(vertex);
        }
    }

    // this method is the "main" method of the class
    public int upperBound(int kcolours, int max_iterations) {
        this.k = kcolours;
        boolean solutionFound = true;
        Configuration[] population = new Configuration[0];
        // the following loops until no solution is found with the k value, otherwise the k value gets decremented each iteration
        while (solutionFound) {
            population = initPopulation(10, k);
            for (Configuration configuration : population) {
                if (configuration.getTotalConflictCount()==0) {
                    solutionFound = true;
                    k = configuration.partition.size()-1;
                    break;
                } else {
                    solutionFound = false;
                }
            }
            // if initiliazing the population did not find a solution, then perform the genetic operation:
            if (!solutionFound) {
                for (int i = 0; i < max_iterations; i++) {
                    Configuration[] parents = chooseParents(population);
                    Configuration child = crossover_and_replace(parents[0], parents[1], population);
                    int childConflictCount = child.getTotalConflictCount();
                    if (childConflictCount == 0) {
                        k -=1;
                        solutionFound = true;
                        break;
                    }
                }
            }
        }

        return k+1;

    }

    private Configuration crossover_and_replace(Configuration parent1, Configuration parent2, Configuration[] population) {
        // this method is responsible for creating a child from two parent configurations, and then replacing one of the parents with the child after a local search operation on the child
        // messy code because I didn't know how to make deep copies (and because I'm really tired) :(
        Configuration child = new Configuration(v, k);
        Configuration originalParent1 = new Configuration(v, k);
        Configuration originalParent2 = new Configuration(v, k);
        originalParent1.partition = parent1.partition;
        originalParent2.partition = parent2.partition;
        ArrayList<ArrayList<Vertex>> newParent1Partition = new ArrayList<>();
        ArrayList<ArrayList<Vertex>> newParent2Partition = new ArrayList<>();
        for (int index = 0; index < parent1.partition.size(); index++) {
            newParent1Partition.add(new ArrayList<>());
            for (Vertex vertex : parent1.partition.get(index)) {
                newParent1Partition.get(index).add(vertex);
            }
        }
        for (int index = 0; index < parent2.partition.size(); index++) {
            newParent2Partition.add(new ArrayList<>());
            for (Vertex vertex : parent2.partition.get(index)) {
                newParent2Partition.get(index).add(vertex);
            }
        }
        Configuration newParent1 = new Configuration(v, k);
        Configuration newParent2 = new Configuration(v, k);
        newParent1.partition = newParent1Partition;
        newParent2.partition = newParent2Partition;
        for (int l = 0; l < parent1.partition.size(); l++) {
            Configuration currentParent;
            if (l % 2 == 0) { // if l is even
              currentParent = newParent1;
            } else { // if l is odd
              currentParent = newParent2;
            }
            ArrayList<Vertex> largestColourClass = currentParent.partition.get(0);
            for (ArrayList<Vertex> colourClass : currentParent.partition) {
                if (colourClass.size() > largestColourClass.size()) {
                    largestColourClass = colourClass;
                }
            }
            ArrayList<Vertex> newLargestColourClass = new ArrayList<>();
            for (int i = 0; i < largestColourClass.size(); i++) {
                newLargestColourClass.add(largestColourClass.get(i));
            }
            child.partition.add(newLargestColourClass);
            // remove vertices from parents
            newParent1.partition = newParent1.removeVertices(largestColourClass);
            newParent2.partition = newParent2.removeVertices(largestColourClass);
        }

        ArrayList<Vertex> unassignedVertices = new ArrayList<>();
        for (int i = 0; i < newParent1.partition.size(); i++) {
            for (int index = 0; index < newParent1.partition.get(i).size(); index++) {
                if (!unassignedVertices.contains(newParent1.partition.get(i).get(index))) {
                    unassignedVertices.add(newParent1.partition.get(i).get(index));
                }
            }
        }
        for (int i = 0; i < newParent2.partition.size(); i++) {
            for (int index = 0; index < newParent2.partition.get(i).size(); index++) {
                if (!unassignedVertices.contains(newParent2.partition.get(i).get(index))) {
                    unassignedVertices.add(newParent2.partition.get(i).get(index));
                }
            }
        }
        for (Vertex v : unassignedVertices) {
            int rNum = (int) (Math.random() * originalParent1.partition.size());
            child.partition.get(rNum).add(v);
        }

        parent1.getTotalConflictCount();

        // tabu search child
        child = localSearch(child, 2000);
        if (child.getTotalConflictCount() == 0) {
            return child;
        }
        
        // replace worse parent with child:
        int conflictcount1 = originalParent1.getTotalConflictCount();
        int conflictcount2 = originalParent2.getTotalConflictCount();
        if (conflictcount1 > conflictcount2){
            for(int i = 0; i < population.length;i++){
                if(population[i].partition.equals(originalParent1.partition)){
                    population[i] = child;
                    break;
                }}}
        if (conflictcount1 < conflictcount2){
            for(int i = 0; i < population.length;i++){
                if(population[i].partition.equals(originalParent2.partition)){
                    population[i] = child;
                    break;
                }}}
        if (conflictcount1 == conflictcount2){
            for(int i = 0; i < population.length;i++){
                if(population[i].partition.equals(originalParent2.partition)){
                    population[i] = child;
                    break;
                }}}

        return child;
    }

    private Configuration[] initPopulation(int population_size, int k) {
        // this method is responsible for creating the population set
        Configuration[] population = new Configuration[population_size];
        for (int i = 0; i < population_size; i++) {
            Configuration newConfig = new Configuration(v, k);
            newConfig.initDsatur();
            int totalConflictCount = newConfig.getTotalConflictCount();
            if (totalConflictCount != 0) {
                population[i] = newConfig;
            } else {
                population[0] = newConfig;
                break;
            }
        }
        return population;
    }

    private Configuration localSearch(Configuration config, int iterations) {
        // implements the tabu search algorithm

        boolean moveFound = true;
        int tabuTenure = (int)(Math.random() * A) + (int)(alpha * config.getTotalConflictCount());
        ArrayList<Entry<Entry<Vertex, Integer>, Integer>> tabuList = new ArrayList<>();
        while (iterations > 0) {

            // adjust tabu tenure
            iterations--;
            if (moveFound) {
                tabuTenure = (int)(Math.random() * A) + (int)(alpha * config.getTotalConflictCount());
                // adjust tabu list
                for (int i = 0; i < tabuList.size(); i++) {
                    if (tabuList.get(i).getValue() <= 0) {
                        tabuList.remove(i);
                    } else {
                        tabuList.get(i).setValue(tabuList.get(i).getValue()-1);
                    }
                }
            }

            ArrayList<Entry<Vertex, Integer>> vConflictCounts = config.getVertexConflictCounts();

            ArrayList<Entry<Vertex, Integer>> conflictingVertices = new ArrayList<>();

            for (int v = 0; v < vConflictCounts.size(); v++) {
                if (vConflictCounts.get(v).getValue() > 0) {
                    conflictingVertices.add(vConflictCounts.get(v));
                }
            }
            if (conflictingVertices.size() == 0) {
                break;
            }

            // choose a move:

            int rNum = (int)(Math.random()*conflictingVertices.size());
            Entry<Vertex, Integer> randomConflictingEntry = conflictingVertices.get(rNum);

            boolean moveIsTabu = true;

            ArrayList<Integer> visitedClasses = new ArrayList<>();
            while(moveIsTabu && visitedClasses.size() < config.partition.size()-1) {
                Entry<Vertex, Integer> swapEntry = vConflictCounts.get((int)(Math.random()*vConflictCounts.size()));
                while (config.getVertexColourClass(swapEntry.getKey()) == config.getVertexColourClass(randomConflictingEntry.getKey())){
                    swapEntry = vConflictCounts.get((int)(Math.random()*vConflictCounts.size()));
                }

                int colourClass1 = config.getVertexColourClass(randomConflictingEntry.getKey());
                int colourClass2 = config.getVertexColourClass(swapEntry.getKey());
                visitedClasses.add(colourClass2);
                int i1 = config.partition.get(colourClass1).indexOf(randomConflictingEntry.getKey());
                int i2 = config.partition.get(colourClass2).indexOf(swapEntry.getKey());

                // check if move is in tabu list
                Entry move = new AbstractMap.SimpleEntry<Vertex, Integer>(randomConflictingEntry.getKey(), colourClass2);
                Entry tabuMove = new AbstractMap.SimpleEntry<Vertex, Integer>(randomConflictingEntry.getKey(), colourClass1);
                Entry swapMove = new AbstractMap.SimpleEntry<Vertex,Integer>(swapEntry.getKey(), colourClass1);
                Entry tabuSwapMove = new AbstractMap.SimpleEntry<Vertex,Integer>(swapEntry.getKey(), colourClass2);
                moveIsTabu = false;
                for (int i = 0; i < tabuList.size(); i++) {
                    if (tabuList.get(i).getKey().getKey() == move.getKey() && tabuList.get(i).getKey().getValue() == move.getValue()) {
                        moveIsTabu = true;
                        break;
                    } else if (tabuList.get(i).getKey().getKey() == swapMove.getKey() && tabuList.get(i).getKey().getValue() == swapMove.getValue()) {
                        moveIsTabu = true;
                        break;
                    } 
                }
                // make a move
                int conflictCountBeforeMove = config.getTotalConflictCount();
                config.partition.get(colourClass1).set(i1, swapEntry.getKey());
                config.partition.get(colourClass2).set(i2, randomConflictingEntry.getKey());
                int conflictCountAfterMove = config.getTotalConflictCount();
                boolean moveMade = false;
                if (conflictCountAfterMove < conflictCountBeforeMove) {
                    moveMade = true;
                    moveFound = true;
                    
                } else if (!moveIsTabu && conflictCountAfterMove == conflictCountBeforeMove) {
                    moveMade = true;
                    moveFound = true;
                }
                if (!moveMade) {
                    // reverse move
                    config.partition.get(colourClass1).set(i1, randomConflictingEntry.getKey());
                    config.partition.get(colourClass2).set(i2, swapEntry.getKey());
                    moveFound = false;
                } else {
                    tabuList.add(new AbstractMap.SimpleEntry<>(tabuMove, tabuTenure));
                    tabuList.add(new AbstractMap.SimpleEntry<>(move, tabuTenure));
                    tabuList.add(new AbstractMap.SimpleEntry<>(swapMove, tabuTenure));
                    tabuList.add(new AbstractMap.SimpleEntry<>(tabuSwapMove, tabuTenure));
                    break;
                }
            }
        }
        return config;

    }

    private Configuration[] chooseParents(Configuration[] population) {
        // return two elements of population 
        Configuration[] parents = new Configuration[2]; 
        Configuration randomParentOne = population[(int)(Math.random()*population.length)];
        Configuration randomParentTwo = population[(int)(Math.random()*population.length)];
        while (randomParentTwo==randomParentOne) {
        randomParentTwo = population[(int)(Math.random()*population.length)];  
        } 
        parents[0] = randomParentOne; 
        parents[1] = randomParentTwo;
        return parents; 
    }

}
