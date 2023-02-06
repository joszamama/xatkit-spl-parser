package com.xatkit.spl.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jayway.jsonpath.JsonPath;

/**
 * Hello world!
 *
 */
public class XatkitParser 
{
    public static void compile(String path) {
        try {   
            JSONParser parser = new JSONParser();
            JSONObject chatbot = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8")));

            String BOT_NAME = JsonPath.read(chatbot, "$.name");
            List<String> BOT_INTENTS_TITLES = JsonPath.read(chatbot, "$.intents[*].title");
            List<List<String>> BOT_INTENTS_TRAINING = JsonPath.read(chatbot, "$.intents[*].training");
            String BOT_FALLBACK = JsonPath.read(chatbot, "$.fallback");

            System.out.println("--------------------");
            System.out.println("Nombre: " + BOT_NAME);
            System.out.println("Intents Names: " + BOT_INTENTS_TITLES);
            System.out.println("First Intent Title: " + BOT_INTENTS_TITLES.get(0));
            System.out.println("Intents Training: " + BOT_INTENTS_TRAINING);
            System.out.println("First Intent Training: " + BOT_INTENTS_TRAINING.get(0));
            for (int i = 0; i < BOT_INTENTS_TRAINING.size(); i++) {
                System.out.println("Training Sentence " + i + "from Intent Training 1: " + BOT_INTENTS_TRAINING.get(0).get(i));
            }
            System.out.println("Fallback: " + BOT_FALLBACK);
            System.out.println("--------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fillIntents() {
        
    }
    
    public static void main( String[] args )
    {
        String path = ".\\src\\bots\\ExampleBot.json";
        compile(path);
    }
}
