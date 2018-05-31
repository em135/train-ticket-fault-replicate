package consign.conf;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {

    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * consign.controller.ConsignController.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURI();
        String thisServiceName = request.getServerName();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();

        logger.info("[Service:" + thisServiceName + "]" +
                    "[URI:" + thisServiceName + url + "]" +
                    "[Method:" + method + "]" +
                    "[RemoteHost:" + remoteHost + "]" +
                    "[IP:" + ip + "]");

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String thisServiceName = request.getServerName();

        if(ret != null){
            logger.info("[Service:" + thisServiceName + "]" + "[Response:" + new Gson().toJson(ret) + "]");
        }else{
            logger.info("[Service:" + thisServiceName + "]" + "[Response:void]");
        }
    }
}