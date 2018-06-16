package riftomer.bcex.pipe.util;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;

public enum EnumDiamondColor {
    BLACK(EnumFacing.DOWN, EnumDyeColor.BLACK),
    YELLOW(EnumFacing.EAST, EnumDyeColor.YELLOW),
    RED(EnumFacing.NORTH, EnumDyeColor.RED),
    BLUE(EnumFacing.SOUTH, EnumDyeColor.BLUE),
    WHITE(EnumFacing.UP, EnumDyeColor.WHITE),
    GREEN(EnumFacing.WEST, EnumDyeColor.GREEN);
    public final EnumFacing facing;
    public final EnumDyeColor color;

    EnumDiamondColor(EnumFacing facing, EnumDyeColor color) {
        this.facing = facing;
        this.color = color;
    }

    public static EnumDiamondColor fromFacing(EnumFacing facing) {
        for (EnumDiamondColor color : EnumDiamondColor.values()) {
            if (color.facing.equals(facing)) {
                return color;
            }
        }
        return null;
    }

    public static EnumDiamondColor fromDye(EnumDyeColor dColor) {
        for (EnumDiamondColor color : EnumDiamondColor.values()) {
            if (color.color.equals(dColor)) {
                return color;
            }
        }
        return null;
    }

    public EnumDiamondColor next() {
        return EnumDiamondColor.values()[(this.ordinal() + 1) % values().length];
    }
}
