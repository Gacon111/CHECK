import java.io.*;
import java.util.*;

abstract class User {
    private String username;
    private String password;
    private String role;
    
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }    
    public User() {
        this.username = null;
        this.password = null;
        this.role = null;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public abstract void showMenu(Scanner sc);

}

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "admin");
    }
    public Admin() {
        super();
    }

    @Override
    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("------- Admin Menu -------");
            System.out.println("  pLease choose an option:");
            System.out.println("1. Add User");
            System.out.println("2. Remove User");
            System.out.println("3. List Users");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addUser(sc);
                    break;
                case 2:
                    removeUser(sc);
                    break;
                case 3:
                    listUsers();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
        while (choice != 4);
    }

    public boolean isUsernameExists(String username) {
        try (Scanner fileScanner = new Scanner(new File("users.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        return false;
    }

    public void addUser(Scanner sc) {
        sc.nextLine();
    
        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = sc.nextLine().trim();
    
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
            } 
            else if (isUsernameExists(username)) {
                System.out.println("Username already exists, please choose another one.");
            } 
            else {
                break;
            }
        }
        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = sc.nextLine().trim();
    
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
            } else {
                break;
            }
        }
        String role;
        while (true) {
            System.out.print("Enter role (Admin / Librarian / Reader): ");
            role = sc.nextLine().trim();

            if (role.isEmpty()) {
                System.out.println("Role cannot be empty.");
            } 
            else if (!role.equals("Admin") && !role.equals("Librarian") && !role.equals("Reader")) {
                System.out.println("Invalid role, please enter Admin, Librarian, or Reader.");
            } 
            else {
                break;
            }
        }

        try {
            FileWriter filewriter = new FileWriter("users.txt", true);
            filewriter.write(username + "," + password + "," + role + "\n");
            filewriter.close();
            System.out.println("New user ''" + username + "'' has been added.");
        } 
        catch (IOException e) {
            System.out.println("Cannot add user: " + e.getMessage());
        }
}
    public void removeUser(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter username to remove: ");
        String username = sc.nextLine().trim();
    
        File userFile = new File("users.txt");
        ArrayList<String> updatedFile = new ArrayList<>();
        boolean found = false;
    
        try {
            Scanner scanner = new Scanner(userFile);
            while (scanner.hasNextLine()) {
                String line =scanner.nextLine();
                String[] data = line.split(",");
                if (data.length >= 1 && data[0].equals(username)) {
                    found = true; 
                    continue;
                }
                updatedFile.add(line);
            }
            scanner.close();
    
            if (found) {
                FileWriter filewriter = new FileWriter(userFile, false);
                for (String line : updatedFile) {
                    filewriter.write(line + "\n");
                }
                filewriter.close();
                System.out.println("User ''" + username + "'' removed successfully.");
            } else {
                System.out.println("Cannot find user ''" + username + "'' in the file.");
            }
    
        } catch (IOException e) {
            System.out.println("Error occurred while processing file: " + e.getMessage());
        }
    }
    public void listUsers() {
        try {
            File userFile = new File("users.txt");
            Scanner sc = new Scanner(userFile);
            System.out.println("------- User List -------");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");
                System.out.println("Username: " + data[0] + ", Role: " + data[2]);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading file: " + e.getMessage());
        }
    }
}

