package com.anarion.service.netdisk;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;

@Service
public class NetFile implements NetEntry {
    private String dir = null;
    private FileChannel fileChannel = null;
    private MappedByteBuffer buffer;
    private String name;
    private long date;

    @Override
    public long getDate() {
        return date;
    }

    @Override
    public void setDate(long date) {
        this.date = date;
    }

    private void openChannel() throws IOException {
        Path path = FileSystems.getDefault().getPath(dir);
        try {
            fileChannel = FileChannel.open(path, StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        name = dir.substring(dir.lastIndexOf('/') + 1);
        buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
    }

    @Override
    public long fileSize() throws IOException {
        return fileChannel.size();
    }

    public NetFile() {
    }

    @Override
    public void setDir(String dir) throws IOException {
        assert dir != null;
        this.dir = dir;
        try {
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openChannel();
    }

    @Override
    public String getDir() {
        return dir;
    }

    public NetFile(String dir) throws IOException {
        this.dir = dir;
        openChannel();
    }

    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public NetEntry getChild(String name) {
        return null;
    }

    @Override
    public Set<NetDirInfo> getChildSet() {
        return null;
    }
}
