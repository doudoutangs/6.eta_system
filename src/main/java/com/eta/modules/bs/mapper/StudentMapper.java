package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.SchoolClass;
import com.eta.modules.bs.model.StatisVO;
import com.eta.modules.bs.model.Student;
import com.eta.modules.bs.model.StudentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper extends MyMapper<Student> {
    List<Student> getChildList(String id);

    Integer batchDelete(List<Integer> list);

    List<StudentVO> selectByParam(StudentVO param);

    Long statisticsByYear(@Param("year") String year);

    List<StatisVO> statisticsGroupByVisitYear(List<String> list);

}