package eu.scattering.core.transfer.container.buffer.FCache;

public interface FCacheFactory {

    default FCache getFCache() {

        return FCache.create();
    }
}
