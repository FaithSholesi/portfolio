package modele.bateau;

import modele.attribut.Direction;
import modele.attribut.Orientation;
import modele.grille.Case;
import modele.grille.Coordonnées;

public class Croiseur extends Bateau {

//	public Croiseur(Coordonnées coord, Orientation o) {
//		super("Croiseur", 5, 4, coord, o);
//		// TODO Auto-generated constructor stub
//	}

	public Croiseur(int reference) {
		super("Croiseur", 5, 4,reference, false);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public void deplacerBateau(Direction direction) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void tirer(Case c) {
//		// TODO Auto-generated method stub
//		
//	}
	
	@Override
	public String toString() {
		return this.color.PURPLE_BOLD_BRIGHT + "CRO" + color.RESET;
	}


}
