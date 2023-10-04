package com.cydeo.entity;

import com.cydeo.enums.Gender;

public class User extends BaseEntity{

    private String firstName;
    private String lastName;
    private String userName;
    private String  password;
    private boolean enabled;
    private String phone;
    private Role role;
    private Gender gender;
}
