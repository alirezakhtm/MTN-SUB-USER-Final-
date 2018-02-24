/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gson;

/**
 *
 * @author alirezakhtm
 */
public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private int sex;

    public Person(String firstName, String lastName, int age, int sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "[Name = '"+this.firstName+"' , Family = '"+this.lastName+"', "
                + "Age = '"+this.age+"', Sex = '"+this.sex+"']";
    }
}
