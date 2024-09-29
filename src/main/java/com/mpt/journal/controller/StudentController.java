package com.mpt.journal.controller;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Получение всех студентов с фильтрацией и пагинацией
    @GetMapping("/students")
    public String getAllStudents(@RequestParam(defaultValue = "") String name,
                                 @RequestParam(defaultValue = "") String lastName,
                                 @RequestParam(defaultValue = "") String firstName,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        List<StudentModel> students = studentService.findStudentsByFilter(name, lastName, firstName, page, size);
        model.addAttribute("students", students);
        return "studentList";
    }

    // Добавление нового студента
    @PostMapping("/students/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String middleName) {
        StudentModel newStudent = new StudentModel(0, name, lastName, firstName, middleName);
        studentService.addStudent(newStudent);
        return "redirect:/students";
    }

    // Обновление существующего студента
    @PostMapping("/students/update")
    public String updateStudent(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String middleName) {
        StudentModel updatedStudent = new StudentModel(id, name, lastName, firstName, middleName);
        studentService.updateStudent(updatedStudent);
        return "redirect:/students";
    }

    // Физическое удаление студента
    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam int id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    // Множественное удаление студентов
    @PostMapping("/students/delete-multiple")
    public String deleteMultipleStudents(@RequestParam(value = "ids", required = false) List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            studentService.deleteMultipleStudents(ids); // Удаление по списку идентификаторов
        }
        return "redirect:/students";
    }

    // Логическое удаление студента
    @PostMapping("/students/soft-delete")
    public String softDeleteStudent(@RequestParam int id) {
        studentService.softDeleteStudent(id);
        return "redirect:/students";
    }
}
