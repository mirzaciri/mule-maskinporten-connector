package org.maskinporten.connector.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Path;
import org.springframework.beans.factory.annotation.Configurable;
import org.mule.runtime.api.meta.model.display.PathModel.Type;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension. 
 */
@Operations(MaskinportenOperations.class)
public class MaskinportenConfiguration {
	
	  @DisplayName("Certification Path")
	  @Parameter
	  @Path
	  public String cert;
	  
	  @DisplayName("Certification Key")
	  @Parameter
	  public String pwd;
	  
	  @DisplayName("Certification Alias")
	  @Parameter
	  public String alias;
	  
	  
	  public enum Environment {
		  Prod,
		  Test,
		  VER2
	  }
	  
	 	@DisplayName("Environment")
		@Parameter
		@Optional(defaultValue="Prod")
		private Environment env;

		public Environment getEnv() {
		 return env;
		}

		public void setMyOption(Environment env) {
		 this.env = env;
		}
	  /*
	  @DisplayName("Issuer Id")
	  @Parameter
	  public String issuer;
	  
	  @DisplayName("Scope")
	  @Parameter
	  public String scope;
	  */
}