class Librarian extends User {
    public Librarian(String username, String password) {
        super(username, password, "Librarian");
    }
    public Librarian() {
        super();
    }
    @Override
    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("------- Librarian Menu -------");
            System.out.println("   pLease choose an option:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. List Available Books");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addBook(sc);
                    break;
                case 2:
                    removeBook(sc);
                    break;
                case 3:
                    listBooks();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
        while (choice != 4);
    }

    public void addBook(Scanner sc) {
        sc.nextLine();
    
        String type;
        while (true) {
            System.out.print("Enter type of book (1. Printed Book/ 2. EBook): ");
            type = sc.nextLine().trim();
            if (type.equals("1") || type.equals("2")) break;
            System.out.println("Invalid book type. Only 1 (Printed Book) or 2 (EBook) allowed.");
        }
    
        String Title;
        String Author;
        String Genre;
        String Isbn;
    
        do {
            System.out.print("Enter Title: ");
            Title = sc.nextLine().trim();
        } 
        while (Title.isEmpty());
    
        do {
            System.out.print("Enter Author Name: ");
            Author = sc.nextLine().trim();
        } 
        while (Author.isEmpty());
    
        do {
            System.out.print("Enter Genre: ");
            Genre = sc.nextLine().trim();
        } while (Genre.isEmpty());
    
        do {
            System.out.print("Enter ISBN: ");
            Isbn = sc.nextLine().trim();
        } 
        while (Isbn.isEmpty());
    
        try {
            FileWriter filewriter = new FileWriter("books.txt", true);
    
            if (type.equals("1")) {
                String availabilityStatus = "true";
                String dueDate = "";
                int numOfPage;
    
                while (true) {
                    System.out.print("Enter Number of Pages: ");
                    String pageInput = sc.nextLine().trim();
                    try {
                        numOfPage = Integer.parseInt(pageInput);
                        if (numOfPage <= 0) {
                            System.out.println("Page number must be greater than 0.");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }
    
                filewriter.write("PrintedBook," + Title + "," + Author + "," + Genre + "," + Isbn + ",true," + numOfPage + "," + dueDate + "\n");
    
            } 
            else {
                String fileFormat;
                do {
                    System.out.print("File Format (e.g. PDF, Word): ");
                    fileFormat = sc.nextLine().trim();
                } 
                while (fileFormat.isEmpty());
    
                filewriter.write("EBook," + Title + "," + Author + "," + Genre + "," + Isbn + ",true," + fileFormat + "\n");
            }
            filewriter.close();
            System.out.println("New book ''" + Title + "'' has been added! (Type: " + (type.equals("1") ? "Printed Book" : "EBook") + ")");
    
        } 
        catch (IOException e) {
            System.out.println("Cannot add book: " + e.getMessage());
        }
    }

    public void removeBook(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter title of book to remove: ");
        String Title = sc.nextLine().trim();
    
        File bookFile = new File("books.txt");
        ArrayList<String> updatedFile = new ArrayList<>();
        boolean found = false;
    
        try {
            Scanner scanner = new Scanner(bookFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                
                if (data.length >= 2 && data[1].equalsIgnoreCase(Title)) {
                    found = true;
                    continue;
                }
                updatedFile.add(line);
            }
            scanner.close();
    
            if (found) {
                FileWriter filewriter = new FileWriter(bookFile, false);
                for (String line : updatedFile) {
                    filewriter.write(line + "\n");
                }
                filewriter.close();
                System.out.println("Book ''" + Title + "'' has been removed.");
            } else {
                System.out.println("Cannot find book ''" + Title + "'' in the file.");
            }
    
        } catch (IOException e) {
            System.out.println("Error occurred while processing file: " + e.getMessage());
        }
    }

    public void listBooks() {
        try {
            File bookFile = new File("books.txt");
            Scanner sc = new Scanner(bookFile);
            System.out.println("------- Book List -------");
    
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");
    
                if (data.length < 7) {
                    System.out.println("Invalid books: " + line);
                    continue;
                }
    
                String type = data[0];
    
                if (type.equals("PrintedBook")) {
                    String available = data[5];
                    if (available.equals("true")) {
                        System.out.println("Title: " + data[1] + ", Author: " + data[2] + ", Genre: " + data[3]
                            + ", ISBN: " + data[4] + ", Pages: " + data[6]);
                    }
                } else if (type.equals("EBook")) {
                    System.out.println("Title: " + data[1] + ", Author: " + data[2] + ", Genre: " + data[3]
                        + ", ISBN: " + data[4] + ", Format: " + data[6]);
                } else {
                    System.out.println("Unknown book type: " + line);
                }
            }
    
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading file.");
        }
    }
} 

class Reader extends User {
    public Reader(String username, String password) {
        super(username, password, "Reader");
    }
    public Reader() {
        super();
    }

