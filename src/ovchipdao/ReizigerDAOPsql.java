package ovchipdao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;
    public ReizigerDAOPsql(Connection connection) {
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

    public boolean save(Reiziger reiziger) {
        boolean result = false;
        try(Connection conn = getConnection()){
            PreparedStatement pt = conn.prepareStatement("INSERT INTO reiziger (reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum) VALUES (?, ?, ?, ?, ?)");
            pt.setInt(1,reiziger.getId());
            pt.setString(2, reiziger.getVoorletters());
            pt.setString(3, reiziger.getTussenvoegsel());
            pt.setString(4, reiziger.getAchternaam());
            pt.setDate(5, reiziger.getGeboortedatum());
            pt.executeUpdate();
            result = true;
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return result;
    }
    public boolean update(Reiziger reiziger) {
        boolean result = false;
        try(Connection conn = getConnection()){
            PreparedStatement pt = conn.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ?" + " WHERE reiziger_id = '"+ reiziger.getId() + "'");
            pt.setString(1, reiziger.getVoorletters());
            pt.setString(2, reiziger.getTussenvoegsel());
            pt.setString(3, reiziger.getAchternaam());
            pt.setDate(4, reiziger.getGeboortedatum());
            pt.executeUpdate();
            result = true;
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return result;
    }
    public boolean delete(Reiziger reiziger) {
        boolean result = false;
        try(Connection conn = getConnection()){
            PreparedStatement pt = conn.prepareStatement("DELETE from reiziger WHERE reiziger_id = '"+ reiziger.getId() + "'");
            pt.executeUpdate();
            result = true;
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return result;
    }
    public Reiziger findById(int id) {
        Reiziger reiziger = null;
        try(Connection conn = getConnection()){
            String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE reiziger_id = '"+ id + "'";
            Statement pt = conn.createStatement();
            ResultSet result = pt.executeQuery(query);
            while (result.next()) {
                int Rid = result.getInt(1);
                String voorletters = result.getString(2);
                String tussenvoegsel = result.getString(3);
                String achternaam = result.getString(4);
                Date geboortedatum = result.getDate(5);

                reiziger = new Reiziger(Rid, voorletters,tussenvoegsel,achternaam,geboortedatum);
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return reiziger;
    }
    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> res = new ArrayList<Reiziger>();
        try(Connection conn = getConnection()){
            String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE geboortedatum = '"+ datum + "'";
            Statement pt = conn.createStatement();
            ResultSet result = pt.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(1);
                String voorletters = result.getString(2);
                String tussenvoegsel = result.getString(3);
                String achternaam = result.getString(4);
                Date geboortedatum = result.getDate(5);

                res.add(new Reiziger (id,voorletters,tussenvoegsel,achternaam,geboortedatum) );
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return res;
    }
    public List<Reiziger> findAll() {
        List<Reiziger> results = new ArrayList<Reiziger>();
        try(Connection conn = getConnection()){
            String query = "SELECT * FROM reiziger";
            Statement pt = conn.createStatement();
            ResultSet result = pt.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(1);
                String voorletters = result.getString(2);
                String tussenvoegsel = result.getString(3);
                String achternaam = result.getString(4);
                Date geboortedatum = result.getDate(5);

                results.add(new Reiziger (id,voorletters,tussenvoegsel,achternaam,geboortedatum) );
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return results;
    }
}