package com.alwex.jboy.utils;

import com.alwex.jboy.hardware.CPU;
import com.alwex.jboy.hardware.MEM;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alex
 */
public class DebuggerTest
{

    private static CPU theCpu = CPU.getInstance();
    private static MEM theMemory = MEM.getInstance();

    public DebuggerTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Test
    public void testParse()
    {
        System.out.println("parse");
        theCpu.init();
        theCpu.setRom(theMemory.read("src/main/resources/test.gb"));

        Debugger theDebugger = new Debugger(theCpu.memory);

        theDebugger.parse();
    }
}
