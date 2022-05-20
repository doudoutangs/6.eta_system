package com.eta.modules.sys.mapper;

import com.eta.core.mapper.MyMapper;
import com.eta.modules.sys.model.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysDictMapper extends MyMapper<SysDict> {
    List<SysDict> getDictByDictValue(String dictValue);

    SysDict getDictByValueAndLevel(@Param("dictValue") String dictValue, @Param("dictLevel") String dictLevel);

    Integer batchDelete(List<Integer> list);

}