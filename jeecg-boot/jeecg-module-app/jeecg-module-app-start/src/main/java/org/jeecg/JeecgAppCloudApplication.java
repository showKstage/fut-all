package org.jeecg;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@EnableFeignClients(basePackages = {"org.jeecg"})
public class JeecgAppCloudApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(JeecgAppCloudApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = oConvertUtils.getString(env.getProperty("server.servlet.context-path"));
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Jeecg-Cloud-App is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/doc.html\n" +
                "External: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
