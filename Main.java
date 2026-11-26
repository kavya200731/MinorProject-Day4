
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        int choice;

        do {
            System.out.println("\n========================================");
            System.out.println("       LIBRARY MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Register Student");
            System.out.println("5. Register Faculty");
            System.out.println("6. Display All Members");
            System.out.println("7. Search Member");
            System.out.println("8. Issue Book");
            System.out.println("9. Return Book");
            System.out.println("10. Display Issued Books");
            System.out.println("11. Generate Library Report");
            System.out.println("12. Exit");
            System.out.println("========================================");

            choice = readInt(sc, "Enter Your Choice: ");

            switch (choice) {

                case 1:
                    System.out.print("Enter Book ID: ");
                    int bookId = readInt(sc, "");

                    System.out.print("Enter Book Name: ");
                    String bookName = sc.nextLine();

                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();

                    System.out.print("Enter Category: ");
                    String category = sc.nextLine();

                    int copies;
                    do {
                        copies = readInt(sc, "Enter Available Copies: ");
                        if (copies < 0) {
                            System.out.println(
                                "Copies cannot be negative!");
                        }
                    } while (copies < 0);

                    Book book = new Book(bookId, bookName, author,
                                         category, copies);

                    library.addBook(book);
                    break;

                case 2:
                    library.displayBooks();
                    break;

                case 3:
                    int searchBookId = readInt(sc,
                            "Enter Book ID to Search: ");
                    library.searchBook(searchBookId);
                    break;

                case 4:
                    System.out.print("Enter Student Name: ");
                    String studentName = sc.nextLine();

                    System.out.print("Enter Contact Number: ");
                    String studentContact = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String studentDepartment = sc.nextLine();

                    int year;
                    do {
                        year = readInt(sc, "Enter Year: ");
                        if (year < 1 || year > 5) {
                            System.out.println(
                                "Enter a valid year (1-5)!");
                        }
                    } while (year < 1 || year > 5);

                    library.registerStudent(studentName,
                            studentContact, studentDepartment, year);
                    break;

                case 5:
                    System.out.print("Enter Faculty Name: ");
                    String facultyName = sc.nextLine();

                    System.out.print("Enter Contact Number: ");
                    String facultyContact = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String facultyDepartment = sc.nextLine();

                    System.out.print("Enter Designation: ");
                    String designation = sc.nextLine();

                    library.registerFaculty(facultyName,
                            facultyContact, facultyDepartment,
                            designation);
                    break;

                case 6:
                    library.displayMembers();
                    break;

                case 7:
                    int searchMemberId = readInt(sc,
                            "Enter Member ID to Search: ");
                    library.displayMemberById(searchMemberId);
                    break;

                case 8:
                    int issueMemberId = readInt(sc,
                            "Enter Member ID: ");
                    int issueBookId = readInt(sc,
                            "Enter Book ID: ");

                    library.issueBook(issueBookId, issueMemberId);
                    break;

                case 9:
                    int returnMemberId = readInt(sc,
                            "Enter Member ID: ");
                    int returnBookId = readInt(sc,
                            "Enter Book ID: ");

                    library.returnBook(returnBookId, returnMemberId);
                    break;

                case 10:
                    System.out.print(
                        "Display only active records? (yes/no): ");
                    String answer = sc.nextLine();

                    boolean activeOnly =
                            answer.equalsIgnoreCase("yes");

                    library.displayIssuedBooks(activeOnly);
                    break;

                case 11:
                    library.generateReport();
                    break;

                case 12:
                    System.out.println(
                        "Thank you for using Library Management System!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 12);

        sc.close();
    }

    // Helper method for reading integer input safely
    public static int readInt(Scanner sc, String message) {

        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }
}
