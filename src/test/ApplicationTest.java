package test;

import main.*;
import org.junit.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class ApplicationTest {


    private ArrayList<Record> records;

    public ApplicationTest() {
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
        records = new ArrayList<>();
        records.add(new Record(1, "book01", "B001A", "E01", 1000, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(2, "book02", "B002A", "E01", 1000, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(3, "book03", "B003A", "E00", 1005, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(4, "book03", "B003B", "E00", 1001, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(5, "book03", "B003C", "E00", 1002, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(6, "book04", "B004A", "E00", 1002, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));
        records.add(new Record(7, "book05", "B004A", "E00", 1002, convertDateStringToUnixSeconds("2016-08-08 12:00"), convertDateStringToUnixSeconds("2016-08-10 10:00")));

    }

    public void printRecords() {
        records.forEach(System.out::println);
    }

    @Test
    public void executeQuery01() {
        String Sqlresult = "165600000";
        System.out.println("--- query01\n" +
                "SELECT AVG(borrowEndTimeStamp - borrowStartTimestamp) FROM data WHERE (book = 'book01')");
        System.out.println();
        double res = records.stream()
                .filter(rec -> Objects.equals(rec.getBook(), "book01"))
                .mapToDouble(rec -> (rec.getBorrowEndTimeStamp() - rec.getBorrowStartTimestamp()))
                .average()
                .getAsDouble();
        System.out.println(res);
        assertEquals(Long.parseLong(Sqlresult),(long)res);
        System.out.println();
    }

    @Test
    public void executeQuery02() {
        String Sqlresult = "165600000";
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
        assertEquals(Long.parseLong(Sqlresult),(long)res);
        System.out.println();
    }
    @Test
    public void executeQuery03() {
        String Sqlresult = "Query03Result{id=3, book='book03', signature='B003A', location='E00'}\n" +
                "Query03Result{id=4, book='book03', signature='B003B', location='E00'}\n" +
                "Query03Result{id=5, book='book03', signature='B003C', location='E00'}\n" +
                "Query03Result{id=6, book='book04', signature='B004A', location='E00'}\n" +
                "Query03Result{id=7, book='book05', signature='B004A', location='E00'}\n" +
                 "Query03Result{id=1, book='book01', signature='B001A', location='E01'}\n" +
                "Query03Result{id=2, book='book02', signature='B002A', location='E01'}";
        System.out.println("--- query03\n" +
                "SELECT id,book,signature,location FROM data ORDER BY location");
        System.out.println();
        List temp = records.stream()
                .sorted(Comparator.comparing(Record::getLocation))
                .map(rec -> new Query03Result(rec.getId(), rec.getBook(), rec.getSignature(), rec.getLocation()))
                .collect(Collectors.toList());
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < temp.size(); i++) {
            resultString.append(temp.get(i).toString());
            resultString.append("\n");
            System.out.println(temp.get(i).toString());
        }
        assertEquals(Sqlresult,resultString.toString().substring(0,resultString.length()-1));
        System.out.println();
    }

    public void executeQuery04() {
        String Sqlresult ="Query04Result{id=1, signature='B001A', book='book01'}\n" +
                "Query04Result{id=2, signature='B002A', book='book02'}\n" +
                "Query04Result{id=5, signature='B003C', book='book03'}\n" +
                "Query04Result{id=4, signature='B003B', book='book03'}\n" +
                "Query04Result{id=3, signature='B003A', book='book03'}\n" +
                "Query04Result{id=6, signature='B004A', book='book04'}\n" +
                "Query04Result{id=7, signature='B004A', book='book05'}";
        System.out.println("--- query04\n" +
                "SELECT id,book,signature FROM data ORDER BY book,signature DESC");
        System.out.println();
        List temp = records.stream()
                .sorted(RecordComparator::compare)
                .map(rec -> new Query04Result(rec.getId(), rec.getSignature(), rec.getBook()))
                .collect(Collectors.toList());
        StringBuilder resString = new StringBuilder();
        for (int i = 0; i < temp.size(); i++) {
            resString.append(temp.get(i).toString());
            resString.append("\n");
            System.out.println(temp.get(i).toString());
        }
        assertEquals(Sqlresult,resString.substring(0,resString.length()-1));
        System.out.println();
    }

    public void executeQuery05() {
        String Sqlresult = "Query05Result{id=3, book='book03'}";
        System.out.println("--- query05\n" +
                "SELECT id,book FROM data WHERE book IN ('book01','book03') AND (customerID = 1005)");
        List temp = records.stream()
                .filter(rec -> (rec.getCustomerID() == 1005))
                .filter(rec -> (rec.getBook().equals("book01") || rec.getBook().equals("book03")))
                .map(rec -> new Query05Result(rec.getId(), rec.getBook()))
                .collect(Collectors.toList());
        String resString = "";
        for (Object aTemp : temp) {
            System.out.println(aTemp.toString());
            resString = aTemp.toString();
        }
        assertEquals(Sqlresult,resString);
        System.out.println();
    }

    public void executeQuery06() {
        String Sqlresult = "Query05Result{id=1, book='book01'}\n" +
                "Query05Result{id=2, book='book02'}\n" +
                "Query05Result{id=4, book='book03'}\n" +
                "Query05Result{id=5, book='book03'}\n" +
                "Query05Result{id=6, book='book04'}\n" +
                "Query05Result{id=7, book='book05'}";
        System.out.println("--- query06\n" +
                "SELECT id,book FROM data WHERE (customerID <= 1002) ORDER BY book");
        List temp =  records.stream()
                .filter(rec -> rec.getCustomerID() <= 1002)
                .sorted(Comparator.comparing(Record::getBook))
                .map(rec -> new Query05Result(rec.getId(), rec.getBook()))
                .collect(Collectors.toList());
        StringBuilder resString = new StringBuilder();
        for (int i = 0; i < temp.size(); i++) {
            resString.append(temp.get(i).toString());
            resString.append("\n");
            System.out.println(temp.get(i).toString());
        }
        assertEquals(Sqlresult,resString.substring(0,resString.length()-1));
        System.out.println();
    }

    public void executeQuery07() {
        String Sqlresult = "Query05Result{id=7, book='book05'}\n" +
                "Query05Result{id=6, book='book04'}";
        System.out.println("--- query07\n" +
                "SELECT id,book FROM data WHERE (customerID <= 1002) ORDER BY book DESC LIMIT 2");
        List temp =  records.stream()
                .filter(rec -> rec.getCustomerID() <= 1002)
                .sorted(Comparator.comparing(Record::getBook).reversed())
                .limit(2)
                .map(rec -> new Query05Result(rec.getId(), rec.getBook()))
                .collect(Collectors.toList());
        StringBuilder resString = new StringBuilder();
        for (int i = 0; i < temp.size(); i++) {
            resString.append(temp.get(i).toString());
            resString.append("\n");
            System.out.println(temp.get(i).toString());
        }
        assertEquals(Sqlresult,resString.substring(0,resString.length()-1));
        System.out.println();
    }

    public void executeQuery08() {
        String Sqlresult = "4";
        System.out.println("--- query08\n" +
                "SELECT COUNT(*) FROM data WHERE book IN ('book02','book03')");
        long res = records.stream()
                .filter(rec -> (Objects.equals(rec.getBook(), "book02") || Objects.equals(rec.getBook(), "book03")))
                .count();
        System.out.println(res);
        assertEquals(Long.parseLong(Sqlresult),res);
        System.out.println();
    }

    public void executeQuery09() {
        String Sqlresult = "{book02=1, book01=1, book05=1, book04=1, book03=3}";
        System.out.println("--- query09\n" +
                "SELECT book,COUNT(*) FROM data GROUP BY book");
        Map<String, Long> res = records.stream()
                .collect(Collectors.groupingBy(Record::getBook, Collectors.counting()));
        System.out.println(res);
        assertEquals(Sqlresult,res.toString());
        System.out.println();
    }

    public void executeQuery10() {
        String Sqlresult = "{E00=4}";
        System.out.println("--- query10\n" +
                "SELECT location,COUNT(*) FROM data WHERE book IN ('book03','book05') GROUP BY location");
        Map<String, Long> res = records.stream()
                .filter(rec -> (Objects.equals(rec.getBook(), "book03") || Objects.equals(rec.getBook(), "book05")))
                .collect(Collectors.groupingBy(Record::getLocation, Collectors.counting()));
        System.out.println(res);
        assertEquals(Sqlresult,res.toString());
        System.out.println();
    }
    @Test
    public void main() {
        ApplicationTest application = new ApplicationTest();
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