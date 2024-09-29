package com.mpt.journal.service;

import com.mpt.journal.model.StudentModel;
import java.util.List;

public interface StudentService {

    public List<StudentModel> findAllStudent(int page, int size);

    public List<StudentModel> findStudentsByFilter(String name, String lastName, String firstName, int page, int size);

    public StudentModel findStudentById(int id);

    public StudentModel addStudent(StudentModel student);

    public StudentModel updateStudent(StudentModel student);

    public void deleteStudent(int id);

    public void deleteMultipleStudents(List<Integer> ids);

    public void softDeleteStudent(int id);

}
