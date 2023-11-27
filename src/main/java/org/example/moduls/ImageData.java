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
public class ImageData {

    String imageName;
    String data;

    public ImageData() {
    }

    public ImageData(String imageName, String data) {
        this.imageName = imageName;
        this.data = data;
    }
}
