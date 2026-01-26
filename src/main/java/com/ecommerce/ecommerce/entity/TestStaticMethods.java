package com.ecommerce.ecommerce.entity;

public class TestStaticMethods {
    public static void main(String[] args) {
        Test.method1();
        Test1.method1();


    }
}



class Test{
    public static void method1(){
        System.out.println("Parent");
    }
}

class Test1 extends Test{
    public static void method1(){
        System.out.println("Chaild");
    }
}
