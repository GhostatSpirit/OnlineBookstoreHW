package com.lykavin.bookstore.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lykav on 2017/6/1.
 */
@Entity
@Table(name = "passwordResetToken", schema = "bookstore", catalog = "")
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name="user_id")
    private UserEntity user;

    @Column(name="expiryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public PasswordResetToken(){
        super();
//        this.token = token;
//        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }


    @Transient
    private Date calculateExpiryDate(final int expiryTimeInMinutes){
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }


    @Transient
    public void updateToken(final String token){
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }


    @Transient
    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
