/**
 * Created by Daniel-user on 22/04/2014.
 */
public class Treap {
    TreapNode root;
    static TreapNode nullNode;
        static
        {
            nullNode = new TreapNode(null);
            nullNode.left = nullNode;
            nullNode.right = nullNode;
            nullNode.priority = Integer.MAX_VALUE;
        }

    public Treap() {
        root = nullNode;
    }

    public void insert(int key) {
        root = insert(key,root);
    }

    private TreapNode insert(int key, TreapNode t) {
        if(t == nullNode) t = new TreapNode(key, nullNode, nullNode);

        else if(key < t.key) {
            t.left = insert(key, t.left);
            if(t.left.priority < t.priority) t = rotateWithLeftChild(t);
        }

        else if(key > t.key) {
            t.right = insert(key, t.right);
            if(t.right.priority < t.priority) t = rotateWithRightChild(t);
        }
        // Caso contrário é repetido, e portanto não faz nada

        return t;
    }

    public void remove(int key) {
        root = remove(key,root);
    }

    private TreapNode remove(int key, TreapNode t) {
        if(t != nullNode) {
            if(key < t.key) t.left = remove(key, t.left);
            else if(key > t.key) t.right = remove(key, t.right);

            else { // encontra a chave a remover
                if(t.left.priority < t.right.priority) t = rotateWithLeftChild(t);
                else t = rotateWithRightChild(t);

                if(t != nullNode) t = remove(key, t);
                else t.left = nullNode;
            }
        }

        return t;
    }

    static TreapNode rotateWithLeftChild(TreapNode t) {
        TreapNode out = t.left;
        t.left = out.right;
        out.right = t;
        return out;
    }

    static TreapNode rotateWithRightChild(TreapNode t) {
        TreapNode out = t.right;
        t.right = out.left;
        out.left = t;
        return out;
    }




    public static void main(String[] args) {
        System.out.println("teste");
    }
}
