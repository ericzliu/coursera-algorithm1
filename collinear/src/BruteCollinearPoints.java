import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BruteCollinearPoints {

    private static final double EPSILON = 0.0001;
    private LineSegment[] segments;

    private static class Line {

        private double slope;
        private Point p;

        public Line(Point p1, double slopeVal) {
            if (slopeVal == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
            slope = slopeVal;
            p = p1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Line line = (Line) o;

            if (Double.compare(line.slope, slope) != 0) {
                return false;
            }

            return (p == line.p || Double.compare(p.slopeTo(line.p), slope) == 0);
        }

        @Override
        public int hashCode() {
            return Double.hashCode(slope);
        }
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(final Point[] points) {
        Point[] buffer = Arrays.copyOf(points, points.length);
        Arrays.sort(buffer);
        validateInput(buffer);
        HashSet<Line> lines = new HashSet<>();
        Arrays.sort(points);
        List<LineSegment> segs = new ArrayList<>();
        List<Point[]> combs = new ArrayList<>();
        combinations(points, 0, 4, new Point[4], combs);
        for (Point[] comb : combs) {
            Point p = comb[0];
            Point q = comb[1];
            Point r = comb[2];
            Point s = comb[3];
            if (collinear(p, q, r, s)) {
                LineSegment segment = new LineSegment(p, s);
                double slope = p.slopeTo(s);
                Line line = new Line(p, slope);
                if (!lines.contains(line)) {
                    lines.add(line);
                    segs.add(segment);
                }
            }
        }
        this.segments = segs.toArray(new LineSegment[0]);
    }

    private boolean collinear(Point p, Point q, Point r, Point s) {
        double qs = p.slopeTo(q);
        double rs = p.slopeTo(r);
        double ss = p.slopeTo(s);
        if (qs == 0.0 && rs == 0.0 && ss == 0.0) {
            return true;
        }
        if (!isInfinity(qs) && !isInfinity(rs) && !isInfinity(ss)) {
            if (Math.abs(qs - rs) == 0.0 && Math.abs(rs - ss) == 0.0) {
                return true;
            }
        } else if (isInfinity(qs) && isInfinity(rs) && isInfinity(ss)) {
            return true;
        }
        return false;
    }

    private boolean isInfinity(double val) {
        return val == Double.NEGATIVE_INFINITY || val == Double.POSITIVE_INFINITY;
    }

    private static void validateInput(final Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        int length = points.length;
        for (int i = 0; i < length; i += 1) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
        }

        if (length > 1) {
            Point p = points[0];
            for (int i = 1; i < length; i += 1) {
                if (points[i].compareTo(p) == 0) {
                    throw new IllegalArgumentException();
                }
                p = points[i];
            }
        }
    }

    private static <T> void combinations(final T[] points, int begin, int length, T[] result, List<T[]> results) {
        if (length == 0) {
            results.add(Arrays.copyOf(result, result.length));
            return;
        }
        for (int i = begin; i <= points.length - length; i += 1) {
            result[result.length - length] = points[i];
            combinations(points, i + 1, length - 1, result, results);
        }
    }


    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

}
