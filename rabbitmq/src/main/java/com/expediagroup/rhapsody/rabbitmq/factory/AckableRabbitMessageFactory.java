/**
 * Copyright 2019 Expedia, Inc.
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
package com.expediagroup.rhapsody.rabbitmq.factory;

import java.util.Map;
import java.util.function.Consumer;

import com.expediagroup.rhapsody.rabbitmq.message.AckableRabbitMessage;
import com.expediagroup.rhapsody.rabbitmq.message.Acker;
import com.expediagroup.rhapsody.rabbitmq.message.RabbitMessage;
import com.expediagroup.rhapsody.util.ConfigLoading;
import com.expediagroup.rhapsody.util.Instantiation;

public interface AckableRabbitMessageFactory<T> {

    String PROPERTY = "ackable-factory";

    static <T> AckableRabbitMessageFactory<T> create(Map<String, ?> properties) {
        AckableRabbitMessageFactory<T> ackableRabbitMessageFactory =
            ConfigLoading.load(properties, PROPERTY, Instantiation::<AckableRabbitMessageFactory<T>>one)
                .orElseGet(DefaultAckableRabbitMessageFactory::new);
        ackableRabbitMessageFactory.configure(properties);
        return ackableRabbitMessageFactory;
    }

    void configure(Map<String, ?> properties);

    AckableRabbitMessage<T> create(RabbitMessage<T> rabbitMessage, Acker acker, Consumer<? super Throwable> nacknowledger);
}
