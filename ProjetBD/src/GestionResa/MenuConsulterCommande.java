package GestionResa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DonnePOJO.Personne;
import DonnePOJO.Reservation;
import DonnePOJO.ReserverFret;
import Outils.LectureClavier;
import PackageDAO.Connexion;
import oracle.sql.TIMESTAMP;

public class MenuConsulterCommande {

	public void afficherCommandes(Connexion conn) {
		conn.connect();
		try {
			conn.getConn().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		afficherListeClients(conn);
		int idClient = LectureClavier.lireEntier("Saisissez l'id du client");
		
		ArrayList<ReserverFret> reservations;
		reservations = recupReservations(conn, idClient);
		
		int indexCommande = 0;
		for(ReserverFret r: reservations)
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

	private ArrayList<ReserverFret> recupReservations(Connexion conn, int idClient) {
		Statement requete;
		ResultSet resultat;
		ArrayList<ReserverFret> result = new ArrayList<ReserverFret>();
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT * FROM ReserverFret  Join Reservation ON Reservation.noResa=ReserverFret.noResa"
					+ " WHERE idPerso = "+idClient);
			
			while(resultat.next())
			{
				result.add(new ReserverFret(resultat.getInt("volumeResa"), resultat.getInt("poidsResa"), resultat.getInt("noResa"), resultat.getString("noVol"), new TIMESTAMP(resultat.getString("datedepart"))));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
