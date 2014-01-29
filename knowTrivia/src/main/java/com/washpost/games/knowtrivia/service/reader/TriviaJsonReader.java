package com.washpost.games.knowtrivia.service.reader;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.washpost.games.knowtrivia.pojo.Category;
import com.washpost.games.knowtrivia.pojo.GameLevel;
import com.washpost.games.knowtrivia.pojo.TriviaQuestion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muppallav on 1/24/14.
 */
public class TriviaJsonReader {

    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();

    public List<TriviaQuestion> readTriviaJson(String jsonString){
      //  Gson gson=new Gson();
        List<TriviaQuestion> questionList=null;
       /* JSONArray questionArray=null;
        try {
            questionArray=new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        //Type listType = new TypeToken<List<TriviaQuestion>>() {}.getType();
        try{
           // questionList=gson.fromJson(jsonString,listType);
            String gsonString=generateTriviaJson();
            Type listType = new TypeToken<List<TriviaQuestion>>() {}.getType();
             questionList=gson.fromJson(jsonString,listType);
        }catch (Exception e){
            Log.e("READER",e.getMessage());
            e.printStackTrace();
        }
        return questionList;
    }

    public String generateTriviaJson(){
        List<TriviaQuestion> triviaQuestions=new ArrayList<TriviaQuestion>();
        for(int i=0;i<2;i++){
            triviaQuestions.add(generateTriviaQuestion(i));

        }
       String gsonString= gson.toJson(triviaQuestions);
        return gsonString;

    }

    public TriviaQuestion generateTriviaQuestion(int id){
        TriviaQuestion triviaQuestion=new TriviaQuestion();
        triviaQuestion.setGameLevel(generateGameLevel(id));
        triviaQuestion.setCategory(generateCategory(id));
        triviaQuestion.setAnswerKey(2);
        triviaQuestion.setMediaType("TEXT");
        triviaQuestion.setOptions(generateOptions());
        triviaQuestion.setQuestion("Question Number "+id);
        triviaQuestion.setHint(null);
        return triviaQuestion;

    }

    public Map<Integer,String> generateOptions(){
        Map<Integer,String> options=new HashMap<Integer, String>(4);
        options.put(1,"option 1");
        options.put(2,"option 2");
        options.put(3,"option 3");
        options.put(4,"option 4");
        return options;
    }

    public Category generateCategory(int id){
        Category category=new Category();
        category.setCategoryId(id);
        category.setCategoryName("Category "+id);
        return category;

    }

    public GameLevel generateGameLevel(int id){
        GameLevel gameLevel=new GameLevel();
        gameLevel.setLevelId(id);
        gameLevel.setLevelCategory("Gamelevel "+id);
        return gameLevel;
    }

}
