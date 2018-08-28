package team.redrock.shorturl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team.redrock.shorturl.been.ResponseEntity;
import team.redrock.shorturl.been.ShortUrl;
import team.redrock.shorturl.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @Description
 * @Author 余歌
 * @Date 2018/8/28
 **/
@Controller
public class AdminController {

    private String serverUrl="http://localhost:8080/";

    @Autowired
    private AdminService adminService;

    private ResponseEntity PARAM_ERROR = new ResponseEntity(403, "param error");

    @GetMapping("/create")
    @ResponseBody
    public ResponseEntity getShortUrl(String url,HttpServletRequest request) throws NoSuchAlgorithmException {
        System.out.println(request.getRequestURL());

        if (url != null) {
            if(!url.matches("^(https?)://.*?")){
                url="http://"+url;
            }
            ShortUrl shortUrl = adminService.creatShortUrl(url);
            return new ResponseEntity(200,"ok",toUrl(shortUrl));
        }
        return PARAM_ERROR;
    }

    @GetMapping("/u/{url}")
    @ResponseBody
    public ResponseEntity redirect(@PathVariable("url") String code,HttpServletResponse response) throws IOException {
        String url = adminService.findOriginUrl(code);
        if(url!=null){
            response.sendRedirect(url);
        }

        return PARAM_ERROR;
    }

    public String toUrl(ShortUrl shortUrl) {
        if (shortUrl != null) {
            StringBuilder sb = new StringBuilder(serverUrl);
            sb.append(shortUrl.getShortCode());
            if (shortUrl.getCount() != 0) {
                sb.append(shortUrl.getCount());
            }
            return sb.toString();
        }
        return null;
    }

}
