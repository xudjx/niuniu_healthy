package com.niuniu.motion.model.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_cities")
public class CityDO {
    @Id
    @GeneratedValue
    @Column(name = "city_id")
    private Long id;
    @Column(name = "province")
    private String province;
    @Column(name = "city")
    private String city;
    @Column(name = "district")
    private String district;

    public Long getId() {
        return id;
    }

    public void setId(Long cityId) {
        this.id = cityId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
