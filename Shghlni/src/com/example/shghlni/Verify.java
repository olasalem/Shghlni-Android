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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Verify extends Activity {
	int ID,TYPE;
	EditText code;
	Button verify;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify);
		Intent intent = getIntent();
		ID = intent.getIntExtra("ID",-1);
		TYPE = intent.getIntExtra("type",-1);
		code = (EditText) findViewById(R.id.editText1);
		verify = (Button) findViewById(R.id.button1);
		verify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new verify().execute(code.getText().toString(),String.valueOf(ID));
				
			}
		});
	}
	 private class verify extends AsyncTask<String, Void ,String>  {
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
						url = new URL("http://95.85.20.131/g5/checkcode.php");
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("POST");
						connection.setDoOutput(true);
						osw = new OutputStreamWriter(connection.getOutputStream());
						osw.write("code="+params[0]+"&id="+params[1]);
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
					Log.d("Message",s);
					if(s.trim().equals("yes")){
						Intent intent;
						if(TYPE == 0){
						intent = new Intent(Verify.this,ShowProfile.class);
						}
						else {
						intent = new Intent(Verify.this,TabBoth.class);
						
						}
						startActivity(intent);
						}
					else {
						Toast.makeText(Verify.this,"Inavlid Code", Toast.LENGTH_SHORT).show();
					}
				}
	 }
}
