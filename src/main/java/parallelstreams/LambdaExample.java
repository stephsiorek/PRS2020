package parallelstreams;

import org.apache.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class LambdaExample {

    static Logger log = Logger.getLogger(LambdaExample.class);
    static int i = 0;

    public static void main(String [ ] args) {

        IntStream stream = IntStream.range(1, 100);
        stream.parallel().forEach(number -> {
            if (number > i) {
                log.info(i + " smaller than " + number);
                        i+=2;
            }
        });
        log.info("i at the end " + i);

        //Sum from 1 to n in streams - this works properly
        int result = IntStream.rangeClosed(1, 15).parallel().reduce(0, (x, y) -> { log.info(x+y); return x + y;});
        log.info(result);



    }
}