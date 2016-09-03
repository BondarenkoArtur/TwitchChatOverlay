package com.uabart.Helpers;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IrcHelper extends PircBot {

	public static final String CHANNEL = "#uabart";
	public static final String BOT_NAME = "uaBArtBot";
	public static final String BOT_OAUTH = "oauth:9ldmg9z1p0uesld6gpa0cqn3i2btkt";
	public static final boolean IS_DEBUG_MODE = true;

	public IrcHelper() {

		// debugging output
		this.setVerbose(IS_DEBUG_MODE);

		this.setName(BOT_NAME);
		// Connect to the IRC server.
		try {
			this.connect("irc.twitch.tv", 6667, BOT_OAUTH);
			this.sendRawLine("CAP REQ :twitch.tv/membership");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}

		// Join the #uabart channel.
		this.joinChannel(CHANNEL);
	}

	public List<String> getUserList() {
		User[] users = getUsers(CHANNEL);
		List<String> list = new ArrayList<String>();
		for (User user : users) {
			list.add(user.getNick());
		}
		return list;
	}


	//	@Override
//	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
//		if (message.equalsIgnoreCase("time")) {
//			String time = new java.util.Date().toString();
//			sendMessage(channel, sender + ": The time is now " + time);
//		}
//		if (message.equalsIgnoreCase("create")) {
//			game.people.addPers(sender);
//		}
//	}
}