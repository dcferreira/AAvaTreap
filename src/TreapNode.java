import java.util.Random;

/**
 * Created by Daniel-user on 26/04/2014.
 */
public class TreapNode {
    int key;
    int priority;
    TreapNode left;
    TreapNode right;

    TreapNode(int n) {
        this(n,null,null);
    }

    TreapNode(int n, TreapNode leftNode, TreapNode rightNode) {
        Random rand = new Random();
        key = n;
        left = leftNode;
        right = rightNode;
        priority = rand.nextInt();
    }

    TreapNode(int n, TreapNode leftNode, TreapNode rightNode, int prior) {
        key = n;
        left = leftNode;
        right = rightNode;
        priority = prior;
    }
}
