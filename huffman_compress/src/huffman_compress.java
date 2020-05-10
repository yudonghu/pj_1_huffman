import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;


public class huffman_compress {
    static int letterNum = 0;
    public static void main(String args[]) throws InterruptedException,  IOException {

        long startingTime = System.nanoTime();
        //put file content to a string
        String tempStr =  convertFileToString(new File("C:\\Users\\HydenLuc\\Desktop\\Constitution.txt"));
//        System.out.println(tempStr);
//        System.out.println(tempStr.length() );


        //get Frequency map
        HashMap tempFreqMap = createFrequencyMap(tempStr);
//        print Frequency map
//        tempMap.forEach((key, value) -> System.out.println(key + ":" + value));
//        tempFreqMap.forEach((key, value) -> System.out.println(key + ":" + value));

        long startingCreatingTree = System.nanoTime();
        //add nodes to Queue
        PriorityBlockingQueue<HuffmanNode> nodesQueue = addNodesToBlockingQueue(tempFreqMap);

        //create huffman tree
        HuffmanNode rootNode = createHuffmanTree(nodesQueue);

        //System.out.println(rootNode.frequency+ " ");
        System.out.println("Creating tree used(in nano-sec) : " + (System.nanoTime() - startingCreatingTree));
        //get codeMap
        HashMap<Character,String> tempCodeMap = huffman_compress.createCodeMap(rootNode,"");

        //compress file
        long startingEncodingFile = System.nanoTime();
        compressFile(tempCodeMap,new File("C:\\Users\\HydenLuc\\Desktop\\Constitution.txt"),new File("C:\\Users\\HydenLuc\\Desktop\\compressFileWithNoThread.txt"));
        System.out.println("Encoding file used(in nano-sec): " + (System.nanoTime() - startingEncodingFile));

        System.out.println("Time used Total(in milli-sec): " + (System.nanoTime() - startingTime)/1_000_000);

//==================================================================================================


    }

    public static void compressFile(HashMap<Character, String> codeMap, File inputFile, File outputFile) throws IOException {

        FileInputStream fis = new FileInputStream(inputFile);
        String wholeFile = convertFileToString(inputFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(outputFile);

        String charInBits = "";

        for(int i = 0; i < wholeFile.length();i++){
            charInBits+=codeMap.get(wholeFile.charAt(i));
        }
        byte[] content = createByteArray(charInBits);
        fos.write(content);

        fos.flush();
        fos.close();
        fis.close();
        bis.close();

    }

    public static byte[] createByteArray(String charInBits) {
        //divide total length
        int size=charInBits.length()/8;
        //get the last digits left
        String destString=charInBits.substring(size*8);
        byte dest[]=destString.getBytes();

        //s for recording each bit
        int s = 0;
        int i = 0;
        int temp = 0;
        // convert string into byte[], easier to transfer binary to decimal
        byte content[] = charInBits.getBytes();
        for (int k = 0; k < content.length; k++) {
            content[k] = (byte) (content[k] - 48);
        }
        //byte[] after transferred
        byte byteContent[];
        if (content.length % 8 == 0) {// if the total length is multiple of 8
            byteContent = new byte[content.length / 8 + 1];
            byteContent[byteContent.length - 1] = 0;// then return the content directly, and add 0 at the end
        } else {
            //else the last bit of byte[] is 0
            byteContent = new byte[content.length / 8 + 2];
            byteContent[byteContent.length - 1] = (byte) (8 - content.length % 8);
        }
        int bytelen=byteContent.length;
        int contentlen=content.length;
        //byteContent is the amount of number of 0 need to add to the end
        while (i < bytelen - 2) {
            for (int j = 0; j < contentlen; j++) {
                if (content[j] == 1) {// if the value of content is 1
                    s =(int)(s + Math.pow(2, (7 - (j - 8 * i))));// get sum
//                   System.out.println(" j= "+j+" i= "+i+" inside= "+(j - 8 * i) +" s = "+s);
                }// if
//                System.out.println(s);
                if ((j+1)%8==0) {// when there is 8 elements

                    byteContent[i] = (byte) s;// put the sum into byteContent
                    i++;
//                    System.out.println("b="+(byte)s);
//                    System.out.println("s="+s);
                    s = 0;// set s to 0
                }// if
            }// for
        }// while
        int destlen=dest.length;
        for(int n=0;n<destlen;n++){
            temp+=(dest[n]-48)*Math.pow(2, 7-n);//get the last second bit
        }
        byteContent[byteContent.length - 2] = (byte) temp;
        return byteContent;
    }

    static HashMap<Character,String> codeMap = new HashMap<>();
    public static HashMap<Character,String> createCodeMap(HuffmanNode rootNode, String code) {

        if(rootNode.right != null){
            createCodeMap(rootNode.right,code+"0");
        }

        if(rootNode.left!= null){
            createCodeMap(rootNode.left,code+"1");
        }
        if(rootNode.left == null && rootNode.right == null){
            codeMap.put(rootNode.letter,code);
            //System.out.println("what");
        }
        //codeMap.forEach((key, value) -> System.out.println(key + ":" + value));
        return codeMap;
    }

    public static HuffmanNode createHuffmanTree(PriorityBlockingQueue<HuffmanNode> frequencyMap) throws InterruptedException {
        HuffmanNode rootNode = null;
        while (frequencyMap.size() > 1){
            HuffmanNode node1 = frequencyMap.take();
            HuffmanNode node2 = frequencyMap.take();
            int newFrequency = node1.frequency + node2.frequency;
            rootNode = new HuffmanNode(newFrequency,node1,node2);
            frequencyMap.put( rootNode );
            //System.out.println(rootNode.frequency);
            //System.out.println(node1.frequency +" "+ node2.frequency +" " + !(nodesQueue.isEmpty()));
//            if(frequencyMap.size() == 1){
//                //System.out.println(rootNode.frequency);
//                //System.out.println(rootNode.letter);
//                break;
//            }
        }
        return rootNode;
    }



    public static PriorityBlockingQueue<HuffmanNode> addNodesToBlockingQueue(HashMap<Character, Integer> letterMap){
        PriorityBlockingQueue<HuffmanNode> queue = new PriorityBlockingQueue<>(letterMap.size(), Comparator.comparingInt(o -> o.frequency));
        letterMap.forEach((letter, frequency) -> queue.add(new HuffmanNode(letter,frequency)));
        return queue;
    }


    public static HashMap<Character,Integer> createFrequencyMap(String content){
        HashMap letterMap = new HashMap<Character,Integer>();
        for(int i = 0; i < content.length();i++){
            char tempChar = content.charAt(i);

            if(letterMap.containsKey(tempChar) ){
                letterMap.replace(tempChar,  ( (Integer)letterMap.get(tempChar) +1)  );
            }else{
                letterMap.put(tempChar,1);
            }
        }

        return letterMap;
    }

    public static String convertFileToString(File file){

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