    @Override
    public void showMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("------- Reader Menu -------");
            System.out.println("  pLease choose an option:");
            System.out.println("(You can borrow only Printed Books)");
            System.out.println("1. View Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    borrowBook(sc);
                    break;
                case 3:
                    returnBook(sc);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 4);
    }

    public void viewBooks() {
        try {
            File bookFile = new File("books.txt");
            Scanner sc = new Scanner(bookFile);
            System.out.println("------- Available Books -------");
    
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");
    
                if (data[0].equals("PrintedBook") && data[5].equals("true")) {
                    System.out.println("Title: " + data[1] + ", Author: " + data[2] + ", Genre: " + data[3] +
                                       ", ISBN: " + data[4] + ", Pages: " + data[6] +
                                       ", Availability: Available, Type: " + data[0]);
                } else if (data[0].equals("EBook") && data[5].equals("true")) {
                    System.out.println("Title: " + data[1] + ", Author: " + data[2] + ", Genre: " + data[3] +
                                       ", ISBN: " + data[4] + ", Format: " + data[6] + ", Type: " + data[0]);
                }
            }
        sc.close();

        } 
        catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading file.");
        }
    }
    
    public void borrowBook(Scanner sc) {
        System.out.print("Enter title of book to borrow: ");
        sc.nextLine();
        String Title = sc.nextLine().trim();
        File bookFile = new File("books.txt");
        ArrayList<String> updatedFile = new ArrayList<>();
        boolean foundTitle = false;
    
        try {
            Scanner scanner = new Scanner(bookFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (data[1].equalsIgnoreCase(Title)) {
                    foundTitle = true;
    
                    if (data[0].equals("EBook")) {
                        System.out.println("You can't borrow EBook!!! Please choose another book.");
                    } 
                    else if (data[5].equals("true")) {
                        data[5] = "false"; // Mark as borrowed
                        System.out.println("You have borrowed the book ''" + Title + "'' successfully.");
    
                        try (FileWriter filewriter = new FileWriter("transactions.txt", true)) {
                            String transaction = getUsername() + " [ BORROW ] Title: " + data[1] +
                                                 " | ISBN: " + data[4] +
                                                 " | Date: " + java.time.LocalDate.now();
                            filewriter.write(transaction + "\n");
                        } 
                        catch (IOException e) {
                            System.out.println("Error writing to transactions file.");
                        }
                    } 
                    else {
                        System.out.println("Book ''" + Title + "'' is not available for borrowing.");
                    }
                }
    
                updatedFile.add(String.join(",", data));
            }
            scanner.close();
    
            if (foundTitle) {
                FileWriter wr = new FileWriter(bookFile, false);
                for (String line : updatedFile) {
                    wr.write(line + "\n");
                }
                wr.close();
            } else {
                System.out.println("Cannot find book ''" + Title + "'' in the file.");
            }
    
        } 
        catch (IOException e) {
            System.out.println("Error occurred while processing file.");
        }
    }
    
    public void returnBook(Scanner sc) {
        System.out.print("Enter title of book to return: ");
        sc.nextLine(); // Consume leftover newline
        String title = sc.nextLine().trim();
    
        File bookFile = new File("books.txt");
        File transactionFile = new File("transactions.txt");
        ArrayList<String> updatedBookFile = new ArrayList<>();
        boolean foundTitle = false;
        boolean canReturn = false;
        String isbnToReturn = "";
    
        try {
            List<String> borrowBook = new ArrayList<>();
            List<String> returnBook = new ArrayList<>();
    
            if (transactionFile.exists()) {
                Scanner fileReader = new Scanner(transactionFile);
                while (fileReader.hasNextLine()) {
                    String line = fileReader.nextLine();
                    if (line.contains("BORROW") && line.contains(title)) {
                        borrowBook.add(line);
                    } else if (line.contains("RETURN") && line.contains(title)) {
                        returnBook.add(line);
                    }
                }
                fileReader.close();
            }
    
            if (borrowBook.size() > returnBook.size()) {
                canReturn = true;
            }
    
            Scanner scanner = new Scanner(bookFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
    
                if (data[1].equalsIgnoreCase(title)) {
                    foundTitle = true;
    
                    if (data[0].equals("EBook")) {
                        System.out.println("You can't return EBook!!! Please choose another book.");
                    } 
                    else if (!canReturn) {
                        System.out.println("You have not borrowed this book or already returned it.");
                    } 
                    else if (data[5].equalsIgnoreCase("false")) {
                        data[5] = "true"; // Mark as returned
                        isbnToReturn = data[4];
                        System.out.println("You have returned the book ''" + title + "'' successfully.");
                        try (FileWriter tfw = new FileWriter(transactionFile, true)) {
                            String transaction = getUsername() + " [ RETURN ] Title: " + data[1] +
                                                 " | ISBN: " + isbnToReturn +
                                                 " | Date: " + java.time.LocalDate.now();
                            tfw.write(transaction + "\n");
                        } catch (IOException e) {
                            System.out.println("Error writing to transactions file.");
                        }
                    } else {
                        System.out.println("Book ''" + title + "'' is not currently borrowed.");
                    }
                }
    
                updatedBookFile.add(String.join(",", data));
            }
            scanner.close();
    
            if (foundTitle) {
                FileWriter filewriter = new FileWriter(bookFile, false);
                for (String line : updatedBookFile) {
                    filewriter.write(line + "\n");
                }
                filewriter.close();
            } 
            else {
                System.out.println("Cannot find book ''" + title + "'' in the file.");
            }
    
        } catch (IOException e) {
            System.out.println("Error occurred while processing file.");
        }
    }
}
abstract class Book {
    protected String Title;
    protected String Author;
    protected String Genre;
    protected String Isbn;
    protected boolean isAvailable;
    protected String dueDate;

