package com.example.shghlni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
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
import android.widget.Toast;

public class Information extends Activity implements OnItemSelectedListener { 
	EditText Phone1;
	EditText Phone2;
	Button Proceed;
	String RatePerHour,PHONE1,PHONE2;
	int ID,TYPE,AreaID;
	Spinner spinnerArea;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		Intent extras = getIntent();
		ID = extras.getIntExtra("ID", -1);
		TYPE = extras.getIntExtra("type", -1);
		RatePerHour = extras.getStringExtra("rate");
		Phone1 = (EditText) findViewById(R.id.editText1);
		Phone2 = (EditText) findViewById(R.id.editText2);
		Proceed = (Button) findViewById(R.id.button1);
		Proceed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if( isEmpty(Phone1) || isEmpty(Phone2))
					Toast.makeText(Information.this, "Missing Feild", Toast.LENGTH_SHORT).show();
				else{
					PHONE1 = Phone1.getText().toString();
					PHONE2 = Phone2.getText().toString();
					AreaID = spinnerArea.getSelectedItemPosition();
					new InfoTask().execute(String.valueOf(ID),String.valueOf(AreaID),PHONE1,PHONE2,RatePerHour);
					//new asynch task(info)
					//new asynch task(qualifications)
					//on post execute
					/*if(TYPE==0){//freelancer
						Intent intent= new Intent(Information.this,ShowProfile.class);
						intent.putExtra("ID", ID);
						startActivity(intent);
					}
					else if(TYPE==2){//both
						Intent intent= new Intent(Information.this,TabBoth.class);
						intent.putExtra("ID", ID);
						startActivity(intent);
					
					}*/
				}
			}
		});
		/*Spinner spinnerSkills = (Spinner) findViewById(R.id.spinner1);
		spinnerSkills.setOnItemSelectedListener(this);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterSkills = ArrayAdapter.createFromResource(this,
		        R.array.qualifications, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterSkills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerSkills.setAdapter(adapterSkills);*/
		spinnerArea = (Spinner) findViewById(R.id.spinner2);
		spinnerArea.setOnItemSelectedListener(this);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterArea = ArrayAdapter.createFromResource(this,
		        R.array.Areas, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerArea.setAdapter(adapterArea);
		
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
	private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
	 private class InfoTask extends AsyncTask<String, Void ,String>  {
		 @Override
	 	 	protected void onPreExecute(){
		 		super.onPreExecute();
		 	}
	    	@Override
	    	protected String doInBackground(String... params) {
	    			URL url = null;
	    			HttpURLConnection connection = null;
					OutputStreamWriter osw = null;
					StringBuilder stringb = new StringBuilder();
					BufferedReader buff = null;
					String line;
					try {
						url = new URL("http://95.85.20.131/g5/GetInfo.php");
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("POST");
						connection.setDoOutput(true);
						osw = new OutputStreamWriter(connection.getOutputStream());
						osw.write("ID=" + params[0]+"&Area=" + params[1]+"&ph1=" + params[2]+"&ph2=" + params[3]+"&RPH=" + params[4]);
						osw.flush();
						buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
						while ((line = buff.readLine())!= null)
							stringb.append(line + "\n");
						buff.close();
						String s =  stringb.toString();
						return s;
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
				protected void onPostExecute(String s){
				new SMS().execute(PHONE1,String.valueOf(ID));
				}
	 }
	 private class SMS extends AsyncTask<String, Void ,String>  {
		 @Override
	 	 	protected void onPreExecute(){
		 		super.onPreExecute();
		 	}
	    	@Override
	    	protected String doInBackground(String... params) {
	    		URL url = null;
    			HttpURLConnection connection = null;
				OutputStreamWriter osw = null;
				StringBuilder stringb = new StringBuilder();
				BufferedReader buff = null;
				String line;
				try {
					url = new URL("http://10.0.2.2/sms.php");
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoOutput(true);
					osw = new OutputStreamWriter(connection.getOutputStream());
					osw.write("phnum=" + params[0]+"&id="+params[1]);
					osw.flush();
					buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					while ((line = buff.readLine())!= null)
						stringb.append(line + "\n");
					buff.close();
					String s =  stringb.toString();
					return s;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();						
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			    }	
				return null;
	    	}
	     protected void onPostExecute(String s){
	    	 	Log.d("SMSRESPONSE",s);
	    	 	Intent intent = new Intent(Information.this,Verify.class);
				intent.putExtra("ID", ID);
				intent.putExtra("type", TYPE);
				startActivity(intent);
				/*
				
				 */
	    	
		}
	 }
	
	
}
