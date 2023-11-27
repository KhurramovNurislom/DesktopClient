package org.example.moduls.upload;

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
public class Upload {
    int id;
    String name;
    String hash;
    String ext;
    String mime;
    double size;
    String url;
    String createdAt;
    String updatedAt;
}
