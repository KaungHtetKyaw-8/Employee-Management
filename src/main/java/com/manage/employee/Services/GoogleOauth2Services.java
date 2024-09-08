package com.manage.employee.Services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.GmailScopes;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;

@Service @Profile("Google")
public class GoogleOauth2Services {
    private final String APPLICATION_NAME = "MyTestingApp";
    private final JsonFactory GSON_FACTORY = GsonFactory.getDefaultInstance();
    private final String TOKENS_DIRECTORY_PATH = "tokens";
    private final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";
    private final Collection<String> SCOPOES = Collections.singleton(GmailScopes.GMAIL_READONLY);
    private final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
    private final String REDIRECT_URL = "http://localhost:8080/api/v1/google/auth/Callback";

    private GoogleAuthorizationCodeFlow googleFlow;


    public String getAuthorizeUrl(String userId) throws IOException {
        return getGoogleAuthorizationCodeFlow()
                .newAuthorizationUrl()
                .setRedirectUri(REDIRECT_URL)
                .build();
    }

    public Credential getCredential(String userId) throws IOException {
        return getGoogleAuthorizationCodeFlow().loadCredential(userId);
    }

    public boolean isStoredCredential(String userId) throws IOException {
        Credential credential = getGoogleAuthorizationCodeFlow().loadCredential(userId);

        return credential != null && (credential.getRefreshToken() != null || credential.getExpiresInSeconds() == null || credential.getExpiresInSeconds() > 60L);
    }

    public Credential credentialBuiltAndStore(String code,String userId) throws IOException {
        GoogleAuthorizationCodeFlow flow = getGoogleAuthorizationCodeFlow();

        /*Token Request and Response*/
        TokenResponse response = flow.newTokenRequest(code)
                .setRedirectUri(REDIRECT_URL+ "/" + userId)
                .execute();

//        AuthorizationCodeInstalledApp
        return flow.createAndStoreCredential(response,userId);
    }

    /** Authorizes the installed application to access user's protected data. */
    private GoogleAuthorizationCodeFlow getGoogleAuthorizationCodeFlow() throws IOException {
        if (googleFlow != null){
            return googleFlow;
        }

        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(GSON_FACTORY,
                new InputStreamReader(Files.newInputStream(Path.of(CREDENTIALS_FILE_PATH))));

        // set up authorization code flow
        googleFlow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), GSON_FACTORY, clientSecrets, SCOPOES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return googleFlow;
    }




//    private static Credential authroize() throws Exception {
//        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
//        Plus plus = new Plus.builder(new NetHttpTransport(),
//                GsonFactory.getDefaultInstance(),
//                credential)
//                .setApplicationName("Google-PlusSample/1.0")
//                .build();
//
//        return credential;
//    }
//    private static Directory getDirectoryService() throws IOException {
//        GoogleCredential credential = GoogleCredential.fromStream(GoogleEmailVerification.class.getResourceAsStream("/path/to/credentials.json"))
//                .createScoped(Collections.singleton("https://www.googleapis.com/auth/admin.directory.user.readonly"));
//
//        return new Directory.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
//                .setApplicationName("EmailVerificationApp")
//                .build();
//    }
//
//    public static boolean verifyEmail(String email) {
//        try {
//            Directory service = getDirectoryService();
//            Users result = service.users().list().setQuery("email:" + email).execute();
//            System.out.println(result);
//            return !result.getUsers().isEmpty();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public static void main(String[] args) throws Exception {
//        verifyEmail("minylover123@gmail.com");
//        Credential credential = authorize();
//    }
}
