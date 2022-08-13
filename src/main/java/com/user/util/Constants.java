package com.user.util;

import com.user.dao.LookupRepository;
import com.user.entity.LookupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Constants {
    private Map<String, String> message;

    @Autowired
    LookupRepository lookupRepo;

    @PostConstruct
    public void init() {
        message = new HashMap<>();
        List<LookupEntity> userMessages = lookupRepo.findByType("USER_MESSAGE");
        userMessages.forEach(m -> message.put(m.getName(), m.getValue()));
    }

    public String getMessage(String msgName) {
        return message.get(msgName);
    }
}
