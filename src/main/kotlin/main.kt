package org.igye.svg

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.w3c.dom.Attr
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


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

fun createSvg(xmlFilePath: String) {
    val documentFactory = DocumentBuilderFactory.newInstance()

    val documentBuilder = documentFactory.newDocumentBuilder()

    val document: Document = documentBuilder.newDocument()

    // root element
    val root: Element = document.createElement("company")
    document.appendChild(root)

    // employee element
    val employee: Element = document.createElement("employee")

    root.appendChild(employee)

    // set an attribute to staff element
    val attr: Attr = document.createAttribute("id")
    attr.setValue("10")
    employee.setAttributeNode(attr)

    //you can also use staff.setAttribute("id", "1") for this

    // firstname element
    val firstName: Element = document.createElement("firstname")
    firstName.appendChild(document.createTextNode("James"))
    employee.appendChild(firstName)

    // lastname element
    val lastname: Element = document.createElement("lastname")
    lastname.appendChild(document.createTextNode("Harley"))
    employee.appendChild(lastname)

    // email element
    val email: Element = document.createElement("email")
    email.appendChild(document.createTextNode("james@example.org"))
    employee.appendChild(email)

    // department elements
    val department: Element = document.createElement("department")
    department.appendChild(document.createTextNode("Human Resources"))
    employee.appendChild(department)

    // create the xml file
    //transform the DOM Object to an XML File
    val transformerFactory = TransformerFactory.newInstance()
    val transformer: Transformer = transformerFactory.newTransformer()
    val domSource = DOMSource(document)
    val streamResult = StreamResult(File(xmlFilePath))

    // If you use
    // StreamResult result = new StreamResult(System.out);
    // the output will be pushed to the standard output ...
    // You can use that for debugging
    transformer.transform(domSource, streamResult)

    println("Done creating XML File")
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