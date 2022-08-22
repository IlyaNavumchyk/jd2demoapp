package com.jd2.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
import java.util.Objects;

public class User {

    private Long id;
    private String userName;
    private String surname;
    private Timestamp birth;
    private boolean isDeleted;
    private Timestamp creationDate;
    private Timestamp modificationDate;

    public User() {

    }

    public User(Long id, String userName, String surname, Timestamp birth, boolean isDeleted, Timestamp creationDate, Timestamp modificationDate) {
        this.id = id;
        this.userName = userName;
        this.surname = surname;
        this.birth = birth;
        this.isDeleted = isDeleted;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Timestamp getBirth() {
        return birth;
    }

    public void setBirth(Timestamp birth) {
        this.birth = birth;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Timestamp modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isDeleted == user.isDeleted && id.equals(user.id) && userName.equals(user.userName) && surname.equals(user.surname) && birth.equals(user.birth) && creationDate.equals(user.creationDate) && modificationDate.equals(user.modificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, surname, birth, isDeleted, creationDate, modificationDate);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
