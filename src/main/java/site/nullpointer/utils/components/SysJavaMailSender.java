package site.nullpointer.utils.components;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author wangjc
 * @Title: SysJavaMailSender
 * @ProjectName demo
 * @Description: TODO
 * @date 2018/7/2113:22
 */
@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class SysJavaMailSender extends JavaMailSenderImpl {
    private final MailProperties properties;

    public SysJavaMailSender(MailProperties properties) {
        super();
        this.properties = properties;
        super.setUsername(properties.getUsername());
        super.setPassword(properties.getPassword());
        super.setProtocol(properties.getProtocol());
        super.setPort(properties.getPort());
        // 设置编码和各种参数
        super.setHost(properties.getHost());
        super.setDefaultEncoding(properties.getDefaultEncoding().name());
    }
}
