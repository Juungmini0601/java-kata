package io.javakata.core.support.aop;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.javakata.core.support.error.ErrorType;
import io.javakata.core.support.error.JavaKataException;
import io.javakata.model.user.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class) && parameter.getParameterType().equals(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        CurrentUser currentUser = (CurrentUser) request.getAttribute(CurrentUser.Current_USER_KEY);

        if (currentUser == null) {
            throw new JavaKataException(ErrorType.AUTHENTICATION_ERROR, "인증이 필요한 요청입니다.");
        }

        return currentUser;
    }

}