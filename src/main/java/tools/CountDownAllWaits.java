package tools;

import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CountDownAllWaits {

    static Logger logger = Logger.getLogger(CountDownAllWaits.class);

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Thread thread = new Thread(() -> {
            IntStream.rangeClosed(1, 3).forEach(it -> {
                logger.info("Process step " + it);
                try {
                    Thread.sleep(1000);
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        });

        ExecutorService service = Executors.newFixedThreadPool(3);
        IntStream.rangeClosed(1, 3).forEach(it -> {
            service.submit(() -> {
                logger.info("Waiting " + Thread.currentThread().getName());
                try {
                    latch.await();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("Proceed " + Thread.currentThread().getName());
            });
        });
        service.shutdown();

        thread.start();
        thread.join();

    }
}
