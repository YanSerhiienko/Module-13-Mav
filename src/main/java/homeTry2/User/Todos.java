package homeTry2.User;

public class Todos {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"userId\": " + userId +",\n" +
                "  \"id\": " + id + ",\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"completed\": " + completed +"\n" +
                "}";
    }
}
