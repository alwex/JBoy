package com.alwex.jboy.hardware;

import com.alwex.jboy.utils.ByteUtil;
import com.alwex.jboy.utils.Debug;
import org.apache.log4j.Logger;

/**
 *
 * @author Alex
 *
 * http://www.zophar.net/fileuploads/2/10809ozrmc/z80_faq.html
 * http://www.zophar.net/fileuploads/2/10810irctp/z80_faq2.html
 * http://nocash.emubase.de/pandocs.htm#videodisplay
 */
public class CPU extends AbstractHardware
{

    /**
     * identifie les diff�rents registres
     */
    public enum Register
    {

        A, B, C, D, E, F, H, L, HL, BC, DE, SP, F_C, F_H, F_N, F_Z, n, nn, _HL_;
    }
    // registres spéciaux valeurs en mémoire
    public int P1 = 0xFF00, // joypad infos and system type RW
            SB = 0xFF01, // serial transfers data RW
            SC = 0xFF02, // SIO control RW
            DIV = 0xFF04, // divider regsiter RW
            TIMA = 0xFF05, // timer counter RW
            TMA = 0xFF06, // timer modulo RW
            TAC = 0xFF7, // timer control RW
            IF = 0xFF0F, // interupt flag RW
            /*
            NR10 = 0xFF10, // sound mode 1 register RW
            NR11 = 0xFF11, // sound mode 1 register wave pattern duty RW
            NR12 = 0xFF12, // sound mode 1 register wave pattern duty RW
            NR13 = 0xFF13, // sound mode 1 register wave pattern duty RW
            NR14 = 0xFF14, // sound mode 1 register wave pattern duty RW
            NR21 = 0xFF16, // sound mode 1 register wave pattern duty RW
             */
            LCDC = 0xFF40, // LCD control RW
            STAT = 0xFF41, // LCD status RW
            SCY = 0xFF42, // BG scroll y RW
            SCX = 0xFF43; // BG scroll x RW
    protected static Logger logger;
    private static CPU instance;
    public final int F_C = 4, F_H = 5, F_N = 6, F_Z = 7;
    // registres du processeur
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
            // initialized to 0x0100
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
     * fonction de lecture de la mémoire
     *
     * @param memoryLocation
     * @return
     */
    public byte getByteAt(int memoryLocation)
    {
        return this.memory[memoryLocation];
    }

    /**
     * fonction d'écriture de la mémoire
     * 
     * @param memoryLocation
     * @param value
     */
    public void setByteAt(int memoryLocation, byte value)
    {
        this.memory[memoryLocation] = value;
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
        byte opCode = this.getByteAt(PC);
        int addr;
        byte[] splittedShort;

        String label = "";

        logger.info("=======================================");
        logger.info("[" + Debug.toHex(opCode) + "] values: "
                + Debug.toHex(this.getByteAt(PC + 1)) + " "
                + Debug.toHex(this.getByteAt(PC + 2)) + " "
                + Debug.toHex(this.getByteAt(PC + 3)) + " ");

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
                B = this.getByteAt(PC + 2);
                C = this.getByteAt(PC + 1);
                PC += 3;
                break;

            //LD (BC),A  1:8  - - - -
            case 0x02:
                label = "LD (BC),A  1:8  - - - -";
                this.setByteAt(ByteUtil.combine(B, C), A);
                PC += 1;
                break;

            //INC BC  1:8  - - - -
            case 0x03:
                label = "INC BC  1:8  - - - -";
                this.INC(Register.BC);
                break;

            //INC B  1:4  Z 0 H -
            case 0x04:
                label = "INC B  1:4  Z 0 H -";
                this.INC(Register.B);
                break;

            //DEC B  1:4  Z 1 H -
            case 0x05:
                label = "DEC B  1:4  Z 1 H -";
                this.DEC(Register.B);
                break;

            //LD B,d8  2:8  - - - -
            case 0x06:
                label = "LD B,d8  2:8  - - - -";
                B = this.getByteAt(PC + 1);
                PC += 2;
                break;

            //RLCA  1:4  0 0 0 C
            case 0x07:
                label = "RLCA  1:4  0 0 0 C";
                break;

            //LD (a16),SP  3:20  - - - -
            case 0x08:
                label = "LD (a16),SP  3:20  - - - -";
                this.setByteAt(ByteUtil.combine(this.getByteAt(PC + 2), this.getByteAt(PC + 1)), (byte) SP);
                PC += 3;
                break;

            //ADD HL,BC  1:8  - 0 H C
            case 0x09:
                label = "ADD HL,BC  1:8  - 0 H C";
                this.ADD_HL_XX(Register.BC);
                break;

            //LD A,(BC)  1:8  - - - -
            case 0x0A:
                label = "LD A,(BC)  1:8  - - - -";
                A = this.getByteAt(ByteUtil.combine(B, C));
                PC += 1;
                break;

            //DEC BC  1:8  - - - -
            case 0x0B:
                label = "DEC BC  1:8  - - - -";
                this.DEC(Register.BC);
                break;

            //INC C  1:4  Z 0 H -
            case 0x0C:
                label = "INC C  1:4  Z 0 H -";
                this.INC(Register.C);
                break;

            //DEC C  1:4  Z 1 H -
            case 0x0D:
                label = "DEC C  1:4  Z 1 H -";
                this.DEC(Register.C);
                break;

            //LD C,d8  2:8  - - - -
            case 0x0E:
                label = "LD C,d8  2:8  - - - -";
                C = this.getByteAt(PC + 1);
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
                D = this.getByteAt(PC + 2);
                E = this.getByteAt(PC + 1);
                PC += 3;
                break;

            //LD (DE),A  1:8  - - - -
            case 0x12:
                label = "LD (DE),A  1:8  - - - -";
                this.setByteAt(ByteUtil.combine(D, E), A);
                PC += 1;
                break;

            //INC DE  1:8  - - - -
            case 0x13:
                label = "INC DE  1:8  - - - -";
                this.INC(Register.DE);
                break;

            //INC D  1:4  Z 0 H -
            case 0x14:
                label = "INC D  1:4  Z 0 H -";
                this.INC(Register.D);
                break;

            //DEC D  1:4  Z 1 H -
            case 0x15:
                label = "DEC D  1:4  Z 1 H -";
                this.DEC(Register.D);
                break;

            //LD D,d8  2:8  - - - -
            case 0x16:
                label = "LD D,d8  2:8  - - - -";
                D = this.getByteAt(PC + 1);
                PC += 2;
                break;

            //RLA  1:4  0 0 0 C
            case 0x17:
                label = "RLA  1:4  0 0 0 C";
                break;

            //JR r8  2:12  - - - -
            case 0x18:
                label = "JR r8  2:12  - - - -";
                PC += (this.getByteAt(PC + 1) & 0xff);
                break;

            //ADD HL,DE  1:8  - 0 H C
            case 0x19:
                label = "ADD HL,DE  1:8  - 0 H C";
                this.ADD_HL_XX(Register.DE);
                break;

            //LD A,(DE)  1:8  - - - -
            case 0x1A:
                label = "LD A,(DE)  1:8  - - - -";
                A = this.getByteAt(ByteUtil.combine(D, E));
                PC += 1;
                break;

            //DEC DE  1:8  - - - -
            case 0x1B:
                label = "DEC DE  1:8  - - - -";
                this.DEC(Register.DE);
                break;

            //INC E  1:4  Z 0 H -
            case 0x1C:
                label = "INC E  1:4  Z 0 H -";
                this.INC(Register.E);
                break;

            //DEC E  1:4  Z 1 H -
            case 0x1D:
                label = "DEC E  1:4  Z 1 H -";
                this.DEC(Register.E);
                break;

            //LD E,d8  2:8  - - - -
            case 0x1E:
                label = "LD E,d8  2:8  - - - -";
                E = this.getByteAt(PC + 1);
                PC += 2;
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
                    PC += (this.getByteAt(PC + 1));
                }
                PC += 2;
                break;

