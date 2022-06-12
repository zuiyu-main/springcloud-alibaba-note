package com.tz.sentinel.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName TestController
 * @Description 自适应保护测试
 * @Date 19:30 2022/6/12
 **/
@RestController
public class TestLoadController {

    @SentinelResource(entryType = EntryType.IN)
    @GetMapping("/rule")
    public String hello(){
        return "hello sentinel rule";
    }


//    @PostConstruct
//    public void initDegradeRule(){
//        List<SystemRule> rules = new LinkedList<>();
//       SystemRule rule = new SystemRule();
//       rule.setQps(2);
//       rules.add(rule);
//        SystemRuleManager.loadRules(rules);
//    }


}
