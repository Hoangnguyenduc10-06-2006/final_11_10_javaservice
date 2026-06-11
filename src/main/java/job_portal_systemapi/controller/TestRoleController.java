package job_portal_systemapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestRoleController {

    @GetMapping("/admin/test")
    public String adminTest() {
        return "ADMIN OK";
    }

    @GetMapping("/employer/test")
    public String employerTest() {
        return "EMPLOYER OK";
    }

    @GetMapping("/candidate/test")
    public String candidateTest() {
        return "CANDIDATE OK";
    }
}
