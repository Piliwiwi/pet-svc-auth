package com.example.auth.config

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import org.springframework.stereotype.Component

@Component
class Keys {
    private val keyPair: KeyPair = generateRSAKeyPair()
    private var privateKey: PrivateKey? = null

    private fun generateRSAKeyPair(): KeyPair {
        val keyPairGenerator: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048) // 2048-bit key size

        return keyPairGenerator.generateKeyPair()
    }

    fun getPrivateKey(): PrivateKey {
        return privateKey ?: keyPair.private
    }
}