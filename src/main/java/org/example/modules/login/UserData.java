package org.example.modules.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties
public class UserData {
    int id;
    String username;
    String email;
    String provider;
    boolean confirmed;
    boolean blocked;
    String createdAt;
    String updatedAt;
}
