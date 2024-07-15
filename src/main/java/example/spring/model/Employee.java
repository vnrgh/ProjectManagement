package example.spring.model;

import example.spring.enums.Skill;

import java.util.Objects;

public class Employee {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final double salary;
    private final Skill skill;

    public Employee(int id, String firstName, String lastName, int age, double salary, Skill skill) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.skill = skill;
    }

    public int getId() {
        return id;
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
        return id == employee.id && age == employee.age && Double.compare(salary, employee.salary) == 0 && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && skill == employee.skill;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, salary, skill);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", skill=" + skill +
                '}';
    }
}