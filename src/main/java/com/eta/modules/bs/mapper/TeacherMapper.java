package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.Teacher;

import java.util.List;

public interface TeacherMapper extends MyMapper<Teacher> {
    Integer batchDelete(List<Integer> list);

}