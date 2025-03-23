package vue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import controleur.Controleur;
import controleur.ValeurException;

public class Accueil {
	
	private Controleur controle;
 
	
	public Accueil(Controleur controle) {
		this.controle=controle;
		//this.pageAccueil();
	}
	
	public void pageAccueil() {

		System.out.println("BATTLESHIP \n");
		System.out.println(" 1 : Nouvelle Partie");
		System.out.println(" 2 : Charger Partie");
		System.out.println(" 3 : Aide");
		System.out.println(" 4 : Quitter\n");
		System.out.println("Veuillez choisir une option (1, 2, 3 ou 4)\n");
		
		Scanner scan =new Scanner(System.in);
		try {
			this.controle.choixJoueur(scan.nextInt());
		} 
		catch (InputMismatchException e) {
			System.out.println("Ce n'est pas la bonne valeur");
			this.pageAccueil();
		}
		catch (ValeurException e) {
			this.pageAccueil();
		}
		scan.close();
	}
	
	public void afficherAide() {
		File f = new File("ressources//documents//text.txt");
		
		BufferedReader b;
		try {
			b = new BufferedReader(new FileReader(f));
			  String line;
				while ((line = b.readLine())!= null){
				    System.out.println(line);
				}
				b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
