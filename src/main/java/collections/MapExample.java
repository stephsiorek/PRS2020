package collections;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MapExample {

    static Logger log = Logger.getLogger(MapExample.class);

    public static void main(String [ ] args) {
        HashMap<String,Long> students = new HashMap<>();
        students.put("Bill", 5L);
        students.put("John", 2L);
        students.put("Daniel", 3L);

        log.info(students.containsKey("Bill"));
        log.info(students.containsKey("Błażej"));

        log.info(students.get("Bill"));

        Set<String> zbiorStudentow = students.keySet();
        Collection<Long> zbiorOcen = students.values();
    }
}