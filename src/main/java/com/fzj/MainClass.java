package com.fzj;

import com.fzj.util.PropUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZJ.Fun
 * @date 2020年7月3日09:21:56
 */
public class MainClass {

    // 在使用正则表达式时，利用好其预编译功能，可以有效加快正则匹配速度。__阿里编码规约
    public static final Pattern COMPILE = Pattern.compile("[\t\r\n\\pP\\pS]");

    public static void main(String[] args) {

        String content = readTxt();

        if (content == null || content.length() == 0) {
            throw new RuntimeException("The content of the file is empty");
        }

        // 替换掉换行符和标点符号
        content = replaceBlank(content);

        Map<String, Integer> count = contentStatistics(content);

        List<Entry<String, Integer>> entries = sortByValue(count);

        System.out.println("文本中");
        for (Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + " 出现了 " + entry.getValue() + " 次 ");
        }

    }

    /**
     * 将 You Have Only One Life.txt 的文本内容读出
     */
    public static String readTxt() {
        String content = null;

        char[] buff;
        // 基于 try resource 机制自动关闭资源
        try (InputStream inputStream = MainClass.class.getClassLoader().getResourceAsStream(PropUtil.getProp("file.name"))) {
            assert inputStream != null;

            try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream); BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                buff = new char[inputStream.available()];
                bufferedReader.read(buff, 0, inputStream.available());
                content = new String(buff);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * 替换掉所有换行符和标点符号,保留空格
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Matcher m = COMPILE.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static Map<String, Integer> contentStatistics(String content) {

        String[] split = content.split("\\s");
        List<String> strings = Arrays.asList(split);
        Map<String, Integer> count = new HashMap<>(strings.size());

        for (String chart : strings) {
            chart = chart.trim();
            if (!count.containsKey(chart)) {
                count.put(chart, 1);
            } else {
                Integer frequency = count.get(chart) + 1;
                count.put(chart, frequency);
            }
        }

        return count;
    }

    /**
     * 根据 Map 的 value 值进行降序排序
     */
    private static List<Map.Entry<String, Integer>> sortByValue(Map<String, Integer> nowPartTwoData) {
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<>(nowPartTwoData.entrySet());
        //然后通过比较器来实现排序
        //降序排序
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return list;
    }

}

