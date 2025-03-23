package vue;

import java.util.InputMismatchException;
import java.util.Scanner;

import controleur.Controleur;
import controleur.ValeurException;
import modele.joueur.Joueur;

public class Partie {
	
	private Controleur controle;
	private Scanner scan;

	public Partie(Controleur controle) {
		this.controle=controle;
		this.scan=new Scanner(System.in);
	}

	public void lancerPartie() {
		this.actionJoueur();		
	}
	
	public void actionJoueur() {
		System.out.println("Que voulez-vous faire ? Deplacer ? Tirer ? Quitter");
		String reponse=scan.next().toUpperCase();
		try {
			this.controle.checkAction(reponse);
			this.controle.afficherBateau(reponse);
		} catch (ValeurException e) {
			this.actionJoueur();
		}
	}
	
	public void afficherBateau(String reponse) {
		String bateau="Lequel voulez-vous utiliser ?\n";
		for(int i=0;i<controle.recupererBateau().size();i=i+3) {
			bateau+=controle.recupererBateau().get(i)+") "+controle.recupererBateau().get(i+1)+" "+controle.recupererBateau().get(i+2)+"\n";
		}
		System.out.println(bateau);
		this.controle.getReponseBateau(reponse);
	}
	
	public void getReponseBateau(String reponse) {
		int reponseBateau=0;
		try {
			reponseBateau=Integer.parseInt(scan.next());
			this.controle.refBateauExist(reponse, reponseBateau);
			this.controle.choixActionJoueur(reponse,reponseBateau);
		}
		catch(ValeurException e) {

			this.actionJoueur();
		}
		catch(NumberFormatException e) {
			System.out.println("Ce n'est ps la bonne valeur");
			this.actionJoueur();
		}
		
	}
	
	public void afficherDirection(int indiceBateau) {
		String direction="Quelle direction ?\n";
		for(int i=0;i<controle.recupererDirection(indiceBateau).size();i++) {
			direction+=i+1+") "+controle.recupererDirection(indiceBateau).get(i)+"\n";
		}
		System.out.println(direction);
		try {
		int indiceDirection=Integer.parseInt(scan.next())-1;
		this.controle.checkDirection(indiceBateau, indiceDirection);
		this.controle.deplacerBateau(indiceBateau,indiceDirection);
		}
		catch(ValeurException e) {
			this.actionJoueur();
		}
		catch(NumberFormatException e) {
			System.out.println("Ce n'est ps la bonne valeur");
			this.actionJoueur();
		}
	}

	public void askCoordTirer(int reponseBateau) {
		System.out.println("Ou voulez vous tirer ? (exemple : x;y) ");
		try {
		String s = scan.next();
		this.controle.checkCoord(s, reponseBateau);
		int x = Integer.parseInt(s.split(";")[0]);
		int y = Integer.parseInt(s.split(";")[1]);
		System.out.println(x+"...."+y);
		this.controle.actionTirer(x,y,reponseBateau);
		}
		catch(ValeurException e) {
			this.actionJoueur();
		}
		catch(NumberFormatException e) {
			System.out.println("Ce n'est ps la bonne valeur");
			this.actionJoueur();
		}
	}
		
	

	public void gagner(boolean etat) {
		if(etat) {
		System.out.println("vous avez gagné la partie");
		}
		else {
			System.out.println("c'est dommage mais c'est un échec");
		}
		this.scan.close();
	}

}
