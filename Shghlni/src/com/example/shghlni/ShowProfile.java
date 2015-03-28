package com.example.shghlni;

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
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShowProfile extends Activity {
	public static final String userdetail = "userdetail";
	public static SharedPreferences pref ;
	public static Editor editor; 
	private ImageView imageview;
	private int ID,TYPE;
	protected TextView Name;
	protected TextView Area;
	protected TextView FreeSlots;
	protected TextView Phone1;
	protected TextView Phone2;
	protected TextView RatePerHour;
	protected RatingBar Ratings;
	String P1,P2;
	Button update, signout,hbutton;
	Context context = this;
	//protected ArrayList<String> profile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_profile);
		//Passed intent from sign in/sign up activity
		Intent intent = getIntent();
		ID = intent.getIntExtra("ID", -1);
		TYPE = intent.getIntExtra("type",-1);
		
		if (TYPE==3){
			setContentView(R.layout.activity_freelancer_profile);
			Name = (TextView)findViewById(R.id.username);
			Area = (TextView)findViewById(R.id.areatext);
			imageview=(ImageView)findViewById(R.id.userimage);
			hbutton = (Button) findViewById(R.id.HireMe);
			//RatePerHour = (TextView)findViewById(R.id.RatePerHour);
			imageview.setImageResource(R.drawable.ic_launcher);
			Intent start = getIntent();
			hbutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.custom);
					dialog.setTitle("Hire Me!");
					// set the custom dialog components - text, image and button
					TextView text = (TextView) dialog.findViewById(R.id.text);
					text.setText("Contact Me at:\n"+P1+"\n"+P2);
					Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
					// if button is clicked, close the custom dialog
					dialogButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
		 
					dialog.show();
				  }
				});
			}
		else{
			/*ActionBar a =getActionBar();
			a.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b6b6b4")));
			
			a.setDisplayShowTitleEnabled(false);
	        a.setDisplayUseLogoEnabled(false);
	        a.setDisplayHomeAsUpEnabled(false);
	        a.setDisplayShowCustomEnabled(true);
	        View cView = getLayoutInflater().inflate(R.layout.actionbar, null);
	        a.setCustomView(cView);*/
			
			Name = (TextView)findViewById(R.id.Name);
			Area = (TextView)findViewById(R.id.Area);
			Phone1 = (TextView)findViewById(R.id.p1);
			Phone2 = (TextView)findViewById(R.id.p2);
			RatePerHour = (TextView)findViewById(R.id.RPH);
			update = (Button) findViewById(R.id.UPDATE);
			signout = (Button) findViewById(R.id.SIGNOUT);
			update.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ShowProfile.this,SignUp.class);
					intent.putExtra("ID", ID);
					intent.putExtra("ACTION",1);
					startActivity(intent);
				}
			});
			signout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ShowProfile.this,MainActivity.class);
					intent.putExtra("ACTION",0);
					startActivity(intent);
					
				}
			});
		}
		new UserSearch().execute(String.valueOf(ID));
	
	}
	private class UserSearch extends AsyncTask <String , Void , String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			URL url = null;
			HttpURLConnection connection = null;
			OutputStreamWriter osw = null;
			StringBuilder stringb = new StringBuilder();
			BufferedReader buff = null;
			String line;
			try {
				url = new URL("http://95.85.20.131/g5/searchID.php");
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				osw = new OutputStreamWriter(connection.getOutputStream());
				osw.write("ID="+ params[0]);
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
			/*int s_id = params[0];
			String ss_id = Integer.toString(s_id);
			URL url = null;
			try {
				url =new URL("http://95.85.20.131/g5/searchID.php");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				OutputStreamWriter osw =  new OutputStreamWriter(connection.getOutputStream());
				osw.write("ID="+ss_id);
				osw.flush();
				//Read the response
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine())!=null)
				{
					sb.append(line+ "\n");
				}
				Log.i("response",sb.toString());
				return sb.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("Exception",e.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Log.e("Exception",e1.toString());
			}*/
			
		}
		@Override
		protected void onPostExecute(String sb){
			try{
			Log.d("Profile",sb);
			JSONArray jarray = new JSONArray(sb);
			JSONObject jobject = jarray.getJSONObject(0);
			//profile.add(jobject.getString("Name"));
			Name.setText(jobject.getString("Name"));
			//profile.add(jobject.getString("Area"));
			Area.setText(jobject.getString("Area"));
			//profile.add(jobject.getString("FreeSlots"));
			
			//profile.add(jobject.getString("Phone1"));
			P1 = jobject.getString("Phone1");
			P2 = jobject.getString("Phone2");
			if(TYPE !=3){
			Phone1.setText(P1);
			//profile.add(jobject.getString("Phone2"));
			Phone2.setText(P2);
			Ratings.setNumStars(jobject.getInt("Ratings"));
			RatePerHour.setText(jobject.getString("RatePerHour"));
			}
			}catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.e("Exception",e1.toString());
		}
		}
		
		
	}
}

