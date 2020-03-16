package site.nullpointer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * <p>
 * 类路径 : site.nullpointer.common.utils.MailUtils
 * <p>
 * 类描述 : 邮件工具类
 * <p>
 * 类详情 : 无
 *
 * @author 王金灿
 * @version 1.0.0
 *          <p>
 *          --------------------------------------------------------------<br>
 *          修改履历：<br>
 *          <li>2018年6月1日，wangjc，创建文件；<br>
 *          --------------------------------------------------------------<br>
 *          </p>
 */
@Component
public class MailUtils {
    @Resource(name = "sysJavaMailSender")
	private JavaMailSender javaMailSender;

	/**
	 * <p>
	 * 功能描述 : 发送纯文本邮箱
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param subject
	 * @param text
	 * @param to
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年6月1日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	public void sendSimpleEmail(String subject, String text, String... to) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("NP");
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(text);
		javaMailSender.send(mail);
	}

	/**
	 * <p>
	 * 功能描述 : 发送HTML邮件
	 * <p>
	 * 详细说明 : 无
	 *
	 * @param subject
	 * @param htmlText
	 * @param to
	 * @author 王金灿
	 *         <p>
	 *         --------------------------------------------------------------<br>
	 *         修改履历：<br>
	 *         <li>2018年6月1日，wangjc，创建方法；<br>
	 *         --------------------------------------------------------------<br>
	 *         </p>
	 */
	public void sendHtmlEmail(String subject, String htmlText, String... to) {
		MimeMessage mail = null;
		try {
			mail = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setFrom("NP");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlText, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		javaMailSender.send(mail);
	}
}
