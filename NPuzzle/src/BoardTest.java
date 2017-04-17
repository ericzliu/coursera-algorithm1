import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void test_equal1() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 4;
        blocks[0][2] = 7;
        blocks[1][0] = 2;
        blocks[1][1] = 5;
        blocks[1][2] = 8;
        blocks[2][0] = 3;
        blocks[2][1] = 6;
        Board b = new Board(blocks);
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
        assertEquals(b, b1);
        assertEquals(b1, b);
    }

    @Test
    public void test_equal2() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 4;
        blocks[0][2] = 7;
        blocks[1][0] = 2;
        blocks[1][1] = 5;
        blocks[1][2] = 8;
        blocks[2][0] = 3;
        blocks[2][1] = 6;
        Board b = new Board(blocks);
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 3;
        blocks1[0][1] = 6;
        blocks1[0][2] = 0;
        blocks1[1][0] = 2;
        blocks1[1][1] = 5;
        blocks1[1][2] = 8;
        blocks1[2][0] = 1;
        blocks1[2][1] = 4;
        blocks1[2][2] = 7;
        Board b1 = new Board(blocks1);
        assertEquals(b, b1);
        assertEquals(b1, b);
    }

    @Test
    public void test_equal3() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 4;
        blocks[0][2] = 7;
        blocks[1][0] = 2;
        blocks[1][1] = 5;
        blocks[1][2] = 8;
        blocks[2][0] = 3;
        blocks[2][1] = 6;
        Board b = new Board(blocks);
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 7;
        blocks1[0][1] = 8;
        blocks1[0][2] = 0;
        blocks1[1][0] = 4;
        blocks1[1][1] = 5;
        blocks1[1][2] = 6;
        blocks1[2][0] = 1;
        blocks1[2][1] = 2;
        blocks1[2][2] = 3;
        Board b1 = new Board(blocks1);
        assertEquals(b, b1);
        assertEquals(b1, b);
    }

    @Test
    public void test_equal4() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 4;
        blocks[0][2] = 7;
        blocks[1][0] = 2;
        blocks[1][1] = 5;
        blocks[1][2] = 8;
        blocks[2][0] = 3;
        blocks[2][1] = 6;
        Board b = new Board(blocks);
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 0;
        blocks1[0][1] = 6;
        blocks1[0][2] = 3;
        blocks1[1][0] = 8;
        blocks1[1][1] = 5;
        blocks1[1][2] = 2;
        blocks1[2][0] = 7;
        blocks1[2][1] = 4;
        blocks1[2][2] = 1;
        Board b1 = new Board(blocks1);
        assertEquals(b, b1);
        assertEquals(b1, b);
    }

    @Test
    public void test_equal5() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 4;
        blocks[0][2] = 7;
        blocks[1][0] = 2;
        blocks[1][1] = 5;
        blocks[1][2] = 8;
        blocks[2][0] = 3;
        blocks[2][1] = 6;
        Board b = new Board(blocks);
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 0;
        blocks1[0][1] = 8;
        blocks1[0][2] = 7;
        blocks1[1][0] = 6;
        blocks1[1][1] = 5;
        blocks1[1][2] = 4;
        blocks1[2][0] = 3;
        blocks1[2][1] = 2;
        blocks1[2][2] = 1;
        Board b1 = new Board(blocks1);
        assertEquals(b, b1);
        assertEquals(b1, b);
    }


    @Test
    public void test_equal6() {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 4;
        blocks[0][2] = 7;
        blocks[1][0] = 2;
        blocks[1][1] = 5;
        blocks[1][2] = 8;
        blocks[2][0] = 3;
        blocks[2][1] = 6;
        Board b = new Board(blocks);
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 3;
        blocks1[0][1] = 2;
        blocks1[0][2] = 1;
        blocks1[1][0] = 6;
        blocks1[1][1] = 5;
        blocks1[1][2] = 4;
        blocks1[2][0] = 0;
        blocks1[2][1] = 8;
        blocks1[2][2] = 7;
        Board b1 = new Board(blocks1);
        assertEquals(b, b1);
        assertEquals(b1, b);
    }

    @Test
    public void test_to_string() {
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 3;
        blocks1[0][1] = 2;
        blocks1[0][2] = 1;
        blocks1[1][0] = 6;
        blocks1[1][1] = 5;
        blocks1[1][2] = 4;
        blocks1[2][0] = 0;
        blocks1[2][1] = 8;
        blocks1[2][2] = 7;
        Board b1 = new Board(blocks1);
        String actual = b1.toString();
        String exp = " 3 2 1" + System.lineSeparator() + " 6 5 4" + System.lineSeparator() + " 0 8 7" + System.lineSeparator();
        assertEquals(exp, actual);
    }


    @Test
    public void test_neighbors1() {
        int[][] blocks1 = new int[3][3];
        blocks1[0][0] = 3;
        blocks1[0][1] = 2;
        blocks1[0][2] = 1;
        blocks1[1][0] = 6;
        blocks1[1][1] = 5;
        blocks1[1][2] = 4;
        blocks1[2][0] = 0;
        blocks1[2][1] = 8;
        blocks1[2][2] = 7;
        Board b1 = new Board(blocks1);
        Iterable<Board> neighbors = b1.neighbors(null);
        Iterator<Board> iterator = neighbors.iterator();
        Set<String> actuals = new HashSet<>();
        while (iterator.hasNext()) {
            Board next = iterator.next();
            actuals.add(next.toString());
        }
        String lb1 = " 3 2 1" + System.lineSeparator() + " 6 5 4" + System.lineSeparator() + " 8 0 7" + System.lineSeparator();
        String lb2 = " 3 2 1" + System.lineSeparator() + " 0 5 4" + System.lineSeparator() + " 6 8 7" + System.lineSeparator();
        Set<String> exp = new HashSet<>();
        exp.add(lb1);
        exp.add(lb2);
        assertEquals(exp, actuals);
    }

}