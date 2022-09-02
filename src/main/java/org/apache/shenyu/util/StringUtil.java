package org.apache.shenyu.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static Map<String, String> parse(final String fileName) {
        Map<String, String> map = new HashMap<>(8);
        String pattern = "([-]\\d.*)\\.jar";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(fileName);
        if (matcher.find()) {
            String versionGroup = matcher.group(0);
            String packageName = fileName.replace(versionGroup, "");
            String version = versionGroup.replace(".jar", "").substring(1);
            map.put("packageName", packageName);
            map.put("version", version);
            map.put("original", fileName);
        }
        map.put("original", fileName);
        return map;
    }

    public static boolean match(String content, String checkTarget) {
        Pattern pattern = Pattern.compile(checkTarget);

        Matcher matcher = pattern.matcher(content);

        return matcher.find();
    }


}
