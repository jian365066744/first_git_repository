package com.ronglian.qd_qrcode.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

public class CustomDateConverter implements Converter<String, Date> {
	private Logger logger = LoggerFactory.getLogger(getClass());
 
    @Override
    public Date convert(String source) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        	if(StringUtils.isNotBlank(source)) {
        		return simpleDateFormat.parse(source);
        	}
        } catch (ParseException e) {
        	logger.error("转换日期类型出错", e);
        }
        return null;
    }
 
}