package modele.grille;

import java.io.Serializable;

public class Grille implements Serializable{
	private static final int longueur = 15;
	private static final int largeur = 15;
	
	private Case[][] cases;
	
	

	public void setCases(Case[][] cases) {
		this.cases = cases;
	}

	public Grille() {
		cases = new Case[getLongueur()][getLargeur()];
		initGrille();
	}
	
	//creer cases dans la grille
	public void initGrille() {
		for(int i=0; i<getLargeur();i++) {
			for(int j=0; j<getLongueur();j++) {
				cases[i][j] = new Case(i,j);
			}
		}
	}
	
//	public void afficherGrille() {
//		for(int i=0; i<getLongueur();i++) {
//			if(i==0) {
//				System.out.print("     ");
//			}
//			System.out.print("     ");
//			System.out.print(i+" ");
//		}
//		System.out.println(" \n");
//		
//		for(int i=0; i<getLargeur();i++) {
//				//System.out.print(i);
//			System.out.print(" |  ");
//			for(int j=0; j<getLongueur();j++) {
//				if(j==0) {
//					System.out.print(i+"  |   ");
//				}
//				System.out.print(cases[j][i] +   "  | ");
//			}
//			System.out.println("\n");
//		}
//	}
	
	public void afficherGrille() {
		System.out.print("  |");
	for (int i = 0; i < 15; i++) {
		if (i>=0 && i<=9) {
			System.out.print(" ");
		}
		System.out.print(" " + i+ "|");	
	}
	System.out.println("\t");
	for(int i=0; i<getLargeur();i++) {
		System.out.println("--+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
		for(int j=0; j<getLongueur();j++) {
			if (j==0) {
				if (i>=0 && i<=9) {
				System.out.print(" ");
				}
				System.out.print(i +"|");
			}
			System.out.print(cases[j][i] +  "|");
		}
		System.out.println("\t");
	}
	System.out.println("--+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+");
	}
	
	public Case getCase(int x, int y) {
		return cases[x][y];
	}
	
	public void setCase(Case caseC, int x, int y) {
		this.cases[x][y].setBateau(caseC.getBateau());
		this.cases[x][y].setHasBeenShoot(caseC.isHasBeenShoot());
		this.cases[x][y].setHasHadBoat(caseC.isHasHadBoat());
	}
	
	public static int getLargeur() {
		return largeur;
	}

	public static int getLongueur() {
		return longueur;
	}
	
	@Override
	public String toString() {
		String s="je suis la";
		for(int i=0; i<getLongueur();i++) {
			if(i==0) {
				s+="     ";
			}
			s+="     ";
			s+=i+" ";
		}
		s+=" \n";
		
		for(int i=0; i<getLargeur();i++) {
				//System.out.print(i);
			s+=" |  ";
			for(int j=0; j<getLongueur();j++) {
				if(j==0) {
				s+=i+"  |   ";
				}
				s+=cases[j][i] +   "  | ";
			}
			s+="\n";
		}
		return s;
	}

	public String afficherCaseFuseeEclairante(Grille grilleAdv, int x, int y) {
		
		String s="la fusee éclairante a été lancée \n";
		for(int i=0; i<getLongueur();i++) {
			if(i==0) {
				s+="     ";
			}
			s+="     ";
			s+=i+" ";
		}
		s+=" \n";
		
		for(int i=0; i<getLargeur();i++) {
			s+=" |  ";
			for(int j=0; j<getLongueur();j++) {
				if(j==0) {
				s+=i+"  |   ";
				}
				if((j>=x && j<=x+3) && (i>=y && i<=y+3)) {
						s+=grilleAdv.getCase(j, i)+   "  | ";
				}
				else {
					s+=cases[j][i] +   "  | ";
				}
			}
			s+="\n";
		}
		return s;
		
	}
	
}
