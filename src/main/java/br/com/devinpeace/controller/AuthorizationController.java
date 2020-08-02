package br.com.devinpeace.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/v1/authorization")
@Api(value = "/v1/authorization", tags = "Authorization", description = " ")
@CrossOrigin
public class AuthorizationController {

	@GetMapping
	@ApiOperation(value = "Authorization test.", authorizations = {@Authorization(value="apiKey")})
	public String test() {
		return "Hello world!";
	}
	
}

