package com.supportportal.enumeration;

import com.supportportal.constant.Authority;


public enum Role {
    ROLE_USER(Authority.USER_AUTHORITIES),
    ROLE_HR(Authority.HR_AUTHORITIES),
    ROLE_MANAGER(Authority.MANAGER_AUTHORITIES),
    ROLE_ADMIN(Authority.ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(Authority.SUPER_ADMIN_AUTHORITES);

    private String[] authorites;


    Role(String... authorites) {
        this.authorites = authorites;
    }

    public String[] getAuthorites() {
        return authorites;
    }
}
