import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args){
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while(!StdIn.isEmpty()){
            q.enqueue(StdIn.readString());
            if(q.size()>=k)
                break;
        }
        if(q.size()>=k)
        {            
            int j = k+1;
            while(!StdIn.isEmpty()){
                String aux = StdIn.readString();
                if(StdRandom.uniform(j) < k){
                    q.dequeue();
                    q.enqueue(aux);
                }
                j++;
            }
        }
        while((--k)>=0){
            StdOut.println(q.dequeue());
        }
    }
}