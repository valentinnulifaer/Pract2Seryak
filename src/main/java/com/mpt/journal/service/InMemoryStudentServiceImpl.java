package com.mpt.journal.service;

import com.mpt.journal.entity.StudentEntity;
import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.InMemoryStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;


//Сервисный слой отвечает за бизнес-логику приложения. Он использует репозиторий для выполнения операций с данными и может включать дополнительные проверки или преобразования данных
//так же мы тут можем настроить инкапсуляцию
//А если простыми словами тут происходит разделенние запросов от контроллера к сервису
@Service
public class InMemoryStudentServiceImpl implements StudentService {

    private final InMemoryStudentRepository studentRepository;

    public InMemoryStudentServiceImpl(InMemoryStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentModel> findAllStudent(int page, int size) {
        return studentRepository.findAllStudents(page, size);
    }

    @Override
    public List<StudentModel> findStudentsByFilter(String name, String lastName, String firstName, int page, int size) {
        return studentRepository.findStudentsByFilter(name, lastName, firstName, page, size);
    }

    @Override
    public StudentModel addStudent(StudentModel student) {
        return studentRepository.addStudent(student);
    }

    @Override
    public StudentModel updateStudent(StudentModel student) {
        List<StudentModel> students = studentRepository.findAllStudents(0, Integer.MAX_VALUE);

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                return student;
            }
        }
        return null;
    }

    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteStudent(id);
    }

    @Override
    public void deleteMultipleStudents(List<Integer> ids) {
        studentRepository.deleteMultipleStudents(ids); // Реализация удаления нескольких студентов
    }

    @Override
    public void softDeleteStudent(int id) {
        studentRepository.softDeleteStudent(id); // Логическое удаление
    }

    @Override
    public StudentModel findStudentById(int id) {
        return studentRepository.findAllStudents(0, Integer.MAX_VALUE).stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
