import javax.swing.*
import javax.swing.border.*
import java.awt.*
import java.awt.dnd.*
import java.awt.datatransfer.DataFlavor
import java.awt.event.*

import java.io.File

import FlagsAndEnums.*

// TODOs:
// * a reload option
// * open recent files feature
// 'skills' tab, use quantum's FO2 calculations
// make it save!!!
// then post about it at https://www.nma-fallout.com/forums/fallout-general-modding.18/

object HeaderInputs {
    val objectTypeCombo = new JComboBox(objectTypeNames)
    val frmTypeCombo = new JComboBox(frmTypeNames)

    val headerFlagsList = new JList(commonHeaderFlags.map(_._2))
    headerFlagsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    headerFlagsList.setLayoutOrientation(JList.VERTICAL)

    val fieldPairs: Seq[(String, JComponent)] = Seq(
        ("Object Type", objectTypeCombo),
        ("Object Id", new JTextField()),
        ("Text Id", new JTextField()),
        ("Frm Type", frmTypeCombo),
        ("Frm Id", new JTextField()),
        ("Light Radius", new JTextField()),
        ("Light Intensity", new JTextField()),
        ("Flags", headerFlagsList)
    )
}

object ItemInputs {
    val actionPanel = new JPanel()
    actionPanel.setBorder(BorderFactory.createEmptyBorder())
    actionFlags.foreach(a => actionPanel.add(a._2))

    val attackModeCombo1 = new JComboBox(attackModeNames)
    val attackModeCombo2 = new JComboBox(attackModeNames)
    val itemSubtypeCombo = new JComboBox(itemSubtypeNames)

    val fieldPairs: Seq[(String, JComponent)] = Seq(
        ("Item Flag", itemFlags(0)(1)), 
        ("Action Flags", actionPanel), 
        ("Weapon Flags", new JTextField()),
        ("Attack Mode 1", attackModeCombo1), 
        ("Attack Mode 2", attackModeCombo2), 
        ("Script Id", new JTextField()),
        ("Object Subtype", itemSubtypeCombo), 
        ("Material Id", new JTextField()),
        ("Size", new JTextField()),
        ("Weight", new JTextField()),
        ("Cost", new JTextField()),
        ("Inv FID", new JTextField()),
        ("Sound Id", new JTextField())
    )
}

object ItemArmorInputs {
    val fieldPairs = Seq(
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
    ).map(s => (s, new JTextField()))
}

object ItemContainerInputs {
    val openFlagPanel = new JPanel()
    openFlagPanel.setBorder(BorderFactory.createEmptyBorder())
    openFlags.foreach(a => openFlagPanel.add(a._2))

    val fieldPairs: Seq[(String, JComponent)] = Seq(
        ("Max Size", new JTextField()),
        ("Open Flags", openFlagPanel)
    )
}

object ItemDrugInputs {
    val fieldPairs = Seq(
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
    ).map(s => (s, new JTextField()))
}

object ItemWeaponInputs {
    val weaponAnimCodeCombo = new JComboBox(weaponAnimCodeNames)
    val weaponDamageTypeCombo = new JComboBox(damageTypeNames)

    val fieldPairs: Seq[(String, JComponent)] = Seq(
        ("Anim Code", weaponAnimCodeCombo), 
        ("Min Damage", new JTextField()), 
        ("Max Damage", new JTextField()), 
        ("Damage Type", weaponDamageTypeCombo), 
        ("Max Range 1", new JTextField()), 
        ("Max Range 2", new JTextField()), 
        ("Proj PID", new JTextField()), 
        ("Min ST", new JTextField()), 
        ("AP Cost 1", new JTextField()), 
        ("MP Cost 2", new JTextField()), 
        ("Crit Fail", new JTextField()), 
        ("Perk", new JTextField()), 
        ("Rounds", new JTextField()), 
        ("Caliber", new JTextField()), 
        ("Ammo PID", new JTextField()), 
        ("Max Ammo", new JTextField()), 
        ("Sound Id", new JTextField())
    )    
}

