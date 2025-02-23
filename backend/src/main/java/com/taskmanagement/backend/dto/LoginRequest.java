package com.taskmanagement.backend.dto;

import lombok.Getter;
import lombok.Setter;

/*
DTO: Simple objects used for transferring data between layers (Controller ⇄ Service).
Why Do You Need DTOs?
Prevents direct exposure of the User entity

Your User entity contains sensitive fields like password.
Using a DTO ensures only required fields are exposed.
Decouples API requests from the database model

Suppose later you add email and roles in the User entity, the API should not be affected.
DTOs allow flexibility without breaking API consumers.
Improves security & validation

You can validate input fields using @Valid in the DTO before passing them to the service.
*/
//This DTO will receive login details from the user. This ensures that the request body contains only username and password hence avoiding unnecessary fields from Users
@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
