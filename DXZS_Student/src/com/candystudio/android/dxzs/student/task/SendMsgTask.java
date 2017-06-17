//package com.candystudio.android.dxzs.student.task;
//
//import org.jivesoftware.smack.Chat;
//import org.jivesoftware.smack.MessageListener;
//import org.jivesoftware.smack.packet.Message;
//
//import com.candystudio.android.dxzs.student.model.StudentInExam;
//import com.candystudio.android.dxzs.student.tool.CommunicationTool;
//
//import android.os.AsyncTask;
//
//public class SendMsgTask extends AsyncTask<StudentInExam, Integer, Boolean> {
//
//	@Override
//	protected Boolean doInBackground(StudentInExam... params) {
//		// TODO Auto-generated method stub
//		sendamsg(params[0]);
//		return null;
//	}
//	
//	private void sendamsg(StudentInExam student)
//	{
//		
//		String ToWho="chengguo@work-pc/Smack";
//		String MSG=student.ToMsg();
//		MessageListener msgListener = new MessageListener() {
//			@Override
//			public void processMessage(Chat chat, Message message) {
////				LogUtil.i("zzh", message.toXML());
//			}
//		};
//		CommunicationTool.SendMessage(ToWho, MSG, msgListener);
//	}
//	
//
//}
