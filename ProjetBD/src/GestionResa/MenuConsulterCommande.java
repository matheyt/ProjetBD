package GestionResa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DonnePOJO.Personne;
import DonnePOJO.Reservation;
import Outils.LectureClavier;
import PackageDAO.Connexion;
import oracle.sql.TIMESTAMP;

public class MenuConsulterCommande {

	public void afficherCommandes(Connexion conn) {
		conn.connect();
		
		afficherListeClients(conn);
		int idClient = LectureClavier.lireEntier("Saisissez l'id du client");
		
		ArrayList<Reservation> reservations;
		reservations = recupReservations(conn, idClient);
		
		int indexCommande = 0;
		for(Reservation r: reservations)
		{
			System.out.print(indexCommande+":  ");
			System.out.println(r.toString());
			indexCommande++;
		}
		
		try {
			conn.getConn().commit();
			conn.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void afficherListeClients(Connexion conn) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Personne> result = new ArrayList<Personne>();
		int indexClients = 0;
		
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT Personne.idPerso, Personne.nom, Personne.prenom, Personne.nbHeuresVol FROM Personne JOIN Client ON Client.idPerso=Personne.idPerso");
			
			while(resultat.next())
			{
				result.add(new Personne(resultat.getInt("idPerso"), resultat.getString("nom"), resultat.getString("prenom"), resultat.getInt("nbHeuresVol")));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		for(Personne p : result) {
			System.out.println(indexClients+" : "+p.toString());
			indexClients++;
		}
	}

	private ArrayList<Reservation> recupReservations(Connexion conn, int idClient) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Reservation> result = new ArrayList<Reservation>();
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT * FROM Reservation WHERE idPerso = "+idClient);
			
			while(resultat.next())
			{
				result.add(new Reservation(resultat.getInt("noResa"), new TIMESTAMP(resultat.getString("dateResa")), resultat.getInt("prixTotal"), resultat.getInt("idPerso")));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
