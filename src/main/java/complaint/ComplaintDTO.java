package complaint;

public class ComplaintDTO {

	int complaintID;
	String userID;
	String complaintName;
	String complaintDivide;
	String complaintContent;
	int likeCount;
	
	public int getComplaintID() {
		return complaintID;
	}
	public void setComplaintID(int complaintID) {
		this.complaintID = complaintID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getComplaintName() {
		return complaintName;
	}
	public void setComplaintName(String complaintName) {
		this.complaintName = complaintName;
	}
	public String getComplaintDivide() {
		return complaintDivide;
	}
	public void setComplaintDivide(String complaintDivide) {
		this.complaintDivide = complaintDivide;
	}
	public String getComplaintContent() {
		return complaintContent;
	}
	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	public ComplaintDTO() {
		
	}
	public ComplaintDTO(int complaintID, String userID, String complaintName, String complaintDivide,
			String complaintContent, int likeCount) {
		super();
		this.complaintID = complaintID;
		this.userID = userID;
		this.complaintName = complaintName;
		this.complaintDivide = complaintDivide;
		this.complaintContent = complaintContent;
		this.likeCount = likeCount;
	}
}
