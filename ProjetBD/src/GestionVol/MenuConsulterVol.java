package GestionVol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DonnePOJO.Vol;
import PackageDAO.Connexion;
import oracle.sql.TIMESTAMP;

public class MenuConsulterVol {

	public void afficherVols(Connexion conn) {
		conn.connect();
		try {
			conn.getConn().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		ArrayList<Vol> vols;
		vols = recupVols(conn);
		
		int indexVol = 0;
		for(Vol v: vols)
		{
			System.out.print(indexVol+":  ");
			System.out.println(v.toString());
			indexVol++;
		}
		
		try {
			conn.getConn().commit();
			conn.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<Vol> recupVols(Connexion conn) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Vol> result = new ArrayList<Vol>();
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT * FROM Vol");
			
			while(resultat.next())
			{
				result.add(new Vol(resultat.getString("noVol"), new TIMESTAMP(resultat.getString("datedepart")), resultat.getString("aeroOrigine"), resultat.getString("aeroDestination"), resultat.getInt("duree")
						, resultat.getInt("distance"), resultat.getInt("arrive"), resultat.getInt("noAvion")));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
