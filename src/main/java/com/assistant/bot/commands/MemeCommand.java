package com.assistant.bot.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//Command: /meme
public class MemeCommand implements ICommand {
    @Override
    public String getName() {
        return "meme";
    }

    @Override
    public String getDescription() {
        return "Send your friends a meme from reddit!";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        JsonParser parser = new JsonParser();
        String postLink = "";
        String title = "";
        String url = "";

        try {
            URL memeURL = new URL("https://meme-api.com/gimme");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                JsonArray array = new JsonArray();
                array.add(parser.parse(lines));

                for (Object o : array) {
                    JsonObject json = (JsonObject) o;

                    postLink = json.get("postLink").getAsString();
                    title = json.get("title").getAsString();
                    url = json.get("url").getAsString();

                }
            }

            bufferedReader.close();
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle(title, postLink)
                    .setImage(url)
                    .setColor(Color.ORANGE);
            event.getChannel().sendMessageEmbeds(builder.build()).queue();
            event.deferReply(true).queue();
            event.getHook().sendMessage("Here's your meme!").queue();

        } catch (Exception e) {
            event.getChannel().sendMessage(":no_entry: **Hey, something went wrong. Please try again later!**").queue();
            e.printStackTrace();
        }
    }
}
