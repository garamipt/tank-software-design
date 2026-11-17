package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.model.LevelLayout;

/**
 * Port (driven): where level layouts come from. Adapter: {@link FileLevelLayoutSource}. In-process
 * implementations: {@link RandomLevelLayoutSource}, {@link RandomBotsLayoutSource}.
 */
@FunctionalInterface
public interface LevelLayoutSource {
    LevelLayout createLayout(int width, int height);
}
