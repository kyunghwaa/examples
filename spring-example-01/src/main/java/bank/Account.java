package bank;

public class Account {
	private String name;
	private String accountNo;
	protected long balance;
	private String kind;
	
	public static final String ACCOUNT_NORMAL = "일반";
	public static final String ACCOUNT_MINUS = "마이너스";
	public static final String DEPOSIT = "입금";
	public static final String WITHDRAW = "출금";
	
	public Account () {}
	
	public Account (String name, String accountNo, String kind) {
		this.name = name;
		this.accountNo = accountNo;
		this.kind = kind;
	}
	
	public Account (String name, String accountNo, long balance, String kind) {
		this.name = name;
		this.accountNo = accountNo;
		this.balance = balance;
		this.kind = kind;		
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getAccountNo () {
		return accountNo;
	}
	
	public void setAccountNo (String accountNo) {
		this.accountNo = accountNo;
	}	
	
	public long getBalance () {
		return balance;
	}
	
	public void setBalance (long balance) {
		this.balance = balance;
	}	
	
	public String getKind () {
		return kind;
	}
	
	public void setKind (String kind) {
		this.kind = kind;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder ();
		
		sb.append(accountNo);
		sb.append("|");
		sb.append(name);
		sb.append("|");
		sb.append(balance);
		sb.append("|");
		sb.append(kind);

		return sb.toString();
	}
}