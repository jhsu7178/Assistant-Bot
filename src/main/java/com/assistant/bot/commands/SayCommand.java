package com.assistant.bot.commands;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.ArrayList;
import java.util.List;

//Command: /say <message> [channel]
public class SayCommand implements ICommand {
    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getDescription() {
        return "Make the bot say a message";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "message", "The messsage you want the bot to say", true));
        data.add(new OptionData(OptionType.CHANNEL, "channel", "The channel you want to sent the message in", false)
                .setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD));

        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping messageOption = event.getOption("message");
        String message = messageOption.getAsString();

        MessageChannel channel;
        OptionMapping channelOption = event.getOption("channel");
        if (channelOption != null) {
            channel = channelOption.getAsChannel().asGuildMessageChannel();
        } else {
            channel = event.getChannel();
        }

        channel.sendMessage(message).queue();
        event.reply("Your message was sent!").setEphemeral(true).queue();
    }
}
