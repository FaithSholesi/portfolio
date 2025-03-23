package modele.grille;

import java.io.Serializable;

public class Coordonnées implements Serializable {
	private int x;
	private int y;
	
	public Coordonnées() {

	}

	public Coordonnées(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		String s="";
		s+=" "+ this.x+ "  "+ this.y;
	return s;
	}
	
}
