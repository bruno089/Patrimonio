package com.gabru.Patrimonio.entities;

public enum Role {
    ADMIN,AUTHENTICATED;
    public String roleName() { return "ROLE_" + this.toString(); }

    public boolean isRole() {
        for(Role role: Role.values()) {
            if(!role.roleName().equals(this)) {
                return false;
            }
        }
        return true;
    }
}