package GestionVol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DonnePOJO.Assure;
import DonnePOJO.Hotesse;
import DonnePOJO.Pilote;
import DonnePOJO.Vol;
import Outils.LectureClavier;
import PackageDAO.AssureDAO;
import PackageDAO.Connexion;

public class MenuModifVolSupprPersonnel {
	public void mainModifVolSupprPersonnel(Connexion conn, Vol vol) {
		conn.connect();
		try {
			conn.getConn().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int choix = LectureClavier.lireEntier("Voulez-vous supprimer un pilote ou une hotesse ? (tapez 1 ou 2)");
		switch(choix) {
			case 1: supprimerPilote(conn, vol);break;
			case 2: supprimerHotesse(conn, vol);break;
		}
		
		try {
			conn.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void supprimerHotesse(Connexion conn, Vol vol) {
		afficherHotesses(conn, vol);
		int idHotesse = LectureClavier.lireEntier("Saisissez l'id de l'hotesse choisie");
		modifierVol(conn, vol, idHotesse);
		System.out.println("Vous avez supprimé une hotesse");
		System.out.println(vol.toString());
	}

	private void afficherHotesses(Connexion conn, Vol vol) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Hotesse> result = new ArrayList<Hotesse>();
		
		try {
			requete = conn.getConn().createStatement();
			
			resultat = requete.executeQuery("SELECT Hotesse.idPerso, Hotesse.secondeLangue FROM Hotesse JOIN assure ON assure.idPerso=Hotesse.idPerso"
					+ " JOIN Vol on Vol.noVol=assure.noVol WHERE Vol.noVol AND Vol.datedepart=assure.datedepart WHERE Vol.noVol = '"+vol.getNoVol()+"' AND Vol.datedepart=TIMESTAMP'"+vol.getDateDepart()+"'");
			
			while(resultat.next())
			{
				result.add(new Hotesse(resultat.getInt("idPerso"), resultat.getString("secondeLangue")));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		// affichage des hotesses
		int indexH = 0;
		for(Hotesse h: result)
		{
			System.out.print(indexH+":  ");
			System.out.println(h.toString());
			indexH++;
		}
	}

	private void supprimerPilote(Connexion conn, Vol vol) {
		afficherPilotes(conn, vol);
		int idPilote = LectureClavier.lireEntier("Saisissez l'id du pilote choisi");
		modifierVol(conn, vol, idPilote);
		System.out.println("Vous avez supprimé un pilote");
		System.out.println(vol.toString());
		
	}

	private void modifierVol(Connexion conn, Vol vol, int idPilote) {
		Object[] tab = new Object[3];
		tab[0] = idPilote;
		tab[1] = vol.getNoVol();
		tab[2] = vol.getDateDepart();
		AssureDAO assureDAO = new AssureDAO(conn.getConn());
		Assure assure = assureDAO.find(tab);
		assureDAO.delete(assure);
	}

	private void afficherPilotes(Connexion conn, Vol vol) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Pilote> result = new ArrayList<Pilote>();
		
		try {
			requete = conn.getConn().createStatement();
			
			resultat = requete.executeQuery("SELECT Pilote.idPerso FROM Pilote JOIN assure ON assure.idPerso=Pilote.idPerso"
					+ " JOIN Vol on Vol.noVol=assure.noVol AND Vol.datedepart=assure.datedepart WHERE Vol.noVol = '"+vol.getNoVol()+"' AND Vol.datedepart=TIMESTAMP'"+vol.getDateDepart()+"'");
			
			while(resultat.next())
			{
				result.add(new Pilote(resultat.getInt("idPerso")));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		// affichage des pilotes
		int indexP = 0;
		for(Pilote p: result)
		{
			System.out.print(indexP+":  ");
			System.out.println(p.toString());
			indexP++;
		}
	}

}
