package com.washpost.games.knowtrivia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.washpost.games.knowtrivia.R;
import com.washpost.games.knowtrivia.constants.MediaTypeEnum;
import com.washpost.games.knowtrivia.pojo.TriviaQuestion;
import com.washpost.games.knowtrivia.service.reader.DataReader;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TriviaMainActivity extends Activity implements View.OnClickListener {

    DataReader reader=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_main);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        reader=new DataReader();
        RelativeLayout homeScreen =(RelativeLayout)findViewById(R.id.home_screen);

        ImageView logoImageView=(ImageView)findViewById(R.id.logoimage);
        logoImageView.setImageResource(R.drawable.knowtrivia);

        Button playButton=(Button)findViewById(R.id.play_button);
        playButton.setOnClickListener(this);

       // InputStream is = context.getAssets().open(questionFile);

    }

    @Override
    public void onClick(View v) {
        String questionDataFile="questions.json";
        List<TriviaQuestion> questionList=null;
        try {
            InputStream is = this.getAssets().open(questionDataFile);
            questionList=reader.readData(is, MediaTypeEnum.JSON_TYPE);
            if(questionList!=null && !questionList.isEmpty()){
                Intent intent = new Intent(this, CategorySelectActivity.class);
                startActivity(intent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void addCategories(){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trivia_main, menu);
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
