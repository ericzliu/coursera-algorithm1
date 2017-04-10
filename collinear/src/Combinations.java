import java.util.Arrays;
import java.util.List;

public class Combinations {

    public static <T> void combinations(T[] points, int begin, int length, T[] result, List<T[]> results) {
        if (length == 0) {
            results.add(Arrays.copyOf(result, length));
            return;
        }
        for (int i = begin; i <= points.length - length; i += 1) {
            result[result.length - length] = points[i];
            combinations(points, i + 1, length - 1, result, results);
        }
    }

}
