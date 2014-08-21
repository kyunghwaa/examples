package bank;

import org.springframework.dao.DataAccessException;

@SuppressWarnings("serial")
public class LackOfBalanceException extends DataAccessException {

	public LackOfBalanceException (String message, Throwable cause) {
		super (message, cause);
	}
	
	public LackOfBalanceException (String message) {
		super (message);
	}

}