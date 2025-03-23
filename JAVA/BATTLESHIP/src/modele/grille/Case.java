package modele.grille;

import java.io.Serializable;

import color.Colors;
import modele.IBateau;
import music.Music;

public class Case implements Serializable {
	private IBateau bateau=null;
	private Coordonnées c;
	private boolean hasBeenShoot, hasHadBoat;
	private Colors color;
	//Music music=new Music();
	
	//Crée une case
	public Case(int i, int j) {
		this.setC(new Coordonnées(i,j));
		//this.music= new Music();
	}
	
	@Override
	public String toString() {
		if (bateau != null) {
			return bateau.toString();
		}
		if (this.hasBeenShoot && this.hasHadBoat) {
			//music.PlayMusic("ressources/sons/explosion.mp3");
			return " X ";
		}
		if (this.hasBeenShoot && !this.hasHadBoat) {
			//music.PlayMusic("ressources/sons/plouf.mp3");
			return " O ";
		}
		return color.BLUE + " . " + color.RESET; //mer
	}


	//Crée une case contenant un bateau 
	public Case(IBateau ibateau, int i, int j) {
		this.setBateau(ibateau);
		this.setC(new Coordonnées(i,j));
		this.setHasHadBoat(true);
		//this.music= new Music();
	}
	
	public IBateau getBateau() {
		return bateau;
	}
	
	public boolean containBateau() {
		if(bateau==null) {
			return false;
		}
		return true;
	}

	public void setBateau(IBateau bateau) {
		if (bateau != null)
			this.setHasHadBoat(true);
		this.bateau = bateau;
	}

	public Coordonnées getC() {
		return c;
	}

	public void setC(Coordonnées c) {
		this.c = c;
	}

	public boolean isHasBeenShoot() {
		return hasBeenShoot;
	}

	public void setHasBeenShoot(boolean hasBeenShoot) {
		this.hasBeenShoot = hasBeenShoot;
	}

	public boolean isHasHadBoat() {
		return hasHadBoat;
	}

	public void setHasHadBoat(boolean hasHadBoat) {
		this.hasHadBoat = hasHadBoat;
	}
}
