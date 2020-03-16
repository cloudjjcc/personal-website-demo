package site.nullpointer.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import site.nullpointer.auth.service.impl.SysUserDetailsServiceImpl;
import site.nullpointer.security.components.*;
import site.nullpointer.security.jwt.JwtAuthorizationTokenFilter;
import site.nullpointer.security.jwt.ApiFilter;

import javax.servlet.Filter;
import java.util.Properties;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 获取用户信息
     *
     * @return
     */
    @Bean
    public UserDetailsService sysUserDetailsService() {
        return new SysUserDetailsServiceImpl();
    }

    /**
     * <p>
     * 功能描述 : 跨域配置
     * <p>
     * 详细说明 : 无
     *
     * @return
     * @author 王金灿
     * <p>
     * --------------------------------------------------------------<br>
     * 修改履历：<br>
     * <li>2018年3月26日，wangjc，创建方法；<br>
     * --------------------------------------------------------------<br>
     * </p>
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    public PasswordEncoder sysPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    /**
     * <p>功能描述 : token 认证过滤器
     * <p>详细说明 : 无
     *
     * @return
     * @author 王金灿
     * <p>
     * --------------------------------------------------------------<br>
     * 修改履历：<br>
     * <li> 2018年4月5日，wangjc，创建方法；<br>
     * --------------------------------------------------------------<br>
     * </p>
     */
    @Bean
    public Filter sysAuthenticationTokenFilter() {
        return new JwtAuthorizationTokenFilter();
    }

    /**
     * 使用Token或是Session
     *
     * @return
     */
    public Filter sysSessionOrTokenFilter() {
        return new ApiFilter();
    }

    /**
     * 用户信息认证
     *
     * @return
     */
    @Bean
    public AuthenticationProvider sysAuthenticationProvider() {
        return new SysAuthenticationProvider(sysUserDetailsService(), sysPasswordEncoder());
    }

    /**
     * 认证入口点
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint sysAuthenticationEntryPoint() {
        return new SysAuthenticationEntryPoint();
    }

    /**
     * 认证成功处理
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler sysLoginSuccessHandler() {
        return new SysLoginSuccessHandler();
    }

    /**
     * 认证失败处理
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler sysLoginFailureHandler() {
        return new SysLoginFailureHandler();
    }

    /**
     * 生成验证码
     *
     * @return
     */
    @Bean
    public Producer captchaProducer() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "120");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "33");
//		properties.setProperty("kaptcha.session.key", "catptcha");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "Algerian");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

    /**
     * session注册管理器
     * @return
     */
    @Bean
    public SessionRegistry sysSessionRegistry() {
          return new SysSessionRegistryImpl();
    }

    /**
     * 配置权限认证提供者
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(sysAuthenticationProvider());
    }

    /**
     * 配置http安全信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/**/user/**").authenticated()
                .antMatchers("/**/admin/**").hasAuthority("ROLE_ADMIN").and()/* 需认证请求 */
                .addFilterBefore(sysSessionOrTokenFilter(), SecurityContextPersistenceFilter.class)/* session or token */
                .addFilterBefore(sysAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)/* jwt token 认证 */
                .formLogin().loginProcessingUrl("/**/login")/* 配置表单登录url */
                .successHandler(sysLoginSuccessHandler())/* 认证成功处理 */
                .failureHandler(sysLoginFailureHandler()).and()/* 认证失败处理 */
                .sessionManagement().maximumSessions(1).sessionRegistry(sysSessionRegistry()).and()/**/
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()/* 如果需要时使用session */
                .logout().logoutUrl("/**/logout").logoutSuccessUrl("/index").and()/*配置登出url*/
                .rememberMe().key("np-r-key").userDetailsService(sysUserDetailsService()).and()/*配置remeberme服务*/
                .cors().disable()/*已经自定义cors过滤器*/
                .csrf().disable()/* 关闭crsf */
//			.anonymous().disable()/* 关闭匿名访问*/
                .exceptionHandling().authenticationEntryPoint(sysAuthenticationEntryPoint()).and();/* 认证入口点 */
    }

    /**
     * 配置web安全信息
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/**/common/**", "/webjars/**", "/js/**", "/css/**", "/img/**", "/lib/**");
    }
}
