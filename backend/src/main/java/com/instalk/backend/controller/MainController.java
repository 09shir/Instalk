package com.instalk.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.instalk.backend.model.Account;
import com.instalk.backend.model.Data;
import com.instalk.backend.model.Follower;
import com.instalk.backend.model.Following;
import com.instalk.backend.model.TargetAccount;
import com.instalk.backend.model.User;

import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@EnableScheduling
@CrossOrigin(origins="*")
public class MainController {

    private WebClient webClient;

    public MainController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }
    
    @Scheduled(cron = "0 50 23 * * *")
    @GetMapping("/timed_instalk")
    public void greeting() {

        System.out.println("starting");

        List<TargetAccount> targetAccounts = this.webClient.method(HttpMethod.GET)
                                                .uri("/targetAccounts")
                                                .retrieve()
                                                .bodyToMono(new ParameterizedTypeReference<List<TargetAccount>>(){})
                                                .block();

        targetAccounts.forEach(targetAccount -> {

            Map<String, String> target_requestBody = new HashMap<>();
            target_requestBody.put("targetAccount_id", targetAccount.getId().toString());

            String response = this.webClient.method(HttpMethod.GET)
                                  .uri("/instalkgram")
                                  .bodyValue(target_requestBody)
                                  .retrieve()
                                  .bodyToMono(String.class)
                                  .block();

            System.out.println(response);
            System.out.println(targetAccount.getUsername());
        });

    }
    
    // @params: targetAccount_id
    @GetMapping("/instalkgram")
    public String instalkgram(@RequestBody Map<String, String> orig_request) {

        String response_msg = "no previous record found. new data written";

        // ----------------- Uses targetAccount_id to find account username, password ----------------------------------
        String targetAccount_id = orig_request.get("targetAccount_id");
        Mono<TargetAccount> targetAccount = this.webClient.method(HttpMethod.GET)
                                                .uri("/targetAccount/" + targetAccount_id)
                                                .retrieve()
                                                .bodyToMono(TargetAccount.class);
        TargetAccount parsed_targetAccount = targetAccount.block();

        Account account = parsed_targetAccount.getAccount();

        Map<String, String> new_request = new HashMap<>();
        new_request.put("username", account.getUsername());
        new_request.put("password", account.getPassword());
        new_request.put("targetUsername", parsed_targetAccount.getUsername());


        // ----------------- Uses the account credentials to find target user's followers/followings ------------------
        Mono<Data> data = this.webClient.method(HttpMethod.GET)
                        .uri("/ins")
                        .bodyValue(new_request)
                        .retrieve()
                        .bodyToMono(Data.class);

        Data parsed_data = data.block();

        // // ----------------- creating mock data for testing --------------------------------------------------------------
        // Data mock_data = new Data();

        // List<User> followers = new ArrayList<>();
        // followers.add(new User("", "wcnmlgcbaaa"));
        // followers.add(new User("Justin", "0doyledd"));
        // followers.add(new User("Hooyar|هویار", "hoohamm_"));
        // mock_data.setFollowers(followers);

        // List<User> followings = new ArrayList<>();
        // followings.add(new User("Justin", "0doyledd"));
        // followings.add(new User("SFUdmc", "dankmemecouver"));
        // followings.add(new User("季汉博", "hanbo_ji"));
        // mock_data.setFollowings(followings);

        // ----------------- obtain today'd & yesterday's date -----------------------------------------------------------
        LocalDate localDate_today = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String today = localDate_today.format(formatter);
        // System.out.println(today);

        // ----------------- retrieve previous db ------------------------------------------------------------------------
        List<Follower> parsed_prev_followers = this.webClient.method(HttpMethod.GET)
                                            .uri("/followers/targetAccId/" + targetAccount_id)
                                            .retrieve()
                                            .bodyToMono(new ParameterizedTypeReference<List<Follower>>(){})
                                            .block();
        
        List<Following> parsed_prev_followings = this.webClient.method(HttpMethod.GET)
                                            .uri("/followings/targetAccId/" + targetAccount_id)
                                            .retrieve()
                                            .bodyToMono(new ParameterizedTypeReference<List<Following>>(){})
                                            .block();
        
        // ----------------- if exists: compare, store result and delete prev data ---------------------------------------
        Map<String, Object> account_requestBody = new HashMap<>();
        account_requestBody.put("id", account.getId());

        Map<String, Object> targetAccount_requestBody = new HashMap<>();
        targetAccount_requestBody.put("id", parsed_targetAccount.getId());

        if (parsed_prev_followers.size() != 0 && parsed_prev_followings.size() != 0){

            // compare
            Set<String> current_followers = parsed_data.getFollowers().stream()
                                                        .map(User::getUsername)
                                                        .collect(Collectors.toSet());
            Set<String> prev_followers = parsed_prev_followers.stream()
                                                        .map(Follower::getFollower)
                                                        .collect(Collectors.toSet());
            Set<String> current_followings = parsed_data.getFollowings().stream()
                                                        .map(User::getUsername)
                                                        .collect(Collectors.toSet());
            Set<String> prev_followings = parsed_prev_followings.stream()
                                                        .map(Following::getFollowing)
                                                        .collect(Collectors.toSet());

            List<Follower> removedFollowers = parsed_prev_followers.stream()
                                                        .filter(f -> !current_followers.contains(f.getFollower()))
                                                        .collect(Collectors.toList());
            List<Following> removedFollowings = parsed_prev_followings.stream()
                                                        .filter(f -> !current_followings.contains(f.getFollowing()))
                                                        .collect(Collectors.toList());
            List<User> addedFollowers = parsed_data.getFollowers().stream()
                                                        .filter(u -> !prev_followers.contains(u.getUsername()))
                                                        .collect(Collectors.toList());
            List<User> addedFollowings = parsed_data.getFollowings().stream()
                                                        .filter(u -> !prev_followings.contains(u.getUsername()))
                                                        .collect(Collectors.toList());

            // store result

            List<Map<String, Object>> FollowersChange_requestBody = new ArrayList<>();
            removedFollowers.forEach(follower -> {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("account", account_requestBody);
                requestBody.put("targetAccount", targetAccount_requestBody);
                requestBody.put("date", today);
                requestBody.put("removed", follower.getFollower());

                FollowersChange_requestBody.add(requestBody);
            });
            addedFollowers.forEach(follower -> {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("account", account_requestBody);
                requestBody.put("targetAccount", targetAccount_requestBody);
                requestBody.put("date", today);
                requestBody.put("added", follower.getUsername());

                FollowersChange_requestBody.add(requestBody);
            });

            String followersChangeResponse = webClient.post()
                    .uri("/followerUpdates")
                    .bodyValue(FollowersChange_requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            List<Map<String, Object>> FolloweringsChange_requestBody = new ArrayList<>();
            removedFollowings.forEach(following -> {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("account", account_requestBody);
                requestBody.put("targetAccount", targetAccount_requestBody);
                requestBody.put("date", today);
                requestBody.put("removed", following.getFollowing());

                FolloweringsChange_requestBody.add(requestBody);
            });
            addedFollowings.forEach(following -> {
                Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("account", account_requestBody);
                requestBody.put("targetAccount", targetAccount_requestBody);
                requestBody.put("date", today);
                requestBody.put("added", following.getUsername());

                FolloweringsChange_requestBody.add(requestBody);
            });

            String followingsChangeResponse = webClient.post()
                    .uri("/followingUpdates")
                    .bodyValue(FolloweringsChange_requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            
            System.out.println(followersChangeResponse);
            System.out.println(followingsChangeResponse);

            // delete prev data
            this.webClient.method(HttpMethod.DELETE)
                            .uri("/followers/targetAccId/" + parsed_targetAccount.getId().toString())
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();
            
            this.webClient.method(HttpMethod.DELETE)
                            .uri("/followings/targetAccId/" + parsed_targetAccount.getId().toString())
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();
            
            response_msg = "data updated";
                                   
        }
        // ----------------- Store new data -------------------------------------------------------------------------------
        

        List<Map<String, Object>> followers_requestBody = new ArrayList<>();
        parsed_data.getFollowers().forEach(follower -> {

                Map<String, Object> inner_requestBody = new HashMap<>();
                inner_requestBody.put("account", account_requestBody);
                inner_requestBody.put("targetAccount", targetAccount_requestBody);
                inner_requestBody.put("date", today);
                inner_requestBody.put("follower", follower.getUsername());

                followers_requestBody.add(inner_requestBody);
        });

        String followers_response = webClient.post()
                .uri("/followers")
                .bodyValue(followers_requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        List<Map<String, Object>> followings_requestBody = new ArrayList<>();
        parsed_data.getFollowings().forEach(following -> {
                
                Map<String, Object> inner_requestBody = new HashMap<>();
                inner_requestBody.put("account", account_requestBody);
                inner_requestBody.put("targetAccount", targetAccount_requestBody);
                inner_requestBody.put("date", today);
                inner_requestBody.put("following", following.getUsername());

                followings_requestBody.add(inner_requestBody);
        });

        String followings_response = webClient.post()
                .uri("/followings")
                .bodyValue(followings_requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        // System.out.println(followers_response);
        // System.out.println(followings_response);
        // System.out.println(response_msg);

        return response_msg + "\n" + followers_response + "\n" + followings_response;
    }
    
}
