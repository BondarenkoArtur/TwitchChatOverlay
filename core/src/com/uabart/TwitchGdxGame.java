package com.uabart;

import com.badlogic.gdx.ApplicationAdapter;
import com.uabart.Helpers.IrcHelper;
import com.uabart.Helpers.RenderHelper;

import org.jibble.pircbot.User;

public class TwitchGdxGame extends ApplicationAdapter {

	String channel = null;
	RenderHelper render;
	IrcHelper ircHelper;

	public TwitchGdxGame(String channel) {
		this.channel = channel;
	}

	public TwitchGdxGame() {
	}

	@Override
	public void create() {
		render = new RenderHelper();
		ircHelper = new IrcHelper(channel) {
			@Override
			protected void onJoin(String channel, String sender, String login, String hostname) {
				super.onJoin(channel, sender, login, hostname);
				refreshUsers();
			}

			@Override
			protected void onPart(String channel, String sender, String login, String hostname) {
				super.onPart(channel, sender, login, hostname);
				refreshUsers();
			}

			@Override
			protected void onMessage(String channel, String sender, String login, String hostname, String message) {
				super.onMessage(channel, sender, login, hostname, message);
				render.renderMessage(sender, message);
			}

			@Override
			protected void onUserList(String channel, User[] users) {
				super.onUserList(channel, users);
				refreshUsers();
			}
		};
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	void refreshUsers() {
		render.refreshUsers(ircHelper.getUserList());
	}

	@Override
	public void render() {
		render.render();
	}

	@Override
	public void dispose() {
		render.dispose();
	}
}
