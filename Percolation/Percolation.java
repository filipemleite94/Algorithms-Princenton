import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private final int n;
   private final boolean[][] grid;
   private final boolean[] conTop;
   private final boolean[] conBot;
   private int numberOfOpenSites;
   private boolean percolates;
   private final WeightedQuickUnionUF wUF;
   
   private void checkInput(int row, int col)
   {
       if(row<1||row>n||col<1||col>n)
           throw new IllegalArgumentException();
   }
   
   public Percolation(int n)                // create n-by-n grid, with all sites blocked
   {
       if(n<=0)
           throw new IllegalArgumentException();
       this.n = n;
       int n2m1 = n*n-1;
       grid = new boolean[n][n];
       conTop = new boolean[n2m1+1];
       conBot = new boolean[n2m1+1];
       for(int i = 0;i<n;i++){
           conTop[i] = true;
           conBot[n2m1-i] = true;
       }
       numberOfOpenSites = 0;
       wUF = new WeightedQuickUnionUF(n2m1+1);
       percolates = false;
   }
   
   public void open(int row, int col)    // open site (row, col) if it is not open already
   {
       checkInput(row, col);
       row--;
       col--;
       if(grid[row][col])
           return;
       grid[row][col] = true;
       numberOfOpenSites++;
       int pos = row*n + col;
       boolean rootTop = conTop[pos];
       boolean rootBot = conBot[pos];
       int root;
       if(row != 0)
           if(grid[row-1][col]){
                root = wUF.find(pos-n);
                rootTop = conTop[root] || rootTop;
                rootBot = conBot[root] || rootBot;
                wUF.union(root, pos);
           }
       if(col!=0)
           if(grid[row][col-1]){
                root = wUF.find(pos-1);
                rootTop = conTop[root]||rootTop;
                rootBot = conBot[root]||rootBot;
                wUF.union(root, pos);
           }
       if(col!=n-1)
           if(grid[row][col+1]){
                root = wUF.find(pos+1);
                rootTop = conTop[root]||rootTop;
                rootBot = conBot[root]||rootBot;
                wUF.union(pos, root);
           }
       if(row != n-1)
           if(grid[row+1][col]){
                root = wUF.find(pos+n);
                rootTop = conTop[root]||rootTop;
                rootBot = conBot[root]||rootBot;
                wUF.union(pos, root);
           }
       root = wUF.find(pos);
       conTop[root] = rootTop;
       conBot[root] = rootBot;
       if(rootBot&&rootTop)
           percolates = true;
   }
   
   public boolean isOpen(int row, int col)  // is site (row, col) open?
   {
       checkInput(row, col);
       return grid[row-1][col-1];
   }
   
   public boolean isFull(int row, int col)  // is site (row, col) full?
   {
       checkInput(row, col);
       row--;
       col--;
       if(!grid[row][col])
           return false;
       return conTop[wUF.find(row*n + col)];
   }
   
   public     int numberOfOpenSites()       // number of open sites
   {
       return numberOfOpenSites;
   }
   
   public boolean percolates()              // does the system percolate?
   {
       return percolates;
   }
}