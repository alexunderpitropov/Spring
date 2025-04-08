package com.alexander.springlibrary.entity;

@Entity
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private List<Long> bookIds = new ArrayList<>();
}
