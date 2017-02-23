package main;

public class Query09Result {
    int count;
    String book;

    public Query09Result(int count, String book) {
        this.count = count;
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Query09Result{" +
                "count=" + count +
                ", book='" + book + '\'' +
                '}';
    }
}
