package com.uabart.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uabart.Entities.ChatUser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RenderHelper {

	SpriteBatch batch;
	OrthographicCamera camera;
	BitmapFont font;
	ArrayList<ChatUser> chatUsers = new ArrayList<ChatUser>();

	public RenderHelper() {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("terminus.fnt"), new TextureRegion(new Texture(Gdx.files.internal("terminus.png"))), false);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 360);
	}

	public void render(float delta) {
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0f, 1f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		for (ChatUser user : chatUsers) {
			Texture userTexture = TextureGetter.getTexture(user.getTexturePath());
			batch.draw(userTexture, user.getX() - userTexture.getWidth() / 2f, user.getY() - userTexture.getHeight() / 2f);

			GlyphLayout glyphLayout = new GlyphLayout();
			glyphLayout.setText(font, user.getUserName());
			font.draw(batch, glyphLayout, user.getX() - glyphLayout.width / 2f, user.getY() + userTexture.getHeight() + glyphLayout.height / 2f);
			if (!user.getLastMessage().equals("")) {
				glyphLayout.setText(font, user.getLastMessage());
				font.draw(batch, user.getLastMessage(), user.getX() - glyphLayout.width / 2f, user.getY() - glyphLayout.height / 4f);
			}
			user.randomMove();
		}
		batch.end();
	}

	public void renderMessage(String sender, String message) {
		boolean userFound = false;
		for (final ChatUser user : chatUsers) {
			if (sender.equals(user.getUserName())) {
				userFound = true;
				if (message.contains("Kappa")) {
					user.setTexturePath("kappa.png");
				}
				user.setLastMessage(message);
				user.resetUser();
			}
		}
		if (!userFound) {
			chatUsers.add(new ChatUser(sender));
			renderMessage(sender, message);
		}
	}

	public void dispose() {
		batch.dispose();
	}

	public void refreshUsers(List<String> userList) {
		for (Iterator<ChatUser> iterator = chatUsers.iterator(); iterator.hasNext(); ) {
			ChatUser user = iterator.next();
			if (!userList.contains(user.getUserName())) {
				iterator.remove();
			}
		}

		for (String userName : userList) {
			boolean isInList = false;
			for (ChatUser user : chatUsers) {
				if (userName.equals(user.getUserName())) {
					isInList = true;
				}
			}
			if (!isInList) {
				chatUsers.add(new ChatUser(userName));
			}
		}
	}
}
