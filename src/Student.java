import java.util.Objects;
import java.util.Scanner;

public class Student {
    int id;
    String name;
    String gender;
    String clazz;
    double score;

    static String format = "|| %-8s || %-14s || %-11s || %-10s || %-10s ||%n";
    static String line = "=".repeat(74);

    Student(){
        this(0,"N/A", "N/A", "N/A", 0.0);
    }

    Student(int id, String name, String gender, String clazz, double score){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.clazz = clazz;
        this.score = score;
    }

    static void printHeader(){
        System.out.println(line);
        System.out.printf(format, "ID", "FULL NAME", "GENDER", "CLASS", "SCORE");
        System.out.println(line);
    }

    @Override
    public String toString() {
        return String.format(format,
                String.format("ISTAD%03d", id),
                name,
                gender,
                clazz,
                String.format("%.2f", score));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    void main(){
        int num = 100;
        Student[] student = new Student[num];
        int count = 0;
        int nextId = 1;

        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("""
                    
                    1. INIT STUDENT
                    2. DISPLAY STUDENT
                    3. UPDATE STUDENT
                    4. DELETE STUDENT
                    5. EXIT
                    """);

            int choice = readInt(input, "[+] CHOOSE OPTION [1-5]: ", 1, 5);

            switch (choice) {

                case 1 -> {
                    if (count >= num) {
                        System.out.println("[!] STUDENT LIST IS FULL.");
                        break;
                    }
                    int total = readInt(input, "[+] ENTER THE NUMBER OF STUDENTS: ", 1, num - count);

                    for (int i = 0; i < total; i++) {
                        System.out.println("INFORMATION STUDENT [" + (i + 1) + "]");

                        String name = readText(input, "FULL NAME");
                        String gender = readGender(input);
                        String clazz = readText(input, "CLASS");
                        double score = readScore(input);

                        student[count] = new Student(nextId, name, gender, clazz, score);
                        count++;
                        nextId++;
                    }
                    System.out.println("[+] " + total + " STUDENT(S) ADDED.");
                }

                case 2 -> {
                    if (count == 0) {
                        System.out.println("[!] NO STUDENT YET. CHOOSE 1 TO INIT.");
                        break;
                    }
                    System.out.println();
                    printHeader();
                    for (int i = 0; i < count; i++) {
                        System.out.print(student[i]);
                    }
                    System.out.println(line);
                    System.out.println("[+] TOTAL: " + count + " STUDENT(S)");
                }

                case 3 -> {
                    if (count == 0) {
                        System.out.println("[!] NO STUDENT TO UPDATE.");
                        break;
                    }
                    int id = readInt(input, "[+] ENTER ID TO UPDATE: ", 1, nextId - 1);

                    int index = -1;
                    for (int i = 0; i < count; i++) {
                        if (student[i].id == id) {
                            index = i;
                        }
                    }
                    if (index == -1) {
                        System.out.println("[!] STUDENT ID " + id + " NOT FOUND.");
                        break;
                    }

                    System.out.println("[+] CURRENT DATA:");
                    printHeader();
                    System.out.print(student[index]);
                    System.out.println(line);

                    student[index].name = readText(input, "FULL NAME");
                    student[index].gender = readGender(input);
                    student[index].clazz = readText(input, "CLASS");
                    student[index].score = readScore(input);

                    System.out.println("[+] UPDATED SUCCESSFULLY.");
                }

                case 4 -> {
                    if (count == 0) {
                        System.out.println("[!] NO STUDENT TO DELETE.");
                        break;
                    }
                    int id = readInt(input, "[+] ENTER ID TO DELETE: ", 1, nextId - 1);

                    int index = -1;
                    for (int i = 0; i < count; i++) {
                        if (student[i].id == id) {
                            index = i;
                        }
                    }
                    if (index == -1) {
                        System.out.println("[!] STUDENT ID " + id + " NOT FOUND.");
                        break;
                    }

                    System.out.print("[+] DELETE " + student[index].name + " ? (y/n): ");
                    String confirm = input.nextLine().trim().toLowerCase();
                    if (!confirm.equals("y") && !confirm.equals("yes")) {
                        System.out.println("[+] CANCELLED.");
                        break;
                    }

                    for (int i = index; i < count - 1; i++) {
                        student[i] = student[i + 1];
                    }
                    count--;
                    student[count] = null;

                    System.out.println("[+] DELETED SUCCESSFULLY.");
                }

                case 5 -> running = false;
            }
        }
        System.out.println("[+] GOODBYE!");
    }

    static String label(String name) {
        return "[+] ENTER " + String.format("%-10s", name) + ": ";
    }

    static String readText(Scanner input, String name) {
        while (true) {
            System.out.print(label(name));
            String value = input.nextLine().trim();
            if (value.length() >= 2) {
                return value.toUpperCase();
            }
            System.out.println("    [!] " + name + " MUST BE AT LEAST 2 CHARACTERS.");
        }
    }

    static String readGender(Scanner input) {
        while (true) {
            System.out.print(label("GENDER"));
            String value = input.nextLine().trim().toUpperCase();
            if (value.equals("MALE") || value.equals("M")) return "MALE";
            if (value.equals("FEMALE") || value.equals("F")) return "FEMALE";
            System.out.println("    [!] GENDER MUST BE MALE OR FEMALE.");
        }
    }

    static double readScore(Scanner input) {
        while (true) {
            System.out.print(label("SCORE"));
            String raw = input.nextLine().trim();
            try {
                double value = Double.parseDouble(raw);
                if (value < 0 || value > 100) {
                    System.out.println("    [!] SCORE MUST BE BETWEEN 0 AND 100.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("    [!] '" + raw + "' IS NOT A NUMBER.");
            }
        }
    }

    static int readInt(Scanner input, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = input.nextLine().trim();
            try {
                int value = Integer.parseInt(raw);
                if (value < min || value > max) {
                    System.out.println("    [!] VALUE MUST BE BETWEEN " + min + " AND " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("    [!] '" + raw + "' IS NOT A WHOLE NUMBER.");
            }
        }
    }
}