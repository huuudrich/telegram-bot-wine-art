package org.wine.art.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "messages")
class MessagesProperties {
    lateinit var start: Start
    lateinit var admin: Admin
    lateinit var payment: Payment

    class Start {
        lateinit var command: Command
    }

    class Admin {
        lateinit var command: Command
    }

    class Payment {
        lateinit var command: Command
    }

    class Command {
        lateinit var message: String
    }
}
