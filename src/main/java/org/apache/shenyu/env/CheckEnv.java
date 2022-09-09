package org.apache.shenyu.env;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author sinsy
 * @date 2022-09-08
 */
public class CheckEnv {

    private final static String PYTHON_VERSION = "Python 3.8";

    public static boolean PYTHON_CHECK = false;

    static {
        String exe = "python -V";
        Process process;
        try {
            process = Runtime.getRuntime().exec(exe);
            InputStream inputStream = process.getInputStream();
            String str = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            if (str.contains(PYTHON_VERSION)) {
                PYTHON_CHECK = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
