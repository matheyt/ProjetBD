package GestionVol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import DonnePOJO.Personne;
import DonnePOJO.Vol;
import PackageDAO.Connexion;
import PackageDAO.PersonneDAO;
import PackageDAO.VolDAO;

public class MenuModifVolArrivee {

	public void mainModifVolArrivee(Connexion conn, Vol vol) {
		try {
			conn.getConn().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		conn.connect();
		
		modifierVolArrivee(conn, vol);
		
		modifierVolDuree(conn, vol);
		
		ajouterDureeVolPersonnesDuVol(conn, vol);
		
		System.out.println("Le vol a ete modifie comme ceci :");
		System.out.println(vol.toString());
		
		try {
			conn.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ajouterDureeVolPersonnesDuVol(Connexion conn, Vol vol) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Personne> result = new ArrayList<Personne>();
		
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT Personne.idPerso, Personne.nom, Personne.prenom, Personne.nbHeuresVol FROM Personne JOIN assure on assure.idPerso=Personne.idPerso "
					+ " JOIN Vol ON assure.noVol = Vol.noVol WHERE assure.noVol='"+vol.getNoVol()+"'");// a terminer
			while(resultat.next())
			{
				result.add(new Personne(resultat.getInt("idPerso"),resultat.getString("nom"),resultat.getString("prenom"),resultat.getInt("nbHeuresVol")));
			}
			modifierNbHeuresVol(conn, result, vol);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void modifierNbHeuresVol(Connexion conn, ArrayList<Personne> result, Vol vol) {
		for(Personne p : result) {
			PersonneDAO personneDAO = new PersonneDAO(conn.getConn());
			p.setNbHeuresVol(p.getNbHeuresVol()+vol.getDuree());
			personneDAO.update(p);
		}
		
	}

	private void modifierVolDuree(Connexion conn, Vol vol) {
		VolDAO volDAO = new VolDAO(conn.getConn());
		long d = Calendar.getInstance().getTime().getTime();
		try {
			d = d-vol.getDateDepart().dateValue().getTime();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int duree = (int) d;
		vol.setDuree(duree);
		volDAO.update(vol);
	}

	private void modifierVolArrivee(Connexion conn, Vol vol) {
		VolDAO volDAO = new VolDAO(conn.getConn());
		vol.setArrive(1);
		volDAO.update(vol);
	}
}
