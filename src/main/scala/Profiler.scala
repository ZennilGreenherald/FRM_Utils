import scala.jdk.StreamConverters._

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import java.io.{DataOutputStream, File, FileOutputStream, InputStream}


@main def mainProfiler(args: String*): Unit = Profiler.main(args*)

/**
 * Proto file manager. Existing one only handles item and critter, not the other types.
 */
object Profiler {

  case class ProfileData(header: CommonHeader, objectTypeData: ObjectTypeData)

  case class CommonHeader(
    objTypeAndId: ObjectTypeAndId, textId: Int, 
    frmType: Int, possiblePartOfFrmId: Int, frmId: Int, 
    lightRadius: Int, lightIntensity: Int, flags: Int
  )
  case class ObjectTypeAndId(objType: Int, possiblePartOfObjId: Int, objId: Int)

  sealed trait ObjectTypeData
  case class CritterData(
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
  ) extends ObjectTypeData

  case class ItemData(commonFields: ItemCommonFields, subTypeData: ItemSubtype) extends ObjectTypeData

  case class ItemCommonFields(data: Array[Int]) {
    val subtype = data(5)
  }

  sealed trait ItemSubtype

  case class ArmorFields    (ac: Int, damageResists: Array[Int], damageThresholds: Array[Int], perk: Int, maleFid: Int, femaleFid: Int) extends ItemSubtype
  case class ContainerFields(maxSize: Int, openFlags: Int) extends ItemSubtype
  case class DrugFields     (data: Array[Int]) extends ItemSubtype
  case class WeaponFields   (data: Array[Int]) extends ItemSubtype
  case class AmmoFields     (data: Array[Int]) extends ItemSubtype
  case class ItemMiscFields (data: Array[Int]) extends ItemSubtype
  case class KeyFields      (KeyCode: Int) extends ItemSubtype

  case class WallsData(lightTypeFlags: Int, actionFlags: Int, scriptTypeAndId: ScriptTypeAndId, materialId: Int) extends ObjectTypeData
  case class TilesData(materialId: Int) extends ObjectTypeData
  case class MiscData(unknown: Int) extends ObjectTypeData
  case class ScriptTypeAndId(scriptType: Int, possiblePartOfScriptId: Int, scriptId: Int)

  case class SceneryData(commonData: SceneryCommonData, scenerySubtypeData: ScenerySubtypeData) extends ObjectTypeData
  case class SceneryCommonData(
    wallLightTypeFlags: Int,
    actionFlags: Int,
    scriptTypeAndId: ScriptTypeAndId,
    scenerySubtype: Int,
    materialId: Int,
    soundId: Int
  )
  sealed trait ScenerySubtypeData
  case class Door(walkThruFlag: Int, unknown: Int) extends ScenerySubtypeData
  case class Stairs(data: Array[Int]) extends ScenerySubtypeData
  case class Elevator(elevType: Int, elevLevel: Int) extends ScenerySubtypeData
  case class LadderBottom(destTileAndElev: Array[Int]) extends ScenerySubtypeData
  case class LadderTop(destTileAndElev: Array[Int]) extends ScenerySubtypeData
  case class Generic(unknown: Int) extends ScenerySubtypeData


  def main(args: String*): Unit =
    args match
      case List[String]("--parse", paths*) => paths.foreach((s: String) => parse(Paths.get(s)))
      case List[String]("--print", paths*) => paths.foreach((s: String) => print(Paths.get(s)))
      case List[String]("--tojson", paths*) => paths.foreach((s: String) => writeJSONFile(Paths.get(s)))
      case List[String]("--topro", paths*) => paths.foreach((s: String) => jsonToPro(Paths.get(s)))


  def print(path: Path): Unit =
    val fileData = parseFile(path)
    println(s"fileData $fileData")
    println(toJSONObjString(toKeyValuePairs(fileData)))


  def parse(path: Path): Unit =
    println(parseFile(path))


