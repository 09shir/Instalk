package com.instalk.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instalk.backend.model.FollowingUpdate;
import com.instalk.backend.repository.FollowingUpdateRepository;

@RestController
@CrossOrigin(origins="*")
public class FollowingUpdateController {
    
    @Autowired
    private FollowingUpdateRepository followingUpdateRepository;

    @PostMapping("/followingUpdate")
    FollowingUpdate newFollowingUpdate(@RequestBody FollowingUpdate newFollowingUpdate){
        return followingUpdateRepository.save(newFollowingUpdate);
    }

    @PostMapping("/followingUpdates")
    String newFollowingUpdates(@RequestBody List<FollowingUpdate> newFollowingUpdates){
        followingUpdateRepository.saveAll(newFollowingUpdates);
        return "Saved " + newFollowingUpdates.size() + " records of followings change";
    }

    @GetMapping("/followingUpdate/accId/{acc_id}")
    List<FollowingUpdate> getFollowingUpdatesByAccId(@PathVariable Long acc_id){
        return followingUpdateRepository.findByAccId(acc_id);
    }

    @GetMapping("/followingUpdate/targetAccId/{targetAcc_id}")
    List<FollowingUpdate> getFollowingUpdatesByTargetAccId(@PathVariable Long targetAcc_id){
        return followingUpdateRepository.findByTargetAccId(targetAcc_id);
    }
}
