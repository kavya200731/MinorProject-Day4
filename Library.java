
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;

public class Library extends LibraryOperations {

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<LibraryMember> members = new ArrayList<>();

    private HashMap<Integer, Book> bookMap = new HashMap<>();

    private LinkedHashMap<Integer, IssueRecord> issueRecords =
            new LinkedHashMap<>();

    private HashSet<Integer> issuedBookIds = new HashSet<>();

    private int nextMemberId = 1001;
    private int nextIssueId = 1;

    // Add Book
    @Override
    public void addBook(Book book) {
        if (bookMap.containsKey(book.getBookId())) {
            System.out.println("Book ID already exists!");
            return;
        }

        books.add(book);
        bookMap.put(book.getBookId(), book);

        System.out.println("Book Added Successfully!");
    }

    // Display All Books
    @Override
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("\n===== BOOK LIST =====");

        for (Book book : books) {
            book.displayBookDetails();
            System.out.println("---------------------");
        }

        System.out.println("Total Books Registered: " + books.size());

        System.out.println("\n===== BOOKS WITH ZERO COPIES =====");

        boolean found = false;

        for (Book book : books) {
            if (book.getAvailableCopies() == 0) {
                book.displayBookDetails();
                System.out.println("---------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books are currently unavailable.");
        }
    }

    // Search Book
    public void searchBook(int bookId) {
        Book book = bookMap.get(bookId);

        if (book == null) {
            System.out.println("Book not found!");
        } else {
            System.out.println("\n===== BOOK DETAILS =====");
            book.displayBookDetails();
        }
    }

    // Register Student
    public void registerStudent(String name, String contact,
                               String department, int year) {
        Student student = new Student(nextMemberId, name, contact,
                                      department, year);

        members.add(student);

        System.out.println("Student Registered Successfully!");
        System.out.println("Generated Member ID: " + nextMemberId);

        nextMemberId++;
    }

    // Register Faculty
    public void registerFaculty(String name, String contact,
                                String department, String designation) {
        Faculty faculty = new Faculty(nextMemberId, name, contact,
                                      department, designation);

        members.add(faculty);

        System.out.println("Faculty Registered Successfully!");
        System.out.println("Generated Member ID: " + nextMemberId);

        nextMemberId++;
    }

    // Display All Members
    public void displayMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        System.out.println("\n===== MEMBER LIST =====");

        for (LibraryMember member : members) {
            member.displayMemberDetails();
            System.out.println("---------------------");
        }
    }

    // Search Member
    public LibraryMember searchMember(int memberId) {
        for (LibraryMember member : members) {
            if (member.getMemberId() == memberId) {
                return member;
            }
        }

        return null;
    }

    // Search and Display Member
    public void displayMemberById(int memberId) {
        LibraryMember member = searchMember(memberId);

        if (member == null) {
            System.out.println("Member not found!");
        } else {
            member.displayMemberDetails();
        }
    }

    // Issue Book
    @Override
    public void issueBook(int bookId, int memberId) {

        LibraryMember member = searchMember(memberId);

        if (member == null) {
            System.out.println("Member not found!");
            return;
        }

        Book book = bookMap.get(bookId);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        if (book.getAvailableCopies() <= 0) {
            System.out.println("No copies available!");
            return;
        }

        // Prevent the same member from issuing the same book
        for (IssueRecord record : issueRecords.values()) {
            if (record.isActive()
                    && record.getMemberId() == memberId
                    && record.getBookId() == bookId) {
                System.out.println(
                    "This member already has this book issued!");
                return;
            }
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        IssueRecord record = new IssueRecord(
                nextIssueId,
                bookId,
                memberId,
                book.getBookName(),
                member.getMemberName()
        );

        issueRecords.put(nextIssueId, record);
        issuedBookIds.add(bookId);

        nextIssueId++;

        System.out.println("\nBook Issued Successfully!");
        System.out.println("Member: " + member.getMemberName());
        System.out.println("Book: " + book.getBookName());
        System.out.println("Available Copies: "
                + book.getAvailableCopies());
    }

    // Return Book
    @Override
    public void returnBook(int bookId, int memberId) {

        LibraryMember member = searchMember(memberId);

        if (member == null) {
            System.out.println("Member not found!");
            return;
        }

        Book book = bookMap.get(bookId);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        IssueRecord activeRecord = null;

        // Find an active issue record for this member and book
        for (IssueRecord record : issueRecords.values()) {
            if (record.isActive()
                    && record.getMemberId() == memberId
                    && record.getBookId() == bookId) {
                activeRecord = record;
                break;
            }
        }

        if (activeRecord == null) {
            System.out.println(
                "No active issue record found for this member and book!");
            return;
        }

        activeRecord.setActive(false);

        book.setAvailableCopies(book.getAvailableCopies() + 1);

        // Remove book ID from HashSet only if no active
        // issue records remain for this book
        boolean stillIssued = false;

        for (IssueRecord record : issueRecords.values()) {
            if (record.isActive() && record.getBookId() == bookId) {
                stillIssued = true;
                break;
            }
        }

        if (!stillIssued) {
            issuedBookIds.remove(bookId);
        }

        System.out.println("\nBook Returned Successfully!");
        System.out.println("Member: " + member.getMemberName());
        System.out.println("Book: " + book.getBookName());
        System.out.println("Available Copies: "
                + book.getAvailableCopies());
    }

    // Display Issued Books
    public void displayIssuedBooks(boolean activeOnly) {

        if (issueRecords.isEmpty()) {
            System.out.println("No issue records available.");
            return;
        }

        System.out.println("\n===== ISSUE RECORDS =====");

        boolean found = false;

        for (IssueRecord record : issueRecords.values()) {

            if (activeOnly && !record.isActive()) {
                continue;
            }

            record.displayIssueDetails();
            System.out.println("---------------------");
            found = true;
        }

        if (!found) {
            System.out.println("No active issue records.");
        }
    }

    // Library Statistics
    public void generateReport() {

        int activeIssues = 0;
        int totalCopies = 0;
        int unavailableBooks = 0;

        for (Book book : books) {
            totalCopies += book.getAvailableCopies();

            if (book.getAvailableCopies() == 0) {
                unavailableBooks++;
            }
        }

        for (IssueRecord record : issueRecords.values()) {
            if (record.isActive()) {
                activeIssues++;
            }
        }

        System.out.println("\n===== LIBRARY REPORT =====");
        System.out.println("Total Books Registered: " + books.size());
        System.out.println("Total Members Registered: " + members.size());
        System.out.println("Total Active Issue Records: " + activeIssues);
        System.out.println("Total Available Book Copies: " + totalCopies);
        System.out.println("Books With Zero Available Copies: "
                + unavailableBooks);
        System.out.println("Book IDs Currently Issued: "
                + issuedBookIds);
    }
}
