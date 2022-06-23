# Datenpersistenz 
## Verwendete Technologien  
* IntelliJ Ultiamte IDE
* Github (GIT)
* XAMPP
* Maven

## Projektsetup
1. Maven Projekt anlegen
2. Dependency MySQL connector hinzufügen
3. pom.xml reloaden
4. Klassen anlegen
5. XAMPP Module Starten [Apache, MySQL]

## Datenbankverbindung anlegen
1. Im phpMyAdmin eine Datenbank anlegen
2. Tabellen anlegen
3. In der Klasse definieren von der URL

### Beispiel zur Datenbankverbindung

```
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbcdemo {

    public static void main(String[] args) {
        System.out.println("JDBC Demo!");
        // INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, 'Liuming Xia', 'liuming@gmx.at'), (NULL, 'Julian Meilinger', 'meilinger07@gmail.com');
        selectallDemo();
    }

    public static void selectallDemo(){
        System.out.println("Select DEMO mit JDBDC");
        String sqlSelectAllpersons = "SELECT * FROM `student`";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        try(Connection conn = DriverManager.getConnection(connectionURL, "root", "")){
            System.out.println("Verbindung zur DB hergestellt!");
        } catch(SQLException e){
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }
}
```
## Daten aus der Datenbank auslesen

Daten auslesen mittels Prepared Statement:
* try catch auf die SQL Verbindung
* Rückgabewerte sind in einem ResulSet gespeichert
* ResultSet verfügt über mehrere methoden Bsp. getInt()
* In diesem Bsp. könnte das Prepared Statement in einem try catch Block gepackt werden, einfachshalber ohne try catch Block

```
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
```

## Daten in die Datenbank speichern

Daten einfügen mittels Prepared Statement:
* ? Platzhalter um SQL injections zu vermeiden
* Prepare Statements sind vorcompailierte SQL statements die auf z.B einen String warten

```
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
```

### Feinheiten
Das Feine ist man muss normalerweise die connection wieder schließen, ist die Verbindung im try catch Block wird die Connection automatisch geschlossen.
```
try(Connection conn = DriverManager.getConnection(connectionURL, "root", "")){}
```

## Daten aktualisieren in der Datenbank

Daten aktualisieren mittels Prepared Statement:
```
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
```

## Daten löschen in der Datenbank
Daten löschen mittels Prepared Statement:
```
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
```

## Alle Daten auslesen aus der Datenbank nach einem Pattern
Daten auslesen nach einem Pattern mittels Prepared Statement:
```
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
```