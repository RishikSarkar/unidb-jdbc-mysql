import java.sql.*;
import java.util.Scanner;

@SuppressWarnings("resource")
public class UniDB {

    private static String degreeName(String dname) {
        if (dname.equalsIgnoreCase("Eng")) {
            return "BA in " + dname;
        }

        return "BS in " + dname;
    }

    private static void formatStudent(int sid) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/hw3", "root", "Neongourami123!");

            Statement stmt = con.createStatement();

            ResultSet sn = stmt.executeQuery("SELECT last_name, first_name FROM Students WHERE id=" + sid);
            while (sn.next()) {
                System.out.println(sn.getString(1) + ", " + sn.getString(2));
            }

            System.out.println("ID: " + sid);

            ResultSet majCount = stmt.executeQuery("SELECT COUNT(dname) FROM Majors WHERE sid=" + sid);
            majCount.next();
            int majorCount = majCount.getInt(1);

            ResultSet maj = stmt.executeQuery("SELECT dname FROM Majors WHERE sid=" + sid);
            if (majorCount == 1) {
                maj.next();
                System.out.println("Major: " + degreeName(maj.getString(1)));
            } else {
                String major = "";
                major += "Majors: ";
                while (maj.next()) {
                    major += degreeName(maj.getString(1));
                    if (!maj.isLast()) {
                        major += ", ";
                    }
                }
                System.out.println(major);
            }

            ResultSet minCount = stmt.executeQuery("SELECT COUNT(dname) FROM Minors WHERE sid=" + sid);
            minCount.next();
            int minorCount = minCount.getInt(1);

            ResultSet min = stmt.executeQuery("SELECT dname FROM Minors WHERE sid=" + sid);
            if (minorCount == 1) {
                min.next();
                System.out.println("Minor: " + min.getString(1));
            } else {
                String minor = "";
                minor += "Minors: ";
                while (min.next()) {
                    minor += min.getString(1);
                    if (!min.isLast()) {
                        minor += ", ";
                    }
                }
                System.out.println(minor);
            }

            System.out.printf("GPA: %.3f \n", getGPA(sid));
            System.out.println("Credits: " + getCredits(sid));
            System.out.println();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static int gradePoints(String grade) {
        grade = grade.toLowerCase();

        switch (grade) {
            case "a":
                return 4;
            case "b":
                return 3;
            case "c":
                return 2;
            case "d":
                return 1;
            case "f":
                return 0;
            default:
                return 0;
        }
    }

    private static double getGPA(int sid) {
        double gpa = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/hw3", "root", "Neongourami123!");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT H.grade, C.credits FROM HasTaken H LEFT JOIN Classes C ON H.name=C.name WHERE H.sid="
                            + sid);

            double gradePointSum = 0;
            double totalCredits = 0;
            while (rs.next()) {
                gradePointSum += gradePoints(rs.getString(1)) * rs.getInt(2);
                totalCredits += rs.getInt(2);
            }
            gpa = gradePointSum / totalCredits * 1.0;

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return gpa;
    }

    private static int getCredits(int sid) {
        int totalCredits = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/hw3", "root", "Neongourami123!");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT C.credits FROM HasTaken H LEFT JOIN Classes C ON H.name=C.name WHERE H.sid="
                            + sid + " AND H.grade != 'F'");

            while (rs.next()) {
                totalCredits += rs.getInt(1);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return totalCredits;
    }

    // Query 1
    private static void searchName() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/hw3", "root", "Neongourami123!");

            Statement stmt = con.createStatement();

            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter the name.");
            String name = scan.nextLine();

            ResultSet count = stmt
                    .executeQuery("SELECT COUNT(S.id) FROM Students S WHERE LOWER(first_name) LIKE '%" + name
                            + "%' OR LOWER(last_name) LIKE '%" + name + "%'");

            System.out.println();
            count.next();
            System.out.println(count.getInt(1) + " students found\n");

            ResultSet rs = stmt.executeQuery("SELECT S.id FROM Students S WHERE LOWER(first_name) LIKE '%" + name
                    + "%' OR LOWER(last_name) LIKE '%" + name + "%'");

            while (rs.next()) {
                formatStudent(rs.getInt(1));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static int runQuery() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Which query would you like to run (1-8)?");
        int queryNum = scan.nextInt();
        scan.nextLine();

        switch (queryNum) {
            case 1:
                searchName();
                break;
            case 2:
                System.out.println("2");
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                System.out.println("Goodbye.");
                break;
            default:
                System.out.println("Please enter a valid query (1-8)!");
                return runQuery();
        }

        return queryNum;
    }

    public static void main(String args[]) {
        System.out.println("Welcome to the university database. Queries available:");
        System.out.println("1. Search students by name.");
        System.out.println("2. Search students by year.");
        System.out.println("3. Search for students with a GPA >= threshold.");
        System.out.println("4. Search for students with a GPA <= threshold.");
        System.out.println("5. Get department statistics.");
        System.out.println("6. Get class statistics.");
        System.out.println("7. Execute an arbitrary SQL query.");
        System.out.println("8. Exit the application.");

        int qnum = 0;

        while (qnum != 8) {
            qnum = runQuery();
        }
    }
}
