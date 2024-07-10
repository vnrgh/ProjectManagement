package example.spring.model;

import example.spring.enums.Skill;

import java.util.Objects;

public class Employee {
    private String firstName;
    private String lastName;
    private int age;
    private double salary;
    private Skill skill;

    public Employee(String firstName, String lastName, int age, double salary, Skill skill) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.skill = skill;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public Skill getSkill() {
        return skill;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age && Double.compare(salary, employee.salary) == 0 && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(skill, employee.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, salary, skill);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", skill='" + skill + '\'' +
                '}';
    }
}