package likey;

public class LikeyDTO {

	String userID;
	int complaintID;
	String userIP;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getComplaintID() {
		return complaintID;
	}
	public void setComplaintID(int complaintID) {
		this.complaintID = complaintID;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	
	public LikeyDTO() {
		
	}
	public LikeyDTO(String userID, int complaintID, String userIP) {
		super();
		this.userID = userID;
		this.complaintID = complaintID;
		this.userIP = userIP;
	}
	
}
