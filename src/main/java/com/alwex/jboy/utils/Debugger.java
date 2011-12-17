package com.alwex.jboy.utils;

/**
 *
 * @author alex
 */
public class Debugger
{

//    public int PC = 0;
    private String[] instruction;
    private byte[] theMemory;

    public Debugger(byte[] memory)
    {
        theMemory = memory;
    }

    public String parse(int PC)
    {
        String label = Debug.toHex((short) PC) + ": " + Debug.toHex(theMemory[PC]) + " => ";

        switch (theMemory[PC] & 0xff)
        {
            // NOP  1:4  - - - -
            case 0x00:
                label += "NOP  1:4  - - - -";
                PC += 1;
                break;

            //LD BC,d16  3:12  - - - -
            case 0x01:
                label += "LD BC,d16  3:12  - - - -";
                PC += 3;
                break;

            //LD (BC),A  1:8  - - - -
            case 0x02:
                label += "LD (BC),A  1:8  - - - -";
                PC += 1;
                break;

            //INC BC  1:8  - - - -
            case 0x03:
                label += "INC BC  1:8  - - - -";
                PC += 1;
                break;

            //INC B  1:4  Z 0 H -
            case 0x04:
                label += "INC B  1:4  Z 0 H -";
                PC += 1;
                break;

            //DEC B  1:4  Z 1 H -
            case 0x05:
                label += "DEC B  1:4  Z 1 H -";
                PC += 1;
                break;

            //LD B,d8  2:8  - - - -
            case 0x06:
                label += "LD B," + Debug.toHex(theMemory[PC + 1]) + "  2:8  - - - -";
                PC += 2;
                break;

            //RLCA  1:4  0 0 0 C
            case 0x07:
                label += "RLCA  1:4  0 0 0 C";
                PC += 1;
                break;

            //LD (a16),SP  3:20  - - - -
            case 0x08:
                label += "LD (" + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "),SP  3:20  - - - -";
                PC += 3;
                break;

            //ADD HL,BC  1:8  - 0 H C
            case 0x09:
                label += "ADD HL,BC  1:8  - 0 H C";
                PC += 1;
                break;

            //LD A,(BC)  1:8  - - - -
            case 0x0A:
                label += "LD A,(BC)  1:8  - - - -";
                PC += 1;
                break;

            //DEC BC  1:8  - - - -
            case 0x0B:
                label += "DEC BC  1:8  - - - -";
                PC += 1;
                break;

            //INC C  1:4  Z 0 H -
            case 0x0C:
                label += "INC C  1:4  Z 0 H -";
                PC += 1;
                break;

            //DEC C  1:4  Z 1 H -
            case 0x0D:
                label += "DEC C  1:4  Z 1 H -";
                PC += 1;
                break;

            //LD C,d8  2:8  - - - -
            case 0x0E:
                label += "LD C," + Debug.toHex(theMemory[PC + 1]) + "  2:8  - - - -";
                PC += 2;
                break;

            //RRCA  1:4  0 0 0 C  
            case 0x0F:
                label += "RRCA  1:4  0 0 0 C  ";
                PC += 1;
                break;

            //STOP 0  2:4  - - - -
            case 0x10:
                label += "STOP 0  2:4  - - - -";
                PC += 2;
                break;

            //LD DE,d16  3:12  - - - -
            case 0x11:
                label += "LD DE,d16  3:12  - - - -";
                PC += 3;
                break;

            //LD (DE),A  1:8  - - - -
            case 0x12:
                label += "LD (DE),A  1:8  - - - -";
                PC += 1;
                break;

            //INC DE  1:8  - - - -
            case 0x13:
                label += "INC DE  1:8  - - - -";
                PC += 1;
                break;

            //INC D  1:4  Z 0 H -
            case 0x14:
                label += "INC D  1:4  Z 0 H -";
                PC += 1;
                break;

            //DEC D  1:4  Z 1 H -
            case 0x15:
                label += "DEC D  1:4  Z 1 H -";
                PC += 1;
                break;

            //LD D,d8  2:8  - - - -
            case 0x16:
                label += "LD D," + Debug.toHex(theMemory[PC + 1]) + "  2:8  - - - -";
                PC += 2;
                break;

            //RLA  1:4  0 0 0 C
            case 0x17:
                label += "RLA  1:4  0 0 0 C";
                PC += 1;
                break;

            //JR r8  2:12  - - - -
            case 0x18:
                label += "JR r8  2:12  - - - -";
                PC += 2;
                break;

            //ADD HL,DE  1:8  - 0 H C
            case 0x19:
                label += "ADD HL,DE  1:8  - 0 H C";
                PC += 1;
                break;

            //LD A,(DE)  1:8  - - - -
            case 0x1A:
                label += "LD A,(DE)  1:8  - - - -";
                PC += 1;
                break;

            //DEC DE  1:8  - - - -
            case 0x1B:
                label += "DEC DE  1:8  - - - -";
                PC += 1;
                break;

            //INC E  1:4  Z 0 H -
            case 0x1C:
                label += "INC E  1:4  Z 0 H -";
                PC += 1;
                break;

            //DEC E  1:4  Z 1 H -
            case 0x1D:
                label += "DEC E  1:4  Z 1 H -";
                PC += 1;
                break;

            //LD E,d8  2:8  - - - -
            case 0x1E:
                label += "LD E," + Debug.toHex(theMemory[PC + 1]) + "  2:8  - - - -";
                PC += 2;
                break;

            //RRA  1:4  0 0 0 C  
            case 0x1F:
                label += "RRA  1:4  0 0 0 C  ";
                PC += 1;
                break;

            //JR NZ,r8  2:12/8  - - - -
            case 0x20:
                label += "JR NZ,r8  2:12/8  - - - -";
                PC += 2;
                break;

            //LD HL,d16  3:12  - - - -
            case 0x21:
                label += "LD HL,d16  3:12  - - - -";
                PC += 3;
                break;

            //LD (HL+),A  1:8  - - - -
            case 0x22:
                label += "LD (HL+),A  1:8  - - - -";
                PC += 1;
                break;

            //INC HL  1:8  - - - -
            case 0x23:
                label += "INC HL  1:8  - - - -";
                PC += 1;
                break;

            //INC H  1:4  Z 0 H -
            case 0x24:
                label += "INC H  1:4  Z 0 H -";
                PC += 1;
                break;

            //DEC H  1:4  Z 1 H -
            case 0x25:
                label += "DEC H  1:4  Z 1 H -";
                PC += 1;
                break;

            //LD H,d8  2:8  - - - -
            case 0x26:
                label += "LD H," + Debug.toHex(theMemory[PC + 1]) + "  2:8  - - - -";
                PC += 2;
                break;

            //DAA  1:4  Z - 0 C
            case 0x27:
                label += "DAA  1:4  Z - 0 C";
                PC += 1;
                break;

            //JR Z,r8  2:12/8  - - - -
            case 0x28:
                label += "JR Z,r8  2:12/8  - - - -";
                PC += 2;
                break;

            //ADD HL,HL  1:8  - 0 H C
            case 0x29:
                label += "ADD HL,HL  1:8  - 0 H C";
                PC += 1;
                break;

            //LD A,(HL+)  1:8  - - - -
            case 0x2A:
                label += "LD A,(HL+)  1:8  - - - -";
                PC += 1;
                break;

            //DEC HL  1:8  - - - -
            case 0x2B:
                label += "DEC HL  1:8  - - - -";
                PC += 1;
                break;

            //INC L  1:4  Z 0 H -
            case 0x2C:
                label += "INC L  1:4  Z 0 H -";
                PC += 1;
                break;

            //DEC L  1:4  Z 1 H -
            case 0x2D:
                label += "DEC L  1:4  Z 1 H -";
                PC += 1;
                break;

            //LD L,d8  2:8  - - - -
            case 0x2E:
                label += "LD L," + Debug.toHex(theMemory[PC + 1]) + "  2:8  - - - -";
                PC += 2;
                break;

            //CPL  1:4  - 1 1 -  
            case 0x2F:
                label += "CPL  1:4  - 1 1 -  ";
                PC += 1;
                break;

            //JR NC,r8  2:12/8  - - - -
            case 0x30:
                label += "JR NC,r8  2:12/8  - - - -";
                PC += 2;
                break;

            //LD SP,d16  3:12  - - - -
            case 0x31:
                label += "LD SP,d16  3:12  - - - -";
                PC += 3;
                break;

            //LD (HL-),A  1:8  - - - -
            case 0x32:
                label += "LD (HL-),A  1:8  - - - -";
                PC += 1;
                break;

            //INC SP  1:8  - - - -
            case 0x33:
                label += "INC SP  1:8  - - - -";
                PC += 1;
                break;

            //INC (HL)  1:12  Z 0 H -
            case 0x34:
                label += "INC (HL)  1:12  Z 0 H -";
                PC += 1;
                break;

            //DEC (HL)  1:12  Z 1 H -
            case 0x35:
                label += "DEC (HL)  1:12  Z 1 H -";
                PC += 1;
                break;

            //LD (HL),d8  2:12  - - - -
            case 0x36:
                label += "LD (HL)," + Debug.toHex(theMemory[PC + 1]) + "  2:12  - - - -";
                PC += 2;
                break;

            //SCF  1:4  - 0 0 1
            case 0x37:
                label += "SCF  1:4  - 0 0 1";
                PC += 1;
                break;

            //JR C,r8  2:12/8  - - - -
            case 0x38:
                label += "JR C,r8  2:12/8  - - - -";
                PC += 2;
                break;

            //ADD HL,SP  1:8  - 0 H C
            case 0x39:
                label += "ADD HL,SP  1:8  - 0 H C";
                PC += 1;
                break;

            //LD A,(HL-)  1:8  - - - -
            case 0x3A:
                label += "LD A,(HL-)  1:8  - - - -";
                PC += 1;
                break;

            //DEC SP  1:8  - - - -
            case 0x3B:
                label += "DEC SP  1:8  - - - -";
                PC += 1;
                break;

            //INC A  1:4  Z 0 H -
            case 0x3C:
                label += "INC A  1:4  Z 0 H -";
                PC += 1;
                break;

            //DEC A  1:4  Z 1 H -
            case 0x3D:
                label += "DEC A  1:4  Z 1 H -";
                PC += 1;
                break;

            //LD A,d8  2:8  - - - -
            case 0x3E:
                label += "LD A," + Debug.toHex(theMemory[PC + 1]) + "  2:8  - - - -";
                PC += 2;
                break;

            //CCF  1:4  - 0 0 C  
            case 0x3F:
                label += "CCF  1:4  - 0 0 C  ";
                PC += 1;
                break;

            //LD B,B  1:4  - - - -
            case 0x40:
                label += "LD B,B  1:4  - - - -";
                PC += 1;
                break;

            //LD B,C  1:4  - - - -
            case 0x41:
                label += "LD B,C  1:4  - - - -";
                PC += 1;
                break;

            //LD B,D  1:4  - - - -
            case 0x42:
                label += "LD B,D  1:4  - - - -";
                PC += 1;
                break;

            //LD B,E  1:4  - - - -
            case 0x43:
                label += "LD B,E  1:4  - - - -";
                PC += 1;
                break;

            //LD B,H  1:4  - - - -
            case 0x44:
                label += "LD B,H  1:4  - - - -";
                PC += 1;
                break;

            //LD B,L  1:4  - - - -
            case 0x45:
                label += "LD B,L  1:4  - - - -";
                PC += 1;
                break;

            //LD B,(HL)  1:8  - - - -
            case 0x46:
                label += "LD B,(HL)  1:8  - - - -";
                PC += 1;
                break;

            //LD B,A  1:4  - - - -
            case 0x47:
                label += "LD B,A  1:4  - - - -";
                PC += 1;
                break;

            //LD C,B  1:4  - - - -
            case 0x48:
                label += "LD C,B  1:4  - - - -";
                PC += 1;
                break;

            //LD C,C  1:4  - - - -
            case 0x49:
                label += "LD C,C  1:4  - - - -";
                PC += 1;
                break;

            //LD C,D  1:4  - - - -
            case 0x4A:
                label += "LD C,D  1:4  - - - -";
                PC += 1;
                break;

            //LD C,E  1:4  - - - -
            case 0x4B:
                label += "LD C,E  1:4  - - - -";
                PC += 1;
                break;

            //LD C,H  1:4  - - - -
            case 0x4C:
                label += "LD C,H  1:4  - - - -";
                PC += 1;
                break;

            //LD C,L  1:4  - - - -
            case 0x4D:
                label += "LD C,L  1:4  - - - -";
                PC += 1;
                break;

            //LD C,(HL)  1:8  - - - -
            case 0x4E:
                label += "LD C,(HL)  1:8  - - - -";
                PC += 1;
                break;

            //LD C,A  1:4  - - - -  
            case 0x4F:
                label += "LD C,A  1:4  - - - -  ";
                PC += 1;
                break;

            //LD D,B  1:4  - - - -
            case 0x50:
                label += "LD D,B  1:4  - - - -";
                PC += 1;
                break;

            //LD D,C  1:4  - - - -
            case 0x51:
                label += "LD D,C  1:4  - - - -";
                PC += 1;
                break;

            //LD D,D  1:4  - - - -
            case 0x52:
                label += "LD D,D  1:4  - - - -";
                PC += 1;
                break;

            //LD D,E  1:4  - - - -
            case 0x53:
                label += "LD D,E  1:4  - - - -";
                PC += 1;
                break;

            //LD D,H  1:4  - - - -
            case 0x54:
                label += "LD D,H  1:4  - - - -";
                PC += 1;
                break;

            //LD D,L  1:4  - - - -
            case 0x55:
                label += "LD D,L  1:4  - - - -";
                PC += 1;
                break;

            //LD D,(HL)  1:8  - - - -
            case 0x56:
                label += "LD D,(HL)  1:8  - - - -";
                PC += 1;
                break;

            //LD D,A  1:4  - - - -
            case 0x57:
                label += "LD D,A  1:4  - - - -";
                PC += 1;
                break;

            //LD E,B  1:4  - - - -
            case 0x58:
                label += "LD E,B  1:4  - - - -";
                PC += 1;
                break;

            //LD E,C  1:4  - - - -
            case 0x59:
                label += "LD E,C  1:4  - - - -";
                PC += 1;
                break;

            //LD E,D  1:4  - - - -
            case 0x5A:
                label += "LD E,D  1:4  - - - -";
                PC += 1;
                break;

            //LD E,E  1:4  - - - -
            case 0x5B:
                label += "LD E,E  1:4  - - - -";
                PC += 1;
                break;

            //LD E,H  1:4  - - - -
            case 0x5C:
                label += "LD E,H  1:4  - - - -";
                PC += 1;
                break;

            //LD E,L  1:4  - - - -
            case 0x5D:
                label += "LD E,L  1:4  - - - -";
                PC += 1;
                break;

            //LD E,(HL)  1:8  - - - -
            case 0x5E:
                label += "LD E,(HL)  1:8  - - - -";
                PC += 1;
                break;

            //LD E,A  1:4  - - - -  
            case 0x5F:
                label += "LD E,A  1:4  - - - -  ";
                PC += 1;
                break;

            //LD H,B  1:4  - - - -
            case 0x60:
                label += "LD H,B  1:4  - - - -";
                PC += 1;
                break;

            //LD H,C  1:4  - - - -
            case 0x61:
                label += "LD H,C  1:4  - - - -";
                PC += 1;
                break;

            //LD H,D  1:4  - - - -
            case 0x62:
                label += "LD H,D  1:4  - - - -";
                PC += 1;
                break;

            //LD H,E  1:4  - - - -
            case 0x63:
                label += "LD H,E  1:4  - - - -";
                PC += 1;
                break;

            //LD H,H  1:4  - - - -
            case 0x64:
                label += "LD H,H  1:4  - - - -";
                PC += 1;
                break;

            //LD H,L  1:4  - - - -
            case 0x65:
                label += "LD H,L  1:4  - - - -";
                PC += 1;
                break;

            //LD H,(HL)  1:8  - - - -
            case 0x66:
                label += "LD H,(HL)  1:8  - - - -";
                PC += 1;
                break;

            //LD H,A  1:4  - - - -
            case 0x67:
                label += "LD H,A  1:4  - - - -";
                PC += 1;
                break;

            //LD L,B  1:4  - - - -
            case 0x68:
                label += "LD L,B  1:4  - - - -";
                PC += 1;
                break;

            //LD L,C  1:4  - - - -
            case 0x69:
                label += "LD L,C  1:4  - - - -";
                PC += 1;
                break;

            //LD L,D  1:4  - - - -
            case 0x6A:
                label += "LD L,D  1:4  - - - -";
                PC += 1;
                break;

            //LD L,E  1:4  - - - -
            case 0x6B:
                label += "LD L,E  1:4  - - - -";
                PC += 1;
                break;

            //LD L,H  1:4  - - - -
            case 0x6C:
                label += "LD L,H  1:4  - - - -";
                PC += 1;
                break;

            //LD L,L  1:4  - - - -
            case 0x6D:
                label += "LD L,L  1:4  - - - -";
                PC += 1;
                break;

            //LD L,(HL)  1:8  - - - -
            case 0x6E:
                label += "LD L,(HL)  1:8  - - - -";
                PC += 1;
                break;

            //LD L,A  1:4  - - - -  
            case 0x6F:
                label += "LD L,A  1:4  - - - -  ";
                PC += 1;
                break;

            //LD (HL),B  1:8  - - - -
            case 0x70:
                label += "LD (HL),B  1:8  - - - -";
                PC += 1;
                break;

            //LD (HL),C  1:8  - - - -
            case 0x71:
                label += "LD (HL),C  1:8  - - - -";
                PC += 1;
                break;

            //LD (HL),D  1:8  - - - -
            case 0x72:
                label += "LD (HL),D  1:8  - - - -";
                PC += 1;
                break;

            //LD (HL),E  1:8  - - - -
            case 0x73:
                label += "LD (HL),E  1:8  - - - -";
                PC += 1;
                break;

            //LD (HL),H  1:8  - - - -
            case 0x74:
                label += "LD (HL),H  1:8  - - - -";
                PC += 1;
                break;

            //LD (HL),L  1:8  - - - -
            case 0x75:
                label += "LD (HL),L  1:8  - - - -";
                PC += 1;
                break;

            //HALT  1:4  - - - -
            case 0x76:
                label += "HALT  1:4  - - - -";
                PC += 1;
                break;

            //LD (HL),A  1:8  - - - -
            case 0x77:
                label += "LD (HL),A  1:8  - - - -";
                PC += 1;
                break;

            //LD A,B  1:4  - - - -
            case 0x78:
                label += "LD A,B  1:4  - - - -";
                PC += 1;
                break;

            //LD A,C  1:4  - - - -
            case 0x79:
                label += "LD A,C  1:4  - - - -";
                PC += 1;
                break;

            //LD A,D  1:4  - - - -
            case 0x7A:
                label += "LD A,D  1:4  - - - -";
                PC += 1;
                break;

            //LD A,E  1:4  - - - -
            case 0x7B:
                label += "LD A,E  1:4  - - - -";
                PC += 1;
                break;

            //LD A,H  1:4  - - - -
            case 0x7C:
                label += "LD A,H  1:4  - - - -";
                PC += 1;
                break;

            //LD A,L  1:4  - - - -
            case 0x7D:
                label += "LD A,L  1:4  - - - -";
                PC += 1;
                break;

            //LD A,(HL)  1:8  - - - -
            case 0x7E:
                label += "LD A,(HL)  1:8  - - - -";
                PC += 1;
                break;

            //LD A,A  1:4  - - - -  
            case 0x7F:
                label += "LD A,A  1:4  - - - -  ";
                PC += 1;
                break;

            //ADD A,B  1:4  Z 0 H C
            case 0x80:
                label += "ADD A,B  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADD A,C  1:4  Z 0 H C
            case 0x81:
                label += "ADD A,C  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADD A,D  1:4  Z 0 H C
            case 0x82:
                label += "ADD A,D  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADD A,E  1:4  Z 0 H C
            case 0x83:
                label += "ADD A,E  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADD A,H  1:4  Z 0 H C
            case 0x84:
                label += "ADD A,H  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADD A,L  1:4  Z 0 H C
            case 0x85:
                label += "ADD A,L  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADD A,(HL)  1:8  Z 0 H C
            case 0x86:
                label += "ADD A,(HL)  1:8  Z 0 H C";
                PC += 1;
                break;

            //ADD A,A  1:4  Z 0 H C
            case 0x87:
                label += "ADD A,A  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADC A,B  1:4  Z 0 H C
            case 0x88:
                label += "ADC A,B  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADC A,C  1:4  Z 0 H C
            case 0x89:
                label += "ADC A,C  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADC A,D  1:4  Z 0 H C
            case 0x8A:
                label += "ADC A,D  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADC A,E  1:4  Z 0 H C
            case 0x8B:
                label += "ADC A,E  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADC A,H  1:4  Z 0 H C
            case 0x8C:
                label += "ADC A,H  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADC A,L  1:4  Z 0 H C
            case 0x8D:
                label += "ADC A,L  1:4  Z 0 H C";
                PC += 1;
                break;

            //ADC A,(HL)  1:8  Z 0 H C
            case 0x8E:
                label += "ADC A,(HL)  1:8  Z 0 H C";
                PC += 1;
                break;

            //ADC A,A  1:4  Z 0 H C  
            case 0x8F:
                label += "ADC A,A  1:4  Z 0 H C  ";
                PC += 1;
                break;

            //SUB B  1:4  Z 1 H C
            case 0x90:
                label += "SUB B  1:4  Z 1 H C";
                PC += 1;
                break;

            //SUB C  1:4  Z 1 H C
            case 0x91:
                label += "SUB C  1:4  Z 1 H C";
                PC += 1;
                break;

            //SUB D  1:4  Z 1 H C
            case 0x92:
                label += "SUB D  1:4  Z 1 H C";
                PC += 1;
                break;

            //SUB E  1:4  Z 1 H C
            case 0x93:
                label += "SUB E  1:4  Z 1 H C";
                PC += 1;
                break;

            //SUB H  1:4  Z 1 H C
            case 0x94:
                label += "SUB H  1:4  Z 1 H C";
                PC += 1;
                break;

            //SUB L  1:4  Z 1 H C
            case 0x95:
                label += "SUB L  1:4  Z 1 H C";
                PC += 1;
                break;

            //SUB (HL)  1:8  Z 1 H C
            case 0x96:
                label += "SUB (HL)  1:8  Z 1 H C";
                PC += 1;
                break;

            //SUB A  1:4  Z 1 H C
            case 0x97:
                label += "SUB A  1:4  Z 1 H C";
                PC += 1;
                break;

            //SBC A,B  1:4  Z 1 H C
            case 0x98:
                label += "SBC A,B  1:4  Z 1 H C";
                PC += 1;
                break;

            //SBC A,C  1:4  Z 1 H C
            case 0x99:
                label += "SBC A,C  1:4  Z 1 H C";
                PC += 1;
                break;

            //SBC A,D  1:4  Z 1 H C
            case 0x9A:
                label += "SBC A,D  1:4  Z 1 H C";
                PC += 1;
                break;

            //SBC A,E  1:4  Z 1 H C
            case 0x9B:
                label += "SBC A,E  1:4  Z 1 H C";
                PC += 1;
                break;

            //SBC A,H  1:4  Z 1 H C
            case 0x9C:
                label += "SBC A,H  1:4  Z 1 H C";
                PC += 1;
                break;

            //SBC A,L  1:4  Z 1 H C
            case 0x9D:
                label += "SBC A,L  1:4  Z 1 H C";
                PC += 1;
                break;

            //SBC A,(HL)  1:8  Z 1 H C
            case 0x9E:
                label += "SBC A,(HL)  1:8  Z 1 H C";
                PC += 1;
                break;

            //SBC A,A  1:4  Z 1 H C  
            case 0x9F:
                label += "SBC A,A  1:4  Z 1 H C  ";
                PC += 1;
                break;

            //AND B  1:4  Z 0 1 0
            case 0xA0:
                label += "AND B  1:4  Z 0 1 0";
                PC += 1;
                break;

            //AND C  1:4  Z 0 1 0
            case 0xA1:
                label += "AND C  1:4  Z 0 1 0";
                PC += 1;
                break;

            //AND D  1:4  Z 0 1 0
            case 0xA2:
                label += "AND D  1:4  Z 0 1 0";
                PC += 1;
                break;

            //AND E  1:4  Z 0 1 0
            case 0xA3:
                label += "AND E  1:4  Z 0 1 0";
                PC += 1;
                break;

            //AND H  1:4  Z 0 1 0
            case 0xA4:
                label += "AND H  1:4  Z 0 1 0";
                PC += 1;
                break;

            //AND L  1:4  Z 0 1 0
            case 0xA5:
                label += "AND L  1:4  Z 0 1 0";
                PC += 1;
                break;

            //AND (HL)  1:8  Z 0 1 0
            case 0xA6:
                label += "AND (HL)  1:8  Z 0 1 0";
                PC += 1;
                break;

            //AND A  1:4  Z 0 1 0
            case 0xA7:
                label += "AND A  1:4  Z 0 1 0";
                PC += 1;
                break;

            //XOR B  1:4  Z 0 0 0
            case 0xA8:
                label += "XOR B  1:4  Z 0 0 0";
                PC += 1;
                break;

            //XOR C  1:4  Z 0 0 0
            case 0xA9:
                label += "XOR C  1:4  Z 0 0 0";
                PC += 1;
                break;

            //XOR D  1:4  Z 0 0 0
            case 0xAA:
                label += "XOR D  1:4  Z 0 0 0";
                PC += 1;
                break;

            //XOR E  1:4  Z 0 0 0
            case 0xAB:
                label += "XOR E  1:4  Z 0 0 0";
                PC += 1;
                break;

            //XOR H  1:4  Z 0 0 0
            case 0xAC:
                label += "XOR H  1:4  Z 0 0 0";
                PC += 1;
                break;

            //XOR L  1:4  Z 0 0 0
            case 0xAD:
                label += "XOR L  1:4  Z 0 0 0";
                PC += 1;
                break;

            //XOR (HL)  1:8  Z 0 0 0
            case 0xAE:
                label += "XOR (HL)  1:8  Z 0 0 0";
                PC += 1;
                break;

            //XOR A  1:4  Z 0 0 0  
            case 0xAF:
                label += "XOR A  1:4  Z 0 0 0  ";
                PC += 1;
                break;

            //OR B  1:4  Z 0 0 0
            case 0xB0:
                label += "OR B  1:4  Z 0 0 0";
                PC += 1;
                break;

            //OR C  1:4  Z 0 0 0
            case 0xB1:
                label += "OR C  1:4  Z 0 0 0";
                PC += 1;
                break;

            //OR D  1:4  Z 0 0 0
            case 0xB2:
                label += "OR D  1:4  Z 0 0 0";
                PC += 1;
                break;

            //OR E  1:4  Z 0 0 0
            case 0xB3:
                label += "OR E  1:4  Z 0 0 0";
                PC += 1;
                break;

            //OR H  1:4  Z 0 0 0
            case 0xB4:
                label += "OR H  1:4  Z 0 0 0";
                PC += 1;
                break;

            //OR L  1:4  Z 0 0 0
            case 0xB5:
                label += "OR L  1:4  Z 0 0 0";
                PC += 1;
                break;

            //OR (HL)  1:8  Z 0 0 0
            case 0xB6:
                label += "OR (HL)  1:8  Z 0 0 0";
                PC += 1;
                break;

            //OR A  1:4  Z 0 0 0
            case 0xB7:
                label += "OR A  1:4  Z 0 0 0";
                PC += 1;
                break;

            //CP B  1:4  Z 1 H C
            case 0xB8:
                label += "CP B  1:4  Z 1 H C";
                PC += 1;
                break;

            //CP C  1:4  Z 1 H C
            case 0xB9:
                label += "CP C  1:4  Z 1 H C";
                PC += 1;
                break;

            //CP D  1:4  Z 1 H C
            case 0xBA:
                label += "CP D  1:4  Z 1 H C";
                PC += 1;
                break;

            //CP E  1:4  Z 1 H C
            case 0xBB:
                label += "CP E  1:4  Z 1 H C";
                PC += 1;
                break;

            //CP H  1:4  Z 1 H C
            case 0xBC:
                label += "CP H  1:4  Z 1 H C";
                PC += 1;
                break;

            //CP L  1:4  Z 1 H C
            case 0xBD:
                label += "CP L  1:4  Z 1 H C";
                PC += 1;
                break;

            //CP (HL)  1:8  Z 1 H C
            case 0xBE:
                label += "CP (HL)  1:8  Z 1 H C";
                PC += 1;
                break;

            //CP A  1:4  Z 1 H C  
            case 0xBF:
                label += "CP A  1:4  Z 1 H C  ";
                PC += 1;
                break;

            //RET NZ  1:20/8  - - - -
            case 0xC0:
                label += "RET NZ  1:20/8  - - - -";
                PC += 1;
                break;

            //POP BC  1:12  - - - -
            case 0xC1:
                label += "POP BC  1:12  - - - -";
                PC += 1;
                break;

            //JP NZ,a16  3:16/12  - - - -
            case 0xC2:
                label += "JP NZ," + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:16/12  - - - -";
                PC += 3;
                break;

            //JP a16  3:16  - - - -
            case 0xC3:
                label += "JP " + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:16  - - - -";
                PC += 3;
                break;

            //CALL NZ,a16  3:24/12  - - - -
            case 0xC4:
                label += "CALL NZ," + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:24/12  - - - -";
                PC += 3;
                break;

            //PUSH BC  1:16  - - - -
            case 0xC5:
                label += "PUSH BC  1:16  - - - -";
                PC += 1;
                break;

            //ADD A,d8  2:8  Z 0 H C
            case 0xC6:
                label += "ADD A," + Debug.toHex(theMemory[PC + 1]) + "  2:8  Z 0 H C";
                PC += 2;
                break;

            //RST 00H  1:16  - - - -
            case 0xC7:
                label += "RST 00H  1:16  - - - -";
                PC += 1;
                break;

            //RET Z  1:20/8  - - - -
            case 0xC8:
                label += "RET Z  1:20/8  - - - -";
                PC += 1;
                break;

            //RET  1:16  - - - -
            case 0xC9:
                label += "RET  1:16  - - - -";
                PC += 1;
                break;

            //JP Z,a16  3:16/12  - - - -
            case 0xCA:
                label += "JP Z," + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:16/12  - - - -";
                PC += 3;
                break;

            //PREFIX CB  1:4  - - - -
            case 0xCB:
                label += "PREFIX CB  1:4  - - - -";
                PC += 1;
                break;

            //CALL Z,a16  3:24/12  - - - -
            case 0xCC:
                label += "CALL Z," + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:24/12  - - - -";
                PC += 3;
                break;

            //CALL a16  3:24  - - - -
            case 0xCD:
                label += "CALL " + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:24  - - - -";
                PC += 3;
                break;

            //ADC A,d8  2:8  Z 0 H C
            case 0xCE:
                label += "ADC A," + Debug.toHex(theMemory[PC + 1]) + "  2:8  Z 0 H C";
                PC += 2;
                break;

            //RST 08H  1:16  - - - -  
            case 0xCF:
                label += "RST 08H  1:16  - - - -  ";
                PC += 1;
                break;

            //RET NC  1:20/8  - - - -
            case 0xD0:
                label += "RET NC  1:20/8  - - - -";
                PC += 1;
                break;

            //POP DE  1:12  - - - -
            case 0xD1:
                label += "POP DE  1:12  - - - -";
                PC += 1;
                break;

            //JP NC,a16  3:16/12  - - - -
            case 0xD2:
                label += "JP NC," + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:16/12  - - - -";
                PC += 3;
                break;

            //CALL NC,a16  3:24/12  - - - -
            case 0xD4:
                label += "CALL NC," + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:24/12  - - - -";
                PC += 3;
                break;

            //PUSH DE  1:16  - - - -
            case 0xD5:
                label += "PUSH DE  1:16  - - - -";
                PC += 1;
                break;

            //SUB d8  2:8  Z 1 H C
            case 0xD6:
                label += "SUB " + Debug.toHex(theMemory[PC + 1]) + "  2:8  Z 1 H C";
                PC += 2;
                break;

            //RST 10H  1:16  - - - -
            case 0xD7:
                label += "RST 10H  1:16  - - - -";
                PC += 1;
                break;

            //RET C  1:20/8  - - - -
            case 0xD8:
                label += "RET C  1:20/8  - - - -";
                PC += 1;
                break;

            //RETI  1:16  - - - -
            case 0xD9:
                label += "RETI  1:16  - - - -";
                PC += 1;
                break;

            //JP C,a16  3:16/12  - - - -
            case 0xDA:
                label += "JP C," + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:16/12  - - - -";
                PC += 3;
                break;

            //CALL C,a16  3:24/12  - - - -
            case 0xDC:
                label += "CALL C," + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "  3:24/12  - - - -";
                PC += 3;
                break;

            //SBC A,d8  2:8  Z 1 H C
            case 0xDE:
                label += "SBC A," + Debug.toHex(theMemory[PC + 1]) + "  2:8  Z 1 H C";
                PC += 2;
                break;

            //RST 18H  1:16  - - - -  
            case 0xDF:
                label += "RST 18H  1:16  - - - -  ";
                PC += 1;
                break;

            //LDH (a8),A  2:12  - - - -
            case 0xE0:
                label += "LDH (a8),A  2:12  - - - -";
                PC += 2;
                break;

            //POP HL  1:12  - - - -
            case 0xE1:
                label += "POP HL  1:12  - - - -";
                PC += 1;
                break;

            //LD (C),A  2:8  - - - -
            case 0xE2:
                label += "LD (C),A  2:8  - - - -";
                PC += 2;
                break;

            //PUSH HL  1:16  - - - -
            case 0xE5:
                label += "PUSH HL  1:16  - - - -";
                PC += 1;
                break;

            //AND d8  2:8  Z 0 1 0
            case 0xE6:
                label += "AND " + Debug.toHex(theMemory[PC + 1]) + "  2:8  Z 0 1 0";
                PC += 2;
                break;

            //RST 20H  1:16  - - - -
            case 0xE7:
                label += "RST 20H  1:16  - - - -";
                PC += 1;
                break;

            //ADD SP,r8  2:16  0 0 H C
            case 0xE8:
                label += "ADD SP,r8  2:16  0 0 H C";
                PC += 2;
                break;

            //JP (HL)  1:4  - - - -
            case 0xE9:
                label += "JP (HL)  1:4  - - - -";
                PC += 1;
                break;

            //LD (a16),A  3:16  - - - -
            case 0xEA:
                label += "LD (" + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + "),A  3:16  - - - -";
                PC += 3;
                break;

            //XOR d8  2:8  Z 0 0 0
            case 0xEE:
                label += "XOR " + Debug.toHex(theMemory[PC + 1]) + "  2:8  Z 0 0 0";
                PC += 2;
                break;

            //RST 28H  1:16  - - - -  
            case 0xEF:
                label += "RST 28H  1:16  - - - -  ";
                PC += 1;
                break;

            //LDH A,(a8)  2:12  - - - -
            case 0xF0:
                label += "LDH A,(a8)  2:12  - - - -";
                PC += 2;
                break;

            //POP AF  1:12  Z N H C
            case 0xF1:
                label += "POP AF  1:12  Z N H C";
                PC += 1;
                break;

            //LD A,(C)  2:8  - - - -
            case 0xF2:
                label += "LD A,(C)  2:8  - - - -";
                PC += 2;
                break;

            //DI  1:4  - - - -
            case 0xF3:
                label += "DI  1:4  - - - -";
                PC += 1;
                break;

            //PUSH AF  1:16  - - - -
            case 0xF5:
                label += "PUSH AF  1:16  - - - -";
                PC += 1;
                break;

            //OR d8  2:8  Z 0 0 0
            case 0xF6:
                label += "OR " + Debug.toHex(theMemory[PC + 1]) + "  2:8  Z 0 0 0";
                PC += 2;
                break;

            //RST 30H  1:16  - - - -
            case 0xF7:
                label += "RST 30H  1:16  - - - -";
                PC += 1;
                break;

            //LD HL,SP+r8  2:12  0 0 H C
            case 0xF8:
                label += "LD HL,SP+r8  2:12  0 0 H C";
                PC += 2;
                break;

            //LD SP,HL  1:8  - - - -
            case 0xF9:
                label += "LD SP,HL  1:8  - - - -";
                PC += 1;
                break;

            //LD A,(a16)  3:16  - - - -
            case 0xFA:
                label += "LD A,(" + Debug.toHex((short) ByteUtil.combine(theMemory[PC + 2], theMemory[PC + 1])) + ")  3:16  - - - -";
                PC += 3;
                break;

            //EI  1:4  - - - -
            case 0xFB:
                label += "EI  1:4  - - - -";
                PC += 1;
                break;

            //CP d8  2:8  Z 1 H C
            case 0xFE:
                label += "CP " + Debug.toHex(theMemory[PC + 1]) + "  2:8  Z 1 H C";
                PC += 2;
                break;

            //RST 38H  1:16  - - - -      
            case 0xFF:
                label += "RST 38H  1:16  - - - -      ";
                PC += 1;
                break;

            default:
                label += "inconnus";
                PC += 1;
        }


        return label;
    }
}
