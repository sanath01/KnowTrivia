package com.washpost.games.knowtrivia.service.reader;

import android.util.Xml;

import com.washpost.games.knowtrivia.constants.MediaTypeEnum;
import com.washpost.games.knowtrivia.pojo.TriviaQuestion;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * Created by muppallav on 1/24/14.
 */
public class DataReader {

    private static final String ns = null;

    private TriviaXmlParser questionExtractor=null;
    private TriviaJsonReader triviaJsonReader=null;

    public List<TriviaQuestion> readData(InputStream in, MediaTypeEnum inputType) throws XmlPullParserException, IOException{

        try {
            switch(inputType){
                case XML_TYPE:
                    questionExtractor=new TriviaXmlParser();
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);
                    parser.nextTag();
                    return questionExtractor.parseXML(parser);
                case JSON_TYPE:
                    triviaJsonReader=new TriviaJsonReader();
                    int size = in.available();
                    byte[] buffer = new byte[size];
                    in.read(buffer);
                    String jsonString = new String(buffer, "UTF-8");
                    return triviaJsonReader.readTriviaJson(jsonString);
                default:
                    return Collections.EMPTY_LIST;
            }

        } finally {
            in.close();
        }
    }







}
