package com.candystudio.android.dxzs.student.activity;

import com.candystudio.android.dxzs.student.app.R;
import com.candystudio.android.dxzs.student.tool.SendStrGetIntThread;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{

	private EditText userIdEditText;
	private EditText passwordEditText;
	private EditText checkPasswordEditText;
    private EditText userNameEditText;
    private EditText classNumberEditText;
    private EditText phoneNumberEditText;
	private Button registerButton;
	private ImageView backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initLayoutMember();
	}

	private void initLayoutMember() {
		userIdEditText = (EditText) findViewById(R.id.register_userid_edittext);
		passwordEditText = (EditText) findViewById(R.id.register_password_edittext);
        checkPasswordEditText = (EditText)findViewById(R.id.register_password2_edittext);
        userNameEditText = (EditText)findViewById(R.id.register_name_edittext);
        classNumberEditText = (EditText)findViewById(R.id.register_classname_edittext);
        phoneNumberEditText = (EditText)findViewById(R.id.register_phonenumber_edittext);
		registerButton = (Button) findViewById(R.id.register_register_button);
		registerButton.setOnClickListener(this);
		backButton = (ImageView)findViewById(R.id.register_return_button);
		backButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	private boolean checkInformation(){
		if(userIdEditText.getText().toString().length() != 12){
			Toast.makeText(this,getString(R.string.register_user_id_wrong),Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(!passwordEditText.getText().toString().equals(checkPasswordEditText.getText().toString()) || passwordEditText.getText().toString().length() == 0){
			Toast.makeText(this,getString(R.string.register_check_password_fail),Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(userNameEditText.getText().toString().length() == 0){
			Toast.makeText(this,getString(R.string.register_name_cannot_null),Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(classNumberEditText.getText().toString().length() == 0){
			Toast.makeText(this,getString(R.string.register_class_cannot_null),Toast.LENGTH_SHORT).show();
			return false;
		}
		else if(phoneNumberEditText.getText().toString().length() != 11){
			Toast.makeText(this,getString(R.string.register_phone_wrong),Toast.LENGTH_SHORT).show();
			return false;
		}
		else {
			return true;
		}
	}
	
	private Boolean iRegister()
	{
		if(checkInformation()){
			String regString = getString(R.string.reg);
			regString += userIdEditText.getText().toString() + getString(R.string.f);
			regString += passwordEditText.getText().toString() + getString(R.string.f);
			regString += userNameEditText.getText().toString() + getString(R.string.f);
			regString += classNumberEditText.getText().toString()+ getString(R.string.f);
			regString += phoneNumberEditText.getText().toString()+ getString(R.string.f);
			regString += getString(R.string.role_S);
			SendStrGetIntThread commthread=null;
			try {
				commthread = new SendStrGetIntThread(regString);
				commthread.start();
				commthread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (commthread.result == 1) {
				Toast.makeText(this, "register success!", Toast.LENGTH_LONG)
						.show();
				return true;
			}
			else if (commthread.result == 2) {
				Toast.makeText(this, "register failed!",
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
		else {
			return false;
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register_register_button:
			iRegister();
			break;
		case R.id.register_return_button:
			setResult(RESULT_OK,getIntent());
			finish();
			break;
		default:
			break;
		}
	}

}
