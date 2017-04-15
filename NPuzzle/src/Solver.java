import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Solver {

    private MinPQ<SearchNode> queue;
    private MinPQ<SearchNode> twinQueue;
    private BoardPath solution;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(final Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        Board twin = initial.twin();
        queue = new MinPQ<>();
        twinQueue = new MinPQ<>();
        queue.insert(new SearchNode(0, initial, null));
        twinQueue.insert(new SearchNode(0, twin, null));
        search();
    }

    private final SearchNode exploreFirst(MinPQ<SearchNode> pq) {
        if (!pq.isEmpty()) {
            SearchNode node = pq.delMin();
            SearchNode prev = node.getPrev();
            Board board = node.getBoard();
            final int num = node.getSteps() + 1;
            for (Board neighbor : board.neighbors()) {
                if (prev == null || !neighbor.equals(prev.getBoard())) {
                    pq.insert(new SearchNode(num, neighbor, node));
                }
            }
            return node;
        }
        return null;
    }

    private final void search() {
        while (true) {
            SearchNode board = exploreFirst(queue);
            SearchNode twinBoard = exploreFirst(twinQueue);
            if (board != null && board.getBoard().isGoal()) {
                solution = board.getPath();
                solvable = true;
                return;
            }
            if (twinBoard != null && twinBoard.getBoard().isGoal()) {
                solution = twinBoard.getPath();
                solvable = false;
                return;
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return solution.size() - 1;
        }
        return -1;
    }

    private static class BoardPath implements Iterable<Board>
    {
        final List<Board> boards;

        BoardPath(final List<Board> bds) {
            boards = bds;
        }
        @Override
        public Iterator<Board> iterator() {
            return boards.iterator();
        }

        public int size() {
            return boards.size();
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private static class SearchNode implements Comparable<SearchNode> {

        private int steps;
        private SearchNode prev;
        private Board board;

        public SearchNode(int steps, Board board, SearchNode prev) {
            this.steps = steps;
            this.board = board;
            this.prev = prev;
        }

        public int getSteps() {
            return steps;
        }

        public Board getBoard() {
            return board;
        }

        public SearchNode getPrev() {
            return prev;
        }

        @Override
        public int compareTo(SearchNode o) {
            return getPriority() - o.getPriority();
        }

        private int getPriority() {
            return steps + board.manhattan();
        }

        public BoardPath getPath() {
            SearchNode current = this;
            List<Board> boards = new ArrayList<>();
            boards.add(board);
            while (current.getPrev() != null) {
                current = current.getPrev();
                boards.add(current.getBoard());
            }
            Collections.reverse(boards);
            return new BoardPath(boards);
        }
    }

    // solve a slider puzzle (given below)
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
