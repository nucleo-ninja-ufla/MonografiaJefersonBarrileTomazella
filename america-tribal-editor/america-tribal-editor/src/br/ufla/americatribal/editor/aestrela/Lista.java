package br.ufla.americatribal.editor.aestrela;

import java.util.ArrayList;

public class Lista {
	ArrayList<Ponto> lista = null;

	public Lista() {
		lista = new ArrayList<Ponto>();
	}

	public Lista(Ponto p) {
		lista = new ArrayList<Ponto>();
		lista.add(p);
	}

	public Lista(Lista l) {
		this.lista = l.lista;
	}

	public void adicionar(Ponto p) {
		lista.add(p);
	}

	public void adicionar(Lista l) {
		if (l != null && l.lista != null && !l.lista.isEmpty()) {
			for (Ponto p : l.lista) {
				if (!tem(p))
					adicionar(p);
			}
		}
	}

	public void remover(Ponto p) {
		if (lista.isEmpty())
			return;

		for (Ponto pa : lista) {
			if (p.igual(pa)) {
				lista.remove(p);
				return;
			}
		}
	}

	public boolean tem(Ponto p) {
		if (lista.isEmpty())
			return false;

		for (Ponto pa : lista) {
			if (p.igual(pa))
				return true;
		}

		return false;
	}

	public void trocarPai(Ponto p, Ponto pai, Ponto destino) {
		if (lista.isEmpty())
			return;

		for (Ponto pa : lista) {
			if (p.igual(pa)) {
				pa.pai = pai;
				pa.custos(destino);
			}
		}
	}

	public Ponto menor() {
		if (lista.isEmpty())
			return null;

		Ponto menor = new Ponto();
		menor.f = 10000000;
		boolean achou = false;

		for (Ponto pa : lista) {
			if (menor.f > pa.f) {
				menor = pa;
				achou = true;
			}
		}

		if (achou == false)
			menor = null;

		return menor;
	}

	public void inverter() {
		if (lista.isEmpty())
			return;

		ArrayList<Ponto> l = new ArrayList<Ponto>();

		for (int i = lista.size() - 1; i >= 0; i--) {
			l.add(lista.get(i));
		}

		lista = l;
	}

	public Ponto pegar(Ponto p) {
		for (Ponto pa : lista) {
			if (p.igual(pa)) {
				return pa;
			}
		}
		return null;
	}

	public void desenhar() {
		for (Ponto p : lista) {
			p.desenhar();
		}
	}

	public static Lista adjacentes(Ponto p, AEstrela mapa, Lista aberta,
			Lista fechada) {
		Lista li = new Lista();

		Ponto pc = p.cima();
		if (!fechada.tem(pc)) {
			if (mapa.passavel(pc)) {
				if (!aberta.tem(pc))
					pc.pai = p;
				else if (pc.g > p.g + pc.custo) {
					pc.pai = p;
					aberta.trocarPai(pc, p, mapa.destino);
				}
				pc.custos(mapa.destino);

				li.adicionar(pc);
			}
		}

		Ponto pb = p.baixo();
		if (!fechada.tem(pb)) {
			if (mapa.passavel(pb)) {
				if (!aberta.tem(pb))
					pb.pai = p;
				else if (pb.g > p.g + pb.custo) {
					pb.pai = p;
					aberta.trocarPai(pb, p, mapa.destino);
				}
				pb.custos(mapa.destino);

				li.adicionar(pb);
			}
		}

		Ponto pe = p.esquerda();
		if (!fechada.tem(pe)) {
			if (mapa.passavel(pe)) {
				if (!aberta.tem(pe))
					pe.pai = p;
				else if (pe.g > p.g + pe.custo) {
					pe.pai = p;
					aberta.trocarPai(pe, p, mapa.destino);
				}
				pe.custos(mapa.destino);

				li.adicionar(pe);
			}
		}

		Ponto pd = p.direita();
		if (!fechada.tem(pd)) {
			if (mapa.passavel(pd)) {
				if (!aberta.tem(pd))
					pd.pai = p;
				else if (pd.g > p.g + pd.custo) {
					pd.pai = p;
					aberta.trocarPai(pd, p, mapa.destino);
				}
				pd.custos(mapa.destino);

				li.adicionar(pd);
			}
		}

		return li;
	}

	public static Lista aEstrela(AEstrela mapa) {
		Lista la = new Lista();
		Lista lf = new Lista();
		Lista adj = null;
		Lista caminho = null;

		la.adicionar(mapa.origem);

		adj = adjacentes(mapa.origem, mapa, la, lf);

		la.adicionar(adj);

		lf.adicionar(mapa.origem);
		la.remover(mapa.origem);

		while (!lf.tem(mapa.destino) && !la.lista.isEmpty()) {
			Ponto atual = la.menor();
			if (atual == null)
				return null;

			lf.adicionar(atual);
			la.remover(atual);

			adj = adjacentes(atual, mapa, la, lf);
			la.adicionar(adj);

		}

		if (la.lista.isEmpty())
			return null;

		caminho = new Lista();

		Ponto p = new Ponto(lf.pegar(mapa.destino));
		while (p != null) {
			caminho.adicionar(p);
			p = p.pai;
		}
		caminho.inverter();

		return caminho;
	}

	public void conhece(Lista l) {
		for (Ponto p : l.lista) {
			if (!(this.lista.contains(p))) {
				this.lista.add(p);
			}
		}

	}

	public boolean contem(Ponto p) {
		for (Ponto o : this.lista) {
			if (p.x==o.x) {
				if(p.y==o.y){
					return true;
				}
			}
		}
		return false;

	}
	public void junta(Lista l){
		for(Ponto p: l.lista){
			this.lista.add(p);
		}
	}
	public void opcoes (AEstrela m){
		int[] v = new int[16];
		int i=0;
		for(Ponto p:this.lista){
			if(!(m.passavel(p))){
				v[i]=this.lista.indexOf(p);
				i++;
			}
		
		}
	}
	

}
