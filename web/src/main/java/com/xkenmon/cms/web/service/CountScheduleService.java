package com.xkenmon.cms.web.service;

import com.xkenmon.cms.dao.entity.Count;
import com.xkenmon.cms.dao.mapper.CountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CountScheduleService {
    private final static Logger logger = LoggerFactory.getLogger(CountScheduleService.class);

    private final static long ONE_MINUTE = 60 * 1000;
    private final static long countInterval = ONE_MINUTE * 10;

    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, AtomicInteger>> PVCount = new ConcurrentHashMap<>();

    private final
    CountMapper countEntryMapper;

    @Autowired
    public CountScheduleService(CountMapper countEntryMapper) {
        this.countEntryMapper = countEntryMapper;
    }

   @Scheduled(fixedDelay = countInterval)
    public void count() {
        logger.info("flushing count info to DB...");
        PVCount.forEach((type, map)
                -> map.forEach((id, count)
                -> flushCountToDB(id, count, type))
        );
        PVCount.clear();
        logger.info("flush count info succeed!");
    }

    public int addPV(String type, String id) {
        if (!PVCount.containsKey(type)) {
            PVCount.put(type, new ConcurrentHashMap<>());
        }
        ConcurrentHashMap<String, AtomicInteger> map = PVCount.get(type);
        if (!map.containsKey(id)) {
            map.put(id, new AtomicInteger());
        }
        return map.get(id).incrementAndGet();
    }

    private void flushCountToDB(String id, AtomicInteger count, String type) {
        Count entry = new Count();
        entry.setCountType(type);
        entry.setCountCid(id);
        entry.setCountTime(Calendar.getInstance().getTimeInMillis());
        entry.setCountPv(count.get());
        entry.setCountInterval((int) countInterval);
        countEntryMapper.insert(entry);
        logger.debug("flush count entry:{}", entry);
    }
}
