package net.subfx.arkplanner.js

/**
 * Engram models
 */

sealed trait Material {
  def name: String
}

sealed trait RawMaterial extends Material

sealed trait CraftedMaterial extends Material

sealed trait CraftingStation

case object Flint extends RawMaterial {
  val name: String = "Flint"
}

case object Fiber extends RawMaterial {
  val name: String = "Fiber"
}

case object Thatch extends RawMaterial {
  val name: String = "Thatch"
}

case object Wood extends RawMaterial {
  val name: String = "Wood"
}

case object Stone extends RawMaterial {
  val name: String = "Stone"
}

case object Metal extends RawMaterial {
  val name: String = "Metal"
}

case object Oil extends RawMaterial {
  val name: String = "Oil"
}

case object Obsidian extends RawMaterial {
  val name: String = "Obsidian"
}

case object Hide extends RawMaterial {
  val name: String = "Hide"
}

case object MetalIngot extends CraftedMaterial {
  val name: String = "Metal Ingot"
}

case class Stack(count: Int, material: Material)

case class Recipe(materials: List[Stack], station: Option[CraftingStation])

case class Engram(id: Int, name: String, points: Int, reqLevel: Int, imageUrl: String, recipe: Recipe, preReqs: List[Engram])

object Recipes {

  val campfire = Recipe(List(Stack(12, Thatch), Stack(1, Flint), Stack(16, Stone), Stack(2, Wood)), None)

  val stoneHatchet = Recipe(List(Stack(1, Flint), Stack(1, Wood), Stack(10, Thatch)), None)

  val spear = Recipe(List(Stack(8, Wood), Stack(12, Fiber), Stack(2, Flint)), None)

  val note = Recipe(List(Stack(3, Thatch), Stack(1, Fiber)), None)

  val clothPants = Recipe(List(Stack(50, Fiber)), None)

  val clothShirt = Recipe(List(Stack(40, Fiber)), None)

  val thatchFoundation = Recipe(List(Stack(20, Thatch), Stack(6, Wood), Stack(15, Fiber)), None)

  val thatchDoorframe = Recipe(List(Stack(8, Thatch), Stack(6, Wood), Stack(6, Fiber)), None)

  val pike = Recipe(List(Stack(10, MetalIngot), Stack(10, Wood), Stack(10, Hide)), Some(Engrams.smithy))
}

object Levels {
  val levels: Map[Int, Int] = Map(
    1 -> 0,
    2 -> 8,
    3 -> 8,
    4 -> 8,
    5 -> 8,
    6 -> 8,
    7 -> 8,
    8 -> 8,
    9 -> 8,
    10 -> 12,
    11 -> 12,
    12 -> 12,
    13 -> 12,
    14 -> 12,
    15 -> 12,
    16 -> 12,
    17 -> 12,
    18 -> 12,
    19 -> 12,
    20 -> 16,
    21 -> 16,
    22 -> 16,
    23 -> 16,
    24 -> 16,
    25 -> 16,
    26 -> 16,
    27 -> 16,
    28 -> 16,
    29 -> 16,
    30 -> 20,
    31 -> 20,
    32 -> 20,
    33 -> 20,
    34 -> 20,
    35 -> 20,
    36 -> 20,
    37 -> 20,
    38 -> 20,
    39 -> 20,
    40 -> 24,
    41 -> 24,
    42 -> 24,
    43 -> 24,
    44 -> 24,
    45 -> 24,
    46 -> 24,
    47 -> 24,
    48 -> 24,
    49 -> 24,
    50 -> 28,
    51 -> 28,
    52 -> 28,
    53 -> 28,
    54 -> 28,
    55 -> 28,
    56 -> 28,
    57 -> 28,
    58 -> 28,
    59 -> 28,
    60 -> 40,
    61 -> 40,
    62 -> 40,
    63 -> 40,
    64 -> 40,
    65 -> 1
  )

  val totalSurvivorEP: Int = levels.values.sum
}

object Engrams {

