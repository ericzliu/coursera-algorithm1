import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args)
    {
        int k = 0;
        if (args.length > 0) {
            k = Integer.parseInt(args[0]);
        }
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        while (k > 0) {
            StdOut.println(queue.dequeue());
            k -= 1;
        }
    }
}
