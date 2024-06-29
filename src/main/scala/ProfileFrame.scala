import javax.swing.*
import javax.swing.border.*
import java.awt.*
import java.awt.event.*

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
    val dtAndDrNames = Seq(
        "DT Normal",
        "DT Laser",
        "DT Fire",
        "DT Plasma",
        "DT Electrical",
        "DT EMP",
        "DT Explosive",
        "DR Normal",
        "DR Laser",
        "DR Fire",
        "DR Plasma",
        "DR Electrical",
        "DR EMP",
        "DR Explosive",
        "DR Radiation",
        "DR Poison",
    )
    val bodyTypeNames = Array("biped", "quadruped", "robotic")
}

class ProfileFrame {
    import FlagsAndEnums.*

    val objectTypeCombo = new JComboBox(objectTypeNames)
    val frmTypeCombo = new JComboBox(frmTypeNames)

    val headerFlagsList = new JList(commonHeaderFlags.map(_._2))
    headerFlagsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    headerFlagsList.setLayoutOrientation(JList.VERTICAL)

    val headerStatsPanel = NamedFieldPanel(
        Seq(
            ("Object Type", objectTypeCombo),
            "Object Id",
            "Text Id",
            ("Frm Type", frmTypeCombo),
            "Frm Id",
            "Light Radius",
            "Light Intensity"
        )
    )
    val headerFlagsPanel = NamedFieldPanel(
        Seq(
            ("Flags", headerFlagsList)
        )
    )
    val headerFieldPanel = new JPanel(new BorderLayout())
    val headerFieldPanel2 = new JPanel(new BorderLayout())
    // headerFieldPanel.setPreferredSize(new Dimension(300, 450))
    headerFieldPanel.add(headerStatsPanel, BorderLayout.NORTH)
    headerFieldPanel2.add(headerFlagsPanel, BorderLayout.NORTH)
    headerFieldPanel.add(headerFieldPanel2)

    val itemSubtypeCombo = new JComboBox(itemSubtypeNames)

    val actionPanel = new JPanel()
    actionPanel.setBorder(BorderFactory.createEmptyBorder())
    actionFlags.foreach(a => actionPanel.add(a._2))

    val itemCommonPanel = new JPanel(new BorderLayout)
    val itemCommonPanel1 = NamedFieldPanel(
        Seq(
            ("Item Flag", itemFlags(0)(1)), 
            ("Action Flags", actionPanel), 
        )
    )

    val attackModeCombo1 = new JComboBox(attackModeNames)
    val attackModeCombo2 = new JComboBox(attackModeNames)

    val itemCommonPanel2 = NamedFieldPanel(
        Seq(
            "Weapon Flags", 
            ("Attack Mode 1", attackModeCombo1), 
            ("Attack Mode 2", attackModeCombo2), 
            "Script Id", 
            ("Object Subtype", itemSubtypeCombo), 
            "Material Id", 
            "Size", 
            "Weight", 
            "Cost", 
            "Inv FID", 
            "Sound Id"
        )
    )
    itemCommonPanel.add(itemCommonPanel1, BorderLayout.NORTH)
    itemCommonPanel.add(itemCommonPanel2)

    val itemArmorPanel = NamedFieldPanel(
        Seq(
            "AC", 

            "DR Normal", 
            "DR Laser", 
            "DR Fire", 
            "DR Plasma", 
            "DR Electrical", 
            "DR EMP", 
            "DR Explosion", 

            "DT Normal", 
            "DT Laser", 
            "DT Fire", 
            "DT Plasma", 
            "DT Electrical", 
            "DT EMP", 
            "DT Explosion", 

            "Perk", 
            "Male FID", 
            "Female FID"
        )
    )

    val openFlagPanel = new JPanel()
    openFlagPanel.setBorder(BorderFactory.createEmptyBorder())
    openFlags.foreach(a => openFlagPanel.add(a._2))

    val itemContainerPanel = NamedFieldPanel(
        Seq(
            "Max Size",
            ("Open Flags", openFlagPanel)
        )
    )

