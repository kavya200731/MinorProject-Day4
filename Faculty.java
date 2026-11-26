
public class Faculty extends LibraryMember {
    private String department;
    private String designation;

    public Faculty(int memberId, String memberName,
                   String contactNumber, String department,
                   String designation) {
        super(memberId, memberName, contactNumber);
        this.department = department;
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    @Override
    public void displayMemberDetails() {
        System.out.println("Member Type: Faculty");
        super.displayMemberDetails();
        System.out.println("Department: " + department);
        System.out.println("Designation: " + designation);
    }
}
