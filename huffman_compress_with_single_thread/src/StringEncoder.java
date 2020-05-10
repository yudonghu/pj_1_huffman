import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class StringEncoder implements Callable {
    HashMap<Character,String> codeMap;
    String fileString;
    AtomicInteger currentIndex;
    public StringEncoder(HashMap<Character,String> codeMap,String fileString,AtomicInteger currentIndex){
        this.codeMap = codeMap;
        this.fileString = fileString;
        this.currentIndex = currentIndex;
    }

    public class resultAndTimeUsed{
        long startingTime;
        long endTime;
        String bytesString;
        public resultAndTimeUsed(String bytesString,long startingTime,long endTime){
            this.bytesString = bytesString;
            this.startingTime = startingTime;
            this.endTime = endTime;
        }
    }
    @Override
    public resultAndTimeUsed call() throws Exception {
        String bytesString = "";
        long startingTime = System.nanoTime();
        while(currentIndex.get() < fileString.length()){
            char currentLetter =  fileString.charAt(currentIndex.get());
            bytesString+=codeMap.get(currentLetter);
//            System.out.println(finalString);
//            System.out.println(codeMap.get(currentLetter));
            currentIndex.incrementAndGet();
        }
        return new resultAndTimeUsed(bytesString,startingTime,System.nanoTime());
    }
}
