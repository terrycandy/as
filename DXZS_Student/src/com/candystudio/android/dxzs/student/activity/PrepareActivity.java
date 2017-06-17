package com.candystudio.android.dxzs.student.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.LoginFilter.UsernameFilterGeneric;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.candystudio.android.dxzs.student.app.R;
import com.candystudio.android.dxzs.student.tool.SendStrGetIntThread;

public class PrepareActivity extends Activity implements OnClickListener {

	private Spinner teacherSpinner;
	private Spinner mapSpinner;
	private Spinner regionSpinner;
	private Button startButton;
	private Button getInfoButton;
	private ImageView exitButton;
	private TextView classTextView;
	private TextView nameTextView;
	private EditText rnumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prepare);
		initLayoutMember();

	}

	private void initLayoutMember() {
		classTextView = (TextView)findViewById(R.id.prepare_classname_textview);
		nameTextView = (TextView)findViewById(R.id.prepare_name_textview);
		rnumber = (EditText)findViewById(R.id.prepare_routenumber_edittext);
		startButton = (Button) findViewById(R.id.prepare_start_button);
		startButton.setOnClickListener(this);
		getInfoButton = (Button)findViewById(R.id.prepare_get_button);
		getInfoButton.setOnClickListener(this);
		exitButton = (ImageView)findViewById(R.id.prepare_back);
		exitButton.setOnClickListener(this);
		initSpinners();
	}

	private void initSpinners() {
		teacherSpinner = (Spinner) findViewById(R.id.prepare_teacher_spinner);
		mapSpinner = (Spinner) findViewById(R.id.prepare_map_spinner);
		regionSpinner = (Spinner) findViewById(R.id.prepare_region_spinner);
	}

	private void go2Exam() {
		Intent intent = new Intent(PrepareActivity.this,
				ExaminationActivity.class);
		// intent.putExtra(name, value)
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prepare, menu);
		return true;
	}
	
	private void setData(String infString){
		classTextView.setText(infString);
		nameTextView.setText(infString);
		rnumber.setText(infString);
//		teacherSpinner;
//		mapSpinner;
//		regionSpinner;
	}
	
	private void getInformation(){
		SharedPreferences sp = getSharedPreferences("currentUser", MODE_PRIVATE);
		String username = sp.getString("username", null);
		String infString = "inf-" + username;
		SendStrGetIntThread commthread=null;
		try {
			commthread = new SendStrGetIntThread(infString);
			commthread.start();
			commthread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (commthread.result == 1) {
			Toast.makeText(this, "register success!", Toast.LENGTH_LONG)
					.show();
			setData(commthread.resObj);
		}
		else if (commthread.result == 2) {
			Toast.makeText(this, "register failed!",
					Toast.LENGTH_LONG).show();
		} else if (commthread.result == 3) {
			Toast.makeText(this, "Unknown host!",
					Toast.LENGTH_LONG).show();
		} else if (commthread.result == 4) {
			Toast.makeText(this, "IO problem!", Toast.LENGTH_LONG)
					.show();
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.prepare_start_button:
				go2Exam();
				break;
			case R.id.prepare_get_button:
				getInformation();
				break;
			case R.id.prepare_back:
				finish();
				break;
			default:
				break;
		}
	}
//
}
