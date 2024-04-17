package idatt2106.systemutvikling.sparesti.mapper;

import java.util.Base64;
import java.util.logging.Logger;

public class Base64Mapper {
  static Logger logger = Logger.getLogger(Base64Mapper.class.getName());

  public static String toBase64String(byte[] image) {
    logger.info("Converting image to base64 string");
    if (image != null) {
      logger.info("Image length: " + image.length);
      String base64String = Base64.getEncoder().encodeToString(image);
      logger.info("Base64 string length: " + base64String.length());
      return base64String;
    }
    return null;
  }

  public static byte[] toByteArray(String base64String) {
    logger.info("Converting base64 string to image");
    if (base64String != null) {
      // Check if the base64 string starts with "data:image/"
      if (base64String.startsWith("data:image/")) {
       // Find the index of the comma separating the image type and the base64 string
        int commaIndex = base64String.indexOf(",");
        if (commaIndex != -1) {
          // Extract the image type from the base64 string
          String imageType = base64String.substring("data:image/".length(), commaIndex);
          logger.info("Image type: " + imageType);
         // Remove the image type from the base64 string
          base64String = base64String.substring(commaIndex + 1);
          logger.info("Converting base64 string to image");
        return Base64.getDecoder().decode(base64String);
        }
      } else {
        return Base64.getDecoder().decode(base64String);
      }
    }
    logger.severe("Base64 string is null");
    return null;
  }

}
