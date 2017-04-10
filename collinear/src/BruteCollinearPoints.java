import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final double EPSILON = 0.0001;
    private LineSegment[] segments;

    private static <T> void combinations(T[] points, int begin, int length, T[] result, List<T[]> results) {
        if (length == 0) {
            results.add(Arrays.copyOf(result, length));
            return;
        }
        for (int i = begin; i <= points.length - length; i += 1) {
            result[result.length - length] = points[i];
            combinations(points, i + 1, length - 1, result, results);
        }
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        List<LineSegment> segments = new ArrayList<>();
        List<Point[]> combs = new ArrayList<>();
        combinations(points, 0, 4, new Point[4], combs);
        for (Point[] comb : combs) {
            Point p = comb[0];
            Point q = comb[1];
            Point r = comb[2];
            Point s = comb[3];
            double qs = p.slopeTo(q);
            double rs = p.slopeTo(r);
            double ss = p.slopeTo(s);
            if (Math.abs(qs - rs) < EPSILON && Math.abs(rs - ss) < EPSILON) {
                segments.add(new LineSegment(p, s));
            }
        }
        this.segments = segments.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

}
