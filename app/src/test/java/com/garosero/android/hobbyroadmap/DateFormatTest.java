package com.garosero.android.hobbyroadmap;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatTest {
    @Test
    public void format_isCorrect() {
        String s = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        assertEquals("2022/04/29", s);
    }
}