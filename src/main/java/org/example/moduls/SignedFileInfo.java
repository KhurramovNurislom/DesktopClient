package org.example.moduls;

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
public class SignedFileInfo {
    int id;
    String name;
    String filePath;
    String hash;
    String ext;
    double size;
    String url;
    String createdAt;
    String updatedAt;
}
