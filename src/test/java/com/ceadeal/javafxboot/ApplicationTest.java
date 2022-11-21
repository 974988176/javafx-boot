package com.ceadeal.javafxboot;

import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * @author Ysh
 * @date 2022/11/21
 */
class ApplicationTest {

    @Test
    void main() {
        if (SystemTray.isSupported()) {
            System.out.println("support");
        }else {
            System.out.println("not");
        }
    }
}
