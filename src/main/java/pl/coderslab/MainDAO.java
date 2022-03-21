package pl.coderslab;

import pl.coderslab.dbUtil.DbUtil;
import pl.coderslab.entity.UserDao;
import pl.coderslab.model.User;

import java.util.Arrays;

public class MainDAO {
    public static void main(String[] args) {

        UserDao user = new UserDao();
        user.delete(3);
        System.out.println(Arrays.toString(user.findAll()));
    }
}