  val campfire = Engram(1, "Campfire", 3, 2,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/01/Campfire.png",
    Recipes.campfire, Nil)

  val stoneHatchet = Engram(2, "Stone Hatchet", 3, 2,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/d6/Stone_Hatchet.png",
    Recipes.stoneHatchet, Nil)

  val spear = Engram(3, "Spear", 3, 2,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/4f/Spear.png",
    Recipes.spear, Nil)

  val note = Engram(4, "Note", 3, 2,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/cc/Note.png",
    Recipes.note, Nil)

  val clothPants = Engram(5, "Cloth Pants", 3, 2,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/69/Cloth_Pants.png",
    Recipes.clothPants, Nil)

  val clothShirt = Engram(6, "Cloth Shirt", 3, 2,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f0/Cloth_Shirt.png",
    Recipes.clothShirt, Nil)

  val thatchFoundation = Engram(7, "Thatch Foundation", 3, 2,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/43/Thatch_Foundation.png",
    Recipes.thatchFoundation, Nil)

  val thatchDoorframe = Engram(8, "Thatch Doorframe", 3, 2,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/e7/Thatch_doorframe.png",
    Recipes.thatchDoorframe, Nil)

  val smithy = new Engram(74, "Smithy", 16, 25,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/b9/Smithy.png",
    Recipe(List(), None), Nil) with CraftingStation

  val pike = Engram(77, "Pike", 18, 25,
    "http://hydra-media.cursecdn.com/ark.gamepedia.com/3/31/Pike.png",
    Recipes.pike, List(spear))

