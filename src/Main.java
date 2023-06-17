

public class Main {
    public static void main(String[] args) {

        BinomialHeap.HeapItem first = new BinomialHeap.HeapItem(new BinomialHeap.HeapNode(), 1, "lol");
        BinomialHeap.HeapNode first_node = new BinomialHeap.HeapNode();
        first_node.next = first_node;
        first_node.rank = 0;
        first_node.item = first;
        BinomialHeap.HeapItem second = new BinomialHeap.HeapItem(new BinomialHeap.HeapNode(), 2, "lol");
        BinomialHeap.HeapNode second_node = new BinomialHeap.HeapNode();
        second_node.next = second_node;
        second_node.rank = 0;
        second_node.item = second;
        BinomialHeap h1 = new BinomialHeap(first_node, first_node);
        h1.insert(2, "lol");
        h1.insert(5, "lol");
    }
}