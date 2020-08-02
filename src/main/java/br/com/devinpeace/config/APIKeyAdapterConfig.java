package br.com.devinpeace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import br.com.devinpeace.auth.CredentialProperty;
import br.com.devinpeace.auth.HeaderFilter;

@Configuration
@EnableWebSecurity
public class APIKeyAdapterConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CredentialProperty credentialProperty;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		HeaderFilter headerFilter = new HeaderFilter("x-api-token");

		headerFilter.setAuthenticationManager(new AuthenticationManager() {
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				
				String token = (String) authentication.getPrincipal();

				if (!credentialProperty.getToken().equals(token)) 
					throw new BadCredentialsException("The API key was not found or not the expected value.");
				
				authentication.setAuthenticated(true);
				
				return authentication;
			}
		});

		httpSecurity.antMatcher("/v1/**")
					.csrf().disable()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.addFilter(headerFilter)
					.addFilterBefore(new ExceptionTranslationFilter(new Http403ForbiddenEntryPoint()), headerFilter.getClass())
					.authorizeRequests()
					.anyRequest()
					.authenticated();
	}

}