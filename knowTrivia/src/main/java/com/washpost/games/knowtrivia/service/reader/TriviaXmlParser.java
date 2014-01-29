package com.washpost.games.knowtrivia.service.reader;

import com.washpost.games.knowtrivia.pojo.Category;
import com.washpost.games.knowtrivia.pojo.GameLevel;
import com.washpost.games.knowtrivia.pojo.Hint;
import com.washpost.games.knowtrivia.pojo.TriviaQuestion;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muppallav on 1/24/14.
 */
public class TriviaXmlParser {

    private static final String ns = null;

    public List<TriviaQuestion> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException{
        List<TriviaQuestion> entries = new ArrayList<TriviaQuestion>();

        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readTriviaQuestion(parser));
            } else {
                skip(parser);
            }
        }
        return entries;

    }

    protected TriviaQuestion readTriviaQuestion(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "triviaQuestion");
        TriviaQuestion question=new TriviaQuestion();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("category")) {
                question.setCategory(readCategory(parser));
            } else if (name.equals("summary")) {
                question.setGameLevel(readGameLevel(parser));
            } else if (name.equals("link")) {
                question.setHint(readHint(parser));
            } else {
                skip(parser);
            }
        }
        return new TriviaQuestion();
    }

    private Category readCategory(XmlPullParser parser) throws IOException, XmlPullParserException {
        Category category=new Category();
        parser.require(XmlPullParser.START_TAG, ns, "category");
        parser.require(XmlPullParser.END_TAG, ns, "category");
        return category;
    }

    private GameLevel readGameLevel(XmlPullParser parser) throws IOException, XmlPullParserException {
        GameLevel gameLevel=new GameLevel();
        parser.require(XmlPullParser.START_TAG, ns, "summary");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return gameLevel;
    }

    private Hint readHint(XmlPullParser parser) throws IOException, XmlPullParserException {
        Hint hint=new Hint();
        parser.require(XmlPullParser.START_TAG, ns, "summary");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return hint;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("link")) {
            if (relType.equals("alternate")){
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    protected void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
