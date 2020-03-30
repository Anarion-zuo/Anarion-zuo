package com.anarion.controller;

import com.anarion.service.netdisk.NetDirInfo;
import com.anarion.service.netdisk.NetDirectory;
import com.anarion.service.netdisk.NetEntry;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;


class DirInfoMap {
    private String dir;

    public DirInfoMap() {
    }

    public DirInfoMap(String dir) {
        this.dir = dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return dir;
    }
}

@Controller
@RequestMapping(value = "/netdisk")
public class NetDiskController {

    static String mappedPath = "/home/anarion/Downloads/netdisk/";

    NetDirectory netDirectory = new NetDirectory(mappedPath);

    public NetDiskController() throws IOException {
    }

    @RequestMapping("/")
    public String index() {
        return "netdisk.html";
    }

    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public @ResponseBody Set<NetDirInfo> upload(@RequestParam("file") MultipartFile file, @RequestParam("dir") String dir) throws IOException {
//        MultipartFile file = files[0];
        boolean result = false;
        String realPath;
        String uploadName = file.getOriginalFilename();
        realPath = mappedPath + uploadName;
        file.transferTo(new File(realPath));
        result = true;
        return NetDirInfo.getDirInfo(netDirectory, dir);
    }

    @ResponseBody
    @RequestMapping(value = "/getDirInfo", method = RequestMethod.GET)
    public Set<NetDirInfo> getDirInfoByJson(@RequestParam("dir") String dir) throws IOException {
        return NetDirInfo.getDirInfo(netDirectory, dir);
    }

    @ResponseBody
    @RequestMapping(value = "/getDirInfoByParam", method = RequestMethod.GET)
    public Set<NetDirInfo> getDirInfoByParam(@RequestParam("dir") String dir) {
        return NetDirInfo.getDirInfo(netDirectory, dir);
    }
}
