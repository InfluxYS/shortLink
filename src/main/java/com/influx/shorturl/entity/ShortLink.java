package com.influx.shorturl.entity;

import com.influx.shorturl.util.SHA256;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity(name = "short_link")
@Getter
@NoArgsConstructor
@ToString
public class ShortLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number")
    private Integer number;

    @Column(name = "full_url")
    private String fullPath;

    @Column(name = "request_count")
    private Integer requestCount;

    @Column(name = "sha256")
    private String sha256;


    public void increaseRequestCount(){
        this.requestCount +=1;
    }


    private ShortLink(String fullPath){
        this.fullPath = fullPath;
        this.requestCount = 0;
        this.sha256 = SHA256.hash(fullPath);
    }

    public static ShortLink create(String fullPath){
        return new ShortLink(fullPath);
    }

}
