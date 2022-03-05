package collections;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapExample {

    static Logger log = Logger.getLogger(TreeMapExample.class);

    public static void main(String[] args) {
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "TreeMap");
        hashMap.put(32, "vs");
        hashMap.put(21, "HashMap");

        // unordered
        for (Integer el : hashMap.keySet()) {
            log.info("Element " + el);
        }

        Map<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "TreeMap");
        treeMap.put(32, "vs");
        treeMap.put(21, "HashMap");

        // ordered
        for (Integer el : treeMap.keySet()) {
            log.info("Element " + el);
        }
    }
}
