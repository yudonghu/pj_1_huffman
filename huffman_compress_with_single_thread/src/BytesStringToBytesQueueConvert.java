import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

public class BytesStringToBytesQueueConvert implements Callable {
    String binaryStr;
    ArrayBlockingQueue<Byte> bytesQueue;
    public BytesStringToBytesQueueConvert(String binaryStr, ArrayBlockingQueue<Byte> bytesQueue){
        this.binaryStr = binaryStr;
        this.bytesQueue= bytesQueue;
    }
    class resultAndTimeUsed{
        long startingTime;
        long endTime;
        resultAndTimeUsed(long startingTime,long endTime){
            this.startingTime = startingTime;
            this.endTime = endTime;
        }

    }
    @Override
    public resultAndTimeUsed call() throws Exception {
        long startingTime = System.nanoTime();
        String tempStr = binaryStr;
//        System.out.println(tempStr);
        int tempSum = 0;
        for(int i = 0; i < tempStr.length()-8; i=i+8){
            for(int j = 0;j<8;j++){
              //  System.out.println("got char"=tempStr.charAt(i));
                if(tempStr.charAt(i+j) == '1'){
                    //System.out.println("got one");
                    tempSum = (int)(tempSum + Math.pow(2,7-j));
//                    System.out.println("j="+ j + "single amount= "+Math.pow(2,7-j));
//                    System.out.println("inside = "+tempSum);
                }
//                System.out.println((byte)tempSum);
            }
           // System.out.println(tempSum);
            bytesQueue.add((byte)tempSum);
            tempSum = 0;
        }
      //  System.out.println("workDone");
        return new resultAndTimeUsed(startingTime,System.nanoTime());
    }
}
