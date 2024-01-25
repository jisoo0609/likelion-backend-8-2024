package com.example.contents.builder;

public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String status;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    // User를 만들 준비를 하는 Builder 클래스
    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private String email;
        private String phone;
        private String firstName;
        private String lastName;
        private String status;

        // 자기 자신의 속성을 할당한 뒤
        // 자기 자신을 반환한다.
        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(
                    this.id,
                    this.username,
                    this.password,
                    this.email,
                    this.phone,
                    this.firstName,
                    this.lastName,
                    this.status
            );
        }
    }

    public User() {}

    public User(Long id, String username, String password, String email, String phone, String firstName, String lastName, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}