package com.xkenmon.cms.dao.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author bigmeng
 */
public class GenerateUtil {
    public static void generateByTables(boolean serviceNameStartWithI, DataSourceConfig dataSourceConfig, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setTablePrefix("cms")
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);
        config.setActiveRecord(false)
                .setAuthor("AutoGenerator")
                .setOutputDir("./codeGen")
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("api")
                                .setEntity("entity")
                ).execute();
    }

    public static void generateByTables(DataSourceConfig config, String packageName, String... tableNames) {
        generateByTables(true, config, packageName, tableNames);
    }
}
