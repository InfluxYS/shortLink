package com.influx.shorturl.controller;

import com.influx.shorturl.dto.ShortUrl;
import com.influx.shorturl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ShortPathController {

    private  final ShortUrlService shortUrlService;


    @GetMapping(value = "/{path}")
    public void path(@PathVariable("path") String path, HttpServletResponse response) throws IOException {
       ShortUrl shortUrl =  shortUrlService.findFullUrl(path).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        response.sendRedirect(shortUrl.getFullUrlWithBaseProtocol());
    }


    @PostMapping(value ="/result")
    public String make(@ModelAttribute("fullUrl") String fullUrl, Model model ){
        ShortUrl shortUrl = Optional.ofNullable(fullUrl)
                .filter(StringUtils::hasLength)
                .map(shortUrlService::makeShortUrl)
                .orElseGet(()-> ShortUrl.createEmpty(Strings.EMPTY));

            model.addAttribute("shortUrl" , shortUrl);
        return "result";
    }


    @GetMapping(value = "/result")
    public String result(HttpServletRequest request, Model model){
        String queryStr  = Optional.ofNullable(request.getQueryString())
                .filter(query->query.contains("fullUrl="))
                .map(query->query.replaceFirst("fullUrl=",Strings.EMPTY))
                .orElse(Strings.EMPTY);

        ShortUrl shortUrl =  shortUrlService.findShortUrl(queryStr).orElseGet(()-> ShortUrl.createEmpty(queryStr));
        model.addAttribute("shortUrl" ,shortUrl);
        return "result";
    }

    @GetMapping(value = "/index")
    public String index(){
        return "index";
    }
}
