
public class IssueRecord {
    private int issueId;
    private int bookId;
    private int memberId;
    private String bookName;
    private String memberName;
    private boolean active;

    public IssueRecord(int issueId, int bookId, int memberId,
                       String bookName, String memberName) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.bookName = bookName;
        this.memberName = memberName;
        this.active = true;
    }

    public int getIssueId() {
        return issueId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getMemberName() {
        return memberName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void displayIssueDetails() {
        System.out.println("Issue ID: " + issueId);
        System.out.println("Member ID: " + memberId);
        System.out.println("Member Name: " + memberName);
        System.out.println("Book ID: " + bookId);
        System.out.println("Book Name: " + bookName);
        System.out.println("Issue Status: "
                + (active ? "Active" : "Returned"));
    }
}
