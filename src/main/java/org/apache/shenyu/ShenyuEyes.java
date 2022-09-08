package org.apache.shenyu;


import org.apache.shenyu.entity.JarDO;
import org.apache.shenyu.env.CheckEnv;
import org.apache.shenyu.util.FileUtil;
import org.apache.shenyu.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ShenyuEyes {

    public static void main(String[] args) {

        // check py version
        System.out.println("Start to check python version...");
        if (CheckEnv.PYTHON_CHECK) {
            System.out.println("The python version check passed.");

//            String filePath = args[0];
            String filePath = "C:\\Users\\User\\Desktop\\新建文件夹 (2)\\apache-shenyu-2.5.0-bootstrap-bin.tar.gz";

            String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);

            System.out.println("Start to unzip " + fileName + "...");

            String destDir = filePath.substring(0, filePath.lastIndexOf("\\"));
            String fileDir = fileName.replace(".tar.gz", "");

            FileUtil.unTarGz(filePath, destDir);
            System.out.println("Decompression succeeded.");
            System.out.println("Start to check LICENSE...");
            List<String> fileNames = FileUtil.getFileName(destDir + "\\" + fileDir + "\\lib");

            JarDO jarDO = JarDO.build(fileNames);
            String content = FileUtil.read(destDir + "\\" + fileDir + "\\LICENSE");

            List<String> failureMatchJar = new ArrayList<>();

            for (JarDO.ParseJar parseJar : jarDO.getParseJar()) {

                if (parseJar.getOriginal().contains("shenyu")) {
                    continue;
                }

                if (!StringUtil.match(content, parseJar.getPackageName() + " " + parseJar.getVersion())) {
                    failureMatchJar.add(parseJar.getOriginal());
                }

            }

            if (failureMatchJar.size() > 0) {
                System.err.println("The following jars need to be modified");
                failureMatchJar.forEach(System.err::println);
            }

            jarDO.setFailureMatchJar(failureMatchJar);

        } else {
            System.err.println("The python version not 3.8");
        }



    }

}
