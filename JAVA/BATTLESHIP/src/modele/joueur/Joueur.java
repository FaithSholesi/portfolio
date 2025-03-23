package modele.joueur;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import modele.IBateau;
import modele.attribut.Direction;
import modele.attribut.Orientation;
import modele.grille.Case;
import modele.grille.Coordonnées;
import modele.grille.Grille;

import modele.bateau.Bateau;
import modele.bateau.Croiseur;
import modele.bateau.Cuirassé;
import modele.bateau.Destroyeur;
import modele.bateau.SousMarin;

public abstract class Joueur {
	private static final int nbCuirasse = 1;
	private static final int nbSousMarin= 4;
	private static final int nbDestroyeur = 3;
	private static final int nbCroiseur = 2;
	private List<Bateau> bateaux;
	private Grille grilleJoueur;
	private Grille historique;
	
	public Grille getGrilleJoueur() {
		return grilleJoueur;
	}
	public void setGrilleJoueur(Grille grilleJoueur) {
		this.grilleJoueur = grilleJoueur;
	}
	public Grille getHistorique() {
		return historique;
	}
	public void setHistorique(Grille historique) {
		this.historique = historique;
	}

	public Joueur() {
		this.initPlateau();
	}
	public void jouer() {
		this.remplirBateaux();
		this.placerBateau(0);
	}
	public List<Bateau> getBateaux() {
		return bateaux;
	}

	public void setBateaux(List<Bateau> bateaux) {
		this.bateaux = bateaux;
	}
	
