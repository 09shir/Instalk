package com.instalk.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instalk.backend.exception.AccNotFoundException;
import com.instalk.backend.model.Following;
import com.instalk.backend.repository.FollowingRepository;

@RestController
@CrossOrigin(origins="*")
public class FollowingController {

    @Autowired
    private FollowingRepository followingRepository;

    @PostMapping("/following")
    Following newFollowing(@RequestBody Following newFollowing){
        return followingRepository.save(newFollowing);
    }

    @PostMapping("/followings")
    String newFollowerings(@RequestBody List<Following> newFollowings){
        followingRepository.saveAll(newFollowings);
        return "Saved " + newFollowings.size() + " records of followings";
    }
    
    @GetMapping("/followings")
    List<Following> getAllFollowings(){
        return followingRepository.findAll();
    }

    @GetMapping("/following/{id}")
    Following getFollowingById(@PathVariable Long id){
        return followingRepository.findById(id).orElseThrow(() -> new AccNotFoundException(id));
    }

    @GetMapping("/followings/accId/{acc_id}")
    List<Following> getFollowingsByAccId(@PathVariable Long acc_id){
        return followingRepository.findByAccId(acc_id);
    }

    @GetMapping("/followings/targetAccId/{targetAcc_id}")
    List<Following> getFollowingsByTargetAccId(@PathVariable Long targetAcc_id){
        return followingRepository.findByTargetAccId(targetAcc_id);
    }

    @DeleteMapping("/following/{id}")
    String deleteFollowing(@PathVariable Long id){
        if (!followingRepository.existsById(id)){
            throw new AccNotFoundException(id);
        }
        followingRepository.deleteById(id);
        return "Following with id " + id + " has been successfully deleted";
    }

    @DeleteMapping("/followings/accId/{acc_id}")
    String deleteFollowingByAccId(@PathVariable Long acc_id){
        if (followingRepository.existsByAccId(acc_id) == 0){
            throw new AccNotFoundException(acc_id);
        }
        followingRepository.deleteByAccId(acc_id);
        return "Followings with acc_id " + acc_id + " has been successfully deleted";
    }

    @DeleteMapping("/followings/targetAccId/{targetAcc_id}")
    String deleteFollowingByTargetAccId(@PathVariable Long targetAcc_id){
        if (followingRepository.existsByTargetAccId(targetAcc_id) == 0){
            throw new AccNotFoundException(targetAcc_id);
        }
        followingRepository.deleteByTargetAccId(targetAcc_id);
        return "Followings with targetAcc_id " + targetAcc_id + " has been successfully deleted";
    }
    
}
