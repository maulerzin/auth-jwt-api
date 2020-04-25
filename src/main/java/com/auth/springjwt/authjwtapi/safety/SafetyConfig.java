package com.auth.springjwt.authjwtapi.safety;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth.springjwt.authjwtapi.safety.jwt.AuthEntryPoint;
//import com.auth.springjwt.authjwtapi.safety.jwt.AuthEntryPoint;
import com.auth.springjwt.authjwtapi.safety.jwt.AuthToken;
import com.auth.springjwt.authjwtapi.safety.resources.ClientDetailSvc;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
		//securedEnabled = true,
		//jsr250Enabled= true,
		prePostEnabled = true)
public class SafetyConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	ClientDetailSvc userDetailsService;

	@Autowired
	private AuthEntryPoint unauthorizedHandler;
	
	@Bean
	public AuthToken authenticationJwtTokenFilter() {
		return new AuthToken();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) 
			throws Exception {
				authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
