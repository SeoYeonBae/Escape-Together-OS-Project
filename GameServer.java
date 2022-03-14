import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;
import java.util.*;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class GameServer {
	// ������ Ŭ���̾�Ʈ�� ����� �̸��� ��� ��Ʈ���� �ؽ� ���̺� ����
	// ���߿� Ư�� ����ڿ��� �޽����� ������ ���. ���� ������ �ִ� ������� ��ü ����Ʈ�� ���Ҷ��� ���
	HashMap<String, ObjectOutputStream> clientOutputStreams =
			new HashMap<String, ObjectOutputStream>();

	public static void main (String[] args) {
		new GameServer().go();
	}

	private void go () {
		try {
			ServerSocket serverSock = new ServerSocket(5000);	// ä���� ���� ���� ��Ʈ 5000 ���

			while(true) {
				Socket clientSocket = serverSock.accept();		// ���ο� Ŭ���̾�Ʈ ���� ���

				// Ŭ���̾�Ʈ�� ���� ����� ��Ʈ�� �� ������ ����
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();									
				System.out.println("S : Ŭ���̾�Ʈ ���� ��");		// ���¸� �������� ��� �޽���
			}
		} catch(Exception ex) {
			System.out.println("S : Ŭ���̾�Ʈ  ���� �� �̻�߻�");	// ���¸� �������� ���  �޽���
			ex.printStackTrace();
		}
	}

	// Client �� 1:1 �����ϴ� �޽��� ���� ������
	private class ClientHandler implements Runnable {
		Socket sock;					// Ŭ���̾�Ʈ ����� ����
		ObjectInputStream reader;		// Ŭ���̾�Ʈ�� ���� �����ϱ� ���� ��Ʈ��
		ObjectOutputStream writer;		// Ŭ���̾�Ʈ�� �۽��ϱ� ���� ��Ʈ��

		// ������. Ŭ���̾�Ʈ���� ���Ͽ��� �б�� ���� ��Ʈ�� ����� ��
		// ��Ʈ���� ���鶧 InputStream�� ���� ����� Hang��. �׷��� OutputStream���� �������.
		// Bug ����... � ������ �ִ� ������ ���߿� ã�ƺ���� ��
		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				writer = new ObjectOutputStream(clientSocket.getOutputStream());
				reader = new ObjectInputStream(clientSocket.getInputStream());
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}

		// Ŭ���̾�Ʈ���� ���� �޽����� ���� �����ϴ� �۾��� ����
		public void run() {
			GameMessage message;
			GameMessage.MsgType type;
			try {
				while (true) {

					// ���� �޽����� ������ ���� ���� ������ ������ ����
					message = (GameMessage) reader.readObject();	  // Ŭ���̾�Ʈ�� ���� �޽��� ����
					type = message.getType();
					if (type == GameMessage.MsgType.LOGIN) {		  // Ŭ���̾�Ʈ �α��� ��û
						handleLogin(message.getSender(),writer);	  // Ŭ���̾�Ʈ �̸��� �׿��� �޽�����
						// ���� ��Ʈ���� ���
					}
					else if (type == GameMessage.MsgType.LOGOUT) {	  // Ŭ���̾�Ʈ �α׾ƿ� ��û
						handleLogout(message.getSender());			  // ��ϵ� �̸� �� �̿� ����� ��Ʈ�� ����
						writer.close(); reader.close(); sock.close(); // �� Ŭ���̾�Ʈ�� ���õ� ��Ʈ���� �ݱ�
						return;										  // ������ ����
					}
					else if (type == GameMessage.MsgType.CLIENT_MSG) {
						handleMessage(message.getSender(), message.getReceiver(), message.getContents());
					}
					else if (type == GameMessage.MsgType.NO_ACT) {
						//  �����ص� �Ǵ� �޽���
						continue;
					}
					else if (type == GameMessage.MsgType.GAME_STATUS) {
						handleStatus(message.getSender(), message.getReceiver(), message.getStatus());
					}
					else if (type == GameMessage.MsgType.GAME_SCORE) {
						handleScore(message.getSender(), message.getReceiver(), message.getScore(),message.getWinlose());
					}
					else if (type == GameMessage.MsgType.GAME_SET) {
						handleSet(message.getSender(), message.getReceiver(), message.getUserCheck());
					}
					else if (type == GameMessage.MsgType.GAME_START) {
						handleStart(message.getSender(), message.getReceiver(), message.getStart());
					}
					else if(type== GameMessage.MsgType.GAME_SUSPEND) {
						handleSuspend(message.getSender(), message.getReceiver(), message.getStart(), message.getSuspend());
					}	
					else if(type== GameMessage.MsgType.GAME_REPLAY) {
						handleReplay(message.getSender(), message.getReceiver(), message.getStart(), message.getSuspend());
					}	
					else if(type==GameMessage.MsgType.GAME_STOPOVER) {
						handleStopover(message.getSender(), message.getReceiver(), message.getStopover(), message.getScore());
					}
					else if(type==GameMessage.MsgType.GAME_ATTACK) {
						handleAttack(message.getSender(), message.getReceiver());
					}
					else if(type==GameMessage.MsgType.GAME_SSANG) {
						handleSsang(message.getReceiver(), message.getSsang());
					}
					else {
						// ��ü�� Ȯ�ε��� �ʴ� �̻��� �޽���?
						throw new Exception("S : Ŭ���̾�Ʈ���� �˼� ���� �޽��� ��������");
					}
				}
			} 
			catch(Exception ex) {
				System.out.println("S : Ŭ���̾�Ʈ ���� ����");			// ����� Ŭ���̾�Ʈ ����Ǹ� ���ܹ߻�
				// �̸� �̿��� ������ �����Ŵ
			}
		} // close run
	} // close inner class
	//**************************************************************************
	// ����� �̸��� Ŭ���̾�Ʈ���� ��� ��Ʈ���� �������� �ؽ� ���̺� �־���.
	// �̹� ������ �̸��� ����ڰ� �ִٸ�, ������ �α����� ���� �Ѱ����� Ŭ���̾�Ʈ���� �˸�
	// �׸��� ���ο� ������ ����Ʈ�� ��� �����ڿ��� ������
	// �ؽ� ���̺��� ���ٿ����� �������� ����� ��� (not Thread-Safe. Synchronized�� ��ȣ���� ��.
	private synchronized void handleLogin(String user, ObjectOutputStream writer) {
		try {
			// �̹� ������ �̸��� ����ڰ� �ִٸ�, ������ �α����� ���� �Ѱ����� Ŭ���̾�Ʈ���� �˸�
			if (clientOutputStreams.containsKey(user)) {
				writer.writeObject(new GameMessage(GameMessage.MsgType.LOGIN_FAILURE, "", "", "����� �̹� ����"));
				writer.flush();
				return;
			}
		} catch (Exception ex) {
			System.out.println("S : login_failure, �������� �۽� �� �̻� �߻�");
			//ex.printStackTrace();
		}
		// �ؽ����̺� �����-���۽�Ʈ�� �� �߰��ϰ� ���ο� �α��� ����Ʈ�� ��ο��� �˸�
		clientOutputStreams.put(user, writer);
		// ���ο� �α��� ����Ʈ�� ��ü���� ���� ��
		broadcastMessage(new GameMessage(GameMessage.MsgType.LOGIN_LIST, "", "", makeClientList()));
	}  // close handleLogin
	//**************************************************************************
	// �־��� ����ڸ� �ؽ����̺��� ���� (��� ��Ʈ���� ����)
	// �׸��� ������Ʈ�� ������ ����Ʈ�� ��� �����ڿ��� ������
	private synchronized void handleLogout(String user) {
		clientOutputStreams.remove(user);
		// ���ο� �α��� ����Ʈ�� ��ü���� ���� ��
		broadcastMessage(new GameMessage(GameMessage.MsgType.LOGIN_LIST, "", "", makeClientList()));
	}  // close handleLogout
	//**************************************************************************
	// Ŭ���̾�Ʈ�� ��ȭ ���濡�� ������ �޽���. �� ��� Ȥ�� "��ü"���� ���� �־�� ��
	private synchronized void handleMessage(String sender, String receiver, String contents) {
		// ���⼭ ��ο��� ������ ��츦 ó���ؾ� ��
		if (receiver.equals(GameMessage.ALL)) {			// "��ü"���� ������ �޽����̸�
			broadcastMessage(new GameMessage(GameMessage.MsgType.SERVER_MSG, sender, "", contents));
			return;
		}
		// Ư�� ��뿡�� ������ �����
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.SERVER_MSG, sender, "", contents));
			write.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}  // close handleIncomingMessage
	//**************************************************************************
	// STATUS
	private synchronized void handleStatus(String sender, String receiver, int status) {
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.GAME_STATUS, sender,"", status));
			write.flush();		
		} catch (Exception ex) {
			System.out.println("S : game_status, �������� �۽� �� �̻� �߻�");
			//ex.printStackTrace();
		}
	}  // close handleIncomingMessage
	//**************************************************************************
	// SCORE
	private synchronized void handleScore(String sender, String receiver, int score, String winLose) {
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		//System.out.println("receiver : "+write);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.GAME_SCORE, sender,"", score, winLose));		
			write.flush();		
		} catch (Exception ex) {
			System.out.println("S : game_score, �������� �۽� �� �̻� �߻�");
			//System.out.println("sender : "+sender + " / receiver : "+receiver + " / score : "+score+" / winlose : "+winLose);
			//ex.printStackTrace();
		}
	} // close handleIncomingMessage
	//**************************************************************************
	// SCORE
	private synchronized void handleAttack(String sender, String receiver) {
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		//System.out.println("receiver : "+write);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.GAME_ATTACK, sender,""));		
			write.flush();		
		} catch (Exception ex) {
			System.out.println("S : game_score, �������� �۽� �� �̻� �߻�");
			//System.out.println("sender : "+sender + " / receiver : "+receiver + " / score : "+score+" / winlose : "+winLose);
			//ex.printStackTrace();
		}
	} // close handleIncomingMessage
	//**************************************************************************
	// READY
	private synchronized void handleSet(String sender, String receiver, boolean s) {
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.GAME_SET, sender, "", s));
			write.flush();		
		} catch (Exception ex) {
			System.out.println("S : game_set, �������� �۽� �� �̻� �߻�");
			//ex.printStackTrace();
		}
	} // close handleIncomingMessage
	//**************************************************************************
	// READY
	private synchronized void handleSsang(String receiver, boolean s) {
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.GAME_SSANG,"", s));
			write.flush();		
		} catch (Exception ex) {
			System.out.println("S : game_set, �������� �۽� �� �̻� �߻�");
			//ex.printStackTrace();
		}
	} // close handleIncomingMessage
	//**************************************************************************
	// START
	private synchronized void handleStart(String sender, String receiver, boolean s) {
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.GAME_START, sender, "", true));
			write.flush();		
		} catch (Exception ex) {
			System.out.println("S : game_start, �������� �۽� �� �̻� �߻�");
			//ex.printStackTrace();
		}
	} // close handleIncomingMessage
	//**************************************************************************
	// SUSPEND
	private synchronized void handleSuspend(String sender, String receiver, boolean s, String suspend) {
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.GAME_SUSPEND, sender, "", false, suspend));
			write.flush();		
		} catch (Exception ex) {
			System.out.println("S : game_suspend, �������� �۽� �� �̻� �߻�");
			//ex.printStackTrace();
		}
	} // close handleIncomingMessage
	//**************************************************************************
	// REPLAY
	private synchronized void handleReplay(String sender, String receiver, boolean s, String suspend) {
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.GAME_REPLAY, sender, "", true, suspend));
			write.flush();		
		} catch (Exception ex) {
			System.out.println("S : game_replay, �������� �۽� �� �̻� �߻�");
			//ex.printStackTrace();
		}
	} // close handleIncomingMessage
	//**************************************************************************
	// STOPOVER
	private synchronized void handleStopover(String sender, String receiver,boolean stopover, int score) {
		ObjectOutputStream write = clientOutputStreams.get(receiver);
		try {
			write.writeObject(new GameMessage(GameMessage.MsgType.GAME_STOPOVER, sender,"", stopover, score));
			write.flush();
		} catch(Exception ex) {
			System.out.println("S : game_stopover, �������� �۽� �� �̻� �߻�");
			ex.printStackTrace();
		}
	} // close handleIncomingMessage
	//**************************************************************************
	// �ؽ��ʿ� �ִ� ��� �����ڵ鿡�� �־��� �޽����� ������ �޼ҵ�.
	// �ݵ�� synchronized �� �޼ҵ忡���� ȣ���ϱ�� ��
	private void broadcastMessage(GameMessage message) {	
		Set<String> s = clientOutputStreams.keySet();	// ���� ��ϵ� ����ڵ��� �����ϰ� �ϳ��ϳ��� �޽��� ����
		// �׷��� ���ؼ� ���� ����� ����Ʈ�� ����
		Iterator<String> it = s.iterator();
		String user;
		while(it.hasNext()) {
			user = it.next();
			try {
				ObjectOutputStream writer = clientOutputStreams.get(user);	// ��� ����ڿ��� ��Ʈ�� ����
				writer.writeObject(message);									// �� ��Ʈ���� ���
				writer.flush();
			} catch(Exception ex) {
				System.out.println("S : broadcast, �������� �۽� �� �̻� �߻�");
				//ex.printStackTrace();
			}
		} // end while	   
	}	// end broadcastMessage
	//**************************************************************************
	private String makeClientList() {
		Set<String> s = clientOutputStreams.keySet();	// ���� ��ϵ� ����ڵ��� ����
		Iterator<String> it = s.iterator();
		String userList = "";
		while(it.hasNext()) {
			userList += it.next() + "/";					// ��Ʈ�� ����Ʈ�� �߰��ϰ� ������ ���
		} // end while
		return userList;									 
	}	// makeClientList
}
