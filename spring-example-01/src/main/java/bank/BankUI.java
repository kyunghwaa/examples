package bank;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BankUI {
	
	public static final String ENTER = System.getProperty("line.separator");
	private String accountFile;
	private Bank bank;
	
	public void setAccountFile(String accountFile) {
		this.accountFile = accountFile;
	}
	
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	private void readyAccount() throws Exception {
		FileReader fr = null;
		
		try {
			fr = new FileReader(accountFile);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(accountFile + " 파일을 찾을 수 없습니다!");
		}
		
		BufferedReader br = new BufferedReader(fr);
		String strAccount = null;
		
		try {
			while ((strAccount = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(strAccount, "|");
				String accountNo = st.nextToken();
				String name = st.nextToken();
				long balance = Long.parseLong(st.nextToken());
				String kind = st.nextToken();
				
				bank.addAccount(name, accountNo, balance, kind);
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException("잔고를 숫자로 바꿀 수 없습니다!");
		} catch (IOException e) {
			throw new IOException("IO Exception이 발생했습니다!");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Start Bank Work
	public void startWork() throws Exception {
		readyAccount();
		String menu = null;
		
		do {
			System.out.println(" ******************");
			System.out.println(" ** 메뉴를 선택하세요 ** ");
			System.out.println(" ******************");
			System.out.println(" 1 *** 계좌등록");
			System.out.println(" 2 *** 계좌목록");
			System.out.println(" 3 *** 입금");
			System.out.println(" 4 *** 출급");
			System.out.println(" 5 *** 이체");
			System.out.println(" q *** 종료");
			System.out.println(" ******************");
			System.out.print(">> ");
			
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				menu = br.readLine();
				
				if (menu.equals("1")) {
					// 계좌등록
					System.out.print("계좌번호를 입력하세요>> ");
					String accountNo = br.readLine();
					if (bank.findAccount(accountNo) != null) {
						System.out.println("이미 계좌번호가 있습니다!!");	
					} else {
						System.out.print("이름을 입력하세요>> ");
						String name = br.readLine();
						System.out.print("일반계좌는 1, 마이너스계좌는 2를 입력하세요>> ");
						String kind = br.readLine();
						if (kind.equals("1")) {
							bank.addAccount(name, accountNo, Account.ACCOUNT_NORMAL);
						} else if (kind.equals("2")) {
							bank.addAccount(name, accountNo, Account.ACCOUNT_MINUS);
						} else {
							System.out.println("잘못 입력했습니다");
						}						
					}
				} else if (menu.equals("2")) {
					// 계좌목록
					ArrayList<Account> accounts = bank.getAccounts();
					int totalAccount = accounts.size();
					for (int i = 0; i < totalAccount; i++) {
						System.out.println(accounts.get(i));
					}
				} else if (menu.equals("3")) {
					// 입금
					System.out.print("계좌번호를 입력하세요>> ");
					String accountNo = br.readLine();
					Account account = bank.findAccount(accountNo);
					if (account != null) {
						System.out.print("입금액을 입력하세요>> ");
						long money = Long.parseLong(br.readLine());
						account.deposit(money);
					} else {
						System.out.println("계좌번호를 잘못 입력했습니다");
					}
				} else if (menu.equals("4")) {
					// 출금
					System.out.print("계좌번호를 입력하세요>> ");
					String accountNo = br.readLine();
					Account account = bank.findAccount(accountNo);
					if (account != null) {
						System.out.print("출금액을 입력하세요>> ");
						long money = Long.parseLong(br.readLine());
						account.withdraw(money);
					} else {
						System.out.println("계좌번호를 잘못 입력했습니다");
					}					
				} else if (menu.equals("5")) {
					// 이체
					System.out.println("FROM 계좌에서 TO 계좌로 이체합니다");
					System.out.print("FROM 계좌번호를 입력하세요>> ");
					String accountNo = br.readLine();
					Account from = bank.findAccount(accountNo);
					if (from != null) {
						System.out.print("TO 계좌번호를 입력하세요>> ");
						accountNo = br.readLine();
						Account to = bank.findAccount(accountNo);
						if (to != null) {
							System.out.print("이체액을 입력하세요>> ");
							long money = Long.parseLong(br.readLine());
							from.withdraw(money);
							to.deposit(money);
						} else {
							System.out.println("계좌번호를 잘못 입력했습니다");
						}
					} else {
						System.out.println("계좌번호를 잘못 입력했습니다");
					}										
				}	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println();
			
		} while (!menu.equals("q"));
		
		endWork();
	}
	
	// End Bank Work
	private void endWork() {
		StringBuilder sb = new StringBuilder();
		ArrayList<Account> accounts = bank.getAccounts();
		int totalAccount = accounts.size();
		for (int i = 0; i < totalAccount; i++) {
			sb.append(accounts.get(i));
			sb.append(ENTER);
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(accountFile, false);
			bw = new BufferedWriter(fw);
			bw.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		BankUI ui = (BankUI) ctx.getBean("BankUI");
		try {
			ui.startWork();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("프로그램이 정상적으로 종료되었습니다.");
		((AbstractApplicationContext) ctx).close();
	}
}