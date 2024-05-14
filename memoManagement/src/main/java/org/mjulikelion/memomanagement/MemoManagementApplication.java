package org.mjulikelion.memomanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing// JPA Auditing 활성화
public class MemoManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemoManagementApplication.class, args);
    }

}
