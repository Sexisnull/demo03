package com.gsww.jup.util;

public class RandomCodeUtil
{
  public static String getRandomNumber(int length)
  {
    String result = "";
    for (int i = 0; i < length; i++) {
      result = result + (int)(Math.random() * 9.0D);
    }
    return result;
  }
}