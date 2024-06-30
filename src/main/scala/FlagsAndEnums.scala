import javax.swing.*

object FlagsAndEnums {

    val commonHeaderFlags = Array(
        (0x00000008, "Flat (rendered first, just after tiles)"),
        (0x00000010, "NoBlock (doesn't block the tile)"),
        (0x00000800, "MultiHex"),
        (0x00001000, "No Highlight"),
        (0x00004000, "TransRed"),
        (0x00008000, "TransNone (opaque)"),
        (0x00010000, "TransWall"),
        (0x00020000, "TransGlass"),
        (0x00040000, "TransSteam"),
        (0x00080000, "TransEnergy"),
        (0x10000000, "WallTransEnd"),
        (0x20000000, "LightThru"),
        (0x80000000, "ShootThru")
    )
    val itemFlags = Array(
        (0x080000, new JCheckBox("Hidden Item"))
    )
    val actionFlags = Array(
        (0x000008, new JCheckBox("Can Be Used")),
        (0x000010, new JCheckBox("Use On Something")),
        (0x000080, new JCheckBox("PickUp"))
    )
    val openFlags = Array(
        (0x00000001, new JCheckBox("Cannot Pick Up")),
        (0x00000008, new JCheckBox("Magic Hands Grnd"))
    )
    val critterFlags = Array(
        (0x00000002, "Barter (can trade with)"),
        (0x00000020, "Steal (cannot steal from)"),
        (0x00000040, "Drop (doesn't drop items)"),
        (0x00000080, "Limbs (can not lose limbs)"),
        (0x00000100, "Ages (dead body does not disappear)"),
        (0x00000200, "Heal (damage is not cured with time)"),
        (0x00000400, "Invulnerable (cannot be hurt)"),
        (0x00000800, "Flatten (leaves no dead body)"),
        (0x00001000, "Special (there is a special type of death)"),
        (0x00002000, "Range (melee attack is possible at a distance)"),
        (0x00004000, "Knock (cannot be knocked down)")
    )
    val wallLightTypeFlagValues = Array(
        (0x0000, "North / South"),
        (0x0800, "East / West"),
        (0x1000, "North Corner"),
        (0x2000, "South Corner"),
        (0x4000, "East Corner"),
        (0x8000, "West Corner")
    )
    val actionFlagValues = Array(
        (0x0001, "Kneel down when using"),
        (0x0008, "Use (can be used)"),
        (0x0010, "Use On Smth (can be used on anything)"),
        (0x0020, "Look"),
        (0x0040, "Talk"),
        (0x0080, "PickUp")
    )
    val doorUnknownValues = Array(
        (0xCCCCCCCC, "0xCCCCCCCC (mostly)"),
        (0xFFFFFFFF, "0xFFFFFFFF (sometimes)")
    )
    val walkThruFlag = 0x0000000F


    val objectTypeNames = Array("items", "critters", "scenery", "walls", "tiles", "misc")
    val frmTypeNames = Array("items", "critters", "scenery", "walls", "tiles", "backgrnd", "intrface", "inven")
    val itemSubtypeNames = Array("Armor", "Container", "Drug", "Weapon", "Ammo", "Misc", "Key")
    val attackModeNames = Array(
        "None",
        "Punch",
        "Kick",
        "Swing",
        "Thrust", 
        "Throw",
        "Fire Single",
        "Fire Burst",
        "Flame"
    )
    val weaponAnimCodeNames = Array(
        "None (A)",
        "Knife (D)",
        "Club (E)",
        "Sledgehammer (F)",
        "Spear (G)",
        "Pistol (H)",
        "SMG (I)",
        "Rifle (J)",
        "Big Gun (K)",
        "Minigun (L)",
        "Rocket Launcher (M)"
    )
    val damageTypeNames = Array(
        "Normal",
        "Laser",
        "Fire",
        "Plasma",
        "Electrical",
        "EMP",
        "Explosive"
    )
    val killTypeNames = Array(
        "Men",
        "Women",
        "Children",
        "Super Mutants",
        "Ghouls",
        "Brahmin",
        "Radscorpions",
        "Rats",
        "Floaters",
        "Centaurs",
        "Robots",
        "Dogs",
        "Manti",
        "DeathClaws",
        "Plants  (only applies to Fallout 2)",
        "Geckos  (only applies to Fallout 2)",
        "Aliens  (only applies to Fallout 2)",
        "Giant Ants  (only applies to Fallout 2)",
        "Big Bad Boss (only applies to Fallout 2)"
    )
    val scenerySubtypeNames = Array(
        "Door",
        "Stairs",
        "Elevator",
        "Ladder Bottom",
        "Ladder Top",
        "Generic Scenery"
    )
    val materialNames = Array(
        "Glass",
        "Metal",
        "Plastic",
        "Wood",
        "Dirt",
        "Stone",
        "Cement",
        "Leather"
    )
    val bodyTypeNames = Array("biped", "quadruped", "robotic")
}
