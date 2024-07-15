package example.spring.configuration;

import example.spring.enums.Skill;
import example.spring.model.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//public class EmployeeConfig {
//    @Bean
//    public List<Employee> employees() {
//        List<Employee> employeeList = new ArrayList<>();
//        employeeList.add(
//                new Employee(
//                        0,
//                        "Jonh",
//                        "Smith",
//                        30,
//                        3000.00,
//                        Skill.MIDDLE
//                )
//        );
//        employeeList.add(
//                new Employee(
//                        1,
//                        "Peter",
//                        "Griffin",
//                        45,
//                        2000.00,
//                        Skill.JUNIOR
//                )
//        );
//        return employeeList;
//    }
//}
