package com.vti.rw41;

import com.vti.rw41.entity.*;
import com.vti.rw41.entity.dto.DepartmentDto;
import com.vti.rw41.entity.dto.ProductDto;
import com.vti.rw41.enumurations.ProductStatus;
import com.vti.rw41.repository.CategoryRepository;
import com.vti.rw41.repository.DepartmentRepository;
import com.vti.rw41.repository.ProductRepository;
import com.vti.rw41.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<DepartmentDto> allDepartments = DepartmentRepository.getAllDepartments();
        for (DepartmentDto departmentDto : allDepartments) {
            System.out.println(departmentDto);
        }
    }

    static void demoCriteria() {
        Session session = HibernateUtils.getSessionFactory()
                .openSession();

        Criteria criteria = session.createCriteria(ProductEntity.class);

        criteria.add(Restrictions.le("id", 1)); //where id =1

        List<ProductEntity> resultList = criteria.list();
        for (ProductEntity productEntity : resultList) {
            System.out.println(productEntity.getProductName() + "--" + productEntity.getPrice());
        }
    }

    static void demoSelectSql() {
        Session session = HibernateUtils.getSessionFactory()
                .openSession();

        NativeQuery<ProductEntity> nativeQuery = session.createNativeQuery("select * from product", ProductEntity.class);
        List<ProductEntity> resultList = nativeQuery.getResultList();
        for (ProductEntity productEntity : resultList) {
            System.out.println(productEntity.getProductName() + "--" + productEntity.getPrice());
        }
    }

    static void demoSelectDto() {
        Session session = HibernateUtils.getSessionFactory().openSession();

        //DTO -> data transfer object
        session.beginTransaction();

        Query<ProductDto> query = session.createQuery("" +
                " select new com.vti.rw41.entity.dto.ProductDto(p.productName, p.price,c.name )" +
                " From ProductEntity p " +
                "left join p.category c ", ProductDto.class);

        List<ProductDto> resultList = query.getResultList();

        for (ProductDto dto : resultList) {
            System.out.println(dto);
        }
        session.getTransaction().commit();
    }

    static void demoUpdateHql() {
        Session session = HibernateUtils.getSessionFactory().openSession();

        session.beginTransaction();
        Query query = session.createQuery("" +
                "update ProductEntity p " +
                "set p.productName =:productName " +
                "where p.id =:productId");

        query.setParameter("productId", 4);
        query.setParameter("productName", "updated product");

        query.executeUpdate();
        session.getTransaction().commit();
    }

    static void demoSelectHql() {
        Session session = HibernateUtils.getSessionFactory().openSession();

        //Select * from Product where name like '%San pham%'
        Query<ProductEntity> query = session.createQuery("" +
                " From ProductEntity as p " +
                "where p.productName like '%San pham%' ", ProductEntity.class);

        List<ProductEntity> resultList = query.getResultList();

        for (ProductEntity productEntity : resultList) {
            System.out.println("id= " + productEntity.getId() + "--name= " + productEntity.getProductName());
        }
    }
//        Session session = HibernateUtils.getSession();
//        Student student = session.find(Student.class,"S202200021");
//        session.beginTransaction();
//        student.setName(student.getName().toUpperCase());
//
//        session.save(student);
//        session.getTransaction().commit();


    static void setCategoryName() {
        Optional<CategoryEntity> category = CategoryRepository.findByName("Sach 1");
        if (category.isPresent()) {
            CategoryEntity categoryEntity = category.get();
            categoryEntity.setName("Sach ");
            CategoryRepository.save(categoryEntity);
        }
    }

    static void getIdAndProductName() {
        Optional<CategoryEntity> category = CategoryRepository.findByName("Sach");
        if (category.isPresent()) {
            CategoryEntity categoryEntity = category.get();
            System.out.println("category name =" + categoryEntity.getName());
            for (ProductEntity product : categoryEntity.getProducts()) {
                System.out.println(product.getId() + "--" + product.getProductName());
            }
        }
    }

    static void getCategory() {
        List<ProductEntity> products = ProductRepository.findByName("San pham 4");
        for (ProductEntity product : products) {
            System.out.println(product.getCategory().getName());
        }
    }

    static void setCategory() {
        Optional<CategoryEntity> category = CategoryRepository.findByName("Thoi trang");
        List<ProductEntity> products = ProductRepository.findAllProduct();
        for (ProductEntity product : products) {
            if (category.isPresent()) {
                product.setCategory(category.get());
            }
            ProductRepository.saveOrUpdate(product);
            System.out.println(product);
        }
    }


    static void testSelectAndDelete() {
        ProductEntity productEntity = ProductRepository.findById(2);
        if (productEntity != null) {
            ProductRepository.delete(productEntity);
        }
    }

    static void testSelectAndUpdate() {

        ProductEntity productEntity = ProductRepository.findById(5);
        if (productEntity != null) {
            productEntity.setPrice(888.88);
            productEntity.setUpdatedDate(LocalDateTime.now());
            productEntity.setProductName("abc");
            productEntity.setStatus(ProductStatus.INACTIVE);

            ProductRepository.saveOrUpdate(productEntity);
        }
    }

    static void testInsert() {
        Session session = HibernateUtils.getSessionFactory()
                .openSession();
        session.beginTransaction();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName("xyz");
        productEntity.setPrice(99.9);
        productEntity.setCreatedDate(LocalDateTime.now());
        productEntity.setUpdatedDate(LocalDateTime.now());

        session.save(productEntity);

        session.getTransaction().commit();

    }
}
