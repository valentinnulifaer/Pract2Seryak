package com.mpt.journal.repository;

import com.mpt.journal.model.StudentModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class InMemoryStudentRepository {
    private List<StudentModel> students = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public StudentModel addStudent(StudentModel student) {
        student.setId(idCounter.getAndIncrement());
        students.add(student);
        return student;
    }

    public StudentModel updateStudent(StudentModel student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                return student;
            }
        }
        return null;
    }

    public void deleteStudent(int id) {
        students.removeIf(student -> student.getId() == id);
    }

    public void deleteMultipleStudents(List<Integer> ids) {
        students.removeIf(student -> ids.contains(student.getId()));
    }

    public void softDeleteStudent(int id) {
        students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .ifPresent(student -> student.setDeleted(true));
    }

    public List<StudentModel> findAllStudents(int page, int size) {
        return students.stream()
                .filter(student -> !student.isDeleted()) // Логически удаленные студенты не отображаются
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<StudentModel> findStudentsByFilter(String name, String lastName, String firstName, int page, int size) {
        return students.stream()
                .filter(student -> !student.isDeleted()) // Игнорируем логически удаленные
                .filter(student -> (name.isEmpty() || student.getName().equalsIgnoreCase(name)) &&
                        (lastName.isEmpty() || student.getLastName().equalsIgnoreCase(lastName)) &&
                        (firstName.isEmpty() || student.getFirstName().equalsIgnoreCase(firstName)))
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}

