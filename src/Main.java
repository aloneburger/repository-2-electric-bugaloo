import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        BinomialHeap h1 = new BinomialHeap();
        h1.insert(1, "lol");
        h1.insert(2, "lol");
        h1.insert(3, "l");
        h1.insert(4, "sd");
        h1.insert(5, "d");
        h1.insert(6, "s");
        h1.insert(7, "lol");
        BinomialHeap.Test.print(h1);
        BinomialHeap h2 = new BinomialHeap();
        for(int i = 25; i >=1; i--)
            h2.insert(i, "test");

        h2.meld(h1);
        BinomialHeap.Test.print(h2);

//        BinomialHeap.Test.print(h1);

    }

}