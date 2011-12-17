package com.alwex.jboy.hardware;

import com.alwex.jboy.utils.Debug;
import org.apache.log4j.Logger;

/**
 *
 * @author Alex
 *
 * http://www.zophar.net/fileuploads/2/10809ozrmc/z80_faq.html
 * http://www.zophar.net/fileuploads/2/10810irctp/z80_faq2.html
 */
public class CPU extends AbstractHardware
{

    protected static Logger logger;
    private static CPU instance;
    public String[] opCodeDescription =
    {
        /*0x00*/ "NOP", /*0x01*/ "LD BC,d16", /*0x02*/ "LD (BC),A", /*0x03*/ "INC BC", /*0x04*/ "INC B", /*0x05*/ "DEC B", /*0x06*/ "LD B,d8", /*0x07*/ "RLCA", /*0x08*/ "LD (a16),SP", /*0x09*/ "ADD HL,BC", /*0x0A*/ "LD A,(BC)", /*0x0B*/ "DEC BC", /*0x0C*/ "INC C", /*0x0D*/ "DEC C", /*0x0E*/ "LD C,d8", /*0x0F*/ "RRCA",
        /*0x10*/ "STOP 0", /*0x11*/ "LD DE,d16", /*0x12*/ "LD (DE),A", /*0x13*/ "INC DE", /*0x14*/ "INC D", /*0x15*/ "DEC D", /*0x16*/ "LD D,d8", /*0x17*/ "RLA", /*0x18*/ "JR r8", /*0x19*/ "ADD HL,DE", /*0x1A*/ "LD A,(DE)", /*0x1B*/ "DEC DE", /*0x1C*/ "INC E", /*0x1D*/ "DEC E", /*0x1E*/ "LD E,d8", /*0x1F*/ "RRA",
        /*0x20*/ "JR NZ,r8", /*0x21*/ "LD HL,d16", /*0x22*/ "", /*0x23*/ "", /*0x24*/ "", /*0x25*/ "", /*0x26*/ "", /*0x27*/ "", /*0x28*/ "JR Z,r8", /*0x29*/ "", /*0x2A*/ "", /*0x2B*/ "", /*0x2C*/ "", /*0x2D*/ "", /*0x2E*/ "", /*0x2F*/ "",
        /*0x30*/ "", /*0x31*/ "", /*0x32*/ "LDD (HL),A", /*0x33*/ "", /*0x34*/ "", /*0x35*/ "", /*0x36*/ "", /*0x37*/ "", /*0x38*/ "", /*0x39*/ "", /*0x3A*/ "", /*0x3B*/ "", /*0x3C*/ "", /*0x3D*/ "", /*0x3E*/ "LD A,d8", /*0x3F*/ "",
        /*0x40*/ "", /*0x41*/ "", /*0x42*/ "", /*0x43*/ "", /*0x44*/ "", /*0x45*/ "", /*0x46*/ "", /*0x47*/ "", /*0x48*/ "", /*0x49*/ "", /*0x4A*/ "", /*0x4B*/ "", /*0x4C*/ "", /*0x4D*/ "", /*0x4E*/ "", /*0x4F*/ "",
        /*0x50*/ "", /*0x51*/ "", /*0x52*/ "", /*0x53*/ "", /*0x54*/ "", /*0x55*/ "", /*0x56*/ "", /*0x57*/ "", /*0x58*/ "", /*0x59*/ "", /*0x5A*/ "", /*0x5B*/ "", /*0x5C*/ "", /*0x5D*/ "", /*0x5E*/ "", /*0x5F*/ "",
        /*0x60*/ "", /*0x61*/ "", /*0x62*/ "", /*0x63*/ "", /*0x64*/ "", /*0x65*/ "", /*0x66*/ "", /*0x67*/ "", /*0x68*/ "", /*0x69*/ "", /*0x6A*/ "", /*0x6B*/ "", /*0x6C*/ "", /*0x6D*/ "", /*0x6E*/ "", /*0x6F*/ "",
        /*0x70*/ "", /*0x71*/ "", /*0x72*/ "", /*0x73*/ "", /*0x74*/ "", /*0x75*/ "", /*0x76*/ "", /*0x77*/ "", /*0x78*/ "", /*0x79*/ "", /*0x7A*/ "", /*0x7B*/ "", /*0x7C*/ "", /*0x7D*/ "", /*0x7E*/ "", /*0x7F*/ "",
        /*0x80*/ "", /*0x81*/ "", /*0x82*/ "", /*0x83*/ "", /*0x84*/ "", /*0x85*/ "", /*0x86*/ "", /*0x87*/ "", /*0x88*/ "", /*0x89*/ "", /*0x8A*/ "", /*0x8B*/ "", /*0x8C*/ "", /*0x8D*/ "", /*0x8E*/ "", /*0x8F*/ "",
        /*0x90*/ "", /*0x91*/ "", /*0x92*/ "", /*0x93*/ "", /*0x94*/ "", /*0x95*/ "", /*0x96*/ "", /*0x97*/ "", /*0x98*/ "", /*0x99*/ "", /*0x9A*/ "", /*0x9B*/ "", /*0x9C*/ "", /*0x9D*/ "", /*0x9E*/ "", /*0x9F*/ "",
        /*0xA0*/ "", /*0xA1*/ "", /*0xA2*/ "", /*0xA3*/ "", /*0xA4*/ "", /*0xA5*/ "", /*0xA6*/ "", /*0xA7*/ "AND A", /*0xA8*/ "", /*0xA9*/ "", /*0xAA*/ "", /*0xAB*/ "", /*0xAC*/ "", /*0xAD*/ "", /*0xAE*/ "", /*0xAF*/ "XOR A",
        /*0xB0*/ "", /*0xB1*/ "", /*0xB2*/ "", /*0xB3*/ "", /*0xB4*/ "", /*0xB5*/ "", /*0xB6*/ "", /*0xB7*/ "", /*0xB8*/ "", /*0xB9*/ "", /*0xBA*/ "", /*0xBB*/ "", /*0xBC*/ "", /*0xBD*/ "", /*0xBE*/ "", /*0xBF*/ "",
        /*0xC0*/ "", /*0xC1*/ "", /*0xC2*/ "", /*0xC3*/ "JP a16", /*0xC4*/ "", /*0xC5*/ "", /*0xC6*/ "", /*0xC7*/ "", /*0xC8*/ "", /*0xC9*/ "", /*0xCA*/ "", /*0xCB*/ "", /*0xCC*/ "", /*0xCD*/ "", /*0xCE*/ "", /*0xCF*/ "",
        /*0xD0*/ "", /*0xD1*/ "", /*0xD2*/ "", /*0xD3*/ "", /*0xD4*/ "", /*0xD5*/ "", /*0xD6*/ "", /*0xD7*/ "", /*0xD8*/ "", /*0xD9*/ "", /*0xDA*/ "", /*0xDB*/ "", /*0xDC*/ "", /*0xDD*/ "", /*0xDE*/ "", /*0xDF*/ "",
        /*0xE0*/ "LDH (a8),A", /*0xE1*/ "", /*0xE2*/ "", /*0xE3*/ "", /*0xE4*/ "", /*0xE5*/ "", /*0xE6*/ "", /*0xE7*/ "", /*0xE8*/ "", /*0xE9*/ "", /*0xEA*/ "", /*0xEB*/ "", /*0xEC*/ "", /*0xED*/ "", /*0xEE*/ "", /*0xEF*/ "",
        /*0xF0*/ "LDH A,(a8)", /*0xF1*/ "", /*0xF2*/ "", /*0xF3*/ "DI", /*0xF4*/ "", /*0xF5*/ "", /*0xF6*/ "", /*0xF7*/ "", /*0xF8*/ "", /*0xF9*/ "", /*0xFA*/ "", /*0xFB*/ "", /*0xFC*/ "", /*0xFD*/ "", /*0xFE*/ "CP d8", /*0xFF*/ ""
    };
    public int F_C = 4, F_H = 5, F_N = 6, F_Z = 7;
    public byte A = 0x00,
            B,
            C,
            D,
            E,
            // flag register 7 6 5 4 3 2 1 0
            //               Z N H C 0 0 0 0
            // * Z Zero flag if operation result match is
            // zero
            // or two when using the CP instruction
            // * N Substract flag, set if a substraction was
            // performed in the last math instruction
            // * H Half carry flag is set if a carry occurred
            // from the lower nibble in the last math operation
            // * Carry flag, is set if a carry occured from the
            // last math operation or if register A is the
            // smaller value when executing the CP instruction
            F = (byte) 0xB0,
            H,
            L;
    // Stack Pointer
    public short SP = (short) 0xFFFE,
            // Program Counter
            // initialiszed to 0x0100
            // first instruction in rom location
            PC = 0x0100;
    public byte[] rom;
    // pourquoi + 1 ?
    public byte[] memory = new byte[0xFFFF + 1];

