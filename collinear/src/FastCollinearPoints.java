import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

public class FastCollinearPoints {
    private final static double EPSILON = 0.00001;
    private LineSegment[] segments;

    private LineSegment createSegment(ArrayList<Point> pts) {
        Collections.sort(pts);
        return new LineSegment(pts.get(0), pts.get(pts.size() - 1));
    }

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        HashSet<String> hashSet = new HashSet<>();
        ArrayList<LineSegment> segments = new ArrayList<>();

        ArrayList<Point> pts = new ArrayList<>(32);
        int length = points.length;
        Point[] buffer = Arrays.copyOf(points, points.length);
        for (Point p : points) {
            pts.add(p);
            Arrays.sort(buffer, p.slopeOrder());
            double lastSlope = p.slopeTo(buffer[0]);
            for (int i = 1; i < length; i += 1) {
                double slope = p.slopeTo(buffer[i]);
                if (Math.abs(slope - lastSlope) < EPSILON) {
                    pts.add(buffer[i]);
                } else {
                    addSegment(hashSet, segments, pts);
                    pts.clear();
                    pts.add(p);
                }
                lastSlope = slope;
            }
            addSegment(hashSet, segments, pts);
            pts.clear();
        }
        this.segments = segments.toArray(new LineSegment[0]);
    }

    private void addSegment(HashSet<String> hashSet, ArrayList<LineSegment> segments, ArrayList<Point> pts) {
        if (pts.size() >= 4) {
            LineSegment segment = createSegment(pts);
            String str = segment.toString();
            if (!hashSet.contains(str)) {
                hashSet.add(str);
                segments.add(segment);
            }
        }
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
