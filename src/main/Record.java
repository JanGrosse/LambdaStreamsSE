package main;

public class Record {
    int id;
    String book;
    String signature;
    String location;
    int customerID;
    long borrowStartTimestamp;
    long borrowEndTimeStamp;

    public Record(int id, String book, String signature, String location, int customerID, long borrowStartTimestamp, long borrowEndTimeStamp) {
        this.id = id;
        this.book = book;
        this.signature = signature;
        this.location = location;
        this.customerID = customerID;
        this.borrowStartTimestamp = borrowStartTimestamp;
        this.borrowEndTimeStamp = borrowEndTimeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public long getBorrowStartTimestamp() {
        return borrowStartTimestamp;
    }

    public void setBorrowStartTimestamp(long borrowStartTimestamp) {
        this.borrowStartTimestamp = borrowStartTimestamp;
    }

    public long getBorrowEndTimeStamp() {
        return borrowEndTimeStamp;
    }

    public void setBorrowEndTimeStamp(long borrowEndTimeStamp) {
        this.borrowEndTimeStamp = borrowEndTimeStamp;
    }
}