package com.niuniu.motion.model.query;
import javax.persistence.*;

@Entity
@Table(name = "t_cityWeather")
public class CityWeatherDO {
    @Id
    @Column(name = "city_id")
    private Long cityId ;
    @Column(name = "airCondition")
    public String airCondition;
    @Column(name = "city")
    public String city;//**************//
    @Column(name = "coldIndex")
    public String coldIndex;
    @Column(name = "date")
    public String date;
    @Column(name = "distrct")
    public String distrct;//****************//
    @Column(name = "dressingIndex")
    public String dressingIndex;
    @Column(name = "exerciseIndex")
    public String exerciseIndex;


    @Column(name = "humidity")
    public String humidity;
    @Column(name = "pollutionIndex")
    public String pollutionIndex;
    @Column(name = "province")
    public String province;//*************//
    @Column(name = "sunrise")
    public String sunrise;
    @Column(name = "sunset")
    public String sunset;
    @Column(name = "temperature")
    public String temperature;
    @Column(name = "time")
    public String time;
    @Column(name = "updateTime")
    public String  updateTime;
    @Column(name = "washIndex")
    public String washIndex;
    @Column(name = "weather")
    public String weather;
    @Column(name = "week")
    public String week;
    @Column(name = "wind")
    public String wind;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(String airCondition) {
        this.airCondition = airCondition;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getColdIndex() {
        return coldIndex;
    }

    public void setColdIndex(String coldIndex) {
        this.coldIndex = coldIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistrct() {
        return distrct;
    }

    public void setDistrct(String distrct) {
        this.distrct = distrct;
    }

    public String getDressingIndex() {
        return dressingIndex;
    }

    public void setDressingIndex(String dressingIndex) {
        this.dressingIndex = dressingIndex;
    }

    public String getExerciseIndex() {
        return exerciseIndex;
    }

    public void setExerciseIndex(String exerciseIndex) {
        this.exerciseIndex = exerciseIndex;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPollutionIndex() {
        return pollutionIndex;
    }

    public void setPollutionIndex(String pollutionIndex) {
        this.pollutionIndex = pollutionIndex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWashIndex() {
        return washIndex;
    }

    public void setWashIndex(String washIndex) {
        this.washIndex = washIndex;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
