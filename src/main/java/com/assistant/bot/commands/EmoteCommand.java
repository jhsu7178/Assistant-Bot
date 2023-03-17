package com.assistant.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.ArrayList;
import java.util.List;

//Command: /emote [type]
public class EmoteCommand implements ICommand {
    @Override
    public String getName() {
        return "emote";
    }

    @Override
    public String getDescription() {
        return "Express your emotions through text";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "type", "The type of emotion to express", true)
                .addChoice("Hug", "hug")
                .addChoice("Laugh", "laugh")
                .addChoice("Cry", "cry"));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping option = event.getOption("type");
        String type = option.getAsString();
        String replyMessage = "";
        switch (type.toLowerCase()) {
            case "hug" -> {
                replyMessage = "You hug the closest person to you.";
            }
            case "laugh" -> {
                replyMessage = "ROFL";
            }
            case "cry" -> {
                replyMessage = "WAH WAH";
            }
        }

        event.reply(replyMessage).queue();
    }
}
