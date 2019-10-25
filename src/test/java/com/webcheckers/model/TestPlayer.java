/***
 * Author: Luyang Lin
 */
package com.webcheckers.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {
    Player p1;

    @BeforeEach
    public void initializeTest(){
        this.p1 = new Player("p1");
    }

    @Test
    public void test_getName(){
        assertEquals("p1",p1.getName());
    }

    @Test
    public void test_toString(){
        assertEquals("p1",this.p1.toString());
    }
}
