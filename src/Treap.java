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
            nullNode.priority = Integer.MIN_VALUE;
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
            if(t.left.priority > t.priority) t = rotateWithLeftChild(t);
        }

        else if(key > t.key) {
            t.right = insert(key, t.right);
            if(t.right.priority > t.priority) t = rotateWithRightChild(t);
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
                if(t.left.priority > t.right.priority) t = rotateWithLeftChild(t);
                else t = rotateWithRightChild(t);

                if(t != nullNode) t = remove(key, t);
                else t.left = nullNode;
            }
        }

        return t;
    }

    public TreapNode find(Integer k) {
        TreapNode aux = root;
        nullNode.key = k;

        while(true) {
            if(k < aux.key) aux = aux.left;
            else if(k > aux.key) aux = aux.right;
            else if(aux != nullNode) return aux;
            else return null;
        }
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
        Treap tree = new Treap();
        tree.insert(3);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);
        tree.remove(5);

        BTreePrinter.printNode(tree.root);

        System.out.println("2 " + Integer.toString(tree.find(2).priority));
        System.out.println("3 " + Integer.toString(tree.find(3).priority));
        System.out.println("7 " + Integer.toString(tree.find(7).priority));



        /*System.out.println(Integer.toString(tree.root.key) + " " + Integer.toString(tree.root.priority));
        System.out.println(Integer.toString(tree.root.left.key) + " " + Integer.toString(tree.root.left.priority));
        System.out.println(Integer.toString(tree.root.right.key) + " " + Integer.toString(tree.root.right.priority));*/
    }
}
