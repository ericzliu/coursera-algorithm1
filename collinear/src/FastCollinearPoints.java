import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class FastCollinearPoints {

    private static final double EPSILON = 0.00001;
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

        public double getSlope() {
            return slope;
        }

        public Point getP() {
            return p;
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

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(final Point[] points) {
        Point[] buffer = Arrays.copyOf(points, points.length);
        Arrays.sort(buffer);
        validateInput(buffer);
        HashSet<Line> hashSet = new HashSet<>();
        ArrayList<LineSegment> segs = new ArrayList<>();
        ArrayList<Point> pts = new ArrayList<>(32);
        int length = points.length;
        for (Point p : points) {
            pts.add(p);
            Arrays.sort(buffer, p.slopeOrder());
            double lastSlope = p.slopeTo(buffer[0]);
            for (int i = 1; i < length; i += 1) {
                double slope = p.slopeTo(buffer[i]);
                if (Math.abs(slope - lastSlope) > 0) {
                    addSegment(hashSet, segs, pts);
                    pts.clear();
                    pts.add(p);
                }
                pts.add(buffer[i]);
                lastSlope = slope;
            }
            addSegment(hashSet, segs, pts);
            pts.clear();
        }
        this.segments = segs.toArray(new LineSegment[0]);
    }

    private static void validateInput(Point[] points) {
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

    private LineSegment createSegment(ArrayList<Point> pts) {
        Collections.sort(pts);
        return new LineSegment(pts.get(0), pts.get(pts.size() - 1));
    }

    private void addSegment(HashSet<Line> hashSet, ArrayList<LineSegment> segs, ArrayList<Point> pts) {
        if (pts.size() >= 4) {
            Line line = new Line(pts.get(0), pts.get(0).slopeTo(pts.get(3)));
            LineSegment segment = createSegment(pts);
            if (!hashSet.contains(line)) {
                hashSet.add(line);
                segs.add(segment);
            }
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
