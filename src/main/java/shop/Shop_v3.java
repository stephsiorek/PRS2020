package shop;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;

public class Shop_v3 {

    static Logger logger = Logger.getLogger(Shop_v3.class);
    ArrayList<Lock> lock = new ArrayList<>();
    ArrayList<Lock> lockZwroty = new ArrayList<>();
    EnumMap<Items, Integer> warehouse = new EnumMap(Items.class);
    EnumMap<Items, Integer> warehouseZwroty = new EnumMap(Items.class);

    public Shop_v3(Lock lock) {
        this.lock.add(new ReentrantLock());
        this.lock.add(new ReentrantLock());
        this.lock.add(new ReentrantLock());
        warehouse.put(Items.GARNEK, 100);
        warehouse.put(Items.TALERZ, 80);
        warehouse.put(Items.SZKLANKA, 20);

        this.lockZwroty.add(new ReentrantLock());
        this.lockZwroty.add(new ReentrantLock());
        this.lockZwroty.add(new ReentrantLock());
        warehouseZwroty.put(Items.GARNEK, 0);
        warehouseZwroty.put(Items.TALERZ, 0);
        warehouseZwroty.put(Items.SZKLANKA, 0);
    }

    public void purchase(Items item) {
        if (item.equals(Items.TALERZ)) {
            lock.get(Items.SZKLANKA.ordinal()).lock();
        }
        lock.get(item.ordinal()).lock();
        logger.info("Byuing " + item);
        int stockSize = warehouse.get(item);
        if (stockSize > 0) {
            if (item.equals(Items.TALERZ)) {
                if (warehouse.get(Items.SZKLANKA) > 0) {
                    warehouse.put(Items.SZKLANKA, warehouse.get(Items.SZKLANKA) - 1);
                    warehouse.put(item, warehouse.get(item) - 1);
                    logger.info("Purchase Accepted - Stock size " + stockSize + " SZKLANKI " + warehouse.get(Items.SZKLANKA));
                } else {
                    logger.info("Purchase Declined - no items in the store");
                }
            } else {
                logger.info("Purchase Accepted - Stock size " + stockSize);
                warehouse.put(item, warehouse.get(item) - 1);
            }
        } else {
            if(warehouseZwroty.get(item) > -stockSize) {
                if (item.equals(Items.TALERZ)) {
                    if (warehouseZwroty.get(Items.SZKLANKA) > -warehouse.get(Items.SZKLANKA)) {
                        warehouse.put(Items.SZKLANKA, warehouse.get(Items.SZKLANKA) - 1);
                        warehouse.put(item, warehouse.get(item) - 1);
                        logger.info("Purchase from Returns ACCEPTED - Stock size " + stockSize + " SZKLANKI " + warehouse.get(Items.SZKLANKA));
                    } else {
                        logger.info("Purchase Declined - no items in the store");
                    }
                } else {
                    warehouse.put(item, warehouse.get(item) - 1);
                    logger.info("Purchase from Returns ACCEPTED - Stock size " +  warehouse.get(item));
                }
            }
            logger.info("Purchase Declined - no items in the store");
        }
        if (item.equals(Items.TALERZ)) {
            lock.get(Items.SZKLANKA.ordinal()).unlock();
        }
        lock.get(item.ordinal()).unlock();
    }

    public void returns(Items item) {
        lockZwroty.get(item.ordinal()).lock();
        warehouseZwroty.put(item, warehouse.get(item) + 1);
        lockZwroty.get(item.ordinal()).unlock();

        lock.get(item.ordinal()).lock();
        logger.info("Return " + item);
        warehouse.put(item, warehouse.get(item) + 1);
        logger.info("Return Accepted - Stock size " + warehouse.get(item));
        warehouseZwroty.put(item, warehouse.get(item) - 1);
        lock.get(item.ordinal()).unlock();
    }

    public static void main(String args[]) {
        Lock lock = new ReentrantLock();
        Shop_v3 obj = new Shop_v3(lock);

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
        Thread thread5 = new Thread(() -> IntStream.rangeClosed(1, 50).forEach(it -> {
            Random r = new Random();
            Items item = Items.values()[Math.abs(r.nextInt() % Items.values().length)];
            obj.returns(item);
        }));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
