package com.eta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: 553039957@qq.com
 * @Date: 2023/9/25 14:35
 * @Description:
 * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）
 * 2. github主页：https://github.com/doudoutangs
 * 3. gitee(码云)主页：https://gitee.com/spdoudoutang
 */
@MapperScan(basePackages = {"com.eta.modules.*.mapper"})
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("启动成功！！！");
        System.out.println(" * 0. 553039957@qq.com\n" +
                " * 1. gitcode主页： https://gitcode.net/tbb414 （推荐）\n" +
                " * 2. github主页：https://github.com/doudoutangs\n" +
                " * 3. gitee(码云)主页：https://gitee.com/spdoudoutang");
    }

}


