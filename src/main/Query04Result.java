package main;

public class Query04Result {
    int id;
    String signature;
    String book;

    public Query04Result(int id, String signature, String book) {
        this.id = id;
        this.signature = signature;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Query04Result{" +
                "id=" + id +
                ", signature='" + signature + '\'' +
                ", book='" + book + '\'' +
                '}';
    }
}
