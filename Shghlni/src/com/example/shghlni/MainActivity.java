package com.example.shghlni;
//ARABIC AND ENGLISH

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String MyPREFERENCES = "MyPref" ;
	public static SharedPreferences pref ; 
	public static Editor editor;
	String NAME,PASSWORD;
	EditText username ;
	EditText password ;
	Button signIn;
	Button signUp;
	Intent intent;
	public int type,x,Action;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		editor = pref.edit();
		Intent i=new Intent();
		Action = i.getIntExtra("ACTION", -1);
		if(Action == 0){
			//editor.putBoolean("SIGNEDIN", false);
			//editor.putInt("IDSH3`LNI", -1);
			editor.clear();
			editor.commit();
		}
		x= pref.getInt("IDSH3`LNI", -1);
		Log.d("IDSH3`LNI",String.valueOf(x));
		type= pref.getInt("TYPESH3`LNI", -1);
		Boolean SIGNED_IN = pref.getBoolean("SIGNEDIN", false);
		Log.d("SIGNEDIN",String.valueOf(SIGNED_IN));
		//if(SIGNED_IN){
		/*if(x!=-1 && type!=-1) {
			if(type==0){
				Intent intent = new Intent (MainActivity.this, ShowProfile.class);
				intent.putExtra("ID", x);
				startActivity(intent);
			}
			else if (type==1){
				Intent intent = new Intent (MainActivity.this, Search.class);
				intent.putExtra("ID", x);
				startActivity(intent);
			}
			else if(type ==2){
				Intent intent = new Intent (MainActivity.this, TabBoth.class);
				intent.putExtra("ID", x);
				startActivity(intent);
			}
			
		}*/
		username = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
	//}else if (!SIGNED_IN ){
		//ab.hide();
		//setContentView(R.layout.activity_main);
		username.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	username.setText("");
            }
            });
		password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	password.setText("");
            }
            });
	//}
		signIn = (Button) findViewById(R.id.SIGNIN);
		signUp = (Button) findViewById(R.id.SIGNUPNOW);
		signIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NAME = username.getText().toString();
				PASSWORD = password.getText().toString();
				if(isEmpty(username) || isEmpty(password))
					Toast.makeText(MainActivity.this, "Missing Feild", Toast.LENGTH_SHORT).show();
				else
					new SignInTask().execute(NAME);
			}
		});
		signUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent(MainActivity.this, SignUp.class);
				startActivity(intent);
			}
		});
		}
	private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
	
	 private class SignInTask extends AsyncTask<String, Void ,String>  {
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
					url = new URL("http://95.85.20.131/g5/validate.php");
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoOutput(true);
					osw = new OutputStreamWriter(connection.getOutputStream());
					osw.write("UserName=" + params[0]);
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
				//the value of result is s, this function is executed automatically after doinbackground
		    	//do something with string s, (read json data, parse it, whatever, continue rest of activities logic flow here)
					
					try {
						//Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
						//s= s.replaceAll("\n", "\\n");
						JSONArray jArary = new JSONArray(s);
						JSONObject j = jArary.getJSONObject(0);
						Log.d("Testing",s);
						String Pass = j.getString("Password");
						type = j.getInt("Type");
						int ID = j.getInt("ID");
						Toast.makeText(MainActivity.this,PASSWORD,Toast.LENGTH_SHORT).show();
						//Toast.makeText(MainActivity.this,Pass,Toast.LENGTH_SHORT).show();
						if(Pass.equals(PASSWORD)){
							editor.putBoolean("SIGNEDIN", true);
							editor.commit();
							//Toast.makeText(MainActivity.this,type,Toast.LENGTH_SHORT).show();
							Log.d("TYPE",String.valueOf(ID));
							if(type==0){
								Intent intent = new Intent (MainActivity.this, ShowProfile.class);
								intent.putExtra("ID", ID);
								startActivity(intent);
							}
							else if (type==1){
								Intent intent = new Intent (MainActivity.this, Search.class);
								intent.putExtra("ID", ID);
								startActivity(intent);
							}
							else if(type ==2){
								Intent intent = new Intent (MainActivity.this, TabBoth.class);
								intent.putExtra("ID",ID);
								startActivity(intent);
							}
						}
						else{
							Toast.makeText(MainActivity.this,"Invalid Password",Toast.LENGTH_SHORT).show();
						}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Toast.makeText(MainActivity.this,"Invalid User Name",Toast.LENGTH_SHORT).show();
					e.printStackTrace();					
				}
    	
    	
				}
	 }
	

}
