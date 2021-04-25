package com.influx.shorturl.repository;

import com.influx.shorturl.entity.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ShortLinkRepository extends JpaRepository<ShortLink,String> {



    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<ShortLink> findShortLinkByNumber(int number);

    public Optional<ShortLink> findShortLinkBySha256(String fullPath);
}
