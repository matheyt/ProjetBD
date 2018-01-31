package PackageDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DonnePOJO.*;
import oracle.sql.TIMESTAMP;

public class ReservationDAO extends DAO<Reservation> {

	public ReservationDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Reservation obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("INSERT INTO Reservation (noResa, dateResa, prixTotal, idPerso) VALUES (" + obj.getNoResa()+", TIMESTAMP '" + obj.getDateResa()+"', "+obj.getPrixTotal()
			        +", "+obj.getIdPerso()+")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Reservation obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("DELETE FROM Reservation WHERE noResa = " + obj.getNoResa());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Reservation obj) {
		try {
			ResultSet result = this.connect.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_UPDATABLE).executeQuery("UPDATE Reservation SET dateResa = TIMESTAMP '" + obj.getDateResa()+"', prixTotal = "+obj.getPrixTotal()
			        +", idPerso = "+obj.getIdPerso()
			        +" WHERE noResa = "+obj.getNoResa());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Reservation find(Object[] tab) {
		int noRes = (int) tab[0];
		Reservation res = null;
		try {
		      ResultSet result = this.connect.createStatement(
		        ResultSet.TYPE_SCROLL_INSENSITIVE,
		        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Reservation WHERE noResa = " + noRes);
		      if(result.first())
		      {
		    	  res = new Reservation(result.getInt("noResa"),new TIMESTAMP(result.getDate("dateResa")),result.getInt("prixTotal"),result.getInt("idPerso"));
		      }
		} catch (SQLException e) {

		      e.printStackTrace();
		    }
		return res;
	}

}
