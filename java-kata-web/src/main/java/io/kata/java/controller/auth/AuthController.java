package io.kata.java.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.kata.java.controller.auth.request.SigninRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

	@GetMapping("/auth/signin")
	public String signinPage(@ModelAttribute("signinRequest") SigninRequest singinRequest) {
		return "auth/signinform";
	}

}
