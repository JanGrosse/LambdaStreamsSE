package main;

public class Query03Result {
    int id;
    String book;
    String signature;
    String location;

    public Query03Result(int id, String book, String signature, String location) {
        this.id = id;
        this.book = book;
        this.signature = signature;
        this.location = location;
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

    @Override
    public String toString() {
        return "Query03Result{" +
                "id=" + id +
                ", book='" + book + '\'' +
                ", signature='" + signature + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
