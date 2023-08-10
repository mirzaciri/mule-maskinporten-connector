package org.maskinporten.connector.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;

import no.ks.fiks.maskinporten.Maskinportenklient;
import no.ks.fiks.maskinporten.MaskinportenklientProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class MaskinportenOperations {

  /**
   * get Token uses the standard 'virksomhetssertifikat' to get token from maskinporten
   * @throws Exception 
   */
  @MediaType(value = ANY, strict = false)
  public String getToken(@Config MaskinportenConfiguration config, String issuer, String scope) throws Exception{
		 
	String aud = "";
	String url = "";
	
	switch (config.getEnv()) {
	  case Prod:
		aud = "https://maskinporten.no/";
		url = "https://maskinporten.no/token";
	    break;
	  case VER2:
		aud = "https://ver2.maskinporten.no/";
		url = "https://ver2.maskinporten.no/token";
	    break;
	  case Test:
		aud = "https://test.maskinporten.no/";
		url = "https://test.maskinporten.no/token";
	    break;
	  default:
		aud = "https://maskinporten.no/";
		url = "https://maskinporten.no/token";
	}

	String token = requestMaskinporten(config.cert, config.pwd, config.alias, aud, url, issuer, scope);
    
	return token;
  }
  
  
  private static String requestMaskinporten(String filename, String pwd, String alias, String audience, String tokenEndpoint, String issuer, String scope) throws Exception 
  {
      String keyStoreFilename = filename;
      char[] keyStorePassword = pwd.toCharArray();
      
      KeyStore keyStore = getKeyStore(keyStoreFilename, keyStorePassword);

      Maskinportenklient maskinporten = new Maskinportenklient(keyStore, alias, keyStorePassword, MaskinportenklientProperties.builder()
              .numberOfSecondsLeftBeforeExpire(10)
              .issuer(issuer)
              .audience(audience)
              .tokenEndpoint(tokenEndpoint)
              .build());

      String accessToken = maskinporten.getAccessToken(scope);
      return accessToken;
  }

  private static KeyStore getKeyStore(String keyStoreFilename, char[] keyStorePassword) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException 
  {
      KeyStore keyStore = KeyStore.getInstance("pkcs12");
      keyStore.load(new FileInputStream(keyStoreFilename), keyStorePassword);
      return keyStore;
  }  
}
