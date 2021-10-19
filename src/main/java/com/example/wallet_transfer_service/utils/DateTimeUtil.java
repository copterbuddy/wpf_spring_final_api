package com.example.wallet_transfer_service.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import com.google.protobuf.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class DateTimeUtil {

    public Timestamp GetTimeStamp(){

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        Instant time = localDateTime.toInstant(ZoneOffset.UTC);
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(time.getEpochSecond()).setNanos(time.getNano())
                .build();
                
        return timestamp;
    }

    
}
