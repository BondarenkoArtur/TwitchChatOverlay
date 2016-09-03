package com.uabart.Helpers;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class IrcHelper extends PircBot {

	public static final String CHANNEL = "#uabart";
	public static final String BOT_NAME = "uaBArtBot";
	public static final String BOT_OAUTH = "oauth:9ldmg9z1p0uesld6gpa0cqn3i2btkt";
	public static final boolean IS_DEBUG_MODE = true;
	private final String tempChannel;

	public IrcHelper() {
		this(CHANNEL);
	}

	public IrcHelper(String channel) {
		this.tempChannel = channel;
		this.setVerbose(IS_DEBUG_MODE);

		this.setName(BOT_NAME);
		try {
			this.setEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			this.connect("irc.twitch.tv", 6667, BOT_OAUTH);
			this.sendRawLine("CAP REQ :twitch.tv/membership");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}

		if (tempChannel != null) {
			this.joinChannel(tempChannel);
		} else {
			this.joinChannel(CHANNEL);
		}
	}

	public List<String> getUserList() {
		User[] users;
		if (tempChannel != null) {
			users = getUsers(tempChannel);
		} else {
			users = getUsers(CHANNEL);
		}
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