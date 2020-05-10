import java.util.HashMap;
import java.util.concurrent.Callable;

public class CodeMapCreator implements Callable {
    HuffmanNode rootNode;
    HashMap<Character,String> codeMap;
    public CodeMapCreator(HuffmanNode rootNode){
        this.rootNode = rootNode;
        this.codeMap = new HashMap<>();
    }

    public class resultAndTimeUsed{
        long startingTime;
        long endTime;
        HashMap<Character,String> codeMap;
        public resultAndTimeUsed(HashMap<Character,String> codeMap,long startingTime,long endTime){
            this.codeMap =codeMap;
            this.startingTime = startingTime;
            this.endTime = endTime;
        }
    }


    @Override
    public resultAndTimeUsed call() throws Exception {
        long startingTime = System.nanoTime();
        findLeaves(rootNode,"");
        return new resultAndTimeUsed(codeMap,startingTime,System.nanoTime());
    }

    public HashMap<Character,String> findLeaves(HuffmanNode rootNode,String code){
        if(rootNode.right != null){
            findLeaves(rootNode.right,code+"0");
        }
        if(rootNode.left!= null){
            findLeaves(rootNode.left,code+"1");
        }
        if(rootNode.left == null && rootNode.right == null){
            codeMap.put(rootNode.letter,code);
        }
        return codeMap;
    }
}
