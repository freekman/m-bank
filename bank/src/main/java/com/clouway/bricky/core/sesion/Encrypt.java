package com.clouway.bricky.core.sesion;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class Encrypt {

  public String sha1(String input) {
    MessageDigest mDigest;
    try {
      mDigest = MessageDigest.getInstance("SHA1");
    } catch (NoSuchAlgorithmException e) {
      return "";
    }

    byte[] result = mDigest.digest(input.getBytes());
    StringBuilder sb = new StringBuilder();
    for (byte aResult : result) {
      sb.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
    }

    return sb.toString();
  }
}