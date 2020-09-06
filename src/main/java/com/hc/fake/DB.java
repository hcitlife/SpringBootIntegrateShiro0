package com.hc.fake;

import com.hc.domain.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DB {
    private static List<User> users = new ArrayList<>();
    private static Map<Integer, Set<String>> auths = new HashMap<>();

    static {
        users.add(new User(1001, "hc", "hc"));
        users.add(new User(1002, "zhangsan", "zhangsan"));
        users.add(new User(1003, "lisi", "lisi"));
        users.add(new User(1004, "wanger", "wanger"));
        auths.put(1001, new HashSet<>(Arrays.asList("user:add", "user:update")));
        auths.put(1002, new HashSet<>(Arrays.asList("user:add")));
        auths.put(1003, new HashSet<>(Arrays.asList("user:update")));
        auths.put(1004, new HashSet<>(Arrays.asList("user:add", "user:update")));
    }

    public static Set<String> getAuths(Integer userId){
        return auths.get(userId);
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
