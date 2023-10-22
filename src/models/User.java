package models;

public class User {
    private Integer id;
    private String userName;
    private String department;
    private String role;
    private Integer gaji_harian;


    public User(){}

    public User(String userName) {
        this.setUserName(userName);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getGaji_harian() {
        return gaji_harian;
    }

    public void setGaji_harian(Integer gaji_harian) {
        this.gaji_harian = gaji_harian;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
