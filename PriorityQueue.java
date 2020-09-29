/**
 * @author Ethan Stern-Ellis
 *
 * This is a implementation of a priority queue. It uses an implicit tree structure that allows adding removing items to be logarithmic in time complexity.
 *
 */

@SuppressWarnings("unchecked")
public class PriorityQueue<P> {

    private int size; //num elements in the queue
    private P[] objectQ; //stores the priority queue objects
    private int[] scoreQ; //stores the priority queue score

    private final int INIT_QUEUE_SIZE = 15; //initial queue size


    /**
     * A priority queue constructor
     */
    public PriorityQueue(){

        size = 0;

        objectQ = (P[])  new Object[INIT_QUEUE_SIZE];

        scoreQ = new int[INIT_QUEUE_SIZE];

    }

    /**
     * This method checks the length of the queue compared to the num elements and does a resize if the are equal. If the queue is empty, it adds the object score pair
     * to the top of the queue. Otherwise, it adds the object score pair to the end and swims the pair up to its proper location. Size is increased by one at the end.
     *
     * @param object the generalized object to be added to the queue
     * @param score  the score orders
     */
    public void insert(P object, int score){


        //resize if needed

        if(size==scoreQ.length){

            resize();

        }

        //check for inserting at the head of the queue

        if(isEmpty()){

            objectQ[0] = object;
            scoreQ[0] = score;

        }

        //add object score pair to the bottom and swim to proper location

        else{

            objectQ[size] = object;
            scoreQ[size] = score;

            swim(size);

        }

        size++;


    }

    /**
     *
     * @return returns true if empty, false if not.
     */
    public boolean isEmpty(){

        return size == 0;

    }

    /**
     * Swaps the top object score pair from the queue with the bottom object score pair, removing the top item and sinking the bottom item to keep balance.
     * @return the object removed, which is used as a key in the main method.
     */
    public P remove(){

        P removed = objectQ[0];

        swap(0,size-1);

        size--;

        sink(0);

        return removed;

    }

    /**
     *
     * @return num elements in queue
     */
    public int getSize(){

        return size;

    }

    /**
     * reduces size to zero so any inserts will copy over existing elements
     */
    public void clear(){


        size = 0;


    }

    private void swim(int index){

        //swim until at correct location

        while((scoreQ[(index-1)/2]>scoreQ[index]) && index > 0){

            swap((index-1)/2,index);

            index=(index-1)/2;

        }


    }

    private void sink(int index){

        //Sink while index has children

        while(index<(size/2)){

            //go down left if left child is smaller than right or there is no right child

            if(2*index+2>=size || scoreQ[(2*index)+1]<=scoreQ[(2*index)+2]){

                if(scoreQ[index] > scoreQ[(2*index)+1]){

                    swap(index,((index*2)+1));

                    index = (index*2)+1;

                }

                else{

                    index = size;

                }

            }

            //go down right side

            else if (scoreQ[(2*index)+1]>scoreQ[(2*index)+2]){

                if(scoreQ[index] > scoreQ[(2*index)+2]){

                    swap(index,((index*2)+2));

                    index = (index*2)+2;

                }

                else{

                    index = size;

                }
            }

        }

    }


    private void resize(){

        //copy items from objectQ to larger array

        P[] resizeO = (P[]) new Object[objectQ.length * 2];

        for (int i = 0; i < objectQ.length; i++) {
            resizeO[i] = objectQ[i];
        }

        objectQ = resizeO;

        //copy items from scoreQ to larger array

        int[] resizeS = new int[scoreQ.length * 2];

        for (int i = 0; i < scoreQ.length; i++) {
            resizeS[i] = scoreQ[i];
        }

        scoreQ = resizeS;

    }

    private void swap(int parent, int child){

        //swap scoreQ parent with child

        int saveScore = scoreQ[parent];

        scoreQ[parent] = scoreQ[child];
        scoreQ[child] = saveScore;

        //swap objectQ parent with child

        P saveObject = objectQ[parent];

        objectQ[parent] = objectQ[child];
        objectQ[child] = saveObject;

    }

}
