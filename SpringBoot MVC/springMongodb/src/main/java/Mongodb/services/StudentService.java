package Mongodb.services;

import Mongodb.entitiy.Student;
import Mongodb.repository.DepartmentRepository;
import Mongodb.repository.StudentRepository;
import Mongodb.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public Student createStudent(Student student) {
        if(student.getDepartment() != null){
            departmentRepository.save(student.getDepartment());
        }
        if(student.getSubjects() != null && student.getSubjects().size() > 0){
            subjectRepository.saveAll(student.getSubjects());
        }
        return studentRepository.save(student);
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id).get();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public String deleteStudentById(String id) {
         studentRepository.deleteById(id);
         return "Student has been Deleted";
    }

    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByName(name);
    }

    public Student getStudentsByNameAndMail(String name, String email) {
        return studentRepository.findByNameAndEmail(name,email);
    }

    public List<Student> getAllWithPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1,pageSize);
        return studentRepository.findAll(pageable).getContent();
    }


    public List<Student> sortByName() {
        Sort sort = Sort.by(Sort.Direction.ASC,"name");
        return studentRepository.findAll(sort);
    }

    public List<Student> byDeptName(String deptName) {
        return studentRepository.findByDepartment_DepartmentName(deptName);
    }

    public List<Student> getBySubject(String subName) {
        return studentRepository.findBySubjectsSubjectName(subName);
    }

    public List<Student> getNameStartsWith(String name) {
        return studentRepository.findBySubjectsSubjectName(name);
    }

    public List<Student> getByDepatmentId(String deptId) {
        return studentRepository.findByDepartmentId(deptId);
    }
}
