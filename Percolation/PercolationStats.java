import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double s;
    private final double cLo;
    private final double cHi;
    
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if(n<=0 || trials<=0)
            throw new IllegalArgumentException();
        
        final double x[];
        x = new double[trials];
        final double n2 = n*n;
        final int np1 = n+1;
        for(int i =0; i<trials; i++){
            Percolation percol = new Percolation(n);
            while(!percol.percolates())
            {
                percol.open(StdRandom.uniform(1,np1), StdRandom.uniform(1,np1));
            }
            x[i] = percol.numberOfOpenSites()/n2;
        }
        mean = StdStats.mean(x);
        s = StdStats.stddev(x);
        double subInterval = 1.96*s/(Math.sqrt(trials));
        cLo = mean-subInterval;
        cHi = mean + subInterval;
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return mean;
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return s;
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return cLo;
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return cHi;
    }
    public static void main(String[] args)        // test client (described below)
    {
        PercolationStats percolStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.print("mean                    = ");
        StdOut.println(percolStats.mean());
        StdOut.print("stddev                  = ");
        StdOut.println(percolStats.stddev());
        StdOut.print("95% confidence interval = [");
        StdOut.print(percolStats.confidenceLo());
        StdOut.print(", ");
        StdOut.print(percolStats.confidenceHi());
        StdOut.print("]");
    }
}