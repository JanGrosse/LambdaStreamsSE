package main;

public class Query03Result {
    int id;
    String book;
    String signature;
    String location;
    int customerID;

    public Query03Result(int id, String book, String signature, String location, int customerID) {
        this.id = id;
        this.book = book;
        this.signature = signature;
        this.location = location;
        this.customerID = customerID;
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

    @Override
    public String toString() {
        return "Query03Result{" +
                "id=" + id +
                ", book='" + book + '\'' +
                ", signature='" + signature + '\'' +
                ", location='" + location + '\'' +
                ", customerID=" + customerID +
                '}';
    }
}
