package com.manage.employee.Services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
public class ZeroEmailVerificationService {

    @Value("${app.zero.bounce.key}")
    private String API_KEY;

    private final String API_URL = "https://api.zerobounce.net/v2/validate?api_key=" + API_KEY + "&email=";

    private JsonParser jsonParser;
    private String response;

    public ZeroEmailVerificationService() {
        this.jsonParser = new JsonParser();
    }

//    *** Response Format ***
//    {
//    "address": "XXXX@gmail.com",
//    "status": "valid",
//    "sub_status": "",
//    "free_email": true,
//    "did_you_mean": null,
//    "account": "XXXX",
//    "domain": "gmail.com",
//    "domain_age_days": "XXXX",
//    "smtp_provider": "google",
//    "mx_found": "true",
//    "mx_record": "alt1.gmail-smtp-in.l.google.com",
//    "firstname": null,
//    "lastname": null,
//    "gender": null,
//    "country": null,
//    "region": null,
//    "city": null,
//    "zipcode": null,
//    "processed_at": "2024-08-25 06:41:33.994"
//    }

    public ZeroEmailVerificationService verifyEmail(String email) {
        try {
            URL url = new URL(API_URL + email);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            this.response = response.toString();
            System.out.println("Response: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean getStatus(){
        JsonObject mailStatus = jsonParser.parse(response).getAsJsonObject();
        return Objects.equals(mailStatus.get("status").getAsString(), "valid");
    }

}
