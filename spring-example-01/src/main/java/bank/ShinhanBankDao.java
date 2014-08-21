package bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class ShinhanBankDao extends NamedParameterJdbcDaoSupport implements BankDao {
	
	private static final String SQL_INSERT_ACCOUNT = 
			"insert into " +
			"account(account_no, name, balance, account_type) " +
			"values (:accountNo, :name, :balance, :kind)";
	
	private static final String SQL_FIND_ACCOUNT_BY_NO =
			"select account_no, name, balance, account_type " +
			"from account " +
			"where account_no = :accountNo";
	
	private static final String SQL_FIND_ACCOUNTS_BY_NAME = 
			"select account_no, name, balance, account_type " +
	        "from account " +
			"where name = :name";
	
	private static final String SQL_GET_ALL_ACCOUNTS =
			"select account_no, name, balance, account_type " +
			"from account " +
			"order by account_no asc";
	
	private static final String SQL_DEPOSIT = 
			"update account " +
			"set balance = balance + :money " +
			"where account_no = :accountNo";
	
	private static final String SQL_WITHDRAW =
			"update account " +
			"set balance = balance - :money " +
			"where account_no = :accountNo";
	
	public void addAccount(Account account) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", account.getAccountNo());
		params.put("name", account.getName());
		params.put("balance", account.getBalance());
		params.put("kind", account.getKind());
		getNamedParameterJdbcTemplate().update(SQL_INSERT_ACCOUNT, params);
	}
	
	public Account findAccount(String accountNo) throws DataAccessException {
		Account account = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);
		account = getNamedParameterJdbcTemplate().queryForObject(
				SQL_FIND_ACCOUNT_BY_NO, 
				params, 
				new ParameterizedRowMapper<Account>() {
					public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
						Account account = new Account();
						account.setAccountNo(rs.getString("account_no"));
						account.setName(rs.getString("name"));
						account.setBalance(rs.getLong("balance"));
						account.setKind(rs.getString("account_type"));
						return account;
					}				
				});
		return account;
	}
	
	public List<Account> findAccountsByName(String name) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		RowMapper<Account> rowMapper = new AccountRowMapper();
		return getNamedParameterJdbcTemplate().query(SQL_FIND_ACCOUNTS_BY_NAME, params, rowMapper);
	}
	
	protected class AccountRowMapper implements RowMapper<Account> {
		
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			String accountNo = rs.getString("account_no");
			String name = rs.getString("name");
			long balance = rs.getLong("balance");
			String kind = rs.getString("account_type");
			
			Account account = new Account(accountNo, name, balance, kind);

			return account;
		}
	}
	
	public List<Account> getAccounts() throws DataAccessException {
		RowMapper<Account> rowMapper = new AccountRowMapper();
		return getJdbcTemplate().query(SQL_GET_ALL_ACCOUNTS, rowMapper);
	}
	
	public void deposit(Account account, long money) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", account.getAccountNo());
		params.put("money", money);
		getNamedParameterJdbcTemplate().update(SQL_DEPOSIT, params);
	}
	
	public void withdraw(Account account, long money) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", account.getAccountNo());
		params.put("money", money);
		getNamedParameterJdbcTemplate().update(SQL_WITHDRAW, params);
	}
	
	public void transfer(Account from, Account to, long money) throws DataAccessException {
		withdraw(from, money);
		deposit(to, money);
	}
}