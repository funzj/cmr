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
 * @date 2020��7��3��09:21:56
 */
public class MainClass {

    // ��ʹ��������ʽʱ�����ú���Ԥ���빦�ܣ�������Ч�ӿ�����ƥ���ٶȡ�__��������Լ
    public static final Pattern COMPILE = Pattern.compile("[\t\r\n\\pP\\pS]");

    public static void main(String[] args) {

        String content = readTxt();

        if (content == null || content.length() == 0) {
            throw new RuntimeException("The content of the file is empty");
        }

        // �滻�����з��ͱ�����
        content = replaceBlank(content);

        Map<String, Integer> count = contentStatistics(content);

        List<Entry<String, Integer>> entries = sortByValue(count);

        System.out.println("�ı���");
        for (Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + " ������ " + entry.getValue() + " �� ");
        }

    }

    /**
     * �� You Have Only One Life.txt ���ı����ݶ���
     */
    public static String readTxt() {
        String content = null;

        char[] buff;
        // ���� try resource �����Զ��ر���Դ
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
     * �滻�����л��з��ͱ�����,�����ո�
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
     * ���� Map �� value ֵ���н�������
     */
    private static List<Map.Entry<String, Integer>> sortByValue(Map<String, Integer> nowPartTwoData) {
        //���ｫmap.entrySet()ת����list
        List<Map.Entry<String, Integer>> list = new ArrayList<>(nowPartTwoData.entrySet());
        //Ȼ��ͨ���Ƚ�����ʵ������
        //��������
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return list;
    }

}

