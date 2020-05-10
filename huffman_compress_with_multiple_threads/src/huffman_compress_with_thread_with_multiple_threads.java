import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparingInt;

public class huffman_compress_with_thread_with_multiple_threads implements Callable {


    @Override
    public Object call() throws Exception {

        int runningTimes = 20;


        long creating_tree_used = 0;
        long writing_File_used = 0;
        long total_time_used = 0;

        for(int i = 0;i<runningTimes;i++){

            //=======================read file
            HuffmanFileReader fileReader = new HuffmanFileReader(new File("C:\\Users\\HydenLuc\\Desktop\\Constitution.txt"));
            FutureTask fileToStringTask = new FutureTask(fileReader);
            new Thread(fileToStringTask).start();

            while(!(fileToStringTask.isDone())){
                //System.out.println("Work Not Done");
            }
            HuffmanFileReader.resultAndTimeUsed fileReaderResult = ( HuffmanFileReader.resultAndTimeUsed) fileToStringTask.get();
            String fileString = fileReaderResult.fileString;

            //==================================Freq Map
            FreqMapCreator freqMapCreator = new FreqMapCreator(fileString);
            FutureTask freqMapTask = new FutureTask(freqMapCreator);
            new Thread(freqMapTask).start();
            while (!(freqMapTask.isDone())){
//            System.out.println("Work Not Done");
            }
            FreqMapCreator.resultAndTimeUsed freqMapResult = ( FreqMapCreator.resultAndTimeUsed) freqMapTask.get();
            HashMap<Character,Integer> freqMap = freqMapResult.freqMap;
//        freqMap.forEach((key, value) -> System.out.println(key + ":" + value));

            //==================================Node Map
            PriorityBlockingQueue<HuffmanNode> nodeQueue= new PriorityBlockingQueue<>(freqMap.size(), Comparator.comparingInt(o -> o.frequency));
            ;
            FutureTask nodeQueueTask = new FutureTask(new NodeQueueCreator(freqMap,nodeQueue));
            new Thread(nodeQueueTask).start();
//            while (!(nodeQueueTask.isDone())){
////            System.out.println("Work Not Done");
//            }
            NodeQueueCreator.resultAndTimeUsed nodeQueueResult = ( NodeQueueCreator.resultAndTimeUsed) nodeQueueTask.get();


            //=============================create Tree
            HuffmanTreeCreator huffmanTreeCreator = new HuffmanTreeCreator(nodeQueue);
            FutureTask rootFinderTask = new FutureTask(huffmanTreeCreator);
            new Thread(rootFinderTask).start();
            while (!(rootFinderTask.isDone())){
//            System.out.println("Work Not Done");
            }
            HuffmanTreeCreator.resultAndTimeUsed rootNodeResult = ( HuffmanTreeCreator.resultAndTimeUsed) rootFinderTask.get();
            HuffmanNode rootNode = rootNodeResult.rootNode;
            //===============================get huffman code
            CodeMapCreator codeMapCreator = new CodeMapCreator(rootNode);
            FutureTask codeMapTask = new FutureTask(codeMapCreator);
            new Thread(codeMapTask).start();
            while(!(codeMapTask.isDone())){
//            System.out.println("Work Not Done");
            }
            CodeMapCreator.resultAndTimeUsed codeMapResult = ( CodeMapCreator.resultAndTimeUsed) codeMapTask.get();
            HashMap<Character,String > codeMap = codeMapResult.codeMap;
//        codeMap.forEach((key, value) -> System.out.println(key + ":" + value));

            //=====================encode char string in to binaryStr string
            AtomicInteger workingIndex = new AtomicInteger(0);
            StringEncoder stringEncoder = new StringEncoder(codeMap,fileString,workingIndex);
            FutureTask stringTransferTask = new FutureTask(stringEncoder);
            new Thread(stringTransferTask).start();
            while(!(codeMapTask.isDone())){
//            System.out.println("Work Not Done");
            }
            StringEncoder.resultAndTimeUsed bytesStringResult = ( StringEncoder.resultAndTimeUsed) stringTransferTask.get();
            String finalBinaryStr = bytesStringResult.bytesString;


            //=======================convert codeString into bytesQueue
            int queueSize = finalBinaryStr.length()/8+1;
            ArrayBlockingQueue<Byte> bytesQueue=   new ArrayBlockingQueue<Byte>(queueSize);
            BytesStringToBytesQueueConvert charStringToBytesQueueConvert = new BytesStringToBytesQueueConvert(finalBinaryStr,bytesQueue);
            FutureTask bytesQueueTask = new FutureTask(charStringToBytesQueueConvert);
            new Thread( bytesQueueTask).start();


//
//            while (!bytesQueueTask.isDone()){
//         //       System.out.println("Still working");
//
//            }


            HuffmanFileWriter fileWriter = new HuffmanFileWriter(bytesQueue,new File("C:\\Users\\HydenLuc\\Desktop\\threadTest.txt"));
            FutureTask writeFileTask = new FutureTask(fileWriter);
            new Thread(writeFileTask).start();
            while(!(writeFileTask.isDone())){
                // System.out.println("Still working");
            }

            BytesStringToBytesQueueConvert.resultAndTimeUsed byteQueueResult = ( BytesStringToBytesQueueConvert.resultAndTimeUsed) bytesQueueTask.get();
            HuffmanFileWriter.resultAndTimeUsed writingResult= (HuffmanFileWriter.resultAndTimeUsed) writeFileTask.get();


            //     System.out.println("Creating tree used(in milli-sec):");
            //     System.out.println("Creating tree used(in milli-sec):"+(nodeQueueResult.endTime));

//
//            System.out.println("Creating tree used(in milli-sec):"+(rootNodeResult.endTime-nodeQueueResult.startingTime)/1_000_000);
//
//            System.out.println("Writing File used(in milli-sec):"+(writingResult.endTime-byteQueueResult.startingTime)/1_000_000);
//            System.out.println("Total time used(in milli-sec):"+(writingResult.endTime-bytesStringResult.startingTime )/1_000_000);



            creating_tree_used += (rootNodeResult.endTime-nodeQueueResult.startingTime);
            writing_File_used += (writingResult.endTime-byteQueueResult.startingTime);
            total_time_used += (writingResult.endTime-bytesStringResult.startingTime );



        }
            System.out.println("Running for "+runningTimes+" times, The average performance:");
            System.out.println("Creating tree used(in nano-sec):"+(creating_tree_used)/runningTimes);
            System.out.println("Writing File used(in nano-sec):"+(writing_File_used)/runningTimes);
            System.out.println("Total time used(in nano-sec):"+(total_time_used)/runningTimes);

        return null;



    }
    public static void main(String []args) throws ExecutionException, InterruptedException {
        huffman_compress_with_thread_with_multiple_threads mainThread = new huffman_compress_with_thread_with_multiple_threads();
        FutureTask ft = new FutureTask(mainThread);
        new Thread(ft).start();

    }
}
