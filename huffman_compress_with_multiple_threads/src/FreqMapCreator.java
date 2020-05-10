import java.util.HashMap;
import java.util.concurrent.Callable;

public class FreqMapCreator implements Callable<FreqMapCreator.resultAndTimeUsed> {
    String fileString;
    public FreqMapCreator(String fileString){
        this.fileString = fileString;
    }


    class resultAndTimeUsed{
        HashMap<Character,Integer> freqMap;
        long startingTime;
        long endTime;
        resultAndTimeUsed(HashMap<Character,Integer> freqMap,long startingTime,long endTime)
        {
            this.freqMap = freqMap;
            this.startingTime = startingTime;
            this.endTime = endTime;
        }

    }


    @Override
    public resultAndTimeUsed call() throws Exception {
        long startingTime = System.nanoTime();

        HashMap freqMap = new HashMap<Character,Integer>();

        for(int i = 0; i < fileString.length();i++){
            char tempChar = fileString.charAt(i);

            if(freqMap.containsKey(tempChar) ){
                freqMap.replace(tempChar,  ( (Integer)freqMap.get(tempChar) +1)  );
            }else{
                freqMap.put(tempChar,1);
            }
        }

        return new resultAndTimeUsed(freqMap,startingTime,System.nanoTime());
    }
}
