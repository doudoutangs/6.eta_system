package com.eta.modules.sys.service;

import com.eta.core.util.DateUtil;
import com.eta.modules.sys.mapper.SysDealLogMapper;
import com.eta.modules.sys.model.SysDealLog;
import com.eta.modules.sys.model.SysRole;
import com.eta.modules.sys.model.SysUser;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysLogService {

    @Autowired
    private SysDealLogMapper sysDealLogMapper;


    public void insert(SysDealLog sysLog) {
        sysDealLogMapper.insert(sysLog);
    }

    public List<SysDealLog> getListByPage(SysDealLog sysDealLog, String keyword, String createDate, SysUser sysUser){
        PageHelper.startPage(sysDealLog.getPage(),sysDealLog.getLimit());
        Example example=new Example(SysDealLog.class);
        Example.Criteria criteria = example.createCriteria();
        boolean isMaster=false;
        SysRole role = sysUser.getRoles().iterator().next();
        if(role!=null){
            Byte roleType = role.getRoleType();
            if(0==roleType){
                isMaster=true;
            }
        }
        if(!isMaster){
            criteria.andEqualTo("username",sysUser.getUsername());
        }
        if(StringUtils.isNotBlank(createDate)){
            Date date = DateUtil.strToDate(createDate);
            criteria.andBetween("createDate",DateUtil.formatDate(date),DateUtil.getNextDay(DateUtil.formatDate(date),"1"));
        }

        if(StringUtils.isNotBlank(keyword)){
            keyword="%"+keyword+"%";
            criteria.andLike("username",keyword);
            criteria.orLike("operation",keyword);
        }
        example.orderBy("createDate").desc();
        return sysDealLogMapper.selectByExample(example);
    }

//    public File getExcel(List<SysDealLog> logList) {
//            File file  = new File("d:\\springboot\\sysLog\\"+ System.currentTimeMillis() +".xls");
//            WritableWorkbook workbook = null;
//            try {
//                workbook = Workbook.createWorkbook(file);
//                WritableSheet sheet = workbook.createSheet("sheet1", 0);
//                WritableCellFormat wcf = new WritableCellFormat();
//                wcf.setBackground(Colour.YELLOW);
//                sheet.addCell(new Label(0, 0, "用户名"   , wcf));
//                sheet.addCell(new Label(1, 0, "用户操作" ,wcf));
//                sheet.addCell(new Label(2, 0, "请求方法", wcf));
//                sheet.addCell(new Label(3, 0, "请求参数"  , wcf));
//                sheet.addCell(new Label(4, 0, "执行时长(毫秒)" , wcf));
//                sheet.addCell(new Label(5, 0, "IP地址" , wcf));
//                sheet.addCell(new Label(6, 0, "日志时间", wcf));
//                sheet.setColumnView(0, 20);
//                sheet.setColumnView(1, 20);
//                sheet.setColumnView(2, 60);
//                sheet.setColumnView(3, 60);
//                sheet.setColumnView(4, 20);
//                sheet.setColumnView(5, 20);
//                sheet.setColumnView(6, 20);
//                for (int row = 0; row < logList.size(); row++) {
//                    sheet.addCell(new Label(0, row+1, logList.get(row).getUsername()));
//                    sheet.addCell(new Label(1, row+1, logList.get(row).getOperation()));
//                    sheet.addCell(new Label(2, row+1, logList.get(row).getMethod()));
//                    sheet.addCell(new Label(3, row+1, logList.get(row).getParams()));
//                    sheet.addCell(new Label(4, row+1, logList.get(row).getTime().toString()));
//                    sheet.addCell(new Label(5, row+1, logList.get(row).getIp()));
//                    sheet.addCell(new Label(6, row+1, DateUtil.formatDate(logList.get(row).getCreateDate(),"yyyy-MM-dd hh:mm:ss")));
//
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (RowsExceededException e) {
//                e.printStackTrace();
//            } catch (WriteException e) {
//                e.printStackTrace();
//            }finally {
//                if (workbook != null) {
//                    try {
//                        workbook.write();
//                        workbook.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (WriteException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            return file;
//    }

    public List<SysDealLog> getList(SysDealLog sysDealLog, String keyword,String createDate, SysUser sysUser) {
        Example example=new Example(SysDealLog.class);
        Example.Criteria criteria = example.createCriteria();
        boolean isMaster=false;
        SysRole role = sysUser.getRoles().iterator().next();
        if(role!=null){
            Byte roleType = role.getRoleType();
            if(0==roleType){
                isMaster=true;
            }
        }
        if(!isMaster){
            criteria.andEqualTo("username",sysUser.getUsername());
        }
        if(StringUtils.isNotBlank(createDate)){
            Date date = DateUtil.strToDate(createDate);
            criteria.andBetween("createDate",DateUtil.formatDate(date),DateUtil.getNextDay(DateUtil.formatDate(date),"1"));
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword="%"+keyword+"%";
            criteria.andLike("username",keyword);
            criteria.orLike("operation",keyword);
        }
        example.orderBy("createDate").desc();
        return sysDealLogMapper.selectByExample(example);
    }
}
