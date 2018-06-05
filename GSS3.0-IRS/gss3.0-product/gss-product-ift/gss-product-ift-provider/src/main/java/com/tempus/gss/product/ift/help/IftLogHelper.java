package com.tempus.gss.product.ift.help;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.tempus.gss.log.entity.LogRecord;
import com.tempus.gss.log.service.ILogService;
import com.tempus.gss.system.entity.User;
import com.tempus.gss.system.service.IUserService;
import com.tempus.gss.vo.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(value = "ift.IftLogHelper")
public class IftLogHelper {
    @Reference
    protected static ILogService logService;
    @Reference
    protected static IUserService userService;

    /** 默认APP名称 */
    protected static final String APPCODE = "UBP";

    /** 目前DPS订单日志统称DPS */
    protected static final String BIZCODE = "IFT";

    /** 日志记录器 */
    protected static final  Logger logger = LoggerFactory.getLogger(IftLogHelper.class);

    @PostConstruct
    public void initialize() {
        logger.info(this.getClass().getName() + " init.");

        if (null == logService) {
            throw new RuntimeException("LogService invalid !!!");
        }
    }
    /**
     * 记录操作日志
     *
     * @param agent 终端信息
     * @param no saleOrderNo
     * @param title 操作标题
     * @param desc 操作内容
     */
    public static void logger(Agent agent, Long no, String title, String desc) {
        User user = null;
        try{
            user = userService.findUserByLoginName(agent, agent.getAccount());
        }catch(Exception e){
            logger.info(no + "记录操作日志取用户信息异常" + e);
        }
        LogRecord log = new LogRecord();
        log.setId(IdWorker.getId());
        log.setAppCode(APPCODE);
        log.setBizCode(BIZCODE);
        log.setBizNo(String.valueOf(no));
        log.setTitle(title);
        log.setDesc(desc);
        log.setCreateTime(new Date());
        log.setOptLoginName(agent.getAccount());
        log.setRequestIp(agent.getIp());
        try {
            logService.insert(log);
        } catch (Exception e) {
            logger.info(no + "记录操作日志异常");
        }
    }

    public static void logger(Agent agent, Long tradeNo, Long no, String title, String desc) {
        LogRecord log = new LogRecord();
        log.setId(IdWorker.getId());
        log.setAppCode(APPCODE);
        log.setBizCode(BIZCODE);
        log.setBizNo(String.valueOf(no));
        log.setTitle(title);
        log.setDesc(desc);
        log.setCreateTime(new Date());
        log.setOptLoginName(agent.getAccount());
        log.setRequestIp(agent.getIp());
        Map<String, Object> otherOpts = new HashMap<String, Object>();
        otherOpts.put("transationOrderNo", tradeNo);
        log.setOtherOpts(otherOpts);
        try {
            logService.insert(log);
        } catch (Exception e) {
            logger.info(no + "记录操作日志异常");
        }
    }

}


