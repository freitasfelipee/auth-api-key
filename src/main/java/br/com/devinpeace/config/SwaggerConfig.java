package br.com.devinpeace.config;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import br.com.devinpeace.controller.AuthorizationController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Value("${app.version}")
	private String appVersion;
	
	@Bean
	public Docket getDocket() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.securitySchemes(Collections.singletonList(new ApiKey("Authorization-Key", "x-api-token", "")))
				.securityContexts(getSecurityContexts())
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(AuthorizationController.class.getPackage().getName()))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title("Api key authorization")
				.description("Official documentation of the authorization test application using api key.")
				.version(appVersion)
				.contact(new Contact("Felipe Gonçalves Freitas dos Santos", null, "freitas.felipee@live.com"))
				.build();
	}
	
	private ArrayList<SecurityContext> getSecurityContexts() {
		
		ArrayList<SecurityContext> securityContexts = new ArrayList<>();
		
		securityContexts.add(SecurityContext.builder().securityReferences(Collections.singletonList(getSecurityReference())).build());
		
		return securityContexts;
	}
	
	
	private SecurityReference getSecurityReference() {
		
		AuthorizationScope[] authScopes = new AuthorizationScope[1];
		
		authScopes[0] = new AuthorizationScopeBuilder().scope("global").description("full access").build();
		
		return  SecurityReference.builder().reference("Authorization-Key").scopes(authScopes).build();
	}

	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}


}