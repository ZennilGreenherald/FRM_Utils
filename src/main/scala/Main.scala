import scala.jdk.StreamConverters._

import java.nio.file.{Files, Path, Paths}
import java.io.{File, InputStream}
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

case class FrmInfo(
  ver: Int, fps: Int, actionFrame: Int, framesPerDir: Int, 
  xDirShifts: Array[Int],
  yDirShifts: Array[Int],
  firstFrameOffsets: Array[Int], 
  frameAreaSize: Int, 
  dirsFrames: Seq[Seq[FrameDims]]
)

case class FrameDims(width: Int, height: Int, size: Int, offsetX: Int, offsetY: Int, colorIndexes: Array[Int])

def hello(args: String*): Unit =
  val dataPath = Paths.get(".").resolve("data")
  val paletteFilePath = dataPath.resolve("color.pal")
  val palette = getPalette(paletteFilePath)
  for (f <- Files.list(dataPath).toScala(Iterator))
    println(f.toString)
    if (f.toString.endsWith(".FRM"))
      val frmInfo = parseFile(f)
      println(frmInfo)
      writeFrameFiles(f, frmInfo.dirsFrames, palette)


def getPalette(p: Path): Array[Int] =
  val is = Files.newInputStream(p)
  try
    val a = Array.ofDim[Int](256)
    for (i <- 0 to 255)
      val r = readByte(is, 4)
      val g = readByte(is, 4)
      val b = readByte(is, 4)
      a(i) = (r << 16) | (g << 8) | b
    a
  finally
    is.close

def writeFrameFiles(p: Path, frames: Seq[Seq[FrameDims]], palette: Array[Int]): Unit =
  val outputDir = p.getParent().resolve(s"${p.getName(p.getNameCount() - 1)}.output")
  Files.createDirectories(outputDir)

  for ((dirFrames, dir) <- frames.zipWithIndex;
       (frameDims, frameNum) <- dirFrames.zipWithIndex)
    val suffix = "png"
    val imagePath = outputDir.resolve(s"$dir.$frameNum.$suffix")
    writeImage(frameDims.colorIndexes.map(i => palette(i)), frameDims.width, frameDims.height, imagePath.toFile(), suffix)


def writeImage(paletteColors: Array[Int], frameWidth: Int, frameHeight: Int, imageFile: File, suffix: String): Unit =

  val img = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB)
  var y = 0
  var x = 0
  for (c <- paletteColors)
    img.setRGB(x, y, c)
    if (x == frameWidth - 1)
      y += 1
    x = (x + 1) % frameWidth

  val wrote = ImageIO.write(img, suffix, imageFile)
  if (!wrote)
    println("No writer found for " + suffix)


def parseFile(p: Path): FrmInfo =
  val is = Files.newInputStream(p)
  try
    val ver = readInt(is)
    val fps = readShort(is)
    val actionFrame = readShort(is)
    val framesPerDir = readShort(is)
    val xDirShifts = Array.fill[Int](6) { readShort(is) }
    val yDirShifts = Array.fill[Int](6) { readShort(is) }
    val firstFrameOffsets = Array.fill[Int](6) { readInt(is) }
    val frameAreaSize = readInt(is)

    val frames: Seq[Seq[FrameDims]] =
      for (dir <- 0 to 5 if is.available() > 0)
        yield for (frameNum <- 0 until framesPerDir)
            yield readFrame(is)

    FrmInfo(ver, fps, actionFrame, framesPerDir, xDirShifts, yDirShifts, firstFrameOffsets, frameAreaSize, frames)
  finally
    is.close()


def readFrame(is: InputStream): FrameDims =
  val frameWidth = readShort(is)
  val frameHeight = readShort(is)
  val frameSize = readInt(is)
  val offsetX = readShort(is)
  val offsetY = readShort(is)
  val colorIndexes = Array.fill[Int](frameSize) { readByte(is) }

  FrameDims(frameWidth, frameHeight, frameSize, offsetX, offsetY, colorIndexes)


def readInt(is: InputStream): Int = readShort(is) << 16 | readShort(is)

def readShort(is: InputStream): Int = readByte(is) << 8 | readByte(is)

def readByte(is: InputStream): Int = readByte(is, 1)

def readIntArray(is: InputStream, size: Int): Array[Int] = Array.fill[Int](size){ readInt(is) }
def readByteArray(is: InputStream, size: Int): Array[Int] = Array.fill[Int](size){ readByte(is) }

def readByte(is: InputStream, multiplier: Int): Int =
  val num = is.read
  if (num == -1)
    throw new Exception("end of file")
  (num * multiplier) & 0xFF

