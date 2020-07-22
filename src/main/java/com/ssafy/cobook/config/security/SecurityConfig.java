package com.ssafy.cobook.config.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import static com.ssafy.cobook.config.security.SocialType.GOOGLE;


public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/", "/oauth2/**", "/login/**", "/css/**",
                        "/images/**", "/js/**", "/console/**", "/favicon.ico/**")
                .permitAll()
                .antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .and()
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // swagger 관련 리소스 시큐리티 필터 제거
        web.ignoring().antMatchers(
                "/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
        if ("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
            return CommonOAuth2Provider.GOOGLE.getBuilder(client).clientId(registration.getClientId()).clientSecret(registration.getClientSecret()).scope("email", "profile").build();
        }
        return null;
    }

}
