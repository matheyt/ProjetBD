package GestionVol;

import Outils.LectureClavier;
import PackageDAO.*;

public class MenuVol {
	
	private Connexion conn;
	private LectureClavier lecture;
	private boolean quit;
	
	public MenuVol()
	{
		this.conn = new Connexion();
		this.lecture = new LectureClavier();
		this.quit = false;
	}

	public void afficherMenu() {
		
		while(quit==false)
		{
			System.out.println("Veuillez saisir votre choix");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 1 . Consulter les Vol en cour ## Terminé");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 2 . Ajouter un nouveau Vol ## En cours");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 3 . Modifier un Vol ## En cours");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 4 . Supprimer un Vol ## En cours");
			System.out.println("------------------------------------------------------------------------------");
			System.out.println("| 5 . Quitter");
			System.out.println("------------------------------------------------------------------------------");
	
			
			String choix;
			choix = this.lecture.lireChaine();
			this.choixFonct(choix);
		}

	}

	private void choixFonct(String choix) {
		switch(choix)
		{
		case "1" :
			MenuConsulterVol mC = new MenuConsulterVol();
			mC.afficherVols(this.conn);break;
		case "2" :
			MenuAjouterVol mA = new MenuAjouterVol();
			mA.AfficherMenu(this.conn);break;
			
		case "3" :
			MenuModifierVol mM = new MenuModifierVol();
			mM.afficherMenuModifier(conn);break;
		case "4" :
			MenuSupprVol mS = new MenuSupprVol();
			mS.SupressionVol(conn);break;
		case "5" :
			this.quit=true; break;
		}
		
	}

}
