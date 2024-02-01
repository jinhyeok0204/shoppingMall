package com.example.withJpa2.Controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieForm extends ItemForm{
    private String director;
    private String actor;
    private String genre;
}
