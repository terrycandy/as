package com.candystudio.android.dxzs.student.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.candystudio.android.dxzs.student.app.R;
import com.candystudio.android.dxzs.student.task.LoginThread;
import com.candystudio.android.dxzs.student.tool.SendStrGetIntThread;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;
	private Button registerButton;
	private ImageView exitButton;
	private String Tag = "Cinton_Login";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initLayoutMember();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void initLayoutMember() {
		usernameEditText = (EditText) findViewById(R.id.login_username_edittext);
		passwordEditText = (EditText) findViewById(R.id.login_password_edittext);
		loginButton = (Button) findViewById(R.id.login_login_button);
		registerButton = (Button) findViewById(R.id.login_register_button);
		exitButton = (ImageView)findViewById(R.id.login_back);
		loginButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		exitButton.setOnClickListener(this);
	}


	private boolean trylogin() {
		String userid = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		SendStrGetIntThread commthread=null;
		if (userid == "" || password == "") {
			Toast.makeText(this, "User ID or password is empty!",
					Toast.LENGTH_LONG).show();
			return false;
		} else {
			String reportString = "gin-";
			reportString+=userid;
			reportString+=";";
			reportString+=password;
			Log.d(Tag, reportString);
			try {
				commthread = new SendStrGetIntThread(reportString);
				commthread.start();
				commthread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (commthread.result == 1) {
				Toast.makeText(this, "Login success!", Toast.LENGTH_LONG)
						.show();
				SharedPreferences sp = getSharedPreferences("currentUser", MODE_PRIVATE);
				Editor ed = sp.edit();
				ed.putString("username", userid);
				ed.commit();
				return true;
			}
			else if (commthread.result == 2) {
				Toast.makeText(this, "Login failed!",
						Toast.LENGTH_LONG).show();
				return false;
			} else if (commthread.result == 3) {
				Toast.makeText(this, "Unknown host!",
						Toast.LENGTH_LONG).show();
				return false;
			} else if (commthread.result == 4) {
				Toast.makeText(this, "IO problem!", Toast.LENGTH_LONG)
						.show();
				return false;
			} else
				return false;

		}

	}

	private void dologin() {

		if (trylogin()) {
			go2Prepare();
		}

	}

	private void go2Prepare() {
		Intent intent = new Intent(LoginActivity.this, PrepareActivity.class);
		// intent.putExtra(name, value)
		startActivity(intent);
		finish();
	}

	private void go2Register() {
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.login_login_button:
				dologin();
				break;
			case R.id.login_register_button:
				go2Register();
				break;
			case R.id.login_back:
	//			// 测试学员发位置
	//			SendMsgTask task = new SendMsgTask();
	//			StudentInExam studentInExam = new StudentInExam("lisi", 113.123,
	//					28.345);
	//			task.execute(studentInExam);
				finish();
				break;
			default:
				break;
		}
	}

}
