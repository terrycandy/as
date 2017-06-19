package com.candystudio.android.dxzs.student.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xbill.DNS.tests.primary;

import com.candystudio.android.dxzs.student.app.R;
import com.candystudio.android.dxzs.student.tool.SendStrGetIntThread;
import com.candystudio.android.dxzs.student.view.ViewHolderE;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ExaminationActivity extends Activity implements OnClickListener {
	//列表视图适配器
	public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context){
            Log.d("Debug-Exam","开始实例化适配器");
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            Log.d("Debug-Exam","开始获取数据大小："+String.valueOf(0));
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d("Debug-Exam","开始准备构建每一个项目");
            ViewHolderE holder = null;
            if (convertView == null) {
                Log.d("Debug-Exam","开始构建每一个子项目");
                holder= new ViewHolderE();
                convertView = mInflater.inflate(R.layout.examlistview, null);
                holder.markTextView = (TextView) convertView.findViewById(R.id.exma_list_mark_textview);
                holder.pointXTextView = (TextView)convertView.findViewById(R.id.exam_coordinate_X_textview);
                holder.pointYTextView = (TextView)convertView.findViewById(R.id.exam_coordinate_Y_textview);
				holder.answerEditText = (EditText)convertView.findViewById(R.id.exma_list_answer_edittext);
				holder.reportButton = (Button)convertView.findViewById(R.id.exma_list_report_button);
                holder.reportButton.setTag(position);
                convertView.setTag(holder);
            }else {
                Log.d("Debug-Exam","项目已经存在");
                holder = (ViewHolderE)convertView.getTag();
            }
            holder.markTextView.setText((String)list.get(position).get("Mark"));
            String poi = (String)list.get(position).get("Point");
            String[] tmp = poi.split(":");
            holder.pointXTextView.setText(tmp[0]);
            holder.pointYTextView.setText(tmp[1]);
			holder.reportButton.setText((String)list.get(position).get("Button"));
			holder.answerEditText.setText("");
            pointId.add((String)list.get(position).get("PointId"));

			holder.reportButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
                    ListView list = (ListView)findViewById(R.id.exam_point_listview);
                    LinearLayout layout = (LinearLayout)list.getChildAt(position);
                    EditText et = (EditText) layout.findViewById(R.id.exma_list_answer_edittext);
                    String mes = et.getText().toString();
//                    Toast.makeText(ExaminationActivity.this,"the input information : " + pointId.get(position) + " : "+mes,Toast.LENGTH_SHORT).show();
//                    sendAnswer(mes, pointId.get(position));
                    report("", userId,  pointId.get(position), mes);
				}
			});
            return convertView;
        }
    }
	
	//private Button reportButton;
	private Button startButton;
    private ImageView exitButton;
    private Button callHelpButton;
	
	private TextView classTextView;
    private TextView nameTextView;
    private TextView mapNameTextView;
    private TextView teacherNameTextView;
    
    private ListView listView;
    
    private String userId;
    
	private LocationManager locationManager;

	private Location curLocation;

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	public ArrayList<String> pointId = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_examination);
		initLayoutMember();
		initLBS();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	private void initData(){
        Map<String, Object> map ;
        map = new HashMap<String, Object>();
        map.put("PointId","1");
        map.put("Mark", "ABC");
        map.put("Point","1234" + ":" + "4321");
        list.add(map);
	}
	private void initLayoutMember() {

//		reportButton = (Button) findViewById(R.id.exam_report_button);
//		reportButton.setOnClickListener(this);
		classTextView = (TextView)findViewById(R.id.exam_classname_textview);
        //classTextView.setText(student.getUnit());
        nameTextView = (TextView)findViewById(R.id.exam_name_textview);
       // nameTextView.setText(student.getUserName());
        mapNameTextView = (TextView)findViewById(R.id.exam_mapname_textview);
       // mapNameTextView.setText(exam.getMap());
        teacherNameTextView = (TextView)findViewById(R.id.exam_teachername_textview);
        //teacherNameTextView.setText(exam.getTeacher());
        
        listView = (ListView)findViewById(R.id.exam_point_listview);
        MyAdapter myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);
        startButton = (Button)findViewById(R.id.exam_return_button);
        exitButton = (ImageView) findViewById(R.id.exam_back);
        callHelpButton = (Button)findViewById(R.id.exam_call_help_button);
        startButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        callHelpButton.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("currentUser", MODE_PRIVATE);
		userId = sp.getString("username", null);

	}

	protected final LocationListener locationListenner = new LocationListener() {

		// 当位置发生变化时，输出位置信息
		public void onLocationChanged(Location location) {
			curLocation = location;

		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	};

	private void initLBS() {

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		if (locationListenner != null) {
			curLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		}

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 1, locationListenner);
	}

	private boolean report(String teacherId, String userID, String pointId, String ansower) {
		SendStrGetIntThread commthread = null;

		if (curLocation == null) {
			Toast.makeText(this, "Location is null!", Toast.LENGTH_LONG).show();
			return false;
		} else {

			// rpt-receiverid;senderid;pointid;longitude;latitude;message
			// rpt-081539;201409060391;1;112.991938;28.23844;KDDXABC
			String reportString = "rpt-";
			reportString += teacherId;
			reportString += ";";
			reportString += userID;
			reportString += ";";
			reportString += pointId;
			reportString += ";";
			reportString += curLocation.getLongitude();
			reportString += ";";
			reportString += curLocation.getLatitude();
			reportString += ";";
			reportString += ansower;

			try {
				commthread = new SendStrGetIntThread(reportString);
				commthread.start();
				commthread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (commthread.result == 1) {
				Toast.makeText(this, "Report success!", Toast.LENGTH_LONG)
						.show();
				return true;
			} else if (commthread.result == 2) {
				Toast.makeText(this, "Report failed!", Toast.LENGTH_LONG)
						.show();
				return false;
			} else if (commthread.result == 3) {
				Toast.makeText(this, "Unknown host!", Toast.LENGTH_LONG).show();
				return false;
			} else if (commthread.result == 4) {
				Toast.makeText(this, "IO problem!", Toast.LENGTH_LONG).show();
				return false;
			} else
				return false;

		}
	}

	protected void callHelp(String userId){
		SendStrGetIntThread commthread = null;

		if (curLocation == null) {
			Toast.makeText(this, "Location is null!", Toast.LENGTH_LONG).show();
		} else {

			// rpt-receiverid;senderid;pointid;longitude;latitude;message
			// rpt-081539;201409060391;1;112.991938;28.23844;KDDXABC
			String helString = "hel-";
			helString += userId;
			helString += ";";
			helString += curLocation.getLongitude();
			helString += ";";
			helString += curLocation.getLatitude();

			try {
				commthread = new SendStrGetIntThread(helString);
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
    }
    protected void finishExam(){
    	SendStrGetIntThread commthread = null;
		if (curLocation == null) {
			Toast.makeText(this, "Location is null!", Toast.LENGTH_LONG).show();
		} else {

			// rpt-receiverid;senderid;pointid;longitude;latitude;message
			// rpt-081539;201409060391;1;112.991938;28.23844;KDDXABC
			String finString = "fin-";
			finString += userId;
	
			try {
				commthread = new SendStrGetIntThread(finString);
				commthread.start();
				commthread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (commthread.result == 1) {
				Toast.makeText(this, "Report success!", Toast.LENGTH_LONG)
						.show();
				Intent intent = new Intent(ExaminationActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				finish();
			} else if (commthread.result == 2) {
				Toast.makeText(this, "Report failed!", Toast.LENGTH_LONG)
						.show();
			} else if (commthread.result == 3) {
				Toast.makeText(this, "Unknown host!", Toast.LENGTH_LONG).show();
			} else if (commthread.result == 4) {
				Toast.makeText(this, "IO problem!", Toast.LENGTH_LONG).show();
			} 

		}
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	        case R.id.exam_return_button:
	            finishExam();
	            break;
	        case R.id.exam_call_help_button:
	            callHelp(userId);
	            break;
	        case R.id.exam_back:
	            finish();
	            break;
	        default:
	            break;
		}
	}

}
