package org.example.moduls.messages;

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
public class Attributes {
    String mavzu;
    String imzo;
    String xabar;
    String createdAt;
    String updatedAt;
    String publishedAt;
    boolean checked;
    String hash;
}
