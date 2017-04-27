package com.chanpay.ppd.mps.web.config;

import com.chanpay.ppd.mps.common.upload.DiskFileOperator;
import com.chanpay.ppd.mps.common.upload.FileOperator;
import com.chanpay.ppd.mps.common.upload.util.FileManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传配置
 *
 * @author zhangxd
 */
@Configuration
public class UploadConfig {

    /**
     * Oss file operator oss file operator.
     *
     * @return the oss file operator
     */
    @Bean
    @ConfigurationProperties("upload")
    public DiskFileOperator diskFileOperator() {
        return new DiskFileOperator();
    }

    /**
     * File manager file manager.
     *
     * @param fileOperator the file operator
     * @return the file manager
     */
    @Bean
    public FileManager fileManager(FileOperator fileOperator) {
        FileManager fileManager = new FileManager();
        fileManager.setFileOperator(fileOperator);
        return fileManager;
    }

}