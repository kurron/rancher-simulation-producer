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

package org.kurron.example.core

import org.kurron.example.outbound.amqp.MessagingService
import org.kurron.example.outbound.sql.PersistentMessage
import org.kurron.example.outbound.sql.PersistentMessageRepository
import org.kurron.feedback.AbstractFeedbackAware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * An example of a core component.  Remember, components can only interact with
 * other components and outbound gateways.  All the work they do is done in-memory,
 * no touching the network, filesystem or anything else that exists outside the process.
 **/
@Component
class DefaultEchoComponent extends AbstractFeedbackAware implements EchoComponent {

    /**
     * Outbound gateway.
     */
    private final MessagingService gateway

    private final PersistentMessageRepository repository

    @Autowired
    DefaultEchoComponent( final MessagingService aGateway,
                          final PersistentMessageRepository aRepository ) {
        gateway = aGateway
        repository = aRepository
    }

    @Override
    String processMessage( final String message ) {
        gateway.send( message )
        repository.save( new PersistentMessage( message: message ) )
        message
    }
}
