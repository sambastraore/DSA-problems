package divideandconquer;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;



public class ClosestPair {
    static class Pair {
        Point p1, p2;
        double dist;
        Pair(Point a, Point b) {
            p1 = a; p2 = b;
            dist = a.distance(b);
        }
    }

    public static Pair closestPair(Point[] points) {
        Point[] Px = points.clone();
        Arrays.sort(Px, Comparator.comparingInt(p -> p.x));
        return closestPairRec(Px);
    }

    private static Pair closestPairRec(Point[] Px) {
        int n = Px.length;
        if (n <= 3) {
            return bruteForce(Px);
        }
        int mid = n / 2;
        Point midPoint = Px[mid];

        Point[] left = Arrays.copyOfRange(Px, 0, mid);
        Point[] right = Arrays.copyOfRange(Px, mid, n);

        Pair dl = closestPairRec(left);
        Pair dr = closestPairRec(right);

        Pair d = (dl.dist < dr.dist) ? dl : dr;

        // Create strip
        Point[] strip = Arrays.stream(Px)
                .filter(p -> Math.abs(p.x - midPoint.x) < d.dist)
                .toArray(Point[]::new);

        Arrays.sort(strip, Comparator.comparingInt(p -> p.y));

        // Check strip
        for (int i = 0; i < strip.length; i++) {
            for (int j = i+1; j < strip.length && (strip[j].y - strip[i].y) < d.dist; j++) {
                double dist = strip[i].distance(strip[j]);
                if (dist < d.dist) d = new Pair(strip[i], strip[j]);
            }
        }
        return d;
    }

    private static Pair bruteForce(Point[] points) {
        Pair minPair = new Pair(points[0], points[0]);
        for (int i = 0; i < points.length; i++)
            for (int j = i+1; j < points.length; j++)
                if (points[i].distance(points[j]) < minPair.dist)
                    minPair = new Pair(points[i], points[j]);
        return minPair;
    }

    public static void main(String[] args) {
        Point[] points = {new Point(2,3), new Point(12,30), new Point(40,50), new Point(5,1)};
        Pair res = closestPair(points);
        System.out.println("Closest pair: (" + res.p1 + ") and (" + res.p2 + ") dist=" + res.dist);
    }
}
