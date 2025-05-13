package io.javakata.core.support.aop;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import io.javakata.core.support.error.ErrorType;
import io.javakata.core.support.error.JavaKataException;
import io.javakata.model.auth.Role;
import io.javakata.model.user.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            Admin adminAnnotation = handlerMethod.getMethodAnnotation(Admin.class);

            if (adminAnnotation != null) {
                CurrentUser user = (CurrentUser) request.getAttribute(CurrentUser.Current_USER_KEY);
                if (user == null || !user.getRole().equals(Role.ROLE_ADMIN.toString())) {
                    throw new JavaKataException(ErrorType.AUTHENTICATION_ERROR, "어드민 권한이 필요한 요청입니다.");
                }
            }
        }

        return true;
    }

}
