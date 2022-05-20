package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.StatisVO;
import com.eta.modules.bs.model.Student;
import com.eta.modules.bs.model.TestAnswer;
import com.eta.modules.bs.model.WorkRegister;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkRegisterMapper extends MyMapper<WorkRegister> {
    WorkRegister getByStudentId(Integer studentId);

    Integer batchDelete(List<Integer> list);

    Long statisticsByYear(@Param("year") String year);
    List<StatisVO> statisticsGroupByVisitYear(List<String> list);

}