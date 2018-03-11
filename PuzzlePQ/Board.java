import java.util.Arrays;
import java.util.LinkedList;
import edu.princeton.cs.algs4.StdOut;

public class Board {
 private int n;
 private int hamming;
 private int manhattan;
 private int[][] b;
 private int i0;
 private int j0;
 
 
 public Board(int[][] blocks)            // construct a board from an n-by-n array of blocks
           // (where blocks[i][j] = block in row i, column j)
 {
  n = blocks.length;
  hamming = 0;
  manhattan = 0;
  b = new int[n][];
  for(int i =0; i< n; i++){
   int iMultn = i*n;
   b[i] = new int[n];
   for(int j =0; j<n; j++){
    int value = blocks[i][j];
    b[i][j] = value;
    if(value==0){
        i0 = i;
        j0 = j;
        continue;
    }
    value--;
    if(value!=iMultn+j){
     hamming++;
     manhattan += (value<iMultn)? i-(value/n): (value/n)-i;
     manhattan += (value%n<j)? j-(value%n) : (value%n)-j;
    }
   }
  }
 }
 public int dimension()                  // board dimension n
 {
  return n;
 }

 public int hamming()                    // number of blocks out of place
 {
  return hamming;
 }
 public int manhattan()                  // sum of Manhattan distances between blocks and goal
 {
  return manhattan;
 }
  
 public boolean isGoal()                 // is this board the goal board
 {
  return hamming==0;
 }
 public Board twin()                     // a board that is obtained by exchanging any pair of blocks
 {
  int i1;
  i1 = (i0+1)%n;
  return createSwap(b, i1,0,i1,1);
 }
 public boolean equals(Object y)         // does this board equal y?
 {
  if(y==this) return true;
  if(y==null) return false;
  if(this.getClass()!=y.getClass()) return false;
  Board by = (Board) y;
  if(by.n!=n||i0!=by.i0||j0!=by.j0) return false;
  return Arrays.deepEquals(b, by.b);
 }
  
 public Iterable<Board> neighbors()      // all neighboring boards
 {
  int extremity=n-1;
  LinkedList<Board> neigh = new LinkedList<Board>();
  if(j0!=0){
   neigh.add(createSwap(b, i0,j0,i0,j0-1));
  }
  if(j0!=extremity){
   neigh.add(createSwap(b, i0,j0,i0,j0+1));
  }
  if(i0!=0){
   neigh.add(createSwap(b, i0,j0,i0-1,j0));
  }
  if(i0!=extremity){
   neigh.add(createSwap(b, i0,j0,i0+1,j0));
  }
  return neigh;
 }
 public String toString()                // string representation of this board (in the output format specified below)
 {
  StringBuilder sb = new StringBuilder();
  sb.append(n);
  //sb.append(" "+manhattan()+" "+hamming());
  sb.append(System.lineSeparator());
  for(int i = 0; i< n;i++){
   for(int j = 0; j< n; j++){
    sb.append(String.format ("%3d ", b[i][j]));
   }
   sb.append(System.lineSeparator());
  }
  return sb.toString();
 }
 
 private Board createSwap(int[][] numbers, int i1, int j1, int i2, int j2){
  int swap = numbers[i1][j1];
  numbers[i1][j1] = numbers[i2][j2];
  numbers[i2][j2] = swap;
  Board b = new Board(numbers);
  numbers[i2][j2]=numbers[i1][j1];
  numbers[i1][j1]=swap;
  return b;
 }
}