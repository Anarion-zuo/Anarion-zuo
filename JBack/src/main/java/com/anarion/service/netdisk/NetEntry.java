package com.anarion.service.netdisk;

import java.io.IOException;
import java.util.Set;

public interface NetEntry {
    boolean isFile();
    String getDir();
    void setDir(String dir) throws IOException;
    long fileSize() throws IOException;
    String getName();
    NetEntry getChild(String name);
    Set<NetDirInfo> getChildSet();

    void setDate(long date);
    long getDate();
}
