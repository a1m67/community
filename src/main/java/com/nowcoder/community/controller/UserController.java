package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    String uploadPath;
    @Value("${server.servlet.context-path}")
    String contextPath;
    @Value("${community.path.domain}")
    String domain;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片");
            return "/site/setting";
        }

        String fileName = headerImage.getOriginalFilename();
//        从最后一个点之后开始截取
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件格式错误");
            return "/site/setting";
        }
        //生成随机文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        //确定文件存放路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常" + e);
        }

        //更新当前用户头像访问路径
        //http://localhost:8080/community/user/header/XXX.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);
        return "redirect:/index";
    }

    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图片 文件固定格式
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fileInputStream = new FileInputStream(fileName);
                OutputStream outputStream = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, b);

            }

        } catch (IOException e) {
            logger.error("头像获取异常" + e.getMessage());
            throw new RuntimeException("" + e);
        }
    }
    @LoginRequired
    @RequestMapping(path = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(@RequestParam("password") String password, @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmNewPassword, Model model) {

        if (StringUtils.isBlank(password)) {
            model.addAttribute("passwordMsg", "原密码不能为空");
            return "/site/setting";
        }
        if (StringUtils.isBlank(newPassword)) {
            model.addAttribute("newPasswordMsg", "新密码不能为空");
            return "/site/setting";
        }
        if (StringUtils.isBlank(confirmNewPassword)) {
            model.addAttribute("confirmNewPasswordMsg", "再次输入新密码不能为空");
            return "/site/setting";
        }
        User user = hostHolder.getUser();
        if (!user.getPassword().equals(CommunityUtil.md5( (password + user.getSalt()) ))) {
            model.addAttribute("passwordMsg", "请输入正确的原密码");
            return "/site/setting";
        }
        if (password.equals(newPassword)) {
            model.addAttribute("passwordMsg", "新密码不能与旧密码相同");
            return "/site/setting";
        }
        if (!confirmNewPassword.equals(newPassword)) {
            model.addAttribute("newPasswordMsg", "二次输入的密码与新密码不同");
            return "/site/setting";
        }

        userService.updatePassword(user.getId(), newPassword);
        return "redirect:/index";
    }

}