  def writeJSONFile(sourcePath: Path): Unit =
    val jsonString = toJSONObjString(toKeyValuePairs(parseFile(sourcePath)))
    Files.write(
      sourcePath.getParent().resolve(sourcePath.getName(sourcePath.getNameCount() - 1).toString + ".output.json"), 
      jsonString.getBytes(StandardCharsets.UTF_8)
    )

  
  def jsonToPro(sourcePath: Path): Unit =
    val pairs = parseJSONFile(sourcePath)
    val outPath = sourcePath.getParent().resolve(sourcePath.getName(sourcePath.getNameCount() - 1).toString + ".output.pro")
    val out = new DataOutputStream(new FileOutputStream(outPath.toFile))
    writePairsToStream(pairs, out)

  
  def parseJSONFile(sourcePath: Path): Array[(String, Int)] =
    val is = Files.newInputStream(sourcePath)
    val source = scala.io.Source.fromFile(sourcePath.toFile())
    var t = try source.mkString finally source.close()
    t = t.dropWhile(c => c.isWhitespace || c == '{')
    while (t.last.isWhitespace || t.last == '}') t = t.init
    var pairs = t.split(",").map(_.split(":").map(_.trim()))
    val tuples = pairs.map(p => {
      if (p(0)(0) == '"') p(0) = p(0).tail
      if (p(0).last == '"') p(0) = p(0).init
      (p(0), p(1).toInt)
    })
    tuples


  def writePairsToStream(pairs: Array[(String, Int)], out: DataOutputStream): Unit =
    // for (p <- pairs)
    //   println(s"pair: $p")

    pairs match
      case Array(
        ("objType", objType),
        ("possiblePartOfObjId", possiblePartOfObjId),
        ("objId", objId),
        ("textId", textId),
        ("frmType", frmType),
        ("possiblePartOfFrmId", possiblePartOfFrmId),
        ("frmId", frmId),
        ("lightRadius", lightRadius),
        ("lightIntensity", lightIntensity),
        ("flags", flags),
        rest*
      ) => 
        out.writeByte(objType)
        out.writeByte(possiblePartOfObjId)
        out.writeShort(objId)

        out.writeInt(textId)

        out.writeByte(frmType)
        out.writeByte(possiblePartOfFrmId)
        out.writeShort(frmId)

        out.writeInt(lightRadius)
        out.writeInt(lightIntensity)
        out.writeInt(flags)

        // println(s"rest: ${rest.toSeq}")

        objType match
          case 0 =>
            val itemCommonFields = rest.take(12).map(_(1))

            for (i <- 0 until 4)
              out.writeByte(itemCommonFields(i))

            for (i <- 4 until 11)
              out.writeInt(itemCommonFields(i))

            out.writeByte(itemCommonFields(11))

            val afterItemCommon = rest.drop(12)

            itemCommonFields(5) match
              case 0 => // Armor
                val armorFields = afterItemCommon.map(_(1))
                for (i <- 0 until 18)
                  out.writeInt(armorFields(i))

              case 1 => // Container
                val containerFields = afterItemCommon.map(_(1))
                for (i <- 0 until 2)
                  out.writeInt(containerFields(i))

              case 2 => // Drug
                val drugFields = afterItemCommon.map(_(1))
                for (i <- 0 until 17)
                  out.writeInt(drugFields(i))

              case 3 => // Weapon
                val weaponFields = afterItemCommon.map(_(1))
                for (i <- 0 until 16)
                  out.writeInt(weaponFields(i))
                out.writeByte(weaponFields(16))

              case 4 => // Ammo
                val ammoFields = afterItemCommon.map(_(1))
                for (i <- 0 until 6)
                  out.writeInt(ammoFields(i))

              case 5 => // Misc
                val miscFields = afterItemCommon.map(_(1))
                for (i <- 0 until 3)
                  out.writeInt(miscFields(i))

              case 6 => // Key
                val keyFields = afterItemCommon.map(_(1))
                out.writeInt(keyFields(0))

          case 1 =>
            val critterFields = rest.map(_(1))
            for (i <- critterFields)
              out.writeInt(i)

