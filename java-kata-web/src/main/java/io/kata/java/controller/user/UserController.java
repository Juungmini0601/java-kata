package io.kata.java.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.kata.java.controller.user.request.SignUpRequest;
import io.kata.java.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/user/signup/form")
	public String signupForm(@ModelAttribute("signUpRequest") SignUpRequest signUpRequest) {
		return "user/signupform";
	}

	@PostMapping("/user/signup")
	public String signup(
		@Valid @ModelAttribute("signUpRequest") SignUpRequest signUpRequest,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			log.error("Validation error: {}", bindingResult.getAllErrors());
			return "user/signupform";
		}

		userService.signup(signUpRequest.email(), signUpRequest.password(), signUpRequest.nickname());

		return "redirect:/";
	}
}
