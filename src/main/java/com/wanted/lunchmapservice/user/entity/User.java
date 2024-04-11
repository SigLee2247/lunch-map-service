package com.wanted.lunchmapservice.user.entity;

import com.wanted.lunchmapservice.common.BaseTime;
import com.wanted.lunchmapservice.user.entity.enums.ServiceAccess;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User extends BaseTime {

    @Id
    @Column(name = "user_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    @Column(name = "service_access")
    private ServiceAccess serviceAccess;

    public void changeLat(Double latitude) {
        this.latitude = latitude;
    }

    public void changeLon(Double longitude) {
        this.longitude = longitude;
    }

    public void changeServiceAccess(ServiceAccess access) {
        this.serviceAccess = access;
    }

    public void changeUsername(String userName) {
        this.userName = userName;
    }
}
