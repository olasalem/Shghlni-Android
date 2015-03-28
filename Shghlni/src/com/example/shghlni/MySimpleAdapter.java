package com.example.shghlni;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MySimpleAdapter extends ArrayAdapter<String> //implements ListAdapter 
{
	/**
	 * 
	 */
	public final Context context;
	private final String[] values;
	ArrayList<Freelancer> Farray;
	  public MySimpleAdapter(Context context, String[]  values, ArrayList<Freelancer> Farray) {
		    super(context, R.layout.rowlayout, values);
		    this.context = context;
		    this.values = values;
		    this.Farray = Farray;
	  }
	@SuppressLint("ViewHolder")
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) 
	  {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.label);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    textView.setText( Farray.get(position).getName());
	    imageView.setImageResource(R.id.icon);
	    //php for fetching the image 
	    //imageView.setImage(Farray.get(position).getImageID());
	    return rowView;
	  }
	/*@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}*/
	} 
	

