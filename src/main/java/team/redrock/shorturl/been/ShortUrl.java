package team.redrock.shorturl.been;

import lombok.Data;

/**
 * @Description
 * @Author 余歌
 * @Date 2018/8/28
 **/
@Data
public class ShortUrl {
    private String originUrl;
    private String shortCode;
    private int count;

    public ShortUrl(){

    }

    public ShortUrl(String originUrl, String shortCode){
        this.originUrl=originUrl;
        this.shortCode=shortCode;
        this.count=0;
    }

    public ShortUrl(String originUrl, String shortCode,int count){
        this.shortCode=shortCode;
        this.originUrl=originUrl;
        this.count=count+1;
    }
}
