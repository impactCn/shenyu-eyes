package org.apache.shenyu.entity;

import org.apache.shenyu.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JarDO {

    private Integer total;

    private Integer parseTotal;

    private List<ParseJar> parseJar;

    private List<String> failureParseJar;

    private List<String> failureMatchJar;

    public static JarDO build(final List<String> fileNames) {
        JarDO jarDO = new JarDO();

        int count = 0;
        List<ParseJar> parseJars = new ArrayList<>();
        List<String> failureParseJars = new ArrayList<>();
        for (String fileName : fileNames) {
            Map<String, String> parse = StringUtil.parse(fileName);

            if (parse.containsKey("version")) {
                count++;
                ParseJar parseJar = new ParseJar();
                parseJar.setOriginal(parse.get("original"));
                parseJar.setPackageName(parse.get("packageName"));
                parseJar.setVersion(parse.get("version"));
                parseJars.add(parseJar);
            } else {
                failureParseJars.add(parse.get("original"));
            }

        }

        jarDO.setTotal(fileNames.size());
        jarDO.setParseTotal(count);
        jarDO.setParseJar(parseJars);
        jarDO.setFailureParseJar(failureParseJars);
        return jarDO;
    }


    public static class FailureParseJar {

        private String original;

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }
    }

    public static class ParseJar {

        private String packageName;

        private String version;

        private String original;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        @Override
        public String toString() {
            return "ParseJar{" +
                    "packageName='" + packageName + '\'' +
                    ", version='" + version + '\'' +
                    ", original='" + original + '\'' +
                    '}';
        }
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getParseTotal() {
        return parseTotal;
    }

    public void setParseTotal(Integer parseTotal) {
        this.parseTotal = parseTotal;
    }

    public List<ParseJar> getParseJar() {
        return parseJar;
    }

    public void setParseJar(List<ParseJar> parseJar) {
        this.parseJar = parseJar;
    }

    public List<String> getFailureParseJar() {
        return failureParseJar;
    }

    public void setFailureParseJar(List<String> failureParseJar) {
        this.failureParseJar = failureParseJar;
    }

    public List<String> getFailureMatchJar() {
        return failureMatchJar;
    }

    public void setFailureMatchJar(List<String> failureMatchJar) {
        this.failureMatchJar = failureMatchJar;
    }

    @Override
    public String toString() {
        return "JarDO{" +
                "total=" + total +
                ", parseTotal=" + parseTotal +
                ", parseJar=" + parseJar +
                ", failureParseJar=" + failureParseJar +
                ", failureMatchJar=" + failureMatchJar +
                '}';
    }
}
