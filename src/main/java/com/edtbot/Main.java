package com.edtbot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

/**
 * Created by Ivan on 24/09/2018.
 *
 * @author Ivan
 */
public class Main {

    public static void main(String[] args) {
        IDiscordClient client = new ClientBuilder()
                .withToken(System.getenv("DISCORD_TOKEN"))
                .build();

        client.getDispatcher().registerListener(new Handler(client));
        client.login();
    }

}
