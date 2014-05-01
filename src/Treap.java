import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

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
            t.left = insert(key, t.left, priority);
            if(t.left.priority > t.priority) t = rotateWithLeftChild(t);
        }

        else if(key > t.key) {
            t.right = insert(key, t.right, priority);
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

   static private TreapNode rotate(TreapNode t) {
       if(t.left.priority > t.priority && t.left.priority > t.right.priority) { // troca com o filho que tiver prioridade maior
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

        return join(S1, k, S2);
    }

    static public int depth(TreapNode T) {
        if(T == nullNode) return 0;
        else return Math.max(depth(T.left),depth(T.right)) + 1;
    }

    static public Treap[] split(Integer k, Treap S) { // este método "danifica" a Treap original -- usar com cuidado
        Treap[] out = new Treap[2];
        S.remove(k);
        S.root = S.insert(k,S.root,Integer.MAX_VALUE);
        if(!S.root.key.equals(k)) System.out.println("Erro!!!!! A chave não foi para a raiz da árvore");
        out[0] = new Treap();
        out[0].root = S.root.left;
        out[1]= new Treap();
        out[1].root = S.root.right;

        return out;
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        /*Treap tree = new Treap();
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

        Treap[] Trees = split(9,tree3);
        BTreePrinter.printNode(tree.root);
        BTreePrinter.printNode(Trees[0].root);
        BTreePrinter.printNode(tree2.root);
        BTreePrinter.printNode(Trees[1].root);*/

        /*String insertCSV = "insert.csv", removeCSV = "remove.csv";
        PrintWriter writer = new PrintWriter(insertCSV,"UTF-8");
        PrintWriter removeW = new PrintWriter(removeCSV,"UTF-8");

        int nmax = 100000,n, rep = 10000;
        long insertStartTime, insertEndTime, removeStartTime, removeEndTime;
        Treap[] tree = new Treap[1];
        Random rand = new Random();
        long[] times = new long[nmax];
        long insertTempTimes, removeTempTimes;

        for(int i=0; i < tree.length; i++) {
            tree[i] = new Treap();
        }
        for(int i=-1000; i < nmax; i++) {
            for(int j=0; j < tree.length; j++) {
                n = rand.nextInt();
                while (tree[j].find(n) != null) n = rand.nextInt();
                insertTempTimes = 0;
                removeTempTimes = 0;
                for(int iteration = 0; iteration < rep; iteration++) {
                    insertStartTime = System.nanoTime();
                    tree[j].insert(n);
                    insertEndTime = System.nanoTime();

                    removeStartTime = System.nanoTime();
                    tree[j].remove(n);
                    removeEndTime = System.nanoTime();

                    insertTempTimes += insertEndTime - insertStartTime;
                    removeTempTimes += removeEndTime - removeStartTime;
                }
                if(i>=0) {
                    writer.println(Integer.toString(i) + "," + Long.toString(insertTempTimes/rep));
                    removeW.println(Integer.toString(i) + "," + Long.toString(removeTempTimes/rep));
                }

            }
        }
        writer.close();*/


        PrintWriter writer = new PrintWriter("depth.csv","UTF-8");
        Treap[] trees = new Treap[10];
        int nmax = 10000,n;
        Random rand = new Random();

        writer.println("\"n\",\"depth\"");

        for(int j=0; j < trees.length; j++) {
            trees[j] = new Treap();
        }

        for(int i=1; i < nmax; i++) {
            for(int j=0; j < trees.length; j++) {
                n = rand.nextInt();
                while (trees[j].find(n) != null) n = rand.nextInt();
                trees[j].insert(n);
                writer.println(Integer.toString(i) + "," + Integer.toString(depth(trees[j].root)));
            }
            System.out.println(i);
        }
        writer.close();
    }
}
