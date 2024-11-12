package org.wine.art.bot.user.payment

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import org.wine.art.model.Payload
import org.wine.art.model.certificate.CertificateStatus
import org.wine.art.service.certificate.CertificateService
import org.wine.art.service.certificate.arguments.CreateCertificateArg
import org.wine.art.utils.createMessage
import org.wine.art.utils.handlers.SuccessfulPaymentHandler
import java.awt.Color
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDate
import java.util.*

@Component
class SendCertificate(
    private val resourceLoader: ResourceLoader,
    private val certificateService: CertificateService
                     ) :
    SuccessfulPaymentHandler(Payload.PAYMENT_CERTIFICATE, "Отправка сертификата") {

    @Value("\${qr.url.page}")
    private lateinit var urlPath: String

    override fun handle(absSender: AbsSender, message: Message, chat: Chat) {
        val chatId = chat.id
        val payment = message.successfulPayment

        val pdf = generatePdf(payment.providerPaymentChargeId, chatId.toString())

        absSender.execute(SendDocument(chatId.toString(), InputFile(pdf, "certificate.pdf")))
        absSender.execute(createMessage(message.chatId.toString(), "Ваш платеж успешно обработан. Спасибо!"))

        certificateService.create(
            CreateCertificateArg(
                UUID.fromString(payment.providerPaymentChargeId),
                chatId,
                payment.totalAmount / 100,
                CertificateStatus.ACTIVATED
                                )
                                 )
    }

    fun generatePdf(paymentId: String, payload: String): InputStream {
        val pdfResource = resourceLoader.getResource("classpath:pdf/certificate.pdf")
        val qrCodeStream = generateQRCode("$urlPath=$paymentId")

        val outputStream = ByteArrayOutputStream()
        PDDocument.load(pdfResource.inputStream).use { document ->
            val page = document.getPage(1)
            PDPageContentStream(
                document,
                page,
                PDPageContentStream.AppendMode.APPEND,
                true,
                true
                               ).use { contentStream ->
                addText(contentStream, payload, 320.74997F, 375F)
                addText(contentStream, LocalDate.now().toString(), 147F, 375F)

                val qrImage = PDImageXObject.createFromByteArray(document, qrCodeStream.toByteArray(), "QR Code")
                contentStream.drawImage(qrImage, 275F, 100F, 110F, 110F)
            }
            document.save(outputStream)
        }

        return ByteArrayInputStream(outputStream.toByteArray())
    }

    private fun addText(contentStream: PDPageContentStream, text: String, x: Float, y: Float) {
        contentStream.beginText()
        contentStream.setFont(PDType1Font.HELVETICA, 12f)
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