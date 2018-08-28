package team.redrock.shorturl.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import team.redrock.shorturl.been.ShortUrl;

/**
 * @Description
 * @Author 余歌
 * @Date 2018/8/28
 **/
@Mapper
@Component
public interface UrlMapper {

    @Select("SELECT * FROM url WHERE short_code = #{code} AND count=#{count}")
    ShortUrl findCode(@Param("code") String code, @Param("count") int count);

    @Select("SELECT * FROM url WHERE origin_url=#{originUrl}")
    ShortUrl findOriginUrl(String originUrl);

    @Select("SELECT count FROM url WHERE short_code=#{code} ORDER BY count DESC limit 1")
    Integer findCount(String code);

    @Insert("INSERT INTO url(short_code,origin_url,count) VALUE(#{shortCode},#{originUrl},#{count})")
    void insert(ShortUrl shortUrl);
}
