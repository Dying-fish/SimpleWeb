package com.example.controller;

import com.example.service.FileService;
import com.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
@Validated
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController( FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public HashMap illustrationUpload(@RequestHeader String token,
                                      @NotNull(message = "缺少插画") MultipartFile file,
                                      @Min(value = 1, message = "pid必须为正整数") Integer pid,
                                      @NotBlank(message = "缺少画师")@RequestParam String illustrator,
                                      @NotBlank(message = "缺少标题") @RequestParam String title,
                                      @NotNull @RequestParam String description) {
        if (JwtUtils.isAdmin(token)) {
            return fileService.illustrationUpload(file, pid, illustrator, title, description);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("code", -1);
        return hashMap;
    }

    @GetMapping("/showAllPic")
    public ArrayList showAllPic() {
        return fileService.showAllPic();
    }
}
