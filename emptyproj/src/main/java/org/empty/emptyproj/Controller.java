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
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @PostConstruct
    public void initFlowRules() {
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("manualResource");
        flowRule.setCount(16); // 每秒最多2次访问
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_WARM_UP);
        flowRules.add(flowRule);
        FlowRuleManager.loadRules(flowRules);

        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("manualResource");  // 指定资源名称
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);  // 设置降级策略，这里使用RT（平均响应时间）
        degradeRule.setCount(100);  // 设置阈值，例如100ms的平均响应时间
        degradeRule.setTimeWindow(10);  // 设置降级后的恢复时间窗口，单位为秒
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);

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
