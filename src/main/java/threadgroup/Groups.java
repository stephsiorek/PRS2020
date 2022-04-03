package threadgroup;

import org.apache.log4j.Logger;

public class Groups {

    static Logger logger = Logger.getLogger(Groups.class);

    public static void main(String[] args) {
        logger.info(Runtime.getRuntime().availableProcessors());

        logger.info(Thread.currentThread().getThreadGroup().getName());
        logger.info(Thread.currentThread().getThreadGroup().getParent().getName());

        ThreadGroup group = new ThreadGroup("moja grupa");
        // tworzymy podgrupę
        ThreadGroup group_sec = new ThreadGroup(group, "moja druga grupa");
        Thread thread1 = new Thread(group_sec, () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(group_sec, () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        group_sec.list();

        logger.info(group.activeCount());
        logger.info(group_sec.activeCount());

    }
}
