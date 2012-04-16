package com.scirisk.riskanalyzer.persistence;

import com.scirisk.riskanalyzer.domain.Network;

public interface NetworkManager {

  void save(Network network);

  Network read();

}