package com.example.shghlni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class RateFreelancer extends Activity {
	protected int freelancerid;
	protected TextView Freelancer_name;
	protected RatingBar ratingbar;
	private Button button;
	protected float rate;
	protected int jumpTime = 0;
    protected int totalProgressTime = 100; 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_freelancer);
        //Getting the intent from the passed activity with the Freelancer id
        //Intent intent = getIntent();
        //freelancerid = intent.getIntExtra("id", -1);
        //Freelancer_name=(TextView)findViewById(R.id.Uname);
        ratingbar=(RatingBar)findViewById(R.id.ratingbar);
        button=(Button)findViewById(R.id.DoneButton);
       
      /*  if(freelancerid==-1)
        {
        	Toast.makeText(getApplicationContext(), "Invalid ID Please Try Again", Toast.LENGTH_SHORT).show();
        }*/
        //else
       // {
        	//GetFreelancer getfreelancer = new GetFreelancer();
        	//getfreelancer.execute();
        	 ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
        	 public void onRatingChanged(RatingBar ratingBar, float rating,
        	        boolean fromUser) {

        	        Toast.makeText(getApplicationContext(),"Your Selected Ratings: " + String.valueOf(rating),Toast.LENGTH_SHORT).show();
        	        rate=rating;
        	      
        	        }
        	        });
        	 	button.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     // Perform action on click
                	 new UpdateRating().execute();
                 }
             });

       // }
        
    }
    private class UpdateRating extends AsyncTask <String , Integer , String>
	{
    	private ProgressDialog progress = new ProgressDialog(getApplicationContext());
		protected void onPreExecute(String...params)
		{
			progress.setMessage("Updating..Please Wait :) ");
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setIndeterminate(true);
			progress.show();
		}
    	@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			URL url;
			try{
			url = new URL("http://95.85.20.131/g5/rate.php");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
			osw.write("ID="+freelancerid+"&Rating"+rate);
			osw.flush();
			publishProgress(jumpTime,totalProgressTime);
			}catch (IOException e)
			{
				Log.e("Exception",e.toString());
			}
			return null;
		}
		protected void onProgressUpdate(Integer... values)
		{
			
		      if(jumpTime < totalProgressTime){
		         jumpTime += 5;
				progress.setProgress(jumpTime);

		}
		}      
		protected void onPostExecute(String...params)
		{
			//Intent i = new Intent(getApplicationContext());//,The Name of the Activity);
			//start(i);
		}
	}
    private class GetFreelancer extends AsyncTask <String , Void , String>
   	{
    	JSONArray jarray;
    	JSONObject jobject;
   		@SuppressLint("NewApi") @Override
   		protected String doInBackground(String... params) {
   			// TODO Auto-generated method stub
   			URL url;
   			try{
   			url = new URL("http://10.0.2.2/sha3'lni/SearchID.php");
   			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
   			connection.setDoOutput(true);
   			connection.setRequestMethod("POST");
   			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
   			osw.write("id="+Integer.valueOf(freelancerid));
   			osw.flush();
   			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine())!=null)
			{
				sb.append(line+ "\n");
			}
			Log.i("response",sb.toString());
			try {
				jobject=new JSONObject(sb.toString());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("JSON Exception",e.toString());
				e.printStackTrace();
			}
			br.close();
			
   			}catch (IOException e)
   			{
   				Log.e("Exception",e.toString());
   			}
   			return null;
   		}
   		protected void onPostExecute(String params)
   		{
   		try {
			Freelancer_name.setText((CharSequence) jobject.get("name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("JSON",e.toString());
			e.printStackTrace();
		}
   		}

   	}
    }

