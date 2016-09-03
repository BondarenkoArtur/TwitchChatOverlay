package com.uabart.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class TextureGetter {

	static HashMap<String, Texture> textureHashMap = new HashMap<String, Texture>();
	static HashMap<String, BitmapFont> fontHashMap = new HashMap<String, BitmapFont>();

	public static Texture getTexture(String path) {
		if (textureHashMap.containsKey(path)) {
			return textureHashMap.get(path);
		} else {
			Texture newTexture = new Texture(path);
			textureHashMap.put(path, newTexture);
			return newTexture;
		}
	}

	public static BitmapFont getFont(String path) {
		if (path == null || path.equalsIgnoreCase("default")) {
			return new BitmapFont();
		}
		if (fontHashMap.containsKey(path)) {
			return fontHashMap.get(path);
		} else {
			BitmapFont newFont = new BitmapFont(Gdx.files.internal(path + ".fnt"), new TextureRegion(new Texture(Gdx.files.internal(path + ".png"))), false);
			fontHashMap.put(path, newFont);
			return newFont;
		}
	}

}
