package modele.bateau;

import modele.attribut.Direction;
import modele.attribut.Orientation;
import modele.grille.Case;
import modele.grille.Coordonnées;

public class Cuirassé extends Bateau {

//	public Cuirassé(Coordonnées coord, Orientation o) {
//		super("Cuirassé", 7, 9, coord, o);
//		// TODO Auto-generated constructor stub
//	}

	public Cuirassé(int reference) {
		super("Cuirassé", 7, 9, reference,false);
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
		return color.GREEN_BOLD_BRIGHT + "CUI" + color.RESET;
	}

}
