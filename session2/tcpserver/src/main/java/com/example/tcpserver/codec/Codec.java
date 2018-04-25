package com.example.tcpserver.codec;

import java.util.List;

public interface Codec<S, D> {
    List<D> decode(S data);
    S encode(D object);
}
