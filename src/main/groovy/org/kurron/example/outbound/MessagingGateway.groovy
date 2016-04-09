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

package org.kurron.example.outbound

import groovy.util.logging.Slf4j
import org.kurron.stereotype.OutboundGateway
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.integration.annotation.InboundChannelAdapter

/**
 * Gateway that know how to send messages to the message broker.
 */
@Slf4j
@OutboundGateway
@EnableBinding( Source )
class MessagingGateway implements MessagingService {

    @InboundChannelAdapter( Source.OUTPUT )
    String send( String message ) {
        log.info( 'Sending {}', message )
        message
    }
}
