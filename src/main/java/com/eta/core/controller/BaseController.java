package com.eta.core.controller;


import com.eta.core.entity.ProcessResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Slf4j
public class BaseController {
    protected ProcessResult processResult=new ProcessResult();


    /**
     */
    public void writer(HttpServletResponse response, String str){
        PrintWriter writer = null;
        OutputStreamWriter osw = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            osw = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
            writer = new PrintWriter(osw, true);

            writer.write(str);
            writer.flush();
            writer.close();
            osw.close();

        } catch (UnsupportedEncodingException e) {
            log.error("输出错误", e);
        } catch (IOException e) {
            log.error("输出错误", e);
        } finally {
            if (null != writer) {
                writer.close();
            }
            if (null != osw) {
                try {
                    osw.close();
                } catch (IOException e) {
                    log.error("close io error ", e);
                }
            }
        }
    }
}
