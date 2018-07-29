import java.sql.*;
public class postgresConnection{

    private final String url = "jdbc:postgresql://localhost/test";
    private final String user = "postgres";
    private final String password = "postgres";

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

        // insert data
        app.insertData("student",999,"Something");

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