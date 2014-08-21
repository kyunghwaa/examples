package bank;

import java.io.*;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BankUI {
	
	private BankService bankService;
	
	public void setBankService (BankService bankService) {
		this.bankService = bankService;
	}
	
	public void startWork() throws Exception {
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
					Account account = null;
					System.out.print("계좌번호를 입력하세요>> ");
					String accountNo = br.readLine();
					System.out.print("이름을 입력하세요>> ");
					String name = br.readLine();
					System.out.print("일반계좌는 1, 마이너스계좌는 2를 입력하세요>> ");
					String kind = br.readLine();

					if (kind.equals("1")) {
						account = new Account(name, accountNo, Account.ACCOUNT_NORMAL);
						bankService.addAccount(account);
					} else if (kind.equals("2")) {
						account = new Account(name, accountNo, Account.ACCOUNT_MINUS);
						bankService.addAccount(account);
					} else {
						System.out.println("잘못 입력했습니다");
					}						
				} else if (menu.equals("2")) {
					// 계좌목록
					List<Account> accounts = bankService.getAccounts();
					int totalAccount = accounts.size();
					for (int i = 0; i < totalAccount; i++) {
						System.out.println(accounts.get(i));
					}
				} else if (menu.equals("3")) {
					// 입금
					System.out.print("계좌번호를 입력하세요>> ");
					String accountNo = br.readLine();
					Account account = bankService.findAccount(accountNo);
					if (account != null) {
						System.out.print("입금액을 입력하세요>> ");
						long money = Long.parseLong(br.readLine());
						bankService.deposit(account, money);
					} else {
						System.out.println("계좌번호를 잘못 입력했습니다");
					}
				} else if (menu.equals("4")) {
					// 출금
					System.out.print("계좌번호를 입력하세요>> ");
					String accountNo = br.readLine();
					Account account = bankService.findAccount(accountNo);
					if (account != null) {
						System.out.print("출금액을 입력하세요>> ");
						long money = Long.parseLong(br.readLine());
						bankService.withdraw(account, money);
					} else {
						System.out.println("계좌번호를 잘못 입력했습니다");
					}					
				} else if (menu.equals("5")) {
					// 이체
					System.out.println("FROM 계좌에서 TO 계좌로 이체합니다");
					System.out.print("FROM 계좌번호를 입력하세요>> ");
					String accountNo = br.readLine();
					Account from = bankService.findAccount(accountNo);
					if (from != null) {
						System.out.print("TO 계좌번호를 입력하세요>> ");
						accountNo = br.readLine();
						Account to = bankService.findAccount(accountNo);
						if (to != null) {
							System.out.print("이체액을 입력하세요>> ");
							long money = Long.parseLong(br.readLine());
							bankService.transfer(from, to, money);
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
			
	}
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		BankUI bankUI = (BankUI) ctx.getBean("bankUI");
		try {
			bankUI.startWork();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("프로그램이 정상적으로 종료되었습니다.");
		((AbstractApplicationContext) ctx).close();
	}
}