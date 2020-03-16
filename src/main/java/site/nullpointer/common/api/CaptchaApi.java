package site.nullpointer.common.api;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;

import site.nullpointer.utils.ClientUtils;
import site.nullpointer.utils.RedisUtils;

@RestController
public class CaptchaApi extends CommonApi {
	@Autowired(required = true)
	private Producer captchaProducer;
	@Autowired
	private RedisUtils redisUtils;
	@Value("${captcha.timeout}")
	private long captchaTimeout;

	/**
	 * 获取验证码
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/captcha")
	public void getCaptcha(HttpServletRequest request,@RequestParam(required=true,name="key") String key, HttpServletResponse response) throws Exception {
		if(!key.matches("[a-fA-F0-9]{32}")) {
			throw new Exception("错误的验证码请求！");
		}
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		String captchaText = captchaProducer.createText();
		String captchaKey=ClientUtils.getIpOnlyMd5Key(request, key);
		redisUtils.setString(captchaKey, captchaText,captchaTimeout);
		BufferedImage captchaImage = captchaProducer.createImage(captchaText);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(captchaImage, "jpg", out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
