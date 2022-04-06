package Mongodb.entitiy;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "department")
public class Department {
    @Id
    private String id;
    @Field(name = "departement_name")
    private String departmentName;
    private String location;


}
