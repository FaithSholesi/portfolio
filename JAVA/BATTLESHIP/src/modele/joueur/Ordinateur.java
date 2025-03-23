package modele.joueur;

import java.util.ArrayList;

import modele.bateau.Bateau;
import modele.grille.Coordonn�es;
import modele.grille.Grille;

public class Ordinateur extends Joueur {
	
	public Ordinateur() {
		super();
	}	

	
	public void actionOrdi(Grille grilleAdv) {
	//int action= (int) Math.floor(Math.random() * 2);
		int action=0;
		System.out.println(action +" action");
		System.out.println(this.getBateaux().size());
		if(action==0) {
			int x=0;
			int y=0;
			System.out.println("tirer");
			
			if(this.searchBateauTouch�Adv().size()!=0) {
				int hasard=(int) Math.floor(Math.random() * this.searchBateauTouch�Adv().size());
				int margeRecherche=(int)Math.floor(Math.random() * (2 - 1 + 1) + 1);
				x=this.searchBateauTouch�Adv().get(hasard).getX()+margeRecherche;
				margeRecherche=(int)Math.floor(Math.random() * (2 - 1 + 1) + 1);
				y=this.searchBateauTouch�Adv().get(hasard).getY()+margeRecherche;
				System.out.println("coord"+y+""+x);
			}
			else {
				x=(int) Math.floor(Math.random() * Grille.getLongueur());
				y=(int) Math.floor(Math.random() * Grille.getLargeur());
			}
			
			int reponseBateau=(int)Math.floor(Math.random() * (this.getBateaux().size() - 1 + 1) + 1);
			this.getByReference(reponseBateau).tirer(x, y,this.getHistorique(), grilleAdv, 0, false);
			System.out.println(x+"  "+y);
			System.out.println(this.getByReference(reponseBateau).getType());
		}
		else {
			System.out.println("deplacer");
			int indiceBateau=0;
			if(this.searchBateauTouch�Ordi().size()!=0) {
				System.out.println("taille"+this.searchBateauTouch�Ordi().size());
				int hasard=(int) Math.floor(Math.random() * this.searchBateauTouch�Ordi().size());
				indiceBateau=this.searchBateauTouch�Ordi().get(hasard);
				System.out.println("hasard"+indiceBateau+" "+hasard);
			}
			else {
				indiceBateau=(int)Math.floor(Math.random() * (10 - 1 + 1) + 1);
			}
			System.out.println(indiceBateau);
			int indiceDirection=(int) Math.floor(Math.random() * this.getByReference(indiceBateau).getDirections().size());
//			System.out.println(this.getByReference(indiceBateau).getType());
//			System.out.println(this.getByReference(indiceBateau).getDirections().get(indiceDirection));
//			System.out.println(indiceBateau + "ici"+indiceDirection);
				if(!super.checkBateau(indiceBateau, indiceDirection) || super.checkBateauTouch�(indiceBateau)) {
					this.actionOrdi(grilleAdv);
				}
				this.getByReference(indiceBateau).deplacer(indiceDirection, this.getGrilleJoueur());
			}
		}
	
	public ArrayList<Coordonn�es> searchBateauTouch�Adv() {
		ArrayList<Coordonn�es>listCoord=new ArrayList<Coordonn�es>();
		
		for(int i=0; i<Grille.getLargeur()-2;i++) {
			for(int j=0; j<Grille.getLongueur()-2;j++) {
				if (this.getHistorique().getCase(j, i).isHasBeenShoot() && this.getHistorique().getCase(j, i).isHasHadBoat()) {
					listCoord.add(this.getHistorique().getCase(j, i).getC());
				}
			}
		}
		return listCoord;
	}
	
	public ArrayList<Integer> searchBateauTouch�Ordi() {
		ArrayList<Integer>listRef=new ArrayList<Integer>();
		int margeRecherche=3;
		int x=0;
		int y=0;
		for(int i=1; i<=this.getBateaux().size();i++) {
			for(int k=0; k<margeRecherche;k++) {
				for(int j=0; j<margeRecherche;j++) {
					x=this.getByReference(i).getCoordonn�es().getX()+k;
					y=this.getByReference(i).getCoordonn�es().getY()+j;
					if(y<Grille.getLargeur()&&x<Grille.getLongueur()) {
						if(this.getGrilleJoueur().getCase(x,y).isHasBeenShoot()) {
							listRef.add(i);
						}
					}
				}	
			}
		}
		return listRef;
	}
		
}

	


