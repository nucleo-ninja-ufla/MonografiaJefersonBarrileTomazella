package br.ufla.americatribal.editor;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title		= "Editor de Níveis - America Tribal";
		cfg.useGL20 	= true;
		cfg.width		= Conf.TELA_RES_LAR;
		cfg.height		= Conf.TELA_RES_ALT;
		cfg.fullscreen	= false;
		//cfg.resizable	= false;
		
		new LwjglApplication(new Editor(), cfg);
	}
}
