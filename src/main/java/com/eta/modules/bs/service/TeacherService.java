package com.eta.modules.bs.service;

import com.eta.core.entity.ProcessResult;
import com.eta.modules.bs.model.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllWithPage(Teacher param);

    List<Teacher> getAll();

    ProcessResult saveOrUpdate(Teacher param);

    Teacher getById(Integer id);

    ProcessResult deleteById(Integer id);

    ProcessResult batchDelete(Integer[] list);

}
