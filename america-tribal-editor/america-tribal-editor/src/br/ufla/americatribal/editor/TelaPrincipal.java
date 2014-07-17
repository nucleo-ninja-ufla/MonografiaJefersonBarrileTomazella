package br.ufla.americatribal.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

public class TelaPrincipal implements Screen, InputProcessor,TextInputListener
{
	DesenhistaDaTelaPrincipal desenhista;
	Mapa mapa;
	public static int selecionado_chao = 0;
	public static int selecionado_objeto = 0;
	int desenhando = 0;
	
	int x_ant = 0;
	int y_ant = 0;
	int x_atu = 0;
	int y_atu = 0;
	
	boolean shift_pressionado = false;
	boolean control_pressionado = false;
	boolean movendo = false;
	static boolean caps_ligado = false;
	
	public static boolean botao1_objeto = false;
	
	public static int tribo_a = 0;
	public static int tribo_b = 2;
	public static int tribo_c = 1;
	
	public static String nomeMapa = "mapa-01";
	
	String entrada = "vazio";

	@Override
	public void show() {
		/* deixa na resolução da tela (praticamente tela cheia)
		Gdx.graphics.setDisplayMode(
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		*/
		
		mapa = new Mapa();
		desenhista = new DesenhistaDaTelaPrincipal(mapa);
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		desenhista.desenhar(selecionado_chao, selecionado_objeto);
	}
	
	
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT){
			mapa.x--;
		}else if(keycode == Keys.RIGHT){
			mapa.x++;
		}
		if (keycode == Keys.UP){
			mapa.y++;
		}else if(keycode == Keys.DOWN){
			mapa.y--;
		}
		if (keycode == Keys.NUM_0){
			mapa.x = 0;
			mapa.y = 0;
		}
		if (keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT){
			shift_pressionado = true;
		}
		
		if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT){
			control_pressionado = true;
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (shift_pressionado == caps_ligado){
			if (keycode == Keys.NUM_1){
				selecionado_chao = 0;
			}else if (keycode == Keys.NUM_2){
				selecionado_chao = 1;
			}else if (keycode == Keys.NUM_3){
				selecionado_chao = 2;
			}else if (keycode == Keys.NUM_4){
				selecionado_chao = 3;
			}else if (keycode == Keys.NUM_5){
				selecionado_chao = 4;
			}else if (keycode == Keys.NUM_6){
				selecionado_chao = 5;
			}
		}else{
			if (keycode == Keys.NUM_1){
				selecionado_objeto = 0;
			}else if (keycode == Keys.NUM_2){
				selecionado_objeto = 1;
			}else if (keycode == Keys.NUM_3){
				selecionado_objeto = 2;
			}else if (keycode == Keys.NUM_4){
				selecionado_objeto = 3;
			}else if (keycode == Keys.NUM_5){
				selecionado_objeto = 4;
			}else if (keycode == Keys.NUM_6){
				selecionado_objeto = 5;
			}else if (keycode == Keys.NUM_7){
				selecionado_objeto = 6;
			}
			else if (keycode == Keys.NUM_8){
				selecionado_objeto = 7;
			}else if (keycode == Keys.Q){
				selecionado_objeto = Conf.ALDEIA_A;
			}else if (keycode == Keys.W){
				selecionado_objeto = Conf.ALDEIA_B;
			}else if (keycode == Keys.E){
				selecionado_objeto = Conf.EXPLORADOR_A;
			}else if (keycode == Keys.R){
				selecionado_objeto = Conf.EXPLORADOR_B;
			}
		}
		
		if (shift_pressionado == false){
			if (keycode == Keys.PAGE_DOWN){
				if (tribo_a == 2)
					desenhista.trocarTribo(0, 0);
				else
					desenhista.trocarTribo(0, tribo_a + 1);
			}else if (keycode == Keys.PAGE_UP){
				if (tribo_a == 0)
					desenhista.trocarTribo(0, 2);
				else
					desenhista.trocarTribo(0, tribo_a - 1);
			}
		}else{
			if (keycode == Keys.PAGE_DOWN){
				if (tribo_b == 2)
					desenhista.trocarTribo(1, 0);
				else
					desenhista.trocarTribo(1, tribo_b + 1);
			}else if (keycode == Keys.PAGE_UP){
				if (tribo_b == 0)
					desenhista.trocarTribo(1, 2);
				else
					desenhista.trocarTribo(1, tribo_b - 1);
			}
		}
		// aspas e apóstrofo (em cima do tab)
		//if (keycode == 68){
		//	caps_ligado = !caps_ligado;
		//}
		
		if (keycode == Keys.TAB){
			botao1_objeto = !botao1_objeto;
			caps_ligado = !caps_ligado;
		}
		
		if (keycode == Keys.NUM_0){
			desenhista.recolocarMapa();
		}
		
		/*
		if (keycode == Keys.PLUS){
			Conf.tam_bloco_atual *= 2;
		}else if (keycode == Keys.MINUS){
			Conf.tam_bloco_atual *= 0.5;
		}
		*/
		
		if (keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT){
			shift_pressionado = false;
		}
		if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT){
			control_pressionado = false;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//Gdx.app.log("mouse", "botao "+button+", pointer "+pointer);
		x_ant = x_atu;
		y_ant = y_atu;
		x_atu = screenX;
		y_atu = screenY;
		
		desenhista.pegarMouse(screenX, screenY);
		
		if (control_pressionado == false){
			if (button == 0){
				desenhando = 1;
				desenhar();
			}else if (button == 1){
				desenhando = 2;
				desenhar();
			}else if (button == 2){
				movendo = true;
				desenhista.moverMapa(x_atu - x_ant, y_atu - y_ant);
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		desenhista.pegarMouse(screenX, screenY);
		desenhando = 0;
		desenhista.selecionarItemObjetos(button);
		
		if (button == 0){
			if (desenhista.botaoRenomearMapa()){
				entrada = "mapa-nome";
				Gdx.input.getTextInput(this, "Renomear Mapa", "mapa-");
			}else if (desenhista.botaoTamanhoX()){
				entrada = "mapa-x";
				Gdx.input.getTextInput(this, "Tamanho Horizontal do Mapa ("+
						Mapa.MIN_X+" a "+Mapa.MAX_X+")", ""+Mapa.MIN_X);
			}else if (desenhista.botaoTamanhoY()){
				entrada = "mapa-y";
				Gdx.input.getTextInput(this, "Tamanho Vertical do Mapa ("+
						Mapa.MIN_Y+" a "+Mapa.MAX_Y+")", ""+Mapa.MIN_Y);
			}else if (desenhista.botaoLimpar()){
				entrada = "mapa-limpar";
				Gdx.input.getTextInput(this, "Alterações não salvas do mapa"
						+ " atual serão perdidas",	"Apenas OK ou Cancelar");
			}else if (desenhista.botaoSalvar()){
				entrada = "mapa-salvar";
				Gdx.input.getTextInput(this, "Escolha o nome para"
						+ " salvar o mapa: ",nomeMapa);
			}else if (desenhista.botaoCarregar()){
				entrada = "mapa-carregar";
				Gdx.input.getTextInput(this, "Escolha o nome do mapa"
						+ " que quer carregar: ",	nomeMapa);
			}else if (desenhista.botaoVerificar()){
				entrada = "mapa-verificar";
				Gdx.input.getTextInput(this, "Verificar se o mapa é eficaz?",
						"Se existe caminho entre as tribos.");
			}
		}
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		x_ant = x_atu;
		y_ant = y_atu;
		x_atu = screenX;
		y_atu = screenY;
		
		desenhista.pegarMouse(screenX, screenY);
		
		if (control_pressionado == false){
			if (desenhando > 0){
				desenhar();
			}else if (movendo == true){
				desenhista.moverMapa(x_atu - x_ant, y_atu - y_ant);
			}
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		x_ant = x_atu;
		y_ant = y_atu;
		x_atu = screenX;
		y_atu = screenY;
		
		desenhista.pegarMouse(screenX, screenY);
		
		if (control_pressionado == true){
			desenhista.moverMapa(x_atu - x_ant, y_atu - y_ant);
		}
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		//Gdx.app.log("Scroll", "q: "+amount);
		if (amount == 1){
			if (shift_pressionado == !caps_ligado){
				selecionado_objeto++;
				if (selecionado_objeto > Conf.MAX_ITENS_OBJ - 1)
					selecionado_objeto = 0;
			}else{
				selecionado_chao++;
				if (selecionado_chao > Conf.MAX_ITENS_CHAO - 1)
					selecionado_chao = 0;
			}
		}
		if (amount == -1){
			if (shift_pressionado == !caps_ligado){
				selecionado_objeto--;
				if (selecionado_objeto < 0)
					selecionado_objeto = Conf.MAX_ITENS_OBJ - 1;
			}else{
				selecionado_chao--;
				if (selecionado_chao < 0 )
					selecionado_chao = Conf.MAX_ITENS_CHAO - 1;
			}
		}
		return true;
	}

	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private void desenhar(){
		// botao 1 pressionado
		if (desenhando == 1){
			if (botao1_objeto == false)
				desenhista.mudarMapaChao(selecionado_chao);
			else
				desenhista.mudarMapaObjeto(selecionado_objeto);
		}else if (desenhando == 2){
			if (botao1_objeto == false)
				desenhista.mudarMapaObjeto(selecionado_objeto);
			else
				desenhista.mudarMapaChao(selecionado_chao);
		}
		
	}

	public static void trocarSelecionado(boolean b1obj) {
		botao1_objeto = b1obj;
		caps_ligado = b1obj;
	}

	@Override
	public void input(String text) {
		if (entrada == "mapa-nome"){
			nomeMapa = new String(text);
		}else if (entrada == "mapa-x"){
			int novo_tamanho = new Integer(text);
			mapa.mudarTamanho(novo_tamanho, mapa.tam_y, false);
		}else if (entrada == "mapa-y"){
			int novo_tamanho = new Integer(text);
			mapa.mudarTamanho(mapa.tam_x, novo_tamanho, false);
		}else if (entrada == "mapa-limpar"){
			mapa.nome = nomeMapa;
			mapa.mudarTamanho(mapa.tam_x, mapa.tam_y, true);
			desenhista.recolocarMapa();
		}else if (entrada == "mapa-salvar"){
			mapa.nome = text;
			mapa.salvar();
			if (mapa.verificarEficacia()){
				mapa.nome = text;
				mapa.salvar();
				Gdx.app.log("MAPA", "Mapa salvo com sucesso!");
				desenhista.adicionarMensagem("Mapa \""+mapa.nome+"\" salvo.");
			}else{
				Gdx.app.log("MAPA", "Mapa salvo. NÃO eficaz.");
				desenhista.adicionarMensagem("Mapa \""+mapa.nome+"\" salvo,"
						+ " porém NÃO é eficaz.");
			}
		}else if (entrada == "mapa-carregar"){
			boolean carregou = mapa.carregar(text);
			if (carregou == true){
				Gdx.app.log("MAPA", "Mapa carregado com sucesso.");
				desenhista.adicionarMensagem("Mapa carregado com sucesso.");
			}else{
				Gdx.app.log("MAPA", "Mapa não pode ser carregado.");
				desenhista.adicionarMensagem("Mapa não pode ser carregado.");
			}
		}if (entrada == "mapa-verificar"){
			if (mapa.verificarEficacia()){
				Gdx.app.log("MAPA", "O mapa é eficaz!");
				desenhista.adicionarMensagem("O mapa é eficaz!");
			}else{
				Gdx.app.log("MAPA", "O mapa NÃO é eficaz.");
				desenhista.adicionarMensagem("O mapa NÃO é eficaz.");
			}
		}
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}
	
}