          case 2 => // scenery
            val sceneryFields = rest.map(_(1))
            out.writeShort(sceneryFields(0))
            out.writeShort(sceneryFields(1))

            out.writeByte(sceneryFields(2))
            out.writeByte(sceneryFields(3))
            out.writeShort(sceneryFields(4))

            out.writeInt(sceneryFields(5))
            out.writeInt(sceneryFields(6))
            out.writeByte(sceneryFields(7))

            sceneryFields(5) match
              case 0 => 
                val doorFields = rest.drop(8).map(_(1))
                out.writeInt(doorFields(0))
                out.writeInt(doorFields(1))

              case 1 => 
                val stairFields = rest.drop(8).map(_(1))

                out.writeByte(stairFields(0))
                out.writeByte(stairFields(1))
                out.writeByte(stairFields(2))
                out.writeByte(stairFields(3))

                out.writeInt(stairFields(4))

              case 2 => 
                val elevatorFields = rest.drop(8).map(_(1))
                out.writeInt(elevatorFields(0))
                out.writeInt(elevatorFields(1))

              case 3 | 4 => 
                val ladderFields = rest.drop(8).map(_(1))
                out.writeByte(ladderFields(0))
                out.writeByte(ladderFields(1))
                out.writeByte(ladderFields(2))
                out.writeByte(ladderFields(3))

              case 5 => 
                val genericFields = rest.drop(8).map(_(1))
                out.writeInt(genericFields(0))

          case 3 => // walls
            val wallsFields = rest.map(_(1))
            out.writeShort(wallsFields(0))
            out.writeShort(wallsFields(1))
            out.writeByte(wallsFields(2))
            out.writeByte(wallsFields(3))
            out.writeShort(wallsFields(4))
            out.writeInt(wallsFields(5))

          case 4 => // tiles
            val tilesFields = rest.map(_(1))
            out.writeInt(tilesFields(0))

          case 5 => // misc
            val miscFields = rest.map(_(1))
            out.writeInt(miscFields(0))


  def parseFile(p: Path): ProfileData =
    val is = Files.newInputStream(p)
    try
      val header = parseCommonHeader(is)
      val subtypeData = 
        header.objTypeAndId.objType match
          case 0 => parseItemData(is)
          case 1 => parseCritterData(is)
          case 2 => parseSceneryData(is)
          case 3 => parseWallsData(is)
          case 4 => TilesData(readInt(is))
          case 5 => MiscData(readInt(is))
          case _ => throw new Exception(s"Bad object type: ${header.objTypeAndId.objType}")
      ProfileData(header, subtypeData)
    finally
      is.close()


  def toJSONObjString(pairs: Array[(String, Int)]): String =
    pairs.map(p => s"\"${p._1}\": ${p._2}").mkString("{", ",\n", "}")


  def toKeyValuePairs(data: ProfileData): Array[(String, Int)] =
    data match
      case ProfileData(
        CommonHeader(ObjectTypeAndId(objType, possiblePartOfObjId, objId), textId, frmType, possiblePartOfFrmId, frmId, lightRadius, lightIntensity, flags),
        objectTypeData
      ) => 

        val headerPairs: Array[(String, Int)] = 
          Array(
            "objType", "possiblePartOfObjId", "objId", "textId", "frmType", "possiblePartOfFrmId", "frmId", "lightRadius", "lightIntensity", "flags"
          ).zip(
            Array(objType, possiblePartOfObjId, objId, textId, frmType, possiblePartOfFrmId, frmId, lightRadius, lightIntensity, flags)
          )

        val damageTypes = Array("normal", "laser", "fire", "plasma", "electrical", "emp", "explosion")

        val subtypePairs =
          objectTypeData match
            case ItemData(ItemCommonFields(commonData), subTypeData) => 

