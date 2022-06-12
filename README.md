# Nacos 配置

##  快速开始

[快速开始官网](https://nacos.io/zh-cn/docs/quick-start.html)

* 启动 standalone代表单机模式运行，非集群

  ```text
  sh startup.sh -m standalone 
  ```
  
* 服务注册
  ```text
curl -X POST 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
  ```
  
* 服务发现

  ```text
  curl -X GET 'http://127.0.0.1:8848/nacos/v1/ns/instance/list?serviceName=nacos.naming.serviceName'
  ```

* 发布配置

  ```text
  curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=HelloWorld"
  ```

* 获取配置

  ```text
  curl -X GET "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test"
  ```
* 关闭服务
  ```text
  sh shutdown.sh
  ```
* nacos 管理页面
  账号密码默认 nacos
  
  ```text
  http://localhost:8848/nacos/#/login
  ```
## springboot nacos

### 本文介绍

* 父pom.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.tz</groupId>
      <artifactId>basenote</artifactId>
      <version>1.0-SNAPSHOT</version>
      <parent>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-parent</artifactId>
          <version>2.0.5.RELEASE</version>
          <relativePath/> <!-- lookup parent from repository -->
      </parent>
      <dependencyManagement>
          <dependencies>
              <dependency>
                  <groupId>org.springframework.cloud</groupId>
                  <artifactId>spring-cloud-dependencies</artifactId>
                  <version>Finchley.SR1</version>
                  <type>pom</type>
                  <scope>import</scope>
              </dependency>
              <dependency>
                  <groupId>org.springframework.cloud</groupId>
                  <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                  <version>0.2.2.RELEASE</version>
                  <type>pom</type>
                  <scope>import</scope>
              </dependency>
          </dependencies>
      </dependencyManagement>
  </project>
  ```

* [GitHub 本文示例demo](https://github.com/TianPuJun/springcloud-alibaba-note)

### 笔记说明

* 版本说明

  [alibabba cloud 版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/版本说明)

* dataId
  * `prefix` 默认为 `spring.application.name` 的值，也可以通过配置项 `spring.cloud.nacos.config.prefix`来配置。
  * `spring.profiles.active` 即为当前环境对应的 profile，详情可以参考 [Spring Boot文档](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html#boot-features-profiles)。 **注意：当 `spring.profiles.active` 为空时，对应的连接符 `-` 也将不存在，dataId 的拼接格式变成 `${prefix}.${file-extension}`**
  * `file-exetension` 为配置内容的数据格式，可以通过配置项 `spring.cloud.nacos.config.file-extension` 来配置。目前只支持 `properties` 和 `yaml` 类型。


* **Config server 需要把application.properties 改为bootstrap.properties**

### Alibaba-Config-Server

* pom.xml

  ```xml
  
  		<dependency>
  			<groupId>org.springframework.cloud</groupId>
  			<artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
  		</dependency>
  
  ```

* 更新配置文件名**Config server 需要把application.properties 改为bootstrap.properties**，内容如下

  ```properties
  server.port=9000
  spring.cloud.nacos.config.server-addr=127.0.0.1:8848
  spring.cloud.nacos.config.group=app1
  # dataId ${prefix}-${spring.profiles.active}.${file-extension}
  spring.application.name=alibaba-nacos-config
  ```

* 创建测试动态更新配置的类

  ```java
  @RestController
  @RefreshScope
  @RequestMapping("/config")
  public class ConfigController {
  
      @Value("${useLocalCache:false}")
      private boolean useLocalCache;
  
      @GetMapping("/get")
      public boolean get(){
          return useLocalCache;
      }
  }
  ```

  到这配置服务就完成，下面我们使用发布配置的方法查看一下配置是否可以动态更新

* 发布新配置，注意发布配置的所属组，默认DEFAULT_GROUP,我们上面指定了group 为app1，所以此时发布配置也要往app1里面发

  此时的dataId可以参考上面dataId部分的介绍，所以我们此处就要使用我们设置的spring.application.name加上`.properties`

  ```text
  curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=alibaba-nacos-config.properties&group=app1&content=useLocalCache=true"
  ```

* 获取配置

  ```text
  // 执行完上面发布配置后此时获取返回true
  http://127.0.0.1:9000/config/get
  // 再次执行，唯一的不同就是最后useLocalCache的值
  curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=alibaba-nacos-config.properties&group=app1&content=useLocalCache=false"
  // 再次获取返回false
  ```

### Alibaba-Provider-Server

* pom.xml

  ```xml
  		<dependency>
  			<groupId>org.springframework.cloud</groupId>
  			<artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  		</dependency>
  ```

* 启动类添加服务发现注解

  ```java
  @EnableDiscoveryClient
  @SpringBootApplication
  public class ServerProviderApplication {
  
  	public static void main(String[] args) {
  		SpringApplication.run(ServerProviderApplication.class, args);
  	}
  	@Slf4j
  	@RestController
  	static class TestController {
  		@GetMapping("/hello/{str}")
  		public String hello(@PathVariable String str) {
  			log.info("invoked name = " + str);
  			return "hello " + str;
  		}
  	}
  
  }
  ```

* application.properties
  ```properties
  server.port=8001
  spring.application.name=alibaba-nacos-discovery-server
  spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
  ```
### Alibaba-Consumer-Server

* pom.xml

  ```xml
  		<dependency>
  			<groupId>org.springframework.cloud</groupId>
  			<artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  		</dependency>
  ```

* 启动类添加

  ```java
  @EnableDiscoveryClient
  @SpringBootApplication
  public class ServerConsumerApplication {
      
      public static void main(String[] args) {
          SpringApplication.run(ServerConsumerApplication.class, args);
      }
  
      @Slf4j
      @RestController
      static class TestController {
  
          @Autowired
          LoadBalancerClient loadBalancerClient;
  
          @GetMapping("/test/{str}")
          public String test(@PathVariable String str) {
              // 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
              ServiceInstance serviceInstance = loadBalancerClient.choose("alibaba-nacos-discovery-server");
              String url = serviceInstance.getUri() + "/hello/" + str;
              RestTemplate restTemplate = new RestTemplate();
              String result = restTemplate.getForObject(url, String.class);
              return "Invoke : " + url + ", return : " + result;
          }
      }
  
      @RestController
      public class HelloController {
  
          private final RestTemplate restTemplate;
  
          @Autowired
          public HelloController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}
  
          @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
          public String echo(@PathVariable String str) {
              return restTemplate.getForObject("http://alibaba-nacos-discovery-server/hello/" + str, String.class);
          }
      }
  }
  ```

* application.properties

  ```properties
  server.port=9001
  spring.application.name=alibaba-nacos-discovery-client-common
  spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
  ```


* ServerConfig

  ```java
  @Configuration
  public class ServerConfig {
      @LoadBalanced
      @Bean
      public RestTemplate restTemplate() {
          return new RestTemplate();
      }
  }
  ```

### 效果演示

* 访问 http://127.0.0.1:8001/hello/test 返回 hello test 

* 访问 http://127.0.0.1:9001/test/tz 返回本地的ip

  ```text
  Invoke : http://192.168.31.38:8001/hello/tz, return : hello tz
  ```

* 访问 http://127.0.0.1:9001/echo/tz 返回 hello tz
