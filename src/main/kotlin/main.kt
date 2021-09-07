package org.igye.svg

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


fun main(args: Array<String>) {
//    createSvg("C:\\igye\\projects\\kotlin\\learn-kotlin\\target\\ttt.xml")

//    println("12".padStart(5, '0'))
//    if (1 == 1) return

    var time = 0.0
    val dt = 1.0/30
    var i = 0
    while (time <= 5.0) {
        i++
        val scene = sceneGraphOfFunction(time)

        val iStr = i.toString().padStart(3, '0')
        val svgFilePath = "C:/igye//projects/kotlin/learn-kotlin/target/movie/generated-$iStr.svg"
        val width = 100
        val height = (width * scene.boundaries.height / scene.boundaries.width).toInt()
        createSvgFile(
            filePath = svgFilePath,
            width = width,
            height = height,
            boundaries = scene.boundaries,
            elems = scene.elems
        )

        svgToPng(
            svgPath = svgFilePath,
            pngPath = "C:/igye//projects/kotlin/learn-kotlin/target/movie/generated-$iStr.png",
            width = width,
            height = height,
        )

        time += dt
    }
}


fun svgToPng(svgPath: String, pngPath: String, width: Int = 400, height: Int = 400) {
    val svg_URI_input = File(svgPath).toURI().toString()
    val input_svg_image = TranscoderInput(svg_URI_input)
    //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
    val png_ostream: OutputStream = FileOutputStream(pngPath)
    val output_png_image = TranscoderOutput(png_ostream)
    // Step-3: Create PNGTranscoder and define hints if required
    val my_converter = PNGTranscoder()
    my_converter.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width.toFloat())
    my_converter.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height.toFloat())
    // Step-4: Convert and Write output
    my_converter.transcode(input_svg_image, output_png_image)
    png_ostream.flush()
    png_ostream.close()
}

fun strLen(str: String) = str.length