    val itemDrugPanel = NamedFieldPanel(
        Seq(
            "Stat0", 
            "Stat1", 
            "Stat2", 
            "Amount0", 
            "Amount1", 
            "Amount2", 
            "Duration1", 
            "Amount0", 
            "Amount1", 
            "Amount2", 
            "Duration2", 
            "Amount0", 
            "Amount1", 
            "Amount2", 
            "Addiction Rate", 
            "Addiction Effect", 
            "Addiction Onset"
        )
    )

    val weaponAnimCodeCombo = new JComboBox(weaponAnimCodeNames)

    val weaponDamageTypeCombo = new JComboBox(damageTypeNames)

    val itemWeaponPanel = NamedFieldPanel(
        Seq(
            ("Anim Code", weaponAnimCodeCombo), 
            "Min Damage", 
            "Max Damage", 
            ("Damage Type", weaponDamageTypeCombo), 
            "Max Range 1", 
            "Max Range 2", 
            "Proj PID", 
            "Min ST", 
            "AP Cost 1", 
            "MP Cost 2", 
            "Crit Fail", 
            "Perk", 
            "Rounds", 
            "Caliber", 
            "Ammo PID", 
            "Max Ammo", 
            "Sound Id", 
        )
    )

    val itemAmmoPanel = NamedFieldPanel(
        Seq(
            "Caliber", 
            "Quantity", 
            "AC modifier", 
            "DC Modifier", 
            "Damage Mult", 
            "Damage Div"
        )
    )

    val itemMiscPanel = NamedFieldPanel(Seq("Power PID", "Power Type", "Charges"))
    val itemKeyPanel  = NamedFieldPanel(Seq("Key Code"))

    val itemSubtypePanel = new SwitchablePanel(Seq(
        ("Armor", itemArmorPanel),
        ("Container", itemContainerPanel),
        ("Drug", itemDrugPanel),
        ("Weapon", itemWeaponPanel),
        ("Ammo", itemAmmoPanel),
        ("Misc", itemMiscPanel),
        ("Key", itemKeyPanel),
    ))

    val baseDts = NamedFieldPanel(dtAndDrNames)
    val bonusDts = NamedFieldPanel(dtAndDrNames)
    val dtAndDrPanels = DoubleColumnPanel(
        new LabeledPanel("Base Values", baseDts),
        new LabeledPanel("Bonuses", bonusDts)
    )

    val baseValsPanel = 
        NamedFieldPanel(Seq(
            "Strength", 
            "Perception", 
            "Endurance", 
            "Charisma", 
            "Intelligence", 
            "Agility", 
            "Luck", 
            "HP", 
            "AP", 
            "AC", 
            "Unarmed Damage (Unused)", 
            "Melee Damage", 
            "Carry Weight", 
            "Sequence", 
            "Healing Rate", 
            "Critical Chance", 
            "Better Criticals"
        )
    )
    val bonusValsPanel =
        NamedFieldPanel(Seq(
            "Strength (1-10)",
            "Perception (1-10)",
            "Endurance (1-10)",
            "Charisma (1-10)",
            "Intelligence (1-10)",
            "Agility (1-10)",
            "Luck (1-10)",
            "HP",
            "AP",
            "AC",
            "Unarmed damage (Unused)",
            "Melee damage",
            "Carry weight (0-999)",
            "Sequence",
            "Healing rate",
            "Critical chance",
            "Better criticals"
        )
    )
    val primaryStatsPanels = DoubleColumnPanel(
        new LabeledPanel("Base Values", baseValsPanel),
        new LabeledPanel("Bonuses", bonusValsPanel)
    )

    val baseAgePanel = NamedFieldPanel(Seq("Age", "Gender"))
    val bonusAgePanel = NamedFieldPanel(Seq("Age", "Gender"))
    val ageAndGenderPanels = DoubleColumnPanel(new LabeledPanel("Base Values", baseAgePanel), new LabeledPanel("Bonuses", bonusAgePanel))

    val critterActionFlagValues = Array("0x00002000", "0x00004000")
    val critterActionFlagCombo = new JComboBox(critterActionFlagValues)

    val bodyTypeCombo = new JComboBox(bodyTypeNames)

