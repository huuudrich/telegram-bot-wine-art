package com.wineart.action.user

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.entities.Update
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.wineart.utils.VoidAction
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDate

@Component
class SendCertificateAction : VoidAction {
    @Value("\${files.path}")
    private lateinit var path: String

    @Value("\${qr.url.page}")
    private lateinit var urlPath: String
    override fun execute(bot: Bot, update: Update) {
        val chatId = update.message?.chat?.id ?: return

        val payment = update.message!!.successfulPayment ?: return

        val pdf = generatePdf(payment.providerPaymentChargeId, payment.invoicePayload)

        bot.sendDocument(ChatId.fromId(chatId), TelegramFile.ByFile(pdf), null, null, null, null, null, null, null)

        pdf.delete()
    }

    private fun generatePdf(paymentId: String, payload: String): File {
        val document = PDDocument.load(File("$path/certificate.pdf"))
        val page = document.getPage(0)

        val contentStream = PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)

        addText(contentStream, payload, 338.74997F, 375F)
        addText(contentStream, LocalDate.now().toString(), 146F, 375F)

        val qrCodeStream = generateQRCode("$urlPath=$paymentId")
        val qrImage = PDImageXObject.createFromByteArray(document, qrCodeStream.toByteArray(), "QR Code")

        contentStream.drawImage(qrImage, 275F, 100F, 110F, 110F)

        contentStream.close()
        val tempFile = File.createTempFile("certificate_", ".pdf")
        document.save(tempFile)
        document.close()

        return tempFile
    }

    private fun addText(contentStream: PDPageContentStream, text: String, x: Float, y: Float) {
        contentStream.beginText()
        contentStream.setFont(PDType1Font.COURIER, 12f)
        contentStream.setNonStrokingColor(Color.WHITE)
        contentStream.newLineAtOffset(x, y)
        contentStream.showText(text)
        contentStream.endText()
    }

    private fun generateQRCode(text: String, width: Int = 150, height: Int = 150): ByteArrayOutputStream {
        val qrCodeWriter = QRCodeWriter()
        val hints = mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")
        val bitMatrix: BitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints)

        val outputStream = ByteArrayOutputStream()
        val config = MatrixToImageConfig(Color.WHITE.rgb, Color.TRANSLUCENT)
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, config)
        return outputStream
    }
}