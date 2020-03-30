package com.anarion.service.netdisk;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class NetDirInfo {
    private boolean isDir;
    private long fileSize;
    private String name;
    private long dateVal;
    private boolean isValid;

    public NetDirInfo(NetEntry entry) throws IOException {
        if (entry == null) {
            this.isValid = false;
            return;
        }
        this.name = entry.getName();
        this.isDir = !entry.isFile();
        this.fileSize = entry.fileSize();
        this.dateVal = entry.getDate();
        this.isValid = true;
    }

    static public Set<NetDirInfo> getDirInfo(NetEntry netDirectory, String dir) {
        String[] names = dir.split("/");
        NetEntry entry = netDirectory;
        ModelAndView modelAndView = new ModelAndView();
        for (int i = 1; i < names.length; ++i) {
            entry = entry.getChild(names[i]);
            if (entry.isFile()) {
                return new HashSet<>();
            }
            if (entry == null) {
                return new HashSet<>();
            }
        }
        return entry.getChildSet();
    }

    public void inValid() { isValid = false; }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateVal() {
        return dateVal;
    }

    public void setDateVal(long dateVal) {
        this.dateVal = dateVal;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
