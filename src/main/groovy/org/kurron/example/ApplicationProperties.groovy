/*
 * Copyright (c) 2016. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kurron.example

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Custom configuration properties that are driven by Spring Boot and its application.yml file.
 */
@ConfigurationProperties( value = 'producer', ignoreUnknownFields = false )
class ApplicationProperties {

    /**
     * This property controls...
     */
    String foo

    /**
     * This property controls the name of the queue that the inbound gateway binds to.
     */
    String queueName

    /**
     * This property controls the name of the exchange that the inbound gateway binds to.
     */
    String exchangeName

    /**
     * This property controls the name of the exchange used to publish poison messages to.
     */
    String deadLetterExchangeName

    /**
     * This property controls the name of the queue used to hold poison messages.
     */
    String deadLetterQueueName

    /**
     * This property controls how many times a message will be processed before being declared a poison message.
     */
    int messageRetryAttempts

    /**
     * What to name the message stream channel.
     */
    String inputChannelName
}
