/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alwex.jboy.hardware;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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

    public OpCodesTest()
    {
    }

    @Test
    public void testOpcodes()
    {
        // chargement de la rom
        theCpu.init();
        theCpu.setRom(theMemory.read("src/main/resources/test2.gb"));

        theCpu.PC = this.memoryLocationFor((byte) 0x00);
        assertEquals(0x00, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x01);
        assertEquals(0x01, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x02);
        assertEquals(0x02, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x03);
        assertEquals(0x03, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x04);
        assertEquals(0x04, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x05);
        assertEquals(0x05, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x06);
        assertEquals(0x06, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x07);
        assertEquals(0x07, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x08);
        assertEquals(0x08, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x09);
        assertEquals(0x09, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x0A);
        assertEquals(0x0A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x0B);
        assertEquals(0x0B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x0C);
        assertEquals(0x0C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x0D);
        assertEquals(0x0D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x0E);
        assertEquals(0x0E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x0F);
        assertEquals(0x0F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x10);
        assertEquals(0x10, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x11);
        assertEquals(0x11, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x12);
        assertEquals(0x12, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x13);
        assertEquals(0x13, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x14);
        assertEquals(0x14, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x15);
        assertEquals(0x15, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x16);
        assertEquals(0x16, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x17);
        assertEquals(0x17, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x18);
        assertEquals(0x18, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x19);
        assertEquals(0x19, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x1A);
        assertEquals(0x1A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x1B);
        assertEquals(0x1B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x1C);
        assertEquals(0x1C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x1D);
        assertEquals(0x1D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x1E);
        assertEquals(0x1E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x1F);
        assertEquals(0x1F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x20);
        assertEquals(0x20, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x21);
        assertEquals(0x21, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x22);
        assertEquals(0x22, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x23);
        assertEquals(0x23, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x24);
        assertEquals(0x24, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x25);
        assertEquals(0x25, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x26);
        assertEquals(0x26, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x27);
        assertEquals(0x27, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x28);
        assertEquals(0x28, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x29);
        assertEquals(0x29, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x2A);
        assertEquals(0x2A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x2B);
        assertEquals(0x2B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x2C);
        assertEquals(0x2C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x2D);
        assertEquals(0x2D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x2E);
        assertEquals(0x2E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x2F);
        assertEquals(0x2F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x30);
        assertEquals(0x30, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x31);
        assertEquals(0x31, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x32);
        assertEquals(0x32, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x33);
        assertEquals(0x33, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x34);
        assertEquals(0x34, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x35);
        assertEquals(0x35, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x36);
        assertEquals(0x36, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x37);
        assertEquals(0x37, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x38);
        assertEquals(0x38, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x39);
        assertEquals(0x39, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x3A);
        assertEquals(0x3A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x3B);
        assertEquals(0x3B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x3C);
        assertEquals(0x3C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x3D);
        assertEquals(0x3D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x3E);
        assertEquals(0x3E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x3F);
        assertEquals(0x3F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x40);
        assertEquals(0x40, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x41);
        assertEquals(0x41, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x42);
        assertEquals(0x42, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x43);
        assertEquals(0x43, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x44);
        assertEquals(0x44, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x45);
        assertEquals(0x45, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x46);
        assertEquals(0x46, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x47);
        assertEquals(0x47, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x48);
        assertEquals(0x48, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x49);
        assertEquals(0x49, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x4A);
        assertEquals(0x4A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x4B);
        assertEquals(0x4B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x4C);
        assertEquals(0x4C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x4D);
        assertEquals(0x4D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x4E);
        assertEquals(0x4E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x4F);
        assertEquals(0x4F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x50);
        assertEquals(0x50, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x51);
        assertEquals(0x51, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x52);
        assertEquals(0x52, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x53);
        assertEquals(0x53, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x54);
        assertEquals(0x54, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x55);
        assertEquals(0x55, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x56);
        assertEquals(0x56, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x57);
        assertEquals(0x57, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x58);
        assertEquals(0x58, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x59);
        assertEquals(0x59, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x5A);
        assertEquals(0x5A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x5B);
        assertEquals(0x5B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x5C);
        assertEquals(0x5C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x5D);
        assertEquals(0x5D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x5E);
        assertEquals(0x5E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x5F);
        assertEquals(0x5F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x60);
        assertEquals(0x60, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x61);
        assertEquals(0x61, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x62);
        assertEquals(0x62, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x63);
        assertEquals(0x63, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x64);
        assertEquals(0x64, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x65);
        assertEquals(0x65, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x66);
        assertEquals(0x66, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x67);
        assertEquals(0x67, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x68);
        assertEquals(0x68, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x69);
        assertEquals(0x69, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x6A);
        assertEquals(0x6A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x6B);
        assertEquals(0x6B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x6C);
        assertEquals(0x6C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x6D);
        assertEquals(0x6D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x6E);
        assertEquals(0x6E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x6F);
        assertEquals(0x6F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x70);
        assertEquals(0x70, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x71);
        assertEquals(0x71, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x72);
        assertEquals(0x72, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x73);
        assertEquals(0x73, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x74);
        assertEquals(0x74, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x75);
        assertEquals(0x75, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x76);
        assertEquals(0x76, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x77);
        assertEquals(0x77, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x78);
        assertEquals(0x78, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x79);
        assertEquals(0x79, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x7A);
        assertEquals(0x7A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x7B);
        assertEquals(0x7B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x7C);
        assertEquals(0x7C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x7D);
        assertEquals(0x7D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x7E);
        assertEquals(0x7E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x7F);
        assertEquals(0x7F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x80);
        assertEquals(0x80, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x81);
        assertEquals(0x81, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x82);
        assertEquals(0x82, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x83);
        assertEquals(0x83, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x84);
        assertEquals(0x84, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x85);
        assertEquals(0x85, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x86);
        assertEquals(0x86, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x87);
        assertEquals(0x87, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x88);
        assertEquals(0x88, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x89);
        assertEquals(0x89, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x8A);
        assertEquals(0x8A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x8B);
        assertEquals(0x8B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x8C);
        assertEquals(0x8C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x8D);
        assertEquals(0x8D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x8E);
        assertEquals(0x8E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x8F);
        assertEquals(0x8F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x90);
        assertEquals(0x90, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x91);
        assertEquals(0x91, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x92);
        assertEquals(0x92, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x93);
        assertEquals(0x93, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x94);
        assertEquals(0x94, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x95);
        assertEquals(0x95, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x96);
        assertEquals(0x96, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x97);
        assertEquals(0x97, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x98);
        assertEquals(0x98, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x99);
        assertEquals(0x99, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x9A);
        assertEquals(0x9A, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x9B);
        assertEquals(0x9B, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x9C);
        assertEquals(0x9C, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x9D);
        assertEquals(0x9D, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x9E);
        assertEquals(0x9E, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0x9F);
        assertEquals(0x9F, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA0);
        assertEquals(0xA0, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA1);
        assertEquals(0xA1, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA2);
        assertEquals(0xA2, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA3);
        assertEquals(0xA3, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA4);
        assertEquals(0xA4, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA5);
        assertEquals(0xA5, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA6);
        assertEquals(0xA6, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA7);
        assertEquals(0xA7, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA8);
        assertEquals(0xA8, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xA9);
        assertEquals(0xA9, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xAA);
        assertEquals(0xAA, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xAB);
        assertEquals(0xAB, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xAC);
        assertEquals(0xAC, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xAD);
        assertEquals(0xAD, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xAE);
        assertEquals(0xAE, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xAF);
        assertEquals(0xAF, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB0);
        assertEquals(0xB0, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB1);
        assertEquals(0xB1, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB2);
        assertEquals(0xB2, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB3);
        assertEquals(0xB3, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB4);
        assertEquals(0xB4, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB5);
        assertEquals(0xB5, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB6);
        assertEquals(0xB6, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB7);
        assertEquals(0xB7, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB8);
        assertEquals(0xB8, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xB9);
        assertEquals(0xB9, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xBA);
        assertEquals(0xBA, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xBB);
        assertEquals(0xBB, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xBC);
        assertEquals(0xBC, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xBD);
        assertEquals(0xBD, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xBE);
        assertEquals(0xBE, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xBF);
        assertEquals(0xBF, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC0);
        assertEquals(0xC0, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC1);
        assertEquals(0xC1, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC2);
        assertEquals(0xC2, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC3);
        assertEquals(0xC3, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC4);
        assertEquals(0xC4, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC5);
        assertEquals(0xC5, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC6);
        assertEquals(0xC6, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC7);
        assertEquals(0xC7, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC8);
        assertEquals(0xC8, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xC9);
        assertEquals(0xC9, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xCA);
        assertEquals(0xCA, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xCB);
        assertEquals(0xCB, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xCC);
        assertEquals(0xCC, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xCD);
        assertEquals(0xCD, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xCE);
        assertEquals(0xCE, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xCF);
        assertEquals(0xCF, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD0);
        assertEquals(0xD0, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD1);
        assertEquals(0xD1, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD2);
        assertEquals(0xD2, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD3);
        assertEquals(0xD3, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD4);
        assertEquals(0xD4, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD5);
        assertEquals(0xD5, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD6);
        assertEquals(0xD6, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD7);
        assertEquals(0xD7, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD8);
        assertEquals(0xD8, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xD9);
        assertEquals(0xD9, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xDA);
        assertEquals(0xDA, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xDB);
        assertEquals(0xDB, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xDC);
        assertEquals(0xDC, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xDD);
        assertEquals(0xDD, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xDE);
        assertEquals(0xDE, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xDF);
        assertEquals(0xDF, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE0);
        assertEquals(0xE0, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE1);
        assertEquals(0xE1, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE2);
        assertEquals(0xE2, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE3);
        assertEquals(0xE3, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE4);
        assertEquals(0xE4, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE5);
        assertEquals(0xE5, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE6);
        assertEquals(0xE6, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE7);
        assertEquals(0xE7, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE8);
        assertEquals(0xE8, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xE9);
        assertEquals(0xE9, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xEA);
        assertEquals(0xEA, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xEB);
        assertEquals(0xEB, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xEC);
        assertEquals(0xEC, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xED);
        assertEquals(0xED, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xEE);
        assertEquals(0xEE, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xEF);
        assertEquals(0xEF, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF0);
        assertEquals(0xF0, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF1);
        assertEquals(0xF1, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF2);
        assertEquals(0xF2, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF3);
        assertEquals(0xF3, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF4);
        assertEquals(0xF4, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF5);
        assertEquals(0xF5, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF6);
        assertEquals(0xF6, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF7);
        assertEquals(0xF7, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF8);
        assertEquals(0xF8, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xF9);
        assertEquals(0xF9, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xFA);
        assertEquals(0xFA, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xFB);
        assertEquals(0xFB, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xFC);
        assertEquals(0xFC, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xFD);
        assertEquals(0xFD, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xFE);
        assertEquals(0xFE, theCpu.memory[theCpu.PC & 0xffff]);

        theCpu.PC = this.memoryLocationFor((byte) 0xFF);
        assertEquals(0xFF, theCpu.memory[theCpu.PC & 0xffff]);
    }

    public short memoryLocationFor(byte aOpcode)
    {
        int location = 100;
        for (int i = 100; i < 0xff00; i++)
        {
            if (theCpu.memory[i] == aOpcode)
            {
                location = i;
                break;
            }
        }

        return (short) location;
    }
}