	public void save() {
		File fichier =  new File("ressources//documents/save.txt") ;

		 // ouverture d'un flux sur un fichier
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
			oos.writeObject(this.grilleJoueur);
			oos.writeObject(this.historique);
			oos.writeObject(this.bateaux);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Joueur(List<Bateau> bateaux, Grille[] plateau) {
		this.bateaux = bateaux;
	}

	public void charger() {
		File fichier=  new File("ressources//documents/save.txt") ;
		
		 // ouverture d'un flux sur un fichier
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichier));
			Grille grilleJ = (Grille)ois.readObject();
			Grille grilleO = (Grille)ois.readObject();
			ArrayList<Bateau> listBateau= (ArrayList<Bateau>)ois.readObject();
			this.grilleJoueur=grilleJ;
			this.historique=grilleO;
			this.bateaux=listBateau;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void quitter() {
		System.exit(0);
		
	}
	//pour savoir si le bateau a ete coulé ou non si c'est le cas il sera supprimé de la liste joueur
	public void checkBateauCoulé() {
		boolean coulé;
		for(int i=0;i<this.bateaux.size();i++) {
			coulé=true;
			for(int j=0; j<Grille.getLargeur();j++) {
				for(int k=0; k<Grille.getLongueur();k++) {
					if(this.grilleJoueur.getCase(k,j).containBateau()) {
						if(this.bateaux.get(i).getReference()==this.grilleJoueur.getCase(k,j).getBateau().getReference()) {
							coulé=false;
							break;
						}
					}
				}
			}
			if(coulé){
				System.out.println("coulé");
				this.supprimBateau(i);
			}
		}
		
	}
	
	
	//regarde si une partie du bateau a ete touché ou nn si c'est le cas on ne pourra ps le deplacer
	public boolean checkBateauTouché(int indiceBateau) {
		
		for(int i=0;i<this.getByReference(indiceBateau).getTaille();i++) {
			if(this.getByReference(indiceBateau).getOrientation()==Orientation.HORIZONTAL) {
				if(!this.grilleJoueur.getCase(this.getByReference(indiceBateau).getCoordonnées().getX()+i,this.getByReference(indiceBateau).getCoordonnées().getY()).containBateau()) {
					return true;
				}
			}
			else {
				if(!this.grilleJoueur.getCase(this.getByReference(indiceBateau).getCoordonnées().getX(),this.getByReference(indiceBateau).getCoordonnées().getY()+i).containBateau()) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	

	//permet de s'assurer que le bateau n'est pas au niveau des extremités des grilles ou qu'il ne va pas en se deplacant chevaucher un autre bateau
	public boolean checkBateau(int indiceBateau, int indiceDirection) {
		System.out.println(indiceBateau+" "+indiceDirection);
		if(this.getByReference(indiceBateau).getDirections().get(indiceDirection)==Direction.NORD) {
			if(this.getByReference(indiceBateau).getCoordonnées().getY()==0){
				return false;
			}
			if(this.grilleJoueur.getCase(this.getByReference(indiceBateau).getCoordonnées().getX(),this.getByReference(indiceBateau).getCoordonnées().getY()-1).getBateau()!=null){
				return false;
			}
		}
		if(this.getByReference(indiceBateau).getDirections().get(indiceDirection)==Direction.SUD) {
			if(this.getByReference(indiceBateau).getCoordonnées().getY()+this.getByReference(indiceBateau).getTaille()-1==Grille.getLargeur()-1){
				return false;
			}
			if(this.grilleJoueur.getCase(this.getByReference(indiceBateau).getCoordonnées().getX(),this.getByReference(indiceBateau).getCoordonnées().getY()+this.getByReference(indiceBateau).getTaille()).getBateau()!=null){
				return false;
			}	
		}
		if(this.getByReference(indiceBateau).getDirections().get(indiceDirection)==Direction.EST) {
			if(this.getByReference(indiceBateau).getCoordonnées().getX()+this.getByReference(indiceBateau).getTaille()-1==Grille.getLargeur()-1) {
				return false;
			}
			if(this.grilleJoueur.getCase(this.getByReference(indiceBateau).getCoordonnées().getX()+this.getByReference(indiceBateau).getTaille(),this.getByReference(indiceBateau).getCoordonnées().getY()).getBateau()!=null){
				return false;
			}
		}
		if(this.getByReference(indiceBateau).getDirections().get(indiceDirection)==Direction.OUEST) {
			if(this.getByReference(indiceBateau).getCoordonnées().getX()==0) {
				return false;
			}
			if(this.grilleJoueur.getCase(this.getByReference(indiceBateau).getCoordonnées().getX()-1,this.getByReference(indiceBateau).getCoordonnées().getY()).getBateau()!=null || this.getByReference(indiceBateau).getCoordonnées().getX()==0){
				return false;
			}
		}
		return true;
		
	}
	
	//pour deplacer le bateau on effectue un balayage des cases pour recuperer ceux qui ont la reference choisie et on les decale en fct de la diretion
	public void deplacerBateau(int indiceBateau, int indiceDirection) {
		System.out.println("je suis la");
			if(this.bateaux.get(indiceBateau).getDirections().get(indiceDirection)==Direction.OUEST||this.bateaux.get(indiceBateau).getDirections().get(indiceDirection)==Direction.NORD){
				int cpt=0;
				for(int j=0; j<Grille.getLargeur();j++) {
					for(int i=0; i<Grille.getLongueur();i++) {
						if(this.grilleJoueur.getCase(i, j).getBateau()!=null) {
							if(this.grilleJoueur.getCase(i, j).getBateau().getReference()==this.bateaux.get(indiceBateau).getReference()){
								if(this.bateaux.get(indiceBateau).getDirections().get(indiceDirection)==Direction.OUEST) {
									copieBateau(cpt,i-1,j,i,j, indiceBateau);
								}
								if(this.bateaux.get(indiceBateau).getDirections().get(indiceDirection)==Direction.NORD) {
									copieBateau(cpt,i,j-1,i,j, indiceBateau);
								}
								cpt++;
							}
						}
					}
				}
			}
			// la on fait un balayage en partant de la fin puisque le decalage se fait vers la droite
			if(this.bateaux.get(indiceBateau).getDirections().get(indiceDirection)==Direction.EST||this.bateaux.get(indiceBateau).getDirections().get(indiceDirection)==Direction.SUD){
				int cpt=0;
				for(int j=Grille.getLargeur()-1; j>=0;j--) {
					for(int i=Grille.getLongueur()-1; i>=0;i--) {
						if(this.grilleJoueur.getCase(i, j).getBateau()!=null) {
							if(this.grilleJoueur.getCase(i, j).getBateau().getReference()==this.bateaux.get(indiceBateau).getReference()){
								if(this.bateaux.get(indiceBateau).getDirections().get(indiceDirection)==Direction.EST) {
									copieBateau(cpt,i+1,j,i,j, indiceBateau);
								}
								if(this.bateaux.get(indiceBateau).getDirections().get(indiceDirection)==Direction.SUD) {
									copieBateau(cpt,i,j+1,i,j, indiceBateau);
								}
								cpt++;
								
								if(cpt==this.bateaux.get(indiceBateau).getTaille()-1) {
									Coordonnées coord=new Coordonnées(i,j);
									this.bateaux.get(indiceBateau).setCoord(coord);
								}
							}
						}
						
					}
				}
				
			}
		}
	
	// pour le deplacement on decale en copiant le bateau en fct de la direction choisie puis on suppr celui a la case courante
	public void copieBateau(int cpt,int x1 , int y1, int x2, int y2, int indiceBateau) {
		if(cpt==0) {
			Coordonnées coord=new Coordonnées(x1,y1);
			this.bateaux.get(indiceBateau).setCoord(coord);
		}
		this.grilleJoueur.setCase(this.grilleJoueur.getCase(x2, y2), x1, y1);
		this.grilleJoueur.getCase(x2, y2).setBateau(null);
		this.grilleJoueur.getCase(x2, y2).setHasBeenShoot(false);
		this.grilleJoueur.getCase(x2, y2).setHasHadBoat(true);
	}
	
	public void placerBateau(int cpt) {
		Boolean caseVide=true;
		for(int i=cpt;i<bateaux.size();i++) {
			//selectionne des coordonnées aleatoirement
			int coordX=(int) Math.floor(Math.random() * 15);
			int coordY=(int) Math.floor(Math.random() * 15);
			int orientation=(int) Math.floor(Math.random() * 2);
			//0-> horizontal et 1-> vertical
			if (orientation==0) {
				//le bateau ne doit pas depasser la taille de la grille quelque
				// soit sa taille
				if(coordX+bateaux.get(i).getTaille()<=Grille.getLongueur()) {
					//on rappelle la fct si c'est le cas pour changer les coord
					for(int m=0;m<bateaux.get(i).getTaille();m++) {
						if(grilleJoueur.getCase(coordX+m,coordY).getBateau()!=null){
							caseVide=false;
							this.placerBateau(i);
							return;
						}
						if(m==bateaux.get(i).getTaille()-1 && caseVide) {
							if(bateaux.get(i).getTaille()==1) {
								this.creationBateau(i,null, coordX, coordY);
							}
							else {
								this.creationBateau(i, Orientation.HORIZONTAL, coordX, coordY);
							}
						}
					}
				}
				else {
					this.placerBateau(i);
					return;
				}
			}
			else {
				if(coordY+bateaux.get(i).getTaille()<=Grille.getLargeur()) {
					for(int m=0;m<bateaux.get(i).getTaille();m++) {
						if(grilleJoueur.getCase(coordX,coordY+m).getBateau()!=null){
							caseVide=false;
							this.placerBateau(i);
							return;
						}
						if(m==bateaux.get(i).getTaille()-1 && caseVide) {
							if(bateaux.get(i).getTaille()==1) {
								this.creationBateau(i,null, coordX, coordY);
							}
							else {
								this.creationBateau(i, Orientation.VERTICAL, coordX, coordY);
							}
						}
					}
				}
				else {
					this.placerBateau(i);
					return;
				}
			}
		}
	}
	
	public void creationBateau(int indiceBateau, Orientation orientation,int coordX, int coordY) {
		if(orientation==Orientation.HORIZONTAL) {
			bateaux.get(indiceBateau).setOrientation(Orientation.HORIZONTAL);
			bateaux.get(indiceBateau).getDirections().add(Direction.OUEST);
			bateaux.get(indiceBateau).getDirections().add(Direction.EST);
			
			for(int j=0;j<bateaux.get(indiceBateau).getTaille();j++) {
				Coordonnées coord= new Coordonnées(coordX+j, coordY);
				if(j==0) {
					bateaux.get(indiceBateau).setCoord(coord);
				}
				grilleJoueur.getCase(coord.getX(),coord.getY()).setBateau(bateaux.get(indiceBateau));
				
			}
		}
		
		if(orientation==Orientation.VERTICAL) {
			bateaux.get(indiceBateau).setOrientation(Orientation.VERTICAL);
			bateaux.get(indiceBateau).getDirections().add(Direction.NORD);
			bateaux.get(indiceBateau).getDirections().add(Direction.SUD);
			
			
			for(int j=0;j<bateaux.get(indiceBateau).getTaille();j++) {
				Coordonnées coord= new Coordonnées(coordX, coordY+j);
				if(j==0) {
					bateaux.get(indiceBateau).setCoord(coord);
				}
				grilleJoueur.getCase(coord.getX(),coord.getY()).setBateau(bateaux.get(indiceBateau));
			}
			
		}
		//si l'orientation est nulle on dit que le bateau a toutes les orientations (ex: sous-marin)
		if(orientation==null) {
			bateaux.get(indiceBateau).setOrientation(null);
			bateaux.get(indiceBateau).getDirections().add(Direction.NORD);
			bateaux.get(indiceBateau).getDirections().add(Direction.SUD);
			bateaux.get(indiceBateau).getDirections().add(Direction.OUEST);
			bateaux.get(indiceBateau).getDirections().add(Direction.EST);
			
			
			for(int j=0;j<bateaux.get(indiceBateau).getTaille();j++) {
				Coordonnées coord= new Coordonnées(coordX, coordY+j);
				if(j==0) {
					bateaux.get(indiceBateau).setCoord(coord);
				}
				grilleJoueur.getCase(coord.getX(),coord.getY()).setBateau(bateaux.get(indiceBateau));
			}	
		}
		
		
	}
	
	public void initPlateau() {
		this.grilleJoueur=new Grille();
		this.historique=new Grille();
	}
	
	public void remplirBateaux() {
		this.bateaux=new ArrayList<Bateau>();
		int ref=1;
		
		for(int i=0;i<nbSousMarin;i++) {
			
//			if(i<nbCuirasse) {
//			Bateau cuirasse= new Cuirassé(ref);
//			bateaux.add(cuirasse);
//			ref++;
//			}
//			
			if(i<2) {
			Bateau croiseur= new Croiseur(ref);
			bateaux.add(croiseur);
			ref++;
			}
			
//			if(i<2) {
//			Bateau destroyeur= new Destroyeur(ref);
//			bateaux.add(destroyeur);
//			ref++;
//			}
//			
//			Bateau sousMarin= new SousMarin(ref);
//			bateaux.add(sousMarin);
//			ref++;
			
		}
	}
	
	//si un element est diff de null dc existe alors la personne n'a pas encore gagné
	public boolean gagner(Grille grilleAdv) {
		for(int i=0; i<Grille.getLargeur();i++) {
			for(int j=0; j<Grille.getLongueur();j++) {
				if(grilleAdv.getCase(j, i).getBateau()!=null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void supprimBateau(int indiceBateau) {
		this.bateaux.remove(indiceBateau);
	}
	
	public IBateau getByReference (int reference) {
		
		for(Bateau bateau : this.bateaux){
			if(bateau.getReference()==reference) {
				return bateau;
			}
		}
		return null;
	}

}
