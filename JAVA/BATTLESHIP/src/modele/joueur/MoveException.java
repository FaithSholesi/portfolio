package modele.joueur;

public class MoveException extends Exception {
	public MoveException(String message) {
		if(message=="bornes") {
		System.out.println("Vous ne pouvez pas deplacer ce bateau: Veillez en choisir un autre ou changer de direction ou d'action");
		}
		
		if(message=="touch�") {
			System.out.println("Vous ne pouvez pas deplacer ce bateau: il a deja �t� touch�. Veillez en choisir un autre ou changer d'action");
		}
	}
}
