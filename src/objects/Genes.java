package objects;
import java.util.Arrays;
import java.util.Random;
public class Genes {
    private final int[] genes;
    private final int[] genesID;
    Genes mom = this;
    public Genes(int[] a){
        Arrays.sort(a);
        this.genes = a;
        this.genesID = getGenesID(a);
    }
    public Genes(){
        Random rd = new Random();
        int[] arr = new int[32];
        for (int i = 0; i < arr.length; i++) arr[i] = rd.nextInt(8);
        Arrays.sort(arr);
        this.genes = arr;
        this.genesID = getGenesID(arr);
    }
    public int getRandomDecision(){
        return genes[(int) (Math.random()*31)];
    }

    public Genes getChildGenes(Genes dad){
        int[] momCopy = copyOf(mom.getGenes());
        int[] dadCopy = copyOf(dad.getGenes());
        shuffle(momCopy);
        shuffle(dadCopy);
        int rand = (int)(Math.random()*100);
        int[] result = new int[32];
        int index = 0;
        int[] first;
        int[] second;
        if(rand >= 50){
            first = getFirstPart(momCopy);
            second = getSecondPart(dadCopy);

        }else{
            first = getFirstPart(dadCopy);
            second = getSecondPart(momCopy);
        }
        for(int x : first){
            result[index]=x;
            index++;
        }
        for(int x : second){
            result[index]=x;
            index++;
        }
        Arrays.sort(result);
        result = checkGenes(result);
        return new Genes(result);
    }

    private static int[] copyOf(int[] original) {
        int[] copy = new int[32];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, 31));
        return copy;
    }
    private int[] checkGenes(int[] arr){
        for(int i = 0;i<8;i++){
            if(binarySearch(arr,0,31,i)==-1){
                int r = (int)(Math.random()*31);
                arr[r] = i;
                return checkGenes(arr);
            }
        }
        return arr;
    }
    private int binarySearch(int[] arr, int l, int r, int x) {
        if (r >= l) {
            int mid = l + (r - l) / 2;
            if (arr[mid] == x)
                return 1;
            if (arr[mid] > x)
                return binarySearch(arr, l, mid - 1, x);
            return binarySearch(arr, mid + 1, r, x);
        }
        return -1;
    }
    private int[] getFirstPart(int[] arr){
        return getSliceOfArray(arr,0,22);
    }
    private int[] getSecondPart(int[] arr){
        return getSliceOfArray(arr,22,32);
    }
    private static int[] getSliceOfArray(int[] arr,int start, int end) {
        int[] slice = new int[end - start];
        if (slice.length >= 0) System.arraycopy(arr, start, slice, 0, slice.length);
        return slice;
    }
    private int[] getGenes(){
        return this.genes;
    }
    private static void shuffle(int[] array) {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
    }
    private int[] getGenesID(int[] genesArr){
        int[] result = {0,0,0,0,0,0,0,0};
        for(int x: genesArr) result[x]++;
        return result;
    }
    public boolean equals(Genes other){
        for(int i=0;i<8;i++){
            if(this.genesID[i] != other.genesID[i]) return false;
        }
        return true;
    }
    @Override
    public String toString(){
        String result = "[";
        for(int x: this.genes) result = result + x;
        result = result + "]";
        return result;
    }
}
