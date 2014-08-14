package bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class BankLogger {
	private Log log = LogFactory.getLog(getClass());
	
	@After("execution(* bank.*.*(..))")
	public void log(JoinPoint point) {
		log.debug(point.getSignature().getName() + " Method");
	}
}