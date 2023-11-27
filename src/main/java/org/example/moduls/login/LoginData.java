package org.example.moduls.login;

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
public class LoginData {
    Data data;
    java.lang.Error error;
    String jwt;
    UserData user;
}
