package com.tap.schoolplatform.models.users.shared;

import com.tap.schoolplatform.models.academic.Group;
import com.tap.schoolplatform.models.users.User;
import com.tap.schoolplatform.models.users.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "memberships", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "group_id"})
})
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(optional = false)
    private User user;

    @NotNull
    @ManyToOne(optional = false)
    private Group group;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public Membership() {}

    public Membership(User user, Group group, Role role) {
        this.user = user;
        this.group = group;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membership that = (Membership) o;
        return id == that.id;
    }

}