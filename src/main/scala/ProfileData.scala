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
