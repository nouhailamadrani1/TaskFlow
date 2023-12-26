package com.yocode.taskv1.service.serviceImpl;

import com.yocode.taskv1.dto.UserDTO;
import com.yocode.taskv1.exception.UserNotFoundException;
import com.yocode.taskv1.mapper.UserMapper;
import com.yocode.taskv1.model.User;
import com.yocode.taskv1.repository.UserRepository;
import com.yocode.taskv1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.dtoToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.entityToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        userDTO.setId(userId);
        User updatedUser = userRepository.save(userMapper.dtoToEntity(userDTO));

        return userMapper.entityToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        userRepository.deleteById(userId);
    }
    @Override
    public boolean existsById(Long userId){
        boolean userExist= userRepository.existsById(userId);
        if(userExist){
            return true;
        }
        return false;
    }
}
