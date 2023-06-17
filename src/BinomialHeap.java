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
        BinomialHeap inserted_as_heap = new BinomialHeap(inserted_node, inserted_node);
        inserted_as_heap.num_of_trees = 1;
        inserted_as_heap.size = 1;
        if(this.size % 2 == 0) // inserting to even size means new root as last with O(1) work
        {
            this.merge(inserted_as_heap);
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
        return null; // should be replaced by student code
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
    private void link(HeapNode x, HeapNode y)
    {
        x.setParent(y);
        if(y.child != null) // y has at least one child
        {
            x.next = y.child.next;
            y.child.next = x;
        }
        y.child = x;
        y.rank+=1;
    }
    private HeapNode merge(BinomialHeap this, BinomialHeap h2)
    {
        HeapNode dummy = new HeapNode();

        HeapNode tail = dummy;
        if(this.empty())
        {
            this.num_of_trees += h2.num_of_trees;
            this.size += h2.size;
            return h2.last.next;
        }
        if(h2.empty())
        {
            return this.last.next;
        }
        int trees1 = this.num_of_trees;
        int trees2 = h2.num_of_trees;
        this.num_of_trees = this.num_of_trees + h2.num_of_trees; // upper bound for num of trees
        this.size += h2.size;
        HeapNode head_1 = this.last.next;
        HeapNode head_2 = h2.last.next;
        while (true){
            if(trees1 == 0) // if we exhausted the trees meaning we inserted everything from this
            {
                tail.next = head_2;
                this.last = h2.last; // h2 still has more nodes which means it has the correct last
                break;
            }
            if(trees2 == 0)
            {
                tail.next = head_1;
                break;
            }
            if(head_1.item.key <= head_2.item.key)
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
        this.last.next = dummy.next; // connecting last with head
        return dummy.next; // the head
    }
    /**
     *
     * Meld the heap with heap2
     *
     */
    public void meld(BinomialHeap heap2)
    {
        if(this.empty())
        {
            this.last = heap2.last;
            this.size = heap2.size;
            this.num_of_trees = heap2.num_of_trees;
            this.min = heap2.min;
        }
        else {
            HeapNode head;
            head= merge(heap2);
            HeapNode x = head; // head of the heap
            HeapNode next_x = x.next; // next of head
            HeapNode prev_x = null; // prev of head
            while(next_x != head) // continue the loop while next isn't this first again
            {
                // if the rank current is smaller than next one or the rank of x is not the same as the rank of the next of next
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
                    }
                    else
                    {
                        prev_x.next = next_x;
                    }
                    link(x, next_x);
                    this.num_of_trees -= 1;
                    x = next_x;
                }
                next_x = next_x.next;
            }
        }
    }

    /**
     *
     * Return the number of elements in the heap
     *
     */
    public int size()
    {
        return 42; // should be replaced by student code
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
        return 0; // should be replaced by student code
    }


    /**
     * Class implementing a node in a Binomial Heap.
     *
     */
    public static class HeapNode{


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
    public static class HeapItem{
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
        public void test()
        {
            HeapItem first = new HeapItem(new HeapNode(), 1, "lol");
            HeapNode first_node = new HeapNode();
            first_node.next = first_node;
            first_node.rank = 0;
            first_node.item = first;
            HeapItem second = new HeapItem(new HeapNode(), 2, "lol");
            HeapNode second_node = new HeapNode();
            second_node.next = second_node;
            second_node.rank = 0;
            second_node.item = second;
            BinomialHeap h1 = new BinomialHeap(first_node, first_node);
            h1.insert(2, "lol");

        }
    }




}
