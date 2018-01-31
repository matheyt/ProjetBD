package GestionVol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DonnePOJO.Avion;
import DonnePOJO.AvionFret;
import DonnePOJO.ReserverFret;
import DonnePOJO.Vol;
import DonnePOJO.VolFret;
import Outils.LectureClavier;
import PackageDAO.AvionFretDAO;
import PackageDAO.Connexion;
import PackageDAO.ReservationDAO;
import PackageDAO.ReserverFretDAO;
import PackageDAO.VolDAO;
import PackageDAO.VolFretDAO;
import oracle.sql.TIMESTAMP;

public class MenuSupprVol {
	
	public void SupressionVol(Connexion conn)
	{
		conn.connect();
		try {
			conn.getConn().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Saisissez le numero de vol que vous souhaitez modifier");
		// saisie numero vol
		String noVol = LectureClavier.lireChaine();
		
		System.out.println("Saisissez la date du vol que vous souhaitez modifier");
		// saisie date vol
		TIMESTAMP dateVol = new TIMESTAMP(LectureClavier.lireChaine());
		
		// initilisation des DAO
		VolDAO VDao = new VolDAO(conn.getConn());
		ReserverFretDAO ResaDao = new ReserverFretDAO(conn.getConn());
		ReservationDAO resD = new ReservationDAO(conn.getConn());
		VolFretDAO vFDAO = new VolFretDAO(conn.getConn());
		
		//recupération des données du vol a supprimer
		Object[] t = new Object[2];
		t[0]=noVol;
		t[1]=dateVol;
		Vol v = VDao.find(t);
		
		
		VolFret vf = vFDAO.find(t);
		if (vf!=null)
		{
			ArrayList<VolFret> volFSim;
			volFSim = RecupVolFretSimilaire(v,conn,vf);
			ArrayList<ReserverFret> resaF;
			resaF = RecupResaFret(v,conn);
			
			if(volFSim.size()!=0)
			{
				System.out.println("modification des reservation ...");
				ReserverFret resaFr;
				VolFret currVF= volFSim.get(0);
				for(int i=0;i<resaF.size();i++ )
				{
					resaFr= new ReserverFret(resaF.get(i).getVolumeResa(), resaF.get(i).getPoidsResa(),resaF.get(i).getNoResa(), currVF.getNoVol(),currVF.getDateDepart());
					ResaDao.create(resaFr);
					ResaDao.delete(resaF.get(i));
				}
			}
			else
			{
				Object[] tab =new Object[1];
				for(int i=0;i<resaF.size();i++ )
				{
					ResaDao.delete(resaF.get(i));
					tab[0]=resaF.get(i).getNoResa();
					resD.delete(resD.find(tab));
				}
			}
			
		}
		else{
			RecupVolPassagerSim(v,conn);
		}
		System.out.println("Suppresion du vol ...");
		VDao.delete(v);
		System.out.println("suppression terminé!!");
		try {
			conn.getConn().commit();
			conn.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<ReserverFret> RecupResaFret(Vol v, Connexion conn) {
		Statement requete;
		ResultSet resultat;
		ArrayList<ReserverFret> result = new ArrayList<ReserverFret>();
		
		
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("Select * from ReserverFret where noVol='"+v.getNoAvion()+"' And datedepart = TIMESTAMP '"+v.getDateDepart()+"'");
			while(resultat.next())
			{
				result.add(new ReserverFret(resultat.getInt("VolumeResa"),resultat.getInt("poidsResa"),resultat.getInt("noResa"),resultat.getString("noVol"), new TIMESTAMP(resultat.getString("datedepart"))));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private void RecupVolPassagerSim(Vol v, Connexion conn) {
		// TODO Auto-generated method stub
		
	}

	private ArrayList<VolFret> RecupVolFretSimilaire(Vol v, Connexion conn,VolFret vf) {
		Statement requete;
		ResultSet resultat;
		ArrayList<VolFret> result = new ArrayList<VolFret>();
		
		
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT VolFret.volumeMin, VolFret.poidsMin, VolFret.prixMetreCube, VolFret.noVol, VolFret.datedepart "
					+ "from volfret JOIN Vol ON volFret.noVol = Vol.noVol ANd volFret.datedepart= Vol.datedepart "
					+ "Where Vol.aeroOrigine = '"+v.getAeroOrigine()+"' AND Vol.aeroDestination = '"+v.getAeroDestination()+"' AND arrive=0");
			while(resultat.next())
			{
				result.add(new VolFret(resultat.getInt("volumeMin"), resultat.getInt("poidsMin"), resultat.getFloat("prixMetreCube"), resultat.getString("noVol"),new TIMESTAMP(resultat.getString("datedepart"))));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	}
	
	//consultaion autre vol similaire
	
	/*si Vol Passager
	 * 
	 * si autre vol similaire transfere des reservation ddes place ver autre place
	 * 
	 * si vol fret
	 * si autre vol similaire transfere resa fret vers nouveau vol*/
	
	//suppression des ancien resa
	
	//suppression des Voltype
	
	//suppression des vol
	
	//indiqué que le vol est supprimé

