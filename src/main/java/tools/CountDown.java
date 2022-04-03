package tools;

import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CountDown {

    static Logger logger = Logger.getLogger(CountDown.class);

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Thread thread = new Thread(() -> {
            try {
                latch.await();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("I can process now");
        });

        ExecutorService service = Executors.newFixedThreadPool(3);
        IntStream.rangeClosed(1, 3).forEach(it -> {
            service.submit(() -> {
                logger.info("Working " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                logger.info("Work is done " + Thread.currentThread().getName());
            });
        });
        service.shutdown();

        thread.start();
        thread.join();

    }
}
