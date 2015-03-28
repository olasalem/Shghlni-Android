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

public class Search extends Activity implements OnItemSelectedListener  {
	
	
	private Spinner spinnerSkills,Areaspinner;
	Button SearchButton,FillRequest;
	int ID,QID,AreaID;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		Intent extras = getIntent();
		ID = extras.getIntExtra("ID", -1);
		Log.d("IDSERACH",String.valueOf(ID));
		Areaspinner = (Spinner) findViewById(R.id.AreaSpinner);
		Areaspinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapterArea = ArrayAdapter.createFromResource(this,R.array.Areas, android.R.layout.simple_spinner_item);
		adapterArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Areaspinner.setAdapter(adapterArea);
		spinnerSkills = (Spinner) findViewById(R.id.SkillsSpinner);
		spinnerSkills.setOnItemSelectedListener(this);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterSkills = ArrayAdapter.createFromResource(this,
		        R.array.qualifications, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterSkills.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerSkills.setAdapter(adapterSkills);
		FillRequest = (Button) findViewById(R.id.FillButton);
		FillRequest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Search.this,Fillin_Request.class);
				i.putExtra("ID", ID);
				Log.d("IDFillin",String.valueOf(ID));
				startActivity(i);
			}
		});
		SearchButton = (Button) findViewById(R.id.SearchButton);
		SearchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				QID = spinnerSkills.getSelectedItemPosition();
				AreaID = Areaspinner.getSelectedItemPosition();
				new SearchQTask().execute(String.valueOf(QID),String.valueOf(AreaID));
			}
		});
		
	}
		/*final ArrayList<String> qitems =new ArrayList<String>();
		String [] qualification;
		qualification=getResources().getStringArray(R.array.qualifications);
		for(int i=0;i<26;i++)
		{
			qitems.add(qualification[i]);
		}
		/*boolean[] checkselected = new boolean[items.size()];
		for(int i=0;i<items.size();i++)
		{
			checkselected[i]=false;
		}
		LayoutInflater inflater = (LayoutInflater)Search.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.pop_up_window, (ViewGroup)findViewById(R.id.PopUpView));
		final PopupWindow pw = new PopupWindow(layout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		pw.setBackgroundDrawable(new BitmapDrawable());
		pw.setTouchable(true);
		pw.setOutsideTouchable(true);
		pw.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
			pw.dismiss();
			return true;
			}
			return false;
			}
			});
		pw.setContentView(layout);
		RelativeLayout main = (RelativeLayout)findViewById(R.id.main);
		pw.showAsDropDown(main);
		final ListView list = (ListView) layout.findViewById(R.id.dropDownlist);
		DropDownListAdapter adapter = new DropDownListAdapter(this, items, tv);
		list.setAdapter(adapter);
		MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
		for(int i=0;i<qitems.size();i++)
		{
			multiSpinner.setItems(qitems, qitems.get(i),
		                    this);
		}
		}*/

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String item = (String) parent.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
	
	 private class SearchQTask extends AsyncTask<String, Void ,String>  {
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
						url = new URL("http://10.0.2.2/Sh3%60lnyProjectPHP/searchQ.php");
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("POST");
						connection.setDoOutput(true);
						osw = new OutputStreamWriter(connection.getOutputStream());
						osw.write("pos=" + params[0]+"&area="+params[1]);
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
					Log.d("Response", s);
					Intent intent = new Intent(Search.this,SearchResult.class);
					//Intent intent = new Intent(Search.this,Fillin_Request.class);
					intent.putExtra("QID",String.valueOf(QID));
					intent.putExtra("Response", s);
					startActivity(intent);
	 }
	 }

}
