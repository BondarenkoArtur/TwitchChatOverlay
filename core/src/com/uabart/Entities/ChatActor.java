package com.uabart.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.uabart.Helpers.TextureGetter;

import java.util.Timer;
import java.util.TimerTask;

public class ChatActor extends Actor {

	public static final float RANDOM_MOVE_RADIUS = 20f;
	public static final float DEFAULT_MIN_X = 0f + 30f;
	public static final float DEFAULT_MAX_X = 640f - 30f;
	public static final float DEFAULT_MIN_Y = 0f + 30f;
	public static final float DEFAULT_MAX_Y = 360f - 30f;
	public static final String DEFAULT_TEXTURE_PATH = "user.png";

	private Color color;
	private String lastMessage = "";
	private TimerTask task;
	private Timer timer = new Timer();
	private String texturePath = DEFAULT_TEXTURE_PATH;

	public ChatActor() {
		this("Someone" + MathUtils.random(999));
	}

	public ChatActor(String userName) {
		color = getRandomNotGreenColor();
		this.setName(userName);
		this.setX(MathUtils.random(DEFAULT_MIN_X, DEFAULT_MAX_X));
		this.setY(MathUtils.random(DEFAULT_MIN_Y, DEFAULT_MAX_Y));
		setDefaultTexturePath();
	}

	private Color getRandomNotGreenColor() {
		Color randomColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1f);
		if (randomColor.g > 0.8f && randomColor.r < 0.4f && randomColor.b < 0.4f) {
			randomColor = getRandomNotGreenColor();
		}
		return randomColor;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		BitmapFont font = TextureGetter.getFont("terminus");
		GlyphLayout glyphLayout = new GlyphLayout();
		Texture userTexture = TextureGetter.getTexture(texturePath);

		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(userTexture, getX(), getY());

		glyphLayout.setText(font, getName());
		font.draw(batch, glyphLayout, getX() + userTexture.getWidth() / 2f - glyphLayout.width / 2f,
				getY() + userTexture.getHeight() + glyphLayout.height);
		if (!getLastMessage().equals("")) {
			glyphLayout.setText(font, getLastMessage());
			font.draw(batch, glyphLayout, getX() + userTexture.getWidth() / 2f - glyphLayout.width / 2f,
					getY() + glyphLayout.height / 4f);
		}
		moveSomewhere();
	}

	public void moveSomewhere() {
		float radius;
		if (MathUtils.randomBoolean(0.1f)) {
			radius = RANDOM_MOVE_RADIUS;
		} else {
			radius = 0f;
		}
		moveSomewhere(DEFAULT_MIN_X, DEFAULT_MAX_X, DEFAULT_MIN_Y, DEFAULT_MAX_Y, radius, 2f);
	}

	public void moveSomewhere(float minX, float maxX, float minY, float maxY, float radius, float duration) {
		try {
			getActions().get(0);
		} catch (Exception e) {
			float newX = MathUtils.random(getX() - radius, getX() + radius);
			float newY = MathUtils.random(getY() - radius, getY() + radius);
			if (newX > maxX) {
				newX = maxX;
			} else if (newX < minX) {
				newX = minX;
			}

			if (newY > maxY) {
				newY = maxY;
			} else if (newY < minY) {
				newY = minY;
			}
			MoveToAction action = new MoveToAction();
			action.setPosition(newX, newY);
			action.setDuration(duration);
			addAction(action);
		}
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		if (lastMessage.charAt(0) == '!') {
			executeCommand(lastMessage);
		} else {
			this.lastMessage = lastMessage;
		}
		if (lastMessage.contains("Kappa")) {
			setUserTexturePath("kappa.png");
		}
		resetUser();
	}

	private void executeCommand(String command) {
		// TODO: 9/3/16 executor for commands
	}

	public void resetUser() {
		if (task != null) {
			task.cancel();
		}
		task = new TimerTask() {
			@Override
			public void run() {
				setLastMessage("");
				setDefaultTexturePath();
			}
		};
		timer.schedule(task, 30 * 1000);
	}

	private void setDefaultTexturePath() {
		texturePath = DEFAULT_TEXTURE_PATH;
	}

	public String getUserTexturePath() {
		return texturePath;
	}

	public void setUserTexturePath(String texturePath) {
		this.texturePath = texturePath;
	}
}