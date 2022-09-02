package org.apache.shenyu.util;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {


    public static void unTarGz(String tarGzFilePath, String destDir) {

        Path source = Paths.get(tarGzFilePath);
        Path target = Paths.get(destDir);

        try (InputStream fi = Files.newInputStream(source);
             BufferedInputStream bi = new BufferedInputStream(fi);
             GzipCompressorInputStream gzi = new GzipCompressorInputStream(bi);
             TarArchiveInputStream ti = new TarArchiveInputStream(gzi)) {

            ArchiveEntry entry;
            while ((entry = ti.getNextEntry()) != null) {

                Path newPath = zipSlipProtect(entry, target);

                if (entry.isDirectory()) {
                    Files.createDirectories(newPath);
                } else {
                    Path parent = newPath.getParent();
                    if (parent != null) {
                        if (Files.notExists(parent)) {
                            Files.createDirectories(parent);
                        }
                    }
                    Files.copy(ti, newPath, StandardCopyOption.REPLACE_EXISTING);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> getFileName(final String path) {
        List<String> fileNames = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        if (tempList == null) {
            throw new RuntimeException("empty folder");
        }

        for (File f : tempList) {
            fileNames.add(f.getName());
        }
        return fileNames;

    }

    public static String read(String filePath) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = in.readLine()) != null) {
                builder.append(str);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Path zipSlipProtect(ArchiveEntry entry,Path targetDir) throws IOException {

        Path targetDirResolved = targetDir.resolve(entry.getName());
        Path normalizePath = targetDirResolved.normalize();

        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Compressed file corrupted: " + entry.getName());
        }

        return normalizePath;
    }

}
