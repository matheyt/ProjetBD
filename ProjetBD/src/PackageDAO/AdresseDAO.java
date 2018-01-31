package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;

public class AdresseDAO extends DAO<Adresse> {

	public AdresseDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Adresse obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO Adresse (idAdresse, noAdresse, rue, cpp, ville, pays, idPerso) VALUES (" + obj.getIdAdresse()+", " + obj.getNoAdresse()+", '"+obj.getRue()
			        +"', "+obj.getCpp()+", '"+obj.getVille()+"', '"+obj.getPays()+"', "+obj.getIdPerso()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Adresse obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM Adresse WHERE idAdresse = " + obj.getIdAdresse());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Adresse obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE Adresse SET noAdresse = " + obj.getNoAdresse()+", rue = '"+obj.getRue()
			        +"', cpp = "+obj.getCpp()+", ville = '"+obj.getVille()+"', pays = '"+obj.getVille()+"', idPerso = "+obj.getIdPerso()
			        +" WHERE idAdresse = "+obj.getIdAdresse());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Adresse find(Object[] tab) {
		int idA = (int) tab[0];
		Adresse A = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Adresse WHERE idAdresse = " + idA);
		      if(result.first())
		      {
		    	  A = new Adresse(result.getInt("idAdresse"), result.getInt("noAdresse"),result.getString("rue"), result.getInt("cpp"), result.getString("ville"),result.getString("pays"), result.getInt("idPerso"));
		      }
		}catch (SQLException e) {

			      e.printStackTrace();

			    }
		return null;
	}

}
