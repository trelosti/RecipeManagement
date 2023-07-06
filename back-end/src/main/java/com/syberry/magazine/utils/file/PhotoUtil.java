package com.syberry.magazine.utils.file;

import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.exception.PhotoCompressionException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * The utility class for photos.
 */
public class PhotoUtil {
  /**
   * This method is responsible for photo compression.
   *
   * @param data A byte array with photo data.
   * @return A byte array with compressed photo data.
   */
  public static byte[] compressPhoto(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setLevel(Deflater.BEST_COMPRESSION);
    deflater.setInput(data);
    deflater.finish();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[PhotoConstant.COMPRESSION_VALUE];

    while (!deflater.finished()) {
      int size = deflater.deflate(tmp);
      outputStream.write(tmp, 0, size);
    }

    try {
      outputStream.close();
    } catch (IOException e) {
      throw new PhotoCompressionException(String.format(ExceptionMessage.PHOTO_COMPRESSION_EXCEPTION,
          e.getMessage()), e);
    }

    return outputStream.toByteArray();
  }

  /**
   * This method is responsible for photo decompression.
   *
   * @param data A byte array with photo data.
   * @return A byte array with decompressed photo data.
   */
  public static byte[] decompressPhoto(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[PhotoConstant.COMPRESSION_VALUE];

    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(tmp);
        outputStream.write(tmp, 0, count);
      }

      outputStream.close();
    } catch (DataFormatException | IOException e) {
      throw new PhotoCompressionException(String.format(ExceptionMessage.PHOTO_COMPRESSION_EXCEPTION,
          e.getMessage()), e);
    }

    return outputStream.toByteArray();
  }
}
