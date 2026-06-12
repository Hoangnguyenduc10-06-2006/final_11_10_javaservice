package job_portal_systemapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExecutionTimeLoggingAspect {

    @Around("execution(* job_portal_systemapi.service.impl.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        try {

            Object result = joinPoint.proceed();

            long end = System.currentTimeMillis();

            log.info(
                    "Phương thức [{}] thực thi thành công trong {} ms",
                    joinPoint.getSignature().toShortString(),
                    end - start
            );

            return result;

        } catch (Throwable ex) {

            long end = System.currentTimeMillis();

            log.error(
                    "Phương thức [{}] thực thi thất bại sau {} ms. Lỗi: {}",
                    joinPoint.getSignature().toShortString(),
                    end - start,
                    ex.getMessage()
            );

            throw ex;
        }
    }
}