package site.nullpointer.common.service;

import org.springframework.security.access.prepost.PreAuthorize;
import site.nullpointer.auth.dto.CallbackInfo;
import site.nullpointer.auth.service.UserBaseInfoService;
import site.nullpointer.common.dto.FilePostToken;

/**
 * 用户上传文件服务
 */
public interface UploadService extends UserBaseInfoService {
    /**
     * 获取上传令牌
     * @return
     * @param uploadType
     * @param format
     */
    FilePostToken getUploadToken(int uploadType, String format);

    CallbackInfo avatarCallback(String key);

    CallbackInfo publishCallback(String object);
}
