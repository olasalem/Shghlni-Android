//FILLINREQUEST:
//==============

package com.example.shghlni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Fillin_Request extends Activity implements OnItemSelectedListener {
	protected EditText JobTitle;
	protected TextView mBodyText;
	protected String C_Area;
	protected EditText RPH;
	protected static int syear;
	protected static int smonth;
	protected static int sday;
	int ID,Qualification,AreaID ;
	protected Spinner spinnerArea,spinnerSkills;
	protected Button submit;
	protected int ans;
	protected int rid;
	protected PendingIntent pendingintent;
	protected AlarmManager manager;
	protected NotificationManager notificationManager;
	protected PendingIntent pIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fillin__request);
		Intent i= getIntent();;
		ID  = i.getIntExtra("ID",-1);
		JobTitle=(EditText)findViewById(R.id.JTitle);
		mBodyText = (TextView) findViewById(R.id.textView2);
		submit = (Button) findViewById(R.id.button2);
		spinnerArea = (Spinner)findViewById(R.id.SpinnerArea);
		RPH = (EditText)findViewById(R.id.rph);
		spinnerArea.setOnItemSelectedListener(this);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterArea = ArrayAdapter.createFromResource(this,
		        R.array.Areas, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerArea.setAdapter(adapterArea);
		spinnerSkills = (Spinner) findViewById(R.id.spinner1);
		spinnerSkills.setOnItemSelectedListener(this);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterSkills = ArrayAdapter.createFromResource(this,
		        R.array.qualifications, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterSkills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		notificationManager= (NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
		Intent intent = new Intent(this, SearchResult.class);
		pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		spinnerSkills.setAdapter(adapterSkills);
		Intent alarmIntent = new Intent(this, AlarmReceiver.class);	   
		pendingintent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Qualification = spinnerSkills.getSelectedItemPosition();
				AreaID = spinnerArea.getSelectedItemPosition();
				Log.d("Fill_in_request",String.valueOf(Qualification)+"   "+String.valueOf(AreaID));
				new FillRequest().execute(String.valueOf(ID),String.valueOf(Qualification),String.valueOf(AreaID), String.valueOf(syear)+"-"+String.valueOf(smonth+ 1)+"-"+String.valueOf(sday),RPH.getText().toString());
			}
		});
		
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		//position equals the position in the data base
		String item = (String) parent.getItemAtPosition(position);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	 private class FillRequest extends AsyncTask <String , Void , String>
		{
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//Layout Of Qualifications??
			URL url = null;
			HttpURLConnection connection = null;
			OutputStreamWriter osw = null;
			StringBuilder stringb = new StringBuilder();
			BufferedReader buff = null;
			String line;
			try {
				url = new URL("http://95.85.20.131/g5/Request.php");
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				osw = new OutputStreamWriter(connection.getOutputStream());
				osw.write("cid="+params[0]+"&desiredq="+params[1]+"&place="+params[2]+"&date="+params[3]+"&rph="+params[4]);
				osw.flush();
				buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = buff.readLine())!= null)
					stringb.append(line + "\n");
				buff.close();
				 rid =  Integer.parseInt(stringb.toString().trim());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		    }	
			return null;
			
			
		}
		@Override
		protected void onPostExecute(String result){
			//Log.d("request",result);
			Toast.makeText(Fillin_Request.this,"Your Request has been saved", Toast.LENGTH_SHORT).show();
			Intent i = new Intent(Fillin_Request.this,Search.class);
			i.putExtra("ID", ID);
			startAlarm();
			startActivity(i);
		}
		}
	
	 class DatePicker extends DialogFragment implements OnDateSetListener 
	 {
		 @Override
		 public Dialog onCreateDialog(Bundle savedInstance)
		 {
			 final Calendar calendar = Calendar.getInstance();
			 int year = calendar.get(Calendar.YEAR);
		     int month = calendar.get(Calendar.MONTH);
		     int day = calendar.get(Calendar.DAY_OF_MONTH);
		     return new DatePickerDialog(getActivity(), this, year, month, day);
		 }

		public void updateDisplay() {
		    mBodyText.setText(//this is the edit text where you want to show the selected date
		        new StringBuilder()
		            // Month is 0 based so add 1
		        .append(syear).append(":")
		        .append(smonth + 1).append(":")
		        .append(sday).append(""));


		            //.append(mMonth + 1).append("-")
		            //.append(mDay).append("-")
		            //.append(mYear).append(" "));
		}
		@Override
		public void onDateSet(android.widget.DatePicker view, int year,
				int monthOfYear, int dayOfMonth)
		{
			// TODO Auto-generated method stub
			syear = year;
			smonth = monthOfYear;
			sday = dayOfMonth;
			updateDisplay();
		}
	 }
	 public void showDatePickerDialog(View v) 
	 	{
		    DialogFragment newFragment = new DatePicker();
		    newFragment.show(getFragmentManager(), "datePicker");
		}
	 private class Check extends AsyncTask<Integer, Void, Integer>
	 {

		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generatParamsed method stub
			URL url = null;
			HttpURLConnection connection = null;
			OutputStreamWriter osw = null;
			StringBuilder stringb = new StringBuilder();
			BufferedReader buff = null;
			String line;
			try {
				url = new URL("http://95.85.20.131/g5/Check.php");
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				osw = new OutputStreamWriter(connection.getOutputStream());
				osw.write("RID="+String.valueOf(rid));
				osw.flush();
				buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = buff.readLine())!= null)
					stringb.append(line + "\n");
				buff.close();
				ans=Integer.parseInt(stringb.toString().trim());
			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		    }	
			return null;
		}
		 
	 }
	@SuppressLint("NewApi")
	public void startAlarm()
	{
		manager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
		int interval = 14400000;
		manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingintent);
		Check check= new Check();
		check.execute();
		Notification n=null;
		switch(ans)
		{
		case 0:
			cancelAlarm();
			n = new Notification.Builder(this).setContentTitle("Sha3'lny")
					.setContentText("We are very sorry we couldn't find anything that matches your request")
					.setContentIntent(pIntent).setAutoCancel(true).build();
			break;
		case 2:
			cancelAlarm();
			n = new Notification.Builder(this).setContentTitle("Sha3'lny")
					.setContentText("We found what you were looking for..Come and Check it out")
					.setContentIntent(pIntent)
					.addAction(R.drawable.slogo,"Check It Out",pIntent)
					.setAutoCancel(true).build();
			break;
		}
		notificationManager.notify(0,n);
	}
	public void cancelAlarm()
	{
			manager.cancel(pendingintent);
	}
		 
}

