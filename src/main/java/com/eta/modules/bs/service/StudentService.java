package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.SchoolClass;
import com.eta.modules.bs.model.Student;
import com.eta.modules.bs.model.StudentVO;

import java.util.List;

public interface StudentService {

    List<StudentVO> getAllWithPage(StudentVO param);

    List<Student> getAll();
    List<Student> getChildList(String id);

    ProcessResult saveOrUpdate(Student param);

    Student getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

}
