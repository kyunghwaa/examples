package bank;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface BankDao {
	
	public void addAccount(Account account) throws DataAccessException;
	
	public Account findAccount(String accountNo) throws DataAccessException;
	
	public List<Account> findAccountsByName(String name) throws DataAccessException;
	
	public List<Account> getAccounts() throws DataAccessException;
	
	public void deposit(Account account, long money) throws DataAccessException;
	
	public void withdraw(Account account, long money) throws DataAccessException;
	
	public void transfer(Account from, Account to, long money) throws DataAccessException;
}