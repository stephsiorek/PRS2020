package locks;

import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class CounterLocks {

    static Logger logger = Logger.getLogger(CounterLocks.class);
    static Integer number = 0;
    static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {

        CounterThreadLocks counter = new CounterThreadLocks(number, lock);

        Thread t1 = new Thread(() -> {
            IntStream.rangeClosed(1, 10000).forEach(num -> {
                counter.increment();
            });
        });

        Thread t2 = new Thread(() -> {
            IntStream.rangeClosed(1, 10000).forEach(num -> {
                counter.decrement();
            });
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        logger.info("Number " + counter.getNumber());

    }
}

class CounterThreadLocks {

    Integer number;
    ReadWriteLock lock;

    public CounterThreadLocks(Integer number, ReadWriteLock lock) {
        this.number = number;
        this.lock = lock;
    }

    public void increment() {
        lock.writeLock().lock();
        number++;
        lock.writeLock().unlock();
    }


    public void decrement() {
        lock.writeLock().lock();
        number--;
        lock.writeLock().unlock();
    }

    public Integer getNumber() {
        lock.readLock().lock();
        Integer num = number;
        lock.readLock().unlock();
        return num;
    }
}
