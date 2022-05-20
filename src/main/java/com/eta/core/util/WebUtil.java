package com.eta.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;


public class WebUtil {

    // -- header 常量定义 --//
    private static final String HEADER_ENCODING = "encoding";
    private static final String HEADER_NOCACHE = "no-cache";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final boolean DEFAULT_NOCACHE = true;

    // -- Content Type 定义 --//
    public static final String TEXT_TYPE = "text/plain";
    public static final String JSON_TYPE = "application/json";
    public static final String XML_TYPE = "text/xml";
    public static final String HTML_TYPE = "text/html";
    public static final String JS_TYPE = "text/javascript";
    public static final String EXCEL_TYPE = "application/vnd.ms-excel";

    private static ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(WebUtil.class);

    private WebUtil() {

    }
    /**
     * @param data
     */
    public static void renderJson(final Object data, HttpServletResponse response) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(response.getOutputStream(),data);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
