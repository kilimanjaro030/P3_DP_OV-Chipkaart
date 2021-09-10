package ovchipdao;

import java.sql.*;

public class Driver {
    public static void main(String[] args) {
        try {
            // 1. Get a connection to database
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "Stabilo12");

            // 2. Create a statement
            Statement myStmt = myConn.createStatement();

            // 3. Execute SQL query
            ResultSet myRs = myStmt.executeQuery("select * from reiziger");

            // 4. Process the result set
            while (myRs.next()) {
                if (myRs.getString("tussenvoegsel") == null ) {
                    System.out.println("#" + myRs.getString("reiziger_id") + ": " + myRs.getString("voorletters") + " " + myRs.getString("achternaam") + " (" + myRs.getString("geboortedatum") + ")");
                } else {
                    System.out.println("#" + myRs.getString("reiziger_id") + ": " + myRs.getString("voorletters") + " " + myRs.getString("tussenvoegsel") + " " + myRs.getString("achternaam") + " (" + myRs.getString("geboortedatum") + ")");
                }
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
