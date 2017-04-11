import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CollinearPointsTest {

    private static Point[] readFile(String input) {
        InputStream inputStream = CollinearPointsTest.class.getResourceAsStream(input);
        Scanner scan = new Scanner(inputStream);
        List<Point> pts = new ArrayList<>();
        int n = scan.nextInt();
        for (int i = 0; i < n; i += 1) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            pts.add(new Point(x, y));
        }
        scan.close();
        return pts.toArray(new Point[0]);
    }

    private void assertSegmentsEquals(LineSegment[] expected, LineSegment[] actual) {
        List<String> expectedStr = new ArrayList<>();
        for (LineSegment segment : expected) {
            expectedStr.add(segment.toString());
        }
        List<String> actualStr = new ArrayList<>();
        for (LineSegment segment : actual) {
            actualStr.add(segment.toString());
        }
        expectedStr.sort(String::compareTo);
        actualStr.sort(String::compareTo);
        assertArrayEquals(expectedStr.toArray(), actualStr.toArray());
    }

    @Test
    public void test_input6() {
        Point[] points = readFile("input6.txt");
        BruteCollinearPoints bruteForce = new BruteCollinearPoints(points);
        int i = bruteForce.numberOfSegments();
        assertEquals(1, i);
    }

    @Test
    public void test_input8_fast() {
        Point[] points = readFile("input8.txt");
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        int i = collinearPoints.numberOfSegments();
        assertEquals(2, i);
        LineSegment[] segments = collinearPoints.segments();
        LineSegment ls1 = new LineSegment(new Point(10000, 0), new Point(0, 10000));
        LineSegment ls2 = new LineSegment(new Point(3000, 4000), new Point(20000, 21000));
        assertSegmentsEquals(new LineSegment[]{ ls1, ls2 }, segments);
    }

    @Test
    public void test_input8_brute_force() {
        Point[] points = readFile("input8.txt");
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        int i = collinearPoints.numberOfSegments();
        assertEquals(2, i);
        LineSegment[] segments = collinearPoints.segments();
        LineSegment ls1 = new LineSegment(new Point(10000, 0), new Point(0, 10000));
        LineSegment ls2 = new LineSegment(new Point(3000, 4000), new Point(20000, 21000));
        assertSegmentsEquals(new LineSegment[]{ ls1, ls2 }, segments);
    }


    @Test
    public void test_vertical_line() {
        Point[] points = readFile("input40.txt");
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        int i = collinearPoints.numberOfSegments();
        assertEquals(4, i);
        LineSegment[] segments = collinearPoints.segments();
        BruteCollinearPoints bruteForce = new BruteCollinearPoints(points);
        i = bruteForce.numberOfSegments();
        assertEquals(4, i);
    }

    @Test
    public void test_fast() {
        Point[] points = readFile("kw1260.txt");
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        int i = collinearPoints.numberOfSegments();
        assertEquals(288, i);
    }
}