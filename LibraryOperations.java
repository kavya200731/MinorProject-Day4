
public abstract class LibraryOperations {
    public abstract void addBook(Book book);

    public abstract void displayBooks();

    public abstract void issueBook(int bookId, int memberId);

    public abstract void returnBook(int bookId, int memberId);
}
