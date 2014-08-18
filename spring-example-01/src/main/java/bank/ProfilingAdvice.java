package bank;

import org.aspectj.lang.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;

@Aspect
public class ProfilingAdvice {
	
	@Around("execution(* bank..*(..))")
	public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
		String signatureString = joinPoint.getSignature().getName();
		System.out.println(signatureString + " Start");
		long start = System.currentTimeMillis();
		try {
			Object result = joinPoint.proceed();
			return result;
		} finally {
			long end = System.currentTimeMillis();
			System.out.println(signatureString + " End");
			System.out.println(signatureString + " Execution Time: " + (end - start) + "ms");
		}
	}
	
}