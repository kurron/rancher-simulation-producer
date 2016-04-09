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

package org.kurron.example.outbound.amqp

import org.kurron.example.MessagingContext
import org.kurron.feedback.AbstractFeedbackAware
import org.kurron.stereotype.OutboundGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.MessageChannel

/**
 * Gateway that know how to send messages to the message broker.
 */
@SuppressWarnings( 'GroovyUnusedDeclaration' )
@OutboundGateway
class MessagingGateway extends AbstractFeedbackAware implements MessagingService {

    private MessageChannel theOutput

    @Autowired
    MessagingGateway( final MessageChannel output ) {
        theOutput = output
    }

    String send( String message ) {
        feedbackProvider.sendFeedback( MessagingContext.MESSAGING_UPDATE, message )
        theOutput.send( MessageBuilder.withPayload( message ).build() )
        message
    }
}
