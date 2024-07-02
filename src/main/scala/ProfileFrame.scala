import javax.swing.*
import javax.swing.border.*
import java.awt.*
import java.awt.dnd.*
import java.awt.datatransfer.DataFlavor
import java.awt.event.*

import java.io.{DataOutputStream, File, FileOutputStream}

import util.Try

import FlagsAndEnums.*

// TODOs:
// * a reload option
// * open recent files feature
// 'skills' tab, use quantum's FO2 calculations

object HeaderInputs {
    val objectTypeCombo = new JComboBox(objectTypeNames)
    val frmTypeCombo = new JComboBox(frmTypeNames)

    val headerFlagsList = new JList(commonHeaderFlags.map(_._2))
    headerFlagsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    headerFlagsList.setLayoutOrientation(JList.VERTICAL)

    val fieldPairs: Seq[(String, JComponent)] = Seq(
        ("Object Type", objectTypeCombo),
        ("Object Id", new NumberField()),
        ("Text Id", new NumberField()),
        ("Frm Type", frmTypeCombo),
        ("Frm Id", new NumberField()),
        ("Light Radius", new NumberField()),
        ("Light Intensity", new NumberField()),
        ("Flags", headerFlagsList)
    )
}

object ItemInputs {
    val actionPanel = new JPanel()
    actionPanel.setBorder(BorderFactory.createEmptyBorder())
    actionFlags.foreach(a => actionPanel.add(a._2))

    val weaponPanel = new JPanel()
    weaponPanel.setBorder(BorderFactory.createEmptyBorder())
    weaponFlags.foreach(a => weaponPanel.add(a._2))

    val attackModeCombo1 = new JComboBox(attackModeNames)
    val attackModeCombo2 = new JComboBox(attackModeNames)
    val itemSubtypeCombo = new JComboBox(itemSubtypeNames)

    val fieldPairs: Seq[(String, JComponent)] = Seq(
        ("Item Flag", itemFlags(0)(1)), 
        ("Action Flags", actionPanel), 
        ("Weapon Flags", weaponPanel),
        ("Attack Mode 1", attackModeCombo1), 
        ("Attack Mode 2", attackModeCombo2), 
        ("Script Id", new NumberField()),
        ("Object Subtype", itemSubtypeCombo), 
        ("Material Id", new NumberField()),
        ("Size", new NumberField()),
        ("Weight", new NumberField()),
        ("Cost", new NumberField()),
        ("Inv FID", new NumberField()),
        ("Sound Id", new NumberField())
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
    ).map(s => (s, new NumberField()))
}

object ItemContainerInputs {
    val openFlagPanel = new JPanel()
    openFlagPanel.setBorder(BorderFactory.createEmptyBorder())
    openFlags.foreach(a => openFlagPanel.add(a._2))

    val fieldPairs: Seq[(String, JComponent)] = Seq(
        ("Max Size", new NumberField()),
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
    ).map(s => (s, new NumberField()))
}

object ItemWeaponInputs {
    val weaponAnimCodeCombo = new JComboBox(weaponAnimCodeNames)
    val weaponDamageTypeCombo = new JComboBox(damageTypeNames)

    val fieldPairs: Seq[(String, JComponent)] = Seq(
        ("Anim Code", weaponAnimCodeCombo), 
        ("Min Damage", new NumberField()), 
        ("Max Damage", new NumberField()), 
        ("Damage Type", weaponDamageTypeCombo), 
        ("Max Range 1", new NumberField()), 
        ("Max Range 2", new NumberField()), 
        ("Proj PID", new NumberField()), 
        ("Min ST", new NumberField()), 
        ("AP Cost 1", new NumberField()), 
        ("MP Cost 2", new NumberField()), 
        ("Crit Fail", new NumberField()), 
        ("Perk", new NumberField()), 
        ("Rounds", new NumberField()), 
        ("Caliber", new NumberField()), 
        ("Ammo PID", new NumberField()), 
        ("Max Ammo", new NumberField()), 
        ("Sound Id", new NumberField())
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
    ).map(s => (s, new NumberField()))
}

