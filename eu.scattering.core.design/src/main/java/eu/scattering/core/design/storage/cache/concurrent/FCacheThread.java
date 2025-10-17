package eu.scattering.core.design.storage.cache.concurrent;

import eu.scattering.core.design.storage.cache.serial.FCache;

public interface FCacheThread extends FCache {

    int getNumberOfThreads();
}
