package bank;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.SUPPORTS)
public class ShinhanBankService implements BankService {
	
	private BankDao bankDao;
	
	public void setBankDao (BankDao bankDao) {
		this.bankDao = bankDao;
	}
	
	public void addAccount(Account account) throws DataAccessException {
		bankDao.addAccount(account);
	}
	
	public Account findAccount(String accountNo) throws DataAccessException {
		return bankDao.findAccount(accountNo);
	}
	
	public List<Account> findAccountsByName(String name) throws DataAccessException {
		return bankDao.findAccountsByName(name);
	}
	
	public List<Account> getAccounts() throws DataAccessException {
		return bankDao.getAccounts();
	}
	
	public void deposit(Account account, long money) throws DataAccessException {
		bankDao.deposit(account, money);
	}
	
	public void withdraw(Account account, long money) throws DataAccessException {
		bankDao.withdraw(account, money);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void transfer(Account from, Account to, long money) throws DataAccessException {	
		bankDao.transfer(from, to, money);
	}
}