              val commonItemFieldNames = Array(
                "itemFlags", "actionFlags", "weaponFlags", "attackModes", "scriptId", 
                "subtype", "materialId", "size", "weight", "cost", "invFid", "soundId"
              )
              val commonPairs: Array[(String, Int)] = commonItemFieldNames.zip(commonData)

              val subtypePairs: Array[(String, Int)] =
                subTypeData match

                  case ArmorFields(ac, damageResists, damageThresholds, perk, maleFid, femaleFid) => 

                    Array(("AC", ac)) ++ 
                    damageTypes.map("damage_resist_" + _).zip(damageResists) ++
                    damageTypes.map("damage_threshold_" + _).zip(damageThresholds) ++
                    Array(("perk", perk), ("maleFid", maleFid), ("femaleFid", femaleFid))

                  case ContainerFields(maxSize, openFlags) => 
                    Array(("maxSize", maxSize), ("openFlags", openFlags))

                  case DrugFields(data) => 
                    val drugNames = Array(
                      "stat0", "stat1", "stat2",
                      "instandEffectAmount0", "instandEffectAmount1", "instandEffectAmount2", 
                      "duration1", "delayedEffect1Amount0", "delayedEffect1Amount1", "delayedEffect1Amount2", 
                      "duration2", "delayedEffect2Amount0", "delayedEffect2Amount1", "delayedEffect2Amount2", 
                      "addictionRate", "addictionEffect", "addictionOnset")
                    drugNames.zip(data)

                  case WeaponFields(data) => 
                    val weaponNames = Array(
                      "animCode",
                      "minDamage",
                      "maxDamage",
                      "damageType",
                      "maxRange1",
                      "maxRange2",
                      "projPid",
                      "minSt",
                      "apCost1",
                      "apCost2",
                      "critFail",
                      "perk",
                      "rounds",
                      "caliber",
                      "ammoPid",
                      "maxAmmo",
                      "soundId")
                    weaponNames.zip(data)

                  case AmmoFields(data) => 
                    Array("caliber", "quantity", "acMod", "drMod", "damMult", "damDiv").zip(data)

                  case ItemMiscFields(data) => 
                    Array("powerPid", "powerType", "charges").zip(data)

                  case KeyFields(data) => 
                    Array(("keyCode", data))

              commonPairs ++ subtypePairs

            case CritterData(
              flagsExt, 
              scriptId, 
              headFid, 
              aiPacket, 
              teamNum, 
              critterFlags, 
              primaryStats: Array[Int], 
              baseDrAndDt: Array[Int], 
              age, 
              sex,
              primaryStatBonuses: Array[Int], 
              baseDrAndDtBonuses: Array[Int], 
              ageBonus, 
              sexBonus, 
              skills: Array[Int], 
              bodyType, 
              expVal, 
              killType, 
              damageType
            ) => 

              val primaryNames = Array(
                "strength",
                "perception",
                "endurance",
                "charisma",
                "intelligence",
                "agility",
                "luck",
                "hp",
                "ap",
                "ac",
                "unarmed",
                "melee",
                "carry_weight",
                "sequence",
                "healing_rate",
                "critical_chance",
                "better_criticals",
              )

              val dtAndDRNames = (
                damageTypes.map("dt_" + _) ++
                damageTypes.map("dr_" + _) ++ 
                Array("dr_radiation", "dr_poison")
              )

              val skillNames = Array(
                "Small guns",
                "Big guns",
                "Energy weapons",
                "Unarmed",
                "Melee",
                "Throwing",
                "First aid",
                "Doctor",
                "Sneak",
                "Lockpick",
                "Steal",
                "Traps",
                "Science",
                "Repair",
                "Speech",
                "Barter",
                "Gambling",
                "Outdoorsman")

