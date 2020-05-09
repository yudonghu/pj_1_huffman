
import java.util.HashMap;
        import java.util.concurrent.Callable;
        import java.util.concurrent.Future;
        import java.util.concurrent.FutureTask;
        import java.util.concurrent.PriorityBlockingQueue;

public class codeMapCreatorNoThread {
    HashMap<Character,String> codeMap;
    public codeMapCreatorNoThread(HashMap<Character,String> codeMap){
        this.codeMap = codeMap;
    }

    public HashMap<Character,String> createCodeMap(HuffmanNode rootNode,String code) {

        if(rootNode.right != null){
            createCodeMap(rootNode.right,code+"0");
            System.out.println("right");
        }

        if(rootNode.left!= null){
            createCodeMap(rootNode.left,code+"1");
            System.out.println("left");
        }
        if(rootNode.left == null && rootNode.right == null){
            codeMap.put(rootNode.letter,code);
            System.out.println("what");
        }
        codeMap.forEach((key, value) -> System.out.println(key + ":" + value));
        return codeMap;
    }


}
