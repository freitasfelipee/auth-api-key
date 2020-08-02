package br.com.devinpeace.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "devinpeace.auth", ignoreUnknownFields = true)
@Component
@Data
public class CredentialProperty {
	private String token;
}
