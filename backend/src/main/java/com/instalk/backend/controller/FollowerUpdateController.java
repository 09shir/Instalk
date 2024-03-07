package com.instalk.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instalk.backend.model.FollowerUpdate;
import com.instalk.backend.repository.FollowerUpdateRepository;

@RestController
@CrossOrigin(origins="*")
public class FollowerUpdateController {
    
    @Autowired
    private FollowerUpdateRepository followerUpdateRepository;

    @PostMapping("/followerUpdate")
    FollowerUpdate newFollowerUpdate(@RequestBody FollowerUpdate newFollowerUpdate){
        return followerUpdateRepository.save(newFollowerUpdate);
    }

    @PostMapping("/followerUpdates")
    String newFollowerUpdates(@RequestBody List<FollowerUpdate> newFollowerUpdates){
        followerUpdateRepository.saveAll(newFollowerUpdates);
        return "Saved " + newFollowerUpdates.size() + " records of followers change";
    }

    @GetMapping("/followerUpdate/accId/{acc_id}")
    List<FollowerUpdate> getFollowerUpdatesByAccId(@PathVariable Long acc_id){
        return followerUpdateRepository.findByAccId(acc_id);
    }

    @GetMapping("/followerUpdate/targetAccId/{targetAcc_id}")
    List<FollowerUpdate> getFollowerUpdatesByTargetAccId(@PathVariable Long targetAcc_id){
        return followerUpdateRepository.findByTargetAccId(targetAcc_id);
    }
}
