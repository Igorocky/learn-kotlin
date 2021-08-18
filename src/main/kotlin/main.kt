import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


fun main(args: Array<String>) {
    val svgUri = File("").toURI()

    val svg_URI_input = File("C:\\igye\\temp\\Prismatic-Low-Poly-Brain.svg").toURI().toString()
    val input_svg_image = TranscoderInput(svg_URI_input)
    //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
    val png_ostream: OutputStream = FileOutputStream("C:\\igye\\temp\\Prismatic-Low-Poly-Brain.png")
    val output_png_image = TranscoderOutput(png_ostream)
    // Step-3: Create PNGTranscoder and define hints if required
    val my_converter = PNGTranscoder()
    my_converter.addTranscodingHint(PNGTranscoder.KEY_WIDTH, 10000.0f)
    my_converter.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, 10000.0f)
    // Step-4: Convert and Write output
    println("It will print")
    my_converter.transcode(input_svg_image, output_png_image)
    println("It will not print")
    png_ostream.flush()
    png_ostream.close()
}

fun strLen(str: String) = str.length