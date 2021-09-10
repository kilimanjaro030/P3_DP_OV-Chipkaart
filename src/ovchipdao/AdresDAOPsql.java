package ovchipdao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


public class AdresDAOPsql implements AdresDAO {
    private Connection conn;
    public AdresDAOPsql(Connection connection) {
        this.conn = connection;
    }

    public static Connection getConnection() throws SQLException{
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "Stabilo12");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public boolean save(Adres adres) {
        boolean result = false;
        try(Connection conn = getConnection()){
            PreparedStatement pt = conn.prepareStatement("INSERT INTO adres (adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES (?, ?, ?, ?, ?, ?)");
            pt.setInt(1,adres.getId());
            pt.setString(2, adres.getPostcode());
            pt.setString(3, adres.getHuisnummer());
            pt.setString(4, adres.getStraat());
            pt.setString(5, adres.getWoonplaats());
            pt.setInt(6, adres.getReiziger().getId());
            pt.executeUpdate();
            result = true;
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return result;
    }
    public boolean update(Adres adres) {
        boolean result = false;
        try(Connection conn = getConnection()){
            PreparedStatement pt = conn.prepareStatement("UPDATE adres SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ?" + " WHERE reiziger_id = '"+ adres.getId() + "'");
            pt.setInt(1,adres.getId());
            pt.setString(2, adres.getPostcode());
            pt.setString(3, adres.getHuisnummer());
            pt.setString(4, adres.getStraat());
            pt.setString(5, adres.getWoonplaats());
            pt.setInt(6, findByReiziger(adres.getReiziger()).getId());
            pt.executeUpdate();
            result = true;
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return result;
    }
//    public boolean delete(Adres adres) {
//    }
    public Adres findByReiziger(Reiziger reiziger){
        Adres adres = null;
        String query = "SELECT adres_id, postcode, huisnummer, straat, woonplaats FROM adres WHERE reiziger_id = '"+ reiziger.getId() + "'";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int adres_id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer= rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                adres = new Adres(adres_id,postcode,huisnummer,straat,woonplaats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adres;
    }
    public List<Adres> findAll() {
        List<Adres> results = new ArrayList<Adres>();
        try(Connection conn = getConnection()){
            String query = "SELECT * FROM adres";
            Statement pt = conn.createStatement();
            ResultSet result = pt.executeQuery(query);
            while (result.next()) {
                int Aid = result.getInt(1);
                String postcode = result.getString(2);
                String huisnummer = result.getString(3);
                String straat = result.getString(4);
                String woonplaats = result.getString(5);

                results.add(new Adres (Aid,postcode,huisnummer,straat,woonplaats));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return results;
    }
}
