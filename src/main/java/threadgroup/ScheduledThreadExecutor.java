package threadgroup;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ScheduledThreadExecutor {

    static Logger logger = Logger.getLogger(Groups.class);


    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

        service.scheduleWithFixedDelay(() -> {
            logger.info("Working " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 100, 100, MILLISECONDS);

        Thread.sleep(10000);
        service.shutdown();
    }

}
