import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;

public class RootFinder implements Callable {

    PriorityBlockingQueue<HuffmanNode> nodeQueue;
    public RootFinder(PriorityBlockingQueue<HuffmanNode> priorityQueue){
        this.nodeQueue = priorityQueue;
    }

    @Override
    public Object call() throws Exception {

        HuffmanNode rootNode = null;
        while (!(nodeQueue.isEmpty())){
            HuffmanNode node1 = nodeQueue.take();
            HuffmanNode node2 = nodeQueue.take();
            int newFrequency = node1.frequency + node2.frequency;
            rootNode = new HuffmanNode(newFrequency,node1,node2);
            nodeQueue.put( rootNode );
//            System.out.println(rootNode.frequency);
            if(nodeQueue.size() == 1){
                break;
            }
        }


        return rootNode;
    }
}
