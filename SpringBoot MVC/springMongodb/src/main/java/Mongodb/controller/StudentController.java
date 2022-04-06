package Mongodb.controller;

import Mongodb.entitiy.Student;
import Mongodb.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public Student createStudent(@RequestBody Student student){
        return  studentService.createStudent(student);
    }
    @GetMapping("/getById/{id}")
    public Student getStudentById(@PathVariable String id){
        return studentService.getStudentById(id);
    }
    @GetMapping("/all")
    public List<Student> getAllStudent(){
        return studentService.getAllStudents();
    }
    @GetMapping("/getByName/{name}")
    public List<Student> studentByName(@PathVariable String name){
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/getByNameAndEmail")
    public Student studentByNameAndEmail(@RequestParam String name,
                                               @RequestParam String email){
        return studentService.getStudentsByNameAndMail(name, email);
    }
    @PutMapping("/update")
    public Student updateStudentById(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudentById(@PathVariable String id){
         return studentService.deleteStudentById(id);
    }

    @GetMapping("/allWithPagination")
    public List<Student> getAllWithPagination(@RequestParam int pageNo,
                                              @RequestParam int pageSize){
        return studentService.getAllWithPagination(pageNo, pageSize);
    }

    @GetMapping("/sortingByName")
    public List<Student> sortingByName(){
        return studentService.sortByName();
    }

    @GetMapping("/byDepartmentName")
    public List<Student> byDepartmentName(@RequestParam String deptName){
        return studentService.byDeptName(deptName);
    }

    @GetMapping("/bySubjectName")
    public List<Student> bySubjectName(@RequestParam String subName){
        return studentService.getBySubject(subName);
    }

    @GetMapping("/nameStartsWith")
    public List<Student> getNameStartsWith(@RequestParam String name){
        return studentService.getNameStartsWith(name);
    }
    @GetMapping("/byDepartmentId/{id}")
    public List<Student> getByDepatmentId(@RequestParam String deptId){
        return studentService.getByDepatmentId(deptId);
    }
}
