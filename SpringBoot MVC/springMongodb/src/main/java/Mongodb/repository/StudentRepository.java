package Mongodb.repository;

import Mongodb.entitiy.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student,String> {

    List<Student> findByName(String name);
    List<Student> findByDepartment_DepartmentName(String deptName);
    List<Student> findBySubjectsSubjectName(String subName);
    List<Student> findByNameStartsWith(String name);
    Student findByNameAndEmail(String name, String mail);
    List<Student> findByDepartmentId(String deptId);

}
