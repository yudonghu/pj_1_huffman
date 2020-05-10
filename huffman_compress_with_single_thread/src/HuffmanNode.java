public class HuffmanNode {
    public HuffmanNode left,right;
    public char letter;
    public int frequency;
    public String huffmanCode;
    public HuffmanNode(char letter, int frequency){
        this.letter = letter;
        this.frequency = frequency;
    }
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right){
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
    public String toString(){
        return (this.letter + " freq: "+ this.frequency + " code: "+ this.huffmanCode);
    }
}
