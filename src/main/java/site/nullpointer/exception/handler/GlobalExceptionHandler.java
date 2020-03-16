package site.nullpointer.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import site.nullpointer.common.dto.ResponseEntity;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private final  Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(value = Exception.class)
//	@ResponseStatus(code=HttpStatus.BAD_REQUEST,reason="exception occur!")
	public void handGlobalException(HttpServletRequest request,HttpServletResponse response,Exception e) {
		try {
		    String cause="异常导致";
		    if(e instanceof HttpRequestMethodNotSupportedException){
		        cause=e.getMessage();
            }else {
                logger.error(request.getRequestURI()+"请求失败！", e);
            }
			response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.getWriter().print(new ResponseEntity().success(false).addKeyValuePair("cause",cause).toJSONString());
		} catch (IOException e1) {
			logger.error("返回数据异常", e1);
		}
	}
}
