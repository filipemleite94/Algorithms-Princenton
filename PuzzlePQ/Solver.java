import java.util.Arrays;
import java.util.Iterator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
 private boolean solvable;
 private Board[] solution;
 private int moves;
 public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
 {
   solution = null;
   moves = -1;
   solvable = false;
  boolean found;
  if(initial==null)
   throw new java.lang.IllegalArgumentException();
  MinPQ<NodeBoard> min1 = new MinPQ<NodeBoard>();
  MinPQ<NodeBoard> min2 = new MinPQ<NodeBoard>();
  NodeBoard n0 = new NodeBoard();
  NodeBoard n1 = new NodeBoard(n0, initial);
  NodeBoard n2 = new NodeBoard(n0, initial.twin());
  min1.insert(n1);
  min2.insert(n2);
  while(true){
   n1 = min1.delMin();
   n2 = min2.delMin();
   
   if(n1.thisBoard.isGoal()){
    solvable=true;
    break;
   }
   if(n2.thisBoard.isGoal()){
    solvable = false;
    break;
   }
   found = false;
   for(Board b1:n1.thisBoard.neighbors()){
    if(!found){
     if(b1.equals(n1.previousNode.thisBoard)){
      found = true;
      continue;
     }
    }
    min1.insert(new NodeBoard(n1, b1));
   }
   found = false;
   for(Board b2:n2.thisBoard.neighbors()){
    if(!found){
     if(b2.equals(n2.previousNode.thisBoard)){
      found = true;
      continue;
     }
    }
    min2.insert(new NodeBoard(n2, b2));
   }
  }
  if(!solvable){
   return;
  }
  moves = n1.numMoves;
  solution = new Board[moves+1];
  
  for(int i = moves; i>-1;i--){
   solution[i] = n1.thisBoard;
   n1 = n1.previousNode;
  }
 }
 public boolean isSolvable()            // is the initial board solvable?
 {
  return solvable;
 }
 
 public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
 {
  return moves;
 }
 public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
 {
     if(solution ==null)
         return null;
     return new Iterable<Board>(){
         @Override
         public Iterator<Board> iterator(){
             return new Iterator<Board>(){
                 private int index = 0;
                 
                 @Override
                 public boolean hasNext(){
                     return solution.length>index;
                 }
                 
                 @Override
                 public Board next(){
                     if(index==solution.length)
                         throw new java.util.NoSuchElementException();
                     return solution[index++];
                 }
                 
                 @Override
                 public void remove(){
                     throw new java.lang.UnsupportedOperationException();
                 }
             };
         }
     };
 }

 private class NodeBoard implements Comparable<NodeBoard>{
  int Manhattan=-1;
  int numMoves;
  Board thisBoard;
  NodeBoard previousNode;
  
  public NodeBoard(NodeBoard previousNode, Board thisBoard){
   numMoves = previousNode.numMoves+1;
   this.previousNode = previousNode;
   this.thisBoard = thisBoard;
  }
  
  private int getManhattan(){
      if(Manhattan==-1)
          Manhattan = numMoves+thisBoard.manhattan();
      return Manhattan;
  }
  
  public NodeBoard(){
   numMoves=-1;
   this.thisBoard = null;
   previousNode = null;
  }
  
  public int compareTo(NodeBoard n2){
   return getManhattan()-n2.getManhattan();
  }   
 }
 public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
 }
}