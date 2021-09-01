package org.igye.svg

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


fun main(args: Array<String>) {
//    createSvg("C:\\igye\\projects\\kotlin\\learn-kotlin\\target\\ttt.xml")

    val svgFilePath = "C:/igye//projects/kotlin/learn-kotlin/target/generated-1.svg"
    createSvgFile(
        filePath = svgFilePath,
        width = 200,
        height = 200,
        Boundaries(minX = -10.0, maxX = 10.0, minY = -10.0, maxY = 10.0),
        Rect(x = -1000.0, y=-1000.0, width = 2000.0, height = 2000.0, fill = "grey"),
        Rect(x = 0.0, y=0.0, width = 5.0, height = 5.0, fill = "green"),
    )

    svgToPng(
        svgPath = svgFilePath,
        pngPath = "C:/igye//projects/kotlin/learn-kotlin/target/generated-1.png"
    )
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