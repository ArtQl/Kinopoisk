package ru.artq.practice.kinopoisk.service.impl;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class UserServiceImplTest {

    @Test
    void recomend() {
        List<Integer> a = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> b = new ArrayList<>(List.of(3, 4, 5));
        List<Integer> c = new ArrayList<>(List.of(1, 3));

        a.removeAll(c);
        b.removeAll(c);
        System.out.println(a);
        System.out.println(b);

    }

}