// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
////////////////////////////////////////////////////////////////////////////////

package com.google.crypto.tink.signature;

import com.google.crypto.tink.Registry;
import com.google.crypto.tink.proto.RegistryConfig;
import java.security.GeneralSecurityException;

/**
 * Static methods and constants for registering with the {@link Registry} all instances of {@link
 * com.google.crypto.tink.PublicKeySign} and {@link com.google.crypto.tink.PublicKeyVerify} key
 * types supported in a particular release of Tink.
 *
 * <p>To register all PublicKeySign and PublicKeyVerify key types provided in the latest Tink
 * version one can do:
 *
 * <pre>{@code
 * SignatureConfig.init();
 * }</pre>
 *
 * <p>For more information on how to obtain and use instances of PublicKeySign or PublicKeyVerify,
 * see {@link PublicKeySignFactory} or {@link PublicKeyVerifyFactory}.
 *
 * @since 1.0.0
 */
public final class SignatureConfig {
  public static final String ECDSA_PUBLIC_KEY_TYPE_URL = new EcdsaVerifyKeyManager().getKeyType();
  public static final String ECDSA_PRIVATE_KEY_TYPE_URL = new EcdsaSignKeyManager().getKeyType();
  public static final String ED25519_PUBLIC_KEY_TYPE_URL = Ed25519PublicKeyManager.TYPE_URL;
  public static final String ED25519_PRIVATE_KEY_TYPE_URL = Ed25519PrivateKeyManager.TYPE_URL;

  /** @deprecated */
  @Deprecated
  public static final RegistryConfig TINK_1_0_0 = RegistryConfig.getDefaultInstance();
  /**
   * @deprecated
   * @since 1.1.0
   */
  @Deprecated
  public static final RegistryConfig TINK_1_1_0 = RegistryConfig.getDefaultInstance();

  /** @since 1.2.0 */
  public static final RegistryConfig LATEST = RegistryConfig.getDefaultInstance();

  static {
    try {
      init();
    } catch (GeneralSecurityException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  /**
   * Tries to register with the {@link Registry} all instances of {@link
   * com.google.crypto.tink.Catalogue} needed to handle PublicKeySign and PublicKeyVerify key types
   * supported in Tink.
   *
   * @deprecated use {@link #register}
   */
  @Deprecated
  public static void init() throws GeneralSecurityException {
    register();
  }

  /**
   * Tries to register with the {@link Registry} all instances of {@link
   * com.google.crypto.tink.Catalogue} needed to handle PublicKeySign and PublicKeyVerify key types
   * supported in Tink.
   *
   * @since 1.2.0
   */
  public static void register() throws GeneralSecurityException {
    Registry.registerAsymmetricKeyManagers(
        new EcdsaSignKeyManager(), new EcdsaVerifyKeyManager(), true);

    Registry.registerKeyManager(new Ed25519PrivateKeyManager());
    Registry.registerKeyManager(new Ed25519PublicKeyManager());

    Registry.registerKeyManager(new RsaSsaPkcs1SignKeyManager());
    Registry.registerKeyManager(new RsaSsaPkcs1VerifyKeyManager());

    Registry.registerKeyManager(new RsaSsaPssSignKeyManager());
    Registry.registerKeyManager(new RsaSsaPssVerifyKeyManager());

    Registry.registerPrimitiveWrapper(new PublicKeySignWrapper());
    Registry.registerPrimitiveWrapper(new PublicKeyVerifyWrapper());
  }
}
