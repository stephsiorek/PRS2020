package debug;

import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class DebugExample {

    static Logger log = Logger.getLogger(DebugExample.class);

    public static void main(String [ ] args) {

        Long silnia = 1L;

        for(int i = 1;i<19;i++ ) {
            silnia = silnia * i;
        }

        log.info("Silnia 19 : " + silnia);
    }
}