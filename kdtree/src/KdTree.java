import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private static final RectHV AREA = new RectHV(0D, 0D, 1D, 1D);
    private Node root;

    private static class Node {
        private double key;
        private int dim;
        private Point2D value;
        private Node left;
        private Node right;
        private int size;
        private RectHV area;

        public Node(int dim, Point2D value, RectHV area) {
            this.dim = dim;
            this.value = value;
            this.area = area;
            if (dim == 0) {
                key = value.x();
            } else {
                key = value.y();
            }
            this.left = null;
            this.right = null;
            this.size = 0;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return root.size;
    }

    private Node insert(Node subtree, Node parent, Point2D value, RectHV area) {
        if (subtree == null) {
            int dim = 0;
            if (parent != null) {
                dim = 1 - parent.dim;
            }
            Node node = new Node(dim, value, area);
            node.size = 1;
            return node;
        }
        if (value.equals(subtree.value)) {
            return subtree;
        }
        if (subtree.dim == 0) {
            double x = value.x();
            if (x < subtree.key) {
                area = new RectHV(subtree.area.xmin(), subtree.area.ymin(), subtree.key, subtree.area.ymax());
                subtree.left = insert(subtree.left, subtree, value, area);
            } else {
                area = new RectHV(subtree.key, subtree.area.ymin(), subtree.area.xmax(), subtree.area.ymax());
                subtree.right = insert(subtree.right, subtree, value, area);
            }
        } else {
            double y = value.y();
            if (y < subtree.key) {
                area = new RectHV(subtree.area.xmin(), subtree.area.ymin(), subtree.area.xmax(), subtree.key);
                subtree.left = insert(subtree.left, subtree, value, area);
            } else {
                area = new RectHV(subtree.area.xmin(), subtree.key, subtree.area.xmax(), subtree.area.ymax());
                subtree.right = insert(subtree.right, subtree, value, area);
            }
        }
        selfUpdate(subtree);
        return subtree;
    }

    private void selfUpdate(Node subtree) {
        int size = 0;
        if (subtree.left != null) {
            size += subtree.left.size;
        }
        if (subtree.right != null) {
            size += subtree.right.size;
        }
        subtree.size = size + 1;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        root = insert(root, null, p, AREA);
    }

    private boolean lookUp(Node subtree, Point2D p) {
        if (subtree == null) {
            return false;
        }
        if (subtree.value.equals(p)) {
            return true;
        }
        double k;
        if (subtree.dim == 0) {
            k = p.x();
        } else {
            k = p.y();
        }
        if (k < subtree.key) {
            return lookUp(subtree.left, p);
        } else {
            return lookUp(subtree.right, p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return lookUp(root, p);
    }

    private void traverse(Node subtree) {
        if (subtree != null) {
            traverse(subtree.left);
            StdDraw.setPenColor(0, 0, 0);
            subtree.value.draw();
            if (subtree.dim == 0) {
                StdDraw.setPenColor(255, 0, 0);
                double x = subtree.value.x();
                StdDraw.line(x, subtree.area.ymin(), x, subtree.area.ymax());
            } else {
                StdDraw.setPenColor(0, 0, 255);
                double y = subtree.value.y();
                StdDraw.line(subtree.area.xmin(), y, subtree.area.xmax(), y);
            }
            StdDraw.setPenColor(0, 0, 0);
            traverse(subtree.right);
        }
    }

    // draw all points to standard draw
    public void draw() {
        traverse(root);
    }

    private void find(final Node subtree, RectHV rect, ArrayList<Point2D> res) {
        if (subtree == null) {
            return;
        }
        if (!rect.intersects(subtree.area)) {
            return;
        }
        if (rect.contains(subtree.value)) {
            res.add(subtree.value);
        }
        find(subtree.left, rect, res);
        find(subtree.right, rect, res);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        ArrayList<Point2D> pts = new ArrayList<>();
        find(root, rect, pts);
        return pts;
    }

    private static class Closest {
        private Point2D point;
        private double dist;

        Closest() {
            point = null;
            dist = Double.MAX_VALUE;
        }
    }

    private void find(Node subtree, Point2D p, Closest act) {
        if (subtree == null) {
            return;
        }
        final double dist = subtree.value.distanceTo(p);
        if (dist < act.dist) {
            act.dist = dist;
            act.point = subtree.value;
        }
        if (p.equals(subtree.value)) {
            return;
        }
        double k;
        if (subtree.dim == 0) {
            k = p.x();
        } else {
            k = p.y();
        }
        double threshold = Math.abs(subtree.key - k);
        Node first;
        Node second;
        if (k < subtree.key) {
            first = subtree.left;
            second = subtree.right;
        } else {
            first = subtree.right;
            second = subtree.left;
        }
        find(first, p, act);
        if (act.dist > threshold) {
            find(second, p, act);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            return null;
        }
        Closest res = new Closest();
        find(root, p, res);
        return res.point;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        long l = System.nanoTime();
        // initialize the two data structures with point from standard input
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        long l2 = System.nanoTime();
        System.out.println(l2 - l);

    }
}
