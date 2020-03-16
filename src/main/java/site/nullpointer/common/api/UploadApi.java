package site.nullpointer.common.api;

import site.nullpointer.common.service.UploadService;
import site.nullpointer.common.dto.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户文件上传
 */
@RestController
public class UploadApi {
    @Autowired
    private UploadService uploadService;

    /**
     * 获取头像上传令牌
     *
     * @return
     */
    @GetMapping({"/api/user/upload/token", "/api/admin/upload/token"})
    public ResponseEntity getUploadToken(@RequestParam int uploadType, @RequestParam String format) {
        return new ResponseEntity().success(true).data(uploadService.getUploadToken(uploadType, format));
    }

    @PostMapping("/api/common/avatar/callback")
    public ResponseEntity avatarCallback(@RequestParam String object) {
        return new ResponseEntity().success(true).data(uploadService.avatarCallback(object));
    }
    @PostMapping("/api/common/publish/callback")
    public ResponseEntity publishCallback(@RequestParam String object) {
        return new ResponseEntity().success(true).data(uploadService.publishCallback(object));
    }
}
