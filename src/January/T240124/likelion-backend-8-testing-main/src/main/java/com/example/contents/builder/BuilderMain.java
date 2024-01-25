package com.example.contents.builder;

public class BuilderMain {
    public static void main(String[] args) {
        // User에는 총 8개의 필드가 있고
        // 전체 필드를 받아서 생성해주는 생성자가 있을 때
        // username, email, firstName, lastName 만 넣어서 초기화를 하고 싶다면
        // gildong, gildong@gmail.com, Gildong, Hong

        /*
        User newUSer = new User(
            null, "gildong", null, "gildong@gmail.com", null, "Gildong", "Hong", null
        );

        // javaBean 방식
        // setter를 이용해서 객체에 데이터를 넣어줌
        User newUser2 = new User();
        newUser2.setUsername("gildong");
        newUser2.setEmail("gildong@gmail.com");
        newUser2.setFirstName("Gildong");
        newUser2.setLastName("Hong");


        User.UserBuilder userBuilder = new User.UserBuilder();
        // UserBuilder
        /*
        User.UserBuilder a = userBuilder.id(1L);
        User.UserBuilder b = a.username("gildong");
        User.UserBuilder c = b.email("gildong@gmail.com");
         */

        User newUser = User.builder()
                .id(1L)
                .username("gildong")
                .email("gildong@gmail.com")
                .build();

    }
}
