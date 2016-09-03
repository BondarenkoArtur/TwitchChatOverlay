package com.uabart.Entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Timer;
import java.util.TimerTask;

public class ChatUser {
	public static final float RANDOM_MOVE_RADIUS = 1f;
	public static final float DEFAULT_MIN_X = 0f + 30f;
	public static final float DEFAULT_MAX_X = 640f - 30f;
	public static final float DEFAULT_MIN_Y = 0f + 30f;
	public static final float DEFAULT_MAX_Y = 360f - 30f;
	public static final String DEFAULT_TEXTURE_PATH = "user.png";

	private String userName;
	private Vector2 coords;
	private String lastMessage = "";
	private String texturePath = DEFAULT_TEXTURE_PATH;
	private Timer timer = new Timer();
	private TimerTask task;

	public ChatUser(String userName, float x, float y) {
		this.userName = userName;
		this.coords = new Vector2(x, y);
		setDefaultTexture();
	}

	public ChatUser(String userName) {
		this.userName = userName;
		this.coords = new Vector2(MathUtils.random(DEFAULT_MIN_X, DEFAULT_MAX_X), MathUtils.random(DEFAULT_MIN_Y, DEFAULT_MAX_Y));
		setDefaultTexture();
	}

	public String getUserName() {
		return userName;
	}

	public float getX() {
		return coords.x;
	}

	public float getY() {
		return coords.y;
	}

	public void randomMove() {
		randomMove(DEFAULT_MIN_X, DEFAULT_MAX_X, DEFAULT_MIN_Y, DEFAULT_MAX_Y, RANDOM_MOVE_RADIUS);
	}

	public void randomMove(float minX, float maxX, float minY, float maxY, float radius) {
		float newX = MathUtils.random(coords.x - radius, coords.x + radius);
		float newY = MathUtils.random(coords.y - radius, coords.y + radius);
		if (newX > maxX) {
			coords.x = maxX;
		} else if (newX < minX) {
			coords.x = minX;
		} else {
			coords.x = newX;
		}
		if (newY > maxY) {
			coords.y = maxY;
		} else if (newY < minY) {
			coords.y = minY;
		} else {
			coords.y = newY;
		}
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public void resetUser() {
		if (task != null) {
			task.cancel();
		}
		task = new TimerTask() {
			@Override
			public void run() {
				setLastMessage("");
				setDefaultTexture();
			}
		};
		timer.schedule(task, 30 * 1000);
	}

	private void setDefaultTexture() {
		setTexturePath(DEFAULT_TEXTURE_PATH);
	}

	public String getTexturePath() {
		return texturePath;
	}

	public void setTexturePath(String texturePath) {
		this.texturePath = texturePath;
	}
}
