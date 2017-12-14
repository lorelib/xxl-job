# 打包命令：
    mvn clean package
    
# 启动spring boot应用
    java -jar **.jar

# 打成WAR包，使部署到web容器
   
   1. 修改pom.xml文件
       
       > 将packaging配置jar改成war
       
       > 添加
       
           `<dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-tomcat</artifactId>
               <scope>provided</scope>
           </dependency>`
           
         避免内嵌的容器干扰需要部署的容器，需设置scope为provided
         
   2. @SpringBootApplication启动类继承SpringBootServletInitializer，并且覆盖configure方法
   
   3. 添加相应的web.xml文件。