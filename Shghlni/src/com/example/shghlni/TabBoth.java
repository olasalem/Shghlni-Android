package com.example.shghlni;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabBoth extends TabActivity
{
            /** Called when the activity is first created. */
            @Override
            public void onCreate(Bundle savedInstanceState)
            {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_tabactivity);
                    Intent intent = getIntent();
                    int ID = intent.getIntExtra("ID",-1);
                    // create the TabHost that will contain the Tabs
                    TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


                    TabSpec tab1 = tabHost.newTabSpec("Serach");
                    TabSpec tab2 = tabHost.newTabSpec("Show Profile");
                    

                   // Set the Tab name and Activity
                   // that will be opened when particular Tab will be selected
                    tab1.setIndicator("Serach");
                    //Second.class hat7oty fiha el class
                    Intent c1 = new Intent(this,Search.class);
                    c1.putExtra("ID", ID);
                    c1.putExtra("TYPE", 2);
                    tab1.setContent(c1);
                   
                    tab2.setIndicator("Show Profile");
                    //Same here
                    Intent c2 = new Intent(this,ShowProfile.class);
                    c2.putExtra("ID", ID);
                    c2.putExtra("TYPE", 2);
                    tab2.setContent(c2);

                    
                   
                    /** Add the tabs  to the TabHost to display. */
                    tabHost.addTab(tab1);
                    tabHost.addTab(tab2);
                    

            }
} 