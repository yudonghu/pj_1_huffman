import java.io.File;
        import java.io.FileOutputStream;
        import java.util.concurrent.ArrayBlockingQueue;
        import java.util.concurrent.Callable;

public class HuffmanFileWriter implements Callable {
    File outputFile;
    ArrayBlockingQueue bytesQueue;
    public HuffmanFileWriter(ArrayBlockingQueue bytesQueue, File outputFile){
        this.bytesQueue = bytesQueue;
        this.outputFile = outputFile;
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

        FileOutputStream fos = new FileOutputStream(outputFile);
        while(!(bytesQueue.isEmpty())){
            byte tempB = (byte)bytesQueue.take();
            fos.write(tempB);

        }

//        fos.write((byte)125);
    //    HuffmanFileWriter
        fos.flush();
        fos.close();
      //  fos.

        return new resultAndTimeUsed(startingTime,System.nanoTime());
    }
}
