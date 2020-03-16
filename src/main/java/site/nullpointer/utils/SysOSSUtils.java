package site.nullpointer.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import com.aliyun.oss.common.auth.ServiceSignature;
import com.aliyun.oss.internal.OSSUtils;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.PolicyConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import site.nullpointer.common.dto.FilePostToken;

/**
 * <p>
 * 类路径 : site.nullpointer.common.utils.OSSUtils
 * <p>
 * 类描述 : 阿里云OSS操作工具
 * <p>
 * 类详情 : 无
 *
 * @author 王金灿
 * @version 1.0.0
 * <p>
 * --------------------------------------------------------------<br>
 * 修改履历：<br>
 * <li>2018年5月29日，wangjc，创建文件；<br>
 * --------------------------------------------------------------<br>
 * </p>
 */
@Component
public class SysOSSUtils {
    @Value("${oss.end-point}")
    private String endPoint;
    @Value("${oss.domain}")
    private String domain;
    @Value("${oss.access-key-id}")
    private String accessKeyId;
    @Value("${oss.secret-access-key}")
    private String secretAccessKey;
    @Value("${oss.bucket-name}")
    private String bucketName;
    @Value("${oss.expiration-sec-default}")
    private int expirationSecDefault;
    @Value("${oss.max-upload-size}")
    private long maxUploadSize;
    @Value("${oss.callback-url-host}")
    private String callbackUrlHost;
    private static final Logger logger = LoggerFactory.getLogger(SysOSSUtils.class);

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public int getExpoirationSecDefault() {
        return expirationSecDefault;
    }

    public void setExpoirationSecDefault(int expirationSecDefault) {
        this.expirationSecDefault = expirationSecDefault;
    }

    public int getExpirationSecDefault() {
        return expirationSecDefault;
    }

    public void setExpirationSecDefault(int expirationSecDefault) {
        this.expirationSecDefault = expirationSecDefault;
    }

    public long getMaxUploadSize() {
        return maxUploadSize;
    }

    public void setMaxUploadSize(long maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

    public String getCallbackUrlHost() {
        return callbackUrlHost;
    }

    public void setCallbackUrlHost(String callbackUrlHost) {
        this.callbackUrlHost = callbackUrlHost;
    }

    public URL uploadFile(String key, File file, CannedAccessControlList access, Date expirationDate) throws Exception {
        return uploadFile(key, new FileInputStream(file), access, expirationDate);
    }

    public URL uploadFile(String key, byte[] data, CannedAccessControlList access, Date expirationDate) throws Exception {
        return uploadFile(key, new ByteArrayInputStream(data), access, expirationDate);
    }

    public URL uploadFile(String key, InputStream ins, CannedAccessControlList access, Date expirationDate) throws Exception {
        OSSClient ossClient = null;
        try {
            URL url = null;
            Date date = new Date(new Date().getTime() + expirationSecDefault * 1000);
            if (expirationDate != null) {
                date = expirationDate;
            }
            // 支持CNAME解析
            ClientConfiguration config = new ClientConfiguration();
            config.setSupportCname(true);
            logger.info("初始化OSSClient");
            ossClient = new OSSClient(endPoint, accessKeyId, secretAccessKey, config);
            logger.info("上传文件：" + key);
            ossClient.putObject(bucketName, key, ins);
            if (access != null) {
                ossClient.setObjectAcl(bucketName, key, access);
                if (access == CannedAccessControlList.Private) {
                    url = ossClient.generatePresignedUrl(bucketName, key, date);
                } else {
                    url = new URL(domain + key);
                }
            } else {
                url = ossClient.generatePresignedUrl(bucketName, key, date);
            }
            return url;
        } catch (Exception e) {
            logger.info("上传文件失败");
            throw e;
        } finally {
            if (ossClient != null) {
                logger.info("关闭OSSClient");
                ossClient.shutdown();
            }
        }
    }

    /**
     * 生产文件表单上传令牌
     *
     * @return
     */
    public FilePostToken obtainFilePostToken(String objectKey, String callbackUrl) {
        OSSClient ossClient = null;
        FilePostToken token = null;
        try {
            // 支持CNAME解析
            ClientConfiguration config = new ClientConfiguration();
            config.setSupportCname(true);
            logger.info("初始化OSSClient");
            ossClient = new OSSClient(endPoint, accessKeyId, secretAccessKey, config);
            token = new FilePostToken();
            token.setAccessKeyId(accessKeyId);
            token.setObjectKey(objectKey);
            PolicyConditions conditions = new PolicyConditions();
            conditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxUploadSize);
            conditions.addConditionItem(PolicyConditions.COND_KEY, objectKey);
            conditions.addConditionItem(PolicyConditions.COND_SUCCESS_ACTION_STATUS, 200 + "");
            String policy = ossClient.generatePostPolicy(new Date(new Date().getTime() + expirationSecDefault * 1000), conditions);
            token.setPolicy(new String(Base64.getEncoder().encodeToString((policy.getBytes()))));
            Callback callback = new Callback();
            callback.setCallbackHost(endPoint.replace("https://", ""));
            callback.setCallbackBody("object=${object}");
            callback.setCalbackBodyType(Callback.CalbackBodyType.URL);
            callback.setCallbackUrl(callbackUrlHost+callbackUrl);
            token.setCallback(new String(Base64.getEncoder().encodeToString(OSSUtils.jsonizeCallback(callback).getBytes())));
            // 生成签名。
            token.setSignature(ServiceSignature.create().computeSignature(secretAccessKey, token.getPolicy()));
        } catch (Exception e) {
            logger.info("获取文件表单上传令牌失败");
            throw e;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return token;
    }

    /**
     * 对象访问权限控制
     *
     * @return
     */
    public String objectACL(String key,CannedAccessControlList access) {
        OSSClient ossClient=null;
        String url="";
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClient(endPoint, accessKeyId, secretAccessKey);
            // 设置文件的访问权限为公共读。
            ossClient.setObjectAcl(bucketName, key, access);
            url=domain + key;
        } catch (Exception e) {
            throw new RuntimeException("设置OSS文件权限失败");
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return url;
    }
}
