package idatt2106.systemutvikling.sparesti.mapper;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Base64MapperTest {

  @Test
  @DisplayName("Test converting byte array to base64 string and back")
  public void testToBase64StringAndToByteArray() throws Exception {
    // Read the image file into a byte array
    Path imagePath = Paths.get("src/test/java/testResources/testImage.jpeg");
    byte[] imageBytes = Files.readAllBytes(imagePath);

    // Convert the byte array to a base64 string
    String base64String = Base64Mapper.toBase64String(imageBytes);

    // Convert the base64 string back to a byte array
    byte[] decodedImageBytes = Base64Mapper.toByteArray(base64String);


    // Compare the original byte array with the decoded byte array
    assertArrayEquals(imageBytes, decodedImageBytes);

    // Convert the decoded byte array back to a base64 string
    String reencodedBase64String = Base64Mapper.toBase64String(decodedImageBytes);

    // Compare the original base64 string with the reencoded base64 string
    assertEquals(base64String, reencodedBase64String);
  }

  @Test
  public void testToByteArrayWithImageDataNoComma() {
    String base64String = "data:image/jpeg;base64" + Base64.getEncoder().encodeToString(new byte[]{1, 2, 3});

    byte[] decodedBytes = Base64Mapper.toByteArray(base64String);

    assertNull(decodedBytes);
  }
}
