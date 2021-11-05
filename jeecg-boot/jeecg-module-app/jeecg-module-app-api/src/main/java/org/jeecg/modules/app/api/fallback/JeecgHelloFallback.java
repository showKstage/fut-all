package org.jeecg.modules.app.api.fallback;

import feign.hystrix.FallbackFactory;
import org.jeecg.modules.app.api.JeecgHelloApi;
import org.springframework.stereotype.Component;

/**
 * @author zyf
 */
@Component
public class JeecgHelloFallback implements FallbackFactory<JeecgHelloApi> {

    @Override
    public JeecgHelloApi create(Throwable throwable) {
        return null;
    }
}
