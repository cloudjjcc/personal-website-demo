package site.nullpointer.auth.dto;

import java.io.Serializable;

/**
 * 返回的文件信息
 */
public class CallbackInfo implements Serializable {
    private String url;

    public CallbackInfo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
