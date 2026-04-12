/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sakuravillage.network.exosource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.datasource.HttpDataSource;
import androidx.media3.datasource.TransferListener;
import com.sakuravillage.network.model.User;


/**
 A {@link Factory} that produces {@link SakuraVillageDefaultHttpDataSource} instances.
 */
public final class SakuraVillageExoHttpDataSourceFactory extends HttpDataSource.BaseFactory {

    private final String userAgent;
    private final @Nullable
    TransferListener listener;
    private final int connectTimeoutMillis;
    private final int readTimeoutMillis;
    private final boolean allowCrossProtocolRedirects;

    /**
     Constructs a SakuraVillageExoHttpDataSourceFactory. Sets {@link
    SakuraVillageDefaultHttpDataSource#DEFAULT_CONNECT_TIMEOUT_MILLIS} as the connection timeout, {@link
    SakuraVillageDefaultHttpDataSource#DEFAULT_READ_TIMEOUT_MILLIS} as the read timeout and disables
     cross-protocol redirects.

     @param userAgent The User-Agent string that should be used.
     */
    public SakuraVillageExoHttpDataSourceFactory(String userAgent) {
        this(userAgent, null);
    }

    /**
     Constructs a SakuraVillageExoHttpDataSourceFactory. Sets {@link
    SakuraVillageDefaultHttpDataSource#DEFAULT_CONNECT_TIMEOUT_MILLIS} as the connection timeout, {@link
    SakuraVillageDefaultHttpDataSource#DEFAULT_READ_TIMEOUT_MILLIS} as the read timeout and disables
     cross-protocol redirects.

     @param userAgent The User-Agent string that should be used.
     @param listener  An optional listener.
     @see #SakuraVillageExoHttpDataSourceFactory(String, TransferListener, int, int, boolean)
     */
    public SakuraVillageExoHttpDataSourceFactory(String userAgent, @Nullable TransferListener listener) {
        this(userAgent, listener, SakuraVillageDefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                SakuraVillageDefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, false);
    }

    /**
     @param userAgent                   The User-Agent string that should be used.
     @param connectTimeoutMillis        The connection timeout that should be used when requesting remote
     data, in milliseconds. A timeout of zero is interpreted as an infinite timeout.
     @param readTimeoutMillis           The read timeout that should be used when requesting remote data, in
     milliseconds. A timeout of zero is interpreted as an infinite timeout.
     @param allowCrossProtocolRedirects Whether cross-protocol redirects (i.e. redirects from HTTP
     to HTTPS and vice versa) are enabled.
     */
    public SakuraVillageExoHttpDataSourceFactory(
            String userAgent,
            int connectTimeoutMillis,
            int readTimeoutMillis,
            boolean allowCrossProtocolRedirects) {
        this(
                userAgent,
                /* listener= */ null,
                connectTimeoutMillis,
                readTimeoutMillis,
                allowCrossProtocolRedirects);
    }

    /**
     @param userAgent                   The User-Agent string that should be used.
     @param listener                    An optional listener.
     @param connectTimeoutMillis        The connection timeout that should be used when requesting remote
     data, in milliseconds. A timeout of zero is interpreted as an infinite timeout.
     @param readTimeoutMillis           The read timeout that should be used when requesting remote data, in
     milliseconds. A timeout of zero is interpreted as an infinite timeout.
     @param allowCrossProtocolRedirects Whether cross-protocol redirects (i.e. redirects from HTTP
     to HTTPS and vice versa) are enabled.
     */
    public SakuraVillageExoHttpDataSourceFactory(
            String userAgent,
            @Nullable TransferListener listener,
            int connectTimeoutMillis,
            int readTimeoutMillis,
            boolean allowCrossProtocolRedirects) {
        this.userAgent = userAgent;
        this.listener = listener;
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.readTimeoutMillis = readTimeoutMillis;
        this.allowCrossProtocolRedirects = allowCrossProtocolRedirects;
    }

    @Override
    protected SakuraVillageDefaultHttpDataSource createDataSourceInternal(
            @NonNull HttpDataSource.RequestProperties defaultRequestProperties) {
        SakuraVillageDefaultHttpDataSource dataSource =
                new SakuraVillageDefaultHttpDataSource(
                        userAgent,
                        connectTimeoutMillis,
                        readTimeoutMillis,
                        allowCrossProtocolRedirects,
                        defaultRequestProperties);
        if (listener != null) {
            dataSource.addTransferListener(listener);
        }
        return dataSource;
    }
}
