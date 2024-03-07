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
import com.instalk.backend.model.Follower;
import com.instalk.backend.repository.FollowerRepository;

@RestController
@CrossOrigin(origins="*")
public class FollowerController {
    
    @Autowired
    private FollowerRepository followerRepository;

    @PostMapping("/follower")
    Follower newFollower(@RequestBody Follower newFollower){
        return followerRepository.save(newFollower);
    }

    @PostMapping("/followers")
    String newFollowers(@RequestBody List<Follower> newFollowers){
        followerRepository.saveAll(newFollowers);
        return "Saved " + newFollowers.size() + " records of followers";
    }
    
    @GetMapping("/followers")
    List<Follower> getAllFollowers(){
        return followerRepository.findAll();
    }

    @GetMapping("/follower/{id}")
    Follower getFollowerById(@PathVariable Long id){
        return followerRepository.findById(id).orElseThrow(() -> new AccNotFoundException(id));
    }

    @GetMapping("/followers/accId/{acc_id}")
    List<Follower> getFollowersByAccId(@PathVariable Long acc_id){
        return followerRepository.findByAccId(acc_id);
    }

    @GetMapping("/followers/targetAccId/{targetAcc_id}")
    List<Follower> getFollowersByTargetAccId(@PathVariable Long targetAcc_id){
        return followerRepository.findByTargetAccId(targetAcc_id);
    }

    @DeleteMapping("/follower/{id}")
    String deleteFollower(@PathVariable Long id){
        if (!followerRepository.existsById(id)){
            throw new AccNotFoundException(id);
        }
        followerRepository.deleteById(id);
        return "Follower with id " + id + " has been successfully deleted";
    }

    @DeleteMapping("/followers/accId/{acc_id}")
    String deleteFollowerByAccId(@PathVariable Long acc_id){
        if (followerRepository.existsByAccId(acc_id) == 0){
            throw new AccNotFoundException(acc_id);
        }
        followerRepository.deleteByAccId(acc_id);
        return "Followers with acc_id " + acc_id + " has been successfully deleted";
    }

    @DeleteMapping("/followers/targetAccId/{targetAcc_id}")
    String deleteFollowerByTargetAccId(@PathVariable Long targetAcc_id){
        if (followerRepository.existsByTargetAccId(targetAcc_id) == 0){
            throw new AccNotFoundException(targetAcc_id);
        }
        followerRepository.deleteByTargetAccId(targetAcc_id);
        return "Followers with targetAcc_id " + targetAcc_id + " has been successfully deleted";
    }
}
