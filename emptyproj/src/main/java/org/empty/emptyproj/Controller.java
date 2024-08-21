package org.empty.emptyproj;

/**
 * @author huangqiang
 * @version TODO
 * @date 2024/8/19 下午5:13
 * @description TODO
 * @modified
 */


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @PostConstruct
    public void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("manualResource");
        rule.setCount(16); // 每秒最多2次访问
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @GetMapping("/time")
    public String manualHello() {
        Entry entry = null;
        try {
            entry = SphU.entry("manualResource");
            // 被保护的业务逻辑
            return String.valueOf(System.currentTimeMillis());
        } catch (BlockException ex) {
            // 限流后的处理逻辑
            return "QPS limit exceeded, please try again later.";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }
}
