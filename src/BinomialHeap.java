import java.util.HashSet;
import java.util.Set;

/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{
    public int size;
    public HeapNode last;
    public HeapNode min;

    public int num_of_trees;

    public BinomialHeap(HeapNode last, HeapNode min) {
        this.size = 0;
        this.last = last;
        this.min = min;
        this.num_of_trees = 0;
    }
    public BinomialHeap()
    {
        this.size = 0;
        this.num_of_trees = 0;
    }


    public void setNum_of_trees(int num_of_trees) {
        this.num_of_trees = num_of_trees;
    }

    /**
     *
     * pre: key > 0
     *
     * Insert (key,info) into the heap and return the newly generated HeapItem.
     *
     */
    public HeapItem insert(int key, String info)
    {
        HeapItem inserted = new HeapItem(new HeapNode(), key, info);
        HeapNode inserted_node = new HeapNode(inserted, null, null, null, 0);
        inserted_node.next = inserted_node;
        inserted_node.item.node = inserted_node;
        BinomialHeap inserted_as_heap = new BinomialHeap(inserted_node, inserted_node);
        inserted_as_heap.num_of_trees = 1;
        inserted_as_heap.size = 1;
        if(this.size == 0) // if there are no elements yet we insert the first one which will be last + min
        {
            this.last = inserted_node;
            this.min = inserted_node;
            this.num_of_trees = 1;
            this.size = 1;
        }
        else
            if(this.size % 2 == 0) // inserting to even size means new root as head with O(1) work
            {
                HeapNode prev_head = this.last.next;
                this.last.next = inserted_node; // the inserted node will be head
                inserted_node.next = prev_head; // connecting the new head to the last head
                this.size+=1;
                this.num_of_trees += 1;
                if(inserted.key < this.min.item.key)
                    this.min = this.last.next; // meaning the head will also be minimum
            }
        else
        {
            this.meld(inserted_as_heap);
        }
        return inserted;
    }

    /**
     *
     * Delete the minimal item
     *
     */
    public void deleteMin()
    {
        return; // should be replaced by student code

    }

    /**
     *
     * Return the minimal HeapItem
     *
     */
    public HeapItem findMin()
    {
        if(this.empty())
            return null;
        return this.min.item;
    }

    /**
     *
     * pre: 0<diff<item.key
     *
     * Decrease the key of item by diff and fix the heap.
     *
     */
    public void decreaseKey(HeapItem item, int diff)
    {
        return; // should be replaced by student code
    }

    /**
     *
     * Delete the item from the heap.
     *
     */
    public void delete(HeapItem item)
    {
        return; // should be replaced by student code
    }
    private void link(HeapNode x, HeapNode y) // linking x to y such that y is parent of x
    {
        x.setParent(y);
        if(x == this.last) // changing last position
            this.last = y;
        if(y.child != null) // y has at least one child
        {
            x.next = y.child.next;
            y.child.next = x;
        }
        else // if x is going to be the only child then it's next will be itself
        {
            x.next = x;
        }
        y.child = x;


        y.rank+=1;
    }
    private HeapNode merge(BinomialHeap h2) // making a new linked lists rank ordered before the linking
    {
        HeapNode dummy = new HeapNode(); // initiating a dummy such that next will be the start of the list
        HeapNode tail = dummy;
        if(h2.empty())
        {
            return this.last.next;
        }
        int min1 = this.min.item.key;
        int min2 = h2.min.item.key;
        int trees1 = this.num_of_trees;
        int trees2 = h2.num_of_trees;
        this.num_of_trees = this.num_of_trees + h2.num_of_trees; // upper bound for num of trees
        this.size += h2.size;
        HeapNode head_1 = this.last.next;
        HeapNode head_2 = h2.last.next;
        while (true){
            if(trees1 == 0) // if we exhausted the trees meaning we inserted everything from this tree/heap
            {
                if(this.last.rank <= h2.last.rank)
                    this.last = h2.last;
                tail.next = head_2;
                break;
            }
            if(trees2 == 0)
            {
                tail.next = head_1;
                break;
            }
            if(head_1.rank <= head_2.rank) // merging in rank-order
            {
                tail.next = head_1;
                head_1 = head_1.next;
                trees1 -= 1;
            }
            else
            {
                tail.next = head_2;
                head_2 = head_2.next;
                trees2 -= 1;
            }
            tail = tail.next;
        }
        this.last.next = dummy.next; // connecting current last to the head (might change later)
        return dummy.next; // returning the head of the list of roots

    }
    private void update_min_pointer()
    {
        HeapNode x = this.last.next;
        int min_val = Integer.MAX_VALUE;
        if(x.next == this.last.next) // meaning num of trees is 1
            if(x.item.key < this.min.item.key)
                this.min = x;
        int trees = num_of_trees;
        while(trees > 0)
        {
            if(x.item.key < min_val)
            {
                min_val = x.item.key;
                this.min = x;
            }
            x = x.next;
            trees-=1;
        }
    }

    /**
     *
     * Meld the heap with heap2
     *
     */
    public void meld(BinomialHeap heap2)
    {
        if(this.empty()) // if we try to meld when this is empty we will get heap 2 as this meaning we need to assign fields
        {
            this.last = heap2.last;
            this.size = heap2.size;
            this.num_of_trees = heap2.num_of_trees;
            this.min = heap2.min;
        } else if (heap2.empty()) {
        } else {
            HeapNode head;
            head = merge(heap2); // getting merged list in rank-order before actually linking
            HeapNode x = head; // head of the heap
            HeapNode next_x = x.next; // next of head
            HeapNode prev_x = null; // prev of head
            while(x != last) // continue the loop while next isn't this first again and x has reached last
            {
                // if the rank of current root is smaller than next one or we have 3 roots with same rank
                // then we can skip because there is no linking to be done
                if((x.rank != next_x.rank) || ((next_x.next != head) && (next_x.next.rank == x.rank)))
                {
                    prev_x = x;
                    x = next_x;
                } else if (x.item.key <= next_x.item.key)
                {
                    x.next = next_x.next;
                    link(next_x, x);
                    this.num_of_trees -= 1;
                }
                else
                {
                    if (prev_x == null) // meaning next-x root is smaller than x so we need to switch places of head
                    {
                        this.last.next = next_x;
                        head = next_x; // we need to assign new head in-order to pass new information into first condition
                    }
                    else
                    {
                        prev_x.next = next_x; // x has key bigger than next, meaning x is no longer root so next is after prev
                    }
                    link(x, next_x);
                    this.num_of_trees -= 1;
                    x = next_x; // after finishing the link we can assign x to be next_x since its the current root after linking
                }
                next_x = x.next;
            }
            update_min_pointer();
        }
    }

    /**
     *
     * Return the number of elements in the heap
     *
     */
    public int size()
    {
        return this.size;
    }

    /**
     *
     * The method returns true if and only if the heap
     * is empty.
     *
     */
    public boolean empty()
    {
        return size == 0;
    }

    /**
     *
     * Return the number of trees in the heap.
     *
     */
    public int numTrees()
    {
        return this.num_of_trees;
    }


    /**
     * Class implementing a node in a Binomial Heap.
     *
     */
    public class HeapNode{


        public HeapItem item;
        public HeapNode child;
        public HeapNode next;
        public HeapNode parent;
        public int rank;

        public HeapNode(HeapItem item, HeapNode child, HeapNode next, HeapNode parent, int rank) {
            this.item = item;
            this.child = child;
            this.next = next;
            this.parent = parent;
            this.rank = rank;
        }
        public HeapNode() {

        }

        public HeapItem getItem() {
            return item;
        }

        public void setItem(HeapItem item) {
            this.item = item;
        }

        public HeapNode getChild() {
            return child;
        }

        public void setChild(HeapNode child) {
            this.child = child;
        }

        public HeapNode getNext() {
            return next;
        }

        public void setNext(HeapNode next) {
            this.next = next;
        }

        public HeapNode getParent() {
            return parent;
        }

        public void setParent(HeapNode parent) {
            this.parent = parent;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }

    /**
     * Class implementing an item in a Binomial Heap.
     *
     */
    public class HeapItem{
        public HeapNode node;
        public int key;
        public String info;

        public HeapItem(HeapNode node, int key, String info) {
            this.node = node;
            this.key = key;
            this.info = info;
        }

        public HeapNode getNode() {
            return node;
        }

        public void setNode(HeapNode node) {
            this.node = node;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
    public class Test{
        public static void print(BinomialHeap h) {
            System.out.println("Binomial Heap:");
            System.out.println("Size: " + h.size);

            if (h.min != null) {
                System.out.println("Minimum Node: " + h.min.item.key);
            } else {
                System.out.println("No minimum node.");
            }

            System.out.println("Heap Nodes:");
            if (h.last != null) {
                Set<HeapNode> visited = new HashSet<>();
                printHeapNode(h.last, 0, visited);
            } else {
                System.out.println("No heap nodes.");
            }
        }

    private static void printHeapNode(HeapNode node, int indentLevel, Set<HeapNode> visited) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            indent.append("    ");
        }

        System.out.println(indent + "Key: " + node.item.key);
        System.out.println(indent + "Info: " + node.item.info);
        System.out.println(indent + "Rank: " + node.rank);

        visited.add(node);

        if (node.child != null && !visited.contains(node.child)) {
            System.out.println(indent + "Child:");
            printHeapNode(node.child, indentLevel + 1, visited);
        }

        if (node.next != null && !visited.contains(node.next)) {
            System.out.println(indent + "Sibling:");
            printHeapNode(node.next, indentLevel, visited);
        }
    }

    }




}
