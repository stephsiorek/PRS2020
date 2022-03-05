package streams;

import org.apache.log4j.Logger;
import streams.model.Student;

import java.util.List;
import java.util.Optional;

public class Terminals {
    static Logger log = Logger.getLogger(Terminals.class);

    List<Student> students;

    public Terminals(List<Student> students) {
        this.students = students;
    }


    public void isThereStudentWhoFailExam() {
        log.info("Is there a student who fail exam");
        log.info(students.stream()
                .anyMatch(student -> student.getGrade() == 2));
    }

    public void hasAllStudentsPassExam() {
        log.info("Has all students pass the exam");
        log.info(students.stream()
                .allMatch(student -> student.getGrade() > 2));
    }

    public void hasAllStudentsPassExamVs2() {
        log.info("Has all students pass the exam vs 2");
        log.info(students.stream()
                .noneMatch(student -> student.getGrade() == 2));
    }

    public void findRandomStudentWhoGet3() {
        log.info("Find trójkowicz");
        Optional<Student> prymus = students.stream()
                .filter(student -> student.getGrade() == 3)
                .findAny();
        log.info("Prymus: " + prymus.get().getName() + " " + prymus.get().getSurname());
    }

    public void findFirstStudentWhoGet3() {
        log.info("Find first trójkowicz");
        Optional<Student> prymus = students.stream()
                .filter(student -> student.getGrade() == 3)
                .findFirst();
        log.info("Prymus: " + prymus.get().getName() + " " + prymus.get().getSurname());
    }

    public void removeStudentsWhoFailExam() {
        log.info("Students number before exam " + students.size());
        students.removeIf(student -> student.getGrade() == 2);
        log.info("Students number after exam " + students.size());
    }



}
