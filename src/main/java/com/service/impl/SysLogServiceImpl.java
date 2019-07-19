package com.service.impl;

import com.entiy.SysLogEntity;
import com.service.SysLogService;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Override
    public void save(SysLogEntity log) {
        System.out.println(log.toString());
    }
}
