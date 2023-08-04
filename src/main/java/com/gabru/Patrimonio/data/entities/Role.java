package com.gabru.Patrimonio.data.entities;

public enum Role {
    ADMIN,MANAGER,OPERATOR,CUSTOMER;
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
