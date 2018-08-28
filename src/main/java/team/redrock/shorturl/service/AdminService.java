package team.redrock.shorturl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import team.redrock.shorturl.been.ShortUrl;
import team.redrock.shorturl.mapper.UrlMapper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description
 * @Author 余歌
 * @Date 2018/8/28
 **/
@Service
@Component
public class AdminService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UrlMapper urlMapper;

    private MessageDigest md = null;

    public AdminService() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD5");
    }

    public ShortUrl creatShortUrl(String text) {
        ShortUrl shortUrl = urlMapper.findOriginUrl(text);

        if (shortUrl == null) {
            md.update(text.getBytes());
            String code = new BigInteger(1, md.digest()).toString(16).substring(0, 4);

            Integer count = urlMapper.findCount(code);

            if (count != null) {
                shortUrl = new ShortUrl(text, code, count + 1);
            } else {
                shortUrl = new ShortUrl(text, code);
            }
            urlMapper.insert(shortUrl);
        }
        return shortUrl;
    }

    public String findOriginUrl(String code) {
        int count = 0;
        if (code.length() > 4) {
            try {
                count = Integer.parseInt(code.substring(0, 4));
            } catch (Throwable e) {
                e.printStackTrace();
            }
            code = code.substring(4);
        }
        ShortUrl shortUrl = urlMapper.findCode(code, count);
        return shortUrl.getOriginUrl();
    }



}
