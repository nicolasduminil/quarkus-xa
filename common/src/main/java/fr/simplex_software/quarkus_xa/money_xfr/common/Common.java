package fr.simplex_software.quarkus_xa.money_xfr.common;

import java.util.*;

public class Common
{
  public static String getRandomId()
  {
    int leftLimit = 97;
    int rightLimit = 122;
    int targetStringLength = 7;

    return new Random().ints(leftLimit, rightLimit + 1)
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
  }
}
