package com.library;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

/**
 * @Author: 0045M
 * @Description:
 * @Date: 11:02 2020/5/23
 */

public class Mygeneration {

    @Test
    public static void main(String[] args) {

        AutoGenerator autoGenerator = new AutoGenerator();

//        全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setActiveRecord(true)
                .setAuthor("0045M")
                .setOutputDir("E:\\Library\\src\\main\\java")//生成路径
                .setFileOverride(true)
                .setIdType(IdType.AUTO)
                .setServiceImplName("%sService")
                .setBaseResultMap(true)
                .setBaseColumnList(true);

//      数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                        .setDriverName("com.mysql.cj.jdbc.Driver")
                        .setUrl("jdbc:mysql://localhost:3306/books_management?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8")
                        .setUsername("root")
                        .setPassword("123456");


//        策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude("books");
//                .setInclude("book_type");
//                .setInclude("book_location");
//                .setInclude("borrow_log");
//                .setInclude("lib_manager");
//                .setInclude("oper_log");
//                .setInclude("sys_manager");
//                .setInclude("user");


//        包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.library")
                    .setMapper("dao")
                    .setService("service")
                    .setController("controller")
                    .setEntity("pojo")
                    .setXml("mapper");

//        整合配置
        autoGenerator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategy)
                .setPackageInfo(packageConfig);
//        执行
        autoGenerator.execute();


    }

}
