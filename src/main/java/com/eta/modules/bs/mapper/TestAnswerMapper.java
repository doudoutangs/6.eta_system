package com.eta.modules.bs.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.bs.model.StatisVO;
import com.eta.modules.bs.model.TestAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestAnswerMapper extends MyMapper<TestAnswer> {
    Integer batchDelete(List<Integer> list);

    TestAnswer getByUserId(Integer userId);

    Long statisticsByYear(@Param("year") String year);

    Long statisticsByYearAndWork(@Param("year") String year);

    List<StatisVO> statisticsGroupByVisitYear(TestAnswer param);

    List<TestAnswer> query(TestAnswer param);

}