import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {

    private final int[][] digits;
    private final int hamming;
    private final int manhattan;
    private final int dim;
    private final int emptyTileRow;
    private final int emptyTileCol;
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(final int[][] blocks) {
        digits = deepCopy(blocks);
        dim = blocks.length;
        int t1 = 0;
        int t2 = 0;

        int voidRow = 0;
        int voidCol = 0;
        for (int i = 0; i < dim; i += 1) {
            for (int j = 0; j < dim; j += 1) {
                if (digits[i][j] != 0 && getTarget(i, j) != digits[i][j]) {
                    t1 += 1;
                    t2 += manhattan(i, j);
                } else if (digits[i][j] == 0) {
                    voidCol = j;
                    voidRow = i;
                }
            }
        }
        emptyTileCol = voidCol;
        emptyTileRow = voidRow;
        hamming = t1;
        manhattan = t2;
    }

    // board dimension n
    public int dimension() {
        return dim;
    }

    private int getTarget(final int i, final int j) {
        return i * dim + j + 1;
    }

    // number of blocks out of place
    public int hamming() {
        return hamming;
    }

    private int manhattan(final int i, final int j) {
        final int actual = digits[i][j];
        final int actRow = (actual - 1) / dim;
        final int actCol = (actual - 1) % dim;
        return Math.abs(actRow - i) + Math.abs(actCol - j);
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    private static int[][] deepCopy(int[][] tiles) {
        final int dim = tiles.length;
        int[][] c = new int[dim][dim];
        for (int i = 0; i < dim; i += 1) {
            System.arraycopy(tiles[i], 0, c[i], 0, dim);
        }
        return c;
    }

    private void exch(int[][] blocks, final int i1, final int j1, final int i2, final int j2) {
        int tmp = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = tmp;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] digitsCopy = deepCopy(digits);
        int i1 = -1;
        int j1 = -1;
        int i2 = -1;
        int j2 = -1;
        for (int i = 0; (i < dim) && (i1 == -1 || i2 == -1); i += 1) {
            for (int j = 0; (j < dim) && (i1 == -1 || i2 == -1); j += 1) {
                if (digitsCopy[i][j] != 0) {
                    if (i1 == -1) {
                        i1 = i;
                        j1 = j;
                    }
                    else if (i2 == -1) {
                        i2 = i;
                        j2 = j;
                    }
                }
            }
        }
        exch(digitsCopy, i1, j1, i2, j2);
        return new Board(digitsCopy);
    }

    private boolean less(int p, int q) {
        if (p > 0 && q == 0)
            return true;
        if (p > 0 && q > 0 && p < q)
            return true;
        return false;
    }

    private DigitIterator iterate() {
        int p = digits[0][0];
        int q = digits[0][dim - 1];
        int r = digits[dim - 1][0];
        int s = digits[dim - 1][dim - 1];
        int row = 0;
        int col = 0;
        Dir primary = Dir.U;
        Dir secondary = Dir.L;
        if (less(p, q) && less(p, r) && less(p, s)) {
            row = 0;
            col = 0;
            int i = digits[0][1];
            int j = digits[1][0];
            if (less(i, j)) {
                primary = Dir.R;
                secondary = Dir.U;
            }
            else {
                primary = Dir.U;
                secondary = Dir.R;
            }
        }
        else if (less(q, p) && less(q, r) && less(q, s)) {
            row = 0;
            col = dim - 1;
            int i = digits[row][col - 1];
            int j = digits[row + 1][col];
            if (less(i, j)) {
                primary = Dir.L;
                secondary = Dir.U;
            }
            else {
                primary = Dir.U;
                secondary = Dir.L;
            }
        }
        else if (less(r, p) && less(r, s) && less(r, p)) {
            row = dim - 1;
            col = 0;
            int i = digits[row - 1][col];
            int j = digits[row][col + 1];
            if (less(i, j)) {
                primary = Dir.D;
                secondary = Dir.R;
            }
            else {
                primary = Dir.R;
                secondary = Dir.D;
            }
        }
        else if (less(s, p) && less(s, q) && less(s, r)) {
            row = dim - 1;
            col = dim - 1;
            int i = digits[row - 1][col];
            int j = digits[row][col - 1];
            if (less(i, j)) {
                primary = Dir.D;
                secondary = Dir.L;
            }
            else {
                primary = Dir.L;
                secondary = Dir.D;
            }
        }
        return new DigitIterator(row, col, primary, secondary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DigitIterator iterator = iterate();
        Board b = (Board)o;
        DigitIterator iterator1 = b.iterate();
        while (iterator.hasNext()) {
            if (!iterator.next().equals(iterator1.next()))
                return false;
        }
        return true;
    }

    private static class BoardIterator implements Iterator<Board>
    {
        private final List<Board> boards;
        private int current;

        BoardIterator(List<Board> bds) {
            boards = bds;
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < boards.size();
        }

        @Override
        public Board next() {
            if (hasNext()) {
                Board board = boards.get(current);
                current += 1;
                return board;
            }
            throw new NoSuchElementException();
        }

    }

    private static class BoardIterable implements Iterable<Board>
    {
        final List<Board> boards;

        BoardIterable(List<Board> bds) {
            boards = bds;
        }
        @Override
        public Iterator<Board> iterator() {
            return new BoardIterator(boards);
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors(Board parent) {
        int row = emptyTileRow;
        int col = emptyTileCol;
        List<Board> bds = new ArrayList<>();
        if (row < dim - 1) {
            if (parent == null || (row + 1) != parent.emptyTileRow || col != parent.emptyTileCol) {
                int[][] ints = deepCopy(digits);
                exch(ints, row, col, row + 1, col);
                bds.add(new Board(ints));
            }
        }
        if (row > 0) {
            if (parent == null || (row - 1) != parent.emptyTileRow || col != parent.emptyTileCol) {
                int[][] ints = deepCopy(digits);
                exch(ints, row, col, row - 1, col);
                bds.add(new Board(ints));
            }
        }
        if (col > 0) {
            if (parent == null || row != parent.emptyTileRow || (col - 1) != parent.emptyTileCol) {
                int[][] ints = deepCopy(digits);
                exch(ints, row, col, row, col - 1);
                bds.add(new Board(ints));
            }
        }
        if (col < dim - 1) {
            if (parent == null || row != parent.emptyTileRow || (col + 1) != parent.emptyTileCol) {
                int[][] ints = deepCopy(digits);
                exch(ints, row, col, row, col + 1);
                bds.add(new Board(ints));
            }
        }
        return new BoardIterable(bds);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        final int length = String.valueOf(dim * dim - 1).length() + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dim; i += 1) {
            for (int j = 0; j < dim; j += 1) {
                String s = String.valueOf(digits[i][j]);
                for (int k = 0; k < length - s.length(); k += 1) {
                    builder.append(' ');
                }
                builder.append(s);
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private enum Dir {
        L,
        R,
        U,
        D
    }

    private class DigitIterator implements Iterator<Integer> {
        private final int dim;
        private final int total;
        private int row;
        private int col;
        private int n;
        private Dir primary;
        private Dir secondary;

        public DigitIterator(int i, int j, Dir first, Dir second) {
            dim = dimension();
            total = dim * dim;
            row = i;
            col = j;
            n = 0;
            primary = first;
            secondary = second;
        }

        private boolean moveUp() {
            row = (row + 1) % dim;
            return row == 0;
        }

        private boolean moveDown() {
            row = (row + dim - 1) % dim;
            return row == (dim - 1);
        }

        private boolean moveLeft() {
            col = (col + dim - 1) % dim;
            return col == (dim - 1);
        }

        private boolean moveRight() {
            col = (col + 1) % dim;
            return col == 0;
        }

        private boolean move(Dir dir) {
            switch (dir) {
                case D:
                    return moveDown();
                case L:
                    return moveLeft();
                case R:
                    return moveRight();
                case U:
                    return moveUp();
            }
            return false;
        }

        @Override
        public boolean hasNext() {
            return n < total;
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                int digit = digits[row][col];
                n += 1;
                if (hasNext()) {
                    if (move(primary)) {
                        move(secondary);
                    }
                }
                return digit;
            }
            throw new NoSuchElementException();
        }
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}