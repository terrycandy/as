package com.candystudio.android.dxzs.student.activity;


import com.candystudio.android.dxzs.student.app.R;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class ConfigActivity extends Activity implements OnClickListener {

	private Button saveButton;
	private EditText ipEditText;
	private EditText portEditText;
	private ImageView exitButton;
	private String Tag = "Cinton_Config";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		initLayoutMember();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
		return true;
	}

	private void initLayoutMember() {
		ipEditText = (EditText) findViewById(R.id.config_ip_edittext);
		portEditText = (EditText) findViewById(R.id.config_port_edittext);
		saveButton = (Button) findViewById(R.id.config_save_button);
		saveButton.setOnClickListener(this);
		exitButton = (ImageView) findViewById(R.id.config_back);
		exitButton.setOnClickListener(this);
	}

	private boolean saveConfig() {
		if(ipEditText.getText().toString().length() == 0 || portEditText.getText().toString().length() == 0){
            Toast.makeText(this,getString(R.string.config_write_server_ip_or_port),Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
			SharedPreferences sp = getSharedPreferences("server", MODE_PRIVATE);
			Editor ed = sp.edit();
			ed.putString("ip", ipEditText.getText().toString());
			ed.putString("port", portEditText.getText().toString());
			Log.d(Tag, "save ip:port" + ipEditText.getText().toString() + ":" + portEditText.getText().toString());
			ed.commit();
			return true;
        }
		
	}

	private void go2Login() {
		Intent intent = new Intent(ConfigActivity.this, LoginActivity.class);
		// intent.putExtra(name, value)
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.config_back:
			finish();
			break;
		case R.id.config_save_button:
			if(saveConfig()){
				go2Login();
				break;
			}
			else {
				break;
			}
		default:
			break;
		}
	}

}
