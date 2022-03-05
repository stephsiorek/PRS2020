package collections;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadCollectionsExample {

    static Logger log = Logger.getLogger(MultiThreadCollectionsExample.class);

    public static void main(String [ ] args) {

        List<Integer> numbers = new ArrayList<>();

        Thread t1 = new Thread(() -> {
            for(int i=0;i<1000;i++) {
                numbers.add(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i=0;i<1000;i++) {
                numbers.add(i);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            log.error(e);
        }

        log.info("Finish " +  numbers.size());
    }
}