              Array(
                ("flagsExt", flagsExt), 
                ("scriptId", scriptId), 
                ("headFid", headFid), 
                ("aiPacket", aiPacket), 
                ("teamNum", teamNum), 
                ("critterFlags", critterFlags)) ++ 
              primaryNames.zip(primaryStats) ++
              dtAndDRNames.zip(baseDrAndDt) ++
              Array(
                ("age", age), 
                ("sex", sex)) ++
              primaryNames.map(_ + "_bonus").zip(primaryStatBonuses) ++
              dtAndDRNames.map(_ + "_bonus").zip(baseDrAndDtBonuses) ++
              Array(
                ("ageBonus", ageBonus), 
                ("sexBonus", sexBonus)) ++
              skillNames.zip(skills) ++
              Array(
                ("bodyType", bodyType), 
                ("expVal", expVal), 
                ("killType", killType), 
                ("damageType", damageType))

            case SceneryData(
              SceneryCommonData(
                wallLightTypeFlags,
                actionFlags,
                ScriptTypeAndId(scriptType, possiblePartOfScriptId, scriptId),
                scenerySubtype,
                materialId,
                soundId
              ),
              scenerySubtypeData
            ) =>
              val commonPairs = Array(
                ("wallLightTypeFlags", wallLightTypeFlags),
                ("actionFlags", actionFlags),
                ("scriptType", scriptType),
                ("possiblePartOfScriptId", possiblePartOfScriptId),
                ("scriptId", scriptId),
                ("scenerySubtype", scenerySubtype),
                ("materialId", materialId),
                ("soundId", soundId),
              )

              val subtypePairs: Array[(String, Int)] =
                scenerySubtypeData match
                  case Door(walkThruFlag, unknown) => Array(("walkThruFlag", walkThruFlag), ("unknown", unknown))

                  case Stairs(data: Array[Int]) => 
                    Array(
                      ("destElev", data(0)),
                      ("possibleDestElev", data(1)),
                      ("destTile0", data(2)),
                      ("destTile1", data(3)),
                      ("destMap", data(4)))

                  case Elevator(elevType, elevLevel) => Array(("elevType", elevType), ("elevLevel", elevLevel))

                  case LadderBottom(destTileAndElev) => 
                    Array(
                      ("destElev", destTileAndElev(0)),
                      ("possibleDestElev", destTileAndElev(1)),
                      ("destTile0", destTileAndElev(2)),
                      ("destTile1", destTileAndElev(3)))

                  case LadderTop(destTileAndElev) => 
                    Array(
                      ("destElev", destTileAndElev(0)),
                      ("possibleDestElev", destTileAndElev(1)),
                      ("destTile0", destTileAndElev(2)),
                      ("destTile1", destTileAndElev(3)))

                  case Generic(unknown) => Array(("unknown", unknown))

              commonPairs ++ subtypePairs

            case WallsData(lightTypeFlags, actionFlags, ScriptTypeAndId(scriptType, possiblePartOfScriptId, scriptId), materialId) =>
              Array(
                ("lightTypeFlags", lightTypeFlags),
                ("actionFlags", actionFlags),
                ("scriptType", scriptType),
                ("possiblePartOfScriptId", possiblePartOfScriptId),
                ("scriptId", scriptId),
                ("materialId", materialId)
              )

            case TilesData(materialId) => Array(("materialId", materialId))

            case MiscData(unknown) => Array(("unknown", unknown))

        headerPairs ++ subtypePairs


  def parseCommonHeader(is: InputStream): CommonHeader =
    val objTypeAndId = ObjectTypeAndId(readByte(is), readByte(is), readShort(is))

    val textId = readInt(is)

    val frmType = readByte(is)
    val possiblePartOfFrmId = readByte(is)
    val frmId = readShort(is)

    val lightRadius = readInt(is)
    val lightIntensity = readInt(is)
    val flags = readInt(is)

    CommonHeader(objTypeAndId, textId, frmType, possiblePartOfFrmId, frmId, lightRadius, lightIntensity, flags)

  def parseItemData(is: InputStream): ItemData =
    val commonFields = ItemCommonFields(readByteArray(is, 4) ++ readIntArray(is, 7) :+ readByte(is))

    if (commonFields.subtype == 0)
      ItemData(commonFields, parseItemArmorFields(is))

    else if (commonFields.subtype == 1)
      ItemData(commonFields, ContainerFields(readInt(is), readInt(is)))

