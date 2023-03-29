package com.xatkit.example;

import com.xatkit.core.XatkitBot;
import com.xatkit.plugins.react.platform.ReactPlatform;
import com.xatkit.plugins.react.platform.io.ReactEventProvider;
import com.xatkit.plugins.react.platform.io.ReactIntentProvider;
import lombok.val;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;

import static com.xatkit.dsl.DSL.eventIs;
import static com.xatkit.dsl.DSL.fallbackState;
import static com.xatkit.dsl.DSL.intent;
import static com.xatkit.dsl.DSL.intentIs;
import static com.xatkit.dsl.DSL.model;
import com.xatkit.core.recognition.nlpjs.*;
import static com.xatkit.dsl.DSL.state;

public class ProfeBOT {

    public static void main(String[] args) {

        val AlumnoMariano = intent("Alumno Mariano")
            .trainingSentence("¿Quién es Mariano?")
            .trainingSentence("Dame info sobre Mariano")
            .trainingSentence("Mariano")
            .trainingSentence("¿Qué horarios tiene Mariano?");

        val AlumnoCarlos = intent("Alumno Carlos")
            .trainingSentence("¿Quién es Carlos?")
            .trainingSentence("Dame info sobre Carlos")
            .trainingSentence("Carlos")
            .trainingSentence("¿Qué horarios tiene Carlos?");

        ReactPlatform reactPlatform = new ReactPlatform();
        ReactEventProvider reactEventProvider = reactPlatform.getReactEventProvider();
        ReactIntentProvider reactIntentProvider = reactPlatform.getReactIntentProvider();

        val init = state("Init");
        val awaitingInput = state("AwaitingInput");
        val handleAlumnoMariano = state("HandleAlumnoMariano");
        val handleAlumnoCarlos = state("HandleAlumnoCarlos");

        init
            .next()
            .when(eventIs(ReactEventProvider.ClientReady)).moveTo(awaitingInput);

        awaitingInput
            .next()
            .when(intentIs(AlumnoMariano)).moveTo(handleAlumnoMariano)
            .when(intentIs(AlumnoCarlos)).moveTo(handleAlumnoCarlos);

        handleAlumnoMariano
            .body(context -> reactPlatform.reply(context, "Mariano Torrado trabaja en el despacho XX.XX, con horarios de tutoría de 16.00 a 16.01"))
            .next()
            .moveTo(awaitingInput);

        handleAlumnoCarlos
            .body(context -> reactPlatform.reply(context, "Carlos Núñez trabaja en el despacho XX.XX, con horarios de tutoría de 16.00 a 16.01"))
            .next()
            .moveTo(awaitingInput);

        val defaultFallback = fallbackState()
                .body(context -> reactPlatform.reply(context, "No dispongo de información relacionada, siento las molestias."));

        val botModel = model()
            .usePlatform(reactPlatform)
            .listenTo(reactEventProvider)
            .listenTo(reactIntentProvider)
            .initState(init)
            .defaultFallbackState(defaultFallback);

        Configuration botConfiguration = new BaseConfiguration();
        botConfiguration.addProperty("xatkit.intent.provider", "com.xatkit.core.recognition.nlpjs.NlpjsIntentRecognitionProvider");
        botConfiguration.addProperty(NlpjsConfiguration.AGENT_ID_KEY, "default");
        botConfiguration.addProperty(NlpjsConfiguration.LANGUAGE_CODE_KEY, "en");
        botConfiguration.addProperty(NlpjsConfiguration.NLPJS_SERVER_KEY, "http://localhost:8080");
        XatkitBot xatkitBot = new XatkitBot(botModel, botConfiguration);
        xatkitBot.run();
    }
}