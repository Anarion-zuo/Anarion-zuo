package com.anarion.service.netdisk;

import org.springframework.stereotype.Service;

import javax.naming.directory.BasicAttributes;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@Service
public class NetDirectory implements NetEntry {
    private Map<String, NetEntry> fileName2Files = null;
    private Set<NetDirInfo> fileName2Info = null;
    private String dir = null;
    private String name = null;
    private long fsize = 0;
    private long date;

    private void loadDir() throws IOException {
        fileName2Files = new HashMap<>();
        fsize = 0;
        File thisFile = new File(dir);
        name = thisFile.getName();
        File[] flist = thisFile.listFiles();
        for (File file : flist) {
            if (file.isDirectory()) {
                String dirName = file.getName();
                NetDirectory dirObj = new NetDirectory(file.getCanonicalPath());
                fileName2Files.put(dirName, dirObj);
                fsize += dirObj.fileSize();
            } else if (file.isFile()) {
                String fileName = file.getName();
                NetFile fileObj = new NetFile(file.getCanonicalPath());
                fileName2Files.put(fileName, fileObj);
                fsize += fileObj.fileSize();
            }
            setDate(Files.readAttributes(thisFile.toPath(), BasicFileAttributes.class).creationTime().toMillis());
        }
        fileName2Info = new HashSet<>();
        for (Map.Entry<String, NetEntry> mapEntry : fileName2Files.entrySet()) {
            fileName2Info.add(new NetDirInfo(mapEntry.getValue()));
        }
    }

    public NetDirectory() {}

    public NetDirectory(String dir) throws IOException {
        assert dir != null;
        this.dir = dir;
        loadDir();
    }

    @Override
    public void setDir(String dir) throws IOException {
        assert dir != null;
        this.dir = dir;
        loadDir();
    }

    @Override
    public long fileSize() {
        return fsize;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public String getDir() {
        return dir;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public NetEntry getChild(String name) {
        return fileName2Files.get(name);
    }

    @Override
    public Set<NetDirInfo> getChildSet() {
        return fileName2Info;
    }

    @Override
    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public long getDate() {
        return date;
    }
}
