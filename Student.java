public class Student {
    private int id;
    private String name;
    private String rollNo;
    private int departmentId;
    private int year;

    public Student() {}

    public Student(String name, String rollNo, int departmentId, int year) {
        this.name = name;
        this.rollNo = rollNo;
        this.departmentId = departmentId;
        this.year = year;
    }

    public Student(int id, String name, String rollNo, int departmentId, int year) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
        this.departmentId = departmentId;
        this.year = year;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