object ItemAmmoInputs {
    val fieldPairs = Seq(
        "Caliber", 
        "Quantity", 
        "AC modifier", 
        "DC Modifier", 
        "Damage Mult", 
        "Damage Div"
    ).map(s => (s, new JTextField()))
}

object ItemMiscInputs {
    val fieldNames = Seq("Power PID", "Power Type", "Charges")    
    val fieldPairs = fieldNames.map(s => (s, new JTextField()))
}

object ItemKeyInputs {
    val fieldPairs = Seq((("Key Code"), new JTextField()))
}

object CritterInputs {
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
    val baseDtPairs  = dtAndDrNames.map(s => (s, new JTextField()))
    val bonusDtPairs = dtAndDrNames.map(s => (s, new JTextField()))

    val statNames = Seq(
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
    val baseStatPairs  = statNames.map(s => (s, new JTextField()))
    val bonusStatPairs = statNames.map(s => (s, new JTextField()))

    val critterActionFlagValues = Array("0x00002000", "0x00004000")
    val critterActionFlagCombo = new JComboBox(critterActionFlagValues)

    val critterFlagsList = new JList(critterFlags.map(_._2))
    critterFlagsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    critterFlagsList.setLayoutOrientation(JList.VERTICAL)

    val bodyTypeCombo = new JComboBox(bodyTypeNames)
    val killTypeCombo = new JComboBox(killTypeNames)
    val critterDamageTypeCombo = new JComboBox(damageTypeNames)

    val descPairs = Seq(
        ("ActionFlags", critterActionFlagCombo), 
        ("Script ID", new JTextField()),
        ("Head FID", new JTextField()),
        ("AI Packet", new JTextField()),
        ("Team Num", new JTextField()),
        ("Critter Flags", critterFlagsList), 
        ("Body Type", bodyTypeCombo), 
        ("Exp Val", new JTextField()),
        ("Kill Type", killTypeCombo), 
        ("Damage Type", critterDamageTypeCombo)
    )

    val skillNames = Seq(
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
    val skillPairs = skillNames.map(s => (s, new JTextField()))

    val ageAndGenderNames = Seq("Age", "Gender")
    val ageAndGenderPairs = ageAndGenderNames.map(s => (s, new JTextField()))
    val ageAndGenderBonusPairs = ageAndGenderNames.map(s => (s, new JTextField()))
}

object SceneryInputs {
    val wallLightTypeFlagList = new JList(wallLightTypeFlagValues.map(_._2))
    wallLightTypeFlagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    wallLightTypeFlagList.setLayoutOrientation(JList.VERTICAL)

    val actionFlagList = new JList(actionFlagValues.map(_._2))
    actionFlagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    actionFlagList.setLayoutOrientation(JList.VERTICAL)

    val scenerySubtypeCombo = new JComboBox(scenerySubtypeNames)
    val materialsCombo      = new JComboBox(materialNames)

    val fieldPairs = Seq(
        ("Wall Light Type Flags", wallLightTypeFlagList),
        ("Action Flags", actionFlagList),
        ("Script Type", new JTextField()),
        ("Script ID", new JTextField()),
        ("Scenery Subtype", scenerySubtypeCombo), 
        ("Material ID", materialsCombo), 
        ("Sound ID", new JTextField())
    )
}

object SceneryDoorInputs {
    val walkThruFlagCheckbox = new JCheckBox("yes")
    val doorUnknownCombo     = new JComboBox(doorUnknownValues.map(_._2))

    val fieldPairs = Seq(
        ("WalkThru Flag", walkThruFlagCheckbox),
        ("Unknown", doorUnknownCombo)
    )
}

object SceneryStairsInputs {
    val fieldNames = Seq("Dest Elev", "Dest Tile", "Dest Map")
    val fieldPairs = fieldNames.map(s => (s, new JTextField()))
}

object SceneryElevatorInputs {
    val fieldNames = Seq("Elev Type", "Elev Level")
    val fieldPairs = fieldNames.map(s => (s, new JTextField()))
}

object SceneryLadderBottomInputs {
    val fieldNames = Seq("Dest Tile", "Dest Elev")
    val fieldPairs = fieldNames.map(s => (s, new JTextField()))
}

object SceneryLadderTopInputs {
    val fieldNames = Seq("Dest Tile", "Dest Elev")
    val fieldPairs = fieldNames.map(s => (s, new JTextField()))
}

object SceneryGenericInputs {
    val sceneryGenericCombo = new JComboBox(doorUnknownValues.map(_._2))
    val fieldPairs = Seq(("Unknown", sceneryGenericCombo))
}

object WallsInputs {

    val wallLightTypeFlagList = new JList(wallLightTypeFlagValues.map(_._2))
    wallLightTypeFlagList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    wallLightTypeFlagList.setLayoutOrientation(JList.VERTICAL)

    val actionFlagsList = new JList(actionFlagValues.map(_._2))
    actionFlagsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    actionFlagsList.setLayoutOrientation(JList.VERTICAL)

    val materialsCombo = new JComboBox(materialNames)

    val fieldPairs = Seq(
        ("Wall Light Type Flags", wallLightTypeFlagList),
        ("ActionFlags", actionFlagsList),
        ("Script Type", new JTextField()),
        ("Script ID", new JTextField()),
        ("Material ID", materialsCombo)
    )
}

object TilesInputs {
    val tilesMaterialsCombo = new JComboBox(materialNames)
    val fieldPairs = Seq(("Material ID", tilesMaterialsCombo))
}

object MiscInputs {
    val fieldPairs = Seq((("Unknown"), new JTextField()))
}


class ProfileFrame {

    val headerFieldPanel   = fieldPanelFactory(HeaderInputs.fieldPairs)
    val itemCommonPanel    = fieldPanelFactory(ItemInputs.fieldPairs)
    val itemArmorPanel     = fieldPanelFactory(ItemArmorInputs.fieldPairs)
    val itemContainerPanel = fieldPanelFactory(ItemContainerInputs.fieldPairs)
    val itemDrugPanel      = fieldPanelFactory(ItemDrugInputs.fieldPairs)
    val itemWeaponPanel    = fieldPanelFactory(ItemWeaponInputs.fieldPairs)
    val itemAmmoPanel      = fieldPanelFactory(ItemAmmoInputs.fieldPairs)
    val itemMiscPanel      = fieldPanelFactory(ItemMiscInputs.fieldPairs)
    val itemKeyPanel       = fieldPanelFactory(ItemKeyInputs.fieldPairs)

    val itemSubtypePanel = new SwitchablePanel(Seq(
        ("Armor", itemArmorPanel),
        ("Container", itemContainerPanel),
        ("Drug", itemDrugPanel),
        ("Weapon", itemWeaponPanel),
        ("Ammo", itemAmmoPanel),
        ("Misc", itemMiscPanel),
        ("Key", itemKeyPanel),
    ))

    val baseDts = fieldPanelFactory(CritterInputs.baseDtPairs)
    val bonusDts = fieldPanelFactory(CritterInputs.bonusDtPairs)
    val dtAndDrPanels = DoubleColumnPanel(
        new LabeledPanel("Base Values", baseDts),
        new LabeledPanel("Bonuses", bonusDts)
    )

    val primaryStatsPanels = DoubleColumnPanel(
        new LabeledPanel("Base Values", fieldPanelFactory(CritterInputs.baseStatPairs)),
        new LabeledPanel("Bonuses",     fieldPanelFactory(CritterInputs.bonusStatPairs))
    )

    val baseAgePanel  = fieldPanelFactory(CritterInputs.ageAndGenderPairs)
    val bonusAgePanel = fieldPanelFactory(CritterInputs.ageAndGenderBonusPairs)
    val ageAndGenderPanels = DoubleColumnPanel(
        new LabeledPanel("Base Values", baseAgePanel), 
        new LabeledPanel("Bonuses",    bonusAgePanel)
    )

    val critterStatsPanel  = fieldPanelFactory(CritterInputs.descPairs)
    val critterSkillsPanel = fieldPanelFactory(CritterInputs.skillPairs)
    val sceneryCommonPanel = fieldPanelFactory(SceneryInputs.fieldPairs)

    val sceneryDoorPanel         = fieldPanelFactory(SceneryDoorInputs.fieldPairs)
    val sceneryStairsPanel       = fieldPanelFactory(SceneryStairsInputs.fieldPairs)
    val sceneryElevatorPanel     = fieldPanelFactory(SceneryElevatorInputs.fieldPairs)
    val sceneryLadderBottomPanel = fieldPanelFactory(SceneryLadderBottomInputs.fieldPairs)
    val sceneryLadderTopPanel    = fieldPanelFactory(SceneryLadderTopInputs.fieldPairs)
    val sceneryGenericPanel      = fieldPanelFactory(SceneryGenericInputs.fieldPairs)

    val scenerySubtypePanel = new SwitchablePanel(Seq(
        ("Door", sceneryDoorPanel),
        ("Stairs", sceneryStairsPanel),
        ("Elevator", sceneryElevatorPanel),
        ("Ladder Bottom", sceneryLadderBottomPanel),
        ("Ladder Top", sceneryLadderTopPanel),
        ("Generic", sceneryGenericPanel),
    ))

    val wallsPanel = fieldPanelFactory(WallsInputs.fieldPairs)
    val tilesPanel = fieldPanelFactory(TilesInputs.fieldPairs)
    val miscPanel  = fieldPanelFactory(MiscInputs.fieldPairs)

    val itemPanel = new TopPanel(itemCommonPanel, itemSubtypePanel)
    ItemInputs.itemSubtypeCombo.addActionListener(e => {
        itemSubtypePanel.selectRadio(itemSubtypeNames.indexOf(ItemInputs.itemSubtypeCombo.getSelectedItem().asInstanceOf[String]))
    })

    val sceneryPanel = new JPanel(new BorderLayout())
    sceneryPanel.add(sceneryCommonPanel, BorderLayout.NORTH)
    sceneryPanel.add(scenerySubtypePanel)
    SceneryInputs.scenerySubtypeCombo.addActionListener(e => {
        scenerySubtypePanel.selectRadio(scenerySubtypeNames.indexOf(SceneryInputs.scenerySubtypeCombo.getSelectedItem().asInstanceOf[String]))
    })

    val critterPanel = new JTabbedPane()
    critterPanel.addTab("Stats", null, critterStatsPanel, "Stats")
    critterPanel.addTab("Bonuses to Skills", null, critterSkillsPanel, "Skills")
    critterPanel.addTab("Age & Gender", null, ageAndGenderPanels, "Age & Gender")
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
    HeaderInputs.objectTypeCombo.addActionListener(e => {
        subtypePanel.selectRadio(objectTypeNames.indexOf(HeaderInputs.objectTypeCombo.getSelectedItem().asInstanceOf[String]))
    })

    val mainPanel = new JPanel(new BorderLayout(8, 8))
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10))

