/*
 * Copyright (C) 2018 Velocity Contributors
 *
 * The Velocity API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the api top-level directory.
 */

package com.velocitypowered.api.command;

import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class ComponentArgumentType implements ArgumentType<Component> {
  private static final List<String> EXAMPLES =
          Arrays.asList("\"hello there\"", "{\"text\":\"hello!\"}", "[\"multiple\", \" elements\"]");

  @Override
  public Component parse(StringReader stringReader) throws CommandSyntaxException {
    final JsonReader jsonReader = new JsonReader(new java.io.StringReader(stringReader.getRemaining()));
    // FIXME: This feels a bit hacky, verify this is okay to do? What about version differences? Or should we only
    //        care about modern Chat Components within Velocity itself (which are backwards compatible with previous
    //        versions).
    final Component component = GsonComponentSerializer.gson().serializer().fromJson(jsonReader, Component.class);

    // Consume the amount of characters consumed when reading the JSON
    // FIXME: Obviously this Field should only be initialized once.
    final Field jsonReaderPos;
    try {
      jsonReaderPos = JsonReader.class.getDeclaredField("pos");
      jsonReaderPos.setAccessible(true);
      stringReader.setCursor(stringReader.getCursor() + jsonReaderPos.getInt(jsonReader));
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }

    return component;
  }

  @Override
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
    return Suggestions.empty();
  }

  @Override
  public Collection<String> getExamples() {
    return EXAMPLES;
  }
}
