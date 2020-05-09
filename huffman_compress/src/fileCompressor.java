import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class fileCompressor {
    HashMap<Character,String> codeMap;
    File inputFile;
    File outputFile;

    int[] mod;
    public fileCompressor(HashMap<Character,String> codeMap,File inputFile,File outputFile,int [] mod){
        this.codeMap = codeMap;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.mod = mod;
    }

    public void compress() throws IOException {

        codeMap.forEach((key, value) -> System.out.println(key + ":" + value));

        FileInputStream fis = new FileInputStream(this.inputFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(outputFile);


        String wholeFile = huffman_compress.readFile(inputFile);
        String charInByte = "";

        for(int i = 0; i < wholeFile.length();i++){
            charInByte+=codeMap.get(wholeFile.charAt(i));
        }
//        byte[] newB =  bis.readAllBytes();bis.read
//        for (int j = 0; j < newB.length ;j++){
//
//            charInByte += codeMap.get( (char) newB[j] ) ;
//
//            System.out.println(  (char) newB[j]  );
//
//
//
//            System.out.println("old: " + newB[j]);
//            System.out.println((String) newB[j]);
//            System.out.println("new: " + newB[j]);
//            System.out.println((int) newB[j]);
//
//
//        }

        //fos.write((byte)Long.parseLong(charInByte));
        byte[] content = createByteArray(charInByte);
        int len = content.length;
        //if(len>0){
            fos.write(content);
        //}


//        for(int j = 0 ; j < charInByte.length() ;j++){
//
//            System.out.println(Integer.parseInt(charInByte.substring(0,7)));
//
//            charInByte.replaceAll(charInByte, charInByte.substring(8) );
//
//        }




//
//        while((i = bis.read())!=-1){
//            System.out.println(  );
//            char key = (char)i;
//            String value = codeMap.get(key);
//            //System.out.println(value);
//
//            str+=value;
//            //System.out.println(str);
//        }
//        String st[]= toEight(str);
//
//        System.out.println(st[0]);
//        byte[] by = getByte(st);
//        fos.write(by);
        fos.flush();
        fos.close();
        fis.close();
        bis.close();
    }



    public byte[] createByteArray(String code) {
        //将每8位字符分隔开来得到字节数组的长度
        int size=code.length()/8;
        //截取得到字符串8整数后的最后几个字符串
        String destString=code.substring(size*8);
        byte dest[]=destString.getBytes();
        //s用来记录字节数组的单字节内容
        int s = 0;
        int i=0;
        int temp = 0;
        // 将字符数组转换成字节数组，得到字符的字节内容,方便将二进制转为十进制
        byte content[] = code.getBytes();
        for (int k = 0; k < content.length; k++) {
            content[k] = (byte) (content[k] - 48);
        }
        //转译后的字节内容数组
        byte byteContent[];
        if (content.length % 8 == 0) {// 如果该字符串正好是8的倍数
            byteContent = new byte[content.length / 8 + 1];
            byteContent[byteContent.length - 1] = 0;// 那么返回的字节内容数组的最后一个数就补0
        } else {
            //否则该数组的最后一个数就是补0的个数
            byteContent = new byte[content.length / 8 + 2];
            byteContent[byteContent.length - 1] = (byte) (8 - content.length % 8);
        }
        int bytelen=byteContent.length;
        int contentlen=content.length;
        // byteContent数组中最后一个是补0的个数，实际操作到次后个元素
        //Math.pow返回第一个参数的第二个参数次幂的值
        while (i < bytelen - 2) {
            for (int j = 0; j < contentlen; j++) {
                if (content[j] == 1) {// 如果数组content的值为1
                    s =(int)(s + Math.pow(2, (7 - (j - 8 * i))));// 累积求和
                }// if
                if ((j+1)%8==0) {// 当有8个元素时
                    byteContent[i] = (byte) s;// 就将求出的和放入到byteContent数组中去
                    i++;
                    s = 0;// 并重新使s的值赋为0
                }// if
            }// for
        }// while
        int destlen=dest.length;
        for(int n=0;n<destlen;n++){
            temp+=(dest[n]-48)*Math.pow(2, 7-n);//求倒数第2个字节的大小
        }
        byteContent[byteContent.length - 2] = (byte) temp;
        return byteContent;
    }
//    public String[] toEight(String str){
//
//
//
//        int size = str.length();
//        mod[0] = 8-(size%8);
//        String bu = "";
//        for(int i = 0;i<mod[0];++i){
//            bu+="0";
//        }
//        str+= bu;
//        String[] str8 = new String[size/8+1];
//        int count = 0;
//        String eight ="";
//        for(int i = 0;i< str.length();++i){
//            eight+=str.charAt(i);
//            count++;
//            eight = "";
//        }
//        return  str8;
//
//    }
//    public byte[] getByte(String[] str) {
//        byte[] by = new byte[str.length];
//        for (int i = 0; i < str.length; ++i) {
//            String exp = str[i];
//            System.out.println(exp);
//            byte b = 0;
//            for (int j = 0; j < 8; j++)
//                b += (byte) (Math.pow(2, 7 - j) * (new Integer(exp.charAt(j)).intValue() - 48));
//            by[i] = b;
//        }
//        return by;
//    }



}

