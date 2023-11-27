package org.example.moduls.users;

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
public class EUsDaUsPerUsDaAttributes {
    String username;
    String email;
    FUsDaUsPerUsDaAttKalits kalits;
    FUsDaUsPerUsDaAttRasm rasm;
}
