package com.washpost.games.knowtrivia.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.washpost.games.knowtrivia.R;
import com.washpost.games.knowtrivia.adapter.ImageAdapter;

public class CategorySelectActivity extends Activity implements GridView.OnItemClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);

        RelativeLayout categoryScreen =(RelativeLayout)findViewById(R.id.category_screen);

        GridView gridview = (GridView) findViewById(R.id.category_selection);
        ImageAdapter imageAdapter=new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(this);

      }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), QuestionAnswerActivity.class);
        intent.putExtra("QUESTION_LIST", "");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
