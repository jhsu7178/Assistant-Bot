package com.assistant.bot.commands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.ArrayList;
import java.util.List;

// Command: /roles
public class RolesCommand implements ICommand {
    @Override
    public String getName() {
        return "roles";
    }

    @Override
    public String getDescription() {
        return "Display all roles on the server";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.deferReply().queue(); // This makes discord wait longer than 3 seconds in case your command takes a while (API Requests)
        String response = "";

        for (Role role : event.getGuild().getRoles()) {
            response += role.getAsMention() + "\n"; // @roleName
        }
        event.getHook().sendMessage(response).queue();  //setEmphemeral on deferReply and not getHook
    }
}
