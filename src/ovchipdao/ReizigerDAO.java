package ovchipdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger reiziger);
    public boolean update(Reiziger reiziger);
    public boolean delete(Reiziger reiziger);
    public Reiziger findById(int id);
    public List<Reiziger> findByGbdatum(String datum);
    public List<Reiziger> findAll();
}