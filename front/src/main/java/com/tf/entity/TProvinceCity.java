package com.tf.entity;
import java.util.ArrayList;
import java.util.List;

public class TProvinceCity {
    private Integer id;

    private String code;

    private String name;

    private Integer parentId;

    private String firstLetter;

    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public List<TProvinceCity> getChildList() {
        return childList;
    }

    public void setChildList(List<TProvinceCity> childList) {
        this.childList = childList;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter == null ? null : firstLetter.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    private List<TProvinceCity> childList = new ArrayList<TProvinceCity>();

}