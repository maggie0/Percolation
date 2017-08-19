import edu.princeton.cs.algs4.StdRandom; 
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats { 
    private double[] thresholds;
    
    public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid 
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("both " + n + " and " + trials + " must be positive.");
        thresholds = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation pc = new Percolation(n);
            while (!pc.percolates()) {               
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (pc.isOpen(row, col))
                    continue;
                pc.open(row, col);
            }
            thresholds[i] = (double) pc.numberOfOpenSites() / (n*n);
        }
    }
    
    public double mean() { // sample mean of percolation threshold 
        return StdStats.mean(thresholds);
    }
    
    public double stddev() { // sample standard deviation of percolation threshold 
        return StdStats.stddev(thresholds);
    }
    
    public double confidenceLo() { // low endpoint of 95% confidence interval 
        return mean() - 1.96 * stddev() / Math.sqrt(thresholds.length);
    }
    
    public double confidenceHi() { // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(thresholds.length);
    }
    
    public static void main(String[] args) { // test client (described below) 
        PercolationStats ps = new PercolationStats(2, 100000);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
