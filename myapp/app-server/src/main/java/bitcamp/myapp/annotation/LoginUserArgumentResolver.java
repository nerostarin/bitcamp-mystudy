package bitcamp.myapp.annotation;

import bitcamp.myapp.vo.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //해당 파라미터에 user어노테이션이 붙어있는지 확인을 하고
        //그게 붙어있으면 파라미터는 user객체를 받을수있는 타입이어야 한다 그럼 true이다
        return parameter.hasParameterAnnotation(LoginUser.class) &&
                parameter.getParameterType().isAssignableFrom(User.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("LoginUserArgumentResolver : 호출됨");
        System.out.println("-----------------------------------------------------------------------");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        return session.getAttribute(parameter.getParameterName());
    }
}
