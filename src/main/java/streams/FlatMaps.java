package streams;

import org.apache.log4j.Logger;
import streams.model.Student;

import java.util.List;

public class FlatMaps {
    static Logger log = Logger.getLogger(FlatMaps.class);

    List<Student> students;

    public FlatMaps(List<Student> students) {
        this.students = students;
    }


    public void printStudentHobbies() {
        log.info("Students hobbies - MAP");
        students.stream()
                .map(student -> student.getHobbiesList())
                .distinct()
                .forEach(s -> log.info(s));
    }

    public void printDistinctStudentHobbies() {
        log.info("Students hobbies separate - FLATMAP");
        students.stream()
                .flatMap(student -> student.getHobbiesList().stream())
                .distinct()
                .forEach(s -> log.info(s));
    }

}
