package com.armsmart.jupiter.bs.api.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.digest.MD5;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.armsmart.common.exception.BusinessException;
import com.armsmart.jupiter.bs.api.config.OssConfig;
import com.armsmart.jupiter.bs.api.dto.request.OssPolicyParam;
import com.armsmart.jupiter.bs.api.dto.response.AssumeRoleResult;
import com.armsmart.jupiter.bs.api.error.NEError;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云OSS对象存储服务
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class AliOssService {

    @Autowired
    private OssConfig ossConfig;
    @Autowired
    private OSS ossClient;

    public AssumeRoleResult getAcsResponse(String bucketName) {
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        DefaultProfile profile = DefaultProfile.getProfile(ossConfig.getRegion(), accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        OssPolicyParam policyParam = new OssPolicyParam();
        String policy = policyParam.put(bucketName);
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setSysProtocol(ProtocolType.HTTPS);
        request.setRoleArn(ossConfig.getRoleArn());
        request.setRoleSessionName("RoleSessionName");
        request.setPolicy(policy);
        //request.setDurationSeconds(ossConfig.getDurationSeconds());
        AssumeRoleResult result = new AssumeRoleResult();
        //发起请求，并得到响应。
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);
            log.info("AssumeRoleResponse:{}", new Gson().toJson(response));
            result.setCredentials(response.getCredentials());
            result.setBucketName(bucketName);
            result.setRegion("oss-" + ossConfig.getRegion());
            return result;
        } catch (Exception e) {
            log.error("getAcsResponse error:", e);
            throw new BusinessException(NEError.GET_ACS_FAILED);
        }

    }

    public PutObjectResult upload(MultipartFile file) throws Exception {
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getAvatarBucketName(), file.getOriginalFilename(), file.getInputStream());
        return ossClient.putObject(putObjectRequest);
    }

    /**
     * 通过文件名下载文件
     *
     * @param objectName    要下载的文件名
     * @param localFileName 本地要创建的文件名
     */
    public  File downloadFile(String objectName, String localFileName) {

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        OSSObject object =ossClient.getObject(new GetObjectRequest(ossConfig.getAvatarBucketName(), objectName));
        InputStream inputStream = object.getObjectContent();

//        ossClient.shutdown();

        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);


            File targetFile = new File(localFileName);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            return targetFile;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;

    }


    public  File getFile(String url) throws Exception {
        //对本地文件命名
        String fileName = url.substring(url.lastIndexOf("."), url.length());
        File file = null;

        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("net_url", fileName);
            //下载
            urlfile = new URL(url);
            inStream = urlfile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    public void delete(String objectName) {
        ossClient.deleteObject(ossConfig.getAvatarBucketName(), objectName);
    }

    public String generatePutPresignedUrl(String objectName) {
        String bucketName = ossConfig.getAvatarBucketName();
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.PUT);
        Date expiration = new Date(System.currentTimeMillis() + ossConfig.getDurationSeconds() * 1000);
        request.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(request);
        return url.toString();
    }


    public String getObjectName(String url) {
        if (StringUtils.hasText(url) && url.contains(ossConfig.getEndpoint())) {
            return url.split(ossConfig.getEndpoint())[1].substring(1);
        }
        return null;
    }

    public String getMd5(String bucketName, String url) {
        InputStream objectContent = null;
        try {
            OSSObject object = ossClient.getObject(bucketName, getObjectName(url));
            objectContent = object.getObjectContent();
            return MD5.create().digestHex(objectContent);
        } finally {
            IoUtil.close(objectContent);
        }
    }

    public static void main(String[] args) {

    }

}
