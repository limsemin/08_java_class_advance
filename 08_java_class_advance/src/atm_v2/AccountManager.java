package atm_v2;

import java.util.Random;
import java.util.Scanner;

public class AccountManager {

	private AccountManager() {}
	private static AccountManager instance = new AccountManager(); //새로운 AccountManager 객체 생성
	public static AccountManager getInstance() { //getInstance앞엔 반드시 public을 사용한다.
		return instance; // (getInstance)객체 를 찾을때 까지 리턴 
	}
	
	Scanner scan = new Scanner(System.in);
	Random ran = new Random();
	UserManager um = UserManager.getInstance();

	void createAcc(int identifier) { //void는 리턴할 수 없다 identifier를 통해 식별자를 찾는다.
		
		int accCntByUser = um.userList[identifier].accCnt; // accCntByUser로 새 배열 생성
		
		if (accCntByUser == 3) { //accCntByUser가 3과 같을 때
			System.out.println("[메세지]계좌개설은 3개까지만 가능합니다.");
			return;
		}
		
		um.userList[identifier].acc[accCntByUser] = new Account();//um.userList새 배열을 new Account()객체로 보낸다.
																	
		String makeAccount = "";// 계좌 생성
		while (true) {
			makeAccount = ran.nextInt(9000000) + 1000001 + "";//계좌가 랜덤으로 천만자리 수 중에서 생성 		
			if (!um.getCheckAcc(makeAccount)){// 만약 um.getCheckAcc가 계좌생성과 맞지 않을때
				break; 						  // 멈춘다.
			}
		}
		um.userList[identifier].acc[accCntByUser].accNumber = makeAccount; //userList배열의 ?번째의 account배열의 ?번째의 번호(계좌)가 makeAccount에 저장된다 
		um.userList[identifier].accCnt++; 
		System.out.println("[메세지]'" + makeAccount + "'계좌가 생성되었습니다.\n");
	}
	
	void deleteIdx(int identifier) {
	}
	
	void printAcc(int identifier) {
		
		User temp = um.userList[identifier]; // temp에 userList 배열을 저장한다.
		System.out.println("====================");
		System.out.println("ID : " + temp.id); // temp.id를 불러와 id입력 
		System.out.println("====================");
		for (int i=0; i<temp.accCnt; i++) {	// accCnt를 반복
			System.out.println("accNumber:" +temp.acc[i].accNumber + " / money: " + temp.acc[i].money);
		}									//temp에서 acc배열 i번째의 accNumber가 계좌번호 / temp의 acc배열의 i번째의 money가 돈
		System.out.println("=============================\n");
		
	}


	
	
}
