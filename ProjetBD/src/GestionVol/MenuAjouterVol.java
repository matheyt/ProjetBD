package GestionVol;

import java.sql.*;
import java.util.ArrayList;

import DonnePOJO.Assure;
import DonnePOJO.Avion;
import DonnePOJO.Classe;
import DonnePOJO.Modele;
import DonnePOJO.Personne;
import DonnePOJO.Place;
import DonnePOJO.Position;
import DonnePOJO.Vol;
import DonnePOJO.VolFret;
import DonnePOJO.VolPassager;
import Outils.LectureClavier;
import PackageDAO.AssureDAO;
import PackageDAO.Connexion;
import PackageDAO.ModeleDAO;
import PackageDAO.PlaceDAO;
import PackageDAO.VolDAO;
import PackageDAO.VolFretDAO;
import PackageDAO.VolPassagerDAO;
import oracle.sql.TIMESTAMP;

public class MenuAjouterVol {
	
	
	public void AfficherMenu(Connexion conn)
	{
		conn.connect();
		try {
			conn.getConn().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		System.out.println("Veuillez saisir votre choix de type de VOl");
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("| 1 . Vol Fret ## Terminé");
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("| 2 . Vol Passager ## non terminé");
		System.out.println("------------------------------------------------------------------------------");
		
		
		String choixType;
		choixType = LectureClavier.lireChaine();
		switch (choixType)
		{
		case "1":
			ajouterVolFret(conn);break;
		case "2":
			ajouterVolPassager(conn);break;
		}
		
		try {
			conn.getConn().commit();
			conn.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ajouterVolFret(Connexion conn) {
		System.out.println("Entree le numero de Vol");
		String noVol;
		noVol = LectureClavier.lireChaine();
		// Entree La date du Vol
		System.out.println("Entree la Date du Vol");
		TIMESTAMP dt;
		dt = new TIMESTAMP(LectureClavier.lireChaine());
		// Entree le aeroOrigine
		System.out.println("Entree l aeroport Origine du Vol");
		String aeroOrigine;
		aeroOrigine = LectureClavier.lireChaine();
		// Entree le aeroDestination
		System.out.println("Entree l aeroport Destination du Vol");
		String aeroDestination;
		aeroDestination = LectureClavier.lireChaine();
		// Entree le aeroDestination
		int distance;
		distance = LectureClavier.lireEntier("Entree la distance de Vol");
		
		int volume = LectureClavier.lireEntier("Entree le nombre minimun de Volume");
		int Poids= LectureClavier.lireEntier("Entree le nombre minimun de Poids");
		
		Avion a;
		
		ArrayList<Avion> Av;
		Av = recupAvionFretDispo(conn, dt, noVol, aeroOrigine, volume, Poids, distance);
		System.out.println("liste des Avions Disponible");
		System.out.println("---------------------------------------");
		
		if(Av.size()==0)
		{
			System.out.println("aucun avion dispo");
		}
		else {
			//choix de l Avion
			AfficherAvion(Av);
			System.out.println("---------------------------------------");
			int indAvion=  LectureClavier.lireEntier("veuiller choisir votre avion");
			a = Av.get(indAvion);
			Modele m;
			ModeleDAO mD = new ModeleDAO(conn.getConn());
			Object[] t = new Object[1];
			t[0]=a.getNoModele();
			m = mD.find(t);
			
			//choix des pilotes
			ArrayList<Personne> pl;
			pl = recupPiloteDispo(conn, dt,a);
			System.out.println("liste des Pilotes Disponible");
			System.out.println("---------------------------------------");
			if (pl.size()<=m.getNbPilotes())
			{
				System.out.println("Pas assez de Pilote");
			}
			else {
				AfficherPilotes(pl);
				System.out.println("---------------------------------------");
				System.out.println("veuiller choisir vos "+m.getNbPilotes()+" Pilotes");
				System.out.println("---------------------------------------");
				// choisir Pilote
				ArrayList<Personne> ap = new ArrayList<Personne>();
				for(int i=0; i<m.getNbPilotes();i++)
				{
					ap.add(pl.get(LectureClavier.lireEntier("entrer le pilote num "+i)));
				}
				
				
				//creation du Vol
				
				Vol v = new Vol(noVol,dt,aeroOrigine,aeroDestination,distance,0,a.getNoAvion());
				VolDAO volD = new VolDAO(conn.getConn());
				volD.create(v);
				
				//creation de Vol Fret
				VolFret volF=new VolFret(volume, Poids, 15, noVol, dt);
				VolFretDAO volFD = new VolFretDAO(conn.getConn());
				volFD.create(volF);
				
				//ajoute des pilote dans la tabla assure
				ajouterPilotes(ap,noVol,dt,conn);
				
				System.out.println("Le Vol a était creer");
		}
		}
		
	
	}
	

	public void ajouterVolPassager(Connexion conn) {
		System.out.println("Entree le numero de Vol");
		String noVol;
		noVol = LectureClavier.lireChaine();
		// Entree La date du Vol
		System.out.println("Entree la Date du Vol");
		TIMESTAMP dt;
		dt = new TIMESTAMP(LectureClavier.lireChaine());
		// Entree le aeroOrigine
		System.out.println("Entree l aeroport Origine du Vol");
		String aeroOrigine;
		aeroOrigine = LectureClavier.lireChaine();
		// Entree le aeroDestination
		System.out.println("Entree l aeroport Destination du Vol");
		String aeroDestination;
		aeroDestination = LectureClavier.lireChaine();
		// Entree le aeroDestination
		int distance;
		distance = LectureClavier.lireEntier("Entree la distance de Vol");
		
		int nbPlEco = LectureClavier.lireEntier("Entree le nombre minimun en classe Eco");
		int nbPlAf= LectureClavier.lireEntier("Entree le nombre minimun en classe Affaire");
		int nbPlPr = LectureClavier.lireEntier("Entree le nombre minimun en classe Premiere");
		
		int nbPlace = nbPlAf+nbPlEco+nbPlPr;
		
		Avion a;
		
		//choix de l'avion
		ArrayList<Avion> Av;
		Av = recupAvionPassagerDispo(conn, dt, noVol, aeroOrigine, nbPlace, distance);
		System.out.println("liste des Avions Disponible");
		System.out.println("---------------------------------------");
		if (Av.size()==0)
		{
			System.out.println("pas d avion dispo");
		}
		else {
			AfficherAvion(Av);
			System.out.println("---------------------------------------");
			
			int indAvion=  LectureClavier.lireEntier("veuiller choisir votre avion");
			
			a = Av.get(indAvion);
			Modele m;
			ModeleDAO mD = new ModeleDAO(conn.getConn());
			Object[] t = new Object[1];
			t[0]=a.getNoModele();
			m = mD.find(t);
			
			//choix des pilotes
			ArrayList<Personne> pl;
			pl = recupPiloteDispo(conn, dt,a);
			
			System.out.println("liste des Pilotes Disponible");
			System.out.println("---------------------------------------");
			
			if (pl.size()< m.getNbPilotes())
			{
				System.out.println("pas assez de pilote dispo");
			}
			else
			{
				AfficherPilotes(pl);
				
				System.out.println("---------------------------------------");
				System.out.println("veuiller choisir vos "+m.getNbPilotes()+" Pilotes");
				System.out.println("---------------------------------------");
				
				ArrayList<Personne> ap = new ArrayList<Personne>();
				for(int i=0; i<m.getNbPilotes();i++)
				{
					ap.add(pl.get(LectureClavier.lireEntier("entrer le pilote num "+i)));
				}
				
				//choix Hotess
				ArrayList<Personne> ph;
				ph = recupHotesseDispo(conn, dt);
				System.out.println("liste des Hotesse Disponible");
				System.out.println("---------------------------------------");
				if (ph.size()==0)
				{
					System.out.println("pas de pilote dispo");
				}
				else
				{
					AfficherHotesse(ph);
					System.out.println("---------------------------------------");
					int nbHot;
					nbHot = LectureClavier.lireEntier("Conbien d'Hotesse voulait vous?");
					System.out.println("veuiller choisir vos Hotesse");
					System.out.println("---------------------------------------");
					ArrayList<Personne> aHot = new ArrayList<Personne>();
					for(int i=0; i<nbHot;i++)
					{
						aHot.add(pl.get(LectureClavier.lireEntier("entrer l hotesse num "+i)));
					}
					
					//creation du nouveau Vol
					
					Vol v = new Vol(noVol,dt,aeroOrigine,aeroDestination,distance,0,a.getNoAvion());
					VolDAO volD = new VolDAO(conn.getConn());
					volD.create(v);
					
					//création du volPasssager
					VolPassager volP=new VolPassager(nbPlEco, nbPlPr, nbPlAf, noVol, dt);
					VolPassagerDAO volPD = new VolPassagerDAO(conn.getConn());
					volPD.create(volP);
					
					//creation des places du Vol
					creerPlaces(m,noVol,dt,conn);
					//ajoute des pilote dans la table assure
					ajouterPilotes(ap,noVol,dt,conn);
					//ajoute des Hotesse dans la table assure
					ajouterHotesse(aHot,noVol,dt,conn);
					System.out.println("Le Vol a était creer");
			
				}
			}
		}
	
	}

	private void creerPlaces(Modele m,String noVol,TIMESTAMP dt, Connexion conn) {
		PlaceDAO PlD= new PlaceDAO(conn.getConn());
		for(int i =0; i<m.getNbPlacesEco();i++)
		{
			Position pos;
			pos = determinePosition(i);
			PlD.create(new Place(0, "E"+i, Classe.Eco, pos, 125, noVol, dt, 0));
		}
		for(int i =0; i<m.getNbPlacesAffaire();i++)
		{
			Position pos;
			pos = determinePosition(i);
			PlD.create(new Place(0, "A"+i, Classe.Affaire, pos, 135, noVol, dt, 0));
		}
		for(int i =0; i<m.getNbPlacesPremiere();i++)
		{
			Position pos;
			pos = determinePosition(i);
			PlD.create(new Place(0, "P"+i, Classe.Premiere, pos, 150, noVol, dt, 0));
		}
		
	}

	public Position determinePosition(int i) {
		Position pos;
		if(i%3==0)
		{
			pos= Position.Centre;
		}
		if(i%3==1)
		{
			pos= Position.Couloir;
		}
		else
		{
			pos =Position.Hublot;
		}
		return pos;
	}

	private void ajouterHotesse(ArrayList<Personne> aHot,String noVol,TIMESTAMP dt, Connexion conn) {
		AssureDAO assureD = new AssureDAO(conn.getConn());
		for(Personne p:aHot)
		{	
			assureD.create(new Assure(p.getIdPerso(), noVol, dt));
		}
	}

	private void ajouterPilotes(ArrayList<Personne> ap,String noVol,TIMESTAMP dt,Connexion conn) {
		AssureDAO assureD = new AssureDAO(conn.getConn());
		for(Personne p:ap)
		{	
			assureD.create(new Assure(p.getIdPerso(), noVol, dt));
		}	
	}

	private void AfficherHotesse(ArrayList<Personne> ph) {
		int indexP = 0;
		for(Personne p: ph)
		{
			System.out.print(indexP+":  ");
			System.out.println(p.toString());
			indexP++;
		}
		
	}

	private ArrayList<Personne> recupHotesseDispo(Connexion conn, TIMESTAMP dt) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Personne> result = new ArrayList<Personne>();
		

		
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT Hotesse.idPerso, Personne.nom, Personne.prenom, Personne.nbHeuresVol FROM Hotesse join Personne on Personne.idPerso=Hotesse.idPerso WHERE Hotesse.idPerso NOT IN (select * From assure join Vol on assure.noVol=Vol.noVol And assure.datedepart=Vol.datedepart where Vol.arrive=false AND Vol.datedepart > TIMESTAMP '"+dt+"')");
			while(resultat.next())
			{
				result.add(new Personne(resultat.getInt("idPerso"),resultat.getString("nom"),resultat.getString("prenom"),resultat.getInt("nbHeuresVol")));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private ArrayList<Personne> recupPiloteDispo(Connexion conn, TIMESTAMP dt, Avion a) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Personne> result = new ArrayList<Personne>();
		
		
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT Pilote.idPerso, Personne.nom, Personne.prenom, Personne.nbHeuresVol FROM Pilote"
					+" JOIN PERSONNEL ON PERSONNEL.idPerso=Pilote.idPerso"
					+" join Personne on Personne.idPerso=PERSONNEL.idPerso"
					+" join saitPiloter on saitPiloter.idPerso=Pilote.idPerso"
					+" WHERE saitPiloter.noModele= '"+a.getNoModele()+"' AND Pilote.idPerso NOT IN ("
					+" select idPerso From assure join Vol on assure.noVol=Vol.noVol And assure.datedepart=Vol.datedepart"
					+" where Vol.arrive=0 AND Vol.datedepart > TIMESTAMP '"+dt+"')");
			while(resultat.next())
			{
				result.add(new Personne(resultat.getInt("idPerso"),resultat.getString("nom"),resultat.getString("prenom"),resultat.getInt("nbHeuresVol")));
			}	
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private void AfficherPilotes(ArrayList<Personne> pl) {
		int indexP = 0;
		for(Personne p: pl)
		{
			System.out.print(indexP+":  ");
			System.out.println(p.toString());
			indexP++;
		}
		
	}

	private void AfficherAvion(ArrayList<Avion> av) {
		int indexA = 0;
		for(Avion a: av)
		{
			System.out.print(indexA+":  ");
			System.out.println(a.toString());
			indexA++;
		}
		
	}

	public ArrayList<Avion> recupAvionFretDispo(Connexion conn, TIMESTAMP dt, String noVol, String aeroOrigine, int volume, int poids, int rayon) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Avion> result = new ArrayList<Avion>();
		
		
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT Avion.noAvion, Avion.rayon, Avion.noModele FROM Vol"
					+" JOIN AVION ON AVION.noAvion = Vol.noAvion"
					+" JOIN AvionFret ON AvionFret.noAvion = AVION.noAvion"
					+" WHERE Vol.datedepart IN(SELECT max(Vol.dateDepart) FROM Vol GROUP BY noAvion) AND aeroDestination= '"+aeroOrigine+"' AND arrive = 1"
					+" AND AvionFret.volumeMax >= "+volume+" AND AvionFret.poidsMax >= "+poids+" AND Avion.rayon >= "+ rayon);

			while(resultat.next())
			{
				result.add(new Avion(resultat.getInt("noAvion"), resultat.getInt("rayon"), resultat.getString("noModele")));
			}	
			
			resultat = requete.executeQuery("SELECT Avion.noAvion, Avion.rayon, Avion.noModele FROM Avion"
					+" JOIN AvionFret ON AvionFret.noAvion = AVION.noAvion"
					+" WHERE Avion.noAvion NOT IN( Select noAvion From VOL) AND Avion.rayon >= "+ rayon);

			while(resultat.next())
			{
				result.add(new Avion(resultat.getInt("noAvion"), resultat.getInt("rayon"), resultat.getString("noModele")));
			}
			
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Avion> recupAvionPassagerDispo(Connexion conn, TIMESTAMP dt, String noVol, String aeroOrigine, int nbPlace, int rayon) {
		Statement requete;
		ResultSet resultat;
		ArrayList<Avion> result = new ArrayList<Avion>();
		
		
		try {
			requete = conn.getConn().createStatement();
			resultat = requete.executeQuery("SELECT Avion.noAvion, Avion.rayon, Avion.noModele FROM Vol"
				+" JOIN AVION ON AVION.noAvion = Vol.noAvion"
				+" JOIN AvionPassager ON AvionPassager.noAvion = AVION.noAvion"
				+" WHERE Vol.datedepart IN(SELECT max(Vol.dateDepart) FROM Vol GROUP BY noAvion) AND aeroDestination='"+aeroOrigine+"' AND arrive =1"
				+" AND AvionPassager.nbPlaces>="+nbPlace +" AND Avion.rayon >= "+ rayon);
			while(resultat.next())
			{
				result.add(new Avion(resultat.getInt("noAvion"), resultat.getInt("rayon"), resultat.getString("noModele")));
			}	
			
			resultat = requete.executeQuery("SELECT Avion.noAvion, Avion.rayon, Avion.noModele FROM Avion"
					+" JOIN AvionPassager ON AvionPassager.noAvion = AVION.noAvion"
					+" WHERE Avion.noAvion NOT IN( Select noAvion From VOL) AND Avion.rayon >= "+ rayon);

			while(resultat.next())
			{
				result.add(new Avion(resultat.getInt("noAvion"), resultat.getInt("rayon"), resultat.getString("noModele")));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
