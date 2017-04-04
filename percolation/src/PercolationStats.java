import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double experiment(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            do {
                int row = 1 + StdRandom.uniform(n);
                int col = 1 + StdRandom.uniform(n);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    break;
                }
            } while (true);
        }
        return Double.valueOf(percolation.numberOfOpenSites()) / Double.valueOf(n * n);
    }

    private static final double COEF = 1.96;
    private double[] samples;
    private double mean;
    private double stddev;
    private double lend;
    private double hend;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        samples = new double[trials];
        for (int i = 0; i < trials; ++i) {
            samples[i] = experiment(n);
        }
        mean = StdStats.mean(samples);
        stddev = StdStats.stddev(samples);
        double root = Math.sqrt(Double.valueOf(trials));
        lend = mean - stddev * COEF / root;
        hend = mean + stddev * COEF / root;
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return lend;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return hend;
    }

    // test client (described below)
    public static void main(String[] args)
    {
        if (args.length == 2) {
            int n = Integer.valueOf(args[0]);
            int t = Integer.valueOf(args[1]);
            if (n <= 0 || t <= 0) {
                throw new IllegalArgumentException();
            }
            PercolationStats stats = new PercolationStats(n, t);
            StdOut.printf("mean                    = %f\n", stats.mean());
            StdOut.printf("stddev                  = %f\n", stats.stddev());
            StdOut.printf("95%% confidence interval = [%1$f, %2$f]\n", stats.confidenceLo(), stats.confidenceHi());
        }
    }
}
