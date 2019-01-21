package org.anki.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {

    private Long id;
    private String login;
    private String password;

}
