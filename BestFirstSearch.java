
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * @author Ethan Stern-Ellis
 */

public class BestFirstSearch {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        PriorityQueue p = new PriorityQueue();

        try{
            File graphFile = new File(args[0]);

            Scanner myReader = new Scanner(graphFile);

            String firstNode = myReader.nextLine();

            HashMap<String,Integer> nodeScore = new HashMap<>();
            HashMap<String,String> nodeAdjacentNodes = new HashMap<>();

            HashSet<String> explored = new HashSet<>();

            //load data into hash maps

            while(myReader.hasNextLine()){

                String data = myReader.nextLine();

                String[] arrOfData = data.split("\t");



                nodeScore.put(arrOfData[0], parseInt(arrOfData[1]));
                if(arrOfData.length==3){
                    nodeAdjacentNodes.put(arrOfData[0], arrOfData[2]);
                }

            }

            //explore the first node

            p.insert(firstNode, nodeScore.get(firstNode));

            String removed = (String) p.remove();

            System.out.println("Exploring node "+ removed);

            if(nodeScore.get(removed)==0){

                System.out.println("GOAL");
                System.exit(0);

            }

            explored.add(removed);

            //add first node's adjacent nodes

            String nodesAdjacents = nodeAdjacentNodes.get(removed);

            String[] splitFirstAdjacents = nodesAdjacents.split(",");

            int numNodes = splitFirstAdjacents.length;

            System.out.println("Adding nodes: ");

            for (int i = 0; i < numNodes; i++) {
                p.insert(splitFirstAdjacents[i],nodeScore.get(splitFirstAdjacents[i]));
                System.out.println(splitFirstAdjacents[i]);
            }

            boolean isGoal = false;
            boolean isEmpty = false;

            //explore the rest of the nodes

            while(!isGoal) {
                removed = (String) p.remove();
                if (!explored.contains(removed)) {


                    System.out.println("Exploring node " + removed);

                    //if node is a goal, notify user and print remaining items

                    if (nodeScore.get(removed) == 0) {

                        System.out.println("GOAL");

                        System.out.println("Upon termination, there were "+ p.getSize() + " nodes ready to explore: ");

                        int size = p.getSize();

                        while (size>0){

                            System.out.println(p.remove());
                            size = p.getSize();

                        }
                        isGoal = true;

                    }

                    //add node to explored

                    else {
                        explored.add(removed);

                        nodesAdjacents = nodeAdjacentNodes.get(removed);

                        String[] splitNodesAdjacents = nodesAdjacents.split(",");

                        numNodes = splitNodesAdjacents.length;

                        //add node's adjacent nodes to queue

                        System.out.println("Adding nodes: ");

                        for (int i = 0; i < numNodes; i++) {
                            if (!explored.contains(splitNodesAdjacents[i])) {
                                p.insert(splitNodesAdjacents[i], nodeScore.get(splitNodesAdjacents[i]));
                                System.out.println(splitNodesAdjacents[i]);
                            }
                        }
                    }

                }
            }

        }

        //user entered incorrect file name

        catch(FileNotFoundException e){

            System.out.println("I'm sorry, you did not enter a correct file name");

        }













    }

}
