package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;

public class HotesseDAO extends DAO<Hotesse> {

	public HotesseDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Hotesse obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO Hotesse (secondeLangue, idPerso) VALUES ('" + obj.getSecondeLangue()+"', " + obj.getIdPerso()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Hotesse obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM Hotesse WHERE idPerso = " + obj.getIdPerso());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Hotesse obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE Hotesse SET secondeLangue = '" + obj.getSecondeLangue()
			        +"' WHERE idPerso = "+obj.getIdPerso());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Hotesse find(Object[] tab) {
		int idPers = (int) tab[0];
		Hotesse Ht = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Hotesse WHERE idPerso = " + idPers);
		      if(result.first())
		      {
		    	  Ht = new Hotesse(result.getInt("idPerso"),result.getString("secondeLangue"));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();
		    }
		return Ht;
	}

}
