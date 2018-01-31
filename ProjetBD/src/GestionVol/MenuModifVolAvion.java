package GestionVol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DonnePOJO.Avion;
import DonnePOJO.Vol;
import Outils.LectureClavier;
import PackageDAO.Connexion;
import PackageDAO.VolDAO;

public class MenuModifVolAvion {

	public void mainModifVolAvion(Connexion conn, Vol vol) {
		conn.connect();
		try {
			conn.getConn().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//conn.connect();
		int noAvion = LectureClavier.lireEntier("Saisissez le numero du nouvel avion choisi");
		
		afficherAvionsDisponibles(conn, noAvion, vol);
		
		modifierVol(conn, noAvion, vol);
		
		System.out.println("Vous avez modifie l'avion du vol");
		System.out.println(vol.toString());
		try {
			conn.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void modifierVol(Connexion conn, int noAvion, Vol vol) {
		VolDAO volDAO = new VolDAO(conn.getConn());
		vol.setNoAvion(noAvion);
		volDAO.update(vol);
	}

	private void afficherAvionsDisponibles(Connexion conn, int noAvion, Vol vol) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Avion> result = new ArrayList<Avion>();
		
		conn.connect();
		
		try {
			requete = conn.getConn().createStatement();
			// si avion fret
			resultat = requete.executeQuery("SELECT noAvion, noModele FROM Avion WHERE noAvion IN "
					+ "(SELECT noAvion FROM Vol JOIN AvionFret ON Vol.noAvion=AvionFret.noAvion WHERE [] = "+vol.getAeroOrigine()+" AND arrive=1"
							+ "AND volumeMax >= [] AND poidsMax >= []");
			
			// si avion passager
			// resultat = ...
			
			while(resultat.next())
			{
				result.add(new Avion(resultat.getInt("noAvion"),resultat.getInt("rayon"),resultat.getString("noModele")));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		// affichage des avions
		int indexA = 0;
		for(Avion a: result)
		{
			System.out.print(indexA+":  ");
			System.out.println(a.toString());
			indexA++;
		}
				
	}
}
