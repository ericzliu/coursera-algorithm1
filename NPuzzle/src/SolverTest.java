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
}