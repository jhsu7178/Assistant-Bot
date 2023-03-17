package com.assistant.bot;

import com.assistant.bot.commands.*;
import com.assistant.bot.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Assistant {

   private final ShardManager shardManager;
   private final Dotenv config;

   /*
      Important Notes for bot design.
      First we use a ShardManager, this allows the bot to be funtional in different servers
      We then have a default builder (this is the bot), which takes in the TOKEN online

      Intents are important for various features when using eventlisteners, so be sure to enable them both online and
      in the declarations.

      Next we need to have a cache for some of it too. So we can cache member details for specific people (or ALL)
      and we also can filter it, so it doesn't do lazy loading. Note there is no reason to chunk when not caching
      NOTE if you make a list without cache and chunking, you won't get a full list without it.
    */
    public Assistant() throws InvalidTokenException {
        config = Dotenv.configure().load();
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(config.get("TOKEN"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Testing the bot"));
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL); // Very bad for bigger amounts of people.
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS);
        shardManager = builder.build();

        // Register Listeners
        shardManager.addEventListener(new EventListener());
        CommandManager manager = new CommandManager();

        manager.add(new WelcomeCommand());
        manager.add(new RolesCommand());
        manager.add(new SayCommand());
        manager.add(new EmoteCommand());
        manager.add(new GiveroleCommand());
        manager.add(new MemeCommand());

        shardManager.addEventListener(manager);

    }

    public Dotenv getConfig() {
        return config;
    }
    public static void main(String[] args) {
        try {
            Assistant bot = new Assistant();
        } catch (InvalidTokenException e) {
            System.out.println("ERROR: Provided bot token is invalid!!!");
        }
    }
}
