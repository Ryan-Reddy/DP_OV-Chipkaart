import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            // 1. Connect met de database
            Connection mijnConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "algra50");

            // 2. Creeer een statement
            Statement myStatement = mijnConn.createStatement();

            // 3. Execute een SQL query
            ResultSet myRs = myStatement.executeQuery("select * from reiziger");

            // 4. Proces de set aan resultaten
            int counter = 0;
            while (myRs.next()) {
                counter++;
                System.out.println("#" + counter + ": " + myRs.getString("achternaam") + ", { " + myRs.getString("geboortedatum") + " }");
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}