    public static CPU getInstance()
    {
        if (null == instance)
        {
            instance = new CPU();
        }

        return instance;
    }

    /**
     * initialisation du logger
     * et construction du CPU
     */
    public CPU()
    {
        logger = Logger.getLogger(this.getClass());
        logger.info("=========== START ===========");
    }

    /**
     * initialise les registres
     */
    public void init()
    {
        PC = 0x0100;
        A = 0x01;
        setF(F_Z, 1);
        setF(F_N, 0);
        setF(F_H, 1);
        setF(F_C, 1);
        B = 0x00;
        C = 0x13;
        D = 0x00;
        E = (byte) 0xD8;
        H = 0x01;
        L = 0x4D;
    }

    /**
     * charge une rom
     * @param rom
     */
    public void setRom(byte[] rom)
    {
        System.arraycopy(rom, 0, this.memory, 0, rom.length);
    }

    /**
     * process des opcodes de la rom
     * http://www.pastraiser.com/cpu/gameboy/gameboy_opcodes.html
     *
     * d8  => immediate 8bits values
     * d16 => immediate 16bits values
     *
     * @param opCode
     */
    public void processOpCode()
    {
        byte opCode = memory[PC];
        int addr;
        String label = "";

        logger.info("=======================================");
        logger.info(opCodeDescription[opCode & 0xFF]);
        logger.info("[" + Debug.toHex(opCode) + "] values: "
                + Debug.toHex(memory[PC + 1]) + " "
                + Debug.toHex(memory[PC + 2]) + " "
                + Debug.toHex(memory[PC + 3]) + " ");

        logger.info("F[ZNHC0000]");
        logger.info("F[" + Debug.toBin(F) + "]");
        logger.info("PC=" + Integer.toHexString(PC));
        logger.info("A=" + Debug.toHex(A) + " F=" + Debug.toHex(F)
                + " B=" + Debug.toHex(B) + " C=" + Debug.toHex(C)
                + " D=" + Debug.toHex(D) + " E=" + Debug.toHex(E)
                + " HL=" + Debug.toHex(H) + "" + Debug.toHex(L)
                + " SP=" + Debug.toHex(SP));

        switch (opCode & 0xFF)
        {


            // NOP  1:4  - - - -
            case 0x00:
                label = "NOP  1:4  - - - -";
                PC++;
                break;

            // LD BC,d16  3:12  - - - -
            case 0x01:
                label = "LD BC,d16  3:12  - - - -";
                break;

            //LD (BC),A  1:8  - - - -
            case 0x02:
                label = "LD (BC),A  1:8  - - - -";
                break;

            //INC BC  1:8  - - - -
            case 0x03:
                label = "INC BC  1:8  - - - -";
                break;

            //INC B  1:4  Z 0 H -
            case 0x04:
                label = "INC B  1:4  Z 0 H -";
                break;

            //DEC B  1:4  Z 1 H -
            case 0x05:
                label = "DEC B  1:4  Z 1 H -";
                B--;
                if (B == 0)
                {
                    setF(F_Z, 1);
                }
                else
                {
                    setF(F_Z, 0);
                }
                setF(F_N, 1);
                setF(F_H, 1); // ?
                PC++;
                break;

            //LD B,d8  2:8  - - - -
            case 0x06:
                label = "LD B,d8  2:8  - - - -";
                B = memory[PC + 1];
                PC += 2;
                break;

            //RLCA  1:4  0 0 0 C
            case 0x07:
                label = "RLCA  1:4  0 0 0 C";
                break;

            //LD (a16),SP  3:20  - - - -
            case 0x08:
                label = "LD (a16),SP  3:20  - - - -";
                break;

            //ADD HL,BC  1:8  - 0 H C
            case 0x09:
                label = "ADD HL,BC  1:8  - 0 H C";
                break;

            //LD A,(BC)  1:8  - - - -
            case 0x0A:
                label = "LD A,(BC)  1:8  - - - -";
                break;

            //DEC BC  1:8  - - - -
            case 0x0B:
                label = "DEC BC  1:8  - - - -";
                int BC = combine(C, B);
                BC--;
                B = (byte) (BC & 0x00FF);
                C = (byte) (BC & 0xFF00);
                PC++;
                break;

            //INC C  1:4  Z 0 H -
            case 0x0C:
                label = "INC C  1:4  Z 0 H -";
                break;

            //DEC C  1:4  Z 1 H -
            case 0x0D:
                label = "DEC C  1:4  Z 1 H -";
                break;

            //LD C,d8  2:8  - - - -
            case 0x0E:
                label = "LD C,d8  2:8  - - - -";
                C = memory[PC + 1];
                PC += 2;
                break;

            //RRCA  1:4  0 0 0 C  
            case 0x0F:
                label = "RRCA  1:4  0 0 0 C  ";
                break;

            //STOP 0  2:4  - - - -
            case 0x10:
                label = "STOP 0  2:4  - - - -";
                break;

            //LD DE,d16  3:12  - - - -
            case 0x11:
                label = "LD DE,d16  3:12  - - - -";
                break;

            //LD (DE),A  1:8  - - - -
            case 0x12:
                label = "LD (DE),A  1:8  - - - -";
                break;

            //INC DE  1:8  - - - -
            case 0x13:
                label = "INC DE  1:8  - - - -";
                break;

            //INC D  1:4  Z 0 H -
            case 0x14:
                label = "INC D  1:4  Z 0 H -";
                break;

            //DEC D  1:4  Z 1 H -
            case 0x15:
                label = "DEC D  1:4  Z 1 H -";
                break;

            //LD D,d8  2:8  - - - -
            case 0x16:
                label = "LD D,d8  2:8  - - - -";
                break;

            //RLA  1:4  0 0 0 C
            case 0x17:
                label = "RLA  1:4  0 0 0 C";
                break;

            //JR r8  2:12  - - - -
            case 0x18:
                label = "JR r8  2:12  - - - -";
                PC += (memory[PC + 1] & 0xff);
                break;

            //ADD HL,DE  1:8  - 0 H C
            case 0x19:
                label = "ADD HL,DE  1:8  - 0 H C";
                break;

            //LD A,(DE)  1:8  - - - -
            case 0x1A:
                label = "LD A,(DE)  1:8  - - - -";
                break;

            //DEC DE  1:8  - - - -
            case 0x1B:
                label = "DEC DE  1:8  - - - -";
                break;

            //INC E  1:4  Z 0 H -
            case 0x1C:
                label = "INC E  1:4  Z 0 H -";
                break;

            //DEC E  1:4  Z 1 H -
            case 0x1D:
                label = "DEC E  1:4  Z 1 H -";
                break;

            //LD E,d8  2:8  - - - -
            case 0x1E:
                label = "LD E,d8  2:8  - - - -";
                break;

            //RRA  1:4  0 0 0 C  
            case 0x1F:
                label = "RRA  1:4  0 0 0 C  ";
                break;

            //JR NZ,r8  2:12/8  - - - -
            case 0x20:
                label = "JR NZ,r8  2:12/8  - - - -";
                if (getF(F_Z) == 0)
                {
                    PC += (short) (memory[PC + 1]);
                }
                else
                {
                    PC += 2;
                }
                break;

            //LD HL,d16  3:12  - - - -
            case 0x21:
                label = "LD HL,d16  3:12  - - - -";
                H = memory[PC + 2];
                L = memory[PC + 1];
                PC += 3;
                break;

            //LD (HL+),A  1:8  - - - -
            case 0x22:
                label = "LD (HL+),A  1:8  - - - -";
                addr = combine(H, L);
                memory[addr] = A;
                addr++;
                H = (byte) (addr & 0xFF00);
                L = (byte) (addr & 0x00FF);
                PC++;
                break;

            //INC HL  1:8  - - - -
            case 0x23:
                label = "INC HL  1:8  - - - -";
                break;

            //INC H  1:4  Z 0 H -
            case 0x24:
                label = "INC H  1:4  Z 0 H -";
                break;

            //DEC H  1:4  Z 1 H -
            case 0x25:
                label = "DEC H  1:4  Z 1 H -";
                break;

            //LD H,d8  2:8  - - - -
            case 0x26:
                label = "LD H,d8  2:8  - - - -";
                break;

            //DAA  1:4  Z - 0 C
            case 0x27:
                label = "DAA  1:4  Z - 0 C";
                break;

            //JR Z,r8  2:12/8  - - - -
            case 0x28:
                label = "JR Z,r8  2:12/8  - - - -";
                if (getF(F_Z) == 1)
                {
                    PC += (memory[PC + 1] & 0xff);
                }
                else
                {
                    PC += 2;
                }
                break;

            //ADD HL,HL  1:8  - 0 H C
            case 0x29:
                label = "ADD HL,HL  1:8  - 0 H C";
                break;

            //LD A,(HL+)  1:8  - - - -
            case 0x2A:
                label = "LD A,(HL+)  1:8  - - - -";
                break;

            //DEC HL  1:8  - - - -
            case 0x2B:
                label = "DEC HL  1:8  - - - -";
                break;

            //INC L  1:4  Z 0 H -
            case 0x2C:
                label = "INC L  1:4  Z 0 H -";
                break;

            //DEC L  1:4  Z 1 H -
            case 0x2D:
                label = "DEC L  1:4  Z 1 H -";
                break;

            //LD L,d8  2:8  - - - -
            case 0x2E:
                label = "LD L,d8  2:8  - - - -";
                break;

            //CPL  1:4  - 1 1 -  
            case 0x2F:
                label = "CPL  1:4  - 1 1 -  ";
                A = (byte) ~A;
                setF(F_N, 1);
                setF(F_H, 1);
                PC++;
                break;

            //JR NC,r8  2:12/8  - - - -
            case 0x30:
                label = "JR NC,r8  2:12/8  - - - -";
                break;

            //LD SP,d16  3:12  - - - -
            case 0x31:
                label = "LD SP,d16  3:12  - - - -";
                break;

            //LD (HL-),A  1:8  - - - -
            case 0x32:
                label = "LD (HL-),A  1:8  - - - -";
                addr = combine(H, L);
                memory[addr] = A;
                addr--;
                H = (byte) ((addr & 0xFF00) >> 8);
                L = (byte) (addr & 0x00FF);
                PC++;
                break;

            //INC SP  1:8  - - - -
            case 0x33:
                label = "INC SP  1:8  - - - -";
                break;

            //INC (HL)  1:12  Z 0 H -
            case 0x34:
                label = "INC (HL)  1:12  Z 0 H -";
                break;

            //DEC (HL)  1:12  Z 1 H -
            case 0x35:
                label = "DEC (HL)  1:12  Z 1 H -";
                break;

            //LD (HL),d8  2:12  - - - -
            case 0x36:
                label = "LD (HL),d8  2:12  - - - -";
                break;

            //SCF  1:4  - 0 0 1
            case 0x37:
                label = "SCF  1:4  - 0 0 1";
                break;

            //JR C,r8  2:12/8  - - - -
            case 0x38:
                label = "JR C,r8  2:12/8  - - - -";
                break;

            //ADD HL,SP  1:8  - 0 H C
            case 0x39:
                label = "ADD HL,SP  1:8  - 0 H C";
                break;

            //LD A,(HL-)  1:8  - - - -
            case 0x3A:
                label = "LD A,(HL-)  1:8  - - - -";
                break;

            //DEC SP  1:8  - - - -
            case 0x3B:
                label = "DEC SP  1:8  - - - -";
                break;

            //INC A  1:4  Z 0 H -
            case 0x3C:
                label = "INC A  1:4  Z 0 H -";
                break;

            //DEC A  1:4  Z 1 H -
            case 0x3D:
                label = "DEC A  1:4  Z 1 H -";
                break;

            //LD A,d8  2:8  - - - -
            case 0x3E:
                label = "LD A,d8  2:8  - - - -";
                PC++;
                A = memory[PC];
                PC++;
                break;

            //CCF  1:4  - 0 0 C  
            case 0x3F:
                label = "CCF  1:4  - 0 0 C  ";
                break;

            //LD B,B  1:4  - - - -
            case 0x40:
                label = "LD B,B  1:4  - - - -";
                // B = B;
                PC++;
                break;

            //LD B,C  1:4  - - - -
            case 0x41:
                label = "LD B,C  1:4  - - - -";
                break;

            //LD B,D  1:4  - - - -
            case 0x42:
                label = "LD B,D  1:4  - - - -";
                break;

            //LD B,E  1:4  - - - -
            case 0x43:
                label = "LD B,E  1:4  - - - -";
                break;

            //LD B,H  1:4  - - - -
            case 0x44:
                label = "LD B,H  1:4  - - - -";
                break;

            //LD B,L  1:4  - - - -
            case 0x45:
                label = "LD B,L  1:4  - - - -";
                break;

            //LD B,(HL)  1:8  - - - -
            case 0x46:
                label = "LD B,(HL)  1:8  - - - -";
                break;

            //LD B,A  1:4  - - - -
            case 0x47:
                label = "LD B,A  1:4  - - - -";
                break;

            //LD C,B  1:4  - - - -
            case 0x48:
                label = "LD C,B  1:4  - - - -";
                break;

            //LD C,C  1:4  - - - -
            case 0x49:
                label = "LD C,C  1:4  - - - -";
                break;

            //LD C,D  1:4  - - - -
            case 0x4A:
                label = "LD C,D  1:4  - - - -";
                break;

            //LD C,E  1:4  - - - -
            case 0x4B:
                label = "LD C,E  1:4  - - - -";
                break;

            //LD C,H  1:4  - - - -
            case 0x4C:
                label = "LD C,H  1:4  - - - -";
                break;

            //LD C,L  1:4  - - - -
            case 0x4D:
                label = "LD C,L  1:4  - - - -";
                break;

            //LD C,(HL)  1:8  - - - -
            case 0x4E:
                label = "LD C,(HL)  1:8  - - - -";
                break;

            //LD C,A  1:4  - - - -  
            case 0x4F:
                label = "LD C,A  1:4  - - - -  ";
                break;

            //LD D,B  1:4  - - - -
            case 0x50:
                label = "LD D,B  1:4  - - - -";
                break;

            //LD D,C  1:4  - - - -
            case 0x51:
                label = "LD D,C  1:4  - - - -";
                break;

            //LD D,D  1:4  - - - -
            case 0x52:
                label = "LD D,D  1:4  - - - -";
                break;

            //LD D,E  1:4  - - - -
            case 0x53:
                label = "LD D,E  1:4  - - - -";
                break;

            //LD D,H  1:4  - - - -
            case 0x54:
                label = "LD D,H  1:4  - - - -";
                break;

            //LD D,L  1:4  - - - -
            case 0x55:
                label = "LD D,L  1:4  - - - -";
                break;

            //LD D,(HL)  1:8  - - - -
            case 0x56:
                label = "LD D,(HL)  1:8  - - - -";
                break;

            //LD D,A  1:4  - - - -
            case 0x57:
                label = "LD D,A  1:4  - - - -";
                break;

            //LD E,B  1:4  - - - -
            case 0x58:
                label = "LD E,B  1:4  - - - -";
                break;

            //LD E,C  1:4  - - - -
            case 0x59:
                label = "LD E,C  1:4  - - - -";
                break;

            //LD E,D  1:4  - - - -
            case 0x5A:
                label = "LD E,D  1:4  - - - -";
                break;

            //LD E,E  1:4  - - - -
            case 0x5B:
                label = "LD E,E  1:4  - - - -";
                break;

            //LD E,H  1:4  - - - -
            case 0x5C:
                label = "LD E,H  1:4  - - - -";
                break;

            //LD E,L  1:4  - - - -
            case 0x5D:
                label = "LD E,L  1:4  - - - -";
                break;

            //LD E,(HL)  1:8  - - - -
            case 0x5E:
                label = "LD E,(HL)  1:8  - - - -";
                break;

            //LD E,A  1:4  - - - -  
            case 0x5F:
                label = "LD E,A  1:4  - - - -  ";
                break;

            //LD H,B  1:4  - - - -
            case 0x60:
                label = "LD H,B  1:4  - - - -";
                break;

            //LD H,C  1:4  - - - -
            case 0x61:
                label = "LD H,C  1:4  - - - -";
                break;

            //LD H,D  1:4  - - - -
            case 0x62:
                label = "LD H,D  1:4  - - - -";
                break;

            //LD H,E  1:4  - - - -
            case 0x63:
                label = "LD H,E  1:4  - - - -";
                break;

            //LD H,H  1:4  - - - -
            case 0x64:
                label = "LD H,H  1:4  - - - -";
                break;

            //LD H,L  1:4  - - - -
            case 0x65:
                label = "LD H,L  1:4  - - - -";
                break;

            //LD H,(HL)  1:8  - - - -
            case 0x66:
                label = "LD H,(HL)  1:8  - - - -";
                H = (byte) (H & 0xFF00 | L & 0x00FF);
                PC++;
                break;

            //LD H,A  1:4  - - - -
            case 0x67:
                label = "LD H,A  1:4  - - - -";
                break;

            //LD L,B  1:4  - - - -
            case 0x68:
                label = "LD L,B  1:4  - - - -";
                break;

            //LD L,C  1:4  - - - -
            case 0x69:
                label = "LD L,C  1:4  - - - -";
                break;

            //LD L,D  1:4  - - - -
            case 0x6A:
                label = "LD L,D  1:4  - - - -";
                break;

            //LD L,E  1:4  - - - -
            case 0x6B:
                label = "LD L,E  1:4  - - - -";
                break;

            //LD L,H  1:4  - - - -
            case 0x6C:
                label = "LD L,H  1:4  - - - -";
                break;

            //LD L,L  1:4  - - - -
            case 0x6D:
                label = "LD L,L  1:4  - - - -";
                break;

            //LD L,(HL)  1:8  - - - -
            case 0x6E:
                label = "LD L,(HL)  1:8  - - - -";
                break;

            //LD L,A  1:4  - - - -  
            case 0x6F:
                label = "LD L,A  1:4  - - - -  ";
                break;

            //LD (HL),B  1:8  - - - -
            case 0x70:
                label = "LD (HL),B  1:8  - - - -";
                break;

            //LD (HL),C  1:8  - - - -
            case 0x71:
                label = "LD (HL),C  1:8  - - - -";
                break;

            //LD (HL),D  1:8  - - - -
            case 0x72:
                label = "LD (HL),D  1:8  - - - -";
                break;

            //LD (HL),E  1:8  - - - -
            case 0x73:
                label = "LD (HL),E  1:8  - - - -";
                break;

            //LD (HL),H  1:8  - - - -
            case 0x74:
                label = "LD (HL),H  1:8  - - - -";
                break;

            //LD (HL),L  1:8  - - - -
            case 0x75:
                label = "LD (HL),L  1:8  - - - -";
                break;

            //HALT  1:4  - - - -
            case 0x76:
                label = "HALT  1:4  - - - -";
                break;

            //LD (HL),A  1:8  - - - -
            case 0x77:
                label = "LD (HL),A  1:8  - - - -";
                break;

            //LD A,B  1:4  - - - -
            case 0x78:
                label = "LD A,B  1:4  - - - -";
                break;

            //LD A,C  1:4  - - - -
            case 0x79:
                label = "LD A,C  1:4  - - - -";
                break;

            //LD A,D  1:4  - - - -
            case 0x7A:
                label = "LD A,D  1:4  - - - -";
                break;

            //LD A,E  1:4  - - - -
            case 0x7B:
                label = "LD A,E  1:4  - - - -";
                break;

            //LD A,H  1:4  - - - -
            case 0x7C:
                label = "LD A,H  1:4  - - - -";
                break;

            //LD A,L  1:4  - - - -
            case 0x7D:
                label = "LD A,L  1:4  - - - -";
                break;

            //LD A,(HL)  1:8  - - - -
            case 0x7E:
                label = "LD A,(HL)  1:8  - - - -";
                break;

            //LD A,A  1:4  - - - -  
            case 0x7F:
                label = "LD A,A  1:4  - - - -  ";
                break;

            //ADD A,B  1:4  Z 0 H C
            case 0x80:
                label = "ADD A,B  1:4  Z 0 H C";
                break;

            //ADD A,C  1:4  Z 0 H C
            case 0x81:
                label = "ADD A,C  1:4  Z 0 H C";
                break;

            //ADD A,D  1:4  Z 0 H C
            case 0x82:
                label = "ADD A,D  1:4  Z 0 H C";
                break;

            //ADD A,E  1:4  Z 0 H C
            case 0x83:
                label = "ADD A,E  1:4  Z 0 H C";
                break;

            //ADD A,H  1:4  Z 0 H C
            case 0x84:
                label = "ADD A,H  1:4  Z 0 H C";
                break;

            //ADD A,L  1:4  Z 0 H C
            case 0x85:
                label = "ADD A,L  1:4  Z 0 H C";
                break;

            //ADD A,(HL)  1:8  Z 0 H C
            case 0x86:
                label = "ADD A,(HL)  1:8  Z 0 H C";
                break;

            //ADD A,A  1:4  Z 0 H C
            case 0x87:
                label = "ADD A,A  1:4  Z 0 H C";
                break;

            //ADC A,B  1:4  Z 0 H C
            case 0x88:
                label = "ADC A,B  1:4  Z 0 H C";
                break;

            //ADC A,C  1:4  Z 0 H C
            case 0x89:
                label = "ADC A,C  1:4  Z 0 H C";
                break;

            //ADC A,D  1:4  Z 0 H C
            case 0x8A:
                label = "ADC A,D  1:4  Z 0 H C";
                break;

            //ADC A,E  1:4  Z 0 H C
            case 0x8B:
                label = "ADC A,E  1:4  Z 0 H C";
                break;

            //ADC A,H  1:4  Z 0 H C
            case 0x8C:
                label = "ADC A,H  1:4  Z 0 H C";
                break;

            //ADC A,L  1:4  Z 0 H C
            case 0x8D:
                label = "ADC A,L  1:4  Z 0 H C";
                break;

            //ADC A,(HL)  1:8  Z 0 H C
            case 0x8E:
                label = "ADC A,(HL)  1:8  Z 0 H C";
                break;

            //ADC A,A  1:4  Z 0 H C  
            case 0x8F:
                label = "ADC A,A  1:4  Z 0 H C  ";
                break;

            //SUB B  1:4  Z 1 H C
            case 0x90:
                label = "SUB B  1:4  Z 1 H C";
                break;

            //SUB C  1:4  Z 1 H C
            case 0x91:
                label = "SUB C  1:4  Z 1 H C";
                break;

            //SUB D  1:4  Z 1 H C
            case 0x92:
                label = "SUB D  1:4  Z 1 H C";
                break;

            //SUB E  1:4  Z 1 H C
            case 0x93:
                label = "SUB E  1:4  Z 1 H C";
                break;

            //SUB H  1:4  Z 1 H C
            case 0x94:
                label = "SUB H  1:4  Z 1 H C";
                break;

            //SUB L  1:4  Z 1 H C
            case 0x95:
                label = "SUB L  1:4  Z 1 H C";
                break;

            //SUB (HL)  1:8  Z 1 H C
            case 0x96:
                label = "SUB (HL)  1:8  Z 1 H C";
                break;

            //SUB A  1:4  Z 1 H C
            case 0x97:
                label = "SUB A  1:4  Z 1 H C";
                break;

            //SBC A,B  1:4  Z 1 H C
            case 0x98:
                label = "SBC A,B  1:4  Z 1 H C";
                break;

            //SBC A,C  1:4  Z 1 H C
            case 0x99:
                label = "SBC A,C  1:4  Z 1 H C";
                break;

            //SBC A,D  1:4  Z 1 H C
            case 0x9A:
                label = "SBC A,D  1:4  Z 1 H C";
                break;

            //SBC A,E  1:4  Z 1 H C
            case 0x9B:
                label = "SBC A,E  1:4  Z 1 H C";
                break;

            //SBC A,H  1:4  Z 1 H C
            case 0x9C:
                label = "SBC A,H  1:4  Z 1 H C";
                break;

            //SBC A,L  1:4  Z 1 H C
            case 0x9D:
                label = "SBC A,L  1:4  Z 1 H C";
                break;

            //SBC A,(HL)  1:8  Z 1 H C
            case 0x9E:
                label = "SBC A,(HL)  1:8  Z 1 H C";
                break;

            //SBC A,A  1:4  Z 1 H C  
            case 0x9F:
                label = "SBC A,A  1:4  Z 1 H C  ";
                break;

            //AND B  1:4  Z 0 1 0
            case 0xA0:
                label = "AND B  1:4  Z 0 1 0";
                break;

            //AND C  1:4  Z 0 1 0
            case 0xA1:
                label = "AND C  1:4  Z 0 1 0";
                break;

            //AND D  1:4  Z 0 1 0
            case 0xA2:
                label = "AND D  1:4  Z 0 1 0";
                break;

            //AND E  1:4  Z 0 1 0
            case 0xA3:
                label = "AND E  1:4  Z 0 1 0";
                break;

            //AND H  1:4  Z 0 1 0
            case 0xA4:
                label = "AND H  1:4  Z 0 1 0";
                break;

            //AND L  1:4  Z 0 1 0
            case 0xA5:
                label = "AND L  1:4  Z 0 1 0";
                break;

            //AND (HL)  1:8  Z 0 1 0
            case 0xA6:
                label = "AND (HL)  1:8  Z 0 1 0";
                break;

            //AND A  1:4  Z 0 1 0
            case 0xA7:
                label = "AND A  1:4  Z 0 1 0";
                A = (byte) (A & memory[PC + 1]);
                if (A == 0)
                {
                    setF(F_Z, 1);
                }
                setF(F_N, 0);
                setF(F_H, 1);
                setF(F_C, 0);

                PC++;
                break;

            //XOR B  1:4  Z 0 0 0
            case 0xA8:
                label = "XOR B  1:4  Z 0 0 0";
                break;

            //XOR C  1:4  Z 0 0 0
            case 0xA9:
                label = "XOR C  1:4  Z 0 0 0";
                break;

            //XOR D  1:4  Z 0 0 0
            case 0xAA:
                label = "XOR D  1:4  Z 0 0 0";
                break;

            //XOR E  1:4  Z 0 0 0
            case 0xAB:
                label = "XOR E  1:4  Z 0 0 0";
                break;

            //XOR H  1:4  Z 0 0 0
            case 0xAC:
                label = "XOR H  1:4  Z 0 0 0";
                break;

            //XOR L  1:4  Z 0 0 0
            case 0xAD:
                label = "XOR L  1:4  Z 0 0 0";
                break;

            //XOR (HL)  1:8  Z 0 0 0
            case 0xAE:
                label = "XOR (HL)  1:8  Z 0 0 0";
                break;

            //XOR A  1:4  Z 0 0 0  
            case 0xAF:
                label = "XOR A  1:4  Z 0 0 0  ";
                A = (byte) ((A ^ A) & 0xff);
                if (A == 0)
                {
                    setF(F_Z, 1);
                }
                setF(F_N, 0);
                setF(F_H, 0);
                setF(F_C, 0);
                PC++;
                break;

            //OR B  1:4  Z 0 0 0
            case 0xB0:
                label = "OR B  1:4  Z 0 0 0";
                break;

            //OR C  1:4  Z 0 0 0
            case 0xB1:
                label = "OR C  1:4  Z 0 0 0";
                break;

            //OR D  1:4  Z 0 0 0
            case 0xB2:
                label = "OR D  1:4  Z 0 0 0";
                break;

            //OR E  1:4  Z 0 0 0
            case 0xB3:
                label = "OR E  1:4  Z 0 0 0";
                break;

            //OR H  1:4  Z 0 0 0
            case 0xB4:
                label = "OR H  1:4  Z 0 0 0";
                break;

            //OR L  1:4  Z 0 0 0
            case 0xB5:
                label = "OR L  1:4  Z 0 0 0";
                break;

            //OR (HL)  1:8  Z 0 0 0
            case 0xB6:
                label = "OR (HL)  1:8  Z 0 0 0";
                break;

            //OR A  1:4  Z 0 0 0
            case 0xB7:
                label = "OR A  1:4  Z 0 0 0";
                break;

            //CP B  1:4  Z 1 H C
            case 0xB8:
                label = "CP B  1:4  Z 1 H C";
                break;

            //CP C  1:4  Z 1 H C
            case 0xB9:
                label = "CP C  1:4  Z 1 H C";
                break;

            //CP D  1:4  Z 1 H C
            case 0xBA:
                label = "CP D  1:4  Z 1 H C";
                break;

            //CP E  1:4  Z 1 H C
            case 0xBB:
                label = "CP E  1:4  Z 1 H C";
                break;

            //CP H  1:4  Z 1 H C
            case 0xBC:
                label = "CP H  1:4  Z 1 H C";
                break;

            //CP L  1:4  Z 1 H C
            case 0xBD:
                label = "CP L  1:4  Z 1 H C";
                break;

            //CP (HL)  1:8  Z 1 H C
            case 0xBE:
                label = "CP (HL)  1:8  Z 1 H C";
                break;

            //CP A  1:4  Z 1 H C  
            case 0xBF:
                label = "CP A  1:4  Z 1 H C  ";
                break;

            //RET NZ  1:20/8  - - - -
            case 0xC0:
                label = "RET NZ  1:20/8  - - - -";
                break;

            //POP BC  1:12  - - - -
            case 0xC1:
                label = "POP BC  1:12  - - - -";
                break;

            //JP NZ,a16  3:16/12  - - - -
            case 0xC2:
                label = "JP NZ,a16  3:16/12  - - - -";
                break;

            //JP a16  3:16  - - - -
            case 0xC3:
                label = "JP a16  3:16  - - - -";
                PC = (short) this.combine(memory[PC + 2], memory[PC + 1]);
                break;

            //CALL NZ,a16  3:24/12  - - - -
            case 0xC4:
                label = "CALL NZ,a16  3:24/12  - - - -";
                break;

            //PUSH BC  1:16  - - - -
            case 0xC5:
                label = "PUSH BC  1:16  - - - -";
                break;

            //ADD A,d8  2:8  Z 0 H C
            case 0xC6:
                label = "ADD A,d8  2:8  Z 0 H C";
                break;

            //RST 00H  1:16  - - - -
            case 0xC7:
                label = "RST 00H  1:16  - - - -";
                break;

            //RET Z  1:20/8  - - - -
            case 0xC8:
                label = "RET Z  1:20/8  - - - -";
                break;

            //RET  1:16  - - - -
            case 0xC9:
                label = "RET  1:16  - - - -";
                break;

            //JP Z,a16  3:16/12  - - - -
            case 0xCA:
                label = "JP Z,a16  3:16/12  - - - -";
                break;

            //PREFIX CB  1:4  - - - -
            case 0xCB:
                label = "PREFIX CB  1:4  - - - -";
                PC++;
                switch (memory[PC] & 0xFF)
                {
                    case 0x37: // SWAP A
                        A = (byte) (((A >> 4) & 0x0F) | ((A << 4) & 0xF0));
                        if (A == 0)
                        {
                            setF(F_Z, 1);
                        }
                        PC++;
                        break;
                }
                break;

            //CALL Z,a16  3:24/12  - - - -
            case 0xCC:
                label = "CALL Z,a16  3:24/12  - - - -";
                if (getF(F_Z) == 1)
                {
                    PC = (short) combine(memory[PC + 2], memory[PC + 1]);
                }
                else
                {
                    PC += 2;
                }
                break;

            //CALL a16  3:24  - - - -
            case 0xCD:
                label = "CALL a16  3:24  - - - -";
                break;

            //ADC A,d8  2:8  Z 0 H C
            case 0xCE:
                label = "ADC A,d8  2:8  Z 0 H C";
                A = (byte) (memory[PC + 1] + getF(F_C));
                if (A == 0)
                {
                    setF(F_Z, 1);
                }
                setF(F_N, 0);
                if (getBit(A, 3) == 1)
                {
                    setF(F_H, 1); // if carry from bit 3
                }
                if (getBit(A, 7) == 1)
                {
                    setF(F_C, 1); // if carry from bit 7
                }
                PC += 2;
                break;

            //RST 08H  1:16  - - - -  
            case 0xCF:
                label = "RST 08H  1:16  - - - -  ";
                break;

            //RET NC  1:20/8  - - - -
            case 0xD0:
                label = "RET NC  1:20/8  - - - -";
                break;

            //POP DE  1:12  - - - -
            case 0xD1:
                label = "POP DE  1:12  - - - -";
                break;

            //JP NC,a16  3:16/12  - - - -
            case 0xD2:
                label = "JP NC,a16  3:16/12  - - - -";
                break;

            //CALL NC,a16  3:24/12  - - - -
            case 0xD4:
                label = "CALL NC,a16  3:24/12  - - - -";
                break;

            //PUSH DE  1:16  - - - -
            case 0xD5:
                label = "PUSH DE  1:16  - - - -";
                break;

            //SUB d8  2:8  Z 1 H C
            case 0xD6:
                label = "SUB d8  2:8  Z 1 H C";
                break;

            //RST 10H  1:16  - - - -
            case 0xD7:
                label = "RST 10H  1:16  - - - -";
                break;

            //RET C  1:20/8  - - - -
            case 0xD8:
                label = "RET C  1:20/8  - - - -";
                break;

            //RETI  1:16  - - - -
            case 0xD9:
                label = "RETI  1:16  - - - -";
                break;

            //JP C,a16  3:16/12  - - - -
            case 0xDA:
                label = "JP C,a16  3:16/12  - - - -";
                break;

            //CALL C,a16  3:24/12  - - - -
            case 0xDC:
                label = "CALL C,a16  3:24/12  - - - -";
                break;

            //SBC A,d8  2:8  Z 1 H C
            case 0xDE:
                label = "SBC A,d8  2:8  Z 1 H C";
                break;

            //RST 18H  1:16  - - - -  
            case 0xDF:
                label = "RST 18H  1:16  - - - -  ";
                break;

            //LDH (a8),A  2:12  - - - -
            case 0xE0:
                label = "LDH (a8),A  2:12  - - - -";
                memory[0xFF00 + (memory[PC + 1] & 0xff)] = A;
                PC += 2;
                break;

            //POP HL  1:12  - - - -
            case 0xE1:
                label = "POP HL  1:12  - - - -";
                break;

            //LD (C),A  2:8  - - - -
            case 0xE2:
                label = "LD (C),A  2:8  - - - -";
                break;

            //PUSH HL  1:16  - - - -
            case 0xE5:
                label = "PUSH HL  1:16  - - - -";
                break;

            //AND d8  2:8  Z 0 1 0
            case 0xE6:
                label = "AND d8  2:8  Z 0 1 0";
                A = (byte) (A & memory[PC + 1]);
                if (A == 0)
                {
                    setF(F_Z, 1);
                }
                setF(F_N, 0);
                setF(F_H, 1);
                setF(F_C, 0);
                PC += 2;
                break;

            //RST 20H  1:16  - - - -
            case 0xE7:
                label = "RST 20H  1:16  - - - -";
                break;

            //ADD SP,r8  2:16  0 0 H C
            case 0xE8:
                label = "ADD SP,r8  2:16  0 0 H C";
                break;

            //JP (HL)  1:4  - - - -
            case 0xE9:
                label = "JP (HL)  1:4  - - - -";
                break;

            //LD (a16),A  3:16  - - - -
            case 0xEA:
                label = "LD (a16),A  3:16  - - - -";
                memory[this.combine(memory[PC + 2], memory[PC + 1])] = A;
                PC += 3;
                break;

            //XOR d8  2:8  Z 0 0 0
            case 0xEE:
                label = "XOR d8  2:8  Z 0 0 0";
                break;

            //RST 28H  1:16  - - - -  
            case 0xEF:
                label = "RST 28H  1:16  - - - -  ";
                break;

            //LDH A,(a8)  2:12  - - - -
            case 0xF0:
                label = "LDH A,(a8)  2:12  - - - -";
                A = memory[0xFF00 + (memory[PC + 1] & 0xff)];
                PC += 2;
                break;

            //POP AF  1:12  Z N H C
            case 0xF1:
                label = "POP AF  1:12  Z N H C";
                break;

            //LD A,(C)  2:8  - - - -
            case 0xF2:
                label = "LD A,(C)  2:8  - - - -";
                break;

            //DI  1:4  - - - -
            case 0xF3:
                label = "DI  1:4  - - - -";
                PC++;
                break;

            //PUSH AF  1:16  - - - -
            case 0xF5:
                label = "PUSH AF  1:16  - - - -";
                break;

            //OR d8  2:8  Z 0 0 0
            case 0xF6:
                label = "OR d8  2:8  Z 0 0 0";
                break;

            //RST 30H  1:16  - - - -
            case 0xF7:
                label = "RST 30H  1:16  - - - -";
                break;

            //LD HL,SP+r8  2:12  0 0 H C
            case 0xF8:
                label = "LD HL,SP+r8  2:12  0 0 H C";
                break;

            //LD SP,HL  1:8  - - - -
            case 0xF9:
                label = "LD SP,HL  1:8  - - - -";
                break;

            //LD A,(a16)  3:16  - - - -
            case 0xFA:
                label = "LD A,(a16)  3:16  - - - -";
                A = memory[this.combine(memory[PC + 2], memory[PC + 1])];
                PC += 3;
                break;

            //EI  1:4  - - - -
            case 0xFB:
                label = "EI  1:4  - - - -";
                break;

            //CP d8  2:8  Z 1 H C
            case 0xFE:
                label = "CP d8  2:8  Z 1 H C";
                byte result = (byte) ((A - memory[PC]) & 0xff);
                if (result == 0)
                {
                    setF(F_Z, 1);
                }
                setF(F_N, 1);
                setF(F_H, 1); // FIXME set if no borrow from bit 4
                if (result < 0)
                {
                    setF(F_C, 1);
                }

                PC += 2;
                break;

            //RST 38H  1:16  - - - -
            case 0xFF:
                label = "RST 38H  1:16  - - - -";
                break;

            default:
                logger.error("opcode inconnus " + Debug.toHex(opCode));
        }
    }

    /**
     * combine 2 octets tels que
     * 0x12 | 0x34 => 0x1234
     *
     * @param a
     * @param b
     * @return
     */
    public int combine(byte a, byte b)
    {
        int result = ((((a << 8) & 0xffff | b & 0xff) & 0x0000ffff) & 0xffff);
        logger.debug(result);
        return result;
    }

    /**
     * met la valeure du flag a 1 ou 0
     * 
     * @param flag
     * @param value 
     */
    public void setF(int flag, int value)
    {
        if (value == 1)
        {
            F = (byte) (F | (1 << flag));
        }
        else
        {
            F = (byte) (F & ~(1 << flag));
        }
    }

    /**
     * 
     * @param flag
     * @return 
     */
    public int getF(int flag)
    {
        if ((F & (1 << flag)) != 0)
        {
            return 1;
        }
        return 0;
    }

    public int getBit(byte b, int pos)
    {
        if ((b & (1 << pos)) != 0)
        {
            return 1;
        }
        return 0;
    }
}
