package br.ufla.americatribal.editor.aestrela;

public class Ponto {
	int x = 0;
	int y = 0;
	
	int g = 0;
	int h = 0;
	int f = 0;
	
	int custo = 10;
	
	Ponto pai = null;
	
	public Ponto(){}
	
	public Ponto(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Ponto(int x, int y, Ponto pai){
		this.x = x;
		this.y = y;
		this.pai = pai;
	}
	public Ponto(Ponto p){
		this.x = p.x;
		this.y = p.y;
		
		this.g = p.g;
		this.h = p.h;
		this.f = p.f;
		
		this.custo = p.custo;
		
		this.pai = p.pai;
	}	
	public void setPonto(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void custos(Ponto destino){	
		g = custo;
		
		if (pai != null){
			g += pai.g;
		}
		
		int dx = Math.abs(this.x - destino.x);
		int dy = Math.abs(this.y - destino.y);
		
		h = (dx + dy) * custo;
		
		f = g + h;
	}	
	public boolean igual(Ponto p){
		return igual(p.x, p.y);
	}
	public boolean igual(int x, int y){
		if (this.x == x && this.y == y)
			return true;
		
		return false;
	}
	public Ponto cima(){
		return new Ponto(x, y - 1);
	}
	public Ponto baixo(){
		return new Ponto(x, y + 1);
	}
	public Ponto esquerda(){
		return new Ponto(x - 1, y);
	}
	public Ponto direita(){
		return new Ponto(x + 1, y);
	}
	public void desenhar(){
		System.out.print("("+x+","+y+") G: "+g+" H: "+h+" F: "+f);
		if (pai != null){
			System.out.print(" pai: ("+pai.x+","+pai.y+")");
		}
		System.out.println();
	}
}
