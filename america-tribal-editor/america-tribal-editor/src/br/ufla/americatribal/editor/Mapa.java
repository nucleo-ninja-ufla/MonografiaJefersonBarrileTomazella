package br.ufla.americatribal.editor;

import br.ufla.americatribal.editor.aestrela.AEstrela;
import br.ufla.americatribal.editor.aestrela.Lista;
import br.ufla.americatribal.editor.aestrela.Ponto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Mapa {
	
	/*	valores
		chao:
			0: grama
			1: areia
			2: terra
			3: agua
			4: pedra
			5: floresta
		
		objeto:
			0 : vazio
			1 : observatorio
			2 : fazenda
			3 : floresta
			4 : mina
			5 : carne
			6 : madeira
			7 : pedra
			8 : aldeia time 1
			9 : aldeia time 2
			10: explorador time 1
			11: explorador time 2
	*/
	
	public static final int MIN_X = 13;
	public static final int MIN_Y = 7;
	public static final int MAX_X = 30;
	public static final int MAX_Y = 30;
	
	public String nome = "mapa-01";
	
	public int tam_x = MIN_X;
	public int tam_y = MIN_Y;
	
	public int x = 0;
	public int y = 0;
	
	public int [][] chao;
	public int [][] objeto;
	
	/* Mapa com uma camada apenas, para o A*
	 * 
	 * valor das células:
	 * 
	 * 0	: passável, sem nada em cima
	 * 1	: bloqueio fixo
	 * 2	: aldeia do jogador
	 * 3	: aldeia inimiga
	 * 4	: explorador do jogador
	 * 5	: explorador inimigo
	 * 6	: recursos coletáveis (carne)
	 * 7	: recursos coletáveis (madeira)
	 * 9	: recursos coletáveis (pedra)
	 * 10	: consumíveis (carne)
	 * 11	: consumíveis (madeira)
	 * 12	: consumíveis (pedra)
	 * 13	: observatório
	 * 14	: unidades inimigas (para ambos os times) *não-utilizado-ainda
	 * 
	 */
	//public static final int CEL_PASSAVEL = 0;
	public int [][] passavel;
	public int [][] geral;
	
	
	public Mapa(){
		mudarTamanho(tam_x, tam_y, true);
	}
	
	public Mapa(int tx, int ty){
		mudarTamanho(tx, ty, true);
	}
	
	public void mudarTamanho(int tx, int ty, boolean alterar){
		if (tx < MIN_X)
			tx = MIN_X;
		if (ty < MIN_Y)
			ty = MIN_Y;
		
		if (tx > MAX_X)
			tx = MAX_X;
		if (ty > MAX_Y)
			ty = MAX_Y;
		
		if (tx < tam_x || ty < tam_y)
			alterar = true;
		
		int [][] chaoantigo		= chao;
		int [][] objetoantigo	= objeto;
		
		chao = new int[tx][ty];
		objeto = new int[tx][ty];
		
		if (alterar == false){
			for (int x = 0; x < tam_x; x++){
				for (int y = 0; y < tam_y; y++){
					chao[x][y] = chaoantigo[x][y];
					objeto[x][y] = objetoantigo[x][y];
				}
			}
			for (int x = tam_x; x < tx; x++){
				for (int y = 0; y < ty; y++){
					chao[x][y] = 0;
					objeto[x][y] = 0;
				}
			}
		}
		
		tam_x = tx;
		tam_y = ty;
		
		if (alterar == true){
			for (int x = 0; x < tam_x; x++){
				for (int y = 0; y < tam_y; y++){
					chao[x][y] = 0;
					objeto[x][y] = 0;
				}
			}
			// time 1, aldeia e explorador
			objeto[1][1] = Conf.ALDEIA_A;
			objeto[2][1] = Conf.EXPLORADOR_A;
			// time 2, aldeia e explorador
			objeto[tam_x-2][tam_y-2] = Conf.ALDEIA_B;
			objeto[tam_x-3][tam_y-2] = Conf.EXPLORADOR_B;
		}
	}
	
	public void mudarChao(int x, int y, int tile){
		if (x >= 0 && x < tam_x && y >= 0 && y < tam_y)
			chao[x][y] = tile;
	}
	
	public void mudarObjeto(int x, int y, int obj){
		if (x >= 0 && x < tam_x && y >= 0 && y < tam_y){
			if (objeto[x][y] != Conf.ALDEIA_A &&
				objeto[x][y] != Conf.ALDEIA_B &&
				objeto[x][y] != Conf.EXPLORADOR_A &&
				objeto[x][y] != Conf.EXPLORADOR_B)
			{
				if (obj == Conf.ALDEIA_A){
					remover(Conf.ALDEIA_A);
				}
				if (obj == Conf.ALDEIA_B){
					remover(Conf.ALDEIA_B);
				}
				if (obj == Conf.EXPLORADOR_A){
					remover(Conf.EXPLORADOR_A);
				}
				if (obj == Conf.EXPLORADOR_B){
					remover(Conf.EXPLORADOR_B);
				}
				
				objeto[x][y] = obj;
			}
		}
	}
	
	private void remover(int obj){
		for (int x = 0; x < tam_x; x++){
			for (int y = 0; y < tam_y; y++){
				if (objeto[x][y] == obj)
					objeto[x][y] = 0;
			}
		}
	}
	
	public void salvar(){
		FileHandle arquivo = Gdx.files.local(Conf.CAMINHO + nome + Conf.EXTENSAO);
		StringBuilder conteudo = new StringBuilder();
		
		conteudo.append("Nível America Tribal\n");
		conteudo.append("Dimensões:");
		conteudo.append(tam_x+","+tam_y+"\n");
		conteudo.append("Blocos Chão:\n");
		for (int y = tam_y - 1; y >= 0; y--){
			for (int x = 0; x < tam_x; x++){
				conteudo.append("  "+chao[x][y]+",");
			}
			conteudo.append("\n");
		}
		conteudo.append("Blocos Objetos:\n");
		for (int y = tam_y - 1; y >= 0; y--){
			for (int x = 0; x < tam_x; x++){
				if (objeto[x][y] < 10)
					conteudo.append("  "+objeto[x][y]+",");
				else
					conteudo.append(" "+objeto[x][y]+",");
			}
			conteudo.append("\n");
		}
		conteudo.append("Fim");
		
		arquivo.writeString(conteudo.toString(), false);
	}
	
	public boolean carregar(String nomeNovo){
		FileHandle arquivo = Gdx.files.local(Conf.CAMINHO + nomeNovo + Conf.EXTENSAO);
		if (arquivo.exists()){
			String [] dados = arquivo.readString().split("\n");
		
			nome = arquivo.nameWithoutExtension();
			TelaPrincipal.nomeMapa = nome;
			
			String [] dimensoes = dados[1].split(":");
			dimensoes = dimensoes[1].split(",");
			int tx = Integer.valueOf(dimensoes[0]);
			int ty = Integer.valueOf(dimensoes[1]);
			
			mudarTamanho(tx,ty,true);
			
			for (int y = 0; y < ty; y++){
				String [] linhachao = dados[y+3].split(",");
				String [] linhaobj = dados[y+3+ty+1].split(",");
				
				for (int x = 0; x < tx; x++){
					chao[x][ty-y-1] = pegarInteiro(linhachao[x]);
					objeto[x][ty-y-1] = pegarInteiro(linhaobj[x]);
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	private int pegarInteiro(String s){
		if (s.isEmpty())
			return 0;
		
		String e = "";
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i) != ' '){
				e += s.charAt(i);
			}
		}
		
		return Integer.valueOf(e);
	}
	
	/*
	public void criarMapaGeral(){
		for (int x = 0; x < tam_x; x++){
			for (int y = 0; y < tam_y; y++){
				
			}
		}
	}
	*/
	
	public void criarMapaPassavel(){
		passavel = new int[tam_x][tam_y];
		
		for (int x = 0; x < tam_x; x++){
			for (int y = 0; y < tam_y; y++){
				passavel[x][y] = 0;
				
				if (objeto[x][y] == 8){
					passavel[x][y] = 2;
				}else if (objeto[x][y] == 9){
					passavel[x][y] = 3;
				}else if (objeto[x][y] > 0 && objeto[x][y] < 5){
					passavel[x][y] = 1;
				}else  if (objeto[x][y] == 0 && chao[x][y] > 2){
					passavel[x][y] = 1;
				}
			}
		}
	}

	private boolean verificarCaminhoEntreTribos(){
		int origemx = 0, origemy = 0;
		int destinox = 1, destinoy = 1;
		
		criarMapaPassavel();
		
		for (int x = 0; x < tam_x; x++){
			for (int y = 0; y < tam_y; y++){
				if (passavel[x][y] == 2){
					origemx = y;
					origemy = x;
				}
				if (passavel[x][y] == 3){
					destinox = y;
					destinoy = x;
				}
			}
		}
		
		Ponto origem = new Ponto(origemx, origemy);
		Ponto destino = new Ponto(destinox, destinoy);
		
		//origem.desenhar();
		//destino.desenhar();
		
		AEstrela aestrela = new AEstrela(passavel,origem,destino);
		
		//aestrela.desenhar();
		
		Lista caminho = Lista.aEstrela(aestrela);
		
		if (caminho == null){
			//Gdx.app.log("aEstrela", "Não tem caminho!");
			return false;
		}
		
		//caminho.desenhar();
		
		if (caminho.tem(origem) && caminho.tem(destino) ){
			//Gdx.app.log("aEstrela", "TEM caminho!");
			return true;
		}
		
		//Gdx.app.log("aEstrela", "Se pá deu erro!");
		return false;
	}
	
	private boolean verificarJogadores(){
		int exploradorA = 0;
		int exploradorB = 0;
		int aldeiaA = 0;
		int aldeiaB = 0;
		
		/*
		2 : fazenda
		3 : floresta
		4 : mina
		5 : carne
		6 : madeira
		7 : pedra
		8 : aldeia time 1
		9 : aldeia time 2
		10: explorador time 1
		11: explorador time 2
		*/
		
		
		for (int x = 0; x < tam_x; x++){
			for (int y = 0; y < tam_y; y++){
				switch(objeto[x][y]){
				case 8:	aldeiaA++; break;
				case 9:	aldeiaB++; break;
				case 10:exploradorA++; break;
				case 11:exploradorB++; break;
				}
			}
		}
		
		if (aldeiaA != 1 ||
			aldeiaB != 1 ||
			exploradorA != 1 ||
			exploradorB != 1)
		{
			return false;
		}
		
		return true;
	}
	
	private boolean verificarRecursos(){
		int col_comida	= 0;
		int col_madeira = 0;
		int col_pedra	= 0;
		
		int fix_comida	= 0;
		int fix_madeira	= 0;
		int fix_pedra	= 0;
		
		/*
		2 : fazenda
		3 : floresta
		4 : mina
		5 : carne
		6 : madeira
		7 : pedra
		8 : aldeia time 1
		9 : aldeia time 2
		10: explorador time 1
		11: explorador time 2
		*/
		
		
		for (int x = 0; x < tam_x; x++){
			for (int y = 0; y < tam_y; y++){
				switch(objeto[x][y]){
				case 2:	fix_comida++; break;
				case 3:	fix_madeira++; break;
				case 4:	fix_pedra++; break;
				case 5:	col_comida++; break;
				case 6:	col_madeira++; break;
				case 7:	col_pedra++; break;

				}
			}
		}
		
		if (fix_comida % 2 != 0 || 
			fix_madeira % 2 != 0 ||
			fix_pedra % 2 != 0 ||
			col_comida % 2 != 0 ||
			col_madeira % 2 != 0 ||
			col_pedra % 2 != 0)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean verificarEficacia(){
		if (verificarJogadores() == false)
			return false;
		
		if (verificarRecursos() == false)
			return false;
		
		if (verificarCaminhoEntreTribos() == false)
			return false;
		
		return true;
	}
}
