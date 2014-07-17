package br.ufla.americatribal.editor;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class DesenhistaDaTelaPrincipal {
	private Rectangle cenario;
	private Rectangle objetos;
	//private Rectangle outros;
	
	private SpriteBatch batch;
	private ShapeRenderer debugRender;
	
	private OrthographicCamera camera;
	
	// imagens
	private TextureRegion [] aldeia;
	private TextureRegion [] explorador;
	
	private TextureRegion [] cenario_chao;
	private TextureRegion [] cenario_objetos;
	
	private TextureRegion [] aura;
	
	//private TextureRegion [][] unidades;
	
	private Mapa mapa;
	
	private int xNoMapa = -1;
	private int yNoMapa = -1;
	
	private int mousex = 0;
	private int mousey = 0;
	
	private float deslocx = 0;
	private float deslocy = 0;
	
	BitmapFont font;
	
	// mensagens
	//private static final int maxMensagens = 5;
	private String mensagem = "";
	private static final int TEMPO_MAX_MENSAGEM = 200; 
	private int tempoMensagem = TEMPO_MAX_MENSAGEM + 1;
	//private int mensagemAtual = 0;
	
	// carregamento
	public DesenhistaDaTelaPrincipal(Mapa mapa){
		camera = new OrthographicCamera(Conf.CAM_TAM_LAR, Conf.CAM_TAM_ALT);
		camera.position.set(Conf.CAM_TAM_LAR * 0.5f, Conf.CAM_TAM_ALT * 0.5f, 0);
		camera.update();
		
		batch = new SpriteBatch();
		debugRender = new ShapeRenderer();
		
		this.mapa = mapa;
		
		carregarTexturas();
		
		font = new BitmapFont();
		font.setScale(0.03f);
		font.setUseIntegerPositions(false);
		
		
	}
	
	private void carregarTexturas(){
		criarAreas();
		
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal(Conf.PACOTE_IMAGENS));
		
		aldeia = new TextureRegion[3];
		aldeia[0] = atlas.findRegion("aldeia-norte");
		aldeia[1] = atlas.findRegion("aldeia-centro");
		aldeia[2] = atlas.findRegion("aldeia-sul");
		
		explorador = new TextureRegion[3];
		explorador[0] = atlas.findRegion("explorador-norte");
		explorador[1] = atlas.findRegion("explorador-centro");
		explorador[2] = atlas.findRegion("explorador-sul");
		
		aura = new TextureRegion[3];
		aura[0] = atlas.findRegion("vazio");
		aura[1] = atlas.findRegion("aura-time-a");
		aura[2] = atlas.findRegion("aura-time-b");
		
		cenario_chao = new TextureRegion[6];
		cenario_chao[0] = atlas.findRegion("cenario-grama");
		cenario_chao[1] = atlas.findRegion("cenario-areia");
		cenario_chao[2] = atlas.findRegion("cenario-terra");
		cenario_chao[3] = atlas.findRegion("cenario-agua");
		cenario_chao[4] = atlas.findRegion("cenario-pedra");
		cenario_chao[5] = atlas.findRegion("cenario-floresta");
		
		cenario_objetos = new TextureRegion[12];
		cenario_objetos[0] = atlas.findRegion("vazio");
		cenario_objetos[1] = atlas.findRegion("recurso-fazenda");
		cenario_objetos[2] = atlas.findRegion("recurso-floresta");
		cenario_objetos[3] = atlas.findRegion("recurso-mina");
		cenario_objetos[4] = atlas.findRegion("construcao-observatorio");
		cenario_objetos[5] = atlas.findRegion("objeto-carne");
		cenario_objetos[6] = atlas.findRegion("objeto-madeira");
		cenario_objetos[7] = atlas.findRegion("objeto-pedra");
		
		cenario_objetos[8] = aldeia[TelaPrincipal.tribo_a];
		cenario_objetos[9] = aldeia[TelaPrincipal.tribo_b];
		cenario_objetos[10]= explorador[TelaPrincipal.tribo_a];
		cenario_objetos[11]= explorador[TelaPrincipal.tribo_b];
		
		/*
		unidades = new TextureRegion[3][];
		unidades[0] = new TextureRegion[6];
		unidades[0][0] = atlas.findRegion("vazio");
		unidades[0][1] = atlas.findRegion("unidade-basico-norte");
		unidades[0][2] = atlas.findRegion("unidade-distancia-norte");
		unidades[0][3] = atlas.findRegion("unidade-animal-norte");
		unidades[0][4] = atlas.findRegion("unidade-mistico-norte");
		unidades[0][5] = atlas.findRegion("unidade-mitico-norte");
		
		unidades[1] = new TextureRegion[6];
		unidades[1][0] = atlas.findRegion("vazio");
		unidades[1][1] = atlas.findRegion("unidade-basico-centro");
		unidades[1][2] = atlas.findRegion("unidade-distancia-centro");
		unidades[1][3] = atlas.findRegion("unidade-animal-centro");
		unidades[1][4] = atlas.findRegion("unidade-mistico-centro");
		unidades[1][5] = atlas.findRegion("unidade-mitico-centro");
		
		unidades[2] = new TextureRegion[6];
		unidades[2][0] = atlas.findRegion("vazio");
		unidades[2][1] = atlas.findRegion("unidade-basico-sul");
		unidades[2][2] = atlas.findRegion("unidade-distancia-sul");
		unidades[2][3] = atlas.findRegion("unidade-animal-sul");
		unidades[2][4] = atlas.findRegion("unidade-mistico-sul");
		unidades[2][5] = atlas.findRegion("unidade-mitico-sul");
		*/
	}
	
	private void criarAreas(){
		float x, lar, y, alt;
		x = 0.1f;
		y = 0.1f;
		lar = Conf.CAM_TAM_LAR * 0.8f;
		alt = Conf.CAM_TAM_ALT - 0.2f;
		cenario = new Rectangle(x, y, lar, alt);
		
		x = Conf.CAM_TAM_LAR * 0.8f;
		y = 0.1f;
		lar = Conf.CAM_TAM_LAR * 0.2f - 0.1f;
		alt = Conf.CAM_TAM_ALT - 0.2f;
		objetos = new Rectangle(x, y, lar, alt);
	}

	
	// desenho
	public void desenhar(int sel_chao, int sel_obj){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			desenharChao();
			desenharObjetos();
		batch.end();
		desenharRetangulos();
		batch.begin();
			desenharInterface(sel_chao, sel_obj);
			pegarPosicaoNoMapa();
			desenharTexto();
		batch.end();
		desenharRetanguloSelecaoObjetos();
	}
	
	private void desenharTexto() {
		// mensagens
		mostrarMensagens();
		
		// nome do mapa
		float x = objetos.x + 0.2f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 0.2f;
		float ty = font.getCapHeight();
		
		font.setScale(0.025f);
		font.setColor(1, 1, 1, 1);
		font.draw(batch, "Mapa: ", x, y);
		
		x += 1.1f;
		font.setColor(0.9f, 0.9f, 0.5f, 1);
		font.draw(batch, TelaPrincipal.nomeMapa, x, y);
		
		y -= ty + 0.3f;
		x = objetos.x + 0.2f + 1.2f;
		font.setScale(0.02f);
		font.setColor(0,1,1,1);
		font.draw(batch, "Renomear Mapa", x, y);
		
		// tamanho
		x = objetos.x + 0.2f;
		y -= 1f;
		font.setFixedWidthGlyphs("0123456789");
		font.setColor(1,1,1,1);
		font.draw(batch, "Tamanho   hor:             ver:", x, y);
		
		x += 2.05f;
		font.setColor(1,1,0,1);
		font.draw(batch, (""+mapa.tam_x), x, y);
		
		x += 1.55f;
		font.setColor(1,1,0,1);
		font.draw(batch, (""+mapa.tam_y), x, y);
		
		// limpar mapa
		x = objetos.x + 0.2f + 1.35f;
		y -= 1.05f;
		font.setColor(1,0.6f,0.6f,1);
		font.draw(batch, "Novo Mapa", x, y);
		
		// salvar e carregar
		x = objetos.x + 0.2f;
		y -= 1.2f;
		
		x += 0.65f;
		font.setColor(1,1,0,0.7f);
		font.draw(batch, "Salvar", x, y);
		
		x += 2.15f;
		font.setColor(1,1,0,0.7f);
		font.draw(batch, "Carregar", x, y);
		
		// verificador de eficácia
		y -= 1.2f;
		x = objetos.x + 0.2f + 1.2f;
		font.setColor(1,1,1,0.7f);
		font.draw(batch, "Verificar Eficácia", x, y);
	}
	
	public boolean botaoVerificar(){
		float x = objetos.x + 0.2f + 0.7f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 1.2f - 1.2f - 1.1f - 2.1f;
		float tx = 3f;
		float ty = 0.5f;
		
		float mx = mousex * Conf.unidades_x;
		float my = mousey * Conf.unidades_y;
		
		if (mx >= x && mx <= x+tx && my >= y && my <= y + ty){
			return true;
		}
		return false;
	}
	
	public boolean botaoSalvar() {
		float x = objetos.x + 0.3f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 1.1f - 2.1f - 1.2f;
		float tx = 1.9f;
		float ty = 0.5f;
		
		float mx = mousex * Conf.unidades_x;
		float my = mousey * Conf.unidades_y;
		
		if (mx >= x && mx <= x+tx && my >= y && my <= y + ty){
			return true;
		}
		return false;
	}
	
	public boolean botaoCarregar() {
		float x = objetos.x + 0.3f + 2.3f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 1.1f - 2.1f - 1.2f;
		float tx = 1.9f;
		float ty = 0.5f;
		
		float mx = mousex * Conf.unidades_x;
		float my = mousey * Conf.unidades_y;
		
		if (mx >= x && mx <= x+tx && my >= y && my <= y + ty){
			return true;
		}
		
		return false;
	}
	
	public boolean botaoLimpar(){
		float x = objetos.x + 0.2f + 0.7f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 1.1f - 2.1f;
		float tx = 3f;
		float ty = 0.5f;
		
		float mx = mousex * Conf.unidades_x;
		float my = mousey * Conf.unidades_y;
		
		if (mx >= x && mx <= x+tx && my >= y && my <= y + ty){
			return true;
		}
		return false;
	}
	
	public boolean botaoTamanhoX(){
		float x = objetos.x + 1.55f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 2.15f;
		float ty = 0.5f;
		float tx = 1.2f;
		
		float mx = mousex * Conf.unidades_x;
		float my = mousey * Conf.unidades_y;
		
		if (mx >= x && mx <= x+tx && my >= y && my <= y + ty){
			return true;
		}
		return false;
	}
	
	public boolean botaoTamanhoY(){
		float x = objetos.x + 1.55f + 1.55f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 2.15f;
		float ty = 0.5f;
		float tx = 1.2f;
		
		float mx = mousex * Conf.unidades_x;
		float my = mousey * Conf.unidades_y;
		
		if (mx >= x && mx <= x+tx && my >= y && my <= y + ty){
			return true;
		}
		return false;
	}
	
	public boolean botaoRenomearMapa(){
		float x = objetos.x + 0.2f + 0.75f;
		float ty = 0.4f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 1.1f;
		float tx = 3;
		
		float mx = mousex * Conf.unidades_x;
		float my = mousey * Conf.unidades_y;
		
		
		if (mx >= x && mx <= x+tx && my >= y && my <= y + ty){
			return true;
		}
		return false;
	}

	private void desenharInterface(int sel_chao, int sel_obj){
		if (sel_chao < Conf.MAX_ITENS_CHAO && sel_chao >= 0){
			float x = objetos.x + 0.1f;
			float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO - 0.2f;
			batch.draw(cenario_chao[sel_chao], x, y, Conf.TAM_BLOCO,
					Conf.TAM_BLOCO);
		}
		
		if (sel_obj >= 0 && sel_obj < Conf.MAX_ITENS_OBJ){
			float x = objetos.x + 0.1f;
			float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 2 - 0.4f;
			batch.draw(cenario_objetos[sel_obj], x, y, Conf.TAM_BLOCO,
					Conf.TAM_BLOCO);
			
			if (sel_obj == Conf.ALDEIA_A || sel_obj == Conf.EXPLORADOR_A)
					batch.draw(aura[1], x, y,Conf.TAM_BLOCO,Conf.TAM_BLOCO);
			
			if (sel_obj == Conf.ALDEIA_B || sel_obj == Conf.EXPLORADOR_B)
				batch.draw(aura[2], x, y,Conf.TAM_BLOCO,Conf.TAM_BLOCO);
		}
		
		float tam = Conf.TAM_BLOCO * 0.45f;
		float x = objetos.x + 0.2f + Conf.TAM_BLOCO + 0.2f;
		float y = Conf.CAM_TAM_ALT - tam - 0.2f;
		for (int i = 0; i < 6; i++){
			batch.draw(cenario_chao[i],x + i * (tam+0.1f),y, tam, tam);
		}
		
		y = Conf.CAM_TAM_ALT - tam - 0.4f - Conf.TAM_BLOCO;
		for (int i = 0; i < 6; i++){
			batch.draw(cenario_objetos[i], x + i * (tam+0.1f),y, tam, tam);
		}
		y -= tam + 0.1f;
		for (int i = 0; i < 6; i++){
			batch.draw(cenario_objetos[i+6], x + i * (tam+0.1f),y, tam, tam);
		}
		batch.draw(aura[1], x + 2 * (tam+0.1f),y, tam, tam);
		batch.draw(aura[2], x + 3 * (tam+0.1f),y, tam, tam);
		batch.draw(aura[1], x + 4 * (tam+0.1f),y, tam, tam);
		batch.draw(aura[2], x + 5 * (tam+0.1f),y, tam, tam);
	}
	
	private void desenharChao(){
		for (int x = 0; x < mapa.tam_x; x++){
			for (int y = 0; y < mapa.tam_y; y++){
				if (x + mapa.x > -2 && x + mapa.x <
						cenario.x+cenario.width+mapa.tam_x + 1 &&
					y + mapa.y > -2 && y + mapa.y <
						cenario.y+cenario.height+mapa.tam_y+ 1)
				{
					TextureRegion chao = cenario_chao[mapa.chao[x][y]]; 
					batch.draw(chao, x + mapa.x + cenario.x + deslocx,
									y + mapa.y + cenario.y + deslocy,
							Conf.TAM_BLOCO, Conf.TAM_BLOCO);
				}
			}
		}
	}
	
	private void desenharObjetos(){
		for (int x = 0; x < mapa.tam_x; x++){
			for (int y = 0; y < mapa.tam_y; y++){
				if (x + mapa.x > -2 && x + mapa.x <
						cenario.x+cenario.width+mapa.tam_x + 1 &&
					y + mapa.y > -2 && y + mapa.y <
						cenario.y+cenario.height+mapa.tam_y+ 1)
				{
					if (mapa.objeto[x][y] > 0){
						TextureRegion obj = cenario_objetos[mapa.objeto[x][y]]; 
						batch.draw(obj, x + mapa.x + cenario.x + deslocx,
										y + mapa.y + cenario.y + deslocy,
								Conf.TAM_BLOCO, Conf.TAM_BLOCO);
						
						if (mapa.objeto[x][y] == Conf.ALDEIA_A ||
								mapa.objeto[x][y] == Conf.EXPLORADOR_A)
							batch.draw(aura[1], x + mapa.x + cenario.x + deslocx,
									y + mapa.y + cenario.y + deslocy,
									Conf.TAM_BLOCO, Conf.TAM_BLOCO);
						
						if (mapa.objeto[x][y] == Conf.ALDEIA_B ||
								mapa.objeto[x][y] == Conf.EXPLORADOR_B)
							batch.draw(aura[2], x + mapa.x + cenario.x + deslocx,
									y + mapa.y + cenario.y + deslocy,
									Conf.TAM_BLOCO, Conf.TAM_BLOCO);
					}
				}
			}
		}
	}
	
	public void mudarMapaChao(int sel){
		if (xNoMapa > -1 && yNoMapa > -1){
			mapa.mudarChao(xNoMapa, yNoMapa, sel);
		}
	}
	public void mudarMapaObjeto(int sel){
		if (xNoMapa > -1 && yNoMapa > -1){
			mapa.mudarObjeto(xNoMapa, yNoMapa, sel);
		}
	}
	
	private void desenharRetangulos(){
		debugRender.setProjectionMatrix(camera.combined);
		debugRender.begin(ShapeType.FilledRectangle);
			//debugRender.setColor(1, 0, 0, 1);
			//debugRender.filledRect(mapab.x,mapab.y,mapab.width,mapab.height);
			//debugRender.setColor(1, 1, 1, 1);
			//debugRender.filledRect(objetos.x-0.1f,objetos.y-0.1f,
			//		objetos.width+0.2f,objetos.height+0.2f);
			debugRender.setColor(0.2f, 0.2f, 0.4f, 1);
			debugRender.filledRect(objetos.x,objetos.y,objetos.width,objetos.height);
			//debugRender.setColor(0, 0, 1, 1);
			//debugRender.filledRect(outros.x,outros.y,outros.width,outros.height);
			
			// retangulos na parte de objetos
			{
				float tam = Conf.TAM_BLOCO * 0.45f;
				float x = objetos.x + 0.1f;
				float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 2 - 0.4f;
				debugRender.setColor(1, 1, 1, 1);
				debugRender.filledRect(x,y,Conf.TAM_BLOCO, Conf.TAM_BLOCO);
				
				x = objetos.x + 0.2f + Conf.TAM_BLOCO + 0.2f;
				y = Conf.CAM_TAM_ALT - tam - 0.4f - Conf.TAM_BLOCO;
				for (int i = 0; i < 6; i++){
					debugRender.filledRect(x + i * (tam+0.1f),y, tam, tam);
				}
				y -= tam + 0.1f;
				for (int i = 0; i < 6; i++){
					debugRender.filledRect(x + i * (tam+0.1f),y, tam, tam);
				}
			}
		debugRender.end();
		
		// retangulo de seleção
		if (xNoMapa > -1 && yNoMapa > -1){
			float x = xNoMapa + cenario.x + mapa.x + deslocx;
			float y = yNoMapa + cenario.y + mapa.y + deslocy;
			debugRender.begin(ShapeType.Rectangle);
				debugRender.setColor(1, 1, 1, 1);
				debugRender.rect(x, y, Conf.TAM_BLOCO, Conf.TAM_BLOCO);
				debugRender.setColor(0, 0, 0, 1);
				debugRender.rect(x+0.02f, y+0.02f,
						Conf.TAM_BLOCO-0.04f, Conf.TAM_BLOCO-0.04f);
			debugRender.end();
		}
		
		// desenhar botão de trocar nome mapa, de limpar mapa,
		// de salvar e carregar
		float x = objetos.x + 0.2f + 0.75f;
		float ty = 0.4f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 1.1f;
		float tx = 3;
		
		debugRender.begin(ShapeType.FilledRectangle);
			debugRender.setColor(0.3f, 0.8f, 0.2f, 1);
			debugRender.filledRect(x, y, tx, ty);
			debugRender.setColor(0.2f, 0.5f, 0.2f, 0.5f);
			debugRender.filledRect(x+0.03f, y+0.03f, tx-0.06f, ty-0.06f);
			
			// limpar mapa
			x = objetos.x + 0.2f + 0.7f;
			y -= 2.1f;
			tx = 3f;
			ty = 0.5f;
			debugRender.setColor(0.8f, 0.3f, 0.2f, 1);
			debugRender.filledRect(x, y, tx, ty);
			debugRender.setColor(0.5f, 0.2f, 0.2f, 0.5f);
			debugRender.filledRect(x+0.03f, y+0.03f, tx-0.06f, ty-0.06f);
			
			// carregar e salvar
			x = objetos.x + 0.3f;
			y -= 1.2f;
			tx = 1.9f;
			ty = 0.5f;
			
			debugRender.setColor(0.3f, 0.8f, 0.2f, 1);
			debugRender.filledRect(x, y, tx, ty);
			debugRender.setColor(0.2f, 0.5f, 0.2f, 0.5f);
			debugRender.filledRect(x+0.03f, y+0.03f, tx-0.06f, ty-0.06f);
			
			x += 2.3f;
			debugRender.setColor(0.2f, 0.8f, 0.8f, 1);
			debugRender.filledRect(x, y, tx, ty);
			debugRender.setColor(0.2f, 0.5f, 0.5f, 0.5f);
			debugRender.filledRect(x+0.03f, y+0.03f, tx-0.06f, ty-0.06f);
			
			// eficacia
			x = objetos.x + 0.2f + 0.7f;
			y -= 1.2f;
			tx = 3f;
			ty = 0.5f;
			debugRender.setColor(0.8f, 0.8f, 0.2f, 1);
			debugRender.filledRect(x, y, tx, ty);
			debugRender.setColor(0.5f, 0.5f, 0.2f, 0.5f);
			debugRender.filledRect(x+0.03f, y+0.03f, tx-0.06f, ty-0.06f);
			
		debugRender.end();
		
		// desenhar botões de tamanho
		x = objetos.x + 1.55f;
		y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 3 - 2.135f;
		ty = 0.5f;
		tx = 1.2f;
		debugRender.begin(ShapeType.Rectangle);
			debugRender.setColor(0,1,0.5f,1);
			debugRender.rect(x,y,tx,ty);
			debugRender.rect(x+1.55f,y,tx,ty);
		debugRender.end();
		
	}
	
	private void desenharRetanguloSelecaoObjetos(){
		float x = objetos.x + 0.1f;
		float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO - 0.2f;
		
		if (TelaPrincipal.botao1_objeto == true)
			y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 2 - 0.4f;
		
		debugRender.begin(ShapeType.FilledRectangle);
			debugRender.setColor(0, 0, 0, 1);
			debugRender.filledRect(0, 0, Conf.CAM_TAM_LAR, 0.1f);
			debugRender.filledRect(0, Conf.CAM_TAM_ALT-0.1f, Conf.CAM_TAM_LAR, 0.1f);
			debugRender.filledRect(0, 0, 0.1f, Conf.CAM_TAM_ALT);
			debugRender.filledRect(Conf.CAM_TAM_LAR-0.1f, 0, 0.1f, Conf.CAM_TAM_ALT);
			debugRender.filledRect(objetos.x, 0, 0.1f, Conf.CAM_TAM_ALT);
		debugRender.end();
		
		debugRender.begin(ShapeType.Rectangle);
			debugRender.setColor(1, 0, 0, 1);
			debugRender.rect(x, y, Conf.TAM_BLOCO, Conf.TAM_BLOCO);
			debugRender.setColor(0, 0, 0, 1);
			debugRender.rect(x+0.02f, y+0.02f,
					Conf.TAM_BLOCO-0.04f, Conf.TAM_BLOCO-0.04f);
			debugRender.setColor(1, 1, 0, 1);
			debugRender.rect(x+0.04f, y+0.04f,
					Conf.TAM_BLOCO-0.08f, Conf.TAM_BLOCO-0.08f);
		debugRender.end();
	}

	private void pegarPosicaoNoMapa() {
		xNoMapa = -1;
		yNoMapa = -1;
		if (mousex * Conf.unidades_x >= cenario.x &&
			mousex * Conf.unidades_x < cenario.x + cenario.width &&
			mousey * Conf.unidades_y >= cenario.y &&
			mousey * Conf.unidades_y < cenario.y + cenario.height)
		{
			xNoMapa = (int) Math.floor(mousex * Conf.unidades_x -
					mapa.x - cenario.x - deslocx);
			yNoMapa = (int) Math.floor(mousey * Conf.unidades_y -
					mapa.y - cenario.y - deslocy);
			
			if (xNoMapa >= mapa.tam_x || xNoMapa < 0){
				xNoMapa = -1;
				yNoMapa = -1;
			}
			if (yNoMapa >= mapa.tam_y || yNoMapa < 0){
				xNoMapa = -1;
				yNoMapa = -1;
			}
			
			//Gdx.app.log("Coisa", "x: "+xNoMapa+ ", y: "+yNoMapa);
		}
	}
	
	public void pegarMouse(int sx, int sy){
		mousex = sx;
		mousey = Conf.TELA_RES_ALT - sy;
	}

	public void moverMapa(int dx, int dy) {
		float desx = dx * Conf.unidades_x;
		float desy = dy * Conf.unidades_y;
		
		deslocx += desx;
		deslocy -= desy;
		
		if (deslocx >= Conf.TAM_BLOCO){
			deslocx = 0;
			mapa.x++;
		}
		if (deslocx <= -Conf.TAM_BLOCO){
			deslocx = 0;
			mapa.x--;
		}
		
		if (deslocy >= Conf.TAM_BLOCO){
			deslocy = 0;
			mapa.y++;
		}
		if (deslocy <= -Conf.TAM_BLOCO){
			deslocy = 0;
			mapa.y--;
		}
	}

	public void recolocarMapa() {
		mapa.x = 0;
		mapa.y = 0;
		deslocx = 0;
		deslocy = 0;
	}
	
	public void selecionarItemObjetos(int botao){
		float mx = mousex * Conf.unidades_x; 
		float my = mousey * Conf.unidades_y;
		if (mx >= objetos.x &&
			mx < objetos.x + objetos.width &&
			my >= objetos.y &&
			my < objetos.y + objetos.height)
		{
			float x = objetos.x + 0.1f;
			float y = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO - 0.2f;
			float y2 = Conf.CAM_TAM_ALT - Conf.TAM_BLOCO * 2 - 0.4f;
			
			if (mx >= x && mx <= x + Conf.TAM_BLOCO && my >= y &&
				my <= y + Conf.TAM_BLOCO)
			{
				TelaPrincipal.trocarSelecionado(false);
				return;
			}
			else if (mx >= x && mx <= x + Conf.TAM_BLOCO && my >= y2 &&
						my <= y2 + Conf.TAM_BLOCO)
			{
				TelaPrincipal.trocarSelecionado(true);
				return;
			} 
			
			float tam = Conf.TAM_BLOCO * 0.45f;
			x = objetos.x + 0.2f + Conf.TAM_BLOCO + 0.2f;
			y = Conf.CAM_TAM_ALT - tam - 0.2f;
			for (int i = 0; i < 6; i++){
				float px = x + i * (tam+0.1f);
				if (mx >= px && mx <= px + tam && my >= y && my <= y + tam){
					TelaPrincipal.selecionado_chao = i;
					if (botao == 0)
						TelaPrincipal.trocarSelecionado(false);
					return;
				}
			}
			
			y = Conf.CAM_TAM_ALT - tam - 0.4f - Conf.TAM_BLOCO;
			for (int i = 0; i < 6; i++){
				float px = x + i * (tam+0.1f); 
				if (mx >= px && mx < px + tam && my >= y && my < y + tam){
					TelaPrincipal.selecionado_objeto = i;
					if (botao == 0)
						TelaPrincipal.trocarSelecionado(true);
					return;
				}
			}
			y -= tam + 0.1f;
			for (int i = 0; i < 6; i++){
				float px = x + i * (tam+0.1f); 
				if (mx >= px && mx < px + tam && my >= y && my < y + tam){
					TelaPrincipal.selecionado_objeto = i + 6;
					if (botao == 0)
						TelaPrincipal.trocarSelecionado(true);
					return;
				}
			}
		}
	}
	
	public void trocarTribo(int tribo, int novaTribo){
		if (tribo == 0){
			if (novaTribo == TelaPrincipal.tribo_a)
				return;
			if (novaTribo != TelaPrincipal.tribo_b){
				TelaPrincipal.tribo_c = TelaPrincipal.tribo_a;
				TelaPrincipal.tribo_a = novaTribo;
			}else{
				TelaPrincipal.tribo_b = TelaPrincipal.tribo_c;
				TelaPrincipal.tribo_c = TelaPrincipal.tribo_a;
				TelaPrincipal.tribo_a = novaTribo;
			}
		}else if (tribo == 1){
			if (novaTribo == TelaPrincipal.tribo_b)
				return;
			if (novaTribo != TelaPrincipal.tribo_a){
				TelaPrincipal.tribo_c = TelaPrincipal.tribo_b;
				TelaPrincipal.tribo_b = novaTribo;
			}else{
				TelaPrincipal.tribo_a = TelaPrincipal.tribo_c;
				TelaPrincipal.tribo_c = TelaPrincipal.tribo_b;
				TelaPrincipal.tribo_b = novaTribo;
			}
		}
		
		cenario_objetos[Conf.ALDEIA_A] = aldeia[TelaPrincipal.tribo_a];
		cenario_objetos[Conf.ALDEIA_B] = aldeia[TelaPrincipal.tribo_b];
		cenario_objetos[Conf.EXPLORADOR_A] = explorador[TelaPrincipal.tribo_a];
		cenario_objetos[Conf.EXPLORADOR_B] = explorador[TelaPrincipal.tribo_b];
		
	}

	public void adicionarMensagem(String mensagem){
		this.mensagem = mensagem;
		tempoMensagem = 0;
		
		String saida = ""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		saida += "/"+Calendar.getInstance().get(Calendar.MONTH);
		saida += "/"+Calendar.getInstance().get(Calendar.YEAR);
		saida += " - "+Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		saida += ":"+Calendar.getInstance().get(Calendar.MINUTE);
		saida += ":"+Calendar.getInstance().get(Calendar.SECOND);
		saida += " - "+mensagem+"\n";
		
		Gdx.app.log("teste",saida);
		
		FileHandle log = Gdx.files.local("log/mensagens.txt");
		log.writeString(saida, true);
	}
	
	private void mostrarMensagens(){
		if (tempoMensagem < TEMPO_MAX_MENSAGEM){
			tempoMensagem++;
			
			float xx = 0.2f;
			float yy = Conf.CAM_TAM_ALT - 0.3f;
			
			font.setScale(0.025f);
			font.setColor(1, 1, 1, 1);
			font.draw(batch, mensagem, xx, yy);
		}
	}
}
