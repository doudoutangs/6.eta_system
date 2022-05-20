package com.eta.core.util;

import com.eta.core.entity.BASE64DecodedMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 */
public class FileUtil implements Serializable {
    /**
     * 下载文件
     * @param response
     * @param file
     * @author qjp
     */
    public static void downloadFile(HttpServletResponse response , File file) {

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(baseStrs[1]);

            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * NIO way
     */
    public static byte[] toByteArray(String filename) throws Exception {

        File f = new File(filename);
        if (!f.exists()) {
            throw new Exception("文件未找到");
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {

            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
        }
    }

}
