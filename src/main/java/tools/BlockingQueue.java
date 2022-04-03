package tools;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

public class BlockingQueue {

    static Logger logger = Logger.getLogger(BlockingQueue.class);

    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> sharedQ = new LinkedBlockingQueue<Integer>();

        ExecutorService producers = Executors.newFixedThreadPool(1);
        ExecutorService consumers = Executors.newFixedThreadPool(5);

        IntStream.rangeClosed(1, 5).forEach(itt -> {
            consumers.submit(() -> {
                while (true) {
                    logger.info("Consuming");
                    Integer num = null;
                    try {
                        num = sharedQ.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("Consumed " + num);
                }
            });
        });

        IntStream.rangeClosed(1, 1).forEach(itt -> {
            producers.submit(() -> {
                IntStream.rangeClosed(1, 100).forEach(it -> {
                    int r = new Random().nextInt();
                    logger.info("Producing " + r);
                    try {
                        Thread.sleep(100);
                        sharedQ.put(r);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            });
        });



        consumers.shutdown();
        producers.shutdown();
    }
}
