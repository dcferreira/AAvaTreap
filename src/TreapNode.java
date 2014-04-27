import java.util.Random;

/**
 * Created by Daniel-user on 26/04/2014.
 */
public class TreapNode {
    Integer key;
    int priority;
    TreapNode left;
    TreapNode right;

    TreapNode(Integer n) {
        this(n,null,null);
    }

    TreapNode(Integer n, TreapNode leftNode, TreapNode rightNode) {
        Random rand = new Random();
        key = n;
        left = leftNode;
        right = rightNode;
        priority = rand.nextInt();
    }

    TreapNode(Integer n, TreapNode leftNode, TreapNode rightNode, int prior) {
        key = n;
        left = leftNode;
        right = rightNode;
        priority = prior;
    }

    public boolean isNull() {
        return this == (new Treap()).root;
    }
}
