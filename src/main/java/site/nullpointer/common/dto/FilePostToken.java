package site.nullpointer.common.dto;

import com.aliyun.oss.model.Callback;

import java.io.Serializable;

/**
 * 文件操作令牌
 */
public class FilePostToken implements Serializable {
    private String accessKeyId;
    private String policy;
    private String signature;
    private String objectKey;
    private String callback;


    public FilePostToken() {
    }

    public FilePostToken(String accessKeyId, String policy, String signature, String objectKey, String callback) {
        this.accessKeyId = accessKeyId;
        this.policy = policy;
        this.signature = signature;
        this.objectKey = objectKey;
        this.callback = callback;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
