package com.chanpay.ppd.mps.mobile.test;

import com.chanpay.ppd.mps.api.entity.TripUser;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class SpringRestTestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8081/";
     
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllUsers(){
        //System.out.println("Testing listAllUsers API-----------");
        //
        //RestTemplate restTemplate = new RestTemplate();
        //List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/user/", List.class);
        //
        //if(usersMap!=null){
        //    for(LinkedHashMap<String, Object> map : usersMap){
        //        System.out.println("User : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
        //    }
        //}else{
        //    System.out.println("No user exist----------");
        //}
    }
     
    /* GET */
    private static void getUser(){
        //System.out.println("Testing getUser API----------");
        //RestTemplate restTemplate = new RestTemplate();
        //User user = restTemplate.getForObject(REST_SERVICE_URI+"/user/1", User.class);
        //System.out.println(user);
    }
     
    /* POST */
    private static void createUser() {
        System.out.println("Testing create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        TripUser user = new TripUser();
        user.setMobile("15101187432");
        user.setAge("28");
        user.setGender("M");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/v1/user/", user, TripUser.class);
        //System.out.println("Location : "+uri.toASCIIString());
    }
 
    /* PUT */
    private static void updateUser() {
        //System.out.println("Testing update User API----------");
        //RestTemplate restTemplate = new RestTemplate();
        //User user  = new User(1,"Tomy",33, 70000);
        //restTemplate.put(REST_SERVICE_URI+"/user/1", user);
        //System.out.println(user);
    }
 
    /* DELETE */
    private static void deleteUser() {
        //System.out.println("Testing delete User API----------");
        //RestTemplate restTemplate = new RestTemplate();
        //restTemplate.delete(REST_SERVICE_URI+"/user/3");
    }
 
 
    /* DELETE */
    private static void deleteAllUsers() {
        //System.out.println("Testing all delete Users API----------");
        //RestTemplate restTemplate = new RestTemplate();
        //restTemplate.delete(REST_SERVICE_URI+"/user/");
    }
 
    public static void main(String args[]){
        //listAllUsers();
        //getUser();
        //createUser();
        //listAllUsers();
        //updateUser();
        //listAllUsers();
        //deleteUser();
        //listAllUsers();
        //deleteAllUsers();
        //listAllUsers();
        createUser();
    }
}