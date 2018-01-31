package GestionVol;

import java.sql.Connection;
import java.sql.SQLException;

import DonnePOJO.Vol;
import Outils.LectureClavier;
import PackageDAO.Connexion;
import PackageDAO.VolDAO;

public class MenuModifVolAeroDestination {

	public void mainModifVolAeroDestination(Connexion conn, Vol vol) {
		try {
			conn.getConn().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		conn.connect();
		
		// saisie aeroport
		System.out.println("Saisissez le nouvel aeroport de destination");
		String aeroportDestination = LectureClavier.lireChaine();
		
		modifierVol(conn, aeroportDestination, vol);
		
		System.out.println(vol.toString());
		
		try {
			conn.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void modifierVol(Connexion conn, String aeroportDestination, Vol vol) {
		VolDAO volDAO = new VolDAO(conn.getConn());
		vol.setAeroDestination(aeroportDestination);
		volDAO.update(vol);
	}
}
