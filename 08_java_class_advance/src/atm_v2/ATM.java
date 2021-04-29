package atm_v2;

import java.util.Scanner;

import atm.v1.Account;

public class ATM {
	
	Scanner scan = new Scanner(System.in);
	int identifier = -1; // identifier 초기식은 -1
	UserManager um = UserManager.getInstance();	//instance를 반환할 getter메서드를 만듬 = um으로 지정 
	
	void play() {
		
		FileManager.getInstance().load(); // 객체
		UserManager.getInstance().printAllUser();	//  객체
		
		while (true) {
			
			System.out.println("[ATM]");
			System.out.println("[1.회원가입]\n[2.로그인]\n[0.종료]");
			System.out.print("메뉴 선택 : ");
			int sel = scan.nextInt();
			
			if		(sel == 1)  join();
			else if (sel == 2)  login();  
			else if (sel == 0)  break;
			
		}
		
	}
	
	
	void login() {
		
		identifier = um.logUser();
		if (identifier != -1) loginMenu();
		else 				  System.out.println("[메세지]아이디와 패스워드를 확인해주세요.");

	}
	
	
	void loginMenu() {
		
		while (true) {
			
			System.out.println("[" + um.userList[identifier].id + "님, 환영합니다.]");
			System.out.println("[1.계좌생성]\n[2.계좌삭제]\n[3.조    회]\n[4.회원탈퇴]\n[0.로그아웃]");
			System.out.println("메뉴 선택 : ");
			int selectMenu = scan.nextInt();
			
			if (selectMenu == 1) {
				AccountManager.getInstance().createAcc(identifier);
				FileManager.getInstance().save();
				
			}
			else if (selectMenu == 2) {
				System.out.println("[메시지] 삭제 할 계좌번호를 입력하세요");
				String deleteAccount = scan.next();
				int tempAccount = 0;
				int delIdx = -1;
					for(int i =0; i<tempAccount; i++) {
						//if(.getInstance().deleteIdx(identifier)){}
					}
			}
			else if (selectMenu == 3) {
				AccountManager.getInstance().printAcc(identifier);
			}
			else if (selectMenu == 4) {
				identifier = um.deleteMember(identifier);
				break;
			}
			else if (selectMenu == 0) {
				identifier = -1;
				System.out.println("로그아웃 되었습니다.");
				break;
			}
			
		}
		
	}
	
	
	void join() {
		
		UserManager.getInstance().joinMember();
		
	}
	

	
}
