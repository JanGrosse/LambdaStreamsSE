import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Application implements IApplication {
    private final String userDirectory = System.getProperty("user.dir");
    private final String fileSeparator = System.getProperty("file.separator");
    private final String dataDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    private final String databaseFile = dataDirectory + "exercise.db";

    private Connection connection;
    private String driverName = "jdbc:hsqldb:";
    private String username = "sa";
    private String password = "";

    public void setupConnection() {
        System.out.println("--- setupConnection");

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            String databaseURL = driverName + databaseFile;
            connection = DriverManager.getConnection(databaseURL,username,password);
            System.out.println("connection : " + connection);
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getStatus() {
        try {
            System.out.println("--- status");
            System.out.println("userDirectory : " + userDirectory);
            System.out.println("dataDirectory : " + dataDirectory);
            System.out.println("databaseFile  : " + databaseFile);
            System.out.println("driverName    : " + driverName);
            System.out.println("username      : " + username);
            System.out.println("password      : " + password);
            System.out.println("connection    : " + connection.hashCode());
            System.out.println("schema        : " + connection.getSchema());
            System.out.println("status        : " + connection.isClosed());
            System.out.println();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public synchronized void update(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sqlStatement);
            if (result == -1)
                System.out.println("error executing " + sqlStatement);
            statement.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public void dropTable() {
        System.out.println("--- dropTable");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE data");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());

        System.out.println();
    }

    public void createTable() {
        System.out.println("--- createTable");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE data ").append(" ( ");
        sqlStringBuilder.append("id INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("book VARCHAR(10) NOT NULL").append(",");
        sqlStringBuilder.append("signature VARCHAR(5) NOT NULL").append(",");
        sqlStringBuilder.append("location VARCHAR(5) NOT NULL").append(",");
        sqlStringBuilder.append("customerID INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("borrowStartTimestamp BIGINT NOT NULL").append(",");
        sqlStringBuilder.append("borrowEndTimeStamp BIGINT NOT NULL").append(",");
        sqlStringBuilder.append("PRIMARY KEY (id)");
        sqlStringBuilder.append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());

        System.out.println();
    }

    public void dump(ResultSet resultSet) {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int maximumNumberColumns = resultSetMetaData.getColumnCount();
            int i;
            Object object;

            for (;resultSet.next();) {
                for (i = 0;i < maximumNumberColumns;++i) {
                    object = resultSet.getObject(i + 1);
                    System.out.print(object.toString() + " ");
                }
                System.out.println(" ");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public synchronized void queryDump(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            dump(resultSet);
            statement.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public long convertDateStringToUnixSeconds(String dateString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return dateFormat.parse(dateString).getTime();
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
        return -1;
    }


    private String buildSQLStatement(int id,String book,String signature,String location,int customerID,
                                    long borrowStartTimestamp,long borrowEndTimeStamp) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO data (id,book,signature,location,customerID,borrowStartTimestamp,borrowEndTimeStamp) VALUES (");
        stringBuilder.append(id).append(",");
        stringBuilder.append("'").append(book).append("'").append(",");
        stringBuilder.append("'").append(signature).append("'").append(",");
        stringBuilder.append("'").append(location).append("'").append(",");
        stringBuilder.append(customerID).append(",");
        stringBuilder.append(borrowStartTimestamp).append(",");
        stringBuilder.append(borrowEndTimeStamp);
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    private void insertData() {
        update(buildSQLStatement(1,"book01","B001A","E01",1000,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
        update(buildSQLStatement(2,"book02","B002A","E01",1000,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
        update(buildSQLStatement(3,"book03","B003A","E00",1005,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
        update(buildSQLStatement(4,"book03","B003B","E00",1001,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
        update(buildSQLStatement(5,"book03","B003C","E00",1002,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
        update(buildSQLStatement(5,"book04","B004A","E00",1002,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
        update(buildSQLStatement(6,"book05","B005A","E01",1002,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
        System.out.println();
    }

    public void init() {
        insertData();
    }

    // aggregation - average
    public void executeSQL01() {
        System.out.println("--- executeSQL01");
        String sqlStatement = "SELECT AVG(bEndTimeStamp - borrowStartTimestamp) FROM data " +
                              "WHERE (book = 'book01')";
        queryDump(sqlStatement);
        System.out.println();
    }

    // aggregation - max
    public void executeSQL02() {
        System.out.println("--- executeSQL02");
        String sqlStatement = "SELECT MAX(borrowEndTimeStamp - borrowStartTimestamp) FROM data " +
                              "WHERE customerID IN (1000,1002,1005)";
        queryDump(sqlStatement);
        System.out.println();
    }

    // sort
    public void executeSQL03() {
        System.out.println("--- executeSQL03");
        String sqlStatement = "SELECT id,book,signature,location FROM data ORDER BY location";
        queryDump(sqlStatement);
        System.out.println();
    }

    // sort
    public void executeSQL04() {
        System.out.println("--- executeSQL04");
        String sqlStatement = "SELECT id,book,signature FROM data " +
                              "ORDER BY book,signature DESC";
        queryDump(sqlStatement);
        System.out.println();
    }

    // filter
    public void executeSQL05() {
        System.out.println("--- executeSQL05");
        String sqlStatement = "SELECT id,book FROM data " +
                              "WHERE book IN ('book01','book03') AND (customerID = 1005)";
        queryDump(sqlStatement);
        System.out.println();
    }

    // filter and sort
    public void executeSQL06() {
        System.out.println("--- executeSQL06");
        String sqlStatement = "SELECT id,book FROM data WHERE (customerID <= 1002) " +
                              "ORDER BY book";
        queryDump(sqlStatement);
        System.out.println();
    }

    // filter, sort and limit
    public void executeSQL07() {
        System.out.println("--- executeSQL07");
        String sqlStatement = "SELECT id,book FROM data WHERE (customerID <= 1002) " +
                              "ORDER BY book DESC LIMIT 2";
        queryDump(sqlStatement);
        System.out.println();
    }

    // aggregation - count
    public void executeSQL08() {
        System.out.println("--- executeSQL08");
        String sqlStatement = "SELECT COUNT(*) FROM data WHERE book IN ('book02','book03')";
        queryDump(sqlStatement);
        System.out.println();
    }

    // aggregation - group
    public void executeSQL09() {
        System.out.println("--- executeSQL09");
        String sqlStatement = "SELECT book,COUNT(*) FROM data GROUP BY book";
        queryDump(sqlStatement);
        System.out.println();
    }

    // aggregation - group and filter
    public void executeSQL10() {
        System.out.println("--- executeSQL10");
        String sqlStatement = "SELECT location,COUNT(*) FROM data " +
                              "WHERE book IN ('book03','book05') " +
                              "GROUP BY location";
        queryDump(sqlStatement);
        System.out.println();
    }

    public void shutdown() {
        System.out.println("--- shutdown");

        try {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
            connection.close();
            System.out.println("isClosed : " + connection.isClosed());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public static void main(String... args) {
        Application application = new Application();
        application.setupConnection();
        application.getStatus();
        application.dropTable();
        application.createTable();
        application.init();
        application.executeSQL01();
        application.executeSQL02();
        application.executeSQL03();
        application.executeSQL04();
        application.executeSQL05();
        application.executeSQL06();
        application.executeSQL07();
        application.executeSQL08();
        application.executeSQL09();
        application.executeSQL10();
        application.shutdown();
    }
}