package atm_v2;

public class User {
	
	Account[] acc = new Account[UserManager.getInstance().ACC_MAX_CNT];	
	int accCnt;	

	String id;											
	String pw;											
	
}


