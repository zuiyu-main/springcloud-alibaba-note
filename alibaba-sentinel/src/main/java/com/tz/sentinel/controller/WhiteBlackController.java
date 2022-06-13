//package com.tz.sentinel.controller;
//
//import com.alibaba.csp.sentinel.annotation.SentinelResource;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import com.alibaba.csp.sentinel.slots.block.RuleConstant;
//import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
//import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.PostConstruct;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * @author @醉鱼
// * @link https://github.com/TianPuJun
// * @ClassName TestController
// * @Description 授权控制测试
// * @Date 19:30 2022/6/12
// **/
//@RestController
//public class WhiteBlackController {
//
//    @SentinelResource(value = "Sentinel_Rule",blockHandler = "exceptionHandler")
//    @GetMapping("/rule")
//    public String hello(){
//        return "hello sentinel rule";
//    }
//    public String exceptionHandler(BlockException e){
//        e.printStackTrace();
//        return "系统繁忙，请稍后";
//    }
//
//    @PostConstruct
//    public void initWhiteRules(){
//       List<AuthorityRule> rules = new LinkedList<>();
//       AuthorityRule rule = new AuthorityRule();
//       rule.setResource("Sentinel_Rule");
//       rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
//       rule.setLimitApp("192.168.168.168");
//       rules.add(rule);
//        AuthorityRuleManager.loadRules(rules);
//    }
//
//    @PostConstruct
//    public void initBlackRules(){
//        List<AuthorityRule> rules = new LinkedList<>();
//        AuthorityRule rule = new AuthorityRule();
//        rule.setResource("Sentinel_Rule");
//        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
//        rule.setLimitApp("127.0.0.1");
//        rules.add(rule);
//        AuthorityRuleManager.loadRules(rules);
//    }
//
//}
