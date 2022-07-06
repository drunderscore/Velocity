/*
 * Copyright (C) 2018 Velocity Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.velocitypowered.proxy.protocol.packet.brigadier;

import com.velocitypowered.api.command.ComponentArgumentType;
import com.velocitypowered.api.network.ProtocolVersion;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

class ComponentArgumentPropertySerializer implements ArgumentPropertySerializer<ComponentArgumentType> {
  static final ArgumentPropertySerializer<ComponentArgumentType> COMPONENT = new ComponentArgumentPropertySerializer();

  @Override
  public @Nullable ComponentArgumentType deserialize(ByteBuf buf, ProtocolVersion protocolVersion) {
    return new ComponentArgumentType();
  }

  @Override
  public void serialize(ComponentArgumentType object, ByteBuf buf, ProtocolVersion protocolVersion) {
  }
}