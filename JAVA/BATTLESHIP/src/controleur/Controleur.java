package controleur;

import java.io.IOException;
import java.util.ArrayList;
import modele.attribut.Direction;
import modele.bateau.Bateau;
import modele.grille.Grille;
import modele.joueur.Joueur;
import modele.joueur.MoveException;
import modele.joueur.Ordinateur;
import modele.joueur.Personne;
import vue.Accueil;
import vue.Partie;

public class Controleur {
	
	private Accueil accueil;
	private Partie partie;
	private Personne personne;
	private Ordinateur ordi;
	//private IBateau bateau=new Bateau();
	
	public Controleur() {
		this.accueil= new Accueil(this);
		this.accueil.pageAccueil();
		//initPartie();
	}
	public static void main(String[] args) {
		Controleur controle=new Controleur();
	}
	
	public 	void choixJoueur(int reponse) throws ValeurException {
		switch(reponse) {
			case 1: {
				this.lancerPartie();
				break;
			}
			case 2: {
				this.lancerSavePartie();
				break;
			}
			case 3: {
				this.accueil.afficherAide();
			}
			case 4: {
				Joueur.quitter();
			}
			default: {
				throw new ValeurException();
			}
		}
	}
	
	//pour nettoyer la console mais ca ne marche que dans le cmd 
	public static void clearScreen() throws InterruptedException, IOException {
		  new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}
	
	public void lancerPartie() {
			//initialisation du joueur et de l'ordi 
			this.initPartie();
			//this.personne.afficherPlat();
			this.personne.jouer();
			this.ordi.jouer();
			this.checkGagner();
	}
	
	public void lancerSavePartie() {
		this.initPartie();
		this.personne.charger();
		this.ordi.charger();
		this.checkGagner();
	}
	
	public void initPartie() {
		Personne personne= new Personne();
		Ordinateur ordi= new Ordinateur();
		this.personne=personne;
		this.ordi=ordi;
		this.partie= new Partie(this);
	}
	
	public void checkGagner() {
		System.out.println("random:"+(int)Math.floor(Math.random() * (5 - 1 + 1) + 1));
		while(!this.personne.gagner(this.ordi.getGrilleJoueur()) && !this.ordi.gagner(this.personne.getGrilleJoueur())) {
			System.out.println("joueur");
			this.personne.getGrilleJoueur().afficherGrille();
			System.out.println("joueurpdv");
			//this.personne.getHistorique().afficherGrille();
			this.ordi.getGrilleJoueur().afficherGrille();
			
//			System.out.println("ordi");
//			System.out.println(this.ordi.getGrilleJoueur());
			
			this.personne.checkBateauCoulé();
			this.partie.actionJoueur();
			if(!this.personne.gagner(this.ordi.getGrilleJoueur())) {
				this.ordi.checkBateauCoulé();
				System.out.println("yg");
				this.ordi.actionOrdi(this.personne.getGrilleJoueur());
			}
			
			this.personne.save();
			
			try {
				clearScreen();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
		this.partie.gagner(this.personne.gagner(this.ordi.getGrilleJoueur()));
	}
	
	
	public ArrayList<Object> recupererBateau() {
		ArrayList<Object> listBateau=new ArrayList<Object>();
		for(Bateau bateau : personne.getBateaux()){
			listBateau.add(bateau.getReference());
			listBateau.add(bateau.toString());
			listBateau.add(bateau.getCoordonnées().getX()+" "+bateau.getCoordonnées().getY());
		}
		return listBateau;
	}
	
	public void getReponseBateau(String reponse) {
		this.partie.getReponseBateau(reponse);
	}

	public void afficherBateau(String reponse) {
		this.partie.afficherBateau(reponse);
		
	}

	
	public ArrayList<Direction> recupererDirection(int refBateau) {
		ArrayList<Direction> listDirection=new ArrayList<Direction>();
		listDirection=this.personne.getByReference(refBateau).getDirections();
		return listDirection;
	}
	
	public void choixActionJoueur(String reponse, int reponseBateau) {
		switch (reponse.toUpperCase()) {
			case "TIRER": {
				this.partie.askCoordTirer(reponseBateau);
				break;
			}
			case "DEPLACER": {
				this.partie.afficherDirection(reponseBateau);
				break;
			}
		}
	}

	public void deplacerBateau(int indiceBateau, int indiceDirection) {
		try {
			if(!personne.checkBateau(indiceBateau, indiceDirection)) {
				throw new MoveException("bornes");
			}
			if(personne.checkBateauTouché(indiceBateau)) {
				throw new MoveException("touché");
			}
			this.personne.getByReference(indiceBateau).deplacer(indiceDirection,this.personne.getGrilleJoueur());
			//personne.deplacerBateau(indiceBateau, indiceDirection);
		} catch (MoveException e) {
			this.partie.actionJoueur();
		}
	}


	

	public void actionTirer(int x, int y, int reponseBateau) {
		if((this.personne.getByReference(reponseBateau)).isFuseeEclairante()) {
			System.out.println(this.personne.getHistorique().afficherCaseFuseeEclairante(this.ordi.getGrilleJoueur(),x,y));
			this.personne.getByReference(reponseBateau).setFuseeEclairante(false);
		}
		else {
			this.personne.getByReference(reponseBateau).tirer(x,y,this.personne.getHistorique(),this.ordi.getGrilleJoueur(), 0, true);
		}
	}	
	
	
	public void checkCoord(String reponse, int reponseBateau) throws ValeurException {
			Integer x = Integer.parseInt(reponse.split(";")[0]);
			Integer y = Integer.parseInt(reponse.split(";")[1]);
			if(x==null||y==null || y>Grille.getLargeur()-1 ||x>Grille.getLongueur()-1) {
				throw new ValeurException();
			}
		}
	
	public void checkAction(String reponse) throws ValeurException{
			if(reponse.toUpperCase().compareTo("TIRER")!=0&&reponse.toUpperCase().compareTo("DEPLACER")!=0&&(reponse.toUpperCase().compareTo("QUITTER")!=0)) {
				throw new ValeurException();
			}
	}
	public void refBateauExist(String reponse, int refBateau) throws ValeurException {
		if(this.personne.getByReference(refBateau)==null) {
			throw new ValeurException();
		}
		
	}
	public void checkDirection(int indiceBateau, int indiceDirection) throws ValeurException {
		if(this.recupererDirection(indiceBateau).size()<=indiceDirection) {
			throw new ValeurException();
		}
	}
	
}
