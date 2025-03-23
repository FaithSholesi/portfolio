package modele.bateau;

import modele.attribut.Direction;

import modele.attribut.Orientation;
import modele.grille.Case;
import modele.grille.Coordonnées;
import modele.grille.Grille;


public class Destroyeur extends Bateau {

//	public Destroyeur(Coordonnées coord, Orientation o) {
//		super("Destroyers", 3, 1, coord, o);
//		//this.fuseeEclairante=true;
//	}
	
	public Destroyeur(int reference) {
		super("Destroyers", 3, 1, reference, true);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public void deplacerBateau(Direction direction) {	
//	}

	@Override
	public String toString() {
		return color.RED_BOLD_BRIGHT + "DES"  + color.RESET;
	}
//	public void tirerFuseeEclairante() {
//		
//	}
//
//	@Override
//	public void tirer(Case c) {
//		// TODO Auto-generated method stub
//		
//	}

	


}
