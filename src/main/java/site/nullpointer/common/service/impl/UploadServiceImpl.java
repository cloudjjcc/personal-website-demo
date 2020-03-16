package site.nullpointer.common.service.impl;

import com.aliyun.oss.model.CannedAccessControlList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nullpointer.auth.dto.CallbackInfo;
import site.nullpointer.common.dto.FilePostToken;
import site.nullpointer.common.service.UploadService;
import site.nullpointer.utils.SysOSSUtils;
import site.nullpointer.utils.UUIDUtils;

/**
 * 用户上传服务
 */
@Service
public class UploadServiceImpl implements UploadService {
    /**
     *
     */
    @Autowired
    private SysOSSUtils ossUtils;
    @Override
    public FilePostToken getUploadToken(int uploadType, String format) {
        String base_path;
        String callback_url;
        switch (uploadType) {
            case 1://上传头像
                base_path = "avatar/" + getCurrentUser().getId() + "/";
                callback_url="/api/common/avatar/callback";
                break;
            case 2://上传文章图片
                base_path="publish/"+getCurrentUser().getId()+"/";
                callback_url="/api/common/publish/callback";
                break;
            default:
                throw new IllegalArgumentException("{uploadType="+uploadType+"} 参数无效");
        }
        String objectKey=base_path+UUIDUtils.getUUIDStrNoLine()+"."+format;
        return ossUtils.obtainFilePostToken(objectKey,callback_url);
    }

    @Override
    public CallbackInfo avatarCallback(String key) {
        if(!key.startsWith("avatar")){
            throw new IllegalArgumentException("{key="+key+"} 参数无效");
        }
        return new CallbackInfo(ossUtils.objectACL(key,CannedAccessControlList.PublicRead));
    }
    @Override
    public CallbackInfo publishCallback(String key) {
        if(!key.startsWith("publish")){
            throw new IllegalArgumentException("{key="+key+"} 参数无效");
        }
        return new CallbackInfo(ossUtils.objectACL(key,CannedAccessControlList.PublicRead));
    }
}
