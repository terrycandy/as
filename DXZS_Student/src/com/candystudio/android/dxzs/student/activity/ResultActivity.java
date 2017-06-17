package com.candystudio.android.dxzs.student.activity;

import com.candystudio.android.dxzs.student.app.R;
import com.candystudio.android.dxzs.student.tool.SendStrGetIntThread;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class ResultActivity extends Activity implements View.OnClickListener{

	private TextView classNameTextView;
    private TextView nameTextView;
    private TextView mapNameTextView;
    private TextView teacherTextView;
    private TextView timeTextView;
    private TextView pointTextView;
    private EditText msgEditText;
    private Button sendButton;
    private Button overButton;
    private ImageView exitButton;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}
	
	protected void initLayoutMember(){
        classNameTextView = (TextView)findViewById(R.id.result_classname_textview);
        nameTextView = (TextView)findViewById(R.id.result_name_textview);
        mapNameTextView = (TextView)findViewById(R.id.result_mapname_textview);
        teacherTextView = (TextView)findViewById(R.id.result_teachername_textview);
        timeTextView = (TextView)findViewById(R.id.result_costtime_textview);
        pointTextView = (TextView)findViewById(R.id.result_point_textview);
        msgEditText = (EditText)findViewById(R.id.result_msg_edittext);
        exitButton = (ImageView)findViewById(R.id.result_back);
        sendButton = (Button)findViewById(R.id.result_send_button);
        overButton = (Button)findViewById(R.id.result_over_button);
        sendButton.setOnClickListener(this);
        overButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }
	
	private void setData(String resString){
		
	}
	
	private void getResult(){
		SharedPreferences sp = getSharedPreferences("currentUser", MODE_PRIVATE);
		String userId = sp.getString("username", null);
		SendStrGetIntThread commthread = null;
        try {
			commthread = new SendStrGetIntThread("res-" + userId);
			commthread.start();
			commthread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (commthread.result == 1) {
			Toast.makeText(this, "Report success!", Toast.LENGTH_LONG)
					.show();
			setData(commthread.resObj);
		} else if (commthread.result == 2) {
			Toast.makeText(this, "Report failed!", Toast.LENGTH_LONG)
					.show();
		} else if (commthread.result == 3) {
			Toast.makeText(this, "Unknown host!", Toast.LENGTH_LONG).show();
		} else if (commthread.result == 4) {
			Toast.makeText(this, "IO problem!", Toast.LENGTH_LONG).show();
		}
	}
	
	protected void sendMsg(){
	        String msg = msgEditText.getText().toString();
	        String sugString = "sug-"+"teacherId"+";"+"userID"+";"+msg;
	        SendStrGetIntThread commthread = null;
	        try {
				commthread = new SendStrGetIntThread(sugString);
				commthread.start();
				commthread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (commthread.result == 1) {
				Toast.makeText(this, "Report success!", Toast.LENGTH_LONG)
						.show();
			} else if (commthread.result == 2) {
				Toast.makeText(this, "Report failed!", Toast.LENGTH_LONG)
						.show();
			} else if (commthread.result == 3) {
				Toast.makeText(this, "Unknown host!", Toast.LENGTH_LONG).show();
			} else if (commthread.result == 4) {
				Toast.makeText(this, "IO problem!", Toast.LENGTH_LONG).show();
			}
	    }
	
	 @Override
	    public void onClick(View v) {
	        switch (v.getId()){
	            case R.id.result_send_button:
	                sendMsg();
	                break;
	            case R.id.result_over_button:
	            case R.id.result_back:
	                finish();
	                break;
	            default:
	                break;
	        }
	    }

}
