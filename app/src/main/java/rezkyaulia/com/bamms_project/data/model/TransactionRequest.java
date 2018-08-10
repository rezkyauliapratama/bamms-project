package rezkyaulia.com.bamms_project.data.model;

public class TransactionRequest {
    String start_date;
    String end_date;
    long id;

    public TransactionRequest(String start_date, String end_date, long id) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.id = id;
    }

    public TransactionRequest(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
