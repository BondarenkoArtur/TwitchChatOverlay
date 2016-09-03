package com.uabart.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uabart.Entities.ChatActor;

import java.util.List;

public class RenderHelper {

	private final Stage stage;

	public RenderHelper() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
	}

	public void render() {
		Gdx.gl.glClearColor(0f, 1f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void renderMessage(String sender, String message) {
		boolean userFound = false;
		for (Actor actor : stage.getActors()) {
			if (sender.equals(actor.getName()) && actor instanceof ChatActor) {
				userFound = true;
				((ChatActor) actor).setLastMessage(message);
			}
		}
		if (!userFound) {
			ChatActor newActor = new ChatActor(sender);
			stage.addActor(newActor);
			newActor.setLastMessage(message);
		}
	}

	public void dispose() {
		stage.dispose();
	}

	public void refreshUsers(List<String> userList) {
		for (Actor actor : stage.getActors()) {
			if (!userList.contains(actor.getName())) {
				actor.remove();
			}
		}

		for (String userName : userList) {
			boolean isInList = false;
			for (Actor actor : stage.getActors()) {
				if (userName.equals(actor.getName())) {
					isInList = true;
				}
			}
			if (!isInList) {
				stage.addActor(new ChatActor(userName));
			}
		}
	}
}
