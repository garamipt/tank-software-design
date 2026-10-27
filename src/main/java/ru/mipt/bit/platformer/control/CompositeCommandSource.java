package ru.mipt.bit.platformer.control;

import java.util.ArrayList;
import java.util.List;

public class CompositeCommandSource implements CommandSource {
    private final List<CommandSource> sources;

    public CompositeCommandSource(List<CommandSource> sources) {
        this.sources = List.copyOf(sources);
    }

    @Override
    public List<Command> getActiveCommands() {
        List<Command> commands = new ArrayList<>();
        for (CommandSource source : sources) {
            commands.addAll(source.getActiveCommands());
        }
        return commands;
    }
}
