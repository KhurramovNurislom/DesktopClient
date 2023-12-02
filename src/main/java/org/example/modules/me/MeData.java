package org.example.modules.me;

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
public class MeData {
    int id;
    String username;
    String email;
    String provider;
    boolean confirmed;
    boolean blocked;
    String createdAt;
    String updatedAt;
}
