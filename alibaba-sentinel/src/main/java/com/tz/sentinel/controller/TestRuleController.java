//package com.tz.sentinel.controller;
//
//import com.alibaba.csp.sentinel.annotation.SentinelResource;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import com.alibaba.csp.sentinel.slots.block.RuleConstant;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
//import com.tz.sentinel.agent.FeignAgent;
//import org.springframework.beans.factory.annotation.Autowired;
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
// * @Description 熔断测试
// * @Date 19:30 2022/6/12
// **/
//@RestController
//public class TestRuleController {
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
////    @PostConstruct
////    public void initDegradeRule(){
////        List<DegradeRule> rules = new LinkedList<>();
////        DegradeRule rule = new DegradeRule();
////        // 定义资源名称
////        rule.setResource("Sentinel_Rule");
////        // 定义规则类型 秒级RT，平均响应时间类型
////        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
////        // 定义阈值
////        rule.setCount(0.01);
////        // 定义降级时间
////        rule.setTimeWindow(10);
////        rules.add(rule);
////        // 加载规则
////        DegradeRuleManager.loadRules(rules);
////    }
//
//
//}
