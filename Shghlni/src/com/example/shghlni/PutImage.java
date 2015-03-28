package com.example.shghlni;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PutImage extends Activity {
	private static int RESULT_LOAD_IMAGE = 1;
	Button Load;
	Button SIGNUP;
	int ID,TYPE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_put_image);
		Intent extras = getIntent();
		ID = extras.getIntExtra("ID", -1);
		TYPE = extras.getIntExtra("type", -1);
		Load = (Button) findViewById(R.id.buttonLoadPicture);
		Load.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
			    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				//startActivityForResult(i, RESULT_LOAD_IMAGE);
				
			}
		});
		SIGNUP.findViewById(R.id.button1);
		SIGNUP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if(TYPE==0){//freelancer
					Intent intent= new Intent(PutImage.this,ShowProfile.class);
					intent.putExtra("ID", ID);
					startActivity(intent);
				/*}
				else if(TYPE==2){//both
					Intent intent= new Intent(PutImage.this,TabBoth.class);
					intent.putExtra("ID", ID);
					startActivity(intent);
				
				}*/
				
			}
		});
	}
	/*@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	      
	     if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,
	                 filePathColumn, null, null, null);
	         cursor.moveToFirst();
	 
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         String picturePath = cursor.getString(columnIndex);
	         cursor.close();
	                      
	         // String picturePath contains the path of selected Image
	     }
	}*/
}
