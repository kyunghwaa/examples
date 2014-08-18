package bank;

import org.aspectj.lang.ProceedingJoinPoint;

public class ProfilingAdvice {
	
	public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
		String signatureString = joinPoint.getSignature().toString();
		System.out.println(signatureString + " Start");
	}
	
}