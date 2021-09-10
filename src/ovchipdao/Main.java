package ovchipdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.Date;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        ReizigerDAO rdao = new ReizigerDAOPsql(getConnection(connection));
        testReizigerDAO(rdao);
        closeConnection(getConnection(connection));
        AdresDAO adao = new AdresDAOPsql(getConnection(connection));
        testAdresDAO(adao);
    }

    private static Connection getConnection(Connection connection) throws SQLException{
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "Stabilo12");
        return connection;
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode sluit onze database connectie met onze Postgres database
     *
     * @throws SQLException
     */
    private static void closeConnection(Connection connection) throws SQLException {
        connection.close();
        System.out.println("Close Connection");
    }
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");
        System.out.println();

        // Haal alle reizigers op uit de database
        System.out.println("\n---------- Haal alle reizigers op uit de database -------------");
        System.out.println();
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        System.out.println("\n---------- Maak een nieuwe reiziger aan en persisteer deze in de database -------------");
        System.out.println();
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // verwijder reiziger
        System.out.println("\n---------- verwijder reiziger -------------");
        System.out.println();
        String gebdatum = "1993-03-14";
        Reiziger hans = new Reiziger(78, "h", "", "boer", java.sql.Date.valueOf(gebdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(hans);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.delete(hans);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // update reiziger
        System.out.println("\n---------- update reiziger -------------");
        System.out.println();
        rdao.update(new Reiziger(77, "M", "", "Boers", java.sql.Date.valueOf(gbdatum)));
        System.out.println();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        // findbyid reiziger
        System.out.println("\n---------- findbyid reiziger -------------");
        System.out.println();
        System.out.println(rdao.findById(6));

        // findByGbdatum gbdatum
        System.out.println("\n---------- findByGbdatum reiziger -------------");
        System.out.println();
        reizigers = rdao.findByGbdatum("10-11-1999");

        for(Reiziger reiziger : reizigers){
            System.out.println(reiziger);
        }
    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        // alle Adressen op uit de database
        System.out.println("\n---------- alle Adressen op uit de database -------------");
        System.out.println();
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // nieuwe adres aanmaken
        System.out.println("\n---------- nieuwe adres aanmaken -------------");
        System.out.println();

        String mehmetBD = "1999-11-10";
        Reiziger mehmet = new Reiziger(77, "M.A", "", "Bayram", java.sql.Date.valueOf(mehmetBD));
        Adres Padualaan = new Adres(12,"3535SB","31","Padualaan","Utrecht");
        Padualaan.setReiziger(mehmet);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " reizigers reiziger toegevoegd.\n");
        adao.save(Padualaan);

        System.out.println("\n---------- FindByReiziger -------------");
        System.out.println();
        String hansBD = "1945-08-01";
        Reiziger Hans = new Reiziger(5,"H.","","Klaasen",java.sql.Date.valueOf(hansBD));
        Adres add = adao.findByReiziger(Hans);
        System.out.println(add);
    }
}