package com.assistant.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.ArrayList;
import java.util.List;

//Command: /welcome
public class WelcomeCommand implements ICommand {
    @Override
    public String getName() {
        return "welcome";
    }
    @Override
    public String getDescription() {
        return "Sends a welcome message";
    }
    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        return data;
    }
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String userTag = event.getUser().getAsTag();
        event.reply("Welcome to the server, **" + userTag + "**!").setEphemeral(true).queue();  // This makes the message invisible
    }
}
