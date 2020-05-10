import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

public class HuffmanFileReader implements Callable {
    File inputFile;
    public HuffmanFileReader(File inputFile){
        this.inputFile = inputFile;
    }

    class resultAndTimeUsed{
        String fileString;
        long startingTime;
        long endTime;
        resultAndTimeUsed(String fileString,long startingTime,long endTime)
        {
            this.fileString = fileString;
            this.startingTime = startingTime;
            this.endTime = endTime;
        }

    }

    @Override
    public resultAndTimeUsed call() throws IOException {
        long startingTime = System.nanoTime();
        if(this.inputFile.isFile()){
            FileInputStream fis = new FileInputStream(this.inputFile);
            byte[] buf = new byte[1024];
            StringBuffer sb = new StringBuffer();
            int len = 0;
            while((len = fis.read(buf))!=-1){
                sb.append(new String(buf,0,len,"utf-8"));
            }
            return new resultAndTimeUsed(sb.toString(),startingTime,System.nanoTime());
        }else{
            System.out.println("File doesn't exist!");
        }
        return null;
    }
}
