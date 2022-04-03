package tools;

import org.apache.log4j.Logger;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class BarrierRunOnBarrierReached {
    static Logger logger = Logger.getLogger(BarrierRunOnBarrierReached.class);

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3, new Thread(() -> {
            logger.info("I can process now");
        }));

        ExecutorService service = Executors.newFixedThreadPool(3);
        IntStream.rangeClosed(1, 9).forEach(it -> {
            service.submit(() -> {
                logger.info("Working " + Thread.currentThread().getName());
                try {
                    logger.info("WAIT " + Thread.currentThread().getName());
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        });

        Thread.sleep(1000);
        service.shutdown();
    }

}
