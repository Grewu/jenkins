package annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import util.ConnectionHolder;

@Aspect
@Component
public class TransactionAspect {

    @Pointcut("@annotation(Transactional)")
    public void transactionMethods() {
    }

    @Around("transactionMethods()")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        var connection = ConnectionHolder.getConnection();
        try {
            connection.setAutoCommit(false);
            System.out.println("Transaction start" + joinPoint);
            Object result = joinPoint.proceed();
            connection.commit();
            System.out.println("Transaction committed");
            return result;
        } catch (RuntimeException e) {
            connection.rollback();
            System.out.println("Transaction rolled back" + joinPoint);
            throw e;
        } finally {
            ConnectionHolder.closeConnection();
            System.out.println("Transaction ended");
        }
    }
}
