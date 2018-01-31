package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;

public class ClientDAO extends DAO<Client> {

	public ClientDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Client obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO Client (idPerso) VALUES (" + obj.getIdPerso()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Client obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM Client WHERE noVol = " + obj.getIdPerso());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Client obj) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Client find(Object[] tab) {
		int idPers = (int) tab[0];
		Client cl = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Client WHERE idPerso = " + idPers);
		      if(result.first())
		      {
		    	  cl = new Client(result.getInt("idPerso"));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();
		    }
		return cl;
	}

}
