package com.example.shghlni;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class SearchResult extends ListActivity {
	String Response;
	ArrayList<Freelancer> FArrayList;
	int ID,QID,AreaID;
	String name,phone1,phone2,ImageID;
	MySimpleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		Intent intent = getIntent();
		QID = intent.getIntExtra("QID", -1);
		Response = intent.getStringExtra("Response");
		Log.d("searchResult",Response);
		String[] values ;
		FArrayList = new ArrayList<Freelancer>();
		try {
			JSONArray jarray = new JSONArray(Response);
			values = new String [jarray.length()];
			for(int i=0;i<jarray.length();i++){
				JSONObject j = jarray.getJSONObject(i);
				ID = j.getInt("ID");
				name = j.getString("Name");
				AreaID = j.getInt("Area");
				phone1 = j.getString("Phone1");
				phone2 = j.getString("Phone2");
				ImageID = j.getString("ImageID");
				values[i] = name;
				FArrayList.add(i,new Freelancer(ID,name,AreaID,phone1,phone2,ImageID));
				//working so far and info is logged 3ady
				Log.d("TEST RESULTS",name+" "+AreaID+" "+phone1);
			}
			//the error is here in this line
			adapter = new MySimpleAdapter(this,values,FArrayList);
			setListAdapter(adapter);
			} catch (JSONException e)
			{
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("JException",e.toString());
			}
			//Log.d("TEST RESULTS",FArrayList.get(0).getName()+" "+FArrayList.get(0).getAreaID());
		}
		public void onListItemClick(ListView parent,View view, int position, long id) 
		{
			super.onListItemClick(parent, view, position, id);
			Intent showprofile = new Intent(this,ShowProfile.class);//FreelancerProfile.class);
			showprofile.putExtra("ID", FArrayList.get(position).getID());
			showprofile.putExtra("type",3);
			startActivity(showprofile);
		};
		
			
}
