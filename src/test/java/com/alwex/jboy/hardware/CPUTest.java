package com.alwex.jboy.hardware;

import com.alwex.jboy.utils.ByteUtil;
import com.alwex.jboy.utils.Debug;
import com.alwex.jboy.utils.Debugger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.alwex.jboy.utils.ByteUtil.*;

/**
 *
 * @author Alex
 */
public class CPUTest
{

    private static CPU theCpu = CPU.getInstance();
    private static MEM theMemory = MEM.getInstance();
    private static Debug debug = new Debug();

    public CPUTest()
    {
    }

    @Test
    public void testFlagSetUnset()
    {
        theCpu.init();
        theCpu.F = 0;
        theCpu.setF(theCpu.F_C, 1);
        assertEquals("00010000", Debug.toBin(theCpu.F));
        theCpu.setF(theCpu.F_Z, 1);
        assertEquals("10010000", Debug.toBin(theCpu.F));
        assertEquals(1, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_H));
        assertEquals(1, theCpu.getF(theCpu.F_C));

        theCpu.setF(theCpu.F_C, 0);
        assertEquals(0, theCpu.getF(theCpu.F_C));
        assertEquals("10000000", Debug.toBin(theCpu.F));


        System.out.println(Debug.toBin(theCpu.F));
    }

    @Test
    public void testProcessPacmanOpCode()
    {
        System.out.println("=============== PAC MAN ===============");
        /*
        Example code:
        Game: Ms. Pacman
        Address: $3b1
        LD A,$20 <- bit 5 = $20
        LD ($FF00),A <- select P14 by setting it low
        LD A,($FF00)
        LD A,($FF00) <- wait a few cycles
        CPL <- complement A
        AND $0F <- get only first 4 bits
        SWAP A <- swap it
        LD B,A <- store A in B
        LD A,$10
        LD ($FF00),A <- select P15 by setting it low
        LD A,($FF00)
        LD A,($FF00)
        LD A,($FF00)
        LD A,($FF00)
        LD A,($FF00)
        LD A,($FF00) <- Wait a few MORE cycles
        CPL <- complement (invert)
        AND $0F <- get first 4 bits
        OR B <- put A and B together
        LD B,A <- store A in D
        LD A,($FF8B) <- read old joy data from ram
        XOR B <- toggle w/current button bit
        AND B <- get current button bit back
        LD ($FF8C),A <- save in new Joydata storage
        LD A,B <- put original value in A
        LD ($FF8B),A <- store it as old joy data
        LD A,$30 <- deselect P14 and P15
        LD ($FF00),A <- RESET Joypad
        RET <- Return from Subroutine
         */
        theCpu.setRom(theMemory.read("C:\\GAMES\\ds\\gameboyColor\\roms\\Ms. Pac-Man (U) [BF].gb"));
        theCpu.init();
        debug.dumpRom(theCpu.memory, 0x03b1, 0x04b1);
        theCpu.PC = 0x03b1;

        theCpu.processOpCode();
        assertEquals(0x20, theCpu.A);

        theCpu.processOpCode();
        debug.dumpRom(theCpu.memory, 0xFF00, 0xFF10);
        assertEquals(theCpu.A, theCpu.memory[0xFF00]);

        theCpu.processOpCode();
        assertEquals(theCpu.memory[0xFF00], theCpu.A);

        theCpu.processOpCode();
        assertEquals(theCpu.memory[0xFF00], theCpu.A);

        theCpu.processOpCode();
        assertEquals(-33, theCpu.A);

        theCpu.processOpCode();
        assertEquals(0x0F, theCpu.A);

        theCpu.processOpCode();
        assertEquals(0xF0, theCpu.A & 0xFF);
    }

    @Test
    public void processMarioOpcode()
    {
        System.out.println("=============== Mario ===============");

        theCpu.init();
//        theCpu.setRom(theMemory.read("C:\\GAMES\\ds\\gameboyColor\\roms\\Mario_Land.gb"));
        theCpu.setRom(theMemory.read("src/main/resources/test.gb"));

        Debug.dumpRom(theCpu.memory, 0x0000, 0x00ff);
        // NOP
        theCpu.processOpCode();
        assertEquals(0x0101, theCpu.PC);
        // JMP a16
        theCpu.processOpCode();
        assertEquals(0x0150, theCpu.PC);
        // JMP a16
        theCpu.processOpCode();
        assertEquals(0x0185, theCpu.PC);
        // LD A, d8
        theCpu.processOpCode();
        assertEquals(0x03, theCpu.A);
        // DI interruption
        theCpu.processOpCode();
        // LDH (a8), A
        theCpu.processOpCode();
        assertEquals(theCpu.memory[0xFF0F], theCpu.A);
        // LDH (a8), A
        theCpu.processOpCode();
        assertEquals(theCpu.memory[0xFFFF], theCpu.A);
        // LD A, d8
        theCpu.processOpCode();
        assertEquals(0x40, theCpu.A);
        // LDH (a8), A
        theCpu.processOpCode();
        assertEquals(theCpu.A, theCpu.memory[0xFF41]);
        // XOR n,A
        theCpu.processOpCode();
        assertEquals("0", Integer.toBinaryString(theCpu.A & 0xff));

        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();

        // LDH A,(a8)
        theCpu.processOpCode();
        assertEquals(theCpu.A, theCpu.memory[0xFF44]);
        //CP A, d8
        theCpu.processOpCode();
        // JR NZ,r8
        theCpu.processOpCode();
//        assertEquals(0x019F + 0x00FA, theCpu.PC);

        theCpu.processOpCode();

        theCpu.processOpCode();

        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();

//        for (int i = 0; i <= 10; i++)
//        {
//            theCpu.processOpCode();
//        }
    }

    @Test
    public void tetris()
    {
        theCpu.init();
        theCpu.setRom(theMemory.read("C:\\GAMES\\ds\\gameboyColor\\roms\\Tetris.gb"));
        Debug.dumpRom(theCpu.memory, 0x0000, 0x00ff);

        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();

        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
        theCpu.processOpCode();
    }

    @Test
    public void byteCombinationTest()
    {
        byte a, b;

        a = 0x00;
        b = 0x00;

        assertEquals(0x0000, ByteUtil.combine(a, b));

        a = 0x00;
        b = (byte) 0xFF;

        assertEquals(0x00FF, ByteUtil.combine(a, b));

        a = (byte) 0xFF;
        b = (byte) 0xFF;

        assertEquals(0xFFFF, ByteUtil.combine(a, b));

        a = (byte) 0xAB;
        b = (byte) 0xCD;

        assertEquals(0xABCD, ByteUtil.combine(a, b));

        a = (byte) 0xFD;
        b = (byte) 0x05;

        assertEquals(0xFD05, ByteUtil.combine(a, b));

    }
}
