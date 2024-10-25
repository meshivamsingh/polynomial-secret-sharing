import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class Point {
    BigInteger x;
    BigInteger y;

    Point(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }
}

class TestCase {
    int n;
    int k;
    List<Point> points;

    TestCase(int n, int k) {
        this.n = n;
        this.k = k;
        this.points = new ArrayList<>();
    }

    void addPoint(int x, String value, int base) {
        BigInteger xBig = BigInteger.valueOf(x);
        BigInteger yBig = new BigInteger(value, base);
        points.add(new Point(xBig, yBig));
    }
}

public class SecretSharing {
    // Find the constant term using Lagrange interpolation
    private static BigInteger findConstantTerm(List<Point> points) {
        BigInteger result = BigInteger.ZERO;
        int k = points.size();

        // For each point, calculate its contribution to the constant term
        for (int i = 0; i < k; i++) {
            Point current = points.get(i);
            BigInteger term = current.y;
            
            // Calculate the Lagrange basis polynomial
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    Point other = points.get(j);
                    // Calculate -xj / (xi - xj)
                    BigInteger numerator = other.x.negate();
                    BigInteger denominator = current.x.subtract(other.x);
                    term = term.multiply(numerator).divide(denominator);
                }
            }
            
            result = result.add(term);
        }

        return result.abs(); // Return absolute value as per requirements
    }

    private static TestCase createTestCase1() {
        TestCase tc = new TestCase(4, 3);
        tc.addPoint(1, "4", 10);
        tc.addPoint(2, "111", 2);
        tc.addPoint(3, "12", 10);
        tc.addPoint(6, "213", 4);
        return tc;
    }

    private static TestCase createTestCase2() {
        TestCase tc = new TestCase(10, 7);
        tc.addPoint(1, "13444211440455345511", 6);
        tc.addPoint(2, "aed7015a346d63", 15);
        tc.addPoint(3, "6aeeb69631c227c", 15);
        tc.addPoint(4, "e1b5e05623d881f", 16);
        tc.addPoint(5, "316034514573652620673", 8);
        tc.addPoint(6, "2122212201122002221120200210011020220200", 3);
        tc.addPoint(7, "20120221122211000100210021102001201112121", 3);
        tc.addPoint(8, "20220554335330240002224253", 6);
        tc.addPoint(9, "45153788322a1255483", 12);
        tc.addPoint(10, "1101613130313526312514143", 7);
        return tc;
    }

    private static BigInteger processTestCase(TestCase testCase) {
        // Take only the first k points as required
        List<Point> points = testCase.points.subList(0, testCase.k);
        return findConstantTerm(points);
    }

    public static void main(String[] args) {
        // Process Test Case 1
        TestCase testCase1 = createTestCase1();
        BigInteger secret1 = processTestCase(testCase1);
        System.out.println("Secret for Test Case 1: " + secret1.toString());

        // Process Test Case 2
        TestCase testCase2 = createTestCase2();
        BigInteger secret2 = processTestCase(testCase2);
        System.out.println("Secret for Test Case 2: " + secret2.toString());
    }
}

//**Output:** Print secret for both the testcases simultaneously.
//Output is - Secret for Test Case 1: 3
//Output is - Secret for Test Case 2: 79836264049764