  val engramList: List[Engram] = List(
    campfire,
    stoneHatchet,
    spear,
    note,
    clothPants,
    clothShirt,
    thatchFoundation,
    thatchDoorframe,
    Engram(9, "Waterskin", 6, 3, "http://hydra-media.cursecdn.com/ark.gamepedia.com/2/2a/Waterskin_%28Image_2%29.png",
      Recipe(List(), None), Nil),
    Engram(10, "Cloth Gloves", 3, 3, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/9e/Cloth_Gloves.png",
      Recipe(List(), None), Nil),
    Engram(11, "Cloth Boots", 3, 3, "http://hydra-media.cursecdn.com/ark.gamepedia.com/3/36/Cloth_Boots.png",
      Recipe(List(), None), Nil),
    Engram(12, "Cloth Hat", 3, 3, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/42/Cloth_Hat.png",
      Recipe(List(), None), Nil),
    Engram(13, "Wooden Sign", 3, 3, "http://hydra-media.cursecdn.com/ark.gamepedia.com/2/22/Wooden_Sign.png",
      Recipe(List(), None), Nil),
    Engram(14, "Hide Sleeping Bag", 3, 3, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/e8/Hide_Sleeping_Bag.png",
      Recipe(List(), None), Nil),
    Engram(15, "Thatch Roof", 3, 3, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/da/Thatch_Roof.png",
      Recipe(List(), None), Nil),
    Engram(16, "Thatch Wall", 3, 3, "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/bd/Thatch_Wall.png",
      Recipe(List(), None), Nil),
    Engram(17, "Thatch Door", 3, 3, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/04/Thatch_door.png",
      Recipe(List(), None), Nil),
    Engram(18, "Slingshot", 6, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/3/3f/Slingshot.png",
      Recipe(List(), None), Nil),
    Engram(19, "Storage Box", 6, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/d0/Storage_Box.png",
      Recipe(List(), None), Nil),
    Engram(20, "Simple Bed", 8, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/b1/Simple_Bed.png",
      Recipe(List(), None), Nil),
    Engram(21, "Phiomia Saddle", 6, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/bb/Phiomia_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(22, "Mortar and Pestle", 6, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f0/Mortar_And_Pestle.png",
      Recipe(List(), None), Nil),
    Engram(23, "Sparkpowder", 6, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/5/56/Sparkpowder.png",
      Recipe(List(), None), Nil),
    Engram(24, "Blood Extraction Syringe", 6, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/92/Blood_Extraction_Syringe.png",
      Recipe(List(), None), Nil),
    Engram(25, "Narcotic", 6, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/e6/Narcotic.png",
      Recipe(List(), None), Nil),
    Engram(26, "Flag", 6, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/7/76/Flag.png",
      Recipe(List(), None), Nil),
    Engram(27, "Standing Torch", 6, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/60/Standing_Torch.png",
      Recipe(List(), None), Nil),
    Engram(28, "Wooden Foundation", 9, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/fb/Wooden_Foundation.png",
      Recipe(List(), None), Nil),
    Engram(29, "Wooden Wall", 9, 5, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/08/Wooden_Wall.png",
      Recipe(List(), None), Nil),
    Engram(30, "Paintbrush", 3, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/c4/Paintbrush.png",
      Recipe(List(), None), Nil),
    Engram(31, "Cooking Pot", 9, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f9/Cooking_Pot.png",
      Recipe(List(), None), Nil),
    Engram(32, "Stimulant", 6, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/e2/Stimulant.png",
      Recipe(List(), None), Nil),
    Engram(33, "Gunpowder", 3, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/ae/Gunpowder.png",
      Recipe(List(), None), Nil),
    Engram(34, "Flare Gun", 6, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/5/57/Flare_Gun.png",
      Recipe(List(), None), Nil),
    Engram(35, "Spyglass", 6, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/c1/Spyglass.png",
      Recipe(List(), None), Nil),
    Engram(36, "Small Crop Plot", 9, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/5/59/Small_Crop_Plot.png",
      Recipe(List(), None), Nil),
    Engram(37, "Parasaur Saddle", 9, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/b6/Parasaur_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(38, "Stone Irrigation Pipe - Intake", 6, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/0d/Stone_Irrigation_Pipe_Intake.png",
      Recipe(List(), None), Nil),
    Engram(39, "Stone Irrigation Pipe - Straight", 3, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/a3/Stone_Irrigation_Pipe_Straight.png",
      Recipe(List(), None), Nil),
    Engram(40, "Stone Irrigation Pipe - Tap", 6, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/c9/Stone_Irrigation_Pipe_Tap.png",
      Recipe(List(), None), Nil),
    Engram(41, "Wooden Wall Sign", 3, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/cc/Wall_Sign.png",
      Recipe(List(), None), Nil),
    Engram(42, "Wooden Ceiling", 6, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/cf/Wooden_Ceiling.png",
      Recipe(List(), None), Nil),
    Engram(43, "Wooden Doorframe", 6, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/8/8c/Wooden_Doorframe.png",
      Recipe(List(), None), Nil),
    Engram(44, "Wooden Door", 6, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/2/2c/Wooden_Door.png",
      Recipe(List(), None), Nil),
    Engram(45, "Wooden Ramp", 3, 10, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f4/Wooden_Ramp.png",
      Recipe(List(), None), Nil),
    Engram(46, "Hide Pants", 9, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/44/Hide_Pants.png",
      Recipe(List(), None), Nil),
    Engram(47, "Hide Shirt", 6, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f3/Hide_Shirt.png",
      Recipe(List(), None), Nil),
    Engram(48, "Bow", 12, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/65/Bow.png",
      Recipe(List(), None), Nil),
    Engram(49, "Stone Arrow", 3, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/2/28/Stone_Arrow.png",
      Recipe(List(), None), Nil),
    Engram(50, "Large Storage Box", 9, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/1/1d/Large_Storage_Box.png",
      Recipe(List(), None), Nil),
    Engram(51, "Parachute", 6, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/8/86/Parachute.png",
      Recipe(List(), None), Nil),
    Engram(52, "Raptor Saddle", 9, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/4b/Raptor_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(53, "Stone Irrigation Pipe - Intersection", 9, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/d2/Stone_Irrigation_Pipe_Intersection.png",
      Recipe(List(), None), Nil),
    Engram(54, "Stone Irrigation Pipe - Inclined", 6, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/3/3d/Stone_Irrigation_Pipe_Inclined.png",
      Recipe(List(), None), Nil),
    Engram(55, "Stone Irrigation Pipe - Vertical", 6, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/90/Stone_Irrigation_Pipe_Vertical.png",
      Recipe(List(), None), Nil),
    Engram(56, "Compost Bin", 9, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/e1/Compost_Bin.png",
      Recipe(List(), None), Nil),
    Engram(57, "Wooden Billboard", 6, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/01/Wooden_Billboard.png",
      Recipe(List(), None), Nil),
    Engram(58, "Wooden Fence Foundation", 6, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f3/Wooden_Fence_Foundation.png",
      Recipe(List(), None), Nil),
    Engram(59, "Wooden Pillar", 9, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/3/39/Wooden_Pillar.png",
      Recipe(List(), None), Nil),
    Engram(60, "Wooden Hatchframe", 9, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/9a/Wooden_Hatchframe.png",
      Recipe(List(), None), Nil),
    Engram(61, "Wooden Ladder", 6, 15, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/c7/Wooden_Ladder.png",
      Recipe(List(), None), Nil),
    Engram(62, "Hide Gloves", 9, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/09/Hide_Gloves.png",
      Recipe(List(), None), Nil),
    Engram(63, "Hide Boots", 9, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/5/56/Hide_Boots.png",
      Recipe(List(), None), Nil),
    Engram(64, "Hide Hat", 12, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/92/Hide_Hat.png",
      Recipe(List(), None), Nil),
    Engram(65, "Tranquilizer Arrow", 15, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/be/Tranquilizer_Arrow.png",
      Recipe(List(), None), Nil),
    Engram(66, "Refining Forge", 21, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/98/Refining_Forge.png",
      Recipe(List(), None), Nil),
    Engram(67, "Trike Saddle", 12, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/de/Trike_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(68, "Dinosaur Gateway", 9, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/a8/Dinosaur_Gateway.png",
      Recipe(List(), None), Nil),
    Engram(69, "Dinosaur Gate", 6, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/b7/Dinosaur_Gate.png",
      Recipe(List(), None), Nil),
    Engram(70, "Wooden Catwalk", 12, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/c3/Wooden_Catwalk.png",
      Recipe(List(), None), Nil),
    Engram(71, "Wooden Trapdoor", 9, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f1/Wooden_Trapdoor.png",
      Recipe(List(), None), Nil),
    Engram(72, "Wooden Windowframe", 12, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/0b/Wooden_Windowframe.png",
      Recipe(List(), None), Nil),
    Engram(73, "Preserving Bin", 8, 20, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/09/Preserving_Bin.png",
      Recipe(List(), None), Nil),
    smithy,
    Engram(75, "Metal Pick", 12, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/9f/Metal_Pick.png",
      Recipe(List(), None), Nil),
    Engram(76, "Metal Hatchet", 12, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/a6/Metal_Hatchet.png",
      Recipe(List(), None), Nil),
    pike,
    Engram(78, "Compass", 16, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/92/Compass.png",
      Recipe(List(), None), Nil),
    Engram(79, "Pulmono-scorpius Saddle", 12, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/7/7a/Pulmonoscorpius_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(80, "Carbonemys Saddle", 12, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/ac/Carbonemys_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(81, "Medium Crop Plot", 12, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/d0/Medium_Crop_Plot.png",
      Recipe(List(), None), Nil),
    Engram(82, "Cementing Paste", 3, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/03/Cementing_Paste.png",
      Recipe(List(), None), Nil),
    Engram(83, "Water Tank", 7, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/ad/Water_Tank.png",
      Recipe(List(), None), Nil),
    Engram(84, "Metal Sign", 15, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/ad/Metal_Sign.png",
      Recipe(List(), None), Nil),
    Engram(85, "Wooden Window", 12, 25, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/ca/Wooden_Window.png",
      Recipe(List(), None), Nil),
    Engram(86, "Metal Spike Wall", 11, 25, "",
      Recipe(List(), None), Nil),
    Engram(87, "Chitin Leggings", 15, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/fd/Chitin_Leggings.png",
      Recipe(List(), None), Nil),
    Engram(88, "Chitin Chestpeice", 18, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f1/Chitin_Chestpiece.png",
      Recipe(List(), None), Nil),
    Engram(89, "Chitin Helmet", 18, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/7/7b/Chitin_Helmet.png",
      Recipe(List(), None), Nil),
    Engram(90, "Water Jar", 12, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/47/Water_Jar.png",
      Recipe(List(), None), Nil),
    Engram(91, "Simple Pistol", 15, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/d0/Simple_Pistol.png",
      Recipe(List(), None), Nil),
    Engram(92, "Scope Attachment", 13, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/63/Scope_Weapons.png",
      Recipe(List(), None), Nil),
    Engram(93, "Simple Bullet", 6, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/8/88/Simple_Bullet.png",
      Recipe(List(), None), Nil),
    Engram(94, "Stego Saddle", 15, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/4c/Stego_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(95, "Metal Wall Sign", 6, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/7/73/Metal_Wall_Sign.png",
      Recipe(List(), None), Nil),
    Engram(96, "Metal Foundation", 24, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/45/Metal_Foundation.png",
      Recipe(List(), None), Nil),
    Engram(97, "Metal Wall", 15, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/a9/Metal_Wall.png",
      Recipe(List(), None), Nil),
    Engram(98, "Metal Doorframe", 24, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/db/Metal_Doorframe.png",
      Recipe(List(), None), Nil),
    Engram(99, "Grenade", 20, 30, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/fb/Grenade.png",
      Recipe(List(), None), Nil),
    Engram(100, "Chitin Gauntlets", 15, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/b9/Chitin_Gauntlets.png",
      Recipe(List(), None), Nil),
    Engram(101, "Chitin Boots", 15, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/0d/Chitin_Boots.png",
      Recipe(List(), None), Nil),
    Engram(102, "Longneck Rifle", 18, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/a2/Longneck_Rifle.png",
      Recipe(List(), None), Nil),
    Engram(103, "Simple Rifle Ammo", 6, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/a0/Simple_Rifle_Ammo.png",
      Recipe(List(), None), Nil),
    Engram(104, "Large Crop Plot", 15, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/ad/Large_Crop_Plot.png",
      Recipe(List(), None), Nil),
    Engram(105, "Pteranodon Saddle", 15, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/92/Pteranodon_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(106, "Sarco Saddle", 15, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/a6/Sarco_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(107, "Shotgun", 18, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/61/Shotgun.png",
      Recipe(List(), None), Nil),
    Engram(108, "Simple Shotgun Ammo", 6, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/1/18/Simple_Shotgun_Ammo.png",
      Recipe(List(), None), Nil),
    Engram(109, "Metal Pillar", 18, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/d8/Metal_Pillar.png",
      Recipe(List(), None), Nil),
    Engram(110, "Metal Ceiling", 15, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/ac/Metal_Ceiling.png",
      Recipe(List(), None), Nil),
    Engram(111, "Metal Door", 12, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/cd/Metal_Door.png",
      Recipe(List(), None), Nil),
    Engram(112, "Metal Ramp", 12, 35, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/eb/Metal_Ramp.png",
      Recipe(List(), None), Nil),
    Engram(113, "Fabricator", 24, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/66/Fabricator.png",
      Recipe(List(), None), Nil),
    Engram(114, "Silencer Attachment", 13, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/94/Silencer_Weapons.png",
      Recipe(List(), None), Nil),
    Engram(115, "Ankylo Saddle", 18, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/5/57/Ankylo_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(116, "Mammoth Saddle", 18, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/1/19/Mammoth_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(117, "Metal Irrigation Pipe - Tap", 15, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f4/Metal_Irrigation_Pipe_Tap.png",
      Recipe(List(), None), Nil),
    Engram(118, "Metal Irrigation Pipe - Straight", 12, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/06/Metal_Irrigation_Pipe_Straight.png",
      Recipe(List(), None), Nil),
    Engram(119, "Metal Hatchframe", 18, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/e6/Metal_Hatchframe.png",
      Recipe(List(), None), Nil),
    Engram(120, "Metal Trapdoor", 14, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/e9/Metal_Trapdoor.png",
      Recipe(List(), None), Nil),
    Engram(121, "Metal Fence Foundation", 12, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/3/3d/Metal_Fence_Foundation.png",
      Recipe(List(), None), Nil),
    Engram(122, "Metal Dinosaur Gateway", 12, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/47/Metal_Dinosaur_Gateway.png",
      Recipe(List(), None), Nil),
    Engram(123, "Metal Dinosaur Gate", 8, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/2/26/Metal_Dinosaur_Gate.png",
      Recipe(List(), None), Nil),
    Engram(124, "Polymer", 6, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/8/81/Polymer.png",
      Recipe(List(), None), Nil),
    Engram(125, "Electronics", 6, 40, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/dd/Electronics.png",
      Recipe(List(), None), Nil),
    Engram(126, "Flak Leggings", 15, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/07/Flak_Leggings.png",
      Recipe(List(), None), Nil),
    Engram(127, "Flak Chestpiece", 18, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/62/Flak_Chestpeice.png",
      Recipe(List(), None), Nil),
    Engram(128, "Improvised Explosive Device", 30, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/1/15/Improvised_Explosive_Device.png",
      Recipe(List(), None), Nil),
    Engram(129, "Metal Irrigation Pipe - Intersection", 18, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f1/Metal_Irrigation_Pipe_Intersection.png",
      Recipe(List(), None), Nil),
    Engram(130, "Metal Irrigation Pipe - Inclined", 12, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/eb/Metal_Irrigation_Pipe_Inclined.png",
      Recipe(List(), None), Nil),
    Engram(131, "Metal Irrigation Pipe - Vertical", 12, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/8/81/Metal_Irrigation_Pipe_Vertical.png",
      Recipe(List(), None), Nil),
    Engram(132, "Metal Irrigation Pipe - Tap", 18, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f4/Metal_Irrigation_Pipe_Tap.png",
      Recipe(List(), None), Nil),
    Engram(133, "Megalodon Saddle", 18, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/8/80/Megalodon_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(134, "Sabertooth Saddle", 18, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/3/33/Sabertooth_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(135, "Metal Windowframe", 18, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/68/Metal_Windowframe.png",
      Recipe(List(), None), Nil),
    Engram(136, "Metal Ladder", 21, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/5/5e/Metal_Ladder.png",
      Recipe(List(), None), Nil),
    Engram(137, "Flashlight Attachment", 18, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/4a/Flashlight_Weapons.png",
      Recipe(List(), None), Nil),
    Engram(138, "GPS", 21, 45, "http://hydra-media.cursecdn.com/ark.gamepedia.com/7/72/GPS.png",
      Recipe(List(), None), Nil),
    Engram(139, "Flak Boots", 16, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/7/77/Flak_Boots.png",
      Recipe(List(), None), Nil),
    Engram(140, "Flak Gauntlets", 16, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f2/Flak_Gauntlets.png",
      Recipe(List(), None), Nil),
    Engram(141, "Flak Helmet", 20, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/ec/Flak_Helmet.png",
      Recipe(List(), None), Nil),
    Engram(142, "Metal Billboard", 15, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/2/25/Metal_Billboard.png",
      Recipe(List(), None), Nil),
    Engram(143, "Metal Window", 18, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/1/11/Metal_Window.png",
      Recipe(List(), None), Nil),
    Engram(144, "Metal Catwalk", 18, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/08/Metal_Catwalk.png",
      Recipe(List(), None), Nil),
    Engram(145, "Carno Saddle", 21, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/a9/Carno_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(146, "Electrical Generator", 24, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/92/Electrical_Generator.png",
      Recipe(List(), None), Nil),
    Engram(147, "Straight Electrical Cable", 16, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/1/1d/Straight_Electrical_Cable.png",
      Recipe(List(), None), Nil),
    Engram(148, "Electrical Outlet", 16, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f3/Electrical_Outlet.png",
      Recipe(List(), None), Nil),
    Engram(149, "Fabricated Pistol", 16, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/2/2e/Fabricated_Pistol.png",
      Recipe(List(), None), Nil),
    Engram(150, "Advanced Bullet", 18, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/3/3e/Advanced_Bullet.png",
      Recipe(List(), None), Nil),
    Engram(151, "Remote Keypad", 18, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/9b/Remote_Keypad.png",
      Recipe(List(), None), Nil),
    Engram(152, "Lamppost", 20, 50, "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/b1/Lamppost.png",
      Recipe(List(), None), Nil),
    Engram(153, "Inclined Electrical Cable", 16, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/c/c3/Inclined_Electrical_Cable.png",
      Recipe(List(), None), Nil),
    Engram(154, "Vertical Electrical Cable", 16, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/d5/Vertical_Electrical_Cable.png",
      Recipe(List(), None), Nil),
    Engram(155, "Electrical Cable Intersection", 24, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/d0/Electrical_Cable_Intersection.png",
      Recipe(List(), None), Nil),
    Engram(156, "Argentavis Saddle", 21, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/04/Argentavis_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(157, "Bronto Saddle", 21, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/a2/Bronto_Saddle.png",
      Recipe(List(), None), Nil),
    Engram(158, "Refrigerator", 20, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/d/d8/Refrigerator.png",
      Recipe(List(), None), Nil),
    Engram(159, "Air Conditioner", 21, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/2/25/Air_Conditioner.png",
      Recipe(List(), None), Nil),
    Engram(160, "C4 Remote Detonator", 24, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/8/85/C4_Remote_Detonator.png",
      Recipe(List(), None), Nil),
    Engram(161, "C4 Charge", 12, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/46/C4_Charge.png",
      Recipe(List(), None), Nil),
    Engram(162, "Assault Rifle", 24, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/e/e0/Assault_Rifle.png",
      Recipe(List(), None), Nil),
    Engram(163, "Advanced Rifle Bullet", 8, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/64/Advanced_Rifle_Bullet.png",
      Recipe(List(), None), Nil),
    Engram(164, "Laser Attachment", 24, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/b/b3/Laser_Attachment.png",
      Recipe(List(), None), Nil),
    Engram(165, "Holo-Scope Attachment", 24, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/45/Holo-Scope.png",
      Recipe(List(), None), Nil),
    Engram(166, "Behemoth Gateway", 28, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/4/49/Behemoth_Gateway.png",
      Recipe(List(), None), Nil),
    Engram(167, "Behemoth Gate", 15, 55, "http://hydra-media.cursecdn.com/ark.gamepedia.com/6/62/Behemoth_Gate.png",
      Recipe(List(), None), Nil),
    Engram(168, "Rex Saddle", 40, 60, "http://hydra-media.cursecdn.com/ark.gamepedia.com/a/af/Tyrannosaurus_saddle.png",
      Recipe(List(), None), Nil),
    Engram(169, "Spino Saddle", 40, 60, "",
      Recipe(List(), None), Nil),
    Engram(170, "Rocket Launcher", 32, 60, "http://hydra-media.cursecdn.com/ark.gamepedia.com/0/05/Rocket_Launcher.png",
      Recipe(List(), None), Nil),
    Engram(171, "Rocket Propelled Grenade", 8, 60, "http://hydra-media.cursecdn.com/ark.gamepedia.com/9/9e/Rocket_Propelled_Grenade.png",
      Recipe(List(), None), Nil),
    Engram(172, "Auto Turret", 40, 60, "http://hydra-media.cursecdn.com/ark.gamepedia.com/f/f9/Auto_Turret.png",
      Recipe(List(), None), Nil)
  )

  def engramById(id: Int): Option[Engram] = engramList find (e => e.id == id)

  val totalEngramEP: Int = (engramList map ( e => e.points )).sum

}
