package collections;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMaps {

    static Logger logger = Logger.getLogger(ConcurrentMaps.class);

    public static void main(String[] args) {
        ConcurrentHashMap<Integer,String> map = new ConcurrentHashMap<Integer,String>();

        map.put(1,"value");
        map.remove(1,"valueNew");
        logger.info(map.size());
        map.remove(1,"value");
        logger.info(map.size());

        logger.info(map.searchValues(2,s -> s.equals("value")));
        map.put(2,"value");
        logger.info(map.searchValues(2,s -> s.equals("value")));

    }
}
