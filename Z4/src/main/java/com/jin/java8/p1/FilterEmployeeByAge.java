package com.jin.java8.p1;

import com.jin.java8.Employee;

public class FilterEmployeeByAge implements MyPredicate<Employee>{
    @Override
    public boolean test(Employee t) {
        return t.getAge()>=35;
    }
}
