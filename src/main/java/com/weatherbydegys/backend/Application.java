package com.weatherbydegys.backend;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
public class Application implements ApplicationRunner {
//    @Autowired
//    FavCityRepo favCityRepo;


    public static void main(String[] args){
        SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot: ");


//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName: beanNames){
//            System.out.println(beanName);
//        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("START");
//        favCityRepo.saveAll(ReadJSONCityList.readAndWriteToJDBC());
//        System.out.println("END");

    }
}
