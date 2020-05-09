import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;

public class Encoder implements Callable {
    HashMap<Character,String> codeMap;
    String code;
    HuffmanNode rootNode;
    public Encoder(HuffmanNode rootNode,String code,HashMap<Character,String> codeMap){
        this.rootNode = rootNode;
        this.code = code;
        this.codeMap = codeMap;
        System.out.println("new");
    }

    @Override
    public Object call() throws Exception {
        long startingTime = System.nanoTime();

        if(rootNode.right.letter == rootNode.letter){
            code ="1" + code;

            FutureTask ft = new FutureTask(new Encoder(rootNode.right,code,codeMap));
            Thread t = new Thread(ft);
            t.start();

            startingTime += (long)ft.get();
            //lookForLeaf(rootNode.right,code,codeMap);
        }else{
            code ="1" + code;
            codeMap.put(rootNode.right.letter,code);
//            System.out.println(rootNode.right.letter+" "+ code);
        }

        if(rootNode.left.letter == rootNode.letter){
            code ="0" + code;

            FutureTask ft = new FutureTask(new Encoder(rootNode.left,code,codeMap));
            Thread t = new Thread(ft);

            t.start();

            startingTime += (long)ft.get();
            //lookForLeaf(rootNode.right,code,codeMap);
        }else{
            code ="0" + code;
            codeMap.put(rootNode.left.letter,code);
//            System.out.println(rootNode.left.letter+" "+ code);
        }

        return System.nanoTime() - startingTime;

    }


}
