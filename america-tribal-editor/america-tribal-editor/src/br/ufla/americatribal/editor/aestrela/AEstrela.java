package br.ufla.americatribal.editor.aestrela;

public class AEstrela {

	public static final String E = "E";
	public static final String D = "D";
	public static final String C = "C";
	public static final String B = "B";

	int mapa[][] = { { 1, 0, 0, 0, 0, 0 }, 
				     { 1, 0, 1, 0, 0, 0 },
				     { 1, 0, 0, 1, 0, 0 }, 
				     { 1, 0, 0, 1, 1, 0 }, 
				     { 3, 0, 0, 1, 0, 0 },
				     { 1, 0, 1, 0, 2, 0 } };

	int ty = mapa.length;
	int tx = mapa[0].length;

	Ponto casa = new Ponto(0, 0);
	Ponto origem = new Ponto();
	Ponto destino = new Ponto();
	Ponto atual = new Ponto();

	int obstaculo = 1;

	public AEstrela() {
	}

	

	public AEstrela(int mapa[][], Ponto origem, Ponto destino, int obstaculo) {
		this.mapa = mapa;
		this.origem = origem;
		this.destino = destino;
		this.ty = mapa.length;
		this.tx = mapa[0].length;
		this.obstaculo = obstaculo;
	}

	public AEstrela(int mapa[][], Ponto origem, Ponto destino) {
		this.mapa = mapa;
		this.origem = origem;
		this.destino = destino;
		this.ty = mapa.length;
		this.tx = mapa[0].length;
	}

	public void visao(Lista l, Lista l2) {
		Ponto pc = new Ponto();
		Ponto pe = new Ponto();
		Ponto pd = new Ponto();
		Ponto pb = new Ponto();
		for (Ponto p : l.lista) {
			pc = p.cima();
			pe = p.esquerda();
			pd = p.direita();
			pb = p.baixo();
			if(!(l2.contem(p))){
				l2.lista.add(p);
			}
			if ((noMapa(pc)) && !(l2.contem(pc))) {
				l2.lista.add(pc);
			}
			if (!(l2.contem(pe))&&(noMapa(pe))) {
				l2.lista.add(pe);
			}
			if (!(l2.contem(pd))&&(noMapa(pd))) {
				l2.lista.add(pd);
			}
			if (!(l2.contem(pb))&&(noMapa(pb))) {
				l2.lista.add(pb);
			}
		}
	}
	public Lista visao(){
		Lista aux = new Lista(this.atual);
		Lista aux2 = new Lista();
		this.visao(aux, aux2);
		this.visao(aux2,aux);
		return aux;
	}
	
	
	public void desenhar() {
		System.out.print("Origem: ");
		origem.desenhar();

		System.out.print("Destino: ");
		destino.desenhar();

		System.out.println("tx: " + tx + " - ty: " + ty);
		for (int y = 0; y < ty; y++) {
			for (int x = 0; x < tx; x++) {
				System.out.print("  " + mapa[y][x]);
			}
			System.out.println();
		}
	}

	public boolean passavel(int x, int y) {
		if (x < 0 || x >= tx || y < 0 || y >= ty)
			return false;

		if (mapa[y][x] == obstaculo) {
			return false;
		}
		return true;
	}

	public boolean passavel(Ponto p) {
		return passavel(p.x, p.y);
	}

	public boolean passavel(Ponto p, String lado) {
		switch (lado) {
		case E:
			return passavel(p.x - 1, p.y);
		case D:
			return passavel(p.x + 1, p.y);
		case C:
			return passavel(p.x, p.y - 1);
		case B:
			return passavel(p.x, p.y + 1);
		}
		return false;
	}
	public boolean noMapa(Ponto p){
		if (p.x < 0 || p.x >= tx || p.y < 0 || p.y >= ty)
			return false;
		return true;
		
	}
}
