package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;

public class PersonneDAO extends DAO<Personne> {

	public PersonneDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Personne obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO Personne (idPerso, nom, prenom, nbHeuresVol) VALUES (" + obj.getIdPerso()+", '" + obj.getNom()+"', '"+obj.getPrenom()
			        +"', "+obj.getNbHeuresVol()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Personne obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM Personne WHERE idPerso = " + obj.getIdPerso());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Personne obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE Personne SET nom = '" + obj.getNom()+"', prenom = '"+obj.getPrenom()
			        +"', nbHeuresVol = "+obj.getNbHeuresVol()
			        +" WHERE idPerso = "+obj.getIdPerso());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Personne find(Object[] tab) {
		int idPers = (int) tab[0];
		Personne P = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Hotesse WHERE idPerso = " + idPers);
		      if(result.first())
		      {
		    	  P = new Personne(result.getInt("idPers"),result.getString("nom"),result.getString("prenom"),result.getInt("nbHeuresVol"));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();
		    }
		return P;
	}

}
