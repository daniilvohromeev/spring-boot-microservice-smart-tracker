package com.javadeveloperzone.service;

import com.javadeveloperzone.modal.Order;
import com.javadeveloperzone.modal.User;
import com.javadeveloperzone.modal.UserDetails;
import com.javadeveloperzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    private final UserRepository userRepository;

    private final CircuitBreakerFactory cbFactory;

    public UserService(@Qualifier("orderRestTemplate") RestTemplate restTemplate, UserRepository userRepository, CircuitBreakerFactory cbFactory) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.cbFactory = cbFactory;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public UserDetails getUserById(Long id){
        UserDetails userDetails = new UserDetails();
        userDetails.setUser( userRepository.findById(id).get());
        userDetails.setOrders(getOrderByUser(id));
        return userDetails;
    }

    public List<Order> getOrderByUser(Long userId){
        return cbFactory.create("order-details").run(()->{
                    ResponseEntity<List<Order>> ordersEntity = restTemplate.exchange("http://order-service/order/" + userId, HttpMethod.GET,null,new ParameterizedTypeReference<List<Order>>(){});
                    return ordersEntity.getBody();
                }, throwable -> new ArrayList<>()
        );
    }

    public List<UserDetails> getAllUsers(){
       List<User>  users = userRepository.findAll();
        return
                users.stream().map(user -> {
            UserDetails userDetails=new UserDetails();
            userDetails.setUser(user);
            userDetails.setOrders(getOrderByUser(user.getId()));
            return userDetails;
        }).collect(Collectors.toList());
    }
}
