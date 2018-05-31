package cn.hfbin.lzzmjx.utils;

import cn.hfbin.lzzmjx.service.EquimentService;
import cn.hfbin.lzzmjx.service.impl.EquimentServiceipml;
import cn.hfbin.lzzmjx.socket.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

/**
 * Created by: HuangFuBin
 * Date: 2018/5/27
 * Time: 15:50
 * Such description:
 */

@Slf4j
public class DataAnalysisUtils {


    public byte[] analysis(String s) {
        String[] split = s.split(" ");


        if (StringUtils.equalsIgnoreCase("AA", split[0])) {
            Integer s0 = Integer.valueOf(split[0], 16);
            Integer s1 = Integer.valueOf(split[1], 16);
            Integer s2 = Integer.valueOf(split[2], 16);
            Integer s3 = Integer.valueOf(split[3], 16);
            Integer s4 = Integer.valueOf(split[4], 16);
            Integer s5 = Integer.valueOf(split[5], 16);
            Integer s6 = Integer.valueOf(split[6], 16);
            Integer s7 = Integer.valueOf(split[7], 16);
            Integer s8 = Integer.valueOf(split[8], 16);

            String equimentId = s2 + "" + s3 + "" + s4 + "" + s5;

            String code = s6 + "" + s7;

            Integer sum = s0 + s1 + s2 + s3 + s4 + s5 + s6 + s7 ;

            log.info("心跳包指令头:" + s0);

            log.info("数据包长度:" + s1);

            log.info("设备号:" + equimentId);

            log.info("确认码:" + code);

            log.info("校验和:" + s8);

            log.info("求和数:" + sum);


            byte[] bytes = {s0.byteValue(), s1.byteValue(), s2.byteValue(), s3.byteValue(), s4.byteValue(),
                    s5.byteValue(), (byte) 160, (byte) 160, sum.byteValue()};
            return bytes;
        }
        if (StringUtils.equals("AB", split[0])) {
            Integer s0 = Integer.valueOf(split[0], 16);
            Integer s1 = Integer.valueOf(split[1], 16);
            Integer s2 = Integer.valueOf(split[2], 16);
            Integer s3 = Integer.valueOf(split[3], 16);
            Integer s4 = Integer.valueOf(split[4], 16);
            Integer s5 = Integer.valueOf(split[5], 16);
            Integer s6 = Integer.valueOf(split[6], 16);
            Integer s7 = Integer.valueOf(split[7], 16);
            Integer s8 = Integer.valueOf(split[8], 16);
            Integer s9 = Integer.valueOf(split[9], 16);
            Integer s10 = Integer.valueOf(split[10], 16);
            Integer s11 = Integer.valueOf(split[11], 16);
            Integer s12 = Integer.valueOf(split[12], 16);

            String equimentId = s2 + "" + s3 + "" + s4 + "" + s5;

            String co = s6 + "" + s7;

            String o2 = s8 + "" + s9;

            String code = s10 + "" + s11;

            Integer sum = s0 + s1 + s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9 + s10 + s11;

            //校验和取低八位
            sum = sum & 0X00ff;



            //接收到的数据是正确的
            if((int)sum == (int) s12){

                log.info("心跳包指令头:" + s0);

                log.info("数据包长度:" + s1);

                log.info("设备号:" + equimentId);

                log.info("CO浓度值:" + co);

                log.info("O2浓度值:" + o2);

                log.info("确认码:" + code);

                log.info("校验和:" + s12);

                log.info("求和数:" + sum);

                EquimentService equimentService = Server.getEquimentService();
                equimentService.updateConcentrationByPrimaryKey(new BigDecimal(Integer.parseInt(co)), equimentId);

                s10 = 161;
                s11 = 161;

                Integer sum1 = s0 + s1 + s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9 + s10 + s11;

                sum1 = sum1 & 0X00ff;

                byte[] bytes = {s0.byteValue(), s1.byteValue(), s2.byteValue(), s3.byteValue(), s4.byteValue(),
                        s5.byteValue(), s6.byteValue(), s7.byteValue(), s8.byteValue(), s9.byteValue(), s10.byteValue(), s11.byteValue(), sum1.byteValue()};
                return bytes;
            }

        }

        return null;
    }



}
