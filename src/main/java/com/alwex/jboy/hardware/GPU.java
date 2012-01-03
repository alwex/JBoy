package com.alwex.jboy.hardware;

/**
 *
 * @author Alex
 */
public class GPU extends AbstractHardware
{

    private CPU theCPU;
    // détail des différentes location descriptives
    // du sprite en cours de traitement
    public static final int spriteYOffset = 0,
            spriteXOffset = 1,
            tileNumberOffset = 2,
            spriteAttributesOffset = 3;

    public void drawSprites()
    {
        int spriteY, spriteX, tileNumber, spriteAttributes;
        // écriture sur l'écran des 40 sprites en mémoire
        for (int i = 0; i < 40; i++)
        {
            spriteY = theCPU.getByteAt(theCPU.memStart_spriteAttributeMemory + spriteYOffset) - 16;
            spriteX = theCPU.getByteAt(theCPU.memStart_spriteAttributeMemory + spriteXOffset) - 8;
            tileNumber = theCPU.getByteAt(theCPU.memStart_spriteAttributeMemory + tileNumberOffset);
            spriteAttributes = theCPU.getByteAt(theCPU.memStart_spriteAttributeMemory + spriteAttributesOffset);
        }
    }
}
