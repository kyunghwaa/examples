package bank;

import org.aspectj.lang.ProceedingJoinPoint;

public class ProfilingAdvice {
	
	public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
		String signatureString = joinPoint.getSignature().toString();
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