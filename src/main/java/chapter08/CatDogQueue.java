package chapter08;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <h2>猫狗队列</h2>
 */
public class CatDogQueue {

    private final Queue<int[]> catQueue;
    private final Queue<int[]> dogQueue;

    public CatDogQueue() {
        this.catQueue = new LinkedList<>();
        this.dogQueue = new LinkedList<>();
    }

    public void enqueue(int[] animal){
        if (animal[1] == 0)
            catQueue.offer(animal);
        if (animal[1] == 1)
            dogQueue.offer(animal);
    }

    public int[] dequeueAny(){
        if (catQueue.isEmpty())
            return dequeueDog();
        if (dogQueue.isEmpty())
            return dequeueCat();

        return catQueue.peek()[0] < dogQueue.peek()[0] ? catQueue.poll(): dogQueue.poll();
    }

    public int[] dequeueCat(){
        return catQueue.isEmpty() ? new int[]{-1, -1}: catQueue.poll();
    }

    public int[] dequeueDog(){
        return dogQueue.isEmpty() ? new int[]{-1, -1}: dogQueue.poll();
    }
}
