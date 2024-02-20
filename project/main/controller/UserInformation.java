package controller;



public class UserInformation {
    private String username;
    private String email;
    private String id;
    private String gender;
    private String phone;

    public UserInformation(String username, String email, String id, String gender, String phone) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.gender = gender;
        this.phone = phone;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
