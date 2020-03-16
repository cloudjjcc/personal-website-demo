package site.nullpointer.config;

import org.springframework.context.annotation.Configuration;

/**
 * <p>类路径 : site.nullpointer.config.RedisConfig
 * <p>类描述 : 配置Redis
 * <p>类详情 : 无
 * 
 * @author  王金灿
 * @version 1.0.0
 *<p>
 *--------------------------------------------------------------<br>
 * 修改履历：<br>
 *        <li> 2018年4月9日，wangjc，创建文件；<br>
 *--------------------------------------------------------------<br>
 *</p>
 */
@Configuration
public class RedisConfig {
//	@Bean
//	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
//		RedisTemplate<String,Object> template=new RedisTemplate<String,Object>();
//		template.setConnectionFactory(connectionFactory);
//		RedisSerializer<String> stringSerializer=new StringRedisSerializer();
//		RedisSerializer<Object> objectSerializer=new Jackson2JsonRedisSerializer<>(Object.class);
//		template.setKeySerializer(stringSerializer);
//		template.setValueSerializer(objectSerializer);
//		template.afterPropertiesSet();
//		return template;
//	}
}
