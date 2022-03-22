package pl.coderslab;

import pl.coderslab.BCrypt.BCrypt;
import pl.coderslab.dbUtil.DbUtil;
import pl.coderslab.entity.UserDao;
import pl.coderslab.model.User;

import java.util.Arrays;

public class MainDAO {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User kasia = new User();
        kasia.setPassword("Pass1234#");
        kasia.setUserName("Kasia1111");
        kasia.setEmail("Kacha28@buziaczek.pl");
        userDao.create(kasia);

        System.out.println("\nuser with id " + kasia.getId()+ ":");
        System.out.println(userDao.read(kasia.getId()));
        System.out.println("non-existing user:");
        System.out.println(userDao.read(Long.MAX_VALUE));

        User user8 = userDao.read(8);
        System.out.println("\nemail before change: " +user8.getEmail());
        user8.setEmail("changedmail@eu.ru");
        userDao.update(user8);
        System.out.println("after change: " + userDao.read(8).getEmail());

        System.out.println("\n password: ");
        System.out.println(kasia.getPassword());
        System.out.println(userDao.read(kasia.getId()).getPassword());
        if(BCrypt.checkpw(kasia.getPassword(),userDao.read(kasia.getId()).getPassword())){
            System.out.println("password matches");
        } else {
            System.out.println("wrong password");
        }

        System.out.println("\n all users:");
        User[] userArray = userDao.findAll();
        for(User k : userArray){
            System.out.println(k);
        }
        userDao.delete(kasia.getId());
        System.out.println("all users after last deleted:");
        User[] userArray2 = userDao.findAll();
        for(User k : userArray2){
            System.out.println(k);
        }
    }
}

