package divideandconquer;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class ParetoFrontier {
    static class Point {
        int x, y;
        Point(int x, int y) { this.x=x; this.y=y; }
        public String toString() { return "(" + x + "," + y + ")"; }
    }

    public static List<Point> paretoFrontier(Point[] points) {
        Arrays.sort(points, (a,b) -> a.x - b.x); // Sort by x
        return paretoRec(points);
    }

    private static List<Point> paretoRec(Point[] points) {
        if (points.length <= 1) {
            List<Point> list = new ArrayList<>();
            if (points.length==1) list.add(points[0]);
            return list;
        }
        int mid = points.length/2;
        Point[] left = Arrays.copyOfRange(points, 0, mid);
        Point[] right = Arrays.copyOfRange(points, mid, points.length);

        List<Point> FL = paretoRec(left);
        List<Point> FR = paretoRec(right);

        int ymax = FR.stream().mapToInt(p -> p.y).max().orElse(Integer.MIN_VALUE);
        List<Point> res = new ArrayList<>(FR);
        for (Point p : FL) if (p.y > ymax) res.add(p);
        return res;
    }

    public static void main(String[] args) {
        Point[] points = { new Point(1,5), new Point(2,3), new Point(3,4), new Point(4,2) };
        List<Point> pareto = paretoFrontier(points);
        System.out.println("Pareto frontier: " + pareto);
    }
}
