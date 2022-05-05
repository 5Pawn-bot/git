package com.robot.robotappliaktion.util

import android.content.Context
import android.media.MediaPlayer
import com.aldebaran.qi.sdk.`object`.conversation.PhraseSet
import com.aldebaran.qi.sdk.`object`.locale.Language
import com.aldebaran.qi.sdk.`object`.locale.Region


fun getQiLocale(locale: java.util.Locale): com.aldebaran.qi.sdk.`object`.locale.Locale {
    val qiLocale: com.aldebaran.qi.sdk.`object`.locale.Locale
    val strLocale = locale.toString()

    when {
        strLocale.contains("fr") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.FRENCH, Region.FRANCE)
        }
        strLocale.contains("zh") -> {
            qiLocale = if (strLocale == "zh_CN") {
                com.aldebaran.qi.sdk.`object`.locale.Locale(Language.CHINESE, Region.CHINA)
            } else {
                com.aldebaran.qi.sdk.`object`.locale.Locale(Language.CHINESE, Region.TAIWAN)
            }
        }
        strLocale.contains("en") -> {
            qiLocale =
                com.aldebaran.qi.sdk.`object`.locale.Locale(Language.ENGLISH, Region.UNITED_STATES)
        }
        strLocale.contains("ar") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.ARABIC, Region.EGYPT)
        }
        strLocale.contains("da") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.DANISH, Region.DENMARK)
        }
        strLocale.contains("nl") -> {
            qiLocale =
                com.aldebaran.qi.sdk.`object`.locale.Locale(Language.DUTCH, Region.NETHERLANDS)
        }
        strLocale.contains("fi") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.FINNISH, Region.FINLAND)
        }
        strLocale.contains("de") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.GERMAN, Region.GERMANY)
        }
        strLocale.contains("it") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.ITALIAN, Region.ITALY)
        }
        strLocale.contains("ja") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.JAPANESE, Region.JAPAN)
        }
        strLocale.contains("nb") -> {
            qiLocale =
                com.aldebaran.qi.sdk.`object`.locale.Locale(
                    Language.NORWEGIAN_BOKMAL,
                    Region.NORWAY
                )
        }
        strLocale.contains("es") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.SPANISH, Region.SPAIN)
        }
        strLocale.contains("sv") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.SWEDISH, Region.SWEDEN)
        }
        strLocale.contains("tr") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.TURKISH, Region.TURKEY)
        }
        strLocale.contains("cs") -> {
            qiLocale =
                com.aldebaran.qi.sdk.`object`.locale.Locale(Language.CZECH, Region.CZECH_REPUBLIC)
        }
        strLocale.contains("pl") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.POLISH, Region.POLAND)
        }
        strLocale.contains("sk") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.SLOVAK, Region.SLOVAKIA)
        }
        strLocale.contains("el") -> {
            qiLocale = com.aldebaran.qi.sdk.`object`.locale.Locale(Language.GREEK, Region.GREECE)
        }
        strLocale.contains("ko") -> {
            qiLocale =
                com.aldebaran.qi.sdk.`object`.locale.Locale(
                    Language.KOREAN,
                    Region.REPUBLIC_OF_KOREA
                )
        }
        strLocale.contains("hu") -> {
            qiLocale =
                com.aldebaran.qi.sdk.`object`.locale.Locale(Language.HUNGARIAN, Region.HUNGARY)
        }
        else -> {
            qiLocale =
                com.aldebaran.qi.sdk.`object`.locale.Locale(Language.ENGLISH, Region.UNITED_STATES)
        }
    }
    return qiLocale
}

fun String.modifyString(modify:String){
    plus(" $modify")
}

fun Array<String>.equalsTo(phrase:String,context: Context):Boolean{
    forEach { phraseToCompare ->
        if (phrase.equalsTo(phraseToCompare,context)){
            return true
        }
    }
    return false
}

fun PhraseSet.equalsTo(phraseSet: Array<String>,context: Context):Boolean{
    phrases.forEach{ phraseToCompare ->
        phraseSet.forEach { otherPhraseToCompare ->
            if (phraseToCompare.text.equalsTo(otherPhraseToCompare,context)){
                return true
            }
        }
    }

    return false
}

fun String.equalsTo(str:String,context: Context):Boolean{
    return uppercase(context.getLocaleConfiguration()).contains(str.uppercase(context.getLocaleConfiguration()))
}

 fun createSimplePhraseSet(): List<Array<String>>{
    return listOf(
        arrayOf(
            "ja",
            "ok"
        ),
        arrayOf(
            "nein",
            "sicher nicht",
            "Na"
        )
    )
}

fun Context.getLocaleConfiguration(): java.util.Locale {
    val res = applicationContext.resources
    val config = res.configuration
    return config.locale
}

fun Context.createMediaPlayer(resource: Int): MediaPlayer {
    return MediaPlayer.create(this, resource)
}
