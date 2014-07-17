package br.ufla.americatribal.editor;

import com.badlogic.gdx.Gdx;

public class Conf {
	public static final int TELA_RES_LAR = 1280;
	public static final int TELA_RES_ALT = 720;
	
	public static final float CAM_TAM_LAR = 24.0f;
	public static final float CAM_TAM_ALT = 13.5f;
	
	public static float unidades_x = CAM_TAM_LAR / TELA_RES_LAR;
	public static float unidades_y = CAM_TAM_ALT / TELA_RES_ALT;
	
	public static final float TAM_BLOCO = 1f;
	public static float tam_bloco_atual = TAM_BLOCO;
	
	public static final String PACOTE_IMAGENS =
			"data/imagens/america-tribal-editor-imagens.pack";
	
	public static final int MAX_ITENS_CHAO = 6;
	public static final int MAX_ITENS_OBJ = 12;

	
	public static final int ALDEIA_A = 8;
	public static final int ALDEIA_B = 9;
	public static final int EXPLORADOR_A = 10;
	public static final int EXPLORADOR_B = 11;
	
	public static void alterarUnidades(){
		unidades_x = CAM_TAM_LAR / Gdx.graphics.getWidth();
		unidades_y = CAM_TAM_ALT / Gdx.graphics.getHeight();
	}
	
	
	public static final String CAMINHO = "niveis/";
	public static final String EXTENSAO = ".atm";
}