            //LD HL,d16  3:12  - - - -
            case 0x21:
                label = "LD HL,d16  3:12  - - - -";
                H = this.getByteAt(PC + 2);
                L = this.getByteAt(PC + 1);
                PC += 3;
                break;

            //LD (HL+),A  1:8  - - - -
            case 0x22:
                label = "LD (HL+),A  1:8  - - - -";
                this.LD_HL(Register.A);
                this.INC(Register.HL);
                PC--;
                break;

            //INC HL  1:8  - - - -
            case 0x23:
                label = "INC HL  1:8  - - - -";
                this.INC(Register.HL);
                break;

            //INC H  1:4  Z 0 H -
            case 0x24:
                label = "INC H  1:4  Z 0 H -";
                this.INC(Register.H);
                break;

            //DEC H  1:4  Z 1 H -
            case 0x25:
                label = "DEC H  1:4  Z 1 H -";
                this.DEC(Register.H);
                break;

            //LD H,d8  2:8  - - - -
            case 0x26:
                label = "LD H,d8  2:8  - - - -";
                H = this.getByteAt(PC + 1);
                PC += 2;
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
                    PC += (this.getByteAt(PC + 1) & 0xff);
                }
                PC += 2;
                break;

            //ADD HL,HL  1:8  - 0 H C
            case 0x29:
                label = "ADD HL,HL  1:8  - 0 H C";
                this.ADD_HL_XX(Register.HL);
                break;

            //LD A,(HL+)  1:8  - - - -
            case 0x2A:
                label = "LD A,(HL+)  1:8  - - - -";
                A = this.getByteAt(ByteUtil.combine(H, L));
                this.INC(Register.HL);
                break;

            //DEC HL  1:8  - - - -
            case 0x2B:
                label = "DEC HL  1:8  - - - -";
                this.DEC(Register.HL);
                break;

            //INC L  1:4  Z 0 H -
            case 0x2C:
                label = "INC L  1:4  Z 0 H -";
                this.INC(Register.L);
                break;

            //DEC L  1:4  Z 1 H -
            case 0x2D:
                label = "DEC L  1:4  Z 1 H -";
                this.DEC(Register.L);
                break;

            //LD L,d8  2:8  - - - -
            case 0x2E:
                label = "LD L,d8  2:8  - - - -";
                L = this.getByteAt(PC + 1);
                PC += 2;
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
                if (getF(F_C) == 0)
                {
                    PC += (this.getByteAt(PC + 1) & 0xff);
                }
                PC += 2;
                break;

            //LD SP,d16  3:12  - - - -
            case 0x31:
                label = "LD SP,d16  3:12  - - - -";
                SP = (short) ByteUtil.combine(this.getByteAt(PC + 2), this.getByteAt(PC + 1));
                PC += 3;
                break;

            //LD (HL-),A  1:8  - - - -
            case 0x32:
                label = "LD (HL-),A  1:8  - - - -";
                this.LD_HL(Register.A);
                this.DEC(Register.HL);
                PC--;
                break;

            //INC SP  1:8  - - - -
            case 0x33:
                label = "INC SP  1:8  - - - -";
                this.INC(Register.SP);
                break;

            //INC (HL)  1:12  Z 0 H -
            case 0x34:
                label = "INC (HL)  1:12  Z 0 H -";
                this.INC(Register._HL_);
                break;

            //DEC (HL)  1:12  Z 1 H -
            case 0x35:
                label = "DEC (HL)  1:12  Z 1 H -";
                this.DEC(Register._HL_);
                break;

            //LD (HL),d8  2:12  - - - -
            case 0x36:
                label = "LD (HL),d8  2:12  - - - -";
                this.setByteAt(ByteUtil.combine(H, L), this.getByteAt(PC + 1));
                PC += 2;
                break;

            //SCF  1:4  - 0 0 1
            case 0x37:
                label = "SCF  1:4  - 0 0 1";
                break;

            //JR C,r8  2:12/8  - - - -
            case 0x38:
                label = "JR C,r8  2:12/8  - - - -";
                if (getF(F_C) == 1)
                {
                    PC += (this.getByteAt(PC + 1) & 0xff);
                }
                PC += 2;
                break;

            //ADD HL,SP  1:8  - 0 H C
            case 0x39:
                label = "ADD HL,SP  1:8  - 0 H C";
                this.ADD_HL_XX(Register.SP);
                break;

            //LD A,(HL-)  1:8  - - - -
            case 0x3A:
                label = "LD A,(HL-)  1:8  - - - -";
                A = this.getByteAt(ByteUtil.combine(H, L));
                this.DEC(Register.HL);
                break;

            //DEC SP  1:8  - - - -
            case 0x3B:
                label = "DEC SP  1:8  - - - -";
                this.DEC(Register.SP);
                break;

            //INC A  1:4  Z 0 H -
            case 0x3C:
                label = "INC A  1:4  Z 0 H -";
                this.INC(Register.A);
                break;

            //DEC A  1:4  Z 1 H -
            case 0x3D:
                label = "DEC A  1:4  Z 1 H -";
                this.DEC(Register.A);
                break;

            //LD A,d8  2:8  - - - -
            case 0x3E:
                label = "LD A,d8  2:8  - - - -";
                A = this.getByteAt(PC + 1);
                PC += 2;
                break;

            //CCF  1:4  - 0 0 C  
            case 0x3F:
                label = "CCF  1:4  - 0 0 C  ";
                break;

            //LD B,B  1:4  - - - -
            case 0x40:
                label = "LD B,B  1:4  - - - -";
                B = B;
                PC++;
                break;

            //LD B,C  1:4  - - - -
            case 0x41:
                label = "LD B,C  1:4  - - - -";
                B = C;
                PC++;
                break;

            //LD B,D  1:4  - - - -
            case 0x42:
                label = "LD B,D  1:4  - - - -";
                B = D;
                PC++;
                break;

            //LD B,E  1:4  - - - -
            case 0x43:
                label = "LD B,E  1:4  - - - -";
                B = E;
                PC++;
                break;

            //LD B,H  1:4  - - - -
            case 0x44:
                label = "LD B,H  1:4  - - - -";
                B = H;
                PC++;
                break;

            //LD B,L  1:4  - - - -
            case 0x45:
                label = "LD B,L  1:4  - - - -";
                B = L;
                PC++;
                break;

            //LD B,(HL)  1:8  - - - -
            case 0x46:
                label = "LD B,(HL)  1:8  - - - -";
                B = this.getByteAt(ByteUtil.combine(H, L));
                PC++;
                break;

            //LD B,A  1:4  - - - -
            case 0x47:
                label = "LD B,A  1:4  - - - -";
                B = A;
                PC++;
                break;

            //LD C,B  1:4  - - - -
            case 0x48:
                label = "LD C,B  1:4  - - - -";
                C = B;
                PC++;
                break;

            //LD C,C  1:4  - - - -
            case 0x49:
                label = "LD C,C  1:4  - - - -";
                C = C;
                PC++;
                break;

            //LD C,D  1:4  - - - -
            case 0x4A:
                label = "LD C,D  1:4  - - - -";
                C = D;
                PC++;
                break;

            //LD C,E  1:4  - - - -
            case 0x4B:
                label = "LD C,E  1:4  - - - -";
                C = E;
                PC++;
                break;

            //LD C,H  1:4  - - - -
            case 0x4C:
                label = "LD C,H  1:4  - - - -";
                C = H;
                PC++;
                break;

            //LD C,L  1:4  - - - -
            case 0x4D:
                label = "LD C,L  1:4  - - - -";
                C = L;
                PC++;
                break;

            //LD C,(HL)  1:8  - - - -
            case 0x4E:
                label = "LD C,(HL)  1:8  - - - -";
                C = this.getByteAt(ByteUtil.combine(H, L));
                PC++;
                break;

            //LD C,A  1:4  - - - -  
            case 0x4F:
                label = "LD C,A  1:4  - - - -  ";
                C = A;
                PC++;
                break;

            //LD D,B  1:4  - - - -
            case 0x50:
                label = "LD D,B  1:4  - - - -";
                D = B;
                PC++;
                break;

            //LD D,C  1:4  - - - -
            case 0x51:
                label = "LD D,C  1:4  - - - -";
                D = C;
                PC++;
                break;

            //LD D,D  1:4  - - - -
            case 0x52:
                label = "LD D,D  1:4  - - - -";
                D = D;
                PC++;
                break;

            //LD D,E  1:4  - - - -
            case 0x53:
                label = "LD D,E  1:4  - - - -";
                D = E;
                PC++;
                break;

            //LD D,H  1:4  - - - -
            case 0x54:
                label = "LD D,H  1:4  - - - -";
                D = H;
                PC++;
                break;

            //LD D,L  1:4  - - - -
            case 0x55:
                label = "LD D,L  1:4  - - - -";
                D = L;
                PC++;
                break;

            //LD D,(HL)  1:8  - - - -
            case 0x56:
                label = "LD D,(HL)  1:8  - - - -";
                D = this.getByteAt(ByteUtil.combine(H, L));
                PC++;
                break;

            //LD D,A  1:4  - - - -
            case 0x57:
                label = "LD D,A  1:4  - - - -";
                D = A;
                PC++;
                break;

            //LD E,B  1:4  - - - -
            case 0x58:
                label = "LD E,B  1:4  - - - -";
                E = B;
                PC++;
                break;

            //LD E,C  1:4  - - - -
            case 0x59:
                label = "LD E,C  1:4  - - - -";
                E = C;
                PC++;
                break;

            //LD E,D  1:4  - - - -
            case 0x5A:
                label = "LD E,D  1:4  - - - -";
                E = D;
                PC++;
                break;

            //LD E,E  1:4  - - - -
            case 0x5B:
                label = "LD E,E  1:4  - - - -";
                E = E;
                PC++;
                break;

            //LD E,H  1:4  - - - -
            case 0x5C:
                label = "LD E,H  1:4  - - - -";
                E = H;
                PC++;
                break;

            //LD E,L  1:4  - - - -
            case 0x5D:
                label = "LD E,L  1:4  - - - -";
                E = L;
                PC++;
                break;

            //LD E,(HL)  1:8  - - - -
            case 0x5E:
                label = "LD E,(HL)  1:8  - - - -";
                E = this.getByteAt(ByteUtil.combine(H, L));
                PC++;
                break;

            //LD E,A  1:4  - - - -  
            case 0x5F:
                label = "LD E,A  1:4  - - - -  ";
                E = A;
                PC++;
                break;

            //LD H,B  1:4  - - - -
            case 0x60:
                label = "LD H,B  1:4  - - - -";
                H = B;
                PC++;
                break;

            //LD H,C  1:4  - - - -
            case 0x61:
                label = "LD H,C  1:4  - - - -";
                H = C;
                PC++;
                break;

            //LD H,D  1:4  - - - -
            case 0x62:
                label = "LD H,D  1:4  - - - -";
                H = D;
                PC++;
                break;

            //LD H,E  1:4  - - - -
            case 0x63:
                label = "LD H,E  1:4  - - - -";
                H = E;
                PC++;
                break;

            //LD H,H  1:4  - - - -
            case 0x64:
                label = "LD H,H  1:4  - - - -";
                H = H;
                PC++;
                break;

            //LD H,L  1:4  - - - -
            case 0x65:
                label = "LD H,L  1:4  - - - -";
                H = L;
                PC++;
                break;

            //LD H,(HL)  1:8  - - - -
            case 0x66:
                label = "LD H,(HL)  1:8  - - - -";
                H = this.getByteAt(ByteUtil.combine(H, L));
                PC++;
                break;

            //LD H,A  1:4  - - - -
            case 0x67:
                label = "LD H,A  1:4  - - - -";
                H = A;
                PC++;
                break;

            //LD L,B  1:4  - - - -
            case 0x68:
                label = "LD L,B  1:4  - - - -";
                L = B;
                PC++;
                break;

            //LD L,C  1:4  - - - -
            case 0x69:
                label = "LD L,C  1:4  - - - -";
                L = C;
                PC++;
                break;

            //LD L,D  1:4  - - - -
            case 0x6A:
                label = "LD L,D  1:4  - - - -";
                L = D;
                PC++;
                break;

            //LD L,E  1:4  - - - -
            case 0x6B:
                label = "LD L,E  1:4  - - - -";
                L = E;
                PC++;
                break;

            //LD L,H  1:4  - - - -
            case 0x6C:
                label = "LD L,H  1:4  - - - -";
                L = H;
                PC++;
                break;

            //LD L,L  1:4  - - - -
            case 0x6D:
                label = "LD L,L  1:4  - - - -";
                L = L;
                PC++;
                break;

            //LD L,(HL)  1:8  - - - -
            case 0x6E:
                label = "LD L,(HL)  1:8  - - - -";
                L = this.getByteAt(ByteUtil.combine(H, L));
                PC++;
                break;

            //LD L,A  1:4  - - - -  
            case 0x6F:
                label = "LD L,A  1:4  - - - -  ";
                L = A;
                PC++;
                break;

            //LD (HL),B  1:8  - - - -
            case 0x70:
                label = "LD (HL),B  1:8  - - - -";
                this.LD_HL(Register.B);
                break;

            //LD (HL),C  1:8  - - - -
            case 0x71:
                label = "LD (HL),C  1:8  - - - -";
                this.LD_HL(Register.C);
                break;

            //LD (HL),D  1:8  - - - -
            case 0x72:
                label = "LD (HL),D  1:8  - - - -";
                this.LD_HL(Register.D);
                break;

            //LD (HL),E  1:8  - - - -
            case 0x73:
                label = "LD (HL),E  1:8  - - - -";
                this.LD_HL(Register.E);
                break;

            //LD (HL),H  1:8  - - - -
            case 0x74:
                label = "LD (HL),H  1:8  - - - -";
                this.LD_HL(Register.H);
                break;

            //LD (HL),L  1:8  - - - -
            case 0x75:
                label = "LD (HL),L  1:8  - - - -";
                this.LD_HL(Register.L);
                break;

            case 0x76:
                //HALT  1:4  - - - -
                label = "HALT  1:4  - - - -";
                break;

            //LD (HL),A  1:8  - - - -
            case 0x77:
                label = "LD (HL),A  1:8  - - - -";
                this.LD_HL(Register.A);
                break;

            //LD A,B  1:4  - - - -
            case 0x78:
                label = "LD A,B  1:4  - - - -";
                A = B;
                PC++;
                break;

            //LD A,C  1:4  - - - -
            case 0x79:
                label = "LD A,C  1:4  - - - -";
                A = C;
                PC++;
                break;

            //LD A,D  1:4  - - - -
            case 0x7A:
                label = "LD A,D  1:4  - - - -";
                A = D;
                PC++;
                break;

            //LD A,E  1:4  - - - -
            case 0x7B:
                label = "LD A,E  1:4  - - - -";
                A = E;
                PC++;
                break;

            //LD A,H  1:4  - - - -
            case 0x7C:
                label = "LD A,H  1:4  - - - -";
                A = H;
                PC++;
                break;

            //LD A,L  1:4  - - - -
            case 0x7D:
                label = "LD A,L  1:4  - - - -";
                A = L;
                PC++;
                break;

            //LD A,(HL)  1:8  - - - -
            case 0x7E:
                label = "LD A,(HL)  1:8  - - - -";
                A = this.getByteAt(ByteUtil.combine(H, L));
                PC++;
                break;

            //LD A,A  1:4  - - - -  
            case 0x7F:
                label = "LD A,A  1:4  - - - -  ";
                A = A;
                PC++;
                break;

            //ADD A,B  1:4  Z 0 H C
            case 0x80:
                label = "ADD A,B  1:4  Z 0 H C";
                this.ADD_X_X(Register.A, Register.B);
                break;

            //ADD A,C  1:4  Z 0 H C
            case 0x81:
                label = "ADD A,C  1:4  Z 0 H C";
                this.ADD_X_X(Register.A, Register.C);
                break;

            //ADD A,D  1:4  Z 0 H C
            case 0x82:
                label = "ADD A,D  1:4  Z 0 H C";
                this.ADD_X_X(Register.A, Register.D);
                break;

            //ADD A,E  1:4  Z 0 H C
            case 0x83:
                label = "ADD A,E  1:4  Z 0 H C";
                this.ADD_X_X(Register.A, Register.E);
                break;

            //ADD A,H  1:4  Z 0 H C
            case 0x84:
                label = "ADD A,H  1:4  Z 0 H C";
                this.ADD_X_X(Register.A, Register.H);
                break;

            //ADD A,L  1:4  Z 0 H C
            case 0x85:
                label = "ADD A,L  1:4  Z 0 H C";
                this.ADD_X_X(Register.A, Register.L);
                break;

            //ADD A,(HL)  1:8  Z 0 H C
            case 0x86:
                label = "ADD A,(HL)  1:8  Z 0 H C";
                this.ADD_X_X(Register.A, Register._HL_);
                break;

            //ADD A,A  1:4  Z 0 H C
            case 0x87:
                label = "ADD A,A  1:4  Z 0 H C";
                this.ADD_X_X(Register.A, Register.A);
                break;

            //ADC A,B  1:4  Z 0 H C
            case 0x88:
                label = "ADC A,B  1:4  Z 0 H C";
                this.ADC(Register.B);
                break;

            //ADC A,C  1:4  Z 0 H C
            case 0x89:
                label = "ADC A,C  1:4  Z 0 H C";
                this.ADC(Register.C);
                break;

            //ADC A,D  1:4  Z 0 H C
            case 0x8A:
                label = "ADC A,D  1:4  Z 0 H C";
                this.ADC(Register.D);
                break;

            //ADC A,E  1:4  Z 0 H C
            case 0x8B:
                label = "ADC A,E  1:4  Z 0 H C";
                this.ADC(Register.E);
                break;

            //ADC A,H  1:4  Z 0 H C
            case 0x8C:
                label = "ADC A,H  1:4  Z 0 H C";
                this.ADC(Register.H);
                break;

            //ADC A,L  1:4  Z 0 H C
            case 0x8D:
                label = "ADC A,L  1:4  Z 0 H C";
                this.ADC(Register.L);
                break;

            //ADC A,(HL)  1:8  Z 0 H C
            case 0x8E:
                label = "ADC A,(HL)  1:8  Z 0 H C";
                this.ADC(Register._HL_);
                break;

            //ADC A,A  1:4  Z 0 H C  
            case 0x8F:
                label = "ADC A,A  1:4  Z 0 H C  ";
                this.ADC(Register.A);
                break;

            //SUB B  1:4  Z 1 H C
            case 0x90:
                label = "SUB B  1:4  Z 1 H C";
                this.SUB(Register.B);
                break;

            //SUB C  1:4  Z 1 H C
            case 0x91:
                label = "SUB C  1:4  Z 1 H C";
                this.SUB(Register.C);
                break;

            //SUB D  1:4  Z 1 H C
            case 0x92:
                label = "SUB D  1:4  Z 1 H C";
                this.SUB(Register.D);
                break;

            //SUB E  1:4  Z 1 H C
            case 0x93:
                label = "SUB E  1:4  Z 1 H C";
                this.SUB(Register.E);
                break;

            //SUB H  1:4  Z 1 H C
            case 0x94:
                label = "SUB H  1:4  Z 1 H C";
                this.SUB(Register.H);
                break;

            //SUB L  1:4  Z 1 H C
            case 0x95:
                label = "SUB L  1:4  Z 1 H C";
                this.SUB(Register.L);
                break;

            //SUB (HL)  1:8  Z 1 H C
            case 0x96:
                label = "SUB (HL)  1:8  Z 1 H C";
                this.SUB(Register._HL_);
                break;

            //SUB A  1:4  Z 1 H C
            case 0x97:
                label = "SUB A  1:4  Z 1 H C";
                this.SUB(Register.A);
                break;

            //SBC A,B  1:4  Z 1 H C
            case 0x98:
                label = "SBC A,B  1:4  Z 1 H C";
                this.SBC(Register.B);
                break;

            //SBC A,C  1:4  Z 1 H C
            case 0x99:
                label = "SBC A,C  1:4  Z 1 H C";
                this.SBC(Register.C);
                break;

            //SBC A,D  1:4  Z 1 H C
            case 0x9A:
                label = "SBC A,D  1:4  Z 1 H C";
                this.SBC(Register.D);
                break;

            //SBC A,E  1:4  Z 1 H C
            case 0x9B:
                label = "SBC A,E  1:4  Z 1 H C";
                this.SBC(Register.E);
                break;

            //SBC A,H  1:4  Z 1 H C
            case 0x9C:
                label = "SBC A,H  1:4  Z 1 H C";
                this.SBC(Register.H);
                break;

            //SBC A,L  1:4  Z 1 H C
            case 0x9D:
                label = "SBC A,L  1:4  Z 1 H C";
                this.SBC(Register.L);
                break;

            //SBC A,(HL)  1:8  Z 1 H C
            case 0x9E:
                label = "SBC A,(HL)  1:8  Z 1 H C";
                this.SBC(Register._HL_);
                break;

            //SBC A,A  1:4  Z 1 H C  
            case 0x9F:
                label = "SBC A,A  1:4  Z 1 H C  ";
                this.SBC(Register.A);
                break;

            //AND B  1:4  Z 0 1 0
            case 0xA0:
                label = "AND B  1:4  Z 0 1 0";
                this.AND(Register.B);
                break;

            //AND C  1:4  Z 0 1 0
            case 0xA1:
                label = "AND C  1:4  Z 0 1 0";
                this.AND(Register.C);
                break;

            //AND D  1:4  Z 0 1 0
            case 0xA2:
                label = "AND D  1:4  Z 0 1 0";
                this.AND(Register.D);
                break;

            //AND E  1:4  Z 0 1 0
            case 0xA3:
                label = "AND E  1:4  Z 0 1 0";
                this.AND(Register.E);
                break;

            //AND H  1:4  Z 0 1 0
            case 0xA4:
                label = "AND H  1:4  Z 0 1 0";
                this.AND(Register.H);
                break;

            //AND L  1:4  Z 0 1 0
            case 0xA5:
                label = "AND L  1:4  Z 0 1 0";
                this.AND(Register.L);
                break;

            //AND (HL)  1:8  Z 0 1 0
            case 0xA6:
                label = "AND (HL)  1:8  Z 0 1 0";
                this.AND(Register._HL_);
                break;

            //AND A  1:4  Z 0 1 0
            case 0xA7:
                label = "AND A  1:4  Z 0 1 0";
                this.AND(Register.A);
                break;

            //XOR B  1:4  Z 0 0 0
            case 0xA8:
                label = "XOR B  1:4  Z 0 0 0";
                this.XOR(Register.B);
                break;

            //XOR C  1:4  Z 0 0 0
            case 0xA9:
                label = "XOR C  1:4  Z 0 0 0";
                this.XOR(Register.C);
                break;

            //XOR D  1:4  Z 0 0 0
            case 0xAA:
                label = "XOR D  1:4  Z 0 0 0";
                this.XOR(Register.D);
                break;

            //XOR E  1:4  Z 0 0 0
            case 0xAB:
                label = "XOR E  1:4  Z 0 0 0";
                this.XOR(Register.E);
                break;

            //XOR H  1:4  Z 0 0 0
            case 0xAC:
                label = "XOR H  1:4  Z 0 0 0";
                this.XOR(Register.H);
                break;

            //XOR L  1:4  Z 0 0 0
            case 0xAD:
                label = "XOR L  1:4  Z 0 0 0";
                this.XOR(Register.L);
                break;

            //XOR (HL)  1:8  Z 0 0 0
            case 0xAE:
                label = "XOR (HL)  1:8  Z 0 0 0";
                this.XOR(Register._HL_);
                break;

            //XOR A  1:4  Z 0 0 0  
            case 0xAF:
                label = "XOR A  1:4  Z 0 0 0  ";
                this.XOR(Register.A);
                break;

            //OR B  1:4  Z 0 0 0
            case 0xB0:
                label = "OR B  1:4  Z 0 0 0";
                this.OR(Register.B);
                break;

            //OR C  1:4  Z 0 0 0
            case 0xB1:
                label = "OR C  1:4  Z 0 0 0";
                this.OR(Register.C);
                break;

            //OR D  1:4  Z 0 0 0
            case 0xB2:
                label = "OR D  1:4  Z 0 0 0";
                this.OR(Register.D);
                break;

            //OR E  1:4  Z 0 0 0
            case 0xB3:
                label = "OR E  1:4  Z 0 0 0";
                this.OR(Register.E);
                break;

            //OR H  1:4  Z 0 0 0
            case 0xB4:
                label = "OR H  1:4  Z 0 0 0";
                this.OR(Register.H);
                break;

            //OR L  1:4  Z 0 0 0
            case 0xB5:
                label = "OR L  1:4  Z 0 0 0";
                this.OR(Register.L);
                break;

            //OR (HL)  1:8  Z 0 0 0
            case 0xB6:
                label = "OR (HL)  1:8  Z 0 0 0";
                this.OR(Register._HL_);
                break;

            //OR A  1:4  Z 0 0 0
            case 0xB7:
                label = "OR A  1:4  Z 0 0 0";
                this.OR(Register.A);
                break;

            //CP B  1:4  Z 1 H C
            case 0xB8:
                label = "CP B  1:4  Z 1 H C";
                this.CP(Register.B);
                break;

            //CP C  1:4  Z 1 H C
            case 0xB9:
                label = "CP C  1:4  Z 1 H C";
                this.CP(Register.C);
                break;

            //CP D  1:4  Z 1 H C
            case 0xBA:
                label = "CP D  1:4  Z 1 H C";
                this.CP(Register.D);
                break;

            //CP E  1:4  Z 1 H C
            case 0xBB:
                label = "CP E  1:4  Z 1 H C";
                this.CP(Register.E);
                break;

            //CP H  1:4  Z 1 H C
            case 0xBC:
                label = "CP H  1:4  Z 1 H C";
                this.CP(Register.H);
                break;

            //CP L  1:4  Z 1 H C
            case 0xBD:
                label = "CP L  1:4  Z 1 H C";
                this.CP(Register.L);
                break;

            //CP (HL)  1:8  Z 1 H C
            case 0xBE:
                label = "CP (HL)  1:8  Z 1 H C";
                this.CP(Register._HL_);
                break;

            //CP A  1:4  Z 1 H C  
            case 0xBF:
                label = "CP A  1:4  Z 1 H C  ";
                this.CP(Register.A);
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
                PC = (short) ByteUtil.combine(this.getByteAt(PC + 2), this.getByteAt(PC + 1));
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
                this.ADD_X_X(Register.A, Register.n);
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
                this.processCBOpCode();
                // FIXME
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
                    PC = (short) ByteUtil.combine(this.getByteAt(PC + 2), this.getByteAt(PC + 1));
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
                this.ADC(Register.n);
                /*
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
                 * 
                 */
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
                this.SUB(Register.n);
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
                this.SBC(Register.n);
                break;

            //RST 18H  1:16  - - - -  
            case 0xDF:
                label = "RST 18H  1:16  - - - -  ";
                break;

            //LDH (a8),A  2:12  - - - -
            case 0xE0:
                label = "LDH (a8),A  2:12  - - - -";
                this.setByteAt(0xFF00 + (this.getByteAt(PC + 1) & 0xff), A);
                PC += 2;
                break;

            //POP HL  1:12  - - - -
            case 0xE1:
                label = "POP HL  1:12  - - - -";
                break;

            //LD (C),A  2:8  - - - -
            case 0xE2:
                label = "LD (C),A  2:8  - - - -";
                this.setByteAt(0xFF00 + (C & 0xFF), A);
                PC += 2;
                break;

            //PUSH HL  1:16  - - - -
            case 0xE5:
                label = "PUSH HL  1:16  - - - -";
                break;

            //AND d8  2:8  Z 0 1 0
            case 0xE6:
                label = "AND d8  2:8  Z 0 1 0";
                this.AND(Register.n);
                break;

            //RST 20H  1:16  - - - -
            case 0xE7:
                label = "RST 20H  1:16  - - - -";
                break;

            //ADD SP,r8  2:16  0 0 H C
            case 0xE8:
                label = "ADD SP,r8  2:16  0 0 H C";
                this.ADD_X_X(Register.SP, Register.n);
                break;

            //JP (HL)  1:4  - - - -
            case 0xE9:
                label = "JP (HL)  1:4  - - - -";
                break;

            //LD (a16),A  3:16  - - - -
            case 0xEA:
                label = "LD (a16),A  3:16  - - - -";
                this.setByteAt(ByteUtil.combine(this.getByteAt(PC + 2), this.getByteAt(PC + 1)), A);
                PC += 3;
                break;

            //XOR d8  2:8  Z 0 0 0
            case 0xEE:
                label = "XOR d8  2:8  Z 0 0 0";
                this.XOR(Register.n);
                break;

            //RST 28H  1:16  - - - -  
            case 0xEF:
                label = "RST 28H  1:16  - - - -  ";
                break;

            //LDH A,(a8)  2:12  - - - -
            case 0xF0:
                label = "LDH A,(a8)  2:12  - - - -";
                A = this.getByteAt(0xFF00 + (this.getByteAt(PC + 1) & 0xff));
                PC += 2;
                break;

            //POP AF  1:12  Z N H C
            case 0xF1:
                label = "POP AF  1:12  Z N H C";
                break;

            //LD A,(C)  2:8  - - - -
            case 0xF2:
                label = "LD A,(C)  2:8  - - - -";
                A = this.getByteAt(0xFF00 + (C & 0xFF));
                PC += 2;
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
                this.OR(Register.n);
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
                SP = (short) ByteUtil.combine(H, L);
                PC += 1;
                break;

            //LD A,(a16)  3:16  - - - -
            case 0xFA:
                label = "LD A,(a16)  3:16  - - - -";
                A = this.getByteAt(ByteUtil.combine(this.getByteAt(PC + 2), this.getByteAt(PC + 1)));
                PC += 3;
                break;

            //EI  1:4  - - - -
            case 0xFB:
                label = "EI  1:4  - - - -";
                break;

            //CP d8  2:8  Z 1 H C
            case 0xFE:
                label = "CP d8  2:8  Z 1 H C";
                this.CP(Register.n);
                /*
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
                 * 
                 */
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
     * traitement des opcodes pr�fix�s par CB
     */
    private void processCBOpCode()
    {
        throw new UnsupportedOperationException("Not yet implemented");
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

    private void LD_HL(Register register)
    {
        byte value = 0;
        switch (register)
        {
            case A:
                value = A;
                break;
            case B:
                value = B;
                break;
            case C:
                value = C;
                break;
            case D:
                value = D;
                break;
            case E:
                value = E;
                break;
            case F:
                value = F;
                break;
            case H:
                value = H;
                break;
            case L:
                value = L;
                break;
            default:
                System.out.println("erreur pas géré le ld");
        }

        this.setByteAt(ByteUtil.combine(H, L), value);
        PC++;
    }

    /**
     * DEC n
     * @param register 
     */
    private void DEC(Register register)
    {
        int result = -1;
        boolean halfCarry = false;
        byte[] splittedShort;

        switch (register)
        {
            case A:
                A--;
                result = A;
                halfCarry = ((A & 0xf) == 0xf);
                break;
            case B:
                B--;
                result = B;
                halfCarry = ((B & 0xf) == 0xf);
                break;
            case C:
                C--;
                result = C;
                halfCarry = ((C & 0xf) == 0xf);
                break;
            case D:
                D--;
                result = D;
                halfCarry = ((D & 0xf) == 0xf);
                break;
            case E:
                E--;
                result = E;
                halfCarry = ((E & 0xf) == 0xf);
                break;
            case F:
                F--;
                result = F;
                halfCarry = ((F & 0xf) == 0xf);
                break;
            case H:
                H--;
                result = H;
                halfCarry = ((H & 0xf) == 0xf);
                break;
            case L:
                L--;
                result = L;
                halfCarry = ((L & 0xf) == 0xf);
                break;
            case BC:
                int BC = ByteUtil.combine(B, C);
                BC--;
                result = BC;
                B = (byte) (BC >> 8 & 0xFF);
                C = (byte) (BC & 0xFF);
                halfCarry = C == 0;
                break;
            case DE:
                int DE = ByteUtil.combine(D, E);
                DE--;
                result = DE;
                splittedShort = ByteUtil.split(result);
                D = splittedShort[0];
                E = splittedShort[1];
                break;
            case SP:
                SP--;
                result = SP;
                break;
            case HL:
                int HLValue = ByteUtil.combine(H, L);
                HLValue--;
                result = HLValue;
                splittedShort = ByteUtil.split(HLValue);
                H = splittedShort[0];
                L = splittedShort[1];
                break;
            case _HL_:
                memory[ByteUtil.combine(H, L)]--;
                break;
        }

        if (result == 0)
        {
            setF(F_Z, 1);
        }
        else
        {
            setF(F_Z, 0);
        }

        // soustraction donc 1
        setF(F_N, 1);

        // half carry
        if (halfCarry)
        {
            setF(F_H, 1);
        }

        PC++;
    }

    private void ADD_HL_XX(Register register)
    {
        byte a1 = 0, b1 = 0, a2 = 0, b2 = 0;
        int HLValue = ByteUtil.combine(H, L);

        a1 = H;
        b1 = L;

        switch (register)
        {
            case SP:
                HLValue += SP;
                break;
            case BC:
                int BCValue = ByteUtil.combine(B, C);
                HLValue += BCValue;
                break;
            case DE:
                int DEValue = ByteUtil.combine(D, E);
                HLValue += DEValue;
                break;
            case HL:
                int HLValue2 = ByteUtil.combine(H, L);
                HLValue += HLValue2;
                break;
        }

        H = (byte) (HLValue >> 8 & 0xFF);
        L = (byte) (HLValue & 0x00FF);

        a2 = H;
        b2 = L;

        setF(F_N, 0);

        if (b2 == 0x00)
        {
            setF(F_H, 1);
        }

        if ((a1 != a2) && (a2 == 0x00))
        {
            setF(F_C, 1);
        }
        PC += 1;
    }

    public void INC(Register register)
    {
        boolean doubleRegister = false;
        byte theValueAfter = 0x00;
        byte theValueBefore = 0x00;
        int doubleValue;
        byte[] splited;

        switch (register)
        {
            case A:
                theValueBefore = A;
                A++;
                theValueAfter = A;
                break;
            case B:
                theValueBefore = B;
                B++;
                theValueAfter = B;
                break;
            case C:
                theValueBefore = C;
                C++;
                theValueAfter = C;
                break;
            case D:
                theValueBefore = D;
                D++;
                theValueAfter = D;
                break;
            case E:
                theValueBefore = E;
                E++;
                theValueAfter = E;
                break;
            case F:
                theValueBefore = F;
                F++;
                theValueAfter = F;
                break;
            case H:
                theValueBefore = H;
                H++;
                theValueAfter = H;
                break;
            case L:
                theValueBefore = L;
                L++;
                theValueAfter = L;
                break;
            case HL:
                doubleRegister = true;
                doubleValue = ByteUtil.combine(H, L);
                doubleValue++;
                splited = ByteUtil.split(doubleValue);
                H = splited[0];
                L = splited[1];
                break;
            case BC:
                doubleRegister = true;
                doubleValue = ByteUtil.combine(B, C);
                doubleValue++;
                splited = ByteUtil.split(doubleValue);
                B = splited[0];
                C = splited[1];
                break;
            case DE:
                doubleRegister = true;
                doubleValue = ByteUtil.combine(D, E);
                doubleValue++;
                splited = ByteUtil.split(doubleValue);
                D = splited[0];
                E = splited[1];
                break;
            case SP:
                doubleRegister = true;
                SP++;
                break;
            case _HL_:
                memory[ByteUtil.combine(H, L)]++;
                doubleRegister = true;
                break;
        }

        if (!doubleRegister)
        {
            setF(F_N, 0);
            setF(F_Z, 0);
            if (theValueAfter == 0x00)
            {
                setF(F_Z, 1);
            }

            setF(F_H, 0);
            if ((((theValueAfter & 0x0f) - (theValueBefore & 0x0f)) & 0x10) != 0)
            {
                setF(F_H, 1);
            }
        }

        PC += 1;
    }

    private void ADD_X_X(Register register1, Register register2)
    {
        byte theValueBefore = 0x00;
        byte theValue = 0x00;
        byte theResult = 0x00;
        boolean halfCarry = false;

        switch (register2)
        {
            case A:
                theValue = A;
                break;
            case B:
                theValue = B;
                break;
            case C:
                theValue = C;
                break;
            case D:
                theValue = D;
                break;
            case E:
                theValue = E;
                break;
            case H:
                theValue = H;
                break;
            case L:
                theValue = L;
                break;
            case _HL_:
                theValue = this.getByteAt(ByteUtil.combine(H, L));
                break;
            case n:
                theValue = this.getByteAt(PC + 1);
                PC += 1;
                break;
            default:
        }


        switch (register1)
        {
            case A:
                theValueBefore = A;
                A += theValue;
                theResult = A;
                break;
            case B:
                theValueBefore = B;
                B += theValue;
                theResult = B;
                break;
            case C:
                theValueBefore = C;
                C += theValue;
                theResult = C;
                break;
            case D:
                theValueBefore = D;
                D += theValue;
                theResult = D;
                break;
            case E:
                theValueBefore = E;
                E += theValue;
                theResult = E;
                break;
            case H:
                theValueBefore = H;
                H += theValue;
                theResult = H;
                break;
            case L:
                theValueBefore = L;
                L += theValue;
                theResult = L;
                break;
            default:
        }

        if (this.needHalfCarry(theValueBefore, theResult))
        {
            halfCarry = true;
        }

        setF(F_Z, 0);
        if (theResult == 0)
        {
            setF(F_Z, 1);
        }

        setF(F_C, 0);
        if (this.needCarry(theValueBefore, theValue))
        {
            setF(F_C, 1);
        }

        // addition donc 0
        setF(F_N, 0);

        // half carry
        setF(F_H, 0);
        if (this.needHalfCarry(theValueBefore, theResult))
        {
            setF(F_H, 1);
        }

        PC += 1;
    }

    private void ADC(Register register)
    {
        byte theValueBefore = A;
        byte theValue = 0x00;
        byte theResult = 0x00;
        boolean halfCarry = false;

        switch (register)
        {
            case A:
                theValue = A;
                break;
            case B:
                theValue = B;
                break;
            case C:
                theValue = C;
                break;
            case D:
                theValue = D;
                break;
            case E:
                theValue = E;
                break;
            case H:
                theValue = H;
                break;
            case L:
                theValue = L;
                break;
            case _HL_:
                theValue = this.getByteAt(ByteUtil.combine(H, L));
                break;
            case n:
                theValue = this.getByteAt(PC + 1);
                PC += 1;
                break;
            default:
        }

        A += theValue;
        A += getF(F_C);

        theResult = A;

        if (this.needHalfCarry(theValueBefore, theResult))
        {
            halfCarry = true;
        }

        setF(F_Z, 0);
        if (theResult == 0)
        {
            setF(F_Z, 1);
        }

        // addition donc 0
        setF(F_N, 0);

        setF(F_C, 0);
        if (this.needCarry(theValueBefore, theValue))
        {
            setF(F_C, 1);
        }

        // half carry
        setF(F_H, 0);
        if (this.needHalfCarry(theValueBefore, theResult))
        {
            setF(F_H, 1);
        }

        PC += 1;
    }

    private void SUB(Register register)
    {
        byte theValue = 0x00;

        switch (register)
        {
            case A:
                theValue = A;
                break;
            case B:
                theValue = B;
                break;
            case C:
                theValue = C;
                break;
            case D:
                theValue = D;
                break;
            case E:
                theValue = E;
                break;
            case F:
                theValue = F;
                break;
            case H:
                theValue = H;
                break;
            case L:
                theValue = L;
                break;
            case _HL_:
                theValue = this.getByteAt(ByteUtil.combine(H, L));
                break;
            case n:
                theValue = this.getByteAt(PC + 1);
                PC += 1;
                break;
        }

        A -= theValue;

        // mise à jour des flags
        if (A == 0x00)
        {
            setF(F_Z, 1);
        }
        // soustraction donc 1
        setF(F_N, 1);

        setF(F_C, 0);
        if ((A & 0x100) != 0)
        {
            setF(F_C, 1);
        }

        setF(F_H, 0);
        if ((((A & 0x0f) - (theValue & 0x0f)) & 0x10) != 0)
        {
            setF(F_H, 1);
        }

        PC += 1;
    }

    private void SBC(Register register)
    {
        byte theValue = 0x00;

        switch (register)
        {
            case A:
                theValue = A;
                break;
            case B:
                theValue = B;
                break;
            case C:
                theValue = C;
                break;
            case D:
                theValue = D;
                break;
            case E:
                theValue = E;
                break;
            case F:
                theValue = F;
                break;
            case H:
                theValue = H;
                break;
            case L:
                theValue = L;
                break;
            case _HL_:
                theValue = this.getByteAt(ByteUtil.combine(H, L));
                break;
            case n:
                theValue = this.getByteAt(PC + 1);
                PC += 1;
                break;
        }

        A -= theValue;
        A -= getF(F_C);

        // mise à jour des flags
        if (A == 0x00)
        {
            setF(F_Z, 1);
        }
        // soustraction donc 1
        setF(F_N, 1);

        setF(F_C, 0);
        if ((A & 0x100) != 0)
        {
            setF(F_C, 1);
        }

        setF(F_H, 0);
        if ((((A & 0x0f) - (theValue & 0x0f)) & 0x10) != 0)
        {
            setF(F_H, 1);
        }

        PC += 1;
    }

    public void AND(Register register)
    {
        byte theValue = 0x00;

        switch (register)
        {
            case A:
                theValue = A;
                break;
            case B:
                theValue = B;
                break;
            case C:
                theValue = C;
                break;
            case D:
                theValue = D;
                break;
            case E:
                theValue = E;
                break;
            case F:
                theValue = F;
                break;
            case H:
                theValue = H;
                break;
            case L:
                theValue = L;
                break;
            case _HL_:
                theValue = this.getByteAt(ByteUtil.combine(H, L));
                break;
            case n:
                theValue = this.getByteAt(PC + 1);
                PC += 1;
        }

        A &= theValue;

        // mise à jour des flags
        if (A == 0x00)
        {
            setF(F_Z, 1);
        }

        setF(F_N, 0);
        setF(F_H, 1);
        setF(F_C, 0);

        PC += 1;
    }

    public void XOR(Register register)
    {
        byte theValue = 0x00;

        switch (register)
        {
            case A:
                theValue = A;
                break;
            case B:
                theValue = B;
                break;
            case C:
                theValue = C;
                break;
            case D:
                theValue = D;
                break;
            case E:
                theValue = E;
                break;
            case F:
                theValue = F;
                break;
            case H:
                theValue = H;
                break;
            case L:
                theValue = L;
                break;
            case _HL_:
                theValue = this.getByteAt(ByteUtil.combine(H, L));
                break;
            case n:
                theValue = this.getByteAt(PC + 1);
                PC += 1;
                break;
        }

        A = (byte) ((A ^ theValue) & 0xff);

        setF(F_Z, 0);
        if (A == 0x00)
        {
            setF(F_Z, 1);
        }
        setF(F_N, 0);
        setF(F_H, 0);
        setF(F_C, 0);

        PC += 1;
    }

    public void OR(Register register)
    {
        byte theValue = 0x00;

        switch (register)
        {
            case A:
                theValue = A;
                break;
            case B:
                theValue = B;
                break;
            case C:
                theValue = C;
                break;
            case D:
                theValue = D;
                break;
            case E:
                theValue = E;
                break;
            case F:
                theValue = F;
                break;
            case H:
                theValue = H;
                break;
            case L:
                theValue = L;
                break;
            case _HL_:
                theValue = this.getByteAt(ByteUtil.combine(H, L));
                break;
            case n:
                theValue = this.getByteAt(PC + 1);
                PC += 1;
                break;
        }

        A = (byte) ((A | theValue) & 0xff);

        setF(F_Z, 0);
        if (A == 0x00)
        {
            setF(F_Z, 1);
        }
        setF(F_N, 0);
        setF(F_H, 0);
        setF(F_C, 0);

        PC += 1;
    }

    public void CP(Register register)
    {
        byte theValue = 0x00;

        switch (register)
        {
            case A:
                theValue = A;
                break;
            case B:
                theValue = B;
                break;
            case C:
                theValue = C;
                break;
            case D:
                theValue = D;
                break;
            case E:
                theValue = E;
                break;
            case F:
                theValue = F;
                break;
            case H:
                theValue = H;
                break;
            case L:
                theValue = L;
                break;
            case _HL_:
                theValue = this.getByteAt(ByteUtil.combine(H, L));
                break;
            case n:
                theValue = this.getByteAt(PC + 1);
                PC += 1;
                break;
        }

        byte theResult = (byte) (A - theValue);

        setF(F_Z, 0);
        if (theResult == 0x00)
        {
            setF(F_Z, 1);
        }

        // soustraction
        setF(F_N, 1);

        setF(F_H, 0);
        if ((((theResult & 0x0f) - (theValue & 0x0f)) & 0x10) != 0)
        {
            setF(F_H, 1);
        }

        setF(F_C, 0);
        if ((theResult & 0x100) != 0)
        {
            setF(F_C, 1);
        }

        PC += 1;
    }

    public boolean needCarry(byte before, byte added)
    {
        boolean carry = false;
        if (((before & 0xff) + (added & 0xff)) > 0xFF)
        {
            carry = true;
        }

        return carry;
    }

    public boolean needHalfCarry(byte before, byte after)
    {
        boolean halfCarry = false;

        byte firstNibbleAfter = (byte) (after & 0xF0);
        byte firstNibbleBefore = (byte) (before & 0xF0);

        if (firstNibbleAfter != firstNibbleBefore)
        {
            halfCarry = true;
        }

        return halfCarry;
    }
}
