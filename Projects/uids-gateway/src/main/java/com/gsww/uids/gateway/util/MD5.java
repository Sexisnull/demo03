package com.gsww.uids.gateway.util;

import java.security.MessageDigest;
import java.util.Random;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MD5
{
  static final int S11 = 7;
  static final int S12 = 12;
  static final int S13 = 17;
  static final int S14 = 22;
  static final int S21 = 5;
  static final int S22 = 9;
  static final int S23 = 14;
  static final int S24 = 20;
  static final int S31 = 4;
  static final int S32 = 11;
  static final int S33 = 16;
  static final int S34 = 23;
  static final int S41 = 6;
  static final int S42 = 10;
  static final int S43 = 15;
  static final int S44 = 21;
  static final byte[] PADDING = { -128 };

  private long[] state = new long[4];
  private long[] count = new long[2];

  private byte[] buffer = new byte[64];
  public String digestHexStr;
  private byte[] digest = new byte[16];

  public String getMD5ofStr(String inbuf)
  {
    md5Init();
    md5Update(inbuf.getBytes(), inbuf.length());
    md5Final();
    this.digestHexStr = "";
    for (int i = 0; i < 16; i++) {
      this.digestHexStr += byteHEX(this.digest[i]);
    }
    return this.digestHexStr;
  }

  public MD5()
  {
    md5Init();
  }

  private void md5Init()
  {
    this.count[0] = 0L;
    this.count[1] = 0L;

    this.state[0] = 1732584193L;
    this.state[1] = 4023233417L;
    this.state[2] = 2562383102L;
    this.state[3] = 271733878L;
  }

  private long F(long x, long y, long z)
  {
    return x & y | (x ^ 0xFFFFFFFF) & z;
  }

  private long G(long x, long y, long z) {
    return x & z | y & (z ^ 0xFFFFFFFF);
  }

  private long H(long x, long y, long z)
  {
    return x ^ y ^ z;
  }

  private long I(long x, long y, long z) {
    return y ^ (x | z ^ 0xFFFFFFFF);
  }

  private long FF(long a, long b, long c, long d, long x, long s, long ac)
  {
    a += F(b, c, d) + x + ac;
    a = (int)a << (int)s | (int)a >>> (int)(32L - s);
    a += b;
    return a;
  }

  private long GG(long a, long b, long c, long d, long x, long s, long ac) {
    a += G(b, c, d) + x + ac;
    a = (int)a << (int)s | (int)a >>> (int)(32L - s);
    a += b;
    return a;
  }

  private long HH(long a, long b, long c, long d, long x, long s, long ac) {
    a += H(b, c, d) + x + ac;
    a = (int)a << (int)s | (int)a >>> (int)(32L - s);
    a += b;
    return a;
  }

  private long II(long a, long b, long c, long d, long x, long s, long ac) {
    a += I(b, c, d) + x + ac;
    a = (int)a << (int)s | (int)a >>> (int)(32L - s);
    a += b;
    return a;
  }

  private void md5Update(byte[] inbuf, int inputLen)
  {
    byte[] block = new byte[64];
    int index = (int)(this.count[0] >>> 3) & 0x3F;

    if ((this.count[0] += (inputLen << 3)) < inputLen << 3)
      this.count[1] += 1L;
    this.count[1] += (inputLen >>> 29);
    int partLen = 64 - index;
    int i;
    if (inputLen >= partLen) {
      md5Memcpy(this.buffer, inbuf, index, 0, partLen);
      md5Transform(this.buffer);
      for ( i = partLen; i + 63 < inputLen; i += 64) {
        md5Memcpy(block, inbuf, 0, i, 64);
        md5Transform(block);
      }
      index = 0;
    } else {
      i = 0;
    }

    md5Memcpy(this.buffer, inbuf, index, i, inputLen - i);
  }

  private void md5Final()
  {
    byte[] bits = new byte[8];

    Encode(bits, this.count, 8);

    int index = (int)(this.count[0] >>> 3) & 0x3F;
    int padLen = index < 56 ? 56 - index : 120 - index;
    md5Update(PADDING, padLen);

    md5Update(bits, 8);

    Encode(this.digest, this.state, 16);
  }

  private void md5Memcpy(byte[] output, byte[] input, int outpos, int inpos, int len)
  {
    for (int i = 0; i < len; i++)
      output[(outpos + i)] = input[(inpos + i)];
  }

  private void md5Transform(byte[] block)
  {
    long a = this.state[0]; long b = this.state[1]; long c = this.state[2]; long d = this.state[3];
    long[] x = new long[16];

    Decode(x, block, 64);

    a = FF(a, b, c, d, x[0], 7L, 3614090360L);
    d = FF(d, a, b, c, x[1], 12L, 3905402710L);
    c = FF(c, d, a, b, x[2], 17L, 606105819L);
    b = FF(b, c, d, a, x[3], 22L, 3250441966L);
    a = FF(a, b, c, d, x[4], 7L, 4118548399L);
    d = FF(d, a, b, c, x[5], 12L, 1200080426L);
    c = FF(c, d, a, b, x[6], 17L, 2821735955L);
    b = FF(b, c, d, a, x[7], 22L, 4249261313L);
    a = FF(a, b, c, d, x[8], 7L, 1770035416L);
    d = FF(d, a, b, c, x[9], 12L, 2336552879L);
    c = FF(c, d, a, b, x[10], 17L, 4294925233L);
    b = FF(b, c, d, a, x[11], 22L, 2304563134L);
    a = FF(a, b, c, d, x[12], 7L, 1804603682L);
    d = FF(d, a, b, c, x[13], 12L, 4254626195L);
    c = FF(c, d, a, b, x[14], 17L, 2792965006L);
    b = FF(b, c, d, a, x[15], 22L, 1236535329L);

    a = GG(a, b, c, d, x[1], 5L, 4129170786L);
    d = GG(d, a, b, c, x[6], 9L, 3225465664L);
    c = GG(c, d, a, b, x[11], 14L, 643717713L);
    b = GG(b, c, d, a, x[0], 20L, 3921069994L);
    a = GG(a, b, c, d, x[5], 5L, 3593408605L);
    d = GG(d, a, b, c, x[10], 9L, 38016083L);
    c = GG(c, d, a, b, x[15], 14L, 3634488961L);
    b = GG(b, c, d, a, x[4], 20L, 3889429448L);
    a = GG(a, b, c, d, x[9], 5L, 568446438L);
    d = GG(d, a, b, c, x[14], 9L, 3275163606L);
    c = GG(c, d, a, b, x[3], 14L, 4107603335L);
    b = GG(b, c, d, a, x[8], 20L, 1163531501L);
    a = GG(a, b, c, d, x[13], 5L, 2850285829L);
    d = GG(d, a, b, c, x[2], 9L, 4243563512L);
    c = GG(c, d, a, b, x[7], 14L, 1735328473L);
    b = GG(b, c, d, a, x[12], 20L, 2368359562L);

    a = HH(a, b, c, d, x[5], 4L, 4294588738L);
    d = HH(d, a, b, c, x[8], 11L, 2272392833L);
    c = HH(c, d, a, b, x[11], 16L, 1839030562L);
    b = HH(b, c, d, a, x[14], 23L, 4259657740L);
    a = HH(a, b, c, d, x[1], 4L, 2763975236L);
    d = HH(d, a, b, c, x[4], 11L, 1272893353L);
    c = HH(c, d, a, b, x[7], 16L, 4139469664L);
    b = HH(b, c, d, a, x[10], 23L, 3200236656L);
    a = HH(a, b, c, d, x[13], 4L, 681279174L);
    d = HH(d, a, b, c, x[0], 11L, 3936430074L);
    c = HH(c, d, a, b, x[3], 16L, 3572445317L);
    b = HH(b, c, d, a, x[6], 23L, 76029189L);
    a = HH(a, b, c, d, x[9], 4L, 3654602809L);
    d = HH(d, a, b, c, x[12], 11L, 3873151461L);
    c = HH(c, d, a, b, x[15], 16L, 530742520L);
    b = HH(b, c, d, a, x[2], 23L, 3299628645L);

    a = II(a, b, c, d, x[0], 6L, 4096336452L);
    d = II(d, a, b, c, x[7], 10L, 1126891415L);
    c = II(c, d, a, b, x[14], 15L, 2878612391L);
    b = II(b, c, d, a, x[5], 21L, 4237533241L);
    a = II(a, b, c, d, x[12], 6L, 1700485571L);
    d = II(d, a, b, c, x[3], 10L, 2399980690L);
    c = II(c, d, a, b, x[10], 15L, 4293915773L);
    b = II(b, c, d, a, x[1], 21L, 2240044497L);
    a = II(a, b, c, d, x[8], 6L, 1873313359L);
    d = II(d, a, b, c, x[15], 10L, 4264355552L);
    c = II(c, d, a, b, x[6], 15L, 2734768916L);
    b = II(b, c, d, a, x[13], 21L, 1309151649L);
    a = II(a, b, c, d, x[4], 6L, 4149444226L);
    d = II(d, a, b, c, x[11], 10L, 3174756917L);
    c = II(c, d, a, b, x[2], 15L, 718787259L);
    b = II(b, c, d, a, x[9], 21L, 3951481745L);

    this.state[0] += a;
    this.state[1] += b;
    this.state[2] += c;
    this.state[3] += d;
  }

  private void Encode(byte[] output, long[] input, int len)
  {
    int i = 0; for (int j = 0; j < len; j += 4) {
      output[j] = ((byte)(int)(input[i] & 0xFF));
      output[(j + 1)] = ((byte)(int)(input[i] >>> 8 & 0xFF));
      output[(j + 2)] = ((byte)(int)(input[i] >>> 16 & 0xFF));
      output[(j + 3)] = ((byte)(int)(input[i] >>> 24 & 0xFF));

      i++;
    }
  }

  private void Decode(long[] output, byte[] input, int len)
  {
    int i = 0; for (int j = 0; j < len; j += 4) {
      output[i] = 
        (b2iu(input[j]) | b2iu(input[(j + 1)]) << 8 | 
        b2iu(input[(j + 2)]) << 16 | b2iu(input[(j + 3)]) << 24);

      i++;
    }
  }

  public static long b2iu(byte b)
  {
    return b < 0 ? b & 0xFF : b;
  }

  public static String byteHEX(byte ib)
  {
    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 
      'B', 'C', 'D', 'E', 'F' };
    char[] ob = new char[2];
    ob[0] = Digit[(ib >>> 4 & 0xF)];
    ob[1] = Digit[(ib & 0xF)];
    String s = new String(ob);
    return s;
  }

  public String getKeyEd(String Txt, String encrypt_key) {
    if ((Txt == null) || (Txt.length() == 0)) {
      Txt = "";
    }
    encrypt_key = getMD5ofStr(encrypt_key);
    int m = 0;
    int n = encrypt_key.length();
    StringBuffer temp = new StringBuffer();
    for (int i = 0; i < Txt.length(); i++) {
      if (m == n) {
        m = 0;
      }
      char cc1 = encrypt_key.charAt(m);
      int cc4 = cc1 ^ Txt.charAt(i);
      char cc2 = (char)cc4;
      temp.append(cc2);
      m++;
    }
    return String.valueOf(temp.toString());
  }

  public String encrypt(String Txt, String Key1)
  {
    int number = getRandom();

    String encrypt_key = getMD5ofStr(Integer.toString(number));
    int ctr = 0;
    int num = Txt.length();
    int num1 = encrypt_key.length();

    Txt = Txt.trim();
    StringBuffer temp = new StringBuffer();
    for (int i = 0; i < num; i++)
    {
      if (ctr == num1)
        ctr = 0;
      char cc1 = encrypt_key.charAt(ctr);
      temp.append(cc1);
      char cc2 = Txt.charAt(i);
      int cc4 = cc1 ^ cc2;
      char cc3 = (char)cc4;
      temp.append(cc3);
      ctr++;
    }
    String er = temp.toString();
    String retur = getKeyEd(er, Key1);
    retur = setFromBASE64(retur);
    retur = retur.replaceAll("\n", "");
    retur = retur.replaceAll("\r", "");
    retur = retur.replaceAll("<br>", "");
    return retur;
  }

  public String decrypt(String Txt, String Key1)
  {
    Txt = getFromBASE64(Txt);
    Txt = getKeyEd(Txt, Key1);
    StringBuffer temp = new StringBuffer();
    char temp1 = '\000';
    for (int i = 0; i < Txt.length(); i++) {
      temp1 = Txt.charAt(i);
      i++;
      char cc1 = Txt.charAt(i);
      int cc2 = cc1 ^ temp1;
      char cc3 = (char)cc2;
      temp = temp.append(cc3);
    }
    Txt = temp.toString();
    return Txt;
  }

  public String encryptMB(String Txt, String Key1)
  {
    int num = Txt.length();
    if (num > 0) {
      Txt = setFromBASE64(Txt);
    }
    Txt = encrypt(Txt, Key1);
    Txt = Txt.replaceAll("\n", "");
    Txt = Txt.replaceAll("\r", "");
    Txt = Txt.replaceAll("<br>", "");
    return Txt;
  }

  public String decryptMB(String Txt, String Key1)
  {
    String s = decrypt(Txt, Key1);
    s = getFromBASE64(s);
    return s;
  }

  public String getFromBASE64(String s)
  {
    if ((s == null) || (s.length() <= 0))
      return "";
    BASE64Decoder decoder = new BASE64Decoder();
    try {
      byte[] b = decoder.decodeBuffer(s);
      return new String(b, "UTF-8"); } catch (Exception e) {
    }
    return "";
  }

  public String setFromBASE64(String s)
  {
    if ((s == null) || (s.length() <= 0))
      return "";
    BASE64Encoder encoder = new BASE64Encoder();
    try {
      return encoder.encode(s.getBytes("UTF-8"));
    } catch (Exception e) {
    }
    return "";
  }

  public String getDecTxtChange(String Txt)
  {
    int n = Txt.length();
    int num = 0;

    String tem1 = "";
    String tem2 = "";
    if (n % 2 == 0)
      num = n / 2;
    else {
      num = (n + 1) / 2;
    }
    tem1 = Txt.substring(0, num);
    tem2 = Txt.substring(num, n);

    StringBuffer temp1 = new StringBuffer(tem1);
    StringBuffer temp2 = new StringBuffer(tem2);
    StringBuffer temp3 = new StringBuffer();

    temp1 = temp1.reverse();
    temp2 = temp2.reverse();

    for (int i = 0; i < num; i++)
    {
      char tem = decAscChange(temp1.charAt(i));
      temp3.append(tem);
      if (n % 2 == 0) {
        char temm = decAscChange(temp2.charAt(i));
        temp3.append(temm);
      }
      else if (i < num - 1) {
        char temm = decAscChange(temp2.charAt(i));
        temp3.append(temm);
      }

    }

    return temp3.toString();
  }

  public char decAscChange(char ib)
  {
    int num = ib;
    String num1 = Integer.toBinaryString(num);
    StringBuffer temp1 = new StringBuffer();
    String ll = "";
    int n = num1.length();
    int k = 8 - n;
    if (k > 0) {
      for (int i = 0; i < k; i++) {
        temp1 = temp1.append("0");
      }
    }

    ll = temp1.toString();
    ll = ll + num1;
    StringBuffer temp7 = new StringBuffer();
    for (int i = 0; i < 4; i++) {
      temp7 = temp7.append(ll.charAt(2 * i));
    }
    for (int i = 4; i > 0; i--) {
      temp7 = temp7.append(ll.charAt(2 * i - 1));
    }

    String lll = temp7.toString();
    char[] er = lll.toCharArray();

    int sum = 7;
    int sum1 = 0;

    for (int j = 0; j < 8; j++) {
      char k1 = er[j];
      int k7 = k1;
      if (k7 == 49) {
        double num12 = Math.pow(2.0D, sum);
        sum1 += (int)num12;
      }

      sum--;
    }

    char k22 = (char)sum1;
    return k22;
  }

  public String getTxtChang(String Txt) {
    StringBuffer temp1 = new StringBuffer();
    StringBuffer temp2 = new StringBuffer();

    for (int i = 0; i < Txt.length(); i++) {
      if (i % 2 == 0) {
        char tem = getByteDEX(Txt.charAt(i));
        temp1 = temp1.append(tem);
      } else {
        char tem1 = getByteDEX(Txt.charAt(i));
        temp2 = temp2.append(tem1);
      }
    }

    temp1 = temp1.reverse();
    temp2 = temp2.reverse();
    temp1.append(temp2);

    return String.valueOf(temp1.toString());
  }

  public char getByteDEX(char ib)
  {
    int num = ib;
    String num1 = Integer.toBinaryString(num);
    StringBuffer temp1 = new StringBuffer();
    int n = num1.length();
    int k = 8 - n;
    String ll = "";
    if (k > 0) {
      for (int i = 0; i < k; i++) {
        temp1 = temp1.append("0");
      }
      ll = temp1.toString();
      ll = ll + num1;
    }

    StringBuffer temp7 = new StringBuffer();
    k = 7;
    for (int i = 0; i < 4; i++) {
      temp7 = temp7.append(ll.charAt(i));
      temp7 = temp7.append(ll.charAt(k--));
    }
    String lll = temp7.toString();
    char[] er = lll.toCharArray();
    int sum = 7;
    int sum1 = 0;
    for (int j = 0; j < 8; j++) {
      char k1 = er[j];
      int k7 = k1;
      if (k7 == 49) {
        double num12 = Math.pow(2.0D, sum);
        sum1 += (int)num12;
      }

      sum--;
    }

    char k22 = (char)sum1;
    return k22;
  }

  public int getRandom() {
    Random generator = new Random();
    int limit = 320000;
    int randomNub = 1;
    boolean j = true;
    while (j) {
      randomNub = (int)(generator.nextDouble() * limit);
      if (randomNub > 10)
        j = false;
    }
    return randomNub;
  }

  public static final String encodeMd5(String s)
  {
    char[] hexDigits = { '0', '1', '2', '3', '4', 
      '5', '6', '7', '8', '9', 
      'a', 'b', 'c', 'd', 'e', 'f' };
    try {
      byte[] btInput = s.getBytes();

      MessageDigest mdInst = MessageDigest.getInstance("MD5");

      mdInst.update(btInput);

      byte[] md = mdInst.digest();

      int j = md.length;
      char[] str = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
        str[(k++)] = hexDigits[(byte0 & 0xF)];
      }
      return new String(str);
    }
    catch (Exception e) {
      e.printStackTrace();
    }return null;
  }
}