    val leftHeaderPanel = headerFieldPanel

    mainPanel.add(new LabeledPanel("Common Header", leftHeaderPanel), BorderLayout.WEST)
    mainPanel.add(subtypePanel)

    val frame = new JFrame()
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    val menuBar = new JMenuBar()
    val menu = new JMenu("File")
    menu.setMnemonic(KeyEvent.VK_F)
    menuBar.add(menu)

    val menuItem = new JMenuItem("Open", KeyEvent.VK_O)
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK))

    var previouslyOpenedFile: File = null

    menuItem.addActionListener(event => {
        if (event.getActionCommand() == "Open")
            val fc = new JFileChooser(previouslyOpenedFile)
            val returnVal = fc.showOpenDialog(frame)
            if (returnVal == JFileChooser.APPROVE_OPTION)
                Option(fc.getSelectedFile()).foreach(f => 
                    previouslyOpenedFile = f
                    load(Profiler.parseFile(f.toPath))
                )
    })
    menu.add(menuItem)

    frame.setJMenuBar(menuBar)
    frame.setDropTarget(new DropTarget() {
        override def drop(event: DropTargetDropEvent): Unit =
            try
                event.acceptDrop(DnDConstants.ACTION_COPY)
                val droppedFiles = event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)

                import collection.JavaConverters.collectionAsScalaIterableConverter
                val fileSeq = droppedFiles.asInstanceOf[java.util.List[File]].asScala // .toSeq()

                fileSeq.headOption.foreach(f => 
                    load(Profiler.parseFile(f.toPath))
                )
                event.dropComplete(true)
            catch
                case ex: Exception => ex.printStackTrace()
    })
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
                val headerInputs = HeaderInputs.fieldPairs.map(_._2)
                headerInputs(0).asInstanceOf[JComboBox[String]].setSelectedItem(objectTypeNames(objType))
                headerInputs(1).asInstanceOf[JTextField].setText(((possiblePartOfObjId << 16) | objId).toString)
                headerInputs(2).asInstanceOf[JTextField].setText(textId.toString)
                headerInputs(3).asInstanceOf[JComboBox[String]].setSelectedItem(frmTypeNames(frmType))
                headerInputs(4).asInstanceOf[JTextField].setText(((possiblePartOfFrmId << 16) | frmId).toString)
                headerInputs(5).asInstanceOf[JTextField].setText(lightRadius.toString)
                headerInputs(6).asInstanceOf[JTextField].setText(lightIntensity.toString)

                setListFromFlagNum(headerInputs(7).asInstanceOf[JList[String]], commonHeaderFlags, headerFlags)


    def setListFromFlagNum(list: JList[String], listTemplateValues: Array[(Int, String)], flagNum: Int): Unit =
        val listModel = list.getModel()
        val selectedMsgs = 
            for ((mask, msg) <- listTemplateValues if (mask & flagNum) == mask)
                yield msg
        val selectedIndexes =
            for (i <- 0 until listModel.getSize() if selectedMsgs.contains(listModel.getElementAt(i))) 
                yield i
        list.setSelectedIndices(selectedIndexes.toArray)


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
                val i = CritterInputs.critterActionFlagValues.indexWhere(hexString => Integer.parseInt(hexString.replace("0x", ""), 16) == flagsExt)
                if (i > -1)
                    CritterInputs.descPairs(0)(1).asInstanceOf[JComboBox[String]].setSelectedItem(CritterInputs.critterActionFlagValues(i))

                CritterInputs.descPairs(1)(1).asInstanceOf[JTextField].setText(scriptId.toString)
                CritterInputs.descPairs(2)(1).asInstanceOf[JTextField].setText(headFid.toString)
                CritterInputs.descPairs(3)(1).asInstanceOf[JTextField].setText(aiPacket.toString)
                CritterInputs.descPairs(4)(1).asInstanceOf[JTextField].setText(teamNum.toString)

                setListFromFlagNum(CritterInputs.descPairs(5)(1).asInstanceOf[JList[String]], commonHeaderFlags, critterFlags)

                CritterInputs.descPairs(6)(1).asInstanceOf[JComboBox[String]].setSelectedItem(bodyTypeNames(bodyType))
                CritterInputs.descPairs(7)(1).asInstanceOf[JTextField].setText(expVal.toString)
                CritterInputs.descPairs(8)(1).asInstanceOf[JComboBox[String]].setSelectedItem(killTypeNames(killType))
                CritterInputs.descPairs(9)(1).asInstanceOf[JComboBox[String]].setSelectedItem(damageTypeNames(damageType))

                val critterSkillInputs = CritterInputs.skillPairs.map(_._2)
                skills.zipWithIndex.foreach{
                    case (num, index) => critterSkillInputs(index).asInstanceOf[JTextField].setText(num.toString)
                }

                CritterInputs.ageAndGenderPairs(0)(1).asInstanceOf[JTextField].setText(age.toString)
                CritterInputs.ageAndGenderPairs(1)(1).asInstanceOf[JTextField].setText(sex.toString)
                CritterInputs.ageAndGenderBonusPairs(0)(1).asInstanceOf[JTextField].setText(ageBonus.toString)
                CritterInputs.ageAndGenderBonusPairs(1)(1).asInstanceOf[JTextField].setText(sexBonus.toString)

                primaryStats.zipWithIndex.foreach{
                    case (num, index) => CritterInputs.baseStatPairs(index)(1).asInstanceOf[JTextField].setText(num.toString)
                }
                primaryStatBonuses.zipWithIndex.foreach{
                    case (num, index) => CritterInputs.bonusStatPairs(index)(1).asInstanceOf[JTextField].setText(num.toString)
                }

                baseDrAndDt.zip(CritterInputs.baseDtPairs).foreach{
                    case (num, (_, field)) => field.asInstanceOf[JTextField].setText(num.toString)
                }
                baseDrAndDtBonuses.zip(CritterInputs.bonusDtPairs).foreach{
                    case (num, (_, field)) => field.asInstanceOf[JTextField].setText(num.toString)
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

                val itemCommonInputs = ItemInputs.fieldPairs.map(_._2)
                itemCommonInputs(2).asInstanceOf[JTextField].setText(itemCommonData(2).toString)
                for (i <- Seq(5, 7, 8, 9, 10, 11, 12))
                    itemCommonInputs(i).asInstanceOf[JTextField].setText(itemCommonData(i - 1).toString)

                val attackNum = itemCommonData(3)
                val attackMode1 = attackNum & 0x0f
                val attackMode2 = attackNum & 0xf0
                itemCommonInputs(3).asInstanceOf[JComboBox[String]].setSelectedItem(weaponAnimCodeNames(attackMode1))
                itemCommonInputs(4).asInstanceOf[JComboBox[String]].setSelectedItem(weaponAnimCodeNames(attackMode2))
                itemCommonInputs(6).asInstanceOf[JComboBox[String]].setSelectedItem(itemSubtypeNames(itemCommonData(5)))

                subTypeData match
                    case ArmorFields(ac: Int, damageResists: Array[Int], damageThresholds: Array[Int], perk: Int, maleFid: Int, femaleFid: Int) =>
                        val itemArmorFields = ItemArmorInputs.fieldPairs.map(_._2)
                        itemArmorFields(0).asInstanceOf[JTextField].setText(ac.toString)
                        itemArmorFields(15).asInstanceOf[JTextField].setText(perk.toString)
                        itemArmorFields(16).asInstanceOf[JTextField].setText(maleFid.toString)
                        itemArmorFields(17).asInstanceOf[JTextField].setText(femaleFid.toString)

                        damageResists.zip(itemArmorFields.drop(1).take(7)).map{
                            case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                        }
                        damageThresholds.zip(itemArmorFields.drop(8).take(7)).map{
                            case (num, field) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case ContainerFields(maxSize, openFlagsData) =>
                        val (name, input) = ItemContainerInputs.fieldPairs(0)
                        input.asInstanceOf[JTextField].setText(maxSize.toString)
                        for ((mask, checkBox) <- openFlags)
                            checkBox.setSelected((mask & openFlagsData) == mask)

                    case DrugFields     (data) =>
                        data.zip(ItemDrugInputs.fieldPairs).map{
                            case (num, (_, field)) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case WeaponFields   (data) =>
                        data.zip(ItemWeaponInputs.fieldPairs).zipWithIndex.map{
                            case ((num, (_, field)), index) =>
                                if (index == 0)
                                    field.asInstanceOf[JComboBox[String]].setSelectedItem(weaponAnimCodeNames(num))
                                else if (index == 3)
                                    field.asInstanceOf[JComboBox[String]].setSelectedItem(damageTypeNames(num))
                                else
                                    field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case AmmoFields     (data) =>
                        data.zip(ItemAmmoInputs.fieldPairs).map{
                            case (num, (_, field)) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case ItemMiscFields (data) =>
                        data.zip(ItemMiscInputs.fieldPairs).map{
                            case (num, (_, field)) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case KeyFields      (keyCode) =>
                        ItemKeyInputs.fieldPairs(0)(0).asInstanceOf[JTextField].setText(keyCode.toString)

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

                setListFromFlagNum(SceneryInputs.wallLightTypeFlagList, wallLightTypeFlagValues, wallLightTypeFlags)
                setListFromFlagNum(SceneryInputs.actionFlagList, actionFlagValues, actionFlags)

                val sceneryFields = SceneryInputs.fieldPairs.drop(2).map(_._2)
                sceneryFields(0).asInstanceOf[JTextField].setText(scriptType.toString)
                sceneryFields(1).asInstanceOf[JTextField].setText(((possiblePartOfScriptId << 16) | scriptId).toString)

                sceneryFields(2).asInstanceOf[JComboBox[String]].setSelectedItem(scenerySubtypeNames(scenerySubtype))
                sceneryFields(3).asInstanceOf[JComboBox[String]].setSelectedItem(materialNames(materialId))

                sceneryFields(4).asInstanceOf[JTextField].setText(soundId.toString)

                scenerySubtypeData match
                    case Door(walkThruFlagValue: Int, unknown: Int) =>
                        SceneryDoorInputs.walkThruFlagCheckbox.setSelected((walkThruFlagValue & walkThruFlag) == walkThruFlag)
                        for ((num, label) <- doorUnknownValues)
                            if (num == unknown)
                                SceneryDoorInputs.doorUnknownCombo.setSelectedItem(label)

                    case Stairs(data: Array[Int]) =>
                        data.zip(SceneryStairsInputs.fieldPairs).map{
                            case (num, (_, field)) => field.asInstanceOf[JTextField].setText(num.toString)
                        }

                    case Elevator(elevType: Int, elevLevel: Int) =>
                        SceneryElevatorInputs.fieldPairs(0)(1).asInstanceOf[JTextField].setText(elevType.toString)
                        SceneryElevatorInputs.fieldPairs(1)(1).asInstanceOf[JTextField].setText(elevLevel.toString)

                    case LadderBottom(destTileAndElev: Array[Int]) =>
                        SceneryLadderBottomInputs.fieldPairs(0)(1).asInstanceOf[JTextField].setText(destTileAndElev(0).toString)
                        SceneryLadderBottomInputs.fieldPairs(1)(1).asInstanceOf[JTextField].setText(destTileAndElev(1).toString)

                    case LadderTop(destTileAndElev: Array[Int]) =>
                        SceneryLadderTopInputs.fieldPairs(0)(1).asInstanceOf[JTextField].setText(destTileAndElev(0).toString)
                        SceneryLadderTopInputs.fieldPairs(1)(1).asInstanceOf[JTextField].setText(destTileAndElev(1).toString)

                    case Generic(unknown: Int) =>
                        val genericCombo = SceneryGenericInputs.fieldPairs(0)(1).asInstanceOf[JComboBox[String]]
                        for ((num, text) <- doorUnknownValues)
                            if (num == unknown)
                                genericCombo.setSelectedItem(text)

            case ProfileData(
                commonHeader,
                WallsData(lightTypeFlags: Int, actionFlags: Int, ScriptTypeAndId(scriptType: Int, possiblePartOfScriptId: Int, scriptId: Int), materialId: Int)
            ) => 
                loadCommon(commonHeader)

                setListFromFlagNum(WallsInputs.wallLightTypeFlagList, wallLightTypeFlagValues, lightTypeFlags)
                setListFromFlagNum(WallsInputs.actionFlagsList, actionFlagValues, actionFlags)

                WallsInputs.fieldPairs(2)(1).asInstanceOf[JTextField].setText(scriptType.toString)
                WallsInputs.fieldPairs(3)(1).asInstanceOf[JTextField].setText(((possiblePartOfScriptId << 16) | scriptId).toString)
                WallsInputs.fieldPairs(4)(1).asInstanceOf[JComboBox[String]].setSelectedItem(materialNames(materialId))

            case ProfileData(commonHeader, TilesData(materialId: Int)) => 
                loadCommon(commonHeader)
                TilesInputs.fieldPairs(0)(1).asInstanceOf[JComboBox[String]].setSelectedItem(materialNames(materialId))

            case ProfileData(commonHeader, MiscData(unknown: Int)) => 
                loadCommon(commonHeader)
                MiscInputs.fieldPairs(0)(1).asInstanceOf[JTextField].setText(unknown.toString)

}
