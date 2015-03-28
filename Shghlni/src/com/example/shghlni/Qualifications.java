package com.example.shghlni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shghlni.MultiSpinner.MultiSpinnerListener;

public class Qualifications extends Activity implements OnItemSelectedListener, MultiSpinnerListener {
	EditText RPH;
	private Button Proceed;
	String rate;
	//Spinner spinnerSkills;
	int TYPE,ID;
	String [] s_items;
	List<String>items = new ArrayList<String>();
	JSONArray jArray = new JSONArray();
	MultiSpinner multispinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		s_items = getResources().getStringArray(R.array.qualifications);
		for(int i=0;i<s_items.length;i++)
		{
			items.add(s_items[i]);
		}
		setContentView(R.layout.activity_qualifications);
		RPH = (EditText) findViewById(R.id.RPH);
		Intent extras = getIntent();
		ID = extras.getIntExtra("ID", -1);
		TYPE = extras.getIntExtra("type", -1);
		Proceed = (Button) findViewById(R.id.button1);
		multispinner=(MultiSpinner) findViewById(R.id.multi_spinner);
		multispinner.setItems(items, "Qualifications", this);
		Proceed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isEmpty(RPH)){
					Toast.makeText(Qualifications.this, "Missing Feild", Toast.LENGTH_SHORT).show();

				}
				else{
					boolean [] gSelected;
					gSelected = multispinner.getSelected();
					for(int i=0;i<gSelected.length;i++){
						if(gSelected[i]==true){
						      JSONObject Q = new JSONObject();
						      try {
								Q.put("q", String.valueOf(i));
								jArray.put(Q);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							//new QualificationTask().execute(String.valueOf(ID),String.valueOf(i));
						}
					}
					String s = jArray.toString();
					Log.d("JSONARRAY",s);
					new QualificationTask().execute(String.valueOf(ID),s);
					
			}}
			});
		/*final ArrayList<String> qitems =new ArrayList<String>();
		String [] qualification;
		qualification=getResources().getStringArray(R.array.qualifications);
		for(int i=0;i<26;i++)
		{
			qitems.add(qualification[i]);
		}
		MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
		for(int i=0;i<qitems.size();i++)
		{
			multiSpinner.setItems(qitems, qitems.get(i),this);
		}*/
		//spinnerSkills = (Spinner) findViewById(R.id.spinner1);
		//spinnerSkills.setOnItemSelectedListener(this);
		// Create an ArrayAdapter using the string array and a default spinner layout
		//ArrayAdapter<CharSequence> adapterSkills = ArrayAdapter.createFromResource(this,
		       // R.array.qualifications, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		//adapterSkills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		//spinnerSkills.setAdapter(adapterSkills);
		
		
		
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		//super.onItemSelected(parent,view,position,id);
		// TODO Auto-generated method stub
		String item = (String) parent.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemsSelected(boolean[] selected) {
		// TODO Auto-generated method stub
		
	}
	private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
	
	 private class QualificationTask extends AsyncTask<String, Void ,String>  {
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
						url = new URL("http://95.85.20.131/g5/GetQ.php");
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("POST");
						connection.setDoOutput(true);
						osw = new OutputStreamWriter(connection.getOutputStream());
						osw.write("ID="+params[0]+"&pos=" + params[1]);
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
					rate = RPH.getText().toString();
					Intent intent = new Intent(Qualifications.this,Information.class);
					intent.putExtra("rate", rate);
					intent.putExtra("ID", ID);
					intent.putExtra("type", TYPE);
					Log.d("INFO",String.valueOf(ID)+"  "+String.valueOf(TYPE));
					startActivity(intent);
	 }
	 }
}

	

