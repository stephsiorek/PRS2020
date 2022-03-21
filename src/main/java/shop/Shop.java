package shop;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Shop {

    static Logger logger = Logger.getLogger(Shop.class);
    ArrayList<Lock> lock = new ArrayList<>();
    EnumMap<Items,Integer> warehouse = new EnumMap(Items.class);


    public Shop(Lock lock) {
        this.lock.add(new ReentrantLock());
        this.lock.add(new ReentrantLock());
        this.lock.add(new ReentrantLock());
        warehouse.put(Items.GARNEK, 100);
        warehouse.put(Items.TALERZ, 20);
        warehouse.put(Items.SZKLANKA, 80);
    }

    public void purchase(Items item) {
        lock.get(item.ordinal()).lock();
        logger.info("Byuing " + item);
        int stockSize = warehouse.get(item);
        if(stockSize > 0) {
            logger.info("Purchase Accepted - Stock size " + stockSize);
            warehouse.put(item, warehouse.get(item)-1);
        } else {
            logger.info("Purchase Declined - no items in the store");
        }
        lock.get(item.ordinal()).unlock();
    }

    public static void main(String args[]) {
        Lock lock = new ReentrantLock();
        Shop obj = new Shop(lock);

        Thread thread1 = new Thread(() -> IntStream.rangeClosed(1, 50).forEach(it -> {
            Random r = new Random();
            Items item = Items.values()[Math.abs(r.nextInt() % Items.values().length)];
            obj.purchase(item);
        }));
        Thread thread2 = new Thread(() -> IntStream.rangeClosed(1, 50).forEach(it -> {
            Random r = new Random();
            Items item = Items.values()[Math.abs(r.nextInt() % Items.values().length)];
            obj.purchase(item);
        }));
        Thread thread3 = new Thread(() -> IntStream.rangeClosed(1, 50).forEach(it -> {
            Random r = new Random();
            Items item = Items.values()[Math.abs(r.nextInt() % Items.values().length)];
            obj.purchase(item);
        }));
        Thread thread4 = new Thread(() -> IntStream.rangeClosed(1, 50).forEach(it -> {
            Random r = new Random();
            Items item = Items.values()[Math.abs(r.nextInt() % Items.values().length)];
            obj.purchase(item);
        }));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