    public Book(String title, String author, String genre, String isbn, boolean isAvailable, String dueDate) {
        this.Title = title;
        this.Author = author;
        this.Genre = genre;
        this.Isbn = isbn;
        this.isAvailable = isAvailable;
        this.dueDate = dueDate;
    }
    public Book() {
        this.Title = null;
        this.Author = null;
        this.Genre = null;
        this.Isbn = null;
        this.isAvailable = false;
        this.dueDate = null;
    }

    public abstract String toFileString();
    public abstract String getType();
    
    public String getTitle() { 
        return Title; 
    }
    public String getIsbn() { 
        return Isbn; 
    }

    public String getAuthor() { 
        return Author; 
    }
    public String getGenre() { 
        return Genre; 
    }
    public boolean getIsAvailable() { 
        return isAvailable; 
    }
    public String getDueDate() { 
        return dueDate; 
    }
    public void setDueDate(String dueDate) { 
        this.dueDate = dueDate; 
    } 
}

class PrintedBook extends Book {

    private int numOfPages;
    public PrintedBook(String title, String author, String genre, String isbn, boolean isAvailable, int numOfPages, String dueDate) {
        super(title, author, genre, isbn, isAvailable, dueDate);
        this.numOfPages = numOfPages;
    }

    @Override
    public String toFileString() {
        return "Printed," + Title + "," + Author + "," + Genre + "," + Isbn + "," + isAvailable + "," + numOfPages + "," + dueDate;
    }

    @Override
    public String getType() {
        return "PrintedBook";
    }
    public int getNumPages() {
        return numOfPages;
    }
}

class Ebook extends Book {
    private String fileFormat;

    public Ebook(String title, String author, String genre, String isbn, String fileFormat) {
        super(title, author, genre, isbn, true, "N/A");
        this.fileFormat = fileFormat;
    }

    @Override
    public String toFileString() {
        return "Ebook," + Title + "," + Author + "," + Genre + "," + Isbn + "," + isAvailable + "," + dueDate + "," + fileFormat;
    }

    @Override
    public String getType() {
        return "Ebook";
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- WELCOME TO LIBRARY MANAGEMENT SYSTEM ---");
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your username: ");
            String username = sc.nextLine().trim();
            System.out.print("Enter your password: ");
            String password = sc.nextLine();

            try (Scanner fileScanner = new Scanner(new File("users.txt"))) {
                boolean found = false;
                while (fileScanner.hasNextLine()) {
                    String[] parts = fileScanner.nextLine().split(",");
                    if (parts.length >= 3 && parts[0].equals(username) && parts[1].equals(password)) {
                        found = true;
                        User user;
                        switch (parts[2]) {
                            case "Admin" -> user = new Admin(username, password);
                            case "Librarian" -> user = new Librarian(username, password);
                            case "Reader" -> user = new Reader(username, password);
                            default -> {
                                System.out.println("This user has an undefined role.");
                                return;
                            }
                        }
                        user.showMenu(sc);
                        return; 
                    }
                }

                if (!found) {
                    System.out.println("Invalid credentials, Please try again...");
                    System.out.print("Do you want to try again? (yes/no): ");
                    String choice = sc.nextLine().trim().toLowerCase();
                    if (!choice.equals("y")) {
                        System.out.println("See you next time!");
                        break;
                    }
                }

            } catch (IOException e) {
                System.out.println("Login error: " + e.getMessage());
                break;
            }
        }

        sc.close();
    }
}


