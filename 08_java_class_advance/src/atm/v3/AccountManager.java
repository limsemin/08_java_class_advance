package atm.v3;


public class AccountManager {

	private AccountManager() {}
	private static AccountManager instance = new AccountManager(); // AccountManager객체 생성
	public static AccountManager getInstance() {				  	
		return instance;
	}
	
	UserManager userManager = UserManager.getInstance();
	//use
	void createAccount() {
		
		User loginUser = userManager.userList[userManager.identifier];
		
		if (loginUser.accCount == 3) {
			System.out.println("[메세지]더 이상 계좌를 생성할 수 없습니다.\n");
			return;
		}
		
		if (loginUser.accCount == 0) {
			loginUser.accList = new Account[loginUser.accCount + 1];
		} 
		else if (loginUser.accCount  > 0) {
			Account[] temp = loginUser.accList;
			
			loginUser.accList = new Account[loginUser.accCount + 1];
			for(int i=0; i<loginUser.accCount; i++) {
				loginUser.accList[i] = temp[i];
			}
			temp = null;
		}
		loginUser.accList[loginUser.accCount] = new Account();
		
		String makeAccount = ATM.ran.nextInt(90000000) + 10000001 +"";
		loginUser.accList[loginUser.accCount].number = makeAccount;
		loginUser.accList[loginUser.accCount].money = 0;
		
		loginUser.accCount++;
		System.out.println("[메세지]계좌가 생성되었습니다.\n");
		
		FileManager.getInstance().saveData();
		
	}
	
	
	int showAccList(String msg) {
		
		int loginAccIndex = -1;

		User loginUser = userManager.userList[userManager.identifier];
		
		if (loginUser.accCount > 0) {
			for (int i=0; i<loginUser.accCount; i++) {
				System.out.println("[" + (i+1) + "]" + loginUser.accList[i].number);
			}
			
			System.out.print("[" + msg + "]내 계좌를 메뉴에서 선택하세요 : ");
			loginAccIndex = ATM.scan.nextInt();
			loginAccIndex--;
		}
		
		return loginAccIndex;
		
	}
	
	
	void income() {
		
		int loginAccIndex = showAccList("입금");
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}
		
		System.out.print("[입금]금액을 입력하세요 : ");
		int money = ATM.scan.nextInt();
		
		userManager.userList[userManager.identifier].accList[loginAccIndex].money += money;
		System.out.println("[메세지]입금을 완료하였습니다.\n");
		
		FileManager.getInstance().saveData();
		
	}
	
	
	void outcome() {
		
		int loginAccIndex = showAccList("출금");
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}
		
		System.out.print("[출금]금액을 입력하세요 : ");
		int money = ATM.scan.nextInt();
		
		if (userManager.userList[userManager.identifier].accList[loginAccIndex].money < money) {
			//userList배열의 usermanager,identifier번쨰와 accList의 로그인 번쨰의 값이 money보다 작다면 잔액 부족
			System.out.println("[메세지]계좌잔액이 부족합니다.\n");
			return;
		}
		
		userManager.userList[userManager.identifier].accList[loginAccIndex].money -= money;
		System.out.println("[메세지]출금을 완료하였습니다.\n");
			//userList배열의 usermanager,identifier 번째와 accList의 로그인 번쨰의 값을 money에서 뺀 뒤 다시 대입        
		FileManager.getInstance().saveData();
		//fileManager의 getInstance , saveData로 이동하면서 객체를 생성
	}
	
	
	int checkAcc(String transAccount) {
		
		int check = -1;
		for (int i=0; i<userManager.userList.length; i++) {
			if (userManager.userList[i].accList != null) {
				for (int j=0; j<userManager.userList[i].accCount; j++) {
					if (transAccount.equals(userManager.userList[i].accList[j].number)) {
						check = i;
					}//반복문을 이용하여 equals 객체의 내용 비교
				}
			}
		}
		return check; //리턴
		
	}
	
	
	int getAccIndex(int transUserIndex, String transAccount) {
		//
		int accIndex = 0;
		
		for (int i=0; i<userManager.userList[transUserIndex].accCount; i++) {
			if (transAccount.equals(userManager.userList[transUserIndex].accList[i].number)) {
				accIndex = i;
			}
		}
		
		return accIndex;
		
	}
	
	
	void transfer() { // 이체
		
		int loginAccIndex = showAccList("이체"); //로그인정보와 accList(이체)
		if (loginAccIndex == -1) {//로그인인덱스와 -1 이 같다면  계좌를 먼저 생성해야한다
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;// 리턴
		}		
		
		System.out.print("[이체]이체할 '계좌번호'를 입력하세요 : ");
		String transAccount = ATM.scan.next(); //계좌생성을 했다면 이체할 계좌번호 입력
		
		int transUserIndex = checkAcc(transAccount);
		if (transUserIndex == -1) {//이체할유저 인덱스와 -1이 같다면 계좌번호 확인
			System.out.println("[메세지]'계좌번호'를 확인해주세요.\n");
			return;
		}
		
		int transAccIndex = getAccIndex(transUserIndex, transAccount);
		//이체할유저 인덱스와 getAccIndex객체의 유저 인덱스와 만든계정이 같다면 이체금액 입력
		System.out.print("[이체]금액을 입력하세요 : ");
		int money = ATM.scan.nextInt();
		
		if (money > userManager.userList[userManager.identifier].accList[loginAccIndex].money) {
			System.out.println("[메세지]계좌잔액이 부족합니다.\n");
			return;//입력한 돈이 userList배열의 usermanager,identifier번째와  
				   //accList 배열의 로그인배열의 돈 == 저장된돈보다 부족하다면 이체 실패
		}
		
		userManager.userList[userManager.identifier].accList[loginAccIndex].money -= money;
		userManager.userList[transUserIndex].accList[transAccIndex].money += money;
		System.out.println("[메세지]이체를 완료하였습니다.\n");
		
		FileManager.getInstance().saveData();
	}
	
	
	void lookupAcc() {
		userManager.userList[userManager.identifier].printOneUserAllAccounts();
	}

	
	void deleteAcc() {
		
		int loginAccIndex = showAccList("삭제");
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}
		
		User user = userManager.userList[userManager.identifier];
		
		if (user.accCount == 1) {
			user.accList = null;
		}
		else if(user.accCount > 1) {
			Account[] acc = user.accList;
			
			user.accList = new Account[user.accCount - 1];
			int j = 0;
			for (int i=0; i<user.accCount; i++) {
				if (i != loginAccIndex) {
					user.accList[j] = acc[i];
					j = j + 1;
				}
			}
			acc = null;
		}
		user.accCount--;
		
		System.out.println("[메세지]계좌삭제를 완료하였습니다.\n");
		
		FileManager.getInstance().saveData();
	
	}
	
	
}
