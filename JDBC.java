import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import oracle.jdbc.OraclePreparedStatement;

public class JDBC {
    String username;
    String password;
    Connection con = null;
    String query = null;
    ResultSet resultSet = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    OraclePreparedStatement oraclePreparedStatement = null;
    final String relationName_suffix = "Fall22_S004_8_";

    public JDBC() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("local.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");

        if(!this.username.isBlank() && !this.password.isBlank()){
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("Oracle JDBC Driver Missing");
                e.printStackTrace();
                return;
            }
            System.out.println("Oracle JDBC Driver Registered");

            try {
                this.con = DriverManager.getConnection("jdbc:oracle:thin:@acaddbprod.uta.edu:1523/pcse1p.data.uta.edu", this.username, this.password);
            } catch (SQLException e) {
                System.out.println("Connection Failed!");
                e.printStackTrace();
                return;
            }
            System.out.println("Got Connection");
        }else{
            System.out.println("Check user credentials");
            return;
        }
    }

    public ResultSet display(String relationName) throws SQLException {
        query = "select * from ".concat(relationName_suffix.concat(relationName));
        statement = this.con.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public int insert(String relationName, ArrayList<String> arrString, ArrayList<Date> arrDate) throws SQLException {
        int k = 0;
        query = "insert into " + relationName_suffix.concat(relationName) + " values(";
        int totalInputSize = arrString.size()+arrDate.size();
        for(int i=0;i<totalInputSize;i++){
            query = query.concat("?");
            if(i<totalInputSize-1){
                query = query.concat(",");
            }
        }
        query = query.concat(")");
        preparedStatement = con.prepareStatement(query);
        for(int i=0;i<arrString.size();i++){
            preparedStatement.setString(i+1, arrString.get(i));
            k=i;
        }
        for(int i=0;i<arrDate.size();i++,k++){
            preparedStatement.setDate(k+2, arrDate.get(i));
        }
        return preparedStatement.executeUpdate();
    }

    public int delete(String relationName, String primaryColumnName, String primaryValue) throws SQLException {
        query = "delete from " + relationName_suffix.concat(relationName) + " where " + primaryColumnName + "=?";
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, primaryValue);
        return preparedStatement.executeUpdate();
    }

    public ResultSet goals(String parameter2, int i) throws SQLException {
        if (i==1) {
            query = "select x.sectorType, x.avg_area from (select p.sector sectorType, avg(p.area) as avg_area from Fall22_S004_8_Project p join Fall22_S004_8_Address a using(Zip) where a.city = ? ".concat("group by p.sector) x");
        }
        else if (i==2) {
            String notes="";
            notes.replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");
            query = "select avg(labourcount) as AvgLC , avg(Rate) as AvgRate from Fall22_S004_8_LabourWorksOnProject lwp join Fall22_S004_8_Labor l using(Labour_City) where Labour_City like ? ESCAPE '!' ";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "%" + parameter2);
            return preparedStatement.executeQuery();
        }
        else if (i==3) {
            query = "select p1.zip, a.city, SUM(p1.selling_price) as selling_price, rank() OVER (ORDER BY SUM(p1.selling_price) desc) as Rank from Fall22_S004_8_Project p1, Fall22_S004_8_Address a  where a.zip = p1.zip and Sector=?".concat("GROUP BY a.city, p1.zip fetch first 3 rows only");
        }
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, parameter2);
        return preparedStatement.executeQuery();
    }

    public static void main(String[] argv) throws IOException, SQLException {
        JDBC jdbc = new JDBC();
        if (jdbc.con != null) {
            System.out.printf("Welcome %s%n", jdbc.username);
        } else {
            System.out.println("Connection null");
        }
    }
}
