package com.assistant.bot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

//Command: /giverole <user> <role>
public class GiveroleCommand implements ICommand {
    @Override
    public String getName() {
        return "giverole";
    }

    @Override
    public String getDescription() {
        return "Give a user a role";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "The user to give the role to", true));
        data.add(new OptionData(OptionType.ROLE, "role", "The role to be given", true));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getOption("user").getAsMember();
        Role role = event.getOption("role").getAsRole();

        int maxRolePosition = 0;
        List<Role> userRoles = event.getMember().getRoles();
        for (Role roles : userRoles) {
            if (roles.getPosition() > maxRolePosition) {
                maxRolePosition = roles.getPosition();
            }
        }

        if (maxRolePosition > role.getPosition()) {
            event.getGuild().addRoleToMember(member, role).queue();
            event.reply(member.getAsMention() + " has been given " + role.getAsMention() + " role!").queue();
        } else {
            event.reply("**Cannot give role higher than your own!**").queue();
        }
    }
}
