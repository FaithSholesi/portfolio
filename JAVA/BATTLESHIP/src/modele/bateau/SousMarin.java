package modele.bateau;

import modele.attribut.Direction;
import modele.attribut.Orientation;
import modele.grille.Case;
import modele.grille.Coordonnées;

public class SousMarin extends Bateau {
//
//	public SousMarin(Coordonnées coord, Orientation o) {
//		super("SousMarin", 1, 1, coord, o);
//		// TODO Auto-generated constructor stub
//	}

	public SousMarin(int reference) {
		super("SousMarin", 1, 1, reference,false);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return color.YELLOW_BOLD_BRIGHT + "S-M" + color.RESET;
	}



}
