//package com.candystudio.android.dxzs.student.tool;
//
//import org.jivesoftware.smack.Chat;
//import org.jivesoftware.smack.ConnectionConfiguration;
//import org.jivesoftware.smack.MessageListener;
//import org.jivesoftware.smack.XMPPConnection;
//import org.jivesoftware.smack.XMPPException;
//import org.jivesoftware.smack.packet.Message;
//import org.jivesoftware.smack.packet.Presence;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
////import android.content.Context;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.candystudio.android.dxzs.student.app.MyApplication;
//
//public class CommunicationTool {
//
//	private static String ipString;
//	private static Integer portInteger;
//	private static ConnectionConfiguration connCfg;
//	private static XMPPConnection conn = null;
//
//	public synchronized static Boolean getConnection() {
//		if (conn == null || !conn.isConnected()) {
//			return openConnection();
//		} else {
//			return true;
//		}
//	}
//
//	private synchronized static Boolean openConnection() {
//
//		Context context = MyApplication.getInstance();
//
//		SharedPreferences sp = context.getSharedPreferences("server",
//				Context.MODE_PRIVATE);
//
//		ipString = sp.getString("ip", "none");
//		portInteger = Integer.valueOf(sp.getString("port", "none"));
//
//		try {
//			// 连接服务器参数
//			connCfg = new ConnectionConfiguration(ipString, portInteger);
//			// 上线
//			connCfg.setSendPresence(true);
//			// 重连许可
//			connCfg.setReconnectionAllowed(true);
//			conn = new XMPPConnection(connCfg);
//
//			// 连接服务器
//			conn.connect();
//			return true;
//
//		} catch (Exception e) {
//			// Toast.makeText(MyApplication.getContext(),
//			// "无法连接到服务器:" + e.getMessage(), 1000).show();
//			return false;
//		}
//	}
//
//	// 用户登录
//	public static synchronized void Login(String UserName, String Password) {
//		if (getConnection()) {
//			try {
//				// SASLAuthentication.supportSASLMechanism("PLAIN", 0);
//				conn.login(UserName, Password);
//				Presence presence = new Presence(Presence.Type.available);
//				conn.sendPacket(presence);
//				String userString = conn.getUser();
//				//
//				// Toast.makeText(MyApplication.getInstance(), "登录成功!", 1000)
//				// .show();
//
//			} catch (Exception e) {
//				// Toast.makeText(MyApplication.getInstance(),
//				// "登录失败:" + e.getMessage(), 1000).show();
//				conn.disconnect();
//			}
//
//		}
//
//	}
//
//	// 新用户注册
//	public static synchronized void Register(String UserName, String Password) {
//
//		if (getConnection()) {
//
//			try {
//				conn.getAccountManager().createAccount(UserName, Password);
//				// Toast.makeText(MyApplication.getContext(), "注册成功!", 1000)
//				// .show();
//				Log.d(null, "register success");
//				conn.disconnect();
//			} catch (XMPPException e) {
//				// Toast.makeText(MyApplication.getContext(),
//				// "注册失败:" + e.getMessage(), 1000).show();
//				conn.disconnect();
//			}
//		}
//	}
//
//	// 发送消息方法
//	public static void SendMessage(String ToWho, String MSG,
//			MessageListener msgListener) {
//
//		if (getConnection()) {
//
//			Chat chat = conn.getChatManager().createChat(ToWho, msgListener);
//			try {
//				chat.sendMessage(MSG);
//			} catch (XMPPException e) {
//				// Toast.makeText(MyApplication.getContext(),
//				// "注册失败:" + e.getMessage(), 1000).show();
//
//			} finally {
////				conn.disconnect();
//			}
//		}
//	}
//
//}
