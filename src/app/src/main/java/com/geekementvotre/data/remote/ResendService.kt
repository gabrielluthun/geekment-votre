package com.geekementvotre.data.remote

import com.geekementvotre.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ResendEmailRequest(
    val from: String,
    val to: List<String>,
    val subject: String,
    val html: String
)

object ResendService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }

    private val RESEND_API_URL = BuildConfig.RESEND_API_URL
    private val API_KEY = BuildConfig.RESEND_API_KEY
    private val OWNER_EMAIL = BuildConfig.CONTACT_EMAIL_RECEIVER
    private val FROM_EMAIL = BuildConfig.RESEND_FROM_EMAIL
    private const val LOGO_URL = "https://vctqkwtnoveuejtrzexk.supabase.co/storage/v1/object/public/public_assets/logo_geekement_votre.png"

    /**
     * Envoie un e-mail de notification à l'administrateur et un accusé de réception à l'utilisateur.
     */
    suspend fun sendContactEmails(nom: String, userEmail: String, sujet: String, message: String): Boolean {
        return try {

            val adminRes = client.post(RESEND_API_URL) {
                header("Authorization", "Bearer $API_KEY")
                contentType(ContentType.Application.Json)
                setBody(ResendEmailRequest(
                    from = "Geekement Votre $FROM_EMAIL",
                    to = listOf(OWNER_EMAIL),
                    subject = "Nouveau message : $sujet",
                    html = createFancyEmailHtml(nom, userEmail, sujet, message, isForAdmin = true)
                ))
            }

            val adminSuccess = adminRes.status.isSuccess()
            
            // 2. Accusé de réception pour l'utilisateur
            if (adminSuccess) {
                try {
                    client.post(RESEND_API_URL) {
                        header("Authorization", "Bearer $API_KEY")
                        contentType(ContentType.Application.Json)
                        setBody(ResendEmailRequest(
                            from = "Geekement Votre $FROM_EMAIL",
                            to = listOf(userEmail),
                            subject = "Bien reçu ! - Geekement Votre",
                            html = createFancyEmailHtml(nom, isForAdmin = false)
                        ))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            adminSuccess
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun createFancyEmailHtml(
        nom: String, 
        email: String? = null, 
        sujet: String? = null, 
        message: String? = null, 
        isForAdmin: Boolean
    ): String {
        val content = if (isForAdmin) {
            """
                <h1 style="color: #D4AF37; font-size: 22px; margin-bottom: 20px;">Nouveau message de contact</h1>
                <div style="text-align: left; background-color: #1A1A1A; padding: 20px; border-radius: 8px; border: 1px solid #333;">
                    <p style="font-size: 14px; color: #D4AF37; margin: 0 0 5px 0; text-transform: uppercase; letter-spacing: 1px;">Expéditeur</p>
                    <p style="font-size: 16px; color: #FFFFFF; margin: 0 0 15px 0;">$nom ($email)</p>
                    
                    <p style="font-size: 14px; color: #D4AF37; margin: 0 0 5px 0; text-transform: uppercase; letter-spacing: 1px;">Sujet</p>
                    <p style="font-size: 16px; color: #FFFFFF; margin: 0 0 15px 0;">$sujet</p>
                    
                    <hr style="border: 0; border-top: 1px solid #333; margin: 20px 0;" />
                    
                    <p style="font-size: 16px; color: #FFFFFF; white-space: pre-wrap; line-height: 1.6;">$message</p>
                </div>
            """.trimIndent()
        } else {
            """
                <h1 style="color: #D4AF37; font-size: 24px; margin-bottom: 20px;">Merci $nom !</h1>
                <p style="font-size: 16px; line-height: 1.6; color: #E0E0E0;">
                    Ton message a bien franchi les portails de <strong>Geekement Vôtre</strong> !
                </p>
                <p style="font-size: 16px; line-height: 1.6; color: #E0E0E0;">
                    Je suis déjà en train d'analyser ta requête avec soin et je reviendrai vers toi très rapidement.
                </p>
            """.trimIndent()
        }

        return """
            <div style="font-family: Arial, sans-serif; background-color: #0F0F0F; color: #FFFFFF; padding: 40px; text-align: center; max-width: 600px; margin: 0 auto; border: 1px solid #333;">
                <div style="margin-bottom: 30px; text-align: center;">
                    <img src="$LOGO_URL?v=1" 
                         alt="Geekement Votre" 
                         width="130" 
                         height="auto"
                         style="display: block; margin: 0 auto; border: 0; outline: none; text-decoration: none; width: 130px;" />
                </div>
                
                $content
                
                <div style="margin-top: 40px; border-top: 1px solid #333; padding-top: 20px;">
                    <p style="font-size: 14px; color: #D4AF37; font-weight: bold; margin-bottom: 5px;">Prépare tes dés, l'aventure ne fait que commencer !</p>
                    <p style="font-size: 12px; color: #888;">Gabriel — Ton MJ & Animateur Pop-Culture</p>
                </div>
            </div>
        """.trimIndent()
    }
}
