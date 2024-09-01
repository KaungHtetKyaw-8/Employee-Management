package com.manage.employee.Controller;

import com.manage.employee.Services.GoogleOauth2Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/google/auth")
public class GoogleController {
    GoogleOauth2Services googleEmailVerification;

    @Autowired
    public GoogleController(GoogleOauth2Services googleEmailVerification) {
        this.googleEmailVerification = googleEmailVerification;
    }

    @GetMapping("/{userId}")
    public String authO(@PathVariable String userId) throws Exception {
        String result = "/error";

        if (googleEmailVerification.isStoredCredential(userId)){
            System.out.println("Mail Checking...");
        }else {
            result = "redirect:" + googleEmailVerification.getAuthorizeUrl(userId);
        }

        return result;
    }

    @GetMapping("/Callback")
    public String authCallBack(
            @PathVariable String userId,
            @RequestParam(required = false) String code)
            throws IOException {
        System.out.println("Called Code : " + code);
        googleEmailVerification.credentialBuiltAndStore(code,userId);

        return "/userId";
    }
}
