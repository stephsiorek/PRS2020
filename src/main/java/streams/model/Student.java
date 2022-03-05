package streams.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Student {

    String name;

    String surname;

    String className;

    Long testResults;

    double grade;

    List<Hobbies> hobbiesList;

}
