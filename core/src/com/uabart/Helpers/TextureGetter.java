package com.uabart.Helpers;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureGetter {

	static HashMap<String, Texture> textureHashMap = new HashMap<String, Texture>();

	public static Texture getTexture(String path){
		if (textureHashMap.containsKey(path)) {
			return textureHashMap.get(path);
		} else {
			Texture newTexture = new Texture(path);
			textureHashMap.put(path, newTexture);
			return newTexture;
		}
	}

}
