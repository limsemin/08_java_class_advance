package atm.v1;
import java.util.Random;
import java.util.Scanner;

public class ATM {
	
	Scanner scan = new Scanner(System.in);
	Random ran   = new Random();
	UserManager userManager = new UserManager();
	int identifier = -1;
	
	void printMainMenu() {
				
		while (true) {
			
			System.out.println("\n[ MEGA ATM ]");
			System.out.print("[1.로그인] [2.로그아웃] [3.회원가입] [4.회원탈퇴] [0.종료] : ");
			int sel = scan.nextInt();
			
			if      (sel == 1) 	    login();  	//로그인
			else if (sel == 2) 		logout();	//로그아웃
			else if (sel == 3) 		join();		//회원가입
			else if (sel == 4) 		leave();	//회원탈퇴
			else if (sel == 0) 		break;		//종료
			
		}
		
		System.out.println("[메시지] 프로그램을 종료합니다."); //종료버튼을 눌렀을 때 break 함수로 반복문이 엄추고 종료한다는 메세지 출력
		
		
	}
	
	
	void login() {
		
		identifier = userManager.logUser(); // identifier는 = logUser에 이름 지정 후 리턴된 값이 loguser로 이동
		
		if (identifier != -1) { //identifier(식별자)가 -1과 같지 않다면 printAccoutMenu(계정메뉴)에 담는다
			printAccountMenu();
		}
		else {
			System.out.println("[메세지] 로그인실패."); //그렇지 않은 경우에는 로그인 실패
		}
		
	}
	
	
	void join() {	
		
		userManager.addUser(); 			// addUser로 계정 생성
	}
	
	
	void logout() {
		
		if (identifier == -1) {//identifier(식별자)가 식별자의 초기값과 비교해 맞지않다면 밑에 메세지 출력
			System.out.println("[메시지] 로그인을 하신 후 이용하실 수 있습니다.");
		}
		else {
			identifier = -1;   //identifrie(식별자)가 식별자의 초기값과 동일하면 로그아웃 메세지 출력
			System.out.println("[메시지] 로그아웃 되었습니다.");
		}
		
	}
	
	
	void leave() {
		
		userManager.leave(); // 회원탈퇴
		
	}
	
	
	void printAccountMenu() { // 계정메뉴
		
		while (true) {
			
			System.out.print("[1.계좌생성] [2.계좌삭제] [3.조회] [0.로그아웃] : ");
			int sel = scan.nextInt();  // 1~0까지의 번호를 선택 입력 =sel 로 지정
			
			String makeAccount = Integer.toString(ran.nextInt(90001)+ 10000);
			 // 계좌 생성 후 랜덤으로 최대99999까지의 수로 계좌가 생성된다.
			
			if (sel == 1) { // 1번 계좌생성 입력시 ,
				if (userManager.user[identifier].accCount == 0) {
					userManager.user[identifier].acc = new Account[1]; // new Account[1]라는 배열 생성
					userManager.user[identifier].acc[0] = new Account(); //new Account[1]배열을 acc[0]에 담고 new Account()라는 객체 생성				
					userManager.user[identifier].acc[0].number = makeAccount; //배열의 번호를 makeAccount라고 지정
				
				}
				else {
					Account[] temp = userManager.getUser(identifier).acc;// 위에 만든account(계정)배열을 identifier번째의 temp로 이동시킨다
					int tempAccCount = userManager.getUser(identifier).accCount;//tempAccCount에 user를 identifier번째에 담는다. 
					userManager.user[identifier].acc = new Account[tempAccCount+1]; // 새로운 계정에 temp accCount에 +1 더하여 새로운 배열을 생성 
					for (int i = 0; i < tempAccCount; i++) { //반복문을 이용해 tempAccCount를 돌려주고 
					userManager.user[identifier].acc[i] = temp[i];//새로운 배열인 acc[i]가 temp[i]
					}												//acc = new Account[2];
					
																	//acc[0] = temp[0];
																	//acc[1].number = "12345";
					//acc[1] = new Account();
					userManager.user[identifier].acc[tempAccCount] = new Account(); // temp를 새로운 객체에 담는다.
					userManager.user[identifier].acc[tempAccCount].number = makeAccount;
				                                                     																				 
				}
				userManager.user[identifier].accCount++; //identifier번째의 user accCount+=1;
				System.out.println("[메시지]'"+makeAccount +"'계좌가 생성되었습니다.\n"); //일때 계좌 생성
			} 	
			else if (sel == 2) {
				
				if (userManager.user[identifier].accCount == 0) { // user가 identifier번째의 accCount가 0과 같을때,
					System.out.println("[메시지] 더 이상 삭제할 수 없습니다.\n");//더이상 삭제할 수 없다.
					continue; //continue
				}
				
				if ( userManager.user[identifier].accCount == 1) {//user identifier번째의 accCount가 1과 같을때  계좌번호 삭제 출력
					System.out.println("[메시지] 계좌번호 :'"+ userManager.user[identifier].acc[0].number+"' 삭제 되었습니다.\n");
					userManager.user[identifier].acc = null; //처리 후 identifier번째의 acc 값은 아무것도 없다 : null
				}
				else {
					
					System.out.print("삭제 하고 싶은 계좌 번호를 입력하세요 : ");	//계좌번호 삭제 
					String deleteAccount = scan.next(); 
					int tempAccCount = userManager.user[identifier].accCount; //위에서 temp에 저장해둔 tempAccCount를 identfier번째의 user에 저장
					int delIdx = -1; // delIdx -1은 초기식	
					for (int i = 0; i <tempAccCount; i++) {//반복문을 통해 삭제할계좌번호 배열을 찾을때까지 반복 true일땐 계속 돌고 false일땐 멈춤
						if (deleteAccount.equals(userManager.user[identifier].acc[i].number)) {
							delIdx = i;
						}
					}
																					
					if ( delIdx == -1 ) {//delIdx와 == -1을 비교해 같다면 아래 메시지 출력 반복
						System.out.println("[메시지] 계좌번호를 확인하세요.\n");
						continue;
					}
					else {
						System.out.println("[메시지] 계좌번호 :'"+ userManager.user[identifier].acc[delIdx].number+"' 삭제 되었습니다.\n");
						//계좌번호 입력에 user정보대로 입력이 되면 계좌번호 삭제
						Account[] temp = userManager.user[identifier].acc;//원하된 계좌번호를 삭제하기 위해 원래 있던 계좌의 정보는 temp로 이동시킨다
						userManager.user[identifier].acc = new Account[tempAccCount-1];
						
						
						for (int i = 0; i < delIdx; i++) {				  	// 0부터 지우기전까지 반복
							userManager.user[identifier].acc[i] = temp[i];	// 임시저장했던 데이터를 새로만든 배열에 옮겨담음.
						}
						for (int i = delIdx; i < tempAccCount - 1; i++) { // 지우고싶은 부분부터 끝-1까지 반복) 왜 끝-1이지?
							userManager.user[identifier].acc[i] = temp[i+1];
						}
						userManager.user[identifier].acc = null;
					}
					
				}
				userManager.user[identifier].accCount--;
			
			}
			
			else if (sel == 3) {//계좌 조회버튼 user정보에서 계좌정보와 비교해 맞다면 메시지 출력
				if (userManager.user[identifier].accCount == 0) {
					System.out.println("[메시지] 생성된 계좌가 없습니다.\n");
				}
				else {
					userManager.user[identifier].printAccount();
				}
			}   
			else if (sel == 0) {
				logout();
				break;
			}
			
		}
		
	}	
}