    val killTypeCombo = new JComboBox(killTypeNames)

    val critterDamageTypeCombo = new JComboBox(damageTypeNames)

    val critterFlagsList = new JList(critterFlags.map(_._2))
    critterFlagsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    critterFlagsList.setLayoutOrientation(JList.VERTICAL)

    val critterStatsPanel1 = NamedFieldPanel(
        Seq(
            ("ActionFlags", critterActionFlagCombo), 
            "Script ID", 
            "Head FID", 
            "AI Packet", 
            "Team Num", 
        )
    )
    val critterStatsPanel1Inputs = critterStatsPanel1.inputs

    val critterStatsPanel2 = NamedFieldPanel(
        Seq(
            ("Critter Flags", critterFlagsList), 
        )
    )
    val critterStatsPanel2Inputs = critterStatsPanel2.inputs

    val critterStatsPanel3 = NamedFieldPanel(
        Seq(
            ("Body Type", bodyTypeCombo), 
            "Exp Val", 
            ("Kill Type", killTypeCombo), 
            ("Damage Type", critterDamageTypeCombo)
        )
    )
    val critterStatsPanel3Inputs = critterStatsPanel3.inputs

    val critterStatsPanel = new TopPanel(
        critterStatsPanel1,
        new TopPanel(
            critterStatsPanel2,
            critterStatsPanel3
        )
    )

    val critterSkillsPanel = NamedFieldPanel(
        Seq(
            "Small guns (0-300)",
            "Big guns (0-300)",
            "Energy weapons (0-300)",
            "Unarmed (0-300)",
            "Melee (0-300)",
            "Throwing (0-300)",
            "First aid (0-300)",
            "Doctor (0-300)",
            "Sneak (0-300)",
            "Lockpick (0-300)",
            "Steal (0-300)",
            "Traps (0-300)",
            "Science (0-300)",
            "Repair (0-300)",
            "Speech (0-300)",
            "Barter (0-300)",
            "Gambling (0-300)",
            "Outdoorsman (0-300)"
        )
    )

    val scenerySubtypeCombo = new JComboBox(scenerySubtypeNames)

    val wallLightTypeFlagList = new JList(wallLightTypeFlagValues.map(_._2))
    wallLightTypeFlagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    wallLightTypeFlagList.setLayoutOrientation(JList.VERTICAL)

    val actionFlagList = new JList(actionFlagValues.map(_._2))
    actionFlagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    actionFlagList.setLayoutOrientation(JList.VERTICAL)

    val materialsCombo = new JComboBox(materialNames)

    val sceneryLightPanel = NamedFieldPanel(Seq(("Wall Light Type Flags", wallLightTypeFlagList)))
    val sceneryActionPanel = NamedFieldPanel(Seq(("Action Flags", actionFlagList)))
    val sceneryCommonListPanel = new TopPanel(sceneryLightPanel, sceneryActionPanel)

    val sceneryCommonIdPanel = NamedFieldPanel(
        Seq(
            "Script Type", 
            "Script ID", 
            ("Scenery Subtype", scenerySubtypeCombo), 
            ("Material ID", materialsCombo), 
            "Sound ID"
        )
    )
    val sceneryCommonPanel = new TopPanel(sceneryCommonListPanel, sceneryCommonIdPanel)

    val walkThruFlagCheckbox = new JCheckBox("yes")

    val doorUnknownCombo = new JComboBox(doorUnknownValues.map(_._2))
    val sceneryGenericCombo = new JComboBox(doorUnknownValues.map(_._2))

    val sceneryDoorPanel = NamedFieldPanel(Seq(
        ("WalkThru Flag", walkThruFlagCheckbox),
        ("Unknown", doorUnknownCombo)
    ))

    val sceneryStairsPanel = NamedFieldPanel(Seq("Dest Elev", "Dest Tile", "Dest Map"))

    val sceneryElevatorPanel = NamedFieldPanel(Seq("Elev Type", "Elev Level"))

    val sceneryLadderBottomPanel = NamedFieldPanel(Seq("Dest Tile", "Dest Elev"))

