package com.shangchao.utils;

import java.util.Random;

public class RandomUtil {
  public static final String[] LOWER_CASES = {
    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
    "t", "u", "v", "w", "x", "y", "z"
  };
  public static final String[] UPPER_CASES = {
    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
    "T", "U", "V", "W", "X", "Y", "Z"
  };
  public static final String[] NUMS_LIST = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
  public static final String[] SYMBOLS_ARRAY = {"!", "~", "^", "_", "*"};

  /**
   * 生成随机密码
   *
   * @param pwd_len 密码长度
   * @return 密码的字符串
   */
  public static String genRandomPwd(int pwd_len) {
    if (pwd_len < 6 || pwd_len > 20) {
      return "";
    }
    int lower = pwd_len / 2;

    int upper = (pwd_len - lower) / 2;

    int num = (pwd_len - lower) / 2;

    int symbol = pwd_len - lower - upper - num;

    StringBuffer pwd = new StringBuffer();
    Random random = new Random();
    int position = 0;
    while ((lower + upper + num + symbol) > 0) {
      if (lower > 0) {
        position = random.nextInt(pwd.length() + 1);

        pwd.insert(position, LOWER_CASES[random.nextInt(LOWER_CASES.length)]);
        lower--;
      }
      if (upper > 0) {
        position = random.nextInt(pwd.length() + 1);

        pwd.insert(position, UPPER_CASES[random.nextInt(UPPER_CASES.length)]);
        upper--;
      }
      if (num > 0) {
        position = random.nextInt(pwd.length() + 1);

        pwd.insert(position, NUMS_LIST[random.nextInt(NUMS_LIST.length)]);
        num--;
      }
      if (symbol > 0) {
        position = random.nextInt(pwd.length() + 1);

        pwd.insert(position, SYMBOLS_ARRAY[random.nextInt(SYMBOLS_ARRAY.length)]);
        symbol--;
      }

      //            System.out.println(pwd.toString());
    }

    return pwd.toString();
  }
  // 生成随机用户名，数字和字母组成,
  public static String getStringRandom(int length) {

    String val = "";
    Random random = new Random();

    // 参数length，表示生成几位随机数
    for (int i = 0; i < length; i++) {

      String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
      // 输出字母还是数字
      if ("char".equalsIgnoreCase(charOrNum)) {
        // 输出是大写字母还是小写字母
        int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
        val += (char) (random.nextInt(26) + temp);
      } else if ("num".equalsIgnoreCase(charOrNum)) {
        val += String.valueOf(random.nextInt(10));
      }
    }
    return val;
  }
}
