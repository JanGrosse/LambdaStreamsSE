import java.sql.ResultSet;

public interface IApplication {
    void setupConnection();
    void getStatus();
    void update(String sqlStatement);
    void dropTable();
    void createTable();
    void dump(ResultSet resultSet);
    void queryDump(String sqlStatement);
    long convertDateStringToUnixSeconds(String dateString);
    void init();
    void executeSQL01();
    void executeSQL02();
    void executeSQL03();
    void executeSQL04();
    void executeSQL05();
    void executeSQL06();
    void executeSQL07();
    void executeSQL08();
    void executeSQL09();
    void executeSQL10();
    void shutdown();
}