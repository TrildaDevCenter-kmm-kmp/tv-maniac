package com.thomaskioko.tvmaniac.i18n.testing.util
import com.thomaskioko.tvmaniac.i18n.PluralsResourceKey
import com.thomaskioko.tvmaniac.i18n.StringResourceKey
import dev.icerock.moko.resources.desc.StringDesc

public expect fun StringResourceKey.getString(): String

public expect fun StringResourceKey.getString(vararg args: Any): String

public expect fun PluralsResourceKey.getPlural(quantity: Int): String

public expect fun PluralsResourceKey.getPlural(quantity: Int, vararg args: Any): String

public expect suspend fun StringDesc.getString(): String
