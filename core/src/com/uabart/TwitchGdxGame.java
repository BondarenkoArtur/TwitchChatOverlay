package com.uabart;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.uabart.Helpers.IrcHelper;
import com.uabart.Helpers.RenderHelper;

import org.jibble.pircbot.User;

import java.util.List;


public class TwitchGdxGame extends ApplicationAdapter {

	RenderHelper render;
	IrcHelper ircHelper;
	List<String> userList;

	@Override
	public void create() {
		render = new RenderHelper();
		ircHelper = new IrcHelper(){
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

	void refreshUsers(){
		render.refreshUsers(ircHelper.getUserList());
	}

	@Override
	public void render() {
		render.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		render.dispose();
	}
}
