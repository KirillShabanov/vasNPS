package com.home.MyWorkTime.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vas_roles")
public class VasRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer user_level_access;

    @ManyToMany(mappedBy = "vasRoles", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<VasUserModel> vasUserModels;

    public VasRoles() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUser_level_access() {
        return user_level_access;
    }

    public void setUser_level_access(Integer user_level_access) {
        this.user_level_access = user_level_access;
    }

    public Set<VasUserModel> getVasUserModelSet() {
        return vasUserModels;
    }

    public void setVasUserModelSet(Set<VasUserModel> vasUserModelSet) {
        this.vasUserModels = vasUserModelSet;
    }

    @Override
    public String toString() {
        return "VasRoles{" +
                "id=" + id +
                ", user_level_access=" + user_level_access +
                ", vasUserModelSet=" + vasUserModels +
                '}';
    }
}
