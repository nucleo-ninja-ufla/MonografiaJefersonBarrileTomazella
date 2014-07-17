package br.ufla.americatribal.editor;

import com.badlogic.gdx.Game;

public class Editor extends Game {

	@Override
	public void create() {
		setScreen(new TelaPrincipal());
	}
}