    else if (commonFields.subtype == 2)
      ItemData(commonFields, DrugFields(readIntArray(is, 17)))

    else if (commonFields.subtype == 3)
      ItemData(commonFields, WeaponFields(readIntArray(is, 16) :+ readByte(is)))

    else if (commonFields.subtype == 4)
      ItemData(commonFields, AmmoFields(readIntArray(is, 6)))

    else if (commonFields.subtype == 5)
      ItemData(commonFields, ItemMiscFields(readIntArray(is, 3)))

    else if (commonFields.subtype == 6)
      ItemData(commonFields, KeyFields(readInt(is)))

    else
      throw new Exception(s"Bad item subtype: ${commonFields.subtype}")


  def parseItemArmorFields(is: InputStream): ArmorFields =
    val ac = readInt(is)
    val damageResists = readIntArray(is, 7)
    val damageThresholds = readIntArray(is, 7)

    val perk = readInt(is)
    val maleFid = readInt(is)
    val femaleFid = readInt(is)

    ArmorFields(ac, damageResists, damageThresholds, perk, maleFid, femaleFid)


  def parseCritterData(is: InputStream): CritterData =
    val Array(flagsExt, scriptId, headFid, aiPacket, teamNum, critterFlags) = readIntArray(is, 6)

    val primaryStats = readIntArray(is, 17)
    val baseDrAndDt = readIntArray(is, 16)

    val age = readInt(is)
    val sex = readInt(is)

    val primaryStatBonuses = readIntArray(is, 17)
    val baseDrAndDtBonuses = readIntArray(is, 16)

    val ageBonus = readInt(is)
    val sexBonus = readInt(is)

    val skills = readIntArray(is, 18)

    val Array(bodyType, expVal, killType, damageType) = readIntArray(is, 4)

    CritterData(flagsExt, scriptId, headFid, aiPacket, teamNum, critterFlags, primaryStats, baseDrAndDt, age, sex,
      primaryStatBonuses, baseDrAndDtBonuses, ageBonus, sexBonus, skills, bodyType, expVal, killType, damageType
    )

  def parseSceneryData(is: InputStream): SceneryData =
    val commonData = parseSceneryCommonData(is)
    if (commonData.scenerySubtype == 0)
      SceneryData(commonData, Door(readInt(is), readInt(is)))

    else if (commonData.scenerySubtype == 1)
      SceneryData(commonData, Stairs(readByteArray(is, 4) :+ readInt(is)))

    else if (commonData.scenerySubtype == 2)
      SceneryData(commonData, Elevator(readInt(is), readInt(is)))

    else if (commonData.scenerySubtype == 3)
      SceneryData(commonData, LadderBottom(readByteArray(is, 4)))

    else if (commonData.scenerySubtype == 4)
      SceneryData(commonData, LadderTop(readByteArray(is, 4)))

    else if (commonData.scenerySubtype == 5)
      SceneryData(commonData, Generic(readInt(is)))

    else
      throw new Exception(s"Bad scenery subtype: ${commonData.scenerySubtype}")


  def parseSceneryCommonData(is: InputStream): SceneryCommonData =
    val wallLightTypeFlags = readShort(is)
    val actionFlags = readShort(is)
    val scriptTypeAndId = ScriptTypeAndId(readByte(is), readByte(is), readShort(is))
    val scenerySubtype = readInt(is)
    val materialId = readInt(is)
    val soundId = readByte(is)
    SceneryCommonData(wallLightTypeFlags, actionFlags, scriptTypeAndId, scenerySubtype, materialId, soundId)


  def parseWallsData(is: InputStream): WallsData =
    val lightTypeFlags = readShort(is)
    val actionFlags = readShort(is)
    val scriptTypeAndId = ScriptTypeAndId(readByte(is), readByte(is), readShort(is))
    val materialId = readInt(is)
    WallsData(lightTypeFlags, actionFlags, scriptTypeAndId, materialId)
}
