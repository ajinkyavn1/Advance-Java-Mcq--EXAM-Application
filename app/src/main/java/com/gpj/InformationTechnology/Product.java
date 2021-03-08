package com.gpj.InformationTechnology;

public class Product {


    private String name,subject,cs;

    private int enrollment,obtainmarks,wrong;

    public Product(String name, int enrollment, int obtainmarks, String subject, int wrong,String cs) {
        this.name = name;
        this.enrollment = enrollment;
        this.obtainmarks = obtainmarks;
        this.subject = subject;
        this.wrong = wrong;
        this.cs= cs;
    }

    public Product(){

    }


    public String getName() {
        return name;
    }

    public int getEnrollment() {
        return enrollment;
    }

    public int getObtainMarks() {
        return obtainmarks;
    }

    public String getSubject() {
        return subject;
    }

    public int getWrong() {
        return wrong;
    }

    public String getcs(){return cs;}
}
