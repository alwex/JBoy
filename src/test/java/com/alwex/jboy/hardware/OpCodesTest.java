package com.alwex.jboy.hardware;

import com.alwex.jboy.utils.ByteUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alex
 */
public class OpCodesTest
{

    private static CPU theCpu = CPU.getInstance();
    private static MEM theMemory = MEM.getInstance();

    @Before
    public void initTest()
    {
        theCpu.init();
        theCpu.setRom(theMemory.read("src/main/resources/test.gb"));
    }

    public OpCodesTest()
    {
    }

    @Test
    public void test_0x00_NOP()
    {
        // NOP  1:4  - - - -
        theCpu.memory[0x0100] = 0x00;
        theCpu.processOpCode();
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x01_LD()
    {
        // LD BC,d16  3:12  - - - -
        theCpu.memory[0x0100] = 0x01;
        theCpu.processOpCode();
//        Debug.dumpRom(theCpu.memory, 0x0100, 0x0104);
        assertEquals(0x50C3, ByteUtil.combine(theCpu.B, theCpu.C));
        assertEquals(0x0103, theCpu.PC);
    }

    @Test
    public void test_0x02_LD()
    {
        // LD (BC),A  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x02;
        theCpu.A = 0x66;
        theCpu.B = 0x01;
        theCpu.C = 0x04;

        theCpu.processOpCode();
        assertEquals(0x66, theCpu.memory[0x0104]);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x03_INC()
    {
        // INC BC  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x03;
        theCpu.B = 0x01;
        theCpu.C = 0x04;

        theCpu.processOpCode();
        assertEquals(0x0105, ByteUtil.combine(theCpu.B, theCpu.C));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x04_INC()
    {
        // INC B  1:4  Z 0 H -
        theCpu.memory[0x0100] = (byte) 0x04;
        theCpu.B = 0x00;
        theCpu.processOpCode();
        assertEquals(0x01, theCpu.B);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x05_DEC()
    {
        // DEC B  1:4  Z 1 H -
        theCpu.memory[0x0100] = (byte) 0x05;

        // > 0
        theCpu.B = 0x02;
        theCpu.processOpCode();
        assertEquals(0x01, theCpu.B);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.B = 0x01;
        theCpu.processOpCode();
        assertEquals(0x00, theCpu.B);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(1, theCpu.getF(theCpu.F_Z));

        // < 0
        theCpu.init();
        theCpu.B = 0x00;
        theCpu.processOpCode();
        assertEquals((byte) 0xFF, theCpu.B);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
    }

    @Test
    public void test_0x06_LD()
    {
        // LD B,d8  2:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x06;
        theCpu.memory[0x0101] = (byte) 0x12;
        theCpu.B = 0x00;
        theCpu.processOpCode();

        assertEquals(0x12, theCpu.B);
        assertEquals(0x0102, theCpu.PC);
    }

    @Test
    public void test_0x07_RLCA()
    {
        // RLCA  1:4  0 0 0 C
        theCpu.memory[0x0100] = (byte) 0x07;
        assertTrue(false);
    }

    @Test
    public void test_0x08_LD()
    {
        // LD (a16),SP  3:20  - - - -
        theCpu.memory[0x0100] = (byte) 0x08;
        theCpu.memory[0x0101] = (byte) 0x20;
        theCpu.memory[0x0102] = (byte) 0x40;
        theCpu.SP = 0x0011;
        theCpu.processOpCode();

        assertEquals(0x0011, theCpu.memory[0x4020]);
        assertEquals(0x0103, theCpu.PC);
    }

    @Test
    public void test_0x09_ADD()
    {
        // ADD HL,BC  1:8  - 0 H C
        theCpu.memory[0x0100] = (byte) 0x09;
        theCpu.H = 0x10;
        theCpu.L = 0x01;
        theCpu.B = 0x20;
        theCpu.C = 0x02;

        assertEquals(0x3003, ByteUtil.combine(theCpu.H, theCpu.L));
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);
        assertTrue(false);
    }

    @Test
    public void test_0x0A_LD()
    {
        // LD A,(BC)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x0A;
        theCpu.memory[0x0105] = 0x3E;
        theCpu.B = 0x01;
        theCpu.C = 0x05;
        theCpu.processOpCode();

        assertEquals(0x3E, theCpu.A);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x0B_DEC()
    {
        // DEC BC  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x0B;
        theCpu.B = 0x12; // 0x1234
        theCpu.C = 0x00;
        theCpu.processOpCode();

        assertEquals(0x11, theCpu.B);
        assertEquals((byte) 0xFF, theCpu.C);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x0C_INC()
    {
        // INC C  1:4  Z 0 H -
        // > 0
        theCpu.memory[0x0100] = (byte) 0x0C;
        theCpu.C = 0x00;
        theCpu.processOpCode();

        assertEquals(0x01, theCpu.C);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.memory[0x0100] = (byte) 0x0C;
        theCpu.C = (byte) 0xFF;
        theCpu.processOpCode();

        assertEquals(0x00, theCpu.C);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x0D_DEC()
    {
        // DEC C  1:4  Z 1 H -
        theCpu.memory[0x0100] = (byte) 0x0D;

        // > 0
        theCpu.C = 0x02;
        theCpu.processOpCode();
        assertEquals(0x01, theCpu.C);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.C = 0x01;
        theCpu.processOpCode();
        assertEquals(0x00, theCpu.C);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(1, theCpu.getF(theCpu.F_Z));
        assertEquals(0x0101, theCpu.PC);

        // < 0
        theCpu.init();
        theCpu.C = 0x00;
        theCpu.processOpCode();
        assertEquals((byte) 0xFF, theCpu.C);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x0E_LD()
    {
        // LD C,d8  2:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x0E;
        theCpu.memory[0x0101] = 0x0A;
        theCpu.C = 0x00;
        theCpu.processOpCode();

        assertEquals(0x0A, theCpu.C);
        assertEquals(0x0102, theCpu.PC);
    }

    @Test
    public void test_0x0F_RRCA()
    {
        // RRCA  1:4  0 0 0 C
        theCpu.memory[0x0100] = (byte) 0x0F;
        assertTrue(false);
    }

    @Test
    public void test_0x10_STOP()
    {
        // STOP 0  2:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x10;
        assertTrue(false);
    }

    @Test
    public void test_0x11_LD()
    {
        // LD DE,d16  3:12  - - - -
        theCpu.memory[0x0100] = (byte) 0x11;
        theCpu.memory[0x0101] = 0x02;
        theCpu.memory[0x0102] = 0x01;
        theCpu.D = 0x00;
        theCpu.E = 0x00;
        theCpu.processOpCode();

        assertEquals(0x01, theCpu.D);
        assertEquals(0x02, theCpu.E);
        assertEquals(0x0103, theCpu.PC);
    }

    @Test
    public void test_0x12_LD()
    {
        // LD (DE),A  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x12;
        theCpu.A = 0x11;
        theCpu.D = 0x11;
        theCpu.E = 0x22;
        theCpu.processOpCode();

        assertEquals(0x11, theCpu.memory[0x1122]);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x13_INC()
    {
        // INC DE  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x13;

        theCpu.D = 0x01;
        theCpu.E = 0x04;

        theCpu.processOpCode();
        assertEquals(0x0105, ByteUtil.combine(theCpu.D, theCpu.E));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x14_INC()
    {
        // INC D  1:4  Z 0 H -
        theCpu.memory[0x0100] = (byte) 0x14;

        // > 0
        theCpu.memory[0x0100] = (byte) 0x0C;
        theCpu.D = 0x00;
        theCpu.processOpCode();

        assertEquals(0x01, theCpu.D);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.memory[0x0100] = (byte) 0x0C;
        theCpu.D = (byte) 0xFF;
        theCpu.processOpCode();

        assertEquals(0x00, theCpu.D);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x15_DEC()
    {
        // DEC D  1:4  Z 1 H -
        theCpu.memory[0x0100] = (byte) 0x15;

        // > 0
        theCpu.D = 0x02;
        theCpu.processOpCode();
        assertEquals(0x01, theCpu.D);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.D = 0x01;
        theCpu.processOpCode();
        assertEquals(0x00, theCpu.D);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(1, theCpu.getF(theCpu.F_Z));

        // < 0
        theCpu.init();
        theCpu.D = 0x00;
        theCpu.processOpCode();
        assertEquals((byte) 0xFF, theCpu.D);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
    }

    @Test
    public void test_0x16_LD()
    {
        // LD D,d8  2:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x16;
        theCpu.memory[0x0101] = 0x0A;
        theCpu.D = 0x00;
        theCpu.processOpCode();

        assertEquals(0x0A, theCpu.D);
        assertEquals(0x0102, theCpu.PC);
    }

    @Test
    public void test_0x17_RLA()
    {
        // RLA  1:4  0 0 0 C
        theCpu.memory[0x0100] = (byte) 0x17;
        assertTrue(false);
    }

    @Test
    public void test_0x18_JR()
    {
        // JR r8  2:12  - - - -
        theCpu.memory[0x0100] = (byte) 0x18;
        theCpu.memory[0x0101] = 0x33;
        theCpu.processOpCode();

        assertEquals(0x0133, theCpu.PC);
    }

    @Test
    public void test_0x19_ADD()
    {
        // ADD HL,DE  1:8  - 0 H C
        theCpu.memory[0x0100] = (byte) 0x19;
        theCpu.H = 0x01;
        theCpu.L = 0x02;
        theCpu.D = 0x03;
        theCpu.E = 0x04;
        theCpu.processOpCode();

        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x04, theCpu.H);
        assertEquals(0x06, theCpu.L);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x1A_LD()
    {
        // LD A,(DE)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x1A;
        theCpu.D = 0x01;
        theCpu.E = 0x02;
        theCpu.memory[0x0102] = 0x55;
        theCpu.A = 0x00;
        theCpu.processOpCode();

        assertEquals(0x55, theCpu.A);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x1B_DEC()
    {
        // DEC DE  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x1B;
        theCpu.D = 0x10;
        theCpu.E = 0x00;
        theCpu.processOpCode();

        assertEquals(0x09, theCpu.D);
        assertEquals((byte) 0xFF, theCpu.E);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x1C_INC()
    {
        // INC E  1:4  Z 0 H -
        theCpu.memory[0x0100] = (byte) 0x1C;
        theCpu.E = 0x01;
        theCpu.processOpCode();

        // > 0
        theCpu.init();
        assertEquals(0x02, theCpu.E);
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.E = (byte) 0xFF;
        theCpu.processOpCode();

        assertEquals(0x00, theCpu.E);
        assertEquals(1, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);

        // < 0
        theCpu.init();
        theCpu.E = 0x00;
        theCpu.processOpCode();

        assertEquals((byte) 0xFF, theCpu.E);
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x1D_DEC()
    {
        // DEC E  1:4  Z 1 H -
        theCpu.memory[0x0100] = (byte) 0x1D;

        // > 0
        theCpu.E = 0x02;
        theCpu.processOpCode();
        assertEquals(0x01, theCpu.E);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.E = 0x01;
        theCpu.processOpCode();
        assertEquals(0x00, theCpu.E);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(1, theCpu.getF(theCpu.F_Z));

        // < 0
        theCpu.init();
        theCpu.E = 0x00;
        theCpu.processOpCode();
        assertEquals((byte) 0xFF, theCpu.E);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
    }

    @Test
    public void test_0x1E_LD()
    {
        // LD E,d8  2:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x1E;
        theCpu.memory[0x0101] = 0x0A;
        theCpu.E = 0x00;
        theCpu.processOpCode();

        assertEquals(0x0A, theCpu.E);
        assertEquals(0x0102, theCpu.PC);
    }

    @Test
    public void test_0x1F_RRA()
    {
        // RRA  1:4  0 0 0 C
        theCpu.memory[0x0100] = (byte) 0x1F;
        assertTrue(false);
    }

    @Test
    public void test_0x20_JR()
    {
        // JR NZ,r8  2:12/8  - - - -
        theCpu.memory[0x0100] = (byte) 0x20;
        theCpu.memory[0x0101] = 0x05;
        
        // F_Z = 0
        theCpu.setF(theCpu.F_Z, 0);
        theCpu.processOpCode();
        assertEquals(0x0107, theCpu.PC);
        
        // F_Z = 1
        theCpu.init();
        theCpu.setF(theCpu.F_Z, 1);
        theCpu.processOpCode();
        assertEquals(0x0102, theCpu.PC);
    }

    @Test
    public void test_0x21_LD()
    {
        // LD HL,d16  3:12  - - - -
        theCpu.memory[0x0100] = (byte) 0x21;

        theCpu.memory[0x0101] = 0x02;
        theCpu.memory[0x0102] = 0x01;
        theCpu.H = 0x00;
        theCpu.L = 0x00;
        theCpu.processOpCode();

        assertEquals(0x01, theCpu.H);
        assertEquals(0x02, theCpu.L);
        assertEquals(0x0103, theCpu.PC);

        assertTrue(false);
    }

    @Test
    public void test_0x22_LD()
    {
        // LD (HL+),A  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x22;
        assertTrue(false);
    }

    @Test
    public void test_0x23_INC()
    {
        // INC HL  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x23;
        theCpu.H = 0x01;
        theCpu.L = 0x04;
        theCpu.processOpCode();
        
        assertEquals(0x0105, ByteUtil.combine(theCpu.H, theCpu.L));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x24_INC()
    {
        // INC H  1:4  Z 0 H -
        theCpu.memory[0x0100] = (byte) 0x24;

        theCpu.H = 0x01;
        theCpu.processOpCode();

        // > 0
        theCpu.init();
        assertEquals(0x02, theCpu.H);
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.H = (byte) 0xFF;
        theCpu.processOpCode();

        assertEquals(0x00, theCpu.H);
        assertEquals(1, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);

        // < 0
        theCpu.init();
        theCpu.H = 0x00;
        theCpu.processOpCode();

        assertEquals((byte) 0xFF, theCpu.H);
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x25_DEC()
    {
        // DEC H  1:4  Z 1 H -
        theCpu.memory[0x0100] = (byte) 0x25;

        // > 0
        theCpu.H = 0x02;
        theCpu.processOpCode();
        assertEquals(0x01, theCpu.H);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.H = 0x01;
        theCpu.processOpCode();
        assertEquals(0x00, theCpu.H);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(1, theCpu.getF(theCpu.F_Z));

        // < 0
        theCpu.init();
        theCpu.H = 0x00;
        theCpu.processOpCode();
        assertEquals((byte) 0xFF, theCpu.H);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
    }

    @Test
    public void test_0x26_LD()
    {
        // LD H,d8  2:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x26;
        theCpu.memory[0x0101] = 0x0A;
        theCpu.H = 0x00;
        theCpu.processOpCode();

        assertEquals(0x0A, theCpu.H);
        assertEquals(0x0102, theCpu.PC);
    }

    @Test
    public void test_0x27_DAA()
    {
        // DAA  1:4  Z - 0 C
        theCpu.memory[0x0100] = (byte) 0x27;
        assertTrue(false);
    }

    @Test
    public void test_0x28_JR()
    {
        // JR Z,r8  2:12/8  - - - -
        theCpu.memory[0x0100] = (byte) 0x28;
        theCpu.memory[0x0101] = 0x05;

        // == 0
        theCpu.setF(theCpu.F_Z, 1);
        theCpu.processOpCode();
        assertEquals(0x0107, theCpu.PC);

        // <> 0
        theCpu.init();
        theCpu.setF(theCpu.F_Z, 0);
        theCpu.processOpCode();
        assertEquals(0x0102, theCpu.PC);
    }

    @Test
    public void test_0x29_ADD()
    {
        // ADD HL,HL  1:8  - 0 H C
        theCpu.memory[0x0100] = (byte) 0x29;

        theCpu.H = 0x01;
        theCpu.L = 0x02;
        theCpu.processOpCode();

        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x02, theCpu.H);
        assertEquals(0x04, theCpu.L);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x2A_LD()
    {
        // LD A,(HL+)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x2A;
        assertTrue(false);
    }

    @Test
    public void test_0x2B_DEC()
    {
        // DEC HL  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x2B;

        theCpu.H = 0x10;
        theCpu.L = 0x00;
        theCpu.processOpCode();

        assertEquals(0x09, theCpu.H);
        assertEquals((byte) 0xFF, theCpu.L);
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x2C_INC()
    {
        // INC L  1:4  Z 0 H -
        theCpu.memory[0x0100] = (byte) 0x2C;

        theCpu.L = 0x01;
        theCpu.processOpCode();

        // > 0
        assertEquals(0x02, theCpu.L);
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.L = (byte) 0xFF;
        theCpu.processOpCode();

        assertEquals(0x00, theCpu.L);
        assertEquals(1, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);

        // < 0
        theCpu.init();
        theCpu.L = 0x00;
        theCpu.processOpCode();

        assertEquals((byte) 0xFF, theCpu.L);
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x2D_DEC()
    {
        // DEC L  1:4  Z 1 H -
        theCpu.memory[0x0100] = (byte) 0x2D;

        // > 0
        theCpu.L = 0x02;
        theCpu.processOpCode();
        assertEquals(0x01, theCpu.L);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
        assertEquals(0x0101, theCpu.PC);

        // == 0
        theCpu.init();
        theCpu.L = 0x01;
        theCpu.processOpCode();
        assertEquals(0x00, theCpu.L);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(1, theCpu.getF(theCpu.F_Z));

        // < 0
        theCpu.init();
        theCpu.L = 0x00;
        theCpu.processOpCode();
        assertEquals((byte) 0xFF, theCpu.L);
        assertEquals(0x0101, theCpu.PC);
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0, theCpu.getF(theCpu.F_Z));
    }

    @Test
    public void test_0x2E_LD()
    {
        // LD L,d8  2:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x2E;
        theCpu.memory[0x0101] = 0x0A;
        theCpu.L = 0x00;
        theCpu.processOpCode();

        assertEquals(0x0A, theCpu.L);
        assertEquals(0x0102, theCpu.PC);
    }

    @Test
    public void test_0x2F_CPL()
    {
        // CPL  1:4  - 1 1 -
        theCpu.memory[0x0100] = (byte) 0x2F;
        theCpu.A = 0x11;
        theCpu.setF(theCpu.F_N, 0);
        theCpu.setF(theCpu.F_H, 0);
        theCpu.processOpCode();

        assertEquals((byte) ~0x11, theCpu.A);
        assertEquals(1, theCpu.getF(theCpu.F_H));
        assertEquals(1, theCpu.getF(theCpu.F_N));
        assertEquals(0x0101, theCpu.PC);
    }

    @Test
    public void test_0x30_JR()
    {
        // JR NC,r8  2:12/8  - - - -
        theCpu.memory[0x0100] = (byte) 0x30;
        theCpu.memory[0x0101] = 0x05;

        // F_C = 0 jump
        theCpu.setF(theCpu.F_C, 0);
        theCpu.processOpCode();
        assertEquals(0x0107, theCpu.PC);

        // F_C = 1 no nump
        theCpu.init();
        theCpu.setF(theCpu.F_C, 1);
        theCpu.processOpCode();
        assertEquals(0x0102, theCpu.PC);

    }

    @Test
    public void test_0x31_LD()
    {
        // LD SP,d16  3:12  - - - -
        theCpu.memory[0x0100] = (byte) 0x31;
        assertTrue(false);
    }

    @Test
    public void test_0x32_LD()
    {
        // LD (HL-),A  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x32;
        assertTrue(false);
    }

    @Test
    public void test_0x33_INC()
    {
        // INC SP  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x33;
        assertTrue(false);
    }

    @Test
    public void test_0x34_INC()
    {
        // INC (HL)  1:12  Z 0 H -
        theCpu.memory[0x0100] = (byte) 0x34;
        assertTrue(false);
    }

    @Test
    public void test_0x35_DEC()
    {
        // DEC (HL)  1:12  Z 1 H -
        theCpu.memory[0x0100] = (byte) 0x35;
        assertTrue(false);
    }

    @Test
    public void test_0x36_LD()
    {
        // LD (HL),d8  2:12  - - - -
        theCpu.memory[0x0100] = (byte) 0x36;
        assertTrue(false);
    }

    @Test
    public void test_0x37_SCF()
    {
        // SCF  1:4  - 0 0 1
        theCpu.memory[0x0100] = (byte) 0x37;
        assertTrue(false);
    }

    @Test
    public void test_0x38_JR()
    {
        // JR C,r8  2:12/8  - - - -
        theCpu.memory[0x0100] = (byte) 0x38;
        theCpu.memory[0x0101] = 0x05;

        // F_C = 1 jump
        theCpu.setF(theCpu.F_C, 1);
        theCpu.processOpCode();
        assertEquals(0x0107, theCpu.PC);

        // F_C = 0 no nump
        theCpu.init();
        theCpu.setF(theCpu.F_C, 0);
        theCpu.processOpCode();
        assertEquals(0x0102, theCpu.PC);
    }

    @Test
    public void test_0x39_ADD()
    {
        // ADD HL,SP  1:8  - 0 H C
        theCpu.memory[0x0100] = (byte) 0x39;
        assertTrue(false);
    }

    @Test
    public void test_0x3A_LD()
    {
        // LD A,(HL-)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x3A;
        assertTrue(false);
    }

    @Test
    public void test_0x3B_DEC()
    {
        // DEC SP  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x3B;
        assertTrue(false);
    }

    @Test
    public void test_0x3C_INC()
    {
        // INC A  1:4  Z 0 H -
        theCpu.memory[0x0100] = (byte) 0x3C;
        assertTrue(false);
    }

    @Test
    public void test_0x3D_DEC()
    {
        // DEC A  1:4  Z 1 H -
        theCpu.memory[0x0100] = (byte) 0x3D;
        assertTrue(false);
    }

    @Test
    public void test_0x3E_LD()
    {
        // LD A,d8  2:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x3E;
        assertTrue(false);
    }

    @Test
    public void test_0x3F_CCF()
    {
        // CCF  1:4  - 0 0 C
        theCpu.memory[0x0100] = (byte) 0x3F;
        assertTrue(false);
    }

    @Test
    public void test_0x40_LD()
    {
        // LD B,B  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x40;
        assertTrue(false);
    }

    @Test
    public void test_0x41_LD()
    {
        // LD B,C  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x41;
        assertTrue(false);
    }

    @Test
    public void test_0x42_LD()
    {
        // LD B,D  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x42;
        assertTrue(false);
    }

    @Test
    public void test_0x43_LD()
    {
        // LD B,E  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x43;
        assertTrue(false);
    }

    @Test
    public void test_0x44_LD()
    {
        // LD B,H  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x44;
        assertTrue(false);
    }

    @Test
    public void test_0x45_LD()
    {
        // LD B,L  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x45;
        assertTrue(false);
    }

    @Test
    public void test_0x46_LD()
    {
        // LD B,(HL)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x46;
        assertTrue(false);
    }

    @Test
    public void test_0x47_LD()
    {
        // LD B,A  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x47;
        assertTrue(false);
    }

    @Test
    public void test_0x48_LD()
    {
        // LD C,B  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x48;
        assertTrue(false);
    }

    @Test
    public void test_0x49_LD()
    {
        // LD C,C  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x49;
        assertTrue(false);
    }

    @Test
    public void test_0x4A_LD()
    {
        // LD C,D  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x4A;
        assertTrue(false);
    }

    @Test
    public void test_0x4B_LD()
    {
        // LD C,E  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x4B;
        assertTrue(false);
    }

    @Test
    public void test_0x4C_LD()
    {
        // LD C,H  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x4C;
        assertTrue(false);
    }

    @Test
    public void test_0x4D_LD()
    {
        // LD C,L  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x4D;
        assertTrue(false);
    }

    @Test
    public void test_0x4E_LD()
    {
        // LD C,(HL)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x4E;
        assertTrue(false);
    }

    @Test
    public void test_0x4F_LD()
    {
        // LD C,A  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x4F;
        assertTrue(false);
    }

    @Test
    public void test_0x50_LD()
    {
        // LD D,B  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x50;
        assertTrue(false);
    }

    @Test
    public void test_0x51_LD()
    {
        // LD D,C  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x51;
        assertTrue(false);
    }

    @Test
    public void test_0x52_LD()
    {
        // LD D,D  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x52;
        assertTrue(false);
    }

    @Test
    public void test_0x53_LD()
    {
        // LD D,E  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x53;
        assertTrue(false);
    }

    @Test
    public void test_0x54_LD()
    {
        // LD D,H  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x54;
        assertTrue(false);
    }

    @Test
    public void test_0x55_LD()
    {
        // LD D,L  1:4  - - - -
        // LD D,L  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x55;
        assertTrue(false);
    }

    @Test
    public void test_0x56_LD()
    {
        // LD D,(HL)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x56;
        assertTrue(false);
    }

    @Test
    public void test_0x57_LD()
    {
        // LD D,A  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x57;
        assertTrue(false);
    }

    @Test
    public void test_0x58_LD()
    {
        // LD E,B  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x58;
        assertTrue(false);
    }

    @Test
    public void test_0x59_LD()
    {
        // LD E,C  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x59;
        assertTrue(false);
    }

    @Test
    public void test_0x5A_LD()
    {
        // LD E,D  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x5A;
        assertTrue(false);
    }

    @Test
    public void test_0x5B_LD()
    {
        // LD E,E  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x5B;
        assertTrue(false);
    }

    @Test
    public void test_0x5C_LD()
    {
        // LD E,H  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x5C;
        assertTrue(false);
    }

    @Test
    public void test_0x5D_LD()
    {
        // LD E,L  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x5D;
        assertTrue(false);
    }

    @Test
    public void test_0x5E_LD()
    {
        // LD E,(HL)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x5E;
        assertTrue(false);
    }

    @Test
    public void test_0x5F_LD()
    {
        // LD E,A  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x5F;
        assertTrue(false);
    }

    @Test
    public void test_0x60_LD()
    {
        // LD H,B  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x60;
        assertTrue(false);
    }

    @Test
    public void test_0x61_LD()
    {
        // LD H,C  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x61;
        assertTrue(false);
    }

    @Test
    public void test_0x62_LD()
    {
        // LD H,D  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x62;
        assertTrue(false);
    }

    @Test
    public void test_0x63_LD()
    {
        // LD H,E  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x63;
        assertTrue(false);
    }

    @Test
    public void test_0x64_LD()
    {
        // LD H,H  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x64;
        assertTrue(false);
    }

    @Test
    public void test_0x65_LD()
    {
        // LD H,L  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x65;
        assertTrue(false);
    }

    @Test
    public void test_0x66_LD()
    {
        // LD H,(HL)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x66;
        assertTrue(false);
    }

    @Test
    public void test_0x67_LD()
    {
        // LD H,A  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x67;
        assertTrue(false);
    }

    @Test
    public void test_0x68_LD()
    {
        // LD L,B  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x68;
        assertTrue(false);
    }

    @Test
    public void test_0x69_LD()
    {
        // LD L,C  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x69;
        assertTrue(false);
    }

    @Test
    public void test_0x6A_LD()
    {
        // LD L,D  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x6A;
        assertTrue(false);
    }

    @Test
    public void test_0x6B_LD()
    {
        // LD L,E  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x6B;
        assertTrue(false);
    }

    @Test
    public void test_0x6C_LD()
    {
        // LD L,H  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x6C;
        assertTrue(false);
    }

    @Test
    public void test_0x6D_LD()
    {
        // LD L,L  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x6D;
        assertTrue(false);
    }

    @Test
    public void test_0x6E_LD()
    {
        // LD L,(HL)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x6E;
        assertTrue(false);
    }

    @Test
    public void test_0x6F_LD()
    {
        // LD L,A  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x6F;
        assertTrue(false);
    }

    @Test
    public void test_0x70_LD()
    {
        // LD (HL),B  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x70;
        assertTrue(false);
    }

    @Test
    public void test_0x71_LD()
    {
        // LD (HL),C  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x71;
        assertTrue(false);
    }

    @Test
    public void test_0x72_LD()
    {
        // LD (HL),D  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x72;
        assertTrue(false);
    }

    @Test
    public void test_0x73_LD()
    {
        // LD (HL),E  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x73;
        assertTrue(false);
    }

    @Test
    public void test_0x74_LD()
    {
        // LD (HL),H  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x74;
        assertTrue(false);
    }

    @Test
    public void test_0x75_LD()
    {
        // LD (HL),L  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x75;
        assertTrue(false);
    }

    @Test
    public void test_0x76_HALT()
    {
        // HALT  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x76;
        assertTrue(false);
    }

    @Test
    public void test_0x77_LD()
    {
        // LD (HL),A  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x77;
        assertTrue(false);
    }

    @Test
    public void test_0x78_LD()
    {
        // LD A,B  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x78;
        assertTrue(false);
    }

    @Test
    public void test_0x79_LD()
    {
        // LD A,C  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x79;
        assertTrue(false);
    }

    @Test
    public void test_0x7A_LD()
    {
        // LD A,D  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x7A;
        assertTrue(false);
    }

    @Test
    public void test_0x7B_LD()
    {
        // LD A,E  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x7B;
        assertTrue(false);
    }

    @Test
    public void test_0x7C_LD()
    {
        // LD A,H  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x7C;
        assertTrue(false);
    }

    @Test
    public void test_0x7D_LD()
    {
        // LD A,L  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x7D;
        assertTrue(false);
    }

    @Test
    public void test_0x7E_LD()
    {
        // LD A,(HL)  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0x7E;
        assertTrue(false);
    }

    @Test
    public void test_0x7F_LD()
    {
        // LD A,A  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0x7F;
        assertTrue(false);
    }

    @Test
    public void test_0x80_ADD()
    {
        // ADD A,B  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x80;
        assertTrue(false);
    }

    @Test
    public void test_0x81_ADD()
    {
        // ADD A,C  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x81;
        assertTrue(false);
    }

    @Test
    public void test_0x82_ADD()
    {
        // ADD A,D  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x82;
        assertTrue(false);
    }

    @Test
    public void test_0x83_ADD()
    {
        // ADD A,E  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x83;
        assertTrue(false);
    }

    @Test
    public void test_0x84_ADD()
    {
        // ADD A,H  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x84;
        assertTrue(false);
    }

    @Test
    public void test_0x85_ADD()
    {
        // ADD A,L  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x85;
        assertTrue(false);
    }

    @Test
    public void test_0x86_ADD()
    {
        // ADD A,(HL)  1:8  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x86;
        assertTrue(false);
    }

    @Test
    public void test_0x87_ADD()
    {
        // ADD A,A  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x87;
        assertTrue(false);
    }

    @Test
    public void test_0x88_ADC()
    {
        // ADC A,B  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x88;
        assertTrue(false);
    }

    @Test
    public void test_0x89_ADC()
    {
        // ADC A,C  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x89;
        assertTrue(false);
    }

    @Test
    public void test_0x8A_ADC()
    {
        // ADC A,D  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x8A;
        assertTrue(false);
    }

    @Test
    public void test_0x8B_ADC()
    {
        // ADC A,E  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x8B;
        assertTrue(false);
    }

    @Test
    public void test_0x8C_ADC()
    {
        // ADC A,H  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x8C;
        assertTrue(false);
    }

    @Test
    public void test_0x8D_ADC()
    {
        // ADC A,L  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x8D;
        assertTrue(false);
    }

    @Test
    public void test_0x8E_ADC()
    {
        // ADC A,(HL)  1:8  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x8E;
        assertTrue(false);
    }

    @Test
    public void test_0x8F_ADC()
    {
        // ADC A,A  1:4  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0x8F;
        assertTrue(false);
    }

    @Test
    public void test_0x90_SUB()
    {
        // SUB B  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x90;
        assertTrue(false);
    }

    @Test
    public void test_0x91_SUB()
    {
        // SUB C  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x91;
        assertTrue(false);
    }

    @Test
    public void test_0x92_SUB()
    {
        // SUB D  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x92;
        assertTrue(false);
    }

    @Test
    public void test_0x93_SUB()
    {
        // SUB E  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x93;
        assertTrue(false);
    }

    @Test
    public void test_0x94_SUB()
    {
        // SUB H  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x94;
        assertTrue(false);
    }

    @Test
    public void test_0x95_SUB()
    {
        // SUB L  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x95;
        assertTrue(false);
    }

    @Test
    public void test_0x96_SUB()
    {
        // SUB (HL)  1:8  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x96;
        assertTrue(false);
    }

    @Test
    public void test_0x97_SUB()
    {
        // SUB A  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x97;
        assertTrue(false);
    }

    @Test
    public void test_0x98_SBC()
    {
        // SBC A,B  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x98;
        assertTrue(false);
    }

    @Test
    public void test_0x99_SBC()
    {
        // SBC A,C  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x99;
        assertTrue(false);
    }

    @Test
    public void test_0x9A_SBC()
    {
        // SBC A,D  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x9A;
        assertTrue(false);
    }

    @Test
    public void test_0x9B_SBC()
    {
        // SBC A,E  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x9B;
        assertTrue(false);
    }

    @Test
    public void test_0x9C_SBC()
    {
        // SBC A,H  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x9C;
        assertTrue(false);
    }

    @Test
    public void test_0x9D_SBC()
    {
        // SBC A,L  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x9D;
        assertTrue(false);
    }

    @Test
    public void test_0x9E_SBC()
    {
        // SBC A,(HL)  1:8  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x9E;
        assertTrue(false);
    }

    @Test
    public void test_0x9F_SBC()
    {
        // SBC A,A  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0x9F;
        assertTrue(false);
    }

    @Test
    public void test_0xA0_AND()
    {
        // AND B  1:4  Z 0 1 0
        theCpu.memory[0x0100] = (byte) 0xA0;
        assertTrue(false);
    }

    @Test
    public void test_0xA1_AND()
    {
        // AND C  1:4  Z 0 1 0
        theCpu.memory[0x0100] = (byte) 0xA1;
        assertTrue(false);
    }

    @Test
    public void test_0xA2_AND()
    {
        // AND D  1:4  Z 0 1 0
        theCpu.memory[0x0100] = (byte) 0xA2;
        assertTrue(false);
    }

    @Test
    public void test_0xA3_AND()
    {
        // AND E  1:4  Z 0 1 0
        theCpu.memory[0x0100] = (byte) 0xA3;
        assertTrue(false);
    }

    @Test
    public void test_0xA4_AND()
    {
        // AND H  1:4  Z 0 1 0
        theCpu.memory[0x0100] = (byte) 0xA4;
        assertTrue(false);
    }

    @Test
    public void test_0xA5_AND()
    {
        // AND L  1:4  Z 0 1 0
        theCpu.memory[0x0100] = (byte) 0xA5;
        assertTrue(false);
    }

    @Test
    public void test_0xA6_AND()
    {
        // AND (HL)  1:8  Z 0 1 0
        theCpu.memory[0x0100] = (byte) 0xA6;
        assertTrue(false);
    }

    @Test
    public void test_0xA7_AND()
    {
        // AND A  1:4  Z 0 1 0
        theCpu.memory[0x0100] = (byte) 0xA7;
        assertTrue(false);
    }

    @Test
    public void test_0xA8_XOR()
    {
        // XOR B  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xA8;
        assertTrue(false);
    }

    @Test
    public void test_0xA9_XOR()
    {
        // XOR C  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xA9;
        assertTrue(false);
    }

    @Test
    public void test_0xAA_XOR()
    {
        // XOR D  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xAA;
        assertTrue(false);
    }

    @Test
    public void test_0xAB_XOR()
    {
        // XOR E  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xAB;
        assertTrue(false);
    }

    @Test
    public void test_0xAC_XOR()
    {
        // XOR H  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xAC;
        assertTrue(false);
    }

    @Test
    public void test_0xAD_XOR()
    {
        // XOR L  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xAD;
        assertTrue(false);
    }

    @Test
    public void test_0xAE_XOR()
    {
        // XOR (HL)  1:8  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xAE;
        assertTrue(false);
    }

    @Test
    public void test_0xAF_XOR()
    {
        // XOR A  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xAF;
        assertTrue(false);
    }

    @Test
    public void test_0xB0_OR()
    {
        // OR B  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xB0;
        assertTrue(false);
    }

    @Test
    public void test_0xB1_OR()
    {
        // OR C  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xB1;
        assertTrue(false);
    }

    @Test
    public void test_0xB2_OR()
    {
        // OR D  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xB2;
        assertTrue(false);
    }

    @Test
    public void test_0xB3_OR()
    {
        // OR E  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xB3;
        assertTrue(false);
    }

    @Test
    public void test_0xB4_OR()
    {
        // OR H  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xB4;
        assertTrue(false);
    }

    @Test
    public void test_0xB5_OR()
    {
        // OR L  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xB5;
        assertTrue(false);
    }

    @Test
    public void test_0xB6_OR()
    {
        // OR (HL)  1:8  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xB6;
        assertTrue(false);
    }

    @Test
    public void test_0xB7_OR()
    {
        // OR A  1:4  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xB7;
        assertTrue(false);
    }

    @Test
    public void test_0xB8_CP()
    {
        // CP B  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xB8;
        assertTrue(false);
    }

    @Test
    public void test_0xB9_CP()
    {
        // CP C  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xB9;
        assertTrue(false);
    }

    @Test
    public void test_0xBA_CP()
    {
        // CP D  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xBA;
        assertTrue(false);
    }

    @Test
    public void test_0xBB_CP()
    {
        // CP E  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xBB;
        assertTrue(false);
    }

    @Test
    public void test_0xBC_CP()
    {
        // CP H  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xBC;
        assertTrue(false);
    }

    @Test
    public void test_0xBD_CP()
    {
        // CP L  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xBD;
        assertTrue(false);
    }

    @Test
    public void test_0xBE_CP()
    {
        // CP (HL)  1:8  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xBE;
        assertTrue(false);
    }

    @Test
    public void test_0xBF_CP()
    {
        // CP A  1:4  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xBF;
        assertTrue(false);
    }

    @Test
    public void test_0xC0_RET()
    {
        // RET NZ  1:20/8  - - - -
        theCpu.memory[0x0100] = (byte) 0xC0;
        assertTrue(false);
    }

    @Test
    public void test_0xC1_POP()
    {
        // POP BC  1:12  - - - -
        theCpu.memory[0x0100] = (byte) 0xC1;
        assertTrue(false);
    }

    @Test
    public void test_0xC2_JP()
    {
        // JP NZ,a16  3:16/12  - - - -
        theCpu.memory[0x0100] = (byte) 0xC2;
        assertTrue(false);
    }

    @Test
    public void test_0xC3_JP()
    {
        // JP a16  3:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xC3;
        assertTrue(false);
    }

    @Test
    public void test_0xC4_CALL()
    {
        // CALL NZ,a16  3:24/12  - - - -
        theCpu.memory[0x0100] = (byte) 0xC4;
        assertTrue(false);
    }

    @Test
    public void test_0xC5_PUSH()
    {
        // PUSH BC  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xC5;
        assertTrue(false);
    }

    @Test
    public void test_0xC6_ADD()
    {
        // ADD A,d8  2:8  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0xC6;
        assertTrue(false);
    }

    @Test
    public void test_0xC7_RST()
    {
        // RST 00H  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xC7;
        assertTrue(false);
    }

    @Test
    public void test_0xC8_RET()
    {
        // RET Z  1:20/8  - - - -
        theCpu.memory[0x0100] = (byte) 0xC8;
        assertTrue(false);
    }

    @Test
    public void test_0xC9_RET()
    {
        // RET  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xC9;
        assertTrue(false);
    }

    @Test
    public void test_0xCA_JP()
    {
        // JP Z,a16  3:16/12  - - - -
        theCpu.memory[0x0100] = (byte) 0xCA;
        assertTrue(false);
    }

    @Test
    public void test_0xCB_PREFIX()
    {
        // PREFIX CB  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0xCB;
        assertTrue(false);
    }

    @Test
    public void test_0xCC_CALL()
    {
        // CALL Z,a16  3:24/12  - - - -
        theCpu.memory[0x0100] = (byte) 0xCC;
        assertTrue(false);
    }

    @Test
    public void test_0xCD_CALL()
    {
        // CALL a16  3:24  - - - -
        theCpu.memory[0x0100] = (byte) 0xCD;
        assertTrue(false);
    }

    @Test
    public void test_0xCE_ADC()
    {
        // ADC A,d8  2:8  Z 0 H C
        theCpu.memory[0x0100] = (byte) 0xCE;
        assertTrue(false);
    }

    @Test
    public void test_0xCF_RST()
    {
        // RST 08H  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xCF;
        assertTrue(false);
    }

    @Test
    public void test_0xD0_RET()
    {
        // RET NC  1:20/8  - - - -
        theCpu.memory[0x0100] = (byte) 0xD0;
        assertTrue(false);
    }

    @Test
    public void test_0xD1_POP()
    {
        // POP DE  1:12  - - - -
        theCpu.memory[0x0100] = (byte) 0xD1;
        assertTrue(false);
    }

    @Test
    public void test_0xD2_JP()
    {
        // JP NC,a16  3:16/12  - - - -
        theCpu.memory[0x0100] = (byte) 0xD2;
        assertTrue(false);
    }

    @Test
    public void test_0xD4_CALL()
    {
        // CALL NC,a16  3:24/12  - - - -
        theCpu.memory[0x0100] = (byte) 0xD4;
        assertTrue(false);
    }

    @Test
    public void test_0xD5_PUSH()
    {
        // PUSH DE  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xD5;
        assertTrue(false);
    }

    @Test
    public void test_0xD6_SUB()
    {
        // SUB d8  2:8  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xD6;
        assertTrue(false);
    }

    @Test
    public void test_0xD7_RST()
    {
        // RST 10H  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xD7;
        assertTrue(false);
    }

    @Test
    public void test_0xD8_RET()
    {
        // RET C  1:20/8  - - - -
        theCpu.memory[0x0100] = (byte) 0xD8;
        assertTrue(false);
    }

    @Test
    public void test_0xD9_RETI()
    {
        // RETI  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xD9;
        assertTrue(false);
    }

    @Test
    public void test_0xDA_JP()
    {
        // JP C,a16  3:16/12  - - - -
        theCpu.memory[0x0100] = (byte) 0xDA;
        assertTrue(false);
    }

    @Test
    public void test_0xDC_CALL()
    {
        // CALL C,a16  3:24/12  - - - -
        theCpu.memory[0x0100] = (byte) 0xDC;
        assertTrue(false);
    }

    @Test
    public void test_0xDE_SBC()
    {
        // SBC A,d8  2:8  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xDE;
        assertTrue(false);
    }

    @Test
    public void test_0xDF_RST()
    {
        // RST 18H  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xDF;
        assertTrue(false);
    }

    @Test
    public void test_0xE0_LDH()
    {
        // LDH (a8),A  2:12  - - - -
        theCpu.memory[0x0100] = (byte) 0xE0;
        assertTrue(false);
    }

    @Test
    public void test_0xE1_POP()
    {
        // POP HL  1:12  - - - -
        theCpu.memory[0x0100] = (byte) 0xE1;
        assertTrue(false);
    }

    @Test
    public void test_0xE2_LD()
    {
        // LD (C),A  2:8  - - - -
        theCpu.memory[0x0100] = (byte) 0xE2;
        assertTrue(false);
    }

    @Test
    public void test_0xE5_PUSH()
    {
        // PUSH HL  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xE5;
        assertTrue(false);
    }

    @Test
    public void test_0xE6_AND()
    {
        // AND d8  2:8  Z 0 1 0
        theCpu.memory[0x0100] = (byte) 0xE6;
        assertTrue(false);
    }

    @Test
    public void test_0xE7_RST()
    {
        // RST 20H  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xE7;
        assertTrue(false);
    }

    @Test
    public void test_0xE8_ADD()
    {
        // ADD SP,r8  2:16  0 0 H C
        theCpu.memory[0x0100] = (byte) 0xE8;
        assertTrue(false);
    }

    @Test
    public void test_0xE9_JP()
    {
        // JP (HL)  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0xE9;
        assertTrue(false);
    }

    @Test
    public void test_0xEA_LD()
    {
        // LD (a16),A  3:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xEA;
        assertTrue(false);
    }

    @Test
    public void test_0xEE_XOR()
    {
        // XOR d8  2:8  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xEE;
        assertTrue(false);
    }

    @Test
    public void test_0xEF_RST()
    {
        // RST 28H  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xEF;
        assertTrue(false);
    }

    @Test
    public void test_0xF0_LDH()
    {
        // LDH A,(a8)  2:12  - - - -
        theCpu.memory[0x0100] = (byte) 0xF0;
        assertTrue(false);
    }

    @Test
    public void test_0xF1_POP()
    {
        // POP AF  1:12  Z N H C
        theCpu.memory[0x0100] = (byte) 0xF1;
        assertTrue(false);
    }

    @Test
    public void test_0xF2_LD()
    {
        // LD A,(C)  2:8  - - - -
        theCpu.memory[0x0100] = (byte) 0xF2;
        assertTrue(false);
    }

    @Test
    public void test_0xF3_DI()
    {
        // DI  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0xF3;
        assertTrue(false);
    }

    @Test
    public void test_0xF5_PUSH()
    {
        // PUSH AF  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xF5;
        assertTrue(false);
    }

    @Test
    public void test_0xF6_OR()
    {
        // OR d8  2:8  Z 0 0 0
        theCpu.memory[0x0100] = (byte) 0xF6;
        assertTrue(false);
    }

    @Test
    public void test_0xF7_RST()
    {
        // RST 30H  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xF7;
        assertTrue(false);
    }

    @Test
    public void test_0xF8_LD()
    {
        // LD HL,SP+r8  2:12  0 0 H C
        theCpu.memory[0x0100] = (byte) 0xF8;
        assertTrue(false);
    }

    @Test
    public void test_0xF9_LD()
    {
        // LD SP,HL  1:8  - - - -
        theCpu.memory[0x0100] = (byte) 0xF9;
        assertTrue(false);
    }

    @Test
    public void test_0xFA_LD()
    {
        // LD A,(a16)  3:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xFA;
        assertTrue(false);
    }

    @Test
    public void test_0xFB_EI()
    {
        // EI  1:4  - - - -
        theCpu.memory[0x0100] = (byte) 0xFB;
        assertTrue(false);
    }

    @Test
    public void test_0xFE_CP()
    {
        // CP d8  2:8  Z 1 H C
        theCpu.memory[0x0100] = (byte) 0xFE;
        assertTrue(false);
    }

    @Test
    public void test_0xFF_RST()
    {
        // RST 38H  1:16  - - - -
        theCpu.memory[0x0100] = (byte) 0xFF;
        assertTrue(false);
    }
}
