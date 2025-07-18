package com.thomaskioko.tvmaniac.locale.api

import kotlinx.coroutines.flow.Flow

public interface LocaleProvider {
    /**
     * The current locale code (e.g., "en", "fr", "es").
     */
    public val currentLocale: Flow<String>

    /**
     * Sets the locale to the specified language code.
     *
     * @param languageCode The ISO 639-1 language code (e.g., "en", "fr", "es").
     */
    public suspend fun setLocale(languageCode: String)

    /**
     * Gets the list of supported locales.
     *
     * @return A list of supported language codes.
     */
    public fun getPreferredLocales(): Flow<List<String>>
}
