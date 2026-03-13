package com.nanyang.academy.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChineseUtils {
    public static String ChineseToPinyin(String chinese) {
        StringBuilder pinyin = new StringBuilder();

        for (char c : chinese.toCharArray()) {
            String[] temp = PinyinHelper.toHanyuPinyinStringArray(c);
            if (temp != null) {
                pinyin.append(temp[0]); // 默认获取第一个读音，如果有多个读音
            } else {
                pinyin.append(c); // 非中文字符保持不变
            }
        }

        //System.out.println(pinyin.toString()); // 输出：nihao，shijie
        return pinyin.toString();
    }

    public static String toPinyin(String chinese) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder sb = new StringBuilder();
        char[] chars = chinese.toCharArray();
        for (char c : chars) {
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c >= '\u4e00' && c <= '\u9fa5') {
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    sb.append(pinyinArray[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String aa = "王国庆";
        String p = ChineseToPinyin(aa);
        System.out.println(p);
        String pa = toPinyin(aa);
        System.out.println(pa);
    }
}
