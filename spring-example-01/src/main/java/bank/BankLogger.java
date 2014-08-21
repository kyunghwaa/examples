package bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class BankLogger {
	private Log log = LogFactory.getLog(getClass());
	private static final String NEW_LINE = System.getProperty("line.separator");
	
	//@After("execution(* bank.*.*(..))")
	@AfterThrowing(pointcut="execution(* bank.*.*(..))", throwing="e")	
	public void log(JoinPoint point, Throwable e) {
		//log.debug(point.getSignature().getName() + " Method");
		if (log.isDebugEnabled()) {
			StringBuilder msg = new StringBuilder();
			msg.append(">> " + point.getSignature().toShortString() + " Method" + NEW_LINE);
			msg.append("Message: " + e.getMessage() + NEW_LINE);
			log.debug(msg);
			//e.printStackTrace();
		}
	}
}