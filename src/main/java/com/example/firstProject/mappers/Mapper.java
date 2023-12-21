package com.example.firstProject.mappers;

public interface Mapper<A, B> {
    B mapTo(A a);
    A mapFrom(B b);
}
