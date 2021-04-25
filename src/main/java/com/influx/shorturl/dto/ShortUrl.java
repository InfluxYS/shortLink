package com.influx.shorturl.dto;

import com.influx.shorturl.entity.ShortLink;
import com.influx.shorturl.util.Base62;
import lombok.Getter;
import org.thymeleaf.util.StringUtils;

@Getter
public class ShortUrl {

    private final Integer number;
    private final String serverDomain;

    private final String shortUrl;
    private final String fullUrl;
    private final Integer requestCount;

    private ShortUrl(Integer number, String fullUrl, Integer requestCount, String serverDomain) {
        this.number = number;
        this.fullUrl = fullUrl;
        this.requestCount = requestCount;
        this.serverDomain = serverDomain;
        this.shortUrl = this.serverDomain + "/" + Base62.encoding(this.number);
    }

    private ShortUrl(String fullUrl){
        this.number = null;
        this.serverDomain = null;
        this.shortUrl = null;
        this.fullUrl = fullUrl;
        this.requestCount = 0;
    }

    public static ShortUrl create(ShortLink shortLink, String serverDomain ){
        return new ShortUrl(
                shortLink.getNumber(),
                shortLink.getFullPath(),
                shortLink.getRequestCount(),
                serverDomain);
    }
    public static ShortUrl createEmpty(String fullUrl){
        return new ShortUrl(fullUrl);
    }


    public String getFullUrlWithBaseProtocol(){
        if(!StringUtils.contains(this.getFullUrl(),"://")){
            return "http://"+this.getFullUrl();
        }
        return fullUrl;
    }
}
