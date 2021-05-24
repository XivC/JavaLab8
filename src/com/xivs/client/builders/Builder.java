package com.xivs.client.builders;

import java.io.IOException;

public abstract class Builder<T> {
    public abstract T build() throws IOException;

}
