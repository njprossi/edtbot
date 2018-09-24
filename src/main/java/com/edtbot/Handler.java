package com.edtbot;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.Calendar;

/**
 * Created by Ivan on 24/09/2018.
 *
 * @author Ivan
 */
public class Handler implements IListener {
    public static final String COMMAND_PREFIX = "edt ";
    public static final String ADE_ROOT_URL = "https://edt.grenoble-inp.fr/"+Utils.getSchoolYear()+"/exterieur";
    public static final int ADE_PROJECT_ID = 7;

    private IDiscordClient client;

    public Handler(IDiscordClient client) {
        this.client = client;
    }

    @Override
    public void handle(Event evt) {
        if (evt instanceof MessageReceivedEvent) {
            MessageReceivedEvent e = (MessageReceivedEvent) evt;
            if (e.getMessage().getContent().startsWith(COMMAND_PREFIX)) commmandHandler(e);
        }
    }

    private void commmandHandler(MessageReceivedEvent e) {
        String command = e.getMessage().getContent().substring(COMMAND_PREFIX.length());
        String[] args = command.split(" ");
        if (args.length<1) return;
        if (args[0].equals("week")) {
            EmbedObject msg = new EmbedBuilder()
                    .withTitle("Emploi du temps de la semaine "+Calendar.getInstance().get(Calendar.WEEK_OF_YEAR))
                    .withUrl(Utils.getWeekURL(ADE_ROOT_URL, ADE_PROJECT_ID, System.getenv("EDT_GROUP")))
                    .withColor(Color.WHITE)
                    .build();
            e.getChannel().sendMessage(msg);
        } else if (args[0].equals("image")) {
            if (args.length<2) return;
            String imgURL = Utils.getWeekImageURL(
                    ADE_ROOT_URL,
                    args[1],
                    ADE_PROJECT_ID,
                    Utils.getADEWeekID(),
                    new int[]{13942,13943},
                    1500,
                    800
            );
            EmbedObject msg = new EmbedBuilder()
                    .withTitle("Emploi du temps de la semaine "+Calendar.getInstance().get(Calendar.WEEK_OF_YEAR))
                    .withUrl(Utils.getWeekURL(ADE_ROOT_URL, ADE_PROJECT_ID, System.getenv("EDT_GROUP")))
                    .withImage(imgURL)
                    .withColor(Color.WHITE)
                    .build();
            e.getChannel().sendMessage(msg);
        } else {
            StringBuilder msg = new StringBuilder("**Commande inconnue !**\n\nCommandes disponibles :\n");
            msg.append("`").append(COMMAND_PREFIX).append("week`\n");
            msg.append("`").append(COMMAND_PREFIX).append("image <TOKEN>`\n");
            e.getChannel().sendMessage(msg.toString());
        }
    }
}