object ItemMiscInputs {
    val fieldNames = Seq("Power PID", "Power Type", "Charges")    
    val fieldPairs = fieldNames.map(s => (s, new NumberField()))
}

object ItemKeyInputs {
    val fieldPairs = Seq((("Key Code"), new NumberField()))
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
    val baseDtPairs  = dtAndDrNames.map(s => (s, new NumberField()))
    val bonusDtPairs = dtAndDrNames.map(s => (s, new NumberField()))

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
    val baseStatPairs  = statNames.map(s => (s, new NumberField()))
    val bonusStatPairs = statNames.map(s => (s, new NumberField()))

    val actionFlagsValues = Array(
        (0x00002000, "0x00002000"),
        (0x00004000, "0x00004000")
    )
    val actionFlagsList = new JList(actionFlagsValues.map(_._2))
    actionFlagsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    actionFlagsList.setLayoutOrientation(JList.VERTICAL)

    val critterFlagsList = new JList(critterFlags.map(_._2))
    critterFlagsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
    critterFlagsList.setLayoutOrientation(JList.VERTICAL)

    val bodyTypeCombo = new JComboBox(bodyTypeNames)
    val killTypeCombo = new JComboBox(killTypeNames)
    val critterDamageTypeCombo = new JComboBox(damageTypeNames)

    val descPairs = Seq(
        ("ActionFlags", actionFlagsList), 
        ("Script ID", new NumberField()),
        ("Head FID", new NumberField()),
        ("AI Packet", new NumberField()),
        ("Team Num", new NumberField()),
        ("Critter Flags", critterFlagsList), 
        ("Body Type", bodyTypeCombo), 
        ("Exp Val", new NumberField()),
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
    val skillPairs = skillNames.map(s => (s, new NumberField()))

    val ageAndGenderNames = Seq("Age", "Gender")
    val ageAndGenderPairs = ageAndGenderNames.map(s => (s, new NumberField()))
    val ageAndGenderBonusPairs = ageAndGenderNames.map(s => (s, new NumberField()))
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
        ("Script Type", new NumberField()),
        ("Script ID", new NumberField()),
        ("Scenery Subtype", scenerySubtypeCombo), 
        ("Material ID", materialsCombo), 
        ("Sound ID", new NumberField())
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
    val fieldPairs = fieldNames.map(s => (s, new NumberField()))
}

object SceneryElevatorInputs {
    val fieldNames = Seq("Elev Type", "Elev Level")
    val fieldPairs = fieldNames.map(s => (s, new NumberField()))
}

object SceneryLadderBottomInputs {
    val fieldNames = Seq("Dest Tile", "Dest Elev")
    val fieldPairs = fieldNames.map(s => (s, new NumberField()))
}

object SceneryLadderTopInputs {
    val fieldNames = Seq("Dest Tile", "Dest Elev")
    val fieldPairs = fieldNames.map(s => (s, new NumberField()))
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
        ("Script Type", new NumberField()),
        ("Script ID", new NumberField()),
        ("Material ID", materialsCombo)
    )
}

object TilesInputs {
    val tilesMaterialsCombo = new JComboBox(materialNames)
    val fieldPairs = Seq(("Material ID", tilesMaterialsCombo))
}

object MiscInputs {
    val fieldPairs = Seq((("Unknown"), new NumberField()))
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

