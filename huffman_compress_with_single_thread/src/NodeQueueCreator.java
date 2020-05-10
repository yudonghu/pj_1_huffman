import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;

public class NodeQueueCreator implements Callable {
    HashMap<Character,Integer> freqMap;
    PriorityBlockingQueue<HuffmanNode> nodeQueue;
    public NodeQueueCreator(HashMap<Character,Integer> freqMap,PriorityBlockingQueue<HuffmanNode> nodeQueue){
        this.freqMap =freqMap;
        this.nodeQueue = nodeQueue;
    }
    public class resultAndTimeUsed{
        long startingTime;
        long endTime;
        public resultAndTimeUsed(long startingTime,long endTime){
            this.startingTime = startingTime;
            this.endTime = endTime;
        }
    }
    @Override
    public resultAndTimeUsed call() throws Exception {
        long startingTime = System.nanoTime();

        freqMap.forEach((letter, frequency) -> nodeQueue.add(new HuffmanNode(letter,frequency)));

        return new resultAndTimeUsed(startingTime,System.nanoTime());

    }
}
