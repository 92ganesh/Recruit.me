/**
 Note:-
 * you will need the postgres jdbc driver which can be downloaded from https://jdbc.postgresql.org/download.html
 * if using java 9 you can the package called postgresql-42.2.4.jar uploaded with this program
 * This code is tested with postgres 9.4 and java 9

 references:-
 * http://www.postgresqltutorial.com/postgresql-jdbc/
 * JDBC notes from PL 2
*/

/* How to setup Eclipse
open eclipse-> File-> project -> java project -> give project name and select 'Use project folder as root for sources and class file' 
then click next -> finish
then right-click on project->new->class->give class name as postgresConnection 
copy paste this code in created .java file
then right-click the project->build path->add external archive->select .jar file
 */




import java.sql.*;
public class postgresConnection{

//**********************************************************************************
//  set details of this block acc your postgres settings
// 'test' is the DB name	
	
    private final String url = "jdbc:postgresql://localhost/test";
    private final String user = "postgres";
    private final String password = "postgres";  
//**********************************************************************************
    
    public static Connection conn = null;
    public Connection connect() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
    
    public void createTable(){
        PreparedStatement pst=null;
        try {
            pst=conn.prepareStatement("create table student(stuId integer PRIMARY KEY,stuName varchar)");
            int r=pst.executeUpdate();
            System.out.println("Table created");
            pst.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertData(String tableName,int stuId,String stuName){
        PreparedStatement pst=null;
        try {
            pst=conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?)");
            pst.setInt(1,stuId);
            pst.setString(2,stuName);
            int r=pst.executeUpdate();
            System.out.println("Data inserted");
            pst.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectData(String tableName){
        PreparedStatement pst=null;
        try {
            pst=conn.prepareStatement("SELECT * FROM "+tableName);
            ResultSet r=(ResultSet)pst.executeQuery();
            while(r.next()){
                int id = r.getInt("stuId");
                String name = r.getString("stuName");
                System.out.println(id+" "+name);
            }
            pst.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        postgresConnection app = new postgresConnection();
        // start the connection
        app.connect();

        // create table
        app.createTable();
        
        // insert data
        app.insertData("student",101,"Karan");
        app.insertData("student",99,"Ganesh");
        app.insertData("student",93,"Yash");
        app.insertData("student",92,"Shubham");
        

        // select and print data
        app.selectData("student");

        // close the connection
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}