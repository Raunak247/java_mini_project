import java.io.*;
import java.sql.*;
import java.util.*;

class Expense {
    String date, category, description;
    double amount;

    Expense(String d, String c, double a, String desc) {
        date = d;
        category = c;
        amount = a;
        description = desc;
    }
}

public class ExpenseManager {

    public static ArrayList<Expense> list = new ArrayList<>();
    static final String FILE = "data.txt";

    // ✅ DB CONFIG (ADDED HERE)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public static void addExpense(Expense e) {
        list.add(e);
    }

    // ===== SEARCH (MULTI FIELD) =====
    public static ArrayList<Expense> search(String key) {
        ArrayList<Expense> res = new ArrayList<>();
        for (Expense e : list) {
            if (e.description.toLowerCase().contains(key.toLowerCase())
                    || e.category.toLowerCase().contains(key.toLowerCase())
                    || e.date.contains(key))
                res.add(e);
        }
        return res;
    }

    // ===== SORT =====
    public static void sortByAmount() {
        list.sort(Comparator.comparingDouble(e -> e.amount));
    }

    public static void sortByDate() {
        list.sort(Comparator.comparing(e -> e.date));
    }

    // ===== FILE =====
    public static void saveFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (Expense e : list)
                bw.write(e.date+"|"+e.category+"|"+e.amount+"|"+e.description+"\n");
        } catch (Exception e) {
            System.out.println("File Save Error");
        }
    }

    public static void loadFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            list.clear();
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split("\\|");
                list.add(new Expense(p[0], p[1],
                        Double.parseDouble(p[2]), p[3]));
            }
        } catch (Exception e) {
            System.out.println("File Load Error");
        }
    }

    // ===== IMPORT ANY FILE =====
    public static void importFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split(",");
                list.add(new Expense(p[0], p[1],
                        Double.parseDouble(p[2]), p[3]));
            }
        } catch (Exception e) {
            System.out.println("Import Error");
        }
    }

    // ===== DB CONNECTION METHOD (BEST PRACTICE) =====
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // ===== SAVE TO DB =====
    public static void saveToDB() {
        try (Connection c = getConnection()) {

            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO expenses(date, category, amount, description) VALUES(?,?,?,?)"
            );

            for (Expense e : list) {
                ps.setString(1, e.date);
                ps.setString(2, e.category);
                ps.setDouble(3, e.amount);
                ps.setString(4, e.description);
                ps.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("DB Save Error");
        }
    }

    // ===== LOAD FROM DB =====
    public static void loadFromDB() {
        try (Connection c = getConnection()) {

            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM expenses");

            list.clear();

            while (rs.next()) {
                list.add(new Expense(
                        rs.getString("date"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("description")
                ));
            }

        } catch (Exception e) {
            System.out.println("DB Load Error");
        }
    }

    // ===== TOTAL =====
    public static double total() {
        return list.stream().mapToDouble(e -> e.amount).sum();
    }
}