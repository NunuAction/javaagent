package com.snow.nu;

/**
 * created with IDEA
 *
 * @author:huqm
 * @date:2020/6/10
 * @time:19:15 <p>
 *
 * </p>
 */
public class User {

    private Integer age;

    private String name;

    public String toString(){
        return name+age.toString();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
