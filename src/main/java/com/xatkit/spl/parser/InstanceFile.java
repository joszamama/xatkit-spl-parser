package com.xatkit.spl.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jayway.jsonpath.JsonPath;

public class InstanceFile {

    /**
     * @param path: The path to the JSON file, without the extension
     * @return void: It creates a .java file with the same name as the JSON file
     */
    public static void compile(String path) {
        try {
            System.out.println("Parsing Xatkit-SPL file at " + path);
            JSONParser parser = new JSONParser();
            JSONObject chatbot = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(path + ".json"), "UTF-8")));

            String BOT_NAME = JsonPath.read(chatbot, "$.name");
            List<String> BOT_INTENTS_TITLES = JsonPath.read(chatbot, "$.intents[*].title");
            List<List<String>> BOT_INTENTS_TRAINING = JsonPath.read(chatbot, "$.intents[*].training");
            List<String> BOT_INTENTS_RESPONSES = JsonPath.read(chatbot, "$.intents[*].response");
            String BOT_FALLBACK = JsonPath.read(chatbot, "$.fallback");

            InstanceFile.instanceFile(path + ".java", BOT_NAME, BOT_INTENTS_TITLES, BOT_INTENTS_TRAINING, BOT_INTENTS_RESPONSES, BOT_FALLBACK);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param path: The path to the JSON file, without the extension
     * @param BOT_NAME: The name of the chatbot
     * @param BOT_INTENTS_TITLES: The titles of the intents
     * @param BOT_INTENTS_TRAINING: The training sentences of the intents
     * @param BOT_INTENTS_RESPONSES: The responses of the intents
     * @param BOT_FALLBACK: The fallback response
     * @return void: It creates a .java file with the same name as the JSON file
     */
    public static void instanceFile(String path, String BOT_NAME, List<String> BOT_INTENTS_TITLES, List<List<String>> BOT_INTENTS_TRAINING, List<String> BOT_INTENTS_RESPONSES, String BOT_FALLBACK) throws IOException {
        new File(path);
        Writer myWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
        myWriter.write("package com.xatkit.example;\n\n");
        
        myWriter.write("import com.xatkit.core.XatkitBot;\n");
        myWriter.write("import com.xatkit.plugins.react.platform.ReactPlatform;\n");
        myWriter.write("import com.xatkit.plugins.react.platform.io.ReactEventProvider;\n");
        myWriter.write("import com.xatkit.plugins.react.platform.io.ReactIntentProvider;\n");
        myWriter.write("import lombok.val;\n");
        myWriter.write("import org.apache.commons.configuration2.BaseConfiguration;\n");
        myWriter.write("import org.apache.commons.configuration2.Configuration;\n\n");

        myWriter.write("import static com.xatkit.dsl.DSL.eventIs;\n");
        myWriter.write("import static com.xatkit.dsl.DSL.fallbackState;\n");
        myWriter.write("import static com.xatkit.dsl.DSL.intent;\n");
        myWriter.write("import static com.xatkit.dsl.DSL.intentIs;\n");
        myWriter.write("import static com.xatkit.dsl.DSL.model;\n");
        myWriter.write("import static com.xatkit.dsl.DSL.state;\n\n");

        myWriter.write("public class " + BOT_NAME + " {\n\n");
        myWriter.write("    public static void main(String[] args) {\n\n");

        for (int i = 0; i < BOT_INTENTS_TITLES.size(); i++) {
            myWriter.write("        val " + BOT_INTENTS_TITLES.get(i).replaceAll(" ", "") + " = intent('" + BOT_INTENTS_TITLES.get(i) + "')\n");
            for (int j = 0; j < BOT_INTENTS_TRAINING.get(i).size(); j++) {
                if (j == BOT_INTENTS_TRAINING.get(i).size() - 1)
                    myWriter.write("            .trainingSentence('" + BOT_INTENTS_TRAINING.get(i).get(j) + "');\n\n");
                else
                    myWriter.write("            .trainingSentence('" + BOT_INTENTS_TRAINING.get(i).get(j) + "')\n");
            }
        }

        myWriter.write("        ReactPlatform reactPlatform = new ReactPlatform();\n");
        myWriter.write("        ReactEventProvider reactEventProvider = reactPlatform.getReactEventProvider();\n");
        myWriter.write("        ReactIntentProvider reactIntentProvider = reactPlatform.getReactIntentProvider();\n\n");

        myWriter.write("        val init = state('Init');\n");
        myWriter.write("        val awaitingInput = state('AwaitingInput');\n");
        for (int i = 0; i < BOT_INTENTS_TITLES.size(); i++) {
            myWriter.write("        val handle" + BOT_INTENTS_TITLES.get(i).replaceAll(" ", "") + " = state('Handle" + BOT_INTENTS_TITLES.get(i).replaceAll(" ", "") + "');\n");
        }

        myWriter.write("\n        init\n");
        myWriter.write("            .next()\n");
        myWriter.write("            .when(eventIs(ReactEventProvider.ClientReady)).moveTo(awaitingInput);\n\n");

        myWriter.write("        awaitingInput\n");
        myWriter.write("            .next()\n");
        for (int i = 0; i < BOT_INTENTS_TITLES.size(); i++) {
            if (i == BOT_INTENTS_TITLES.size() - 1)
                myWriter.write("            .when(intentIs(" + BOT_INTENTS_TITLES.get(i).replaceAll(" ", "") + ")).moveTo(handle" + BOT_INTENTS_TITLES.get(i).replaceAll(" ", "") + ");\n\n");
            else {
                myWriter.write("            .when(intentIs(" + BOT_INTENTS_TITLES.get(i).replaceAll(" ", "") + ")).moveTo(handle" + BOT_INTENTS_TITLES.get(i).replaceAll(" ", "") + ")\n");
            }
        }

        for (int i = 0; i < BOT_INTENTS_TITLES.size(); i++) {
            myWriter.write("        handle" + BOT_INTENTS_TITLES.get(i).replaceAll(" ", "") + "\n");
            myWriter.write("            .body(context -> reactPlatform.reply(context, '" + BOT_INTENTS_RESPONSES.get(i) +"'))\n");
            myWriter.write("            .next()\n");
            myWriter.write("            .moveTo(awaitingInput);\n\n");
        }

        myWriter.write("        val defaultFallback = fallbackState()\n");
        myWriter.write("                .body(context -> reactPlatform.reply(context, '" + BOT_FALLBACK + "'));\n\n");

        myWriter.write("        val botModel = model()\n");
        myWriter.write("            .usePlatform(reactPlatform)\n");
        myWriter.write("            .listenTo(reactEventProvider)\n");
        myWriter.write("            .listenTo(reactIntentProvider)\n");
        myWriter.write("            .initState(init)\n");
        myWriter.write("            .defaultFallbackState(defaultFallback);\n\n");
        
        myWriter.write("        Configuration botConfiguration = new BaseConfiguration();\n");
        myWriter.write("        XatkitBot xatkitBot = new XatkitBot(botModel, botConfiguration);\n");
        myWriter.write("        xatkitBot.run();\n");
        
        myWriter.write("    }\n");
        myWriter.write("}");

        myWriter.close();

        String content = new String(Files.readAllBytes(Paths.get(path)));
        content = content.replaceAll("XatkitBotInstance", BOT_NAME);
        content = content.replaceAll("'", "\"");
        Files.write(Paths.get(path), content.getBytes());

    }
}
