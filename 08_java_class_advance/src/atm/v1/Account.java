package atm.v1;

// 1. 단어블록 + ctrl + f 한 파일 내에서 단어 검색(수정도 가능)
// 2. 단어블록 + ctrl + G 
// 3. ctrl + h + 단어 (프로젝트 내에서 단어 검색(수정도 가능))
// 4. ctrl + shift _ r  파일명으로 파일 검색

public class Account {
	
	String number = "";
	int money = 0;
	
	void printOwnAccount() {
		System.out.println(number +  " : " + money);
	}
	
}
