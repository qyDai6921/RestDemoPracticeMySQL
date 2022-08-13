package com.user.vo;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

// 用在servers之间响应的时候
@XmlRootElement
//When a top level class or an enum type is annotated with the @XmlRootElement annotation,
// then its value is represented as XML element in an XML document.
public class User {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @Max(100)
    @Min(10)
    private Integer age;
    private Double salary;

    public User() {
    }

    public User(Long id, String name, Integer age, Double salary) {
        super(); //?
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
