package GestionResa;

import GestionVol.MenuAjouterVol;
import GestionVol.MenuConsulterVol;
import GestionVol.MenuModifierVol;
import GestionVol.MenuSupprVol;
import Outils.LectureClavier;
import PackageDAO.Connexion;

public class MenuResa {

	private Connexion conn;
	private LectureClavier lecture;
	private boolean quit;
	
	public MenuResa() {
		this.conn = new Connexion();
		this.lecture = new LectureClavier();
		this.quit = false;
	}
	
	public void InitMenu() {
		while(quit==false)
		{
			System.out.println("Veuillez saisir votre choix");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 1 . Consulter les commandes d'un client ## non terminé");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 2 . Creer une reservation ## non terminé");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 3 . Modifier une reservation ## non terminé");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 4 . Supprimer une reservation ## non terminé");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 5 . Quitter");
			System.out.println("------------------------------------------------------------------------------");
	
			// Saisir choix | String choix = ....
			
			String choix;
			choix = this.lecture.lireChaine();
			this.choixFonct(choix);
		}
	}
	
	private void choixFonct(String choix) {
		switch(choix)
		{
		case "1" :
			MenuConsulterCommande mC = new MenuConsulterCommande();
			mC.afficherCommandes(this.conn);break;
		/*case "2" :
			MenuAjouterVol mA = new MenuAjouterVol();
			mA.AfficherMenu(this.conn);break;
			
		case "3" :
			MenuModifierVol mM = new MenuModifierVol();
			mM.afficherMenuModifier(conn);break;
		case "4" :
			MenuSupprVol mS = new MenuSupprVol();break;
		case "5" :
			this.quit=true; break;*/
		}
		
	}

}
