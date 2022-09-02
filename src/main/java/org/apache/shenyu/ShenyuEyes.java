package org.apache.shenyu;


import org.apache.shenyu.entity.JarDO;
import org.apache.shenyu.util.FileUtil;
import org.apache.shenyu.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ShenyuEyes {

    public static void main(String[] args) {
//        String filePath = "C:\\Users\\User\\Desktop\\新建文件夹 (2)\\apache-shenyu-2.5.0-bootstrap-bin.tar.gz";

        String filePath = args[0];

        String destDir = filePath.substring(0, filePath.lastIndexOf("\\"));
        String fileDir = filePath.substring(filePath.lastIndexOf("\\") + 1).replace(".tar.gz", "");

//
        FileUtil.unTarGz(filePath, destDir);
        List<String> fileNames = FileUtil.getFileName(destDir + "\\" + fileDir + "\\lib");

        JarDO jarDO = JarDO.build(fileNames);

        String content = FileUtil.read(destDir + "\\" + fileDir + "\\LICENSE");

        List<String> failureMatchJar = new ArrayList<>();

        for (JarDO.ParseJar parseJar : jarDO.getParseJar()) {

            if (!StringUtil.match(content, parseJar.getPackageName() + " " + parseJar.getVersion())) {
                failureMatchJar.add(parseJar.getOriginal());
                System.err.println(parseJar.getOriginal());
            }

        }

        jarDO.setFailureMatchJar(failureMatchJar);

    }

}
