import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int gridLength;
    private int gridSize;
    private boolean[][] sites;
    private int numberOfOpenSites;

    private void validate(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int indexOf(int row, int col) {
        return (row - 1) * gridLength + col;
    }
    // create n-by-n grid, with all sites blocked
    public Percolation(int n)
    {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        gridLength = n;
        gridSize = gridLength * gridLength;
        uf = new WeightedQuickUnionUF(2 + gridSize);
        sites = new boolean[gridLength + 1][gridLength + 1];
        numberOfOpenSites = 0;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col)
    {
        validate(row, col);
        if (!sites[row][col]) {
            sites[row][col] = true;
            numberOfOpenSites += 1;

            int ind = indexOf(row, col);
            if (row == 1) {
                uf.union(ind, 0);
            }
            else if (row > 1) {
                checkAndUnion(row - 1, col, ind);
            }
            if (row == gridLength) {
                uf.union(ind, gridSize + 1);
            }
            else if (row < gridLength) {
                checkAndUnion(row + 1, col, ind);
            }
            if (col > 1) {
                checkAndUnion(row, col - 1, ind);
            }
            if (col < gridLength) {
                checkAndUnion(row, col + 1, ind);
            }
        }
    }

    private void checkAndUnion(final int row, final int col, final int ind) {
        if (isOpen(row, col)) {
            int ind1 = indexOf(row, col);
            uf.union(ind, ind1);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        validate(row, col);
        return sites[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col)
    {
        validate(row, col);
        return uf.connected(0, indexOf(row, col));
    }

    // number of open sites
    public int numberOfOpenSites()
    {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return uf.connected(0, gridSize + 1);
    }

    // test client (optional)
    public static void main(String[] args)
    {

    }
}
