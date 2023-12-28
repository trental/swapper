package net.warhasher.swapper.service;

import net.warhasher.swapper.dto.SwapDto;

import java.util.UUID;

public interface SwapService {

    SwapDto createSwap(SwapDto swapDto);

    void deleteSwap(UUID id);
}
