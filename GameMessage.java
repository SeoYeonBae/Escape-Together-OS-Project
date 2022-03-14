import java.awt.event.ActionEvent;
import java.io.Serializable;

import javax.swing.JButton;

public class GameMessage implements Serializable {
	// �޽��� Ÿ�� ����
	// 1���� �޽��� ���� �ʵ�� 3���� String�� �ʵ�.
	// NO_ACT�� ������ �� �ִ� Dummy �޽���. ������ ������ ����ϱ� ���� ����� ����
	// (1) Ŭ���̾�Ʈ�� ������ �޽��� ����
	//	- LOGIN  : CLIENT �α���.
	//		�޽��� ���� : LOGIN, "�۽���", "", ""
	//	- LOGOUT : CLIENT �α׾ƿ�.
	//		�޽��� ���� : LOGOUT, "�۽���", "", ""
	// 	- CLIENT_MSG : �������� ������  ��ȭ .
	// 		�޽�������  : CLIENT_MSG, "�۽���", "������", "����"
	// (2) ������ ������ �޽��� ����
	// 	- LOGIN_FAILURE  : �α��� ����
	//		�޽��� ���� : LOGIN_FAILURE, "", "", "�α��� ���� ����"
	// 	- SERVER_MSG : Ŭ���̾�Ʈ���� �������� ������ ��ȭ 
	//		�޽�������  : SERVER_MSG, "�۽���", "", "����" 
	// 	- LOGIN_LIST : ���� �α����� ����� ����Ʈ.
	//		�޽��� ���� : LOGIN_LIST, "", "", "/�� ���е� ����� ����Ʈ"
	public enum MsgType {NO_ACT, LOGIN, LOGOUT, CLIENT_MSG, LOGIN_FAILURE, SERVER_MSG, LOGIN_LIST, GAME_SET, GAME_START, GAME_SUSPEND, GAME_REPLAY, GAME_STATUS, GAME_SCORE, GAME_STOPOVER , GAME_HEART, GAME_ATTACK, GAME_SSANG};
	public static final String ALL = "��ü";	 // ����� �� �� �ڽ��� ������ ��� �α��εǾ� �ִ�
											 // ����ڸ� ��Ÿ���� �ĺ���
	private MsgType type;
	private String sender;
	private String receiver;
	private String contents;
	private int status;
	private int score;
	private String winLose;
	private String suspendStr;
	public boolean userCheck;
	public boolean stopover;
	public boolean isSSang;
	
	public GameMessage() {
		this(MsgType.NO_ACT, "", "", "");
	}
	public GameMessage(MsgType t, String sID, String rID, String mesg) {
		type = t;
		sender = sID;
		receiver = rID;
		contents = mesg;
	}
	public GameMessage(MsgType t, String sID, String rID, int status) {
		type = t;
		sender = sID;
		receiver = rID;
		this.status = status;
	}
	public GameMessage(MsgType t, String sID, String rID, int score, String winLose) {
		type = t;
		sender = sID;
		receiver = rID;
		this.score = score;
		this.winLose=winLose;
	}	
	public GameMessage(MsgType t,String sID,String rID, boolean uk){
		type = t;
		sender = sID;
		receiver = rID;
		userCheck = uk;
	}		
	public GameMessage(MsgType t,String rID, boolean isSsang){
		type = t;
		receiver = rID;
		isSSang = isSsang;
	}		
	public GameMessage(MsgType t,String sID,String rID){
		type = t;
		sender = sID;
		receiver = rID;
	}
	public GameMessage(MsgType t,String sID,String rID, boolean uk, String suspendStr){
		type = t;
		sender = sID;
		receiver = rID;
		userCheck = uk;
		this.suspendStr = suspendStr;
	}	
	public GameMessage(MsgType t, String sID, String rID, boolean stopover, int score) {
		type = t;
		sender = sID;
		receiver = rID;
		this.stopover=stopover;
		this.score=score;
	}
			
	public void setType (MsgType t) {
		type = t;
	}
	public MsgType getType() {
		return type;
	}

	public void setSender (String id) {
		sender = id;
	}
	public String getSender() {
		return sender;
	}
	
	public void setReceiver (String id) {
		receiver = id;
	}
	public String getReceiver() {
		return receiver;
	}
	
	public void setContents (String mesg) {
		contents = mesg;
	}
	public String getContents() {
		return contents;
	}
	
		
	public boolean getUserCheck() {
		return userCheck;
	}	
	public String toString() {
		return ("�޽��� ���� : " + type + "\n" +
				"�۽���         : " + sender + "\n" +
				"������         : " + receiver + "\n" +
				"�޽��� ���� : " + contents + "\n");
	}	
	public int getStatus(){
		return status;
	}
	public boolean getSsang(){
		return isSSang;
	}
	public int getScore(){
		return score;
	}	
	public String getWinlose(){
		return winLose;
	}	
	public boolean getStart(){
		return userCheck;
	}	
	public String getSuspend() {
		return suspendStr;
	}		
	public boolean getStopover() {
		return stopover;
	}
}