    val openMenuItem = new JMenuItem("Open...", KeyEvent.VK_O)
    openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK))

    var previouslyOpenedFile: File = null

    openMenuItem.addActionListener(event => {
        if (event.getActionCommand() == "Open...")
            val fc = new JFileChooser(previouslyOpenedFile)
            val returnVal = fc.showOpenDialog(frame)
            if (returnVal == JFileChooser.APPROVE_OPTION)
                Option(fc.getSelectedFile()).foreach(f => 
                    previouslyOpenedFile = f
                    frame.setTitle(f.toString)
                    load(Profiler.parseFile(f.toPath))
                )
    })
    menu.add(openMenuItem)

    val saveAsMenuItem = new JMenuItem("Save As...", KeyEvent.VK_A)

    saveAsMenuItem.addActionListener(event => {
        if (event.getActionCommand() == "Save As...")
            val fc = new JFileChooser(previouslyOpenedFile)
            val returnVal = fc.showSaveDialog(frame)
            if (returnVal == JFileChooser.APPROVE_OPTION)
                Option(fc.getSelectedFile()).foreach(f => 
                    previouslyOpenedFile = f
                    frame.setTitle(f.toString)
                    save(f)
                )
    })
    menu.add(saveAsMenuItem)

    frame.setJMenuBar(menuBar)
    frame.setDropTarget(new DropTarget() {
        override def drop(event: DropTargetDropEvent): Unit =
            try
                event.acceptDrop(DnDConstants.ACTION_COPY)
                val droppedFiles = event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)

                import scala.jdk.CollectionConverters.*
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


    def save(outFile: File): Unit =

        val headerInputs = HeaderInputs.fieldPairs.map(_._2)

        val objTypeNameIndex = headerInputs(0).asInstanceOf[JComboBox[String]].getSelectedIndex()
        val objType        = math.max(0, objTypeNameIndex)

        val allObjId       = Try(headerInputs(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
        val textId         = Try(headerInputs(2).asInstanceOf[JTextField].getText().toInt).getOrElse(0)

        val frmTypeNameIndex = headerInputs(3).asInstanceOf[JComboBox[String]].getSelectedIndex()
        val frmType        = math.max(0, frmTypeNameIndex)

        println(s"allFrmId ${headerInputs(4).asInstanceOf[NumberField].getText()}")
        println(s"lightRadius ${headerInputs(5).asInstanceOf[NumberField].getText()}")
        println(s"lightIntensity ${headerInputs(6).asInstanceOf[NumberField].getText()}")

        val allFrmId       = Try(headerInputs(4).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
        val lightRadius    = Try(headerInputs(5).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
        val lightIntensity = Try(headerInputs(6).asInstanceOf[JTextField].getText().toInt).getOrElse(0)

        val headerIndices = headerInputs(7).asInstanceOf[JList[String]].getSelectedIndices()
        val headerFlags = commonHeaderFlags.zipWithIndex.foldLeft(0) {
            case (acc, ((flag, _), i)) => if (headerIndices.contains(i)) acc | flag else acc
        }

        val objectTypeData = frmType match
            case 0 => 
                val itemFlagsNum = itemFlags.foldLeft(0) {
                    case (acc, (flag, checkBox)) => if (checkBox.isSelected()) acc | flag else acc
                }
                val actionFlagsNum = actionFlags.foldLeft(0) {
                    case (acc, (flag, checkBox)) => if (checkBox.isSelected()) acc | flag else acc
                }
                val weaponFlagsNum = weaponFlags.foldLeft(0) {
                    case (acc, (flag, checkBox)) => if (checkBox.isSelected()) acc | flag else acc
                }

                val itemCommonInputs = ItemInputs.fieldPairs.map(_._2)

                val attackMode1 = math.max(0, itemCommonInputs(3).asInstanceOf[JComboBox[String]].getSelectedIndex())
                val attackMode2 = math.max(0, itemCommonInputs(4).asInstanceOf[JComboBox[String]].getSelectedIndex())
                val attackNum = (attackMode2 << 4) | attackMode1

                val scriptId = Try(itemCommonInputs(5).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val objSubtype = math.max(0, itemCommonInputs(6).asInstanceOf[JComboBox[String]].getSelectedIndex())
                val itemData6 = Try(itemCommonInputs(7).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val itemData7 = Try(itemCommonInputs(8).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val itemData8 = Try(itemCommonInputs(9).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val itemData9 = Try(itemCommonInputs(10).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val itemData10 = Try(itemCommonInputs(11).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val itemData11 = Try(itemCommonInputs(12).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                
                val itemCommonData = Array(
                    itemFlagsNum,
                    actionFlagsNum,
                    weaponFlagsNum,
                    attackNum,
                    scriptId,
                    objSubtype,
                    itemData6,
                    itemData7,
                    itemData8,
                    itemData9,
                    itemData10,
                    itemData11
                )

                val subTypeData: ItemSubtype = objSubtype match
                    case 0 => 
                        val armorFields = ItemArmorInputs.fieldPairs.map(_._2).map(f => Try(f.getText().toInt).getOrElse(0))
                        ArmorFields(armorFields(0), armorFields.drop(1).take(7).toArray, armorFields.drop(8).take(7).toArray, armorFields(15), armorFields(16), armorFields(17))

                    case 1 => 
                        val (name, input) = ItemContainerInputs.fieldPairs(0)
                        val maxSize = Try(input.asInstanceOf[JTextField].getText().toInt).getOrElse(0)

                        ContainerFields(
                            maxSize,
                            openFlags.foldLeft(0) {
                                case (acc, (flag, checkBox)) => if (checkBox.isSelected()) acc | flag else acc
                            }
                        )

                    case 2 => 
                        DrugFields(
                            ItemDrugInputs.fieldPairs.map{
                                case (_, field) => Try(field.asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                            }.toArray
                        )

                    case 3 => 
                        WeaponFields(
                            ItemWeaponInputs.fieldPairs.zipWithIndex.map{
                                case ((_, field), index) =>
                                    if (index == 0 || index == 3)
                                        math.max(0, field.asInstanceOf[JComboBox[String]].getSelectedIndex())
                                    else
                                        Try(field.asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                            }.toArray
                        )

                    case 4 => 
                        AmmoFields(
                            ItemAmmoInputs.fieldPairs.map{
                                case (_, field) => Try(field.asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                            }.toArray
                        )

                    case 5 => 
                        AmmoFields(
                            ItemMiscInputs.fieldPairs.map{
                                case (_, field) => Try(field.asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                            }.toArray
                        )

                    case 6 => 
                        KeyFields(
                            Try(ItemKeyInputs.fieldPairs(0)(0).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                        )


                ItemData(ItemCommonFields(itemCommonData), subTypeData)

            case 1 =>

                val flagsExt = CritterInputs.actionFlagsValues.zipWithIndex.foldLeft(0){
                    case (acc, ((flag, _), i)) => if (CritterInputs.actionFlagsList.isSelectedIndex(i)) acc | flag else acc
                }

                val scriptId = Try(CritterInputs.descPairs(1)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val headFid  = Try(CritterInputs.descPairs(2)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val aiPacket = Try(CritterInputs.descPairs(3)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val teamNum  = Try(CritterInputs.descPairs(4)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)

                val critterFlagsValue = critterFlags.zipWithIndex.foldLeft(0){
                    case (acc, ((flag, _), i)) => if (CritterInputs.critterFlagsList.isSelectedIndex(i)) acc | flag else acc
                }

                val primaryStats = CritterInputs.baseStatPairs.map(p => Try(p._2.asInstanceOf[JTextField].getText().toInt).getOrElse(0))
                val baseDrAndDt = CritterInputs.baseDtPairs.map(p => Try(p._2.asInstanceOf[JTextField].getText().toInt).getOrElse(0))

                val age = Try(CritterInputs.ageAndGenderPairs(0)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val sex = Try(CritterInputs.ageAndGenderPairs(1)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)

                val primaryStatBonuses = CritterInputs.bonusStatPairs.map(p => Try(p._2.asInstanceOf[JTextField].getText().toInt).getOrElse(0))
                val baseDrAndDtBonuses = CritterInputs.bonusDtPairs.map(p => Try(p._2.asInstanceOf[JTextField].getText().toInt).getOrElse(0))

                val ageBonus = Try(CritterInputs.ageAndGenderBonusPairs(0)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val sexBonus = Try(CritterInputs.ageAndGenderBonusPairs(1)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)

                val skills = CritterInputs.skillPairs.map(p => Try(p._2.asInstanceOf[JTextField].getText().toInt).getOrElse(0))

                val bodyType = math.max(0, CritterInputs.descPairs(6)(1).asInstanceOf[JComboBox[String]].getSelectedIndex())
                val expVal   = Try(CritterInputs.descPairs(7)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val killType = math.max(0, CritterInputs.descPairs(8)(1).asInstanceOf[JComboBox[String]].getSelectedIndex())
                val damageType = math.max(0, CritterInputs.descPairs(9)(1).asInstanceOf[JComboBox[String]].getSelectedIndex())

                CritterData(
                    flagsExt, 
                    scriptId, 
                    headFid, 
                    aiPacket, 
                    teamNum, 
                    critterFlagsValue, 
                    primaryStats.toArray, 
                    baseDrAndDt.toArray, 
                    age, 
                    sex,
                    primaryStatBonuses.toArray, 
                    baseDrAndDtBonuses.toArray, 
                    ageBonus, 
                    sexBonus, 
                    skills.toArray, 
                    bodyType, 
                    expVal, 
                    killType, 
                    damageType
                )

            case 2 =>

                val wallLightTypeFlags = wallLightTypeFlagValues.zipWithIndex.foldLeft(0){
                    case (acc, ((flag, _), i)) => if (SceneryInputs.wallLightTypeFlagList.isSelectedIndex(i)) acc | flag else acc
                }

                val actionFlags = actionFlagValues.zipWithIndex.foldLeft(0){
                    case (acc, ((flag, _), i)) => if (SceneryInputs.actionFlagList.isSelectedIndex(i)) acc | flag else acc
                }

                val sceneryFields = SceneryInputs.fieldPairs.drop(2).map(_._2)
                val scriptType = Try(sceneryFields(0).asInstanceOf[JTextField].getText().toInt).getOrElse(0)

                val allScriptId = Try(sceneryFields(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val possiblePartOfScriptId = allScriptId >> 16
                val scriptId = allScriptId & 0xFFFF

                val scenerySubtype = math.max(0, sceneryFields(2).asInstanceOf[JComboBox[String]].getSelectedIndex())
                val materialId = math.max(0, sceneryFields(3).asInstanceOf[JComboBox[String]].getSelectedIndex())
                val soundId = Try(sceneryFields(4).asInstanceOf[JTextField].getText().toInt).getOrElse(0)

                val scenerySubtypeData = scenerySubtype match
                    case 0 =>
                        Door(
                            if (SceneryDoorInputs.walkThruFlagCheckbox.isSelected()) walkThruFlag else 0,
                            doorUnknownValues(math.max(0, SceneryDoorInputs.doorUnknownCombo.getSelectedIndex()))(0)
                        )

                    case 1 =>
                        Stairs(
                            SceneryStairsInputs.fieldPairs.map{
                                case (_, field) => Try(field.asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                            }.toArray
                        )

                    case 2 =>
                        val elevType  = Try(SceneryElevatorInputs.fieldPairs(0)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                        val elevLevel = Try(SceneryElevatorInputs.fieldPairs(1)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                        Elevator(elevType, elevLevel)

                    case 3 =>
                        LadderBottom(Array(
                            Try(SceneryLadderBottomInputs.fieldPairs(0)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0),
                            Try(SceneryLadderBottomInputs.fieldPairs(1)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                        ))

                    case 4 =>
                        LadderTop(Array(
                            Try(SceneryLadderTopInputs.fieldPairs(0)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0),
                            Try(SceneryLadderTopInputs.fieldPairs(1)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                        ))

                    case 5 =>
                        Generic(doorUnknownValues(math.max(0, SceneryGenericInputs.sceneryGenericCombo.getSelectedIndex()))(0))

                SceneryData(
                    SceneryCommonData(
                        wallLightTypeFlags,
                        actionFlags,
                        ScriptTypeAndId(scriptType, possiblePartOfScriptId, scriptId),
                        scenerySubtype,
                        materialId,
                        soundId: Int
                    ),
                    scenerySubtypeData
                )

            case 3 =>
                val lightTypeFlags = wallLightTypeFlagValues.zipWithIndex.foldLeft(0){
                    case (acc, ((flag, _), i)) => if (WallsInputs.wallLightTypeFlagList.isSelectedIndex(i)) acc | flag else acc
                }

                val actionFlags = actionFlagValues.zipWithIndex.foldLeft(0){
                    case (acc, ((flag, _), i)) => if (WallsInputs.actionFlagsList.isSelectedIndex(i)) acc | flag else acc
                }

                val scriptType = Try(WallsInputs.fieldPairs(2)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val allScriptId = Try(WallsInputs.fieldPairs(3)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                val possiblePartOfScriptId = allScriptId >> 16
                val scriptId = allScriptId & 0xFFFF

                val materialId = math.max(0, WallsInputs.fieldPairs(4)(1).asInstanceOf[JComboBox[String]].getSelectedIndex())

                WallsData(
                    lightTypeFlags,
                    actionFlags,
                    ScriptTypeAndId(scriptType, possiblePartOfScriptId, scriptId),
                    materialId: Int
                )

            case 4 =>
                val materialId = math.max(0, TilesInputs.fieldPairs(0)(1).asInstanceOf[JComboBox[String]].getSelectedIndex())
                TilesData(materialId)

            case 5 =>
                val unknown = Try(MiscInputs.fieldPairs(0)(1).asInstanceOf[JTextField].getText().toInt).getOrElse(0)
                MiscData(unknown: Int)


        val profileData = ProfileData(
            CommonHeader(
                ObjectTypeAndId(objType, allObjId >> 16, allObjId & 0xFFFF),
                textId, 
                frmType, 
                allFrmId >> 16, 
                allFrmId & 0xFFFF, 
                lightRadius, 
                lightIntensity, 
                headerFlags
            ),
            objectTypeData
        )
        println(s"saving $profileData")
        val out = new DataOutputStream(new FileOutputStream(outFile))
        Profiler.writeProfileToStream(profileData, out)


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
                    critterFlagsValue: Int,
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

                setListFromFlagNum(CritterInputs.actionFlagsList, CritterInputs.actionFlagsValues, flagsExt)

                CritterInputs.descPairs(1)(1).asInstanceOf[JTextField].setText(scriptId.toString)
                CritterInputs.descPairs(2)(1).asInstanceOf[JTextField].setText(headFid.toString)
                CritterInputs.descPairs(3)(1).asInstanceOf[JTextField].setText(aiPacket.toString)
                CritterInputs.descPairs(4)(1).asInstanceOf[JTextField].setText(teamNum.toString)

                setListFromFlagNum(CritterInputs.descPairs(5)(1).asInstanceOf[JList[String]], critterFlags, critterFlagsValue)

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
                println(s"Loading item data")
                println(s"commonHeader: $commonHeader")
                println(s"itemCommonData: ${itemCommonData.length}: ${itemCommonData.toSeq}")

                loadCommon(commonHeader)

                val (mask, checkBox) = itemFlags(0)
                checkBox.setSelected((mask & itemCommonData(0)) == mask)

                actionFlags.foreach{
                    case (mask, checkBox) => checkBox.setSelected((mask & itemCommonData(1)) == mask)
                }

                weaponFlags.foreach{
                    case (mask, checkBox) => checkBox.setSelected((mask & itemCommonData(2)) == mask)
                }

                val itemCommonInputs = ItemInputs.fieldPairs.map(_._2)
                for (i <- Seq(5, 7, 8, 9, 10, 11, 12))
                    itemCommonInputs(i).asInstanceOf[JTextField].setText(itemCommonData(i - 1).toString)

                val attackNum = itemCommonData(3)
                println(s"read attackNum $attackNum")
                val attackMode1 = attackNum & 0x0f
                val attackMode2 = (attackNum & 0xf0) >> 4
                println(s"read attackModes $attackMode1, $attackMode2")
                itemCommonInputs(3).asInstanceOf[JComboBox[String]].setSelectedItem(attackModeNames(attackMode1))
                itemCommonInputs(4).asInstanceOf[JComboBox[String]].setSelectedItem(attackModeNames(attackMode2))
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
