import java.sql.*;

public class Jdbcdemo {

    public static void main(String[] args) {
        System.out.println("JDBC Demo!");
        // INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, 'Liuming Xia', 'liuming@gmx.at'), (NULL, 'Julian Meilinger', 'meilinger07@gmail.com');

        findAllStudentDemo("ming");
    }

    public static void findAllStudentDemo(String pattern){
        System.out.println("Select all by DEMO mit JDBDC");
        String sqlSelectAllpersons = "SELECT * FROM `student` WHERE `student` . `name` LIKE ?";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        try(Connection conn = DriverManager.getConnection(connectionURL, "root", "")){
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectAllpersons);
            preparedStatement.setString(1, "%" + pattern + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [ID] " + id + " " + "[Name] " + name + " " + "[Email] " + email + " ");
            }
        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void deleteStudentDemo(){
        System.out.println("DELTE DEMO mit JDBDC");
        String sqlSelectAllpersons = "DELETE FROM `student` WHERE `student` . `id` = ?";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        try(Connection conn = DriverManager.getConnection(connectionURL, "root", "")){
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectAllpersons);
            try{
                preparedStatement.setInt(1, 3);
            } catch(SQLException ex){

            }
        } catch(SQLException e){
            System.out.println("Fehler beim SQL-UPDATE Statement: " + e.getMessage());
        }
    }

    public static void updateStudentDemo(){
        System.out.println("INSERT DEMO mit JDBDC");
        String sqlSelectAllpersons = "UPDATE `student` SET `name` = ? WHERE `student`.`id` = 3";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        try(Connection conn = DriverManager.getConnection(connectionURL, "root", "")){
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectAllpersons);
            try{
                preparedStatement.setString(1, "G");
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("Anzahl der aktualisierten Datensätze " + affectedRows);
            } catch(SQLException ex){

            }
        } catch(SQLException e){
            System.out.println("Fehler beim SQL-UPDATE Statement: " + e.getMessage());
        }
    }

    public static void insertStudentDemo(){
        System.out.println("INSERT DEMO mit JDBDC");
        String sqlSelectAllpersons = "INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, ?, ?)";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        try(Connection conn = DriverManager.getConnection(connectionURL, "root", "")){
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectAllpersons);
            try{
                preparedStatement.setString(1, "Niklas Heim");
                preparedStatement.setString(2,"heim@gmx.at");
                int rowAffected = preparedStatement.executeUpdate();
                System.out.println(rowAffected + " Datensätze eingefügt");
            } catch(SQLException ex){

            }
        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void selectallDemo(){
        System.out.println("Select DEMO mit JDBDC");
        String sqlSelectAllpersons = "SELECT * FROM `student`";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        try(Connection conn = DriverManager.getConnection(connectionURL, "root", "")){
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectAllpersons);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [ID] " + id + " " + "[Name] " + name + " " + "[Email] " + email + " ");
            }
        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }
}
