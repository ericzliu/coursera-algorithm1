import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.*;

public class SolverTest {
    @Test
    public void test1() {
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 1;
        blocks1[0][1] = 2;
        blocks1[0][2] = 3;
        blocks1[1][0] = 4;
        blocks1[1][1] = 5;
        blocks1[1][2] = 6;
        blocks1[2][0] = 7;
        blocks1[2][1] = 8;
        blocks1[2][2] = 0;
        Board b1 = new Board(blocks1);
        Solver solver = new Solver(b1);
        assertTrue(solver.isSolvable());
        assertEquals(0, solver.moves());
    }

    @Test
    public void test2() {
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 0;
        blocks1[0][1] = 1;
        blocks1[0][2] = 3;
        blocks1[1][0] = 4;
        blocks1[1][1] = 2;
        blocks1[1][2] = 5;
        blocks1[2][0] = 7;
        blocks1[2][1] = 8;
        blocks1[2][2] = 6;
        Board b1 = new Board(blocks1);
        Solver solver = new Solver(b1);
        assertTrue(solver.isSolvable());
        assertEquals(4, solver.moves());
    }

    @Test
    public void test3() {
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 1;
        blocks1[0][1] = 2;
        blocks1[0][2] = 3;
        blocks1[1][0] = 4;
        blocks1[1][1] = 5;
        blocks1[1][2] = 6;
        blocks1[2][0] = 8;
        blocks1[2][1] = 7;
        blocks1[2][2] = 0;
        Board b1 = new Board(blocks1);
        Solver solver = new Solver(b1);
        assertFalse(solver.isSolvable());
    }

    @Test
    public void test_2_2() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = classLoader.getResource("puzzle2x2-00.txt").getPath();
        int[][] tiles = readTiles(path);
        Board b1 = new Board(tiles);
        Solver solver = new Solver(b1);
        assertTrue(solver.isSolvable());

        path = classLoader.getResource("puzzle2x2-01.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertTrue(solver.isSolvable());

        path = classLoader.getResource("puzzle2x2-02.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertTrue(solver.isSolvable());

        path = classLoader.getResource("puzzle2x2-unsolvable1.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertFalse(solver.isSolvable());


        path = classLoader.getResource("puzzle2x2-unsolvable2.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertFalse(solver.isSolvable());
    }

    @Test
    public void test_3_3() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = classLoader.getResource("puzzle3x3-29.txt").getPath();
        int[][] tiles = readTiles(path);
        Board b1 = new Board(tiles);
        Solver solver = new Solver(b1);
        assertTrue(solver.isSolvable());

        path = classLoader.getResource("puzzle3x3-30.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertTrue(solver.isSolvable());

        path = classLoader.getResource("puzzle3x3-31.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertTrue(solver.isSolvable());

        path = classLoader.getResource("puzzle3x3-unsolvable1.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertFalse(solver.isSolvable());


        path = classLoader.getResource("puzzle3x3-unsolvable2.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertFalse(solver.isSolvable());
    }

    @Test
    public void test_4_4() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String path;
        int[][] tiles;
        Board b1;
        Solver solver;

        path = classLoader.getResource("puzzle4x4-80.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertTrue(solver.isSolvable());

        path = classLoader.getResource("puzzle4x4-unsolvable.txt").getPath();
        tiles = readTiles(path);
        b1 = new Board(tiles);
        solver = new Solver(b1);
        assertFalse(solver.isSolvable());
    }

    private int[][] readTiles(String filename) {
        int[][] tiles = null;
        In in = null;
        try {
            in = new In(filename);
            int n = in.readInt();
            tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
        } finally {
            if (in != null)
                in.close();
        }
        return tiles;
    }

}