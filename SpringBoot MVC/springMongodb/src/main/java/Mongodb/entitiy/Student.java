package Mongodb.entitiy;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "student")
public class Student {
    @Id
    private String id;
    private String name;
    @Field(name = "mail")
    private String email;
    @DBRef
    private Department department;
    @DBRef
    private List<Subject> subjects;
/*    @Transient
    private int percentage;

    Student(){}

    public int getPercentage() {
        if(subjects.size() > 0){
            int total = 0;
            for(Subject subject : subjects){
                total+= subject.getMarksObtained();
            }
            return total/subjects.size();
        }
        return 0;
    }

 */
}

