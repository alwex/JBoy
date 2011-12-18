/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alwex.jboy.sandbox;

import com.alwex.jboy.utils.Debug;
import com.sun.corba.se.impl.orbutil.concurrent.DebugMutex;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class Sandbox
{

    private Integer a, b;
    
    public Sandbox()
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
    public void bitRotation()
    {
        System.out.println(Integer.toHexString(0xFF << 8 | 0x50));
        System.out.println(Integer.toHexString(0x01 << 8 | 0x50));
        System.out.println(Integer.toHexString(0xFF << 8 | 0x00));
        System.out.println(Integer.toHexString((0xff & 0x00ff) | (0x05 & 0xff00)));

        byte b = (byte) 0x01;
        byte c = (byte) 0x85;
        short result = (short) (((b << 8) | c) & 0x0000ffff);
        result = (short) (b << 8 & 0xffff | c & 0xff);
        System.out.println(Integer.toHexString(result & 0xffff));
    }

    @Test
    public void bitSetting()
    {
        byte b = 0, c = 0;
        System.out.println(Debug.toBin(b));
        b = (byte) (b | (1 << 7));
        System.out.println(Debug.toBin(b));
        b = (byte) (b & ~(1 << 7));
        System.out.println(Debug.toBin(b));

        b = 0x40;
        c = (byte) 0xE0;

        System.out.println(Integer.toHexString(b ^ c & 0xff));

        System.out.println(Integer.toHexString(0xE0 ^ 0x20));
    }

    @Test
    public void writeOpDesc()
    {
        for (int i = 0; i <= 0xff; i++)
        {
//            System.out.println("case 0x" + Debug.toHex((byte)i) + ": break;");
            if (i % 16 == 0)
            {
                System.out.println("");
            }
            System.out.print("/*0x" + Debug.toHex((byte) i) + "*/ \"\", ");
        }
    }

    @Test
    public void testCharByte()
    {
        byte b = (byte) 0xff;

        System.out.println(Debug.toHex(b));
        System.out.println(b);
        b = (byte) 255;
        System.out.println(b);
        int i = 0xff;
        System.out.println(i);
    }

    @Test
    public void testMask()
    {
        short s = (short) 0xDFFE;
        System.out.println(Debug.toHex(s));

        byte b = (byte) ((s & 0xFF00) >> 8);

        System.out.println(Debug.toHex(b));

        b = (byte) 0xFC;
        s = 0x216;

        System.out.println(Debug.toHex((short) (b + s)));
    }

    @Test
    public void testStringBuilder()
    {
        StringBuilder s = new StringBuilder();
        s.append("test");
        assertEquals("test", s.toString());

        s.append("truc");
        assertEquals("testtruc", s.toString());

        String a = String.format("LD %s, %s, in %s", "0x00", "0x12", "0xFC");
        System.out.println(a);
    }

    @Test
    public void createSwitch()
    {
        for (short i = 0; i <= 0xff; i++)
        {
            System.out.println("case " + Debug.toHex((byte) i) + ": break;");
        }
    }

    @Test
    public void parseWebPage()
    {
        String page = "NOP  1:4  - - - -[XX]LD BC,d16  3:12  - - - -[XX]LD (BC),A  1:8  - - - -[XX]INC BC  1:8  - - - -[XX]INC B  1:4  Z 0 H -[XX]DEC B  1:4  Z 1 H -[XX]LD B,d8  2:8  - - - -[XX]RLCA  1:4  0 0 0 C[XX]LD (a16),SP  3:20  - - - -[XX]ADD HL,BC  1:8  - 0 H C[XX]LD A,(BC)  1:8  - - - -[XX]DEC BC  1:8  - - - -[XX]INC C  1:4  Z 0 H -[XX]DEC C  1:4  Z 1 H -[XX]LD C,d8  2:8  - - - -[XX]RRCA  1:4  0 0 0 C  [XX]STOP 0  2:4  - - - -[XX]LD DE,d16  3:12  - - - -[XX]LD (DE),A  1:8  - - - -[XX]INC DE  1:8  - - - -[XX]INC D  1:4  Z 0 H -[XX]DEC D  1:4  Z 1 H -[XX]LD D,d8  2:8  - - - -[XX]RLA  1:4  0 0 0 C[XX]JR r8  2:12  - - - -[XX]ADD HL,DE  1:8  - 0 H C[XX]LD A,(DE)  1:8  - - - -[XX]DEC DE  1:8  - - - -[XX]INC E  1:4  Z 0 H -[XX]DEC E  1:4  Z 1 H -[XX]LD E,d8  2:8  - - - -[XX]RRA  1:4  0 0 0 C  [XX]JR NZ,r8  2:12/8  - - - -[XX]LD HL,d16  3:12  - - - -[XX]LD (HL+),A  1:8  - - - -[XX]INC HL  1:8  - - - -[XX]INC H  1:4  Z 0 H -[XX]DEC H  1:4  Z 1 H -[XX]LD H,d8  2:8  - - - -[XX]DAA  1:4  Z - 0 C[XX]JR Z,r8  2:12/8  - - - -[XX]ADD HL,HL  1:8  - 0 H C[XX]LD A,(HL+)  1:8  - - - -[XX]DEC HL  1:8  - - - -[XX]INC L  1:4  Z 0 H -[XX]DEC L  1:4  Z 1 H -[XX]LD L,d8  2:8  - - - -[XX]CPL  1:4  - 1 1 -  [XX]JR NC,r8  2:12/8  - - - -[XX]LD SP,d16  3:12  - - - -[XX]LD (HL-),A  1:8  - - - -[XX]INC SP  1:8  - - - -[XX]INC (HL)  1:12  Z 0 H -[XX]DEC (HL)  1:12  Z 1 H -[XX]LD (HL),d8  2:12  - - - -[XX]SCF  1:4  - 0 0 1[XX]JR C,r8  2:12/8  - - - -[XX]ADD HL,SP  1:8  - 0 H C[XX]LD A,(HL-)  1:8  - - - -[XX]DEC SP  1:8  - - - -[XX]INC A  1:4  Z 0 H -[XX]DEC A  1:4  Z 1 H -[XX]LD A,d8  2:8  - - - -[XX]CCF  1:4  - 0 0 C  [XX]LD B,B  1:4  - - - -[XX]LD B,C  1:4  - - - -[XX]LD B,D  1:4  - - - -[XX]LD B,E  1:4  - - - -[XX]LD B,H  1:4  - - - -[XX]LD B,L  1:4  - - - -[XX]LD B,(HL)  1:8  - - - -[XX]LD B,A  1:4  - - - -[XX]LD C,B  1:4  - - - -[XX]LD C,C  1:4  - - - -[XX]LD C,D  1:4  - - - -[XX]LD C,E  1:4  - - - -[XX]LD C,H  1:4  - - - -[XX]LD C,L  1:4  - - - -[XX]LD C,(HL)  1:8  - - - -[XX]LD C,A  1:4  - - - -  [XX]LD D,B  1:4  - - - -[XX]LD D,C  1:4  - - - -[XX]LD D,D  1:4  - - - -[XX]LD D,E  1:4  - - - -[XX]LD D,H  1:4  - - - -[XX]LD D,L  1:4  - - - -[XX]LD D,(HL)  1:8  - - - -[XX]LD D,A  1:4  - - - -[XX]LD E,B  1:4  - - - -[XX]LD E,C  1:4  - - - -[XX]LD E,D  1:4  - - - -[XX]LD E,E  1:4  - - - -[XX]LD E,H  1:4  - - - -[XX]LD E,L  1:4  - - - -[XX]LD E,(HL)  1:8  - - - -[XX]LD E,A  1:4  - - - -  [XX]LD H,B  1:4  - - - -[XX]LD H,C  1:4  - - - -[XX]LD H,D  1:4  - - - -[XX]LD H,E  1:4  - - - -[XX]LD H,H  1:4  - - - -[XX]LD H,L  1:4  - - - -[XX]LD H,(HL)  1:8  - - - -[XX]LD H,A  1:4  - - - -[XX]LD L,B  1:4  - - - -[XX]LD L,C  1:4  - - - -[XX]LD L,D  1:4  - - - -[XX]LD L,E  1:4  - - - -[XX]LD L,H  1:4  - - - -[XX]LD L,L  1:4  - - - -[XX]LD L,(HL)  1:8  - - - -[XX]LD L,A  1:4  - - - -  [XX]LD (HL),B  1:8  - - - -[XX]LD (HL),C  1:8  - - - -[XX]LD (HL),D  1:8  - - - -[XX]LD (HL),E  1:8  - - - -[XX]LD (HL),H  1:8  - - - -[XX]LD (HL),L  1:8  - - - -[XX]HALT  1:4  - - - -[XX]LD (HL),A  1:8  - - - -[XX]LD A,B  1:4  - - - -[XX]LD A,C  1:4  - - - -[XX]LD A,D  1:4  - - - -[XX]LD A,E  1:4  - - - -[XX]LD A,H  1:4  - - - -[XX]LD A,L  1:4  - - - -[XX]LD A,(HL)  1:8  - - - -[XX]LD A,A  1:4  - - - -  [XX]ADD A,B  1:4  Z 0 H C[XX]ADD A,C  1:4  Z 0 H C[XX]ADD A,D  1:4  Z 0 H C[XX]ADD A,E  1:4  Z 0 H C[XX]ADD A,H  1:4  Z 0 H C[XX]ADD A,L  1:4  Z 0 H C[XX]ADD A,(HL)  1:8  Z 0 H C[XX]ADD A,A  1:4  Z 0 H C[XX]ADC A,B  1:4  Z 0 H C[XX]ADC A,C  1:4  Z 0 H C[XX]ADC A,D  1:4  Z 0 H C[XX]ADC A,E  1:4  Z 0 H C[XX]ADC A,H  1:4  Z 0 H C[XX]ADC A,L  1:4  Z 0 H C[XX]ADC A,(HL)  1:8  Z 0 H C[XX]ADC A,A  1:4  Z 0 H C  [XX]SUB B  1:4  Z 1 H C[XX]SUB C  1:4  Z 1 H C[XX]SUB D  1:4  Z 1 H C[XX]SUB E  1:4  Z 1 H C[XX]SUB H  1:4  Z 1 H C[XX]SUB L  1:4  Z 1 H C[XX]SUB (HL)  1:8  Z 1 H C[XX]SUB A  1:4  Z 1 H C[XX]SBC A,B  1:4  Z 1 H C[XX]SBC A,C  1:4  Z 1 H C[XX]SBC A,D  1:4  Z 1 H C[XX]SBC A,E  1:4  Z 1 H C[XX]SBC A,H  1:4  Z 1 H C[XX]SBC A,L  1:4  Z 1 H C[XX]SBC A,(HL)  1:8  Z 1 H C[XX]SBC A,A  1:4  Z 1 H C  [XX]AND B  1:4  Z 0 1 0[XX]AND C  1:4  Z 0 1 0[XX]AND D  1:4  Z 0 1 0[XX]AND E  1:4  Z 0 1 0[XX]AND H  1:4  Z 0 1 0[XX]AND L  1:4  Z 0 1 0[XX]AND (HL)  1:8  Z 0 1 0[XX]AND A  1:4  Z 0 1 0[XX]XOR B  1:4  Z 0 0 0[XX]XOR C  1:4  Z 0 0 0[XX]XOR D  1:4  Z 0 0 0[XX]XOR E  1:4  Z 0 0 0[XX]XOR H  1:4  Z 0 0 0[XX]XOR L  1:4  Z 0 0 0[XX]XOR (HL)  1:8  Z 0 0 0[XX]XOR A  1:4  Z 0 0 0  [XX]OR B  1:4  Z 0 0 0[XX]OR C  1:4  Z 0 0 0[XX]OR D  1:4  Z 0 0 0[XX]OR E  1:4  Z 0 0 0[XX]OR H  1:4  Z 0 0 0[XX]OR L  1:4  Z 0 0 0[XX]OR (HL)  1:8  Z 0 0 0[XX]OR A  1:4  Z 0 0 0[XX]CP B  1:4  Z 1 H C[XX]CP C  1:4  Z 1 H C[XX]CP D  1:4  Z 1 H C[XX]CP E  1:4  Z 1 H C[XX]CP H  1:4  Z 1 H C[XX]CP L  1:4  Z 1 H C[XX]CP (HL)  1:8  Z 1 H C[XX]CP A  1:4  Z 1 H C  [XX]RET NZ  1:20/8  - - - -[XX]POP BC  1:12  - - - -[XX]JP NZ,a16  3:16/12  - - - -[XX]JP a16  3:16  - - - -[XX]CALL NZ,a16  3:24/12  - - - -[XX]PUSH BC  1:16  - - - -[XX]ADD A,d8  2:8  Z 0 H C[XX]RST 00H  1:16  - - - -[XX]RET Z  1:20/8  - - - -[XX]RET  1:16  - - - -[XX]JP Z,a16  3:16/12  - - - -[XX]PREFIX CB  1:4  - - - -[XX]CALL Z,a16  3:24/12  - - - -[XX]CALL a16  3:24  - - - -[XX]ADC A,d8  2:8  Z 0 H C[XX]RST 08H  1:16  - - - -  [XX]RET NC  1:20/8  - - - -[XX]POP DE  1:12  - - - -[XX]JP NC,a16  3:16/12  - - - -[XX]<td class=\"withborder\">&nbsp;[XX]CALL NC,a16  3:24/12  - - - -[XX]PUSH DE  1:16  - - - -[XX]SUB d8  2:8  Z 1 H C[XX]RST 10H  1:16  - - - -[XX]RET C  1:20/8  - - - -[XX]RETI  1:16  - - - -[XX]JP C,a16  3:16/12  - - - -[XX]<td class=\"withborder\">&nbsp;[XX]CALL C,a16  3:24/12  - - - -[XX]<td class=\"withborder\">&nbsp;[XX]SBC A,d8  2:8  Z 1 H C[XX]RST 18H  1:16  - - - -  [XX]LDH (a8),A  2:12  - - - -[XX]POP HL  1:12  - - - -[XX]LD (C),A  2:8  - - - -[XX]<td class=\"withborder\">&nbsp;[XX]<td class=\"withborder\">&nbsp;[XX]PUSH HL  1:16  - - - -[XX]AND d8  2:8  Z 0 1 0[XX]RST 20H  1:16  - - - -[XX]ADD SP,r8  2:16  0 0 H C[XX]JP (HL)  1:4  - - - -[XX]LD (a16),A  3:16  - - - -[XX]<td class=\"withborder\">&nbsp;[XX]<td class=\"withborder\">&nbsp;[XX]<td class=\"withborder\">&nbsp;[XX]XOR d8  2:8  Z 0 0 0[XX]RST 28H  1:16  - - - -  [XX]LDH A,(a8)  2:12  - - - -[XX]POP AF  1:12  Z N H C[XX]LD A,(C)  2:8  - - - -[XX]DI  1:4  - - - -[XX]<td class=\"withborder\">&nbsp;[XX]PUSH AF  1:16  - - - -[XX]OR d8  2:8  Z 0 0 0[XX]RST 30H  1:16  - - - -[XX]LD HL,SP+r8  2:12  0 0 H C[XX]LD SP,HL  1:8  - - - -[XX]LD A,(a16)  3:16  - - - -[XX]EI  1:4  - - - -[XX]<td class=\"withborder\">&nbsp;[XX]<td class=\"withborder\">&nbsp;[XX]CP d8  2:8  Z 1 H C[XX]RST 38H  1:16  - - - -      ";
        page = page.replace("[", "").replace("]", "");
        String[] opcodes = page.split("XX");

        byte i = 0;
        for (String code : opcodes)
        {

            if (! code.contains("withborder"))
            {
                System.out.println("// " + code);
                System.out.println("case " + Debug.toHex(i) + ": label = \"" + code + "\"; break;");
                System.out.println("");
            }
            i++;
        }
    }
    
    @Test
    public void testNegative()
    {
        System.out.println((byte) 0xfc);
    }
    
    private void changeValues(Integer theA)
    {
        theA = 10;
    }
    
    @Test
    public void referenceTest()
    {
        a = 0;
        b = 0;
        
        this.changeValues(a);
        this.changeValues(b);
        
        System.out.println(a);
        System.out.println(b);
    }
}
