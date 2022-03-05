package lambda;

import org.apache.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaExample {

    static Logger log = Logger.getLogger(LambdaExample.class);

    public static void main(String [ ] args) {

        //*******************************

        // Nic nie przyjmuje, zwraca wartość
        Supplier<Integer> produceInt = () -> 5;

        log.info("Od producenta " + produceInt.get());

        //*******************************

        // Przyjmuje argument, nic nie zwraca
        Consumer<Integer> consumeInt = (par) -> log.info("funkcja konsumująca " + par);

        consumeInt.accept(5);

        //*******************************

        // Nic nie zwraca nie nie przymuje
        Runnable a = () -> log.info("funkcja bezparametrowa");

        a.run();

        //*******************************

        // Funkcja z argumentem wejściowym i wyjściowym
        Function<Integer, String> simple = (par) -> "funkcja prosta " + par;

        log.info(simple.apply(5));

        //*******************************

        Function<Integer, String> complex = (par) -> {
            log.info("funkcja złożona " + par);
            return "end";
        };

        complex.apply(5);
    }
}