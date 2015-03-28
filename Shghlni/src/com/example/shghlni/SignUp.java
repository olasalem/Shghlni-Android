package com.example.shghlni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity{
	String NAME,USERNAME,PASSWORD;
	EditText name;
	EditText username ;
	EditText password ;
	Button signUp;
	CheckBox freelancerCheck;
	CheckBox clientCheck;
	Intent intent;
	Boolean C, F;
	public static final String MyPREFERENCES = "MyPref" ;
	public static SharedPreferences pref ; 
	public static Editor editor; 
	int ID,type,Action ; //0=freelancer, 1=client, 2= both
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		C = F = false;
		ActionBar ab=getActionBar() ;
		ab.hide();
		setContentView(R.layout.activity_sign_up);
		//pref = getApplicationContext().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE); // 0 - for private mode
		pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		editor = pref.edit();
		name =(EditText) findViewById(R.id.editText3);
		name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	name.setText("");
            }
            });
		username =(EditText) findViewById(R.id.editText1);
		username.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	username.setText("");
            }
            });
		password =(EditText) findViewById(R.id.editText2);
		password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	password.setText("");
            }
            });
		freelancerCheck =(CheckBox)findViewById(R.id.checkBox2);
		clientCheck = (CheckBox) findViewById(R.id.checkBox1);
		signUp = (Button) findViewById(R.id.SIGNUP);
		Intent i=new Intent();
		Action = i.getIntExtra("ACTION", -1);
		Log.d("ACTION",String.valueOf(Action));
		if (Action == 1){
			//UPDATING
			ID = i.getIntExtra("ID", -1);
			signUp.setText("Update");
			new Update().execute(String.valueOf(ID));
		}
		else{
		signUp.setText("Sign Up");}
		signUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				NAME = name.getText().toString();
				USERNAME = username.getText().toString();
				PASSWORD = password.getText().toString();
				C = clientCheck.isChecked();
				F = freelancerCheck.isChecked();
				if( C && !F ) type = 1;
				else if (F&&!C) type = 0;
				else if (F && C) type = 2;
				else if (!C && !F) type = -1;
				if(type==-1 || isEmpty(name) ||isEmpty(username) || isEmpty(password)){
					Toast.makeText(SignUp.this, "Missing Field", Toast.LENGTH_SHORT).show();
				}
				else{
					if(Action == 1){
						new Change().execute(String.valueOf(ID),NAME,USERNAME,PASSWORD,String.valueOf(type));	
					}
					else {new Validate().execute(USERNAME);}
						
						
				}
			}
		});
	}
	private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
	private class SignUpTask extends AsyncTask<String, Void , String>  {
		 @Override
	 	 	protected void onPreExecute(){
		 		super.onPreExecute();
		 	}
	    	@Override
	    	protected String doInBackground(String... params) {
	    			URL url = null;
					try {
						url = new URL("http://95.85.20.131/g5/register.php");
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					HttpURLConnection connection = null;
					try {
						connection = (HttpURLConnection) url.openConnection();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					try {
						connection.setRequestMethod("POST");
					} catch (ProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					connection.setDoOutput(true);
					OutputStreamWriter osw = null;
					try {
						osw = new OutputStreamWriter(connection.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						osw.write("Name=" + params[0]+"&UserName=" + params[1]+"&Password=" + params[2]+"&Type=" + params[3]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						osw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					StringBuilder stringb = new StringBuilder();
					BufferedReader buff = null;
					try {
						buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String line;
					try {
						while ((line = buff.readLine())!= null)
						stringb.append(line + "\n");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						buff.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return stringb.toString();
	        	}
				@Override
		    	protected void onPostExecute(String s) { //the value of result is s, this function is executed automatically after doinbackground
		    	//do something with string s, (read json data, parse it, whatever, continue rest of activities logic flow here)
					//Toast.makeText(SignUp.this, s , Toast.LENGTH_SHORT).show();
					//store ID and type in shared prefrences
					editor.putInt("IDSH3`LNI",Integer.parseInt(s.trim()));
					editor.putInt("TYPESH3`LNI", type);
					editor.commit();
					int x= pref.getInt("IDSH3`LNI", -1);
					//Toast.makeText(SignUp.this, String.valueOf(x) , Toast.LENGTH_SHORT).show();
					//Log.d("ID",s);
					Log.d("IDRegister",String.valueOf(x));
					if(type==0 || type==2){
						Intent intent = new Intent (SignUp.this, Qualifications.class);
						intent.putExtra("ID",x);// Integer.parseInt(s.trim()));
						intent.putExtra("type", type);
						startActivity(intent);	
					}
					else if (type == 1){
						Intent intent = new Intent (SignUp.this, Search.class);
						intent.putExtra("ID",x);// Integer.parseInt(s.trim()));
						startActivity(intent);
					}
					}
				}
    	 
	 class Validate extends AsyncTask<String, Void , Boolean>  {
		 public Validate() {
			// TODO Auto-generated constructor stub
		}
		@Override
	 	 	protected void onPreExecute(){
		 		super.onPreExecute();
		 	}
	    	@Override
	    	protected Boolean doInBackground(String... params) {
	    			URL url = null;
					try {
						url = new URL("http://95.85.20.131/g5/V.php");
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					HttpURLConnection connection = null;
					try {
						connection = (HttpURLConnection) url.openConnection();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					try {
						connection.setRequestMethod("POST");
					} catch (ProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					connection.setDoOutput(true);
					OutputStreamWriter osw = null;
					try {
						osw = new OutputStreamWriter(connection.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						osw.write("UserName=" + params[0]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						osw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					StringBuilder stringb = new StringBuilder();
					BufferedReader buff = null;
					try {
						buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String line;
					try {
						while ((line = buff.readLine())!= null)
						stringb.append(line);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						buff.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String s = stringb.toString();
					Log.d("result",s.trim());
					if(s.trim().equals("Yes")) 
						return true;
					else if (s.trim().equals("NO"))
						return false;
					return false;
	        	}
	    	
				@Override
		    	protected void onPostExecute(Boolean result) { //the value of result is s, this function is executed automatically after doinbackground
					if(result)
						new SignUpTask().execute(SignUp.this.NAME, SignUp.this.USERNAME,SignUp.this.PASSWORD,String.valueOf(SignUp.this.type));
					else
						Toast.makeText(SignUp.this, "User Name Taken", Toast.LENGTH_SHORT).show();
				}
	 }
	 private class Update extends AsyncTask<String, Void ,String>  {
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
					url = new URL("http://95.85.20.131/g5/SearchID.php");
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoOutput(true);
					osw = new OutputStreamWriter(connection.getOutputStream());
					osw.write("ID=" + params[0]);
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
		try {
			JSONArray jArary = new JSONArray(s);
			JSONObject j = jArary.getJSONObject(0);
			name.setText(j.getString("Name"));
			username.setText(j.getString("UserNmae"));
			password.setText(j.getString("Password"));
			freelancerCheck.setChecked(true); 
			//PROCEED DIFFRENT LAYOUT				
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();					
			}
	    	
	    	
		}
	 }
	 private class Change extends AsyncTask<String, Void ,String>  {
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
					url = new URL("http://95.85.20.131/g5/Change.php");
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoOutput(true);
					osw = new OutputStreamWriter(connection.getOutputStream());
					osw.write("ID=" + params[0]+"&Name=" + params[1]+"&UserName=" + params[2]+"&Password=" + params[3]+"&Type=" + params[4]);
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
	    		editor.putInt("TYPESH3`LNI", type);
				editor.commit();
				int x= pref.getInt("IDSH3`LNI", -1);
				//Toast.makeText(SignUp.this, String.valueOf(x) , Toast.LENGTH_SHORT).show();
				//Log.d("ID",s);
				Log.d("IDIntentudgu",String.valueOf(x));
				if(type==0 || type==2){
					Intent intent = new Intent (SignUp.this, Qualifications.class);
					intent.putExtra("ID", x);//Integer.parseInt(s.trim()));
					intent.putExtra("type", type);
					startActivity(intent);	
				}
				else if (type == 1){
					Intent intent = new Intent (SignUp.this, Search.class);
					intent.putExtra("ID", x);//Integer.parseInt(s.trim()));
					startActivity(intent);
				}
	    	
		}
	 }
	 
}