    val sceneryLadderTopPanel = NamedFieldPanel(Seq("Dest Tile", "Dest Elev"))

    val sceneryGenericPanel = NamedFieldPanel(Seq(("Unknown", sceneryGenericCombo)))

    val scenerySubtypePanel = new SwitchablePanel(Seq(
        ("Door", sceneryDoorPanel),
        ("Stairs", sceneryStairsPanel),
        ("Elevator", sceneryElevatorPanel),
        ("Ladder Bottom", sceneryLadderBottomPanel),
        ("Ladder Top", sceneryLadderTopPanel),
        ("Generic", sceneryGenericPanel),
    ))

    val wallsWallLightTypeFlagList = new JList(wallLightTypeFlagValues.map(_._2))
    wallsWallLightTypeFlagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    wallsWallLightTypeFlagList.setLayoutOrientation(JList.VERTICAL)

    val wallsMaterialsCombo = new JComboBox(materialNames)

    val wallActionFlagList = new JList(actionFlagValues.map(_._2))
    wallActionFlagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    wallActionFlagList.setLayoutOrientation(JList.VERTICAL)

    val wallLightTypePanel = NamedFieldPanel(Seq(("Wall Light Type Flags", wallsWallLightTypeFlagList)))
    val wallActionPanel    = NamedFieldPanel(Seq(("ActionFlags", wallActionFlagList)))

    val wallsPanel1 = new TopPanel(wallLightTypePanel, wallActionPanel)

    val wallsPanel2 = NamedFieldPanel(Seq(
        "Script Type", 
        "Script ID", 
        ("Material ID", wallsMaterialsCombo)
    ))

    val wallsPanel = new TopPanel(wallsPanel1, wallsPanel2)

    val tilesMaterialsCombo = new JComboBox(materialNames)
    val tilesPanel = NamedFieldPanel(Seq(("Material ID", tilesMaterialsCombo)))

    val miscPanel = NamedFieldPanel(Seq("Unknown"))

    val itemPanel = new JPanel(new BorderLayout())
    itemPanel.add(itemCommonPanel, BorderLayout.NORTH)
    itemPanel.add(itemSubtypePanel)
    itemSubtypeCombo.addActionListener(e => {
        itemSubtypePanel.selectRadio(itemSubtypeNames.indexOf(itemSubtypeCombo.getSelectedItem().asInstanceOf[String]))
    })

    val sceneryPanel = new JPanel(new BorderLayout())
    sceneryPanel.add(sceneryCommonPanel, BorderLayout.NORTH)
    sceneryPanel.add(scenerySubtypePanel)
    scenerySubtypeCombo.addActionListener(e => {
        scenerySubtypePanel.selectRadio(scenerySubtypeNames.indexOf(scenerySubtypeCombo.getSelectedItem().asInstanceOf[String]))
    })

    val critterPanel = new JTabbedPane()
    critterPanel.addTab("Stats", null, new TopPanel(critterStatsPanel), "Stats")
    critterPanel.addTab("Skills", null, critterSkillsPanel, "Skills")
    critterPanel.addTab("Age & Gender", null, new TopPanel(ageAndGenderPanels), "Age & Gender")
    critterPanel.addTab("Primary Stats", null, primaryStatsPanels, "Primary Stats")
    critterPanel.addTab("DT and DR", null, dtAndDrPanels, "DT and DR")

    val subtypePanel = new SwitchablePanel(Seq(
        ("Items", itemPanel),
        ("Critters", critterPanel),
        ("Scenery", sceneryPanel),
        ("Walls", wallsPanel),
        ("Tiles", tilesPanel),
        ("Misc", miscPanel)
    ))
    objectTypeCombo.addActionListener(e => {
        subtypePanel.selectRadio(objectTypeNames.indexOf(objectTypeCombo.getSelectedItem().asInstanceOf[String]))
    })

