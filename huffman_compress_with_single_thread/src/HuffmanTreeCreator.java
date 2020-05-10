import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;

public class HuffmanTreeCreator implements Callable {
    PriorityBlockingQueue<HuffmanNode> nodeQueue;
    public HuffmanTreeCreator(PriorityBlockingQueue<HuffmanNode> nodeQueue){
        this.nodeQueue = nodeQueue;
    }

    public class resultAndTimeUsed{
        long startingTime;
        long endTime;
        HuffmanNode rootNode;
        public resultAndTimeUsed(HuffmanNode rootNode, long startingTime, long endTime){
            this.rootNode = rootNode;
            this.startingTime = startingTime;
            this.endTime = endTime;
        }
    }
    @Override
    public resultAndTimeUsed call() throws Exception {
        long startingTime = System.nanoTime();
        HuffmanNode rootNode = null;
        while (nodeQueue.size() > 1){
            HuffmanNode node1 = nodeQueue.take();
            HuffmanNode node2 = nodeQueue.take();
            int newFrequency = node1.frequency + node2.frequency;
            rootNode = new HuffmanNode(newFrequency,node1,node2);
            nodeQueue.put( rootNode );
        }
        return new resultAndTimeUsed(rootNode,startingTime,System.nanoTime());
    }
}
