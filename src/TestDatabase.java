import challange.java_swing.model.Database;

public class TestDatabase {
    public static void main(String[] args) {
        Database db = new Database();
        db.connect();
        db.disconnect();
    }
}
