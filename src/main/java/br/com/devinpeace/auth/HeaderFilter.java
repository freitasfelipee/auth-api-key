package br.com.devinpeace.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class HeaderFilter extends AbstractPreAuthenticatedProcessingFilter {

	private String headerName;

	public HeaderFilter(String headerName) {
		this.headerName = headerName;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return request.getHeader(headerName);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}
}