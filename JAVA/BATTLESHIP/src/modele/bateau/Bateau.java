package modele.bateau;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

import color.Colors;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import modele.IBateau;
import modele.attribut.Direction;
import modele.attribut.Orientation;
import modele.grille.Case;
import modele.grille.Coordonnées;
import modele.grille.Grille;
import music.Music;

public abstract class Bateau implements IBateau, Serializable {
	protected boolean fuseeEclairante;
	protected String type;
	protected int taille;
	protected int puissDeTir;
	protected int reference;
	protected Coordonnées coord;
	protected Orientation o;
	protected ArrayList<Direction> directions;
	protected Colors color;
	protected Music music;
	//attribut music instanciation 


//	public Bateau(String type, int taille, int puissDeTir, Coordonnées coord, Orientation o) {
//		this.coord=new Coordonnées();
//		this.type = type;
//		this.taille = taille;
//		this.puissDeTir = puissDeTir;
//		this.coord = coord;
//		this.o = o;
//	}

	public Bateau(String type, int taille, int puissDeTir, int reference, boolean fusee) {
		this.type = type;
		this.taille = taille;
		this.puissDeTir = puissDeTir;
		this.reference=reference;
		this.directions=new ArrayList<Direction>();
		this.coord=new Coordonnées();
		this.fuseeEclairante=fusee;
//		this.music=new Music();
	}
	
	public ArrayList<Direction> getDirections() {
		return directions;
	}

	public boolean isFuseeEclairante() {
		return fuseeEclairante;
	}

	public void setFuseeEclairante(boolean fuseeEclairante) {
		this.fuseeEclairante = fuseeEclairante;
	}

	public String getType() {
		return type;
	}

	public int getTaille() {
		return taille;
	}

	public int getPuissDeTir() {
		return puissDeTir;
	}
	
	@Override
	public Coordonnées getCoordonnées() {
		return coord;
	}

	public void setCoord(Coordonnées coord) {
		this.coord = coord;
	}

	public Orientation getOrientation() {
		return o;
	}

	public void setOrientation(Orientation o) {
		this.o = o;
	}

	public int getReference() {
		return reference;
	}
	
	@Override
	public void deplacer(int indiceDirection, Grille grille) {
		
		System.out.println("je suis la");
		if(this.getDirections().get(indiceDirection)==Direction.OUEST||this.getDirections().get(indiceDirection)==Direction.NORD){
			int cpt=0;
			for(int j=0; j<Grille.getLargeur();j++) {
				for(int i=0; i<Grille.getLongueur();i++) {
					if(grille.getCase(i, j).getBateau()!=null) {
						if(grille.getCase(i, j).getBateau().getReference()==this.getReference()){
							if(this.getDirections().get(indiceDirection)==Direction.OUEST) {
								copieBateau(cpt,i-1,j,i,j, grille);
							}
							if(this.getDirections().get(indiceDirection)==Direction.NORD) {
								copieBateau(cpt,i,j-1,i,j, grille);
							}
							cpt++;
						}
					}
				}
			}
		}
		// la on fait un balayage en partant de la fin puisque le decalage se fait vers la droite
		if(this.getDirections().get(indiceDirection)==Direction.EST||this.getDirections().get(indiceDirection)==Direction.SUD){
			int cpt=0;
			for(int j=Grille.getLargeur()-1; j>=0;j--) {
				for(int i=Grille.getLongueur()-1; i>=0;i--) {
					if(grille.getCase(i, j).getBateau()!=null) {
						if(grille.getCase(i, j).getBateau().getReference()==this.getReference()){
							if(this.getDirections().get(indiceDirection)==Direction.EST) {
								copieBateau(cpt,i+1,j,i,j, grille);
							}
							if(this.getDirections().get(indiceDirection)==Direction.SUD) {
								copieBateau(cpt,i,j+1,i,j, grille);
							}
							cpt++;
							
							if(cpt==this.getTaille()-1) {
								Coordonnées coord=new Coordonnées(i,j);
								this.setCoord(coord);
							}
						}
					}
					
				}
			}
			
		}
	}

// pour le deplacement on decale en copiant le bateau en fct de la direction choisie puis on suppr celui a la case courante
public void copieBateau(int cpt,int x1 , int y1, int x2, int y2, Grille grille) {
	if(cpt==0) {
		Coordonnées coord=new Coordonnées(x1,y1);
		this.setCoord(coord);
	}
	grille.setCase(grille.getCase(x2, y2), x1, y1);
	grille.getCase(x2, y2).setBateau(null);
	grille.getCase(x2, y2).setHasBeenShoot(false);
	grille.getCase(x2, y2).setHasHadBoat(true);
}

	public void PlayMusicBateau() {
		String file="ressources/sons/explosion.mp3";
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			Player player = new Player(fileInputStream);
			player.play();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void tirer(int x, int y, Grille grilleJ, Grille grilleAdv, int cpt, Boolean song) {
		//this.music=new Music();
//		String file="ressources/sons/explosion.mp3";
		
		if(y<Grille.getLargeur()&&x<Grille.getLongueur()) {
			Case c= grilleAdv.getCase(x, y);
			//si le bateau est diff de null -> alors on peut tirer sauf si le bateau n'est pas un ss marin et tire sur un ss marin
			//String file=null;
			if (c.getBateau() != null) {
				//Music music= new Music("ressources/sons/explosion.mp3");
				//file="ressources/sons/explosion.mp3";
				Orientation orient=c.getBateau().getOrientation();
				if (c.getBateau().getType().compareTo("SousMarin") != 0) {
						//music.PlayMusic();
					//this.PlayMusic(file);
						c.setBateau(null);
						c.setHasHadBoat(true);
						// on insere dans une grille vide les elements de l'adv vus par le joueur
						grilleJ.getCase(x, y).setHasHadBoat(true);
				}
				else {
					if (this.getType().compareTo("SousMarin") == 0) {
						//music.PlayMusic();
						//this.PlayMusic(file);
						c.setBateau(null);
						c.setHasHadBoat(true);
						grilleJ.getCase(x, y).setHasHadBoat(true);
					}
				}
				if(song) {
					this.PlayMusicBateau();
				}
				this.repeatTirer(orient, c, grilleJ, grilleAdv, cpt, song);
			}
			//sinon on dit on indique juste que le joueur a tenté une case 
			else {
				this.repeatTirer(null, c, grilleJ, grilleAdv, cpt, song);
			}
			
			c.setHasBeenShoot(true);
			grilleJ.getCase(x, y).setHasBeenShoot(true);
		}
		
	}

	public void repeatTirer(Orientation orient,Case c, Grille grilleJ,  Grille grilleAdv, int cpt, Boolean song) {
		if(cpt==0) {
			//String file="ressources/sons/explosion.mp3";
			while(cpt<this.getPuissDeTir()-1) {
				if(orient==Orientation.HORIZONTAL) {
					cpt=cpt+1;
					c.getC().setX(c.getC().getX()+1);
					this.tirer(c.getC().getX(),c.getC().getY(), grilleJ, grilleAdv, cpt, song);
				}
				else {
					cpt=cpt+1;
					c.getC().setY(c.getC().getY()+1);
					this.tirer(c.getC().getX(),c.getC().getY(), grilleJ, grilleAdv, cpt, song);
				}
			}
		}
	}

	
}
