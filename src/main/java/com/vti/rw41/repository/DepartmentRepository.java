package com.vti.rw41.repository;

import com.vti.rw41.entity.dto.DepartmentDto;
import com.vti.rw41.entity.test.Department;
import com.vti.rw41.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DepartmentRepository {
    public static void createDepartments() {

        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        for (int i = 0; i < 10; i++) {
            Department department = new Department();
            department.setDepartmentName("department_" + (i + 1));
            session.save(department);

        }
        session.getTransaction().commit();
    }

    public static List<DepartmentDto> getAllDepartments() {

        Session session = HibernateUtils.getSession();
        Query<DepartmentDto> query = session.createQuery("select " +
                "new com.vti.rw41.entity.dto.DepartmentDto(d.id,d.departmentName,a.name) " +
                "From DetailDepartment dd" +
                " right join dd.department d " +
                "left join dd.address a", DepartmentDto.class);
        return query.getResultList();
    }

}
