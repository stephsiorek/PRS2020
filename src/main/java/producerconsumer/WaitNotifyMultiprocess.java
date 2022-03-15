package producerconsumer;

import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class WaitNotifyMultiprocess {

    static Logger logger = Logger.getLogger(WaitNotifyMultiprocess.class);
    static Queue<Integer> tasks = new LinkedList<Integer>();
    static AtomicIntegerArray finish = new AtomicIntegerArray(2);
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();


    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerMultiprocess producer = new ProducerConsumerMultiprocess(tasks, lock, condition, finish);

        Thread p1 = new Thread(() -> producerThread(producer));
        Thread p2 = new Thread(() -> producerThread(producer));
        Thread p3 = new Thread(() -> producerThread(producer));

        Thread k1 = new Thread(() -> consumerThread(producer));
        Thread k2 = new Thread(() -> consumerThread(producer));

        Instant starts = Instant.now();

        p1.start();
        p2.start();
        p3.start();
        k1.setName("one");
        k2.setName("two");
        k1.start();
        k2.start();

        p1.join();
        p2.join();
        p3.join();
        k1.join();
        k2.join();

        Instant ends = Instant.now();

        System.out.println(Duration.between(starts, ends).toSeconds());

    }

    private static void consumerThread(ProducerConsumerMultiprocess producer) {
        try {
            boolean output = true;
            while (output) {
                output = producer.consume();
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void producerThread(ProducerConsumerMultiprocess producer) {
        IntStream.rangeClosed(1, 10).forEach(num -> {
            Random r = new Random();
            try {
                Thread.sleep(Math.abs(r.nextInt()) % 2000);
                producer.produce(Math.abs(r.nextInt() % 10000), num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

class ProducerConsumerMultiprocess {

    Logger logger = Logger.getLogger(WaitNotifyMultiprocess.class);

    Queue<Integer> queue;
    Lock lock;
    Condition condition;

    AtomicIntegerArray finish;

    public ProducerConsumerMultiprocess(Queue<Integer> queue, ReentrantLock lock, Condition condition, AtomicIntegerArray finish) {
        this.queue = queue;
        this.lock = lock;
        this.condition = condition;
        this.finish = finish;
    }

    public void produce(Integer num, Integer iter) throws InterruptedException {
        logger.info("Start produce " + num + " " + iter);
        lock.lock();
        try {
            queue.add(num);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        logger.info("Finish produce");
    }

    public boolean consume() throws InterruptedException {
        lock.lock();
        if (queue.size() == 0) {
            logger.info("Wait consume");
            condition.await(2, TimeUnit.SECONDS);
            if (queue.size() == 0) {
                finish.set(Thread.currentThread().getName().equals("one") ? 0 : 1, 1); // oznacz siebie jako gotowego do zakonczenia
            } else {
                finish.set(Thread.currentThread().getName().equals("one") ? 0 : 1, 0); // oznacz siebie jako dzialajacego
            }
            if (queue.size() == 0 && finish.get(Thread.currentThread().getName().equals("one") ? 1 : 0) != 0) {
                logger.info("QUEUE ZERO");
                lock.unlock();
                return false;
            }
        } else {
            finish.set(Thread.currentThread().getName().equals("one") ? 0 : 1, 0); // oznacz siebie jako dzialajacego
            logger.info("Start consume");
            Integer num = queue.poll();
            logger.info("Consume " + num);
        }
        lock.unlock();
        return true;
    }

}