    val mainPanel = new JPanel(new BorderLayout(8, 8))
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10))

    val leftHeaderPanel = new TopPanel(headerFieldPanel)

    mainPanel.add(new LabeledPanel("Common Header", leftHeaderPanel), BorderLayout.WEST)
    mainPanel.add(subtypePanel)

    val frame = new JFrame()
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    val menuBar = new JMenuBar()
    val menu = new JMenu("File")
    menu.setMnemonic(KeyEvent.VK_F)
    menuBar.add(menu)
    val menuItem = new JMenuItem("Open", KeyEvent.VK_O)
    menuItem.addActionListener(event => {
        if (event.getActionCommand() == "Open")
            val fc = new JFileChooser()
            val returnVal = fc.showOpenDialog(frame)
            if (returnVal == JFileChooser.APPROVE_OPTION)
                Option(fc.getSelectedFile()).foreach(f => load(Profiler.parseFile(f.toPath)))
    })
    menu.add(menuItem)
    frame.setJMenuBar(menuBar)

    frame.getContentPane().add(mainPanel)
    frame.pack()
    frame.setVisible(true)

    def loadCommon(commonHeader: CommonHeader): Unit =
        commonHeader match
            case CommonHeader(
                ObjectTypeAndId(objType, possiblePartOfObjId, objId),
                textId, 
                frmType,
                possiblePartOfFrmId,
                frmId, 
                lightRadius,
                lightIntensity,
                headerFlags
            ) =>
                val headerInputs = headerStatsPanel.inputs
                headerInputs(0).asInstanceOf[JComboBox[String]].setSelectedItem(objectTypeNames(objType))
                headerInputs(1).asInstanceOf[JTextField].setText(((possiblePartOfObjId << 16) | objId).toString)
                headerInputs(2).asInstanceOf[JTextField].setText(textId.toString)
                headerInputs(3).asInstanceOf[JComboBox[String]].setSelectedItem(frmTypeNames(frmType))
                headerInputs(4).asInstanceOf[JTextField].setText(((possiblePartOfFrmId << 16) | frmId).toString)
                headerInputs(5).asInstanceOf[JTextField].setText(lightRadius.toString)
                headerInputs(6).asInstanceOf[JTextField].setText(lightIntensity.toString)
                val headerFlagsList = headerFlagsPanel.inputs(0).asInstanceOf[JList[String]]
                val listModel = headerFlagsList.getModel()
                val selectedMsgs = 
                    for ((mask, msg) <- commonHeaderFlags if (mask & headerFlags) == mask)
                        yield msg
                val selectIndexes =
                    for (i <- 0 until listModel.getSize() if selectedMsgs.contains(listModel.getElementAt(i))) 
                        yield i
                headerFlagsList.setSelectedIndices(selectIndexes.toArray)

    def load(profileData: ProfileData): Unit =
        profileData match
            case ProfileData(
                commonHeader,
                objectTypeData @ CritterData(
                    flagsExt: Int, 
                    scriptId: Int, 
                    headFid: Int, 
                    aiPacket: Int, 
                    teamNum: Int, 
                    critterFlags: Int, 
                    primaryStats: Array[Int], 
                    baseDrAndDt: Array[Int], 
                    age: Int, 
                    sex: Int,
                    primaryStatBonuses: Array[Int], 
                    baseDrAndDtBonuses: Array[Int], 
                    ageBonus: Int, 
                    sexBonus: Int, 
                    skills: Array[Int], 
                    bodyType: Int, 
                    expVal: Int, 
                    killType: Int, 
                    damageType: Int
                )
            ) => 
                println(s"loaded ${commonHeader}")
                println(s"loaded ${objectTypeData}")
                println(s"loaded primaryStats: ${primaryStats.toSeq}")
                println(s"loaded primaryStatBonuses: ${primaryStatBonuses.toSeq}")
                println(s"loaded baseDrAndDt: ${baseDrAndDt.toSeq}")
                println(s"loaded baseDrAndDtBonuses: ${baseDrAndDtBonuses.toSeq}")
                println(s"loaded skills: ${skills.toSeq}")

                loadCommon(commonHeader)

                println(s"loaded flagsExt: $flagsExt")
                val i = critterActionFlagValues.indexWhere(hexString => Integer.parseInt(hexString.replace("0x", ""), 16) == flagsExt)
                if (i > -1)
                    critterStatsPanel1Inputs(0).asInstanceOf[JComboBox[String]].setSelectedItem(critterActionFlagValues(i))

                critterStatsPanel1Inputs(1).asInstanceOf[JTextField].setText(scriptId.toString)
                critterStatsPanel1Inputs(2).asInstanceOf[JTextField].setText(headFid.toString)
                critterStatsPanel1Inputs(3).asInstanceOf[JTextField].setText(aiPacket.toString)
                critterStatsPanel1Inputs(4).asInstanceOf[JTextField].setText(teamNum.toString)

                val critterFlagsList = critterStatsPanel2Inputs(0).asInstanceOf[JList[String]]
                val critterListModel = critterFlagsList.getModel()
                val selectedCritterMsgs = 
                    for ((mask, msg) <- commonHeaderFlags if (mask & critterFlags) == mask)
                        yield msg
                val selectCritterIndexes =
                    for (i <- 0 until critterListModel.getSize() if selectedCritterMsgs.contains(critterListModel.getElementAt(i))) 
                        yield i
                critterFlagsList.setSelectedIndices(selectCritterIndexes.toArray)

                critterStatsPanel3Inputs(0).asInstanceOf[JComboBox[String]].setSelectedItem(bodyTypeNames(bodyType))
                critterStatsPanel3Inputs(1).asInstanceOf[JTextField].setText(expVal.toString)
                critterStatsPanel3Inputs(2).asInstanceOf[JComboBox[String]].setSelectedItem(killTypeNames(killType))
                critterStatsPanel3Inputs(3).asInstanceOf[JComboBox[String]].setSelectedItem(damageTypeNames(damageType))

                val critterSkillInputs = critterSkillsPanel.inputs
                skills.zipWithIndex.foreach{
                    case (num, index) => critterSkillInputs(index).asInstanceOf[JTextField].setText(num.toString)
                }
                baseAgePanel.inputs(0).asInstanceOf[JTextField].setText(age.toString)
                baseAgePanel.inputs(1).asInstanceOf[JTextField].setText(sex.toString)
                bonusAgePanel.inputs(0).asInstanceOf[JTextField].setText(ageBonus.toString)
                bonusAgePanel.inputs(1).asInstanceOf[JTextField].setText(sexBonus.toString)

                primaryStats.zipWithIndex.foreach{
                    case (num, index) => baseValsPanel.inputs(index).asInstanceOf[JTextField].setText(num.toString)
                }
                primaryStatBonuses.zipWithIndex.foreach{
                    case (num, index) => bonusValsPanel.inputs(index).asInstanceOf[JTextField].setText(num.toString)
                }

                baseDrAndDt.zip(baseDts.inputs).foreach{
                    case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                }
                baseDrAndDtBonuses.zip(bonusDts.inputs).foreach{
                    case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                }

            case ProfileData(
                commonHeader,
                ItemData(ItemCommonFields(itemCommonData), subTypeData)
            ) => 
                loadCommon(commonHeader)

                println(s"itemCommonData: ${itemCommonData.length}: ${itemCommonData.toSeq}")

                val (mask, checkBox) = itemFlags(0)
                checkBox.setSelected((mask & itemCommonData(0)) == mask)

                actionFlags.foreach{
                    case (mask, checkBox) => checkBox.setSelected((mask & itemCommonData(1)) == mask)
                }

                itemCommonPanel2.inputs(0).asInstanceOf[JTextField].setText(itemCommonData(2).toString)
                for (i <- Seq(3, 5, 6, 7, 8, 9, 10))
                    itemCommonPanel2.inputs(i).asInstanceOf[JTextField].setText(itemCommonData(1 + i).toString)

                val attackNum = itemCommonData(2 + 1)
                val attackMode1 = attackNum & 0x0f
                val attackMode2 = attackNum & 0xf0
                itemCommonPanel2.inputs(1).asInstanceOf[JComboBox[String]].setSelectedItem(weaponAnimCodeNames(attackMode1))
                itemCommonPanel2.inputs(2).asInstanceOf[JComboBox[String]].setSelectedItem(weaponAnimCodeNames(attackMode2))
                itemCommonPanel2.inputs(4).asInstanceOf[JComboBox[String]].setSelectedItem(itemSubtypeNames(itemCommonData(2 + 3)))

                subTypeData match
                    case ArmorFields(ac: Int, damageResists: Array[Int], damageThresholds: Array[Int], perk: Int, maleFid: Int, femaleFid: Int) =>
                        itemArmorPanel.inputs(0).asInstanceOf[JTextField].setText(ac.toString)
                        itemArmorPanel.inputs(15).asInstanceOf[JTextField].setText(perk.toString)
                        itemArmorPanel.inputs(16).asInstanceOf[JTextField].setText(maleFid.toString)
                        itemArmorPanel.inputs(17).asInstanceOf[JTextField].setText(femaleFid.toString)

                        damageResists.zip(itemArmorPanel.inputs.drop(1).take(7)).map{
                            case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                        }
                        damageThresholds.zip(itemArmorPanel.inputs.drop(8).take(7)).map{
                            case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case ContainerFields(maxSize, openFlagsData) =>
                        itemContainerPanel.inputs(0).asInstanceOf[JTextField].setText(maxSize.toString)
                        for ((mask, checkBox) <- openFlags)
                            checkBox.setSelected((mask & openFlagsData) == mask)

                    case DrugFields     (data) =>
                        data.zip(itemDrugPanel.inputs).map{
                            case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case WeaponFields   (data) =>
                        data.zip(itemWeaponPanel.inputs).zipWithIndex.map{
                            case ((num, field), index) =>
                                if (index == 0)
                                    field.asInstanceOf[JComboBox[String]].setSelectedItem(weaponAnimCodeNames(num))
                                else if (index == 3)
                                    field.asInstanceOf[JComboBox[String]].setSelectedItem(damageTypeNames(num))
                                else
                                    field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case AmmoFields     (data) =>
                        data.zip(itemAmmoPanel.inputs).map{
                            case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case ItemMiscFields (data) =>
                        data.zip(itemMiscPanel.inputs).map{
                            case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case KeyFields      (keyCode) =>
                        itemKeyPanel.inputs(0).asInstanceOf[JTextField].setText(keyCode.toString)

            case ProfileData(
                commonHeader,
                SceneryData(
                    SceneryCommonData(
                        wallLightTypeFlags: Int,
                        actionFlags: Int,
                        ScriptTypeAndId(scriptType: Int, possiblePartOfScriptId: Int, scriptId: Int),
                        scenerySubtype: Int,
                        materialId: Int,
                        soundId: Int
                    ),
                    scenerySubtypeData: ScenerySubtypeData
                )
            ) => 
                loadCommon(commonHeader)

                val wallLightListModel = wallLightTypeFlagList.getModel()
                val selectedWallLightMsgs = 
                    for ((mask, msg) <- wallLightTypeFlagValues if (mask & wallLightTypeFlags) == mask)
                        yield msg
                val selectWallLightIndexes =
                    for (i <- 0 until wallLightListModel.getSize() if selectedWallLightMsgs.contains(wallLightListModel.getElementAt(i))) 
                        yield i
                wallLightTypeFlagList.setSelectedIndices(selectWallLightIndexes.toArray)

                val actionFlagListModel = actionFlagList.getModel()
                val selectedActionMsgs = 
                    for ((mask, msg) <- actionFlagValues if (mask & actionFlags) == mask)
                        yield msg
                val selectActionIndexes =
                    for (i <- 0 until actionFlagListModel.getSize() if selectedActionMsgs.contains(actionFlagListModel.getElementAt(i))) 
                        yield i
                actionFlagList.setSelectedIndices(selectActionIndexes.toArray)

                sceneryCommonIdPanel.inputs(0).asInstanceOf[JTextField].setText(scriptType.toString)
                sceneryCommonIdPanel.inputs(1).asInstanceOf[JTextField].setText(((possiblePartOfScriptId << 16) | scriptId).toString)

                sceneryCommonIdPanel.inputs(2).asInstanceOf[JComboBox[String]].setSelectedItem(scenerySubtypeNames(scenerySubtype))
                sceneryCommonIdPanel.inputs(3).asInstanceOf[JComboBox[String]].setSelectedItem(materialNames(materialId))

                sceneryCommonIdPanel.inputs(4).asInstanceOf[JTextField].setText(soundId.toString)

                scenerySubtypeData match
                    case Door(walkThruFlagValue: Int, unknown: Int) =>
                        walkThruFlagCheckbox.setSelected((walkThruFlagValue & walkThruFlag) == walkThruFlag)
                        for ((num, label) <- doorUnknownValues)
                            if (num == unknown)
                                doorUnknownCombo.setSelectedItem(label)

                    case Stairs(data: Array[Int]) =>
                        data.zip(sceneryStairsPanel.inputs).map{
                            case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case Elevator(elevType: Int, elevLevel: Int) =>
                        sceneryElevatorPanel.inputs(0).asInstanceOf[JTextField].setText(elevType.toString)
                        sceneryElevatorPanel.inputs(1).asInstanceOf[JTextField].setText(elevLevel.toString)

                    case LadderBottom(destTileAndElev: Array[Int]) =>
                        sceneryElevatorPanel.inputs(0).asInstanceOf[JTextField].setText(destTileAndElev(0).toString)
                        sceneryElevatorPanel.inputs(1).asInstanceOf[JTextField].setText(destTileAndElev(1).toString)

                    case LadderTop(destTileAndElev: Array[Int]) =>
                        sceneryElevatorPanel.inputs(0).asInstanceOf[JTextField].setText(destTileAndElev(0).toString)
                        sceneryElevatorPanel.inputs(1).asInstanceOf[JTextField].setText(destTileAndElev(1).toString)

                    case Generic(unknown: Int) =>
                        sceneryGenericPanel.inputs(0).asInstanceOf[JTextField].setText(unknown.toString)

            case ProfileData(
                commonHeader,
                WallsData(lightTypeFlags: Int, actionFlags: Int, ScriptTypeAndId(scriptType: Int, possiblePartOfScriptId: Int, scriptId: Int), materialId: Int)
            ) => 
                loadCommon(commonHeader)

                val wallLightListModel = wallsWallLightTypeFlagList.getModel()
                val selectedWallLightMsgs = 
                    for ((mask, msg) <- wallLightTypeFlagValues if (mask & lightTypeFlags) == mask)
                        yield msg
                val selectWallLightIndexes =
                    for (i <- 0 until wallLightListModel.getSize() if selectedWallLightMsgs.contains(wallLightListModel.getElementAt(i))) 
                        yield i
                wallsWallLightTypeFlagList.setSelectedIndices(selectWallLightIndexes.toArray)

                val wallActionFlagListModel = wallActionFlagList.getModel()
                val selectedWallActionMsgs = 
                    for ((mask, msg) <- actionFlagValues if (mask & actionFlags) == mask)
                        yield msg
                val selectWallActionIndexes =
                    for (i <- 0 until wallActionFlagListModel.getSize() if selectedWallActionMsgs.contains(wallActionFlagListModel.getElementAt(i))) 
                        yield i
                wallActionFlagList.setSelectedIndices(selectWallActionIndexes.toArray)

                wallsPanel2.inputs(0).asInstanceOf[JTextField].setText(scriptType.toString)
                wallsPanel2.inputs(1).asInstanceOf[JTextField].setText(((possiblePartOfScriptId << 16) | scriptId).toString)
                wallsPanel2.inputs(2).asInstanceOf[JComboBox[String]].setSelectedItem(materialNames(materialId))

            case ProfileData(
                commonHeader,
                TilesData(materialId: Int)
            ) => 
                loadCommon(commonHeader)

                tilesPanel.inputs(0).asInstanceOf[JComboBox[String]].setSelectedItem(materialNames(materialId))

            case ProfileData(
                commonHeader,
                MiscData(unknown: Int)
            ) => 
                loadCommon(commonHeader)

                miscPanel.inputs(0).asInstanceOf[JTextField].setText(unknown.toString)

}

