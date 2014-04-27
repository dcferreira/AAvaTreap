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

    private TreapNode insert(int key, TreapNode t, int priority) {
        if(t == nullNode) t = new TreapNode(key, nullNode, nullNode, priority);

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

    public Integer findMax() {
        if(this.root == nullNode) return null;

        TreapNode aux = root;
        while(aux.right != nullNode) aux = aux.right;

        return aux.key;
    }

    public Integer findMin() {
        if(this.root == nullNode) return null;

        TreapNode aux = root;
        while(aux.left != nullNode) aux = aux.left;

        return aux.key;
    }

   /*static private TreapNode rotate(TreapNode t) {
        if(t.left.priority > t.priority) {
            t = rotateWithLeftChild(t);
            t.left = rotate(t.left);
        }
        else if(t.right.priority > t.priority) {
            t = rotateWithRightChild(t);
            t.right = rotate(t.right);
        }

        return t;
    }*/
   static private TreapNode rotate(TreapNode t) {
       if(t.left.priority > t.priority) {
           t.left = rotate(t.left);
           return rotateWithLeftChild(t);
       }
       else if(t.right.priority > t.priority) {
           t.right = rotate(t.right);
           return rotateWithRightChild(t);
       }

       return t;
   }



    static public Treap join(Treap S1, Integer k, Treap S2) {
        Treap out = new Treap();
        if(S1.findMax() < k && S2.findMin() > k) {
            out.insert(k);
            out.root.left = S1.root;
            out.root.right = S2.root;

            out.root = rotate(out.root);
        } else {
            System.out.println("Erro! O valor máximo da primeira árvore tem de ser menor que k e o valor mínimo da segunda tem de ser maior que k");
        }
        return out;
    }

    static public Treap paste(Treap S1, Treap S2) {
        Integer k = S1.findMax();
        S1.remove(k);
        Treap out = join(S1, k, S2);

        return out;
    }

    static public Treap[] split(Integer k, Treap S) {
        Treap[] out = new Treap[2];
        S.remove(k);
        S.insert(k,S.root,Integer.MAX_VALUE);
        out[0] = new Treap();
        out[0].root = S.root.left;
        out[1]= new Treap();
        out[1].root = S.root.right;

        return out;
    }

    public static void main(String[] args) {
        Treap tree = new Treap();
        tree.insert(3);
        tree.insert(5);
        tree.insert(2);
        tree.insert(7);

        Treap tree2 = new Treap();
        tree2.insert(15);
        tree2.insert(13);
        tree2.insert(10);

        Treap tree3 = join(tree, 9, tree2);

        BTreePrinter.printNode(tree3.root);

        System.out.println("2 " + Integer.toString(tree.find(2).priority));
        System.out.println("3 " + Integer.toString(tree.find(3).priority));
        System.out.println("5 " + Integer.toString(tree.find(5).priority));
        System.out.println("7 " + Integer.toString(tree.find(7).priority));
        System.out.println("9 " + Integer.toString(tree3.find(9).priority));
        System.out.println("10 " + Integer.toString(tree2.find(10).priority));
        System.out.println("13 " + Integer.toString(tree2.find(13).priority));
        System.out.println("15 " + Integer.toString(tree2.find(15).priority));

        /*System.out.println(Integer.toString(tree.root.key) + " " + Integer.toString(tree.root.priority));
        System.out.println(Integer.toString(tree.root.left.key) + " " + Integer.toString(tree.root.left.priority));
        System.out.println(Integer.toString(tree.root.right.key) + " " + Integer.toString(tree.root.right.priority));*/
    }
}
