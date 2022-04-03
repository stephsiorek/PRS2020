package threadgroup;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SingleThreadExecutor {

    static Logger logger = Logger.getLogger(Groups.class);


    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();

        IntStream.rangeClosed(1,10).forEach(it -> {
            service.submit(() -> {
                logger.info("Working " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        Thread.sleep(1000);
        service.shutdown();
    }

}
