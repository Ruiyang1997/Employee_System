package com.ry.employee_system.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Emp {
    private int id;
    private String name;
    private String path;
    private String salary;
    private String age;
}
