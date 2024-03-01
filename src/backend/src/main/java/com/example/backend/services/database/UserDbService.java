package com.example.backend.services.database;

import com.example.backend.models.database.UserDbEntity;
import com.example.backend.repos.UserDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDbService {
    private UserDbRepo repo;

    @Autowired
    public UserDbService(UserDbRepo repo) {
        this.repo = repo;
    }

    public void addUser(UserDbEntity user) {
        try {
            repo.save(user);
        } catch(IllegalArgumentException |
                OptimisticLockingFailureException error) {
            System.out.println("Error in UserDbService::addUser - failed to save the user in the database:\n" + error);
        }
    }

    public void deleteUser(UserDbEntity user) {
        try {
            repo.delete(user);
        } catch(IllegalArgumentException | OptimisticLockingFailureException error) {
            System.out.println("Error in UserDbService::deleteUser - failed to delete the user from the database:\n" + error);
        }
    }

    public UserDbEntity findUserById(Long id) {
        if(id == 0 || id == null) {
            throw new IllegalArgumentException("User id cannot be 0 or null");
        }

        return repo.findUserById(id);
    }

    public UserDbEntity findUserByUsername(String username) {
        return repo.findUserByUsername(username);
    }
}
