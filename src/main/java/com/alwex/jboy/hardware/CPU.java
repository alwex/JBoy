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
            case 0x00: // NOP on ne fait rien
                PC++;
                break;

            case 0x05: // DEC B
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

            case 0x06: // LD B,d8
                B = memory[PC + 1];
                PC += 2;
                break;

            case 0x0B: // DEC BC
                int BC = combine(C, B);
                BC--;
                B = (byte) (BC & 0x00FF);
                C = (byte) (BC & 0xFF00);
                PC++;
                break;

            case 0x0E: // LD C,d8
                C = memory[PC + 1];
                PC += 2;
                break;

            case 0x18: // JR r8
                PC += (memory[PC + 1] & 0xff);
                break;

            case 0x20: // JR NZ,r8
                if (getF(F_Z) == 0)
                {
                    PC += (short) (memory[PC + 1]);
                }
                else
                {
                    PC += 2;
                }
                break;

            case 0x21: //LD HL,d16
                H = memory[PC + 2];
                L = memory[PC + 1];
                PC += 3;
                break;

            case 0x22: // LD (HL+),A
                addr = combine(H, L);
                memory[addr] = A;
                addr++;
                H = (byte) (addr & 0xFF00);
                L = (byte) (addr & 0x00FF);
                PC++;
                break;

            case 0x28: // JR Z,r8
                if (getF(F_Z) == 1)
                {
                    PC += (memory[PC + 1] & 0xff);
                }
                else
                {
                    PC += 2;
                }
                break;

            case 0x32: // LDD (HL),A
                addr = combine(H, L);
                memory[addr] = A;
                addr--;
                H = (byte) ((addr & 0xFF00) >> 8);
                L = (byte) (addr & 0x00FF);
                PC++;
                break;

            case 0x3E: // LD A, d8
                PC++;
                A = memory[PC];
                PC++;
                break;

            case 0x2F: // CPL compl�ment A, inverse tous les bits de A
                A = (byte) ~A;
                setF(F_N, 1);
                setF(F_H, 1);
                PC++;
                break;

            case 0x40: // LD B,B
                // B = B;
                PC++;
                break;

            case 0x66: // LD H,(HL)
                H = (byte) (H & 0xFF00 | L & 0x00FF);
                PC++;
                break;

            case 0xA7: // AND A
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

            case 0xAF: // XOR n,A
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

            case 0xC3: // JP a16
                PC = (short) this.combine(memory[PC + 2], memory[PC + 1]);
                break;

            case 0xCC: // CALL Z,a16
                if (getF(F_Z) == 1)
                {
                    PC = (short) combine(memory[PC + 2], memory[PC + 1]);
                }
                else
                {
                    PC += 2;
                }
                break;

            case 0xCE: // ADC A,d8
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

            case 0xE0: // LDH (n), A Put A into memory address $FF00+n.
                memory[0xFF00 + (memory[PC + 1] & 0xff)] = A;
                PC += 2;
                break;

            // AND d8 (A & memory[d8])
            // 2 8
            // Z 0 1 0
            case 0xE6:
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

            case 0xEA: // LD a16, A (placer A en m�moire � l'adresse a16 (deux prochain octets)
                memory[this.combine(memory[PC + 2], memory[PC + 1])] = A;
                PC += 3;
                break;


            case 0xFA: // LD A, a16 (place memoire[a16] dans A
                A = memory[this.combine(memory[PC + 2], memory[PC + 1])];
                PC += 3;
                break;

            case 0xCB:
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

            case 0xF0: // LDH A,(n)
                A = memory[0xFF00 + (memory[PC + 1] & 0xff)];
                PC += 2;
                break;

            case 0xF3: // DI TODO
                PC++;
                break;

            case 0xFE: // CP d8 compare A et d8
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
     * r�cup�re la valeure du bit
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
