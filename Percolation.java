import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation { 
    private boolean[] grid;
    private int openCount;
    private int n;
    private WeightedQuickUnionUF ufVB; // union-find object with virtual-bottom
    private WeightedQuickUnionUF uf;  // union-find object without virtual-bottom
    
    public Percolation(int n) { // create n-by-n grid, with all sites blocked 
        if (n <= 0)
            throw new IllegalArgumentException(n + " must be positive.");
        this.n = n;
        openCount = 0;
        int length = n * n + 1;
        grid = new boolean[length];
        for (int i = 1; i <= n*n; ++i) {
            grid[i] = false;
        }
        ufVB = new WeightedQuickUnionUF(length+1);
        uf = new WeightedQuickUnionUF(length);
    }
    
    public void open(int row, int col) { // open site (row, col) if it is not open already 
        validation(row, col);
        if (isOpen(row, col)) 
            return;
        int pos = xyTo1D(row, col);
        grid[pos] = true;
        ++openCount;
        if (row == 1) {
            ufVB.union(0, pos);
            uf.union(0, pos);
        }
        if (row == n) {
            ufVB.union(n*n+1, pos);
        }
        if (row > 1 && isOpen(row-1, col)) {
            ufVB.union(pos, pos-n);
            uf.union(pos, pos-n);
        }
        if (row < n && isOpen(row+1, col)) {
            ufVB.union(pos, pos+n);
            uf.union(pos, pos+n);
        }
        if (col > 1 && isOpen(row, col-1)) {
            ufVB.union(pos, pos-1);
            uf.union(pos, pos-1);
        }
        if (col < n && isOpen(row, col+1)) {
            ufVB.union(pos, pos+1);
            uf.union(pos, pos+1);
        }
    }
    
    public boolean isOpen(int row, int col) { // is site (row, col) open? 
        validation(row, col);
        return grid[xyTo1D(row, col)];
    }
    
    public boolean isFull(int row, int col) { // is site (row, col) full? 
        validation(row, col);
        return uf.connected(0, xyTo1D(row, col));
    }
        
    public int numberOfOpenSites() { // number of open sites 
        return openCount;
    }
        
    public boolean percolates() { // does the system percolate? 
        return ufVB.connected(0, grid.length);
    }
    
    private int xyTo1D(int row, int col) { // uniquely mapping 2D coordinates to 1D coordinates
        return (row - 1) * n + col;
    }
    
    private void validation(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IndexOutOfBoundsException("both " + row + " and " + col + " should be between 1 and " + n);
    }
    
    public static void main(String[] args) { // test client (optional) 
        Percolation pc = new Percolation(5);
        pc.open(1, 2);
        System.out.println(pc.percolates());
        pc.open(2, 2);
        System.out.println(pc.percolates());
        pc.open(2, 1);
        System.out.println(pc.percolates());
        pc.open(3, 1);
        System.out.println(pc.percolates());
        pc.open(4, 1);
        System.out.println(pc.percolates());
        pc.open(5, 1);
        System.out.println(pc.percolates());
    }
}