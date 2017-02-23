package main;

import com.sun.xml.internal.bind.v2.model.core.ID;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Application implements IApplication {
    private ArrayList<Record> records;

    public Application() {
        records = new ArrayList<>();
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

    public void init() {
            /*
      update(buildSQLStatement(1,"book01","B001A","E01",1000,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
      update(buildSQLStatement(2,"book02","B002A","E01",1000,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
      update(buildSQLStatement(3,"book03","B003A","E00",1005,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
      update(buildSQLStatement(4,"book03","B003B","E00",1001,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
      update(buildSQLStatement(5,"book03","B003C","E00",1002,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
      update(buildSQLStatement(5,"book04","B004A","E00",1002,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));
      update(buildSQLStatement(6,"book05","B005A","E01",1002,convertDateStringToUnixSeconds("2016-08-08 12:00"),convertDateStringToUnixSeconds("2016-08-10 10:00")));

     */
        records = new ArrayList<>();
        records.add(new Record(1, "book01", "B001a", "E01", 1000, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(2, "book01", "B001a", "E01", 1000, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(3, "book01", "B001a", "E00", 1005, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(4, "book01", "B001a", "E00", 1001, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(5, "book01", "B001a", "E00", 1002, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(6, "book01", "B001a", "E00", 1002, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
    }

    public void printRecords() {
        records.forEach(System.out::println);
    }

    public void executeQuery01() {
        System.out.println("--- query01\n" +
                "SELECT AVG(borrowEndTimeStamp - borrowStartTimestamp) FROM data WHERE (book = 'book01')");
        System.out.println();
        double res = records.stream()
                .filter(rec -> Objects.equals(rec.getBook(), "book01"))
                .mapToDouble(rec -> (rec.getBorrowEndTimeStamp() - rec.getBorrowStartTimestamp()))
                .average()
                .getAsDouble();
        System.out.println(res);
        System.out.println();
    }

    public void executeQuery02() {
        System.out.println("--- query02: \n" +
                "SELECT MAX(borrowEndTimeStamp - borrowStartTimestamp) FROM data WHERE customerID IN (1000,1002,1005)");
        System.out.println();
        ArrayList<Integer> IDs = new ArrayList<>();
        IDs.add(1000);
        IDs.add(1002);
        IDs.add(1005);
        long res = records.stream()
                .filter(rec -> (IDs.contains(rec.getCustomerID())))
                .mapToLong(rec -> (rec.getBorrowEndTimeStamp() - rec.getBorrowStartTimestamp()))
                .max()
                .getAsLong();
        System.out.println(res);
        System.out.println();
    }

    public void executeQuery03() {
        System.out.println("--- query03\n" +
                "SELECT id,book,signature,location FROM data ORDER BY location");
        System.out.println();
        List temp = records.stream()
                .sorted(Comparator.comparing(Record::getLocation))
                .map(rec -> new Query03Result(rec.getId(), rec.getBook(), rec.getSignature(), rec.getLocation(), rec.getCustomerID()))
                .collect(Collectors.toList());
        for(int i = 0; i < temp.size(); i++){
            System.out.println(temp.get(i).toString());
        }
        System.out.println();
    }

    public void executeQuery04() {
        System.out.println("--- query04\n" +
                "SELECT id,book,signature FROM data ORDER BY book,signature DESC");
        System.out.println();
        List temp = records.stream()
                .sorted(RecordComparator::compare)
                .map(rec -> new Query04Result(rec.getId(), rec.getSignature(), rec.getBook()))
                .collect(Collectors.toList());
        for(int i=0; i < temp.size();i++){
            System.out.println(temp.get(i).toString());
        }
        System.out.println();
    }

    public void executeQuery05() {
  /*      System.out.println("--- query05\n" +
                "SELECT id,book FROM data WHERE book IN ('book01','book03') AND (customerID = 1005)");
        records.stream()
                .filter(rec -> (rec.getCustomerID() == 1005))
                .filter(rec -> rec.getCustomerID() == 1005))
        System.out.println(); */
    }

    public void executeQuery06() {
        System.out.println("--- query06");
        System.out.println();
    }

    public void executeQuery07() {
        System.out.println("--- query07");
        System.out.println();
    }

    public void executeQuery08() {
        System.out.println("--- query08");
        System.out.println();
    }

    public void executeQuery09() {
        System.out.println("--- query09");
        System.out.println();
    }

    public void executeQuery10() {
        System.out.println("--- query10");
        System.out.println();
    }

    public static void main(String... args) {
        Application application = new Application();
        application.init();
        application.printRecords();
        application.executeQuery01();
        application.executeQuery02();
        application.executeQuery03();
        application.executeQuery04();
        application.executeQuery05();
        application.executeQuery06();
        application.executeQuery07();
        application.executeQuery08();
        application.executeQuery09();
        application.executeQuery10();
    }
}