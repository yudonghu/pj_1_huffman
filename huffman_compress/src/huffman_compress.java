
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


public class huffman_compress {
    static int letterNum = 0;
    public static void main(String args[]) throws InterruptedException,  IOException {
        String tempStr =  readFile(new File("C:\\Users\\HydenLuc\\Desktop\\Constitution.txt"));
        //System.out.println(tempStr);

        System.out.println(tempStr.length() );
        //get Frequency map
        HashMap tempMap = letterCounter(tempStr);

        //print Frequency map
//        tempMap.forEach((key, value) -> System.out.println(key + ":" + value));
        tempMap.forEach((key, value) -> System.out.println(key + ":" + value));


        //add node to Queue
        PriorityBlockingQueue<HuffmanNode> nodesQueue = addNodesToBlockingQueue(tempMap);
        HuffmanNode rootNode = null;
        while (!(nodesQueue.isEmpty())){
            HuffmanNode node1 = nodesQueue.take();
            HuffmanNode node2 = nodesQueue.take();
            int newFrequency = node1.frequency + node2.frequency;
            rootNode = new HuffmanNode(newFrequency,node1,node2);
            nodesQueue.put( rootNode );
            //System.out.println(rootNode.frequency);
            //System.out.println(node1.frequency +" "+ node2.frequency +" " + !(nodesQueue.isEmpty()));
            if(nodesQueue.size() == 1){
                //System.out.println(rootNode.frequency);
                //System.out.println(rootNode.letter);
                break;
            }
        }


        //get codeMap
        HashMap<Character,String> codeMap_NoThread = new HashMap<>();

        codeMapCreatorNoThread mapCreator = new codeMapCreatorNoThread(codeMap_NoThread);

        codeMap_NoThread = mapCreator.createCodeMap(rootNode,"");


//        codeMap_NoThread.forEach((key, value) -> System.out.println(key + ":" + value));
        codeMap_NoThread.forEach((key, value) -> System.out.println(value));
        System.out.println(codeMap_NoThread.isEmpty());

        int[] mod = new int[1];
        mod[0] = 5;
        new fileCompressor(codeMap_NoThread,new File("C:\\Users\\HydenLuc\\Desktop\\Constitution.txt"),new File("C:\\Users\\HydenLuc\\Desktop\\newFile.txt"),mod).compress();

       // System.out.println(tempMap.size() +" " +letterNum);


//        while(leftNode.left.letter == rootNode.letter ){
//            leftNode = leftNode.left;
//            System.out.println(leftNode.frequency);
//        }
//
//        System.out.println(leftNode.left.letter +" "+ leftNode.left.frequency);
//
//        HuffmanNode rightNode = rootNode;
//        while(rightNode.right.letter == rootNode.letter ){
//            rightNode = rightNode.right;
//            System.out.println(rightNode.frequency);
//        }
//        System.out.println(rightNode.right.letter +" "+ rightNode.right.frequency);

//        lookForLeaf(rootNode,"0");



//        System.out.println((System.nanoTime()-starting));
//        System.out.println((System.nanoTime()-starting)+ 0*(long)ft.get());
//        System.out.println((System.nanoTime()-starting)+(long)ft.get());
//        System.out.println((System.nanoTime()-starting)*0+(long)ft.get());
//        System.out.println((System.nanoTime()-starting)+(long)ft.get());
//        System.out.println((System.nanoTime()-starting)*0+(long)ft.get());
//        System.out.println((System.nanoTime()-starting)*0+(long)ft.get());


//        while (!(nodesQueue.isEmpty())){
//            HuffmanNode node1 = nodesQueue.take();
//            HuffmanNode node2 = nodesQueue.take();
//            int newFrequency = node1.frequency + node2.frequency;
//            rootNode = new HuffmanNode(newFrequency,node1,node2);
//            nodeQueue.put( rootNode );
//            System.out.println(rootNode.frequency);
//        }


//        nodesQueue.forEach( (HuffmanNode) -> System.out.println(HuffmanNode.toString()));

//        System.out.println(nodesQueue.take().toString());
//        System.out.println(nodesQueue.take().toString());
//        System.out.println(nodesQueue.take().toString());
//        System.out.println(nodesQueue.take().toString());
//        ;

//        nodesQueue.forEach( (HuffmanNode) -> {
//            try {
//                System.out.println(nodesQueue.take().toString());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//===================================================
//        ExecutorService pool = Executors.newFixedThreadPool(1);
//        Future rootNode = pool.submit( new TreeCreator(nodesQueue) );
//
//        pool.shutdown();
//         System.out.println(rootNode.toString());
//        HashMap<Character,String> codeMap = new HashMap<>();
//        FutureTask ft= new FutureTask(new Encoder(rootNode,"0",codeMap));
//        Thread t = new Thread(ft);
//
//        t.start();

    }

    static void lookForLeaf(HuffmanNode rootNode, String code){

        if(rootNode.right.letter == rootNode.letter){
            code ="1" + code;
            lookForLeaf(rootNode.right,code);
        }else{
            code ="1" + code;
            System.out.println(rootNode.right.letter+" "+ code);
            letterNum++;
        }

        if(rootNode.left.letter == rootNode.letter){
            code ="0" + code;
            lookForLeaf(rootNode.left,code);
        }else{
            code ="0" + code;
            System.out.println(rootNode.left.letter+" "+ code);
            letterNum++;
        }


    }




    public static PriorityBlockingQueue<HuffmanNode> addNodesToBlockingQueue(HashMap<Character, Integer> letterMap){
        PriorityBlockingQueue<HuffmanNode> queue = new PriorityBlockingQueue<>(letterMap.size(), Comparator.comparingInt(o -> o.frequency));
        letterMap.forEach((letter, frequency) -> queue.add(new HuffmanNode(letter,frequency)));
        return queue;
    }


    public static HashMap<Character,Integer> letterCounter(String content){
        HashMap letterMap = new HashMap<Character,Integer>();
        for(int i = 0; i < content.length();i++){
            char tempChar = content.charAt(i);

            if(letterMap.containsKey(tempChar) ){
                letterMap.replace(tempChar,  ( (Integer)letterMap.get(tempChar) +1)  );
            }else{
                letterMap.put(tempChar,0);
            }
        }

        return letterMap;
    }

    public static String readFile(File file){

        if(file.isFile()){
            FileInputStream fis = null;
            try{
                fis = new FileInputStream(file);
                byte[] buf = new byte[1024];
                StringBuffer sb = new StringBuffer();
                int len = 0;
                while((len = fis.read(buf))!=-1){
                    sb.append(new String(buf,0,len,"utf-8"));
                }

                return sb.toString();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("File doesn't exist!");
        }
        return null;
    }
}
