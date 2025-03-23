package modele;

import java.util.ArrayList;

import modele.attribut.Direction;
import modele.attribut.Orientation;
import modele.grille.Case;
import modele.grille.Coordonnées;
import modele.grille.Grille;

public interface IBateau {
	
	public Coordonnées getCoordonnées();
	
	public int getReference();
	
	public String getType();
	
	public int getTaille();
	
	public Orientation getOrientation();



	public void setFuseeEclairante(boolean b);

	public boolean isFuseeEclairante();

	public void deplacer(int indiceDirection, Grille grille);

	public ArrayList<Direction> getDirections();

	public void tirer(int x, int y, Grille grilleJ, Grille grilleAdv, int cpt, Boolean song);

}
