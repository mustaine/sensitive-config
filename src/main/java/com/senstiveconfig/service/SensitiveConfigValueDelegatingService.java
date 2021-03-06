package com.senstiveconfig.service;

import com.senstiveconfig.config.DecryptedValue;

import java.util.Collection;

public class SensitiveConfigValueDelegatingService {

  private final Collection<SensitiveConfigValueService> sensitiveConfigValueServices;

  public SensitiveConfigValueDelegatingService(Collection<SensitiveConfigValueService> sensitiveConfigValueServices) {
    this.sensitiveConfigValueServices = sensitiveConfigValueServices;
  }

  public DecryptedValue retrieveSecret(String secretPath) {
    return sensitiveConfigValueServices.stream()
      .filter(service -> service.matches(secretPath))
      .findFirst()
      .map(service -> service.retrieveSecret(secretPath))
      .orElseThrow(() -> new IllegalArgumentException("No Decryption Service registered for: " + secretPath));
  }
}
