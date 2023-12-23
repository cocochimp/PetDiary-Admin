package com.ruoyi.functions.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.functions.config.OSSConfig;
import com.ruoyi.functions.service.ISysFileService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author wupx
 * @date 2020/07/09
 */
@Primary
@Service
public class OSSServiceImpl implements ISysFileService {

    public static Logger log = LoggerFactory.getLogger(OSSServiceImpl.class);

    @Autowired
    private OSSConfig ossConfiguration;

    @Autowired
    private OSS ossClient;

    /**
     * 上传文件到阿里云 OSS 服务器
     * 链接：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
     *
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file) {
        String fileName = "";
        try {
            // 创建一个唯一的文件名，类似于id，就是保存在OSS服务器上文件的文件名
            fileName = UUID.randomUUID().toString();
            InputStream inputStream = file.getInputStream();
            // 设置对象
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置数据流里有多少个字节可以读取
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            fileName = DateUtils.getDateYYMM() + "/" + fileName;
            // 上传文件
            ossClient.putObject(ossConfiguration.getBucketName(), fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            log.error("oss uploadFile error: {}", e.getMessage(), e);
        }
        return getSingeNatureUrl(fileName,60);
    }

    /**
     * 下载文件
     * 详细请参看“SDK手册 > Java-SDK > 上传文件”
     * 链接：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/download_object.html?spm=5176.docoss/sdk/java-sdk/manage_object
     *
     * @param os
     * @param objectName
     */
    public void exportFile(OutputStream os, String objectName) {
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流
        OSSObject ossObject = ossClient.getObject(ossConfiguration.getBucketName(), objectName);
        // 读取文件内容
        BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int lenght;
        try {
            while ((lenght = in.read(buffer)) != -1) {
                out.write(buffer, 0, lenght);
            }
            out.flush();
        } catch (IOException e) {
            log.error("Error occurred: {}", e.getMessage(), e);
        }
    }

    /**
     * 获取 url
     *
     * @param filename
     * @param expSeconds
     * @return
     */
    public String getSingeNatureUrl(String filename, int expSeconds) {
        Date expiration = new Date(System.currentTimeMillis() + expSeconds * 1000);
        Optional<URL> optionalUrl = Optional.ofNullable(ossClient.generatePresignedUrl(ossConfiguration.getBucketName(), filename, expiration));
        return optionalUrl.map(url -> url.toString().split("\\?")[0]).orElse(null);
    }

    /**
     * 删除文件
     * 详细请参看“SDK手册 > Java-SDK > 管理文件”
     * 链接：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
     *
     * @param fileName
     */
    public void deleteFile(String fileName) {
        try {
            ossClient.deleteObject(ossConfiguration.getBucketName(), fileName);
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage(), e);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public boolean doesObjectExist(String fileName) {
        try {
            if (Strings.isEmpty(fileName)) {
                log.error("文件名不能为空");
                return false;
            } else {
                return ossClient.doesObjectExist(ossConfiguration.getBucketName(), fileName);
            }
        } catch (OSSException | ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查看 Bucket 中的 Object 列表
     * 详细请参看“SDK手册 > Java-SDK > 管理文件”
     * 链接：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
     *
     * @return
     */
    public List<String> listObjects() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(ossConfiguration.getBucketName()).withMaxKeys(200);
        ObjectListing objectListing = ossClient.listObjects(listObjectsRequest);
        List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        return objectSummaries.stream().map(OSSObjectSummary::getKey).collect(Collectors.toList());
    }

    /**
     * 设置文件访问权限
     * 详细请参看“SDK手册 > Java-SDK > 管理文件”
     * 链接：https://help.aliyun.com/document_detail/84838.html
     *
     * @param fileName
     */
    public void setObjectAcl(String fileName) {
        ossClient.setObjectAcl(ossConfiguration.getBucketName(), fileName, CannedAccessControlList.PublicRead);
    }
}