package tools;

import org.apache.log4j.Logger;
import tools.model.Task;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class BarrierZadanie {
    static Logger logger = Logger.getLogger(BarrierZadanie.class);

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3);
        CyclicBarrier barrierStopOthers = new CyclicBarrier(3);

        ExecutorService service = Executors.newFixedThreadPool(3);

        IntStream.rangeClosed(1, 100).forEach(it -> {
            Random r = new Random();
            Task t = Math.abs(r.nextInt()) % 100 <= 90 ? Task.PROCESS : Task.SYNCHRONIZATION;
            service.submit(new LoopTask(t, logger, barrier, barrierStopOthers));
        });

        service.shutdown();
    }

}


class LoopTask implements Runnable {

    static Logger logger;

    private Task loopTaskType;

    private CyclicBarrier barrier;
    private CyclicBarrier stopOthers;

    public LoopTask(Task loopTaskName, Logger logger, CyclicBarrier cyclicBarrier, CyclicBarrier stopOthers) {
        super();
        this.loopTaskType = loopTaskName;
        this.logger = logger;
        this.barrier = cyclicBarrier;
        this.stopOthers = stopOthers;
    }

    @Override
    public void run() {
        try {
            if (loopTaskType.equals(Task.SYNCHRONIZATION)) {
                barrier.await();
                logger.info("Synchronization");
                stopOthers.await();
            }
            if (barrier.getNumberWaiting() > 0) {
                logger.info("Stopped");
                barrier.await();
                stopOthers.await();
                logger.info("Started");
            }
            if (loopTaskType.equals(Task.PROCESS)) {
                logger.info("Process");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
