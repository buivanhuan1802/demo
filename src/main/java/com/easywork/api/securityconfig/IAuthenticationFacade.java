package com.easywork.api.securityconfig;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
	 Authentication getAuthentication();
}
