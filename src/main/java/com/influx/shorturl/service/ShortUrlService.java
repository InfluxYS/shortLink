package com.influx.shorturl.service;

import com.influx.shorturl.dto.ShortUrl;
import com.influx.shorturl.entity.ShortLink;
import com.influx.shorturl.repository.ShortLinkRepository;
import com.influx.shorturl.util.Base62;
import com.influx.shorturl.util.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    public final String NOT_FOUNT_PAGE = "http://google.com";
    public final String SERVER_DOMAIN = "http://localhost";

    private  final ShortLinkRepository shortLinkRepository;


    @Transactional
    public Optional<ShortUrl> findFullUrl(String path){
         Optional<ShortLink> shortLink = shortLinkRepository.findShortLinkByNumber(Base62.decoding(path));
         shortLink.ifPresent(ShortLink::increaseRequestCount);

         return shortLink.map(sl-> ShortUrl.create(sl,SERVER_DOMAIN));
    }


    public ShortUrl makeShortUrl(String fullUrl){
    ShortLink shortLink = shortLinkRepository
                            .findShortLinkBySha256(SHA256.hash(fullUrl))
                            .orElseGet(()-> shortLinkRepository.save(ShortLink.create(fullUrl)));
        return ShortUrl.create(shortLink, SERVER_DOMAIN);
    }


    public Optional<ShortUrl> findShortUrl(String fullUrl){
        return shortLinkRepository.findShortLinkBySha256(SHA256.hash(fullUrl)).map(sl-> ShortUrl.create(sl,SERVER_DOMAIN));
    }


}
