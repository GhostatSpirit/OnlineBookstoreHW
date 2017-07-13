package com.lykavin.bookstore.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by lykav on 2017/5/31.
 */
public class Authority implements GrantedAuthority {
    private final String authority;

    public Authority(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority(){
        return authority;
    }
}
