import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        BinomialHeap h1 = new BinomialHeap();
        h1.insert(2, "lol");
        h1.insert(5, "lol");
        h1.insert(3, "l");
        h1.insert(1, "sd");
        h1.insert(6, "d");
        h1.insert(7, "s");
        BinomialHeap.Test.print(h1);
//        BinomialHeap.Test.print(h1);

    }
    public static void displayHeap(BinomialHeap h)
    {
        System.out.print("\nHeap : ");
        if(h.last != null)
        {
            Set<BinomialHeap.HeapNode> visited = new HashSet<>();
            displayHeapRec(h.last.next, visited, ""); // start displaying from head

        }
        System.out.println("\n");
    }

    private static void displayHeapRec(BinomialHeap.HeapNode r, Set<BinomialHeap.HeapNode> visited, String spaces)
    {
        if (!visited.contains(r)) {
            visited.add(r);
            if(r.child != null)
            {
                System.out.println(r.item.key + "->");
                System.out.println(spaces + "|");

                displayHeapRec(r.child, visited, spaces + " ");
            }
            else
                System.out.print(spaces + r.item.key + " -> ");
            displayHeapRec(r.next, visited, spaces + "      ");
        